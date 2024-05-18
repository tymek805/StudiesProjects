from __future__ import annotations

import re
from abc import ABC
from datetime import datetime
from ipaddress import IPv4Address, AddressValueError
from re import Pattern, Match
from typing import Optional, Union


class SSHLogEntry(ABC):
    _log_pattern: Pattern[str] = re.compile(
        r'(?P<timestamp>\w{3}\s{1,2}\d{1,2}\s\d{2}:\d{2}:\d{2})\s(?P<host>\w+)\s(?P<component>\w+)\[(?P<pid>\d+)]:\s(?P<message>.+)'
    )
    _ipv4_pattern: Pattern[str] = re.compile(r'[0-9]+(?:\.[0-9]+){3}')
    _user_pattern: Pattern[str] = re.compile(
        r'(invalid user |Invalid user |Failed password for invalid user |Failed password for |Accepted password for |user=)(\w+)')
    _port_pattern: Pattern[str] = re.compile(r'port (\d+)')

    def __init__(self, raw_log: str) -> None:
        self._raw_log: str = raw_log
        match: Optional[Match[str]] = re.match(self._log_pattern, self._raw_log)

        if match:
            self.date: str = match.group('timestamp')
            self.host: str = match.group('host')
            self.component: str = match.group('component')
            self.pid: str = match.group('pid')
            self.message: str = match.group('message')
        else:
            raise ValueError("Provided log could not be parsed")

    def __repr__(self) -> str:
        return (f"{self.__class__.__name__}( "
                f"Date: {self.date} | "
                f"Host: {self.host} | "
                f"Component: {self.component} | "
                f"PID: {self.pid} | "
                f"Message: \"{self.message}\" "
                f")")

    def __lt__(self, other: Union[SSHLogEntry, datetime]) -> bool:
        return self.datetime() < (other.datetime() if type(other) is SSHLogEntry else other)

    def __gt__(self, other: Union[SSHLogEntry, datetime]) -> bool:
        return self.datetime() > (other.datetime() if type(other) is SSHLogEntry else other)

    def __eq__(self, other: object) -> bool:
        return self.datetime() == (other.datetime() if type(other) is SSHLogEntry else other)

    def datetime(self) -> datetime:
        return datetime.strptime(self.date, "%b %d %H:%M:%S")

    def validate(self) -> bool:
        raise NotImplementedError

    def ipv4(self) -> Optional[IPv4Address]:
        ipv4: list = re.findall(self._ipv4_pattern, self.message)
        try:
            return IPv4Address(ipv4[0]) if ipv4 else None
        except AddressValueError:
            return None

    @property
    def has_ipv4(self) -> bool:
        return self.ipv4() is not None

    def get_user(self) -> Optional[str]:
        username: list = re.findall(self._user_pattern, self.message)
        return username[0][1] if username else None

    def get_port(self) -> Optional[str]:
        port: list = re.findall(self._port_pattern, self.message)
        return port[0] if port else None


class RejectedPasswordSSH(SSHLogEntry):
    def __init__(self, raw_log: str) -> None:
        super().__init__(raw_log)
        self.ip: Optional[IPv4Address] = self.ipv4()
        self.user: Optional[str] = self.get_user()
        self.port: Optional[str] = self.get_port()

    def validate(self) -> bool:
        match: Optional[Match[str]] = self._log_pattern.match(self._raw_log)
        ipv4: list = re.findall(self._ipv4_pattern, self.message)
        username: list = re.findall(self._user_pattern, self.message)
        port: list = re.findall(self._port_pattern, self.message)

        if not (match and ipv4 and username and port):
            return False

        return (
                self.date == match.group('timestamp') and
                self.host == match.group('host') and
                self.component == match.group('component') and
                self.pid == match.group('pid') and
                self.message == match.group('message') and
                self.ip == IPv4Address(ipv4[0]) and
                self.user == username[0][1] and
                self.port == port[0]
        )


class AcceptedPasswordSSH(SSHLogEntry):
    def __init__(self, raw_log: str) -> None:
        super().__init__(raw_log)
        self.ip: Optional[IPv4Address] = self.ipv4()
        self.user: Optional[str] = self.get_user()
        self.port: Optional[str] = self.get_port()

        if not (self.user and self.port and self.ip):
            raise ValueError(f"Provided log does not contain all required values [user, port, ipv4]")

    def validate(self) -> bool:
        match: Optional[Match[str]] = self._log_pattern.match(self._raw_log)
        ipv4: list = re.findall(self._ipv4_pattern, self.message)
        username: list = re.findall(self._user_pattern, self.message)
        port: list = re.findall(self._port_pattern, self.message)

        if not (match and ipv4 and username and port):
            return False

        return (
                self.date == match.group('timestamp') and
                self.host == match.group('host') and
                self.component == match.group('component') and
                self.pid == match.group('pid') and
                self.message == match.group('message') and
                self.ip == IPv4Address(ipv4[0]) and
                self.user == username[0][1] and
                self.port == port[0]
        )


class ErrorSSH(SSHLogEntry):
    def __init__(self, raw_log: str) -> None:
        super().__init__(raw_log)
        self.ip: Optional[IPv4Address] = self.ipv4()

        if not self.ip:
            raise ValueError(f"Provided log does not contain required value: ipv4")

    def validate(self) -> bool:
        match: Optional[Match[str]] = self._log_pattern.match(self._raw_log)
        ipv4: list = re.findall(self._ipv4_pattern, self.message)

        if not (match and ipv4):
            return False

        return (
                self.date == match.group('timestamp') and
                self.host == match.group('host') and
                self.component == match.group('component') and
                self.pid == match.group('pid') and
                self.message == match.group('message') and
                self.ip == IPv4Address(ipv4[0])
        )


class OtherSSH(SSHLogEntry):
    def validate(self) -> bool:
        return True

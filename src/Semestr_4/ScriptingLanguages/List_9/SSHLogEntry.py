import re
from abc import ABC
from datetime import datetime
from ipaddress import IPv4Address


class SSHLogEntry(ABC):
    _log_pattern = re.compile(
        r'(?P<timestamp>\w{3}\s{1,2}\d{1,2}\s\d{2}:\d{2}:\d{2})\s(?P<host>\w+)\s(?P<component>\w+)\[(?P<pid>\d+)]:\s(?P<message>.+)'
    )
    _ipv4_pattern = re.compile(r'[0-9]+(?:\.[0-9]+){3}')
    _user_pattern = re.compile(
        r'(invalid user |Invalid user |Failed password for invalid user |Failed password for |Accepted password for |user=)(\w+)')
    _port_pattern = re.compile(r'port (\d+)')

    def __init__(self, raw_log: str):
        self._raw_log = raw_log
        match = re.match(self._log_pattern, self._raw_log)

        if match:
            self.date = match.group('timestamp')
            self.host = match.group('host')
            self.component = match.group('component')
            self.pid = match.group('pid')
            self.message = match.group('message')
        else:
            raise ValueError("Provided log could not be parsed")

    def __repr__(self):
        return (f"{self.__class__.__name__}( "
                f"Date: {self.date} | "
                f"Host: {self.host} | "
                f"Component: {self.component} | "
                f"PID: {self.pid} | "
                f"Message: \"{self.message}\" "
                f")")

    def __lt__(self, other):
        other_date = other.datetime() if type(other) is SSHLogEntry else other
        return self.datetime() < other_date

    def __gt__(self, other):
        other_date = other.datetime() if type(other) is SSHLogEntry else other
        return self.datetime() > other_date

    def __eq__(self, other):
        other_date = other.datetime() if type(other) is SSHLogEntry else other
        return self.datetime() == other_date

    def datetime(self):
        return datetime.strptime(f"{self.date}", "%b %d %H:%M:%S")

    def validate(self) -> bool:
        raise NotImplementedError

    def ipv4(self):
        ipv4 = re.findall(self._ipv4_pattern, self.message)
        return IPv4Address(ipv4[0]) if ipv4 else None

    @property
    def has_ipv4(self):
        return self.ipv4() is not None

    def user(self):
        username = re.findall(self._user_pattern, self.message)
        return username[0][1] if username else None

    def port(self):
        port = re.findall(self._port_pattern, self.message)
        return port[0] if port else None


class RejectedPasswordSSH(SSHLogEntry):
    def __init__(self, raw_log):
        super().__init__(raw_log)
        self.ip = self.ipv4()
        self.user = self.user()
        self.port = self.port()

        if not (self.user and self.port and self.ip):
            raise ValueError(f"Provided log does not contain all required values [ipv4, user, port]")

    def validate(self):
        match = self._log_pattern.match(self._raw_log)
        ipv4 = re.findall(self._ipv4_pattern, self.message)
        username = re.findall(self._user_pattern, self.message)
        port = re.findall(self._port_pattern, self.message)

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
    def __init__(self, raw_log):
        super().__init__(raw_log)
        self.user = self.user()
        self.port = self.port()
        self.ip = self.ipv4()

        if not (self.user and self.port and self.ip):
            raise ValueError(f"Provided log does not contain all required values [user, port, ipv4]")

    def validate(self):
        match = self._log_pattern.match(self._raw_log)
        ipv4 = re.findall(self._ipv4_pattern, self.message)
        username = re.findall(self._user_pattern, self.message)
        port = re.findall(self._port_pattern, self.message)

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
    def __init__(self, raw_log):
        super().__init__(raw_log)
        self.ip = self.ipv4()

        if not self.ip:
            raise ValueError(f"Provided log does not contain required value: ipv4")

    def validate(self):
        match = self._log_pattern.match(self._raw_log)
        ipv4 = re.findall(self._ipv4_pattern, self.message)

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
    def validate(self):
        return True

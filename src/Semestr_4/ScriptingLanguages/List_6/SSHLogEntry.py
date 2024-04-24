import re
from ipaddress import IPv4Address


class SSHLogEntry:
    _log_pattern = re.compile(
        r"(?P<timestamp>\w{3}\s{1,2}\d{1,2}\s\d{2}:\d{2}:\d{2})\s(?P<host>\w+)\s(?P<component>\w+)\[(?P<pid>\d+)]:\s(?P<message>.+)"
    )

    def __init__(self, raw_log: str):
        match = re.match(self._log_pattern, raw_log)

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

    def ipv4(self):
        ipv4 = re.findall(r"[0-9]+(?:\.[0-9]+){3}", self.message)
        return IPv4Address(ipv4[0]) if ipv4 else None


class FailedSSH(SSHLogEntry):
    def __init__(self, raw_log):
        super().__init__(raw_log)


if __name__ == "__main__":
    SSHLogEntry('Jan  7 16:56:15 LabSZ sshd[30216]: Failed password for root from 104.192.1.10 port 41994 ssh2')

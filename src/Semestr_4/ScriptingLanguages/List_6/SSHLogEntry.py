import re
from ipaddress import IPv4Address


class SSHLogEntry:
    def __init__(self, raw_log):
        self.date = " ".join(raw_log[:3])
        self.host = raw_log[3]
        self.component = raw_log[4].split('[')[0]
        self.pid = raw_log[4].split('[')[1].split(']')[0]
        self.message = " ".join(raw_log[5:])

    def __repr__(self):
        return (f"{self.__class__.__name__}( "
                f"Date: {self.date} | "
                f"Host: {self.host} | "
                f"Component: {self.component} | "
                f"PID: {self.pid} | "
                f"Message: \"{self.message}\" "
                f")")

    def get_ipv4(self):
        ipv4 = re.findall(r'[0-9]+(?:\.[0-9]+){3}', self.message)
        return IPv4Address(ipv4[0]) if ipv4 else None

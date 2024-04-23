import re
from ipaddress import IPv4Address


class SSHLogEntry:
    def __init__(self, date, host, component, pid, message):
        self.date = date
        self.host = host
        self.component = component
        self.pid = pid
        self.message = message

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

import re
from datetime import datetime


class SSHUser:
    def __init__(self, username: str, date: datetime | None):
        self.username = username
        self.date = date

    def validate(self):
        return re.match(r"^[a-z_][a-z0-9_-]{0,31}$", self.username) is not None

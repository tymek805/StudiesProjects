from datetime import datetime
from typing import Any, Iterator

from SSHLogEntry import RejectedPasswordSSH, AcceptedPasswordSSH, ErrorSSH, OtherSSH, SSHLogEntry


class SSHLogJournal:
    _ssh_classes: list[type[SSHLogEntry]] = [RejectedPasswordSSH, AcceptedPasswordSSH, ErrorSSH, OtherSSH]

    def __init__(self) -> None:
        self._ssh_logs: list[SSHLogEntry] = []

    def append(self, raw_log: str) -> None:
        log: SSHLogEntry = OtherSSH(raw_log)
        if 'Failed' in raw_log:
            log = RejectedPasswordSSH(raw_log)
        elif 'Accepted' in raw_log:
            log = AcceptedPasswordSSH(raw_log)
        elif 'error' in raw_log:
            log = ErrorSSH(raw_log)

        if not log.validate():
            raise ValueError('Incorrect data for detected type!')
        self._ssh_logs.append(log)

    def __len__(self) -> int:
        return len(self._ssh_logs)

    def __contains__(self, item: Any) -> bool:
        return item in self._ssh_logs

    def __iter__(self) -> Iterator[SSHLogEntry]:
        return iter(self._ssh_logs)

    def get_logs_by_date(self, start_date: datetime, end_date: datetime = datetime.now()) -> list[SSHLogEntry]:
        return [log for log in self._ssh_logs if (start_date < log < end_date)]

from datetime import datetime

from SSHLogEntry import RejectedPasswordSSH, AcceptedPasswordSSH, ErrorSSH, OtherSSH


class SSHLogJournal:
    _ssh_classes = [RejectedPasswordSSH, AcceptedPasswordSSH, ErrorSSH, OtherSSH]

    def __init__(self):
        self._ssh_logs = []

    def append(self, raw_log: str):
        if 'Failed' in raw_log:
            log = RejectedPasswordSSH(raw_log)
        elif 'Accepted' in raw_log:
            log = AcceptedPasswordSSH(raw_log)
        elif 'error' in raw_log:
            log = ErrorSSH(raw_log)
        else:
            log = OtherSSH(raw_log)

        if not log.validate():
            raise ValueError('Incorrect data for detected type!')
        self._ssh_logs.append(log)

    def __len__(self):
        return len(self._ssh_logs)

    def __contains__(self, item):
        return item in self._ssh_logs

    def __iter__(self):
        return iter(self._ssh_logs)

    def get_logs_by_date(self, start_date: datetime, end_date: datetime = datetime.now()):
        return [log for log in self._ssh_logs if (start_date < log < end_date)]

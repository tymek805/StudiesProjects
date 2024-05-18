from datetime import datetime
from ipaddress import IPv4Address

import pytest

from SSHLogEntry import RejectedPasswordSSH, OtherSSH, AcceptedPasswordSSH, ErrorSSH, SSHLogEntry
from SSHLogJournal import SSHLogJournal


def test_timestamp_extraction():
    raw_entry = "Dec 10 06:55:48 LabSZ sshd[24200]: Failed password for invalid user webmaster from 173.234.31.186 port 38926 ssh2"
    entry = RejectedPasswordSSH(raw_entry)
    assert entry.datetime() == datetime.strptime("Dec 10 06:55:48", '%b %d %H:%M:%S')


@pytest.mark.parametrize("raw_log, expected_ipv4", [
    (
    "Dec 10 06:55:48 LabSZ sshd[24200]: Failed password for invalid user webmaster from 173.234.31.186 port 38926 ssh2",
    IPv4Address("173.234.31.186")),
    (
    "Dec 10 06:55:48 LabSZ sshd[24200]: Failed password for invalid user webmaster from 666.777.88.213 port 38926 ssh2",
    None),
    ("Dec 10 06:55:48 LabSZ sshd[24200]: Failed password for invalid user webmaster from unknown port 38926 ssh2", None)
])
def test_ipv4_extraction(raw_log, expected_ipv4):
    log: SSHLogEntry = OtherSSH(raw_log)
    if 'Failed' in raw_log:
        log = RejectedPasswordSSH(raw_log)
    elif 'Accepted' in raw_log:
        log = AcceptedPasswordSSH(raw_log)
    elif 'error' in raw_log:
        log = ErrorSSH(raw_log)
    assert log.ipv4() == expected_ipv4


@pytest.mark.parametrize("log_class, raw_log", [
    (AcceptedPasswordSSH,
     "Dec 10 09:32:20 LabSZ sshd[24680]: Accepted password for fztu from 119.137.62.142 port 49116 ssh2"),
    (RejectedPasswordSSH,
     "Dec 10 06:55:48 LabSZ sshd[24200]: Failed password for invalid user webmaster from 173.234.31.186 port 38926 ssh2"),
    (ErrorSSH,
     "Dec 10 11:03:44 LabSZ sshd[25455]: error: Received disconnect from 103.99.0.122: 14: No more user authentication methods available. [preauth]"),
    (OtherSSH,
     "Dec 10 07:28:37 LabSZ sshd[24273]: Received disconnect from 112.95.230.3: 11: Bye Bye [preauth]")
])
def test_append_type(log_class, raw_log: str):
    journal = SSHLogJournal()
    assert isinstance(journal.append(raw_log), log_class)

import os
import re
from datetime import datetime
from enum import Enum

from SSHLogEntry import RejectedPasswordSSH, AcceptedPasswordSSH, ErrorSSH, OtherSSH
from SSHLogJournal import SSHLogJournal
from SSHUser import SSHUser


if __name__ == "__main__":
    journal = SSHLogJournal()
    x = RejectedPasswordSSH('Dec 10 06:55:48 LabSZ sshd[24200]: Failed password for invalid user webmaster from 173.234.31.186 port 38926 ssh2')
    y = AcceptedPasswordSSH('Dec 10 09:32:20 LabSZ sshd[24680]: Accepted password for fztu from 119.137.62.142 port 49116 ssh2')
    z = ErrorSSH('Dec 10 11:03:44 LabSZ sshd[25455]: error: Received disconnect from 103.99.0.122: 14: No more user authentication methods available. [preauth]')
    [print(ssh.validate()) for ssh in [x, y, z]]
    journal.append('Dec 10 06:55:48 LabSZ sshd[24200]: Failed password for invalid user webmaster from 173.234.31.186 port 38926 ssh2')
    journal.append('Dec 10 09:32:20 LabSZ sshd[24680]: Accepted password for fztu from 119.137.62.142 port 49116 ssh2')
    journal.append('Dec 10 11:03:44 LabSZ sshd[25455]: error: Received disconnect from 103.99.0.122: 14: No more user authentication methods available. [preauth]')
    print(journal.get_logs_by_date(datetime(1800, 1, 1)))

    journal = SSHLogJournal()
    journal.append("Dec 10 06:55:46 LabSZ sshd[24200]: reverse mapping checking getaddrinfo for ns.marryaldkfaczcz.com [173.234.31.186] failed - POSSIBLE BREAK-IN ATTEMPT!")
    journal.append("Dec 10 06:55:46 LabSZ sshd[24200]: Invalid user webmaster from 173.234.31.186")
    journal.append("Dec 10 06:55:46 LabSZ sshd[24200]: input_userauth_request: invalid user webmaster [preauth]")
    journal.append("Dec 10 06:55:46 LabSZ sshd[24200]: pam_unix(sshd:auth): check pass; user unknown")
    journal.append("Dec 10 06:55:46 LabSZ sshd[24200]: pam_unix(sshd:auth): authentication failure; logname= uid=0 euid=0 tty=ssh ruser= rhost=173.234.31.186")
    journal.append("Dec 10 06:55:48 LabSZ sshd[24200]: Failed password for invalid user webmaster from 173.234.31.186 port 38926 ssh2")
    journal.append("Dec 10 06:55:48 LabSZ sshd[24200]: Connection closed by 173.234.31.186 [preauth]")
    journal.append("Dec 10 07:02:47 LabSZ sshd[24203]: Connection closed by 212.47.254.145 [preauth]")
    journal.append("Dec 10 07:07:38 LabSZ sshd[24206]: Invalid user test9 from 52.80.34.196")
    journal.append("Dec 10 07:07:38 LabSZ sshd[24206]: input_userauth_request: invalid user test9 [preauth]")
    journal.append("Dec 10 07:07:38 LabSZ sshd[24206]: pam_unix(sshd:auth): check pass; user unknown")
    journal.append("Dec 10 07:07:38 LabSZ sshd[24206]: pam_unix(sshd:auth): authentication failure; logname= uid=0 euid=0 tty=ssh ruser= rhost=ec2-52-80-34-196.cn-north-1.compute.amazonaws.com.cn")
    users = [SSHUser("webmaster", None), SSHUser("test9", None)]

    log_entries = journal.get_logs_by_date(datetime(1800, 1, 1))

    two_lists = users + log_entries

    for item in two_lists:
        print(item)
        if not item.validate():
            print(f"Invalid item: {item}")
            break


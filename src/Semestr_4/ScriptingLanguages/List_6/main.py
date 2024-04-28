from datetime import datetime

from SSHLogEntry import OtherSSH
from SSHLogJournal import SSHLogJournal
from SSHUser import SSHUser

if __name__ == "__main__":
    journal = SSHLogJournal()
    test_line = ''

    with open('SSH.log', 'r') as f:
        for line in f.readlines()[:10]:
            if test_line == '': test_line = line
            journal.append(line)

    for line in journal:
        print(line)
    print(OtherSSH(test_line) in journal)
    print('test SSH' in journal)
    print(len(journal))
    print(journal.get_logs_by_date(datetime(1800, 1, 1)))

    users = [SSHUser("webmaster", None), SSHUser("test9", None)]
    for user in users:
        print(user.validate())

    two_lists = users + journal.get_logs_by_date(datetime(1800, 1, 1))

    for item in two_lists:
        if item.validate():
            print(item)

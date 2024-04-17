import re
from collections import Counter
from datetime import datetime


def read_log(log: str) -> dict:
    log = log.split()
    return {
        'date': datetime.strptime(" ".join(log[:3]), "%b %d %H:%M:%S"),
        'host': log[3],
        'component': log[4].split('[')[0],
        'PID': log[4].split('[')[1].split(']')[0],
        'message': " ".join(log[5:]),
    }


def get_ipv4_from_log(log: dict):
    ipv4 = re.findall(r'[0-9]+(?:\.[0-9]+){3}', log['message'])
    return ipv4 if ipv4 else None


def get_user_from_log(log: dict):
    username = re.findall(r'user (\w+)', log['message'])
    return username[0] if username else None


def get_message_type(msg: str):
    if re.search(r'Accepted password for \w+ from', msg):
        return "Successful login"
    elif re.search(r'Failed password for \w+ from', msg):
        return "Failed login"
    elif re.search(r'Connection closed by|Disconnect(ed|ing)', msg):
        return "Closed connection"
    elif re.search(r'Failed password for invalid user', msg):
        return "Wrong password"
    elif re.search(r'[I|i]nvalid user', msg):
        return "Wrong username"
    elif re.search(r'POSSIBLE BREAK-IN ATTEMPT!', msg):
        return "Break-in attempt"
    else:
        return "inne"


def read_logs():
    with open("SSH.log") as f:
        return [read_log(log) for log in f.readlines()]


if __name__ == "__main__":
    arr = [get_message_type(l['message']) for l in read_logs()[:50]]
    print(Counter(arr))

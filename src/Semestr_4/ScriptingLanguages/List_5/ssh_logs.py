import random
import re
from datetime import datetime
from enum import Enum

from logger import define_logger

logger = define_logger()


class MessageType(Enum):
    SUCCESS = "Successful login"
    FAILURE = "Failed login"
    CLOSED = "Connection closed"
    PASSWORD = "Wrong password"
    USERNAME = "Wrong username"
    BREAKIN = "Break-in attempt"
    OTHER = "Other"


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


def get_message_type(msg: str) -> MessageType:
    types = [
        (r'Accepted password for \w+ from', MessageType.SUCCESS),
        (r'Failed password for \w+ from', MessageType.FAILURE),
        (r'Connection closed by|Disconnect(ed|ing)', MessageType.CLOSED),
        (r'Failed password for invalid user', MessageType.PASSWORD),
        (r'[I|i]nvalid user', MessageType.USERNAME),
        (r'POSSIBLE BREAK-IN ATTEMPT!', MessageType.BREAKIN),
    ]
    for t in types:
        if re.search(t[0], msg):
            return t[1]
    return MessageType.OTHER


def get_logs_from_random_user(n: int):
    if n < 1:
        raise ValueError("n must be positive number")

    logs = read_logs()
    user = random.choice(list(set([get_user_from_log(log) for log in logs])))
    logs = [log for log in logs if get_user_from_log(log) == user]
    return [random.choice(logs) for _ in range(n)]


def read_logs():
    with open("SSH.log") as f:
        logs = []
        read_bytes = 0

        for log in f.readlines():
            log_dict = read_log(log)
            logs.append(log_dict)
            log_message_type(get_message_type(log_dict['message']))
            read_bytes += len(log)

        logger.debug(f"{read_bytes} bytes read")
        return logs


def log_message_type(msg_type: MessageType):
    if msg_type == MessageType.SUCCESS or msg_type == MessageType.CLOSED:
        logger.info(msg_type.value)
    elif msg_type == MessageType.FAILURE:
        logger.warning(msg_type.value)
    elif msg_type == MessageType.PASSWORD or msg_type == MessageType.USERNAME:
        logger.error(msg_type.value)
    elif msg_type == MessageType.BREAKIN:
        logger.critical(msg_type.value)


if __name__ == "__main__":
    get_logs_from_random_user(1)
    # arr = [get_message_type(l['message']) for l in read_logs()[:50]]

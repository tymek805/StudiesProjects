import logging
import os
import random
import re
import statistics
from datetime import datetime
from enum import Enum

logger = logging.getLogger()


class MessageType(Enum):
    SUCCESS = "Successful login"
    FAILURE = "Failed login"
    CLOSED = "Connection closed"
    PASSWORD = "Wrong password"
    USERNAME = "Wrong username"
    BREAK_IN = "Break-in attempt"
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


def get_message_type_from_log(log: dict) -> MessageType:
    types = [
        (r'Accepted password for \w+ from', MessageType.SUCCESS),
        (r'Failed password for \w+ from', MessageType.FAILURE),
        (r'Connection closed by|Disconnect(ed|ing)', MessageType.CLOSED),
        (r'Failed password for invalid user', MessageType.PASSWORD),
        (r'[I|i]nvalid user', MessageType.USERNAME),
        (r'POSSIBLE BREAK-IN ATTEMPT!', MessageType.BREAK_IN),
    ]
    msg = log['message']
    for t in types:
        if re.search(t[0], msg):
            return t[1]
    return MessageType.OTHER


def log_message_type(msg_type: MessageType):
    if msg_type == MessageType.SUCCESS or msg_type == MessageType.CLOSED:
        logger.info(msg_type.value)
    elif msg_type == MessageType.FAILURE:
        logger.warning(msg_type.value)
    elif msg_type == MessageType.PASSWORD or msg_type == MessageType.USERNAME:
        logger.error(msg_type.value)
    elif msg_type == MessageType.BREAK_IN:
        logger.critical(msg_type.value)


def get_logs_from_random_user(n: int, logs):
    if n < 1:
        raise ValueError("n must be positive number")

    user = random.choice(list(set([get_user_from_log(log) for log in logs])))
    logs = [log for log in logs if get_user_from_log(log) == user]
    return [random.choice(logs) for _ in range(n)]


def calculate_avg_duration_and_deviation(logs):
    sessions_start = {}
    durations = []

    for log in logs:
        if 'session opened' in log['message']:
            sessions_start[log['PID']] = log['date']
        elif 'session closed' in log['message'] and log['PID'] in sessions_start:
            start_time = sessions_start.pop(log['PID'])
            if start_time > log['date']:
                start_time = start_time.replace(year=start_time.year - 1)
            durations.append((log['date'] - start_time).total_seconds())
    if durations:
        return statistics.mean(durations), (statistics.stdev(durations) if len(durations) > 2 else 0.0)
    else:
        return 0.0, 0.0


def calculate_avg_and_deviation_by_users(logs):
    sessions_start = {}
    users_pid = {}
    users_durations = {}

    for log in logs:
        if 'session opened' in log['message']:
            sessions_start[log['PID']] = log['date']
            users_pid[log['PID']] = get_user_from_log(log)
        elif 'session closed' in log['message'] and log['PID'] in sessions_start:
            start_time = sessions_start.pop(log['PID'])
            if start_time > log['date']:
                start_time = start_time.replace(year=start_time.year - 1)

            durations = users_durations.get(get_user_from_log(log), [])
            durations.append((log['date'] - start_time).total_seconds())
            users_durations[get_user_from_log(log)] = durations

    average_by_users = {}
    for user, durations in users_durations.items():
        average_by_users[user] = statistics.mean(durations), (
            statistics.stdev(durations) if len(durations) > 2 else 0.0)
    return average_by_users


def most_least_user_login(logs):
    users_login = {}

    for log in logs:
        user = get_user_from_log(log)
        mt = get_message_type_from_log(log)
        if user is not None and mt in [MessageType.USERNAME, MessageType.PASSWORD]:
            users_login[user] = users_login.get(user, 0) + 1

    return min(users_login, key=users_login.get), max(users_login, key=users_login.get)


def read_logs(path):
    if os.path.isfile(path):
        with open(path) as f:
            logs = []
            read_bytes = 0

            for line in f.readlines():
                log = read_log(line)
                logs.append(log)
                # log_message_type(get_message_type_from_log(log))
                read_bytes += len(line)

            logger.debug(f"{read_bytes} bytes read")
            return logs
    else:
        raise FileNotFoundError("File does not exist!")


if __name__ == "__main__":
    print(most_least_user_login(read_logs("SSH.log")))

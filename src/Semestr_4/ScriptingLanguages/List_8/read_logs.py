from datetime import datetime


def read_logs(path: str) -> list[str]:
    with open(path, 'r') as file:
        return file.readlines()


def log_to_dict(log: str) -> dict:
    separated_line = log.split()
    date = datetime.strptime(log.split('[')[1].split(']')[0], '%d/%b/%Y:%H:%M:%S %z')
    method, resource = log.split('"')[1].split()[:2]
    return {
        'host': separated_line[0],
        'date': date,
        'timezone': datetime.strftime(date, '%z'),
        'method': method,
        'resource': resource,
        'code': separated_line[-2],
        'size': separated_line[-1],
    }

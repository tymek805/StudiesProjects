import sys


def count_requests():
    requests_status_dir = {}

    for line in sys.stdin:
        request_status = line.split()[-2]
        if request_status not in requests_status_dir.keys():
            requests_status_dir[request_status] = 1
        else:
            requests_status_dir[request_status] += 1

    for key, val in requests_status_dir.items():
        sys.stdout.write(f"Amount of requests with code '{key}': {val}\n")


if __name__ == '__main__':
    count_requests()

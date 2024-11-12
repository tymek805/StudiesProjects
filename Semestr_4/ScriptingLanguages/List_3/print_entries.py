from read_l import read_l


def print_entries(logs, n):
    if n < 0 or n >= len(logs):
        raise ValueError("Invalid index")

    [print(log) for log in logs[n:]]


if __name__ == "__main__":
    print_entries(read_l(), 0)

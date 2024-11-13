from read_l import read_l


def get_entries_addr(logs, host: str):
    return [log for log in logs if log[0] == host]


if __name__ == "__main__":
    print(get_entries_addr(read_l(), "d104.aa.net"))

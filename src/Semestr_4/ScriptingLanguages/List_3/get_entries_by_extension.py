from read_l import read_l


def get_entries_by_extension(logs, extension):
    return [log for log in logs if log[2].split()[1].split('.')[-1] == extension]


if __name__ == "__main__":
    print(get_entries_by_extension(read_l(), 'gif'))

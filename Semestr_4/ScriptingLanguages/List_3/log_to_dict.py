from read_l import read_l


def log_to_dict(logs):
    entry_dict = {}
    for log in logs:
        key = log[0]
        if key in entry_dict.keys():
            entry_dict[key].append(log)
        else:
            entry_dict[key] = [log]
    return entry_dict


if __name__ == "__main__":
    print(log_to_dict(read_l()))

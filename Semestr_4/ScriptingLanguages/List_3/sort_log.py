from read_l import read_l


def sort_log(logs, idx):
    if len(logs) < 1:
        return
    if idx < 0 or idx > len(logs[0]):
        raise IndexError("Invalid index")
    return sorted(logs, key=lambda x: x[idx])


if __name__ == "__main__":
    print(sort_log(read_l(), 4))

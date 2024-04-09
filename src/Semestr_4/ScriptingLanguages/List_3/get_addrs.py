from log_to_dict import log_to_dict
from read_l import read_l


def get_addrs(logs_dict):
    return list(logs_dict.keys())


if __name__ == "__main__":
    print(get_addrs(log_to_dict(read_l())))

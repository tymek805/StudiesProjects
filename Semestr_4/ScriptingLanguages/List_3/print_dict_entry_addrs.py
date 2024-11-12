from log_to_dict import log_to_dict
from read_l import read_l


def print_dict_entry_addrs(logs_dict, code):
    for key, value in logs_dict.items():
        dates = [entry[1] for entry in value]
        code_ratio = [1 if entry[3] == code else 0 for entry in value]
        print(
            f"IP address: {key}\n"
            f"First request date: {min(dates)}\n"
            f"Last request date: {max(dates)}\n"
            f"Sent requests: {len(value)}\n"
            f"Code ratio: {sum(code_ratio) / len(code_ratio)}\n"
        )


if __name__ == "__main__":
    print_dict_entry_addrs(log_to_dict(read_l()), 200)

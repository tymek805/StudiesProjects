from read_l import read_l


def get_reads(logs, first_code, second_code, return_one=False):
    if return_one is False:
        return [log for log in logs if log[3] == first_code], [log for log in logs if log[3] == second_code]
    return [log for log in logs if log[3] in [first_code, second_code]]


if __name__ == "__main__":
    print(get_reads(read_l(), 200, 400))

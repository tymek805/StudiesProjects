import sys
from datetime import datetime as dt


def read_l():
    array = []

    for line in sys.stdin:
        separated_list = line.split()
        try:
            host = separated_list[0]
            date_signature = dt.strptime(line.split('[')[1].split(']')[0].split()[0], '%d/%b/%Y:%H:%M:%S')
            http_protocol = line.split('"')[1]
            http_response, resources = int(separated_list[-2]), int(separated_list[-1])

            array.append((host, date_signature, http_protocol, http_response, resources))
        except ValueError:
            pass
    return array


if __name__ == "__main__":
    print(read_l())

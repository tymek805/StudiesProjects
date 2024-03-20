import sys

from util.get_package import get_package
from util.write_error_msg import error


def get_input_hours():
    if len(sys.argv) != 3:
        raise TypeError("Incorrect number of variables. Expected two variables [being_hour, end_hour]")

    try:
        begin_hour, end_hour = int(sys.argv[1]), int(sys.argv[2])
        if 0 <= begin_hour <= 23 and 0 <= end_hour <= 23:
            return begin_hour, end_hour
        else:
            raise ValueError
    except ValueError:
        raise ValueError("Invalid 'hour' variables. 'hour' have to be a number from range (0, 23)")


def filter_by_hour():
    try:
        begin_hour, end_hour = get_input_hours()
        for line in sys.stdin:
            hour = int(line.split('[')[1].split(']')[0].split()[0][-8:-6])
            package = get_package(line)
            if package != '':
                if begin_hour > end_hour:
                    if hour >= begin_hour or hour < end_hour:
                        sys.stdout.write(line)
                elif begin_hour <= hour < end_hour:
                    sys.stdout.write(line)
    except (ValueError, TypeError) as e:
        error(e)


if __name__ == '__main__':
    filter_by_hour()

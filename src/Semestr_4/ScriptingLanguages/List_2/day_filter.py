import sys
from datetime import datetime as dt

from util.get_package import get_package
from util.write_error_msg import error


def get_input_date():
    if len(sys.argv) != 2:
        raise TypeError("Incorrect number of variables. Expected one variable [date]")

    try:
        return dt.strptime(sys.argv[1], '%d.%m.%Y').strftime('%d/%b/%Y')
    except ValueError:
        raise ValueError("Invalid 'date' variable. 'date' have to be a date in supported format: DD.MM.YYYY")


def filter_by_date():
    # 28-07-1995
    try:
        date = get_input_date()
        [sys.stdout.write(line) for line in sys.stdin if date in line and get_package(line) != '']
    except (TypeError, ValueError) as e:
        error(e)


if __name__ == '__main__':
    filter_by_date()

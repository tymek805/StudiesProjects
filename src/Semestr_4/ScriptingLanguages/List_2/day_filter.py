import sys
from datetime import datetime as dt

from get_package import get_package


def get_input_date():
    if len(sys.argv) < 2:
        raise TypeError("'date' variable is missing")

    date = sys.argv[1]

    if len(sys.argv) != 2:
        sys.stdout.write(f"Passed more than one variable, omitting others than {date}\n")

    try:
        return dt.strptime(date, '%d-%m-%Y').strftime('%d/%b/%Y')
    except ValueError as e:
        raise ValueError("Invalid 'date' variable. 'date' has to be in supported format: DD-MM-YYYY")


def find_code():
    # 28-07-1995
    try:
        date = get_input_date()
    except (TypeError, ValueError) as e:
        print("Error:", e)
    else:
        for line in sys.stdin:
            if date in line:
                package = get_package(line)
                if package != '':
                    sys.stdout.write(package + '\n')


if __name__ == '__main__':
    find_code()

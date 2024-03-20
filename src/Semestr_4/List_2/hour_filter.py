import sys

from get_package import get_package


def get_input_date():
    if len(sys.argv) < 3:
        raise TypeError("'hours' variables are missing")

    begin_hour, end_hour = sys.argv[1:3]

    if len(sys.argv) != 3:
        sys.stdout.write(f"Passed more than required variables, omitting others than {begin_hour, end_hour}\n")

    try:
        begin_hour, end_hour = int(begin_hour), int(end_hour)
        if not (0 <= begin_hour <= 23 and 0 <= end_hour <= 23):
            raise ValueError
        else:
            return begin_hour, end_hour
    except ValueError:
        raise ValueError("Invalid 'hours' variable. 'hours' have to be a numbers from range (0, 23)")


def find_code():
    try:
        begin_hour, end_hour = get_input_date()
    except ValueError as e:
        print("Error:", e)
    else:
        for line in sys.stdin:
            hour = int(line.split('[')[1].split(']')[0].split()[0][-8:-6])
            package = get_package(line)
            if package != '':
                if begin_hour > end_hour:
                    if hour >= begin_hour or hour < end_hour:
                        sys.stdout.write(package + '\n')
                elif begin_hour <= hour < end_hour:
                    sys.stdout.write(package + '\n')


if __name__ == '__main__':
    find_code()

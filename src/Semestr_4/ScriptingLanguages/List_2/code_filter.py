import sys

from util.write_error_msg import error


def get_input_code():
    if len(sys.argv) != 2:
        raise TypeError("Incorrect number of variables. Expected one variable [code]")

    try:
        code = int(sys.argv[1])
        if code < 0:
            raise ValueError
        else:
            return str(code)
    except ValueError:
        raise ValueError("Invalid 'code' variable. 'code' have to be a positive number")


def filter_by_code():
    try:
        code = get_input_code()
        [sys.stdout.write(line) for line in sys.stdin if line.split()[-2] == code]
    except (TypeError, ValueError) as e:
        error(e)


if __name__ == '__main__':
    filter_by_code()

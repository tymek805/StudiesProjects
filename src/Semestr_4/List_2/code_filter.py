import sys


def get_input_code():
    if len(sys.argv) < 2:
        raise TypeError("'code' variable is missing")

    code = sys.argv[1]

    if len(sys.argv) != 2:
        sys.stdout.write(f"Passed more than one variable, omitting others than {code}\n")

    try:
        code = int(code)
        if code < 0:
            raise ValueError
        else:
            return str(code)
    except ValueError:
        raise ValueError("Invalid 'code' variable. 'code' have to be a number")


def find_code():
    try:
        code = get_input_code()
    except ValueError as e:
        print("Error:", e)
    else:
        [sys.stdout.write(line) for line in sys.stdin if line.split()[-2] == code]


if __name__ == '__main__':
    find_code()

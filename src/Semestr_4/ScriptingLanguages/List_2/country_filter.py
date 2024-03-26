import sys

from util.write_error_msg import error


def get_input_country():
    if len(sys.argv) != 2:
        raise TypeError("Incorrect number of variables. Expected one variable [country]")

    country = sys.argv[1]

    if len(country) != 2:
        raise ValueError("Invalid 'country' variable. 'country' have to be a two character string")

    return country


def filter_by_country():
    try:
        country = get_input_country()
        for line in sys.stdin:
            if line.split()[0].split('.')[-1] == country:
                sys.stdout.write(line)
    except (TypeError, ValueError) as e:
        error(e)


if __name__ == '__main__':
    filter_by_country()

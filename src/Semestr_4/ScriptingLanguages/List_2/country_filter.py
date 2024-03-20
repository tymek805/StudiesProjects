import sys


def get_input_country():
    if len(sys.argv) < 2:
        raise TypeError("'country' variable is missing")

    country = sys.argv[1]

    if len(sys.argv) != 2:
        sys.stdout.write(f"Passed more than one variable, omitting others than {country}\n")

    if len(country) < 2:
        raise ValueError("Invalid country postfix! Country postfix has to have two characters")

    if len(country) > 2:
        sys.stdout.write(f"Passed variable has more than two characters, omitting others than {country[:2]}\n")

    return country[:2]


def find_code():
    try:
        country = get_input_country()
    except ValueError as e:
        print("Error:", e)
    else:
        for line in sys.stdin:
            if line.split()[0].split('.')[-1] == country:
                sys.stdout.write(line.split('"')[1] + '\n')


if __name__ == '__main__':
    find_code()

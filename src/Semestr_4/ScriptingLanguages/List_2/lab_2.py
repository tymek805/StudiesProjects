import sys


def print_input_file():
    for line in sys.stdin:
        print(line.replace('\n', ''))


if __name__ == '__main__':
    print_input_file()

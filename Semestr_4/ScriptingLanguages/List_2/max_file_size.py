import sys


def find_max_file():
    max_file_size = -1
    max_file_path = ""

    for line in sys.stdin:
        line = line.split()
        current_path = line[6]
        current_amount = line[-1]
        if current_amount != '-' and max_file_size < int(current_amount) or max_file_size == -1:
            max_file_size = int(current_amount)
            max_file_path = current_path

    sys.stdout.write("Max file path: " + max_file_path + "\n")
    sys.stdout.write("Max file size: " + str(max_file_size))


if __name__ == '__main__':
    find_max_file()

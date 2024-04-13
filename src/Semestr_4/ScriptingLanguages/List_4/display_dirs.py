import os
import sys


def display_dirs():
    dirs = os.getenv('PATH').split(':')

    if len(sys.argv) == 1:
        [print(directory) for directory in dirs]
    elif '--list-files' in sys.argv[1:]:
        for directory in dirs:
            print(directory)
            [print(f" - {filename}") for filename in os.listdir(directory)]


if __name__ == "__main__":
    display_dirs()

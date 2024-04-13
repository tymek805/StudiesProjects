import os
import sys


def display_env_params():
    params = sys.argv[1:]

    if len(params) == 0:
        for key, value in sorted(os.environ.items()):
            print(f"{key}={value}")
    else:
        for key, value in sorted(os.environ.items()):
            [print(f"{key}={value}") for param in params if param in key]


if __name__ == "__main__":
    display_env_params()

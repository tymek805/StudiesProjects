import sys


def head():
    args = sys.argv[1:]
    bytes_to_read = 10
    show_filename = False

    for arg in sys.argv[1:]:
        if arg.startswith("--bytes="):
            bytes_to_read = int(arg.split('=')[-1])
            args.remove(arg)
        elif arg == "-v":
            show_filename = True
            args.remove(arg)

    if len(args) == 0:
        filename = "<stdin>"
        data = sys.stdin.read(bytes_to_read)
    else:
        filename = args[0]
        with open(filename) as f:
            data = f.read(bytes_to_read)

    if show_filename:
        print(filename)
    print(data)


if __name__ == "__main__":
    head()

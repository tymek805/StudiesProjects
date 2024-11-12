import sys


def calculate_sent_amount():
    amount_sent = 0

    for line in sys.stdin:
        current_amount = line.split()[-1]
        if current_amount != '-':
            amount_sent += int(current_amount)
    sys.stdout.write("File size [GB]: " + str(amount_sent / 1024 ** 3))


if __name__ == '__main__':
    calculate_sent_amount()

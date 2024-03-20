import sys


def calculate_pictures_ratio():
    downloaded_pictures = 0
    downloaded_others = 0

    for line in sys.stdin:
        line = line.split('"GET ')
        if len(line) > 1:
            line = line[1].split()[0].split('.')
            if len(line) > 1:
                line = line[1]
                if line in ['gif', 'jpg', 'jpeg', 'xbm']:
                    downloaded_pictures += 1
                else:
                    downloaded_others += 1

    sys.stdout.write("Downloaded pictures: " + str(downloaded_pictures) + "\n")
    sys.stdout.write("Downloaded others: " + str(downloaded_others) + "\n")
    sys.stdout.write("Ratio [ pictures / others ]: " + str(downloaded_pictures / downloaded_others))


if __name__ == '__main__':
    calculate_pictures_ratio()

import sys

from util.get_package import get_package


def calculate_pictures_ratio():
    downloaded_pictures = 0
    downloaded_others = 0

    for line in sys.stdin:
        package = get_package(line)
        if package != '':
            package_extension = package.split('.')[-1]
            if package_extension in ['gif', 'jpg', 'jpeg', 'xbm']:
                downloaded_pictures += 1
            else:
                downloaded_others += 1

    sys.stdout.write("Downloaded pictures: " + str(downloaded_pictures) + "\n")
    sys.stdout.write("Downloaded others: " + str(downloaded_others) + "\n")
    sys.stdout.write("Ratio [ pictures / others ]: " + str(downloaded_pictures / downloaded_others))


if __name__ == '__main__':
    calculate_pictures_ratio()

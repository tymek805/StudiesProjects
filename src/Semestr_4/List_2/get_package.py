def get_package(line: str) -> str:
    try:
        return line.split('"GET ')[1].split()[0]
    except IndexError as e:
        return ''

from read_l import read_l


def entry_to_dict(entry):
    return {
        "ip": entry[0],
        "datetime": entry[1],
        "http_protocol": entry[2],
        "http_response": entry[3],
        "resources": entry[4]
    }


if __name__ == "__main__":
    logs = read_l()
    for log in logs:
        print(entry_to_dict(log))

import json
from collections import Counter


def most_frequent_word(data):
    words = {}

    for word in data.split():
        word = ''.join(filter(str.isalnum, word.lower()))
        if word in words:
            words[word] += 1
        else:
            words[word] = 1

    return max(dict(sorted(words.items(), key=lambda item: item[1])))


def analyze_file():
    path = input()
    with open(path) as f:
        data = f.read()

    output = {
        'path': path,
        'character count': len(data),
        'words count': len(data.split()),
        'row count': len(data.split('\n')),
        'most frequent character': Counter(data).most_common(1)[0][0],
        'most frequent word': most_frequent_word(data)
    }

    with open("statistics.json", "w") as f:
        json.dump(output, f, indent=4)

    print(json.dumps(output, indent=4))


if __name__ == "__main__":
    analyze_file()

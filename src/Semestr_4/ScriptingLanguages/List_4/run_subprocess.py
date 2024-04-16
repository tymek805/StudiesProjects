import json
import os
import subprocess
import sys


def run():
    dir_path = sys.argv[1]

    results = []

    for filename in os.listdir(dir_path):
        if os.path.isfile(os.path.join(dir_path, filename)):
            print(filename)
            filepath = os.path.join(dir_path, filename)
            process = subprocess.Popen(["python3", "file_statistics.py"], stdout=subprocess.PIPE, stdin=subprocess.PIPE, text=True)
            results.append(json.loads(process.communicate(input=filepath)[0]))

    print({
        'read files': len(results),
        'characters sum': sum(result['character count'] for result in results),
        'words sum': sum(result['words count'] for result in results),
        'rows sum': sum(result['row count'] for result in results),
        'most frequent character': max(set(result["most frequent character"] for result in results), key=results.count),
        'most frequent word': max(set(result["most frequent word"] for result in results), key=results.count),
    })


if __name__ == "__main__":
    run()

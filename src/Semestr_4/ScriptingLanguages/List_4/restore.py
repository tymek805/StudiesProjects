import json
import os
import subprocess
import sys

from modify_env import get_backup_path, get_history_path


def run():
    if len(sys.argv) > 1:
        target_dir = sys.argv[1]
    else:
        target_dir = os.getcwd()

    backup_path = get_backup_path()
    if os.path.exists(get_history_path()):
        with open(get_history_path(), 'r') as f:
            history_data = json.load(f)

        print("\nDostępne kopie zapasowych:")
        for i, record in enumerate(history_data):
            date = record['date']
            source_path = record['source_path']
            backup_filename = record['backup_filename']
            print(f"{i + 1}. {date}: {source_path} -> {backup_filename}")

        selected_backup_index = int(input("\nPodaj numer kopii zapasowej do przywrócenia: ")) - 1

        while selected_backup_index < 0 or selected_backup_index >= len(history_data):
            print("Niepoprawny numer kopii zapasowej. Wprowadź ponownie.")
            selected_backup_index = int(input("\nPodaj numer kopii zapasowej do przywrócenia: ")) - 1

        backup_filepath = history_data[selected_backup_index]['source_path']
        subprocess.run(['unzip', backup_filepath, '-d', target_dir])
        os.remove(backup_filepath)


if __name__ == "__main__":
    run()

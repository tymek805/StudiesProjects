import json
import os
import subprocess
import sys
import datetime

from modify_env import get_backup_path, get_history_path


def run():
    dir_path = sys.argv[1]
    timestamp = datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')
    archive_name = f"{timestamp}-{dir_path.split(os.path.sep)[-1]}.zip"
    subprocess.run(["zip", "-r", archive_name, dir_path])

    backups_dir = get_backup_path()
    if not os.path.exists(backups_dir):
        os.mkdir(backups_dir)

    # Move archive file to desired location
    destination_path = os.path.join(backups_dir, archive_name)
    subprocess.run(['mv', os.path.join(os.getcwd(), archive_name), destination_path])

    record = {
        "date": timestamp,
        "source_path": destination_path,
        "backup_filename": archive_name
    }

    if os.path.exists(get_history_path()):
        with open(get_history_path(), 'r') as f:
            history_data = json.load(f)
        history_data.append(record)
        with open(get_history_path(), 'w') as f:
            json.dump(history_data, f, indent=4)
    else:
        with open(get_history_path(), 'w') as f:
            json.dump([record], f, indent=4)


if __name__ == "__main__":
    run()

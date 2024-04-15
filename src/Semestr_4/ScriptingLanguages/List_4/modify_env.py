import os


def get_backup_path():
    backups_path = os.environ.get('BACKUPS_DIR')
    if not backups_path:
        backups_path = os.path.join(os.path.expanduser('~'), '.backups')
    return backups_path


def get_history_path():
    return os.path.join(get_backup_path(), 'logs.json')

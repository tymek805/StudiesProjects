from datetime import datetime
import sqlite3
import sys
from csv import DictReader
from time import mktime
from typing import Text
from create_database import stations_table, rentals_table

def load_data(data_file: Text, db_file: Text):
    if not db_file.endswith('.sqlite'):
        db_file += '.sqlite'

    connection = sqlite3.connect(db_file)
    cursor = connection.cursor()

    with open(data_file, 'r') as f:
        stations = []
        data = []
        for item in DictReader(f):
            if isinstance(item, dict) and len(item) == 7:
                item = list(item.values())
                item[2:4] = map(convert_to_posix, item[2:4])
                item[4:6] = map(prepare_value_to_db, item[4:6])
                stations.extend(item[4:6])
                data.append(item)
        stations = list(dict.fromkeys(stations))

    cursor.executemany(f"INSERT OR IGNORE INTO {stations_table} (station_name) VALUES (?);", zip(stations))
    cursor.executemany(
        f"INSERT INTO {rentals_table} (rental_id, bike_id, start_time, end_time, rental_station, return_station, rental_time) VALUES (?, ?, ?, ?, (SELECT station_id FROM {stations_table} WHERE station_name = ?), (SELECT station_id FROM {stations_table} WHERE station_name = ?), ?)",
        data)
    cursor.close()

    if connection:
        connection.commit()
        connection.close()


def prepare_value_to_db(value: str) -> str:
    return value.replace(u"\u00A0", " ").strip()


def convert_to_posix(value: str) -> int:
    return int(mktime(datetime.strptime(value, '%Y-%m-%d %H:%M:%S').timetuple()))


def truncate_table(db_file, table_name):
    connection = sqlite3.connect(db_file)
    cursor = connection.cursor()
    cursor.execute(f"DELETE FROM {table_name};")
    cursor.close()
    connection.commit()
    connection.close()


if __name__ == "__main__":
    if len(sys.argv) == 3:
        load_data(*sys.argv[1:])
    else:
        truncate_table('rentals_db.sqlite', rentals_table)
        truncate_table('rentals_db.sqlite', stations_table)

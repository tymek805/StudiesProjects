import sqlite3
import sys
from typing import Text, Dict

stations_table = 'stations'
rentals_table = 'rentals'


def create_database(db_name: Text = 'rentals_db'):
    if not db_name.endswith('.sqlite'):
        db_name += '.sqlite'

    table_names: Dict[Text, Text] = {
        stations_table: "station_id INTEGER PRIMARY KEY AUTOINCREMENT, station_name TEXT",
        rentals_table: "rental_id INTEGER PRIMARY KEY, bike_id INTEGER, start_time DATE, end_time DATE, rental_station INTEGER, return_station INTEGER, rental_time INTEGER, "
                       "FOREIGN KEY (rental_station) REFERENCES stations(station_id), FOREIGN KEY (return_station) REFERENCES stations(station_id)",
    }

    connection = sqlite3.connect(db_name)
    for table_name, structure in table_names.items():
        connection.execute(f"CREATE TABLE IF NOT EXISTS {table_name} ({structure});")

    if connection:
        connection.close()


if __name__ == "__main__":
    if len(sys.argv) == 2:
        create_database(sys.argv[1])
    else:
        create_database()

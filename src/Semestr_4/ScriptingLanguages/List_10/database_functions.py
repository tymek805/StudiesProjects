import sqlite3
from typing import Text

from create_database import rentals_table, stations_table


class DatabaseManager:
    def __init__(self, db_file: Text):
        if not db_file.endswith('.sqlite'):
            db_file += '.sqlite'
        self.connection = sqlite3.connect(db_file)

    def __del__(self):
        if self.connection:
            self.connection.close()

    def get_stations(self) -> list:
        cursor = self.connection.cursor()
        cursor.execute(f"SELECT station_name FROM {stations_table}")
        result: list = cursor.fetchall()
        cursor.close()
        return [value[0] for value in result]

    def calculate(self, station_name: Text) -> (float, float, int, int):
        return self.average_duration_start(station_name), self.average_duration_end(
            station_name), self.distinct_bikes_parked(station_name), self.paid_rentals_number(station_name)

    def average_duration_start(self, station_name: Text) -> float:
        cursor = self.connection.cursor()
        cursor.execute(
            f"SELECT AVG(rental_time) FROM rentals JOIN stations ON rentals.rental_station = stations.station_id WHERE stations.station_name = ?;",
            (station_name,))
        result: float = cursor.fetchone()[0]
        cursor.close()
        return result

    def average_duration_end(self, station_name: Text) -> float:
        cursor = self.connection.cursor()
        cursor.execute(
            f"SELECT AVG(rental_time) FROM rentals JOIN stations ON rentals.return_station = stations.station_id WHERE stations.station_name = ?;",
            (station_name,))
        result: float = cursor.fetchone()[0]
        cursor.close()
        return result

    def distinct_bikes_parked(self, station_name: Text) -> int:
        cursor = self.connection.cursor()
        cursor.execute(
            f"SELECT COUNT(DISTINCT bike_id) FROM rentals JOIN stations ON rentals.return_station = stations.station_id WHERE stations.station_name = ?;",
            (station_name,))
        result: int = cursor.fetchone()[0]
        cursor.close()
        return result

    def paid_rentals_number(self, station_name: Text) -> int:
        cursor = self.connection.cursor()
        cursor.execute(
            f"SELECT COUNT(*) FROM rentals JOIN stations ON rentals.rental_station = stations.station_id WHERE stations.station_name = ? AND rental_time > 20;",
            (station_name,))
        result = cursor.fetchone()[0]
        cursor.close()
        return result

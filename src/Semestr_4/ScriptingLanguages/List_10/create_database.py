import sqlite3
import sys
from typing import Text, Dict

from logger import prepare_logger


class DatabaseManager:
    _db_name: Text = 'rentals_db.sqlite'
    _table_names: Dict[Text, Text] = {
        'stations': "station_id INTEGER PRIMARY KEY, station_name TEXT",
        'rentals': "rental_id INTEGER PRIMARY KEY, bike_id INTEGER, start_time DATE, end_time DATE, rental_station INTEGER, return_station INTEGER, rental_time INTEGER, "
                   "FOREIGN KEY (rental_station) REFERENCES stations(station_id), FOREIGN KEY (return_station) REFERENCES stations(station_id)",
    }

    def __init__(self):
        self.logger = prepare_logger('db', log_level='DEBUG')

        if len(sys.argv) == 2:
            self._db_name = sys.argv[1].strip().replace(' ', '_') + '.sqlite'

        try:
            self.connection = sqlite3.connect(self._db_name)
            self.logger.debug(f"connected to database {self._db_name}")
            for table_name, structure in self._table_names.items():
                self.connection.execute(f"CREATE TABLE IF NOT EXISTS {table_name} ({structure});")
        except sqlite3.Error as error:
            self.logger.critical("error occurred - " + str(error))
            self.close()

    def __del__(self):
        self.close()

    def close(self):
        if self.connection:
            self.connection.close()
            self.logger.debug(f"connection to the database {self._db_name} has been closed")

    # def get_last_status(self, item: TranslationItem) -> Optional[Text]:
    #     self.logger.debug(f"request for last status for {str(item)}")
    #     cursor = self.connection.cursor()
    #     cursor.execute(f"SELECT status FROM {self.table_name} WHERE title = ? AND volume = ? ORDER BY id DESC LIMIT 1;",
    #                    (item.title, item.volume))
    #     results = cursor.fetchall()
    #     cursor.close()
    #     return results[0][0] if results else None
    #
    # def add_new_status(self, item: TranslationItem):
    #     self.logger.debug(f"request to add {str(item)}")
    #     cursor = self.connection.cursor()
    #     cursor.execute(f"INSERT INTO {self.table_name} (title, volume, status) VALUES (?, ?, ?);",
    #                    (item.title, item.volume, item.status))
    #     cursor.close()
    #     self.connection.commit()
    #
    # def update_notification_status(self, item: TranslationItem):
    #     self.logger.debug(f"request to update notification status for {str(item)}")
    #     cursor = self.connection.cursor()
    #     cursor.execute(f"UPDATE {self.table_name} SET is_notified = TRUE "
    #                    f"WHERE title = ? AND volume = ? AND status = ?;", (item.title, item.volume, item.status))
    #     cursor.close()
    #     self.connection.commit()

    # def items_with_unsent_notification(self) -> List:
    #     self.logger.debug(f"request to find all missing notifications")
    #     cursor = self.connection.cursor()
    #     cursor.execute(f"SELECT title, volume, status FROM items WHERE is_notified = FALSE;")
    #     results = cursor.fetchall()
    #     cursor.close()
    #     return results


if __name__ == "__main__":
    db = DatabaseManager()

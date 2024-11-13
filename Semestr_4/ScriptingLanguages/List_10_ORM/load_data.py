from datetime import datetime
import sqlite3
import sys
from csv import DictReader
from time import mktime
from typing import Text

from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker

from create_database import Station, Rental


def load_data(data_file: Text, db_file: Text):
    if not db_file.endswith('.sqlite'):
        db_file += '.sqlite'

    with open(data_file, 'r') as f:
        stations = []
        rentals = []
        for item in DictReader(f):
            if isinstance(item, dict) and len(item) == 7:
                item = list(item.values())
                item[2:4] = map(convert_to_posix, item[2:4])
                item[4:6] = map(prepare_value_to_db, item[4:6])
                stations.extend(item[4:6])
                rentals.append(item)
        stations = list(dict.fromkeys(stations))

    engine = create_engine(f'sqlite:///{db_file}')
    session = sessionmaker(bind=engine)()
    for station in stations:
        session.add(Station(station_name=station))
    for rental in rentals:
        session.add(Rental(
            rental_id=rental[0],
            bike_id=rental[1],
            start_time=rental[2],
            end_time=rental[3],
            rental_station=session.query(Station.station_id).filter(Station.station_name == rental[4]).scalar(),
            return_station=session.query(Station.station_id).filter(Station.station_name == rental[5]).scalar(),
            rental_time=rental[6]
        ))
    session.commit()
    session.close()


def prepare_value_to_db(value: str) -> str:
    return value.replace(u"\u00A0", " ").strip()


def convert_to_posix(value: str) -> int:
    return int(mktime(datetime.strptime(value, '%Y-%m-%d %H:%M:%S').timetuple()))


if __name__ == "__main__":
    if len(sys.argv) == 3:
        load_data(*sys.argv[1:])

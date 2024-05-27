from typing import Text

from sqlalchemy import create_engine, func
from sqlalchemy.orm import sessionmaker

from create_database import Base, Station, Rental


class DatabaseManager:
    def __init__(self, db_file: Text):
        if not db_file.endswith('.sqlite'):
            db_file += '.sqlite'

        self.engine = create_engine(f'sqlite:///{db_file}')
        Base.metadata.create_all(self.engine)
        self.session = sessionmaker(bind=self.engine)()

    def get_stations(self) -> list:
        return [x[0] for x in self.session.query(Station.station_name).all()]

    def calculate(self, station_name: Text) -> list[float | int]:
        return [self.average_duration_start(station_name), self.average_duration_end(
            station_name), self.distinct_bikes_parked(station_name), self.paid_rentals_number(station_name)]

    def average_duration_start(self, station_name: Text) -> float:
        result = self.session.query(func.avg(Rental.rental_time)).join(Station, Rental.rental_station == Station.station_id).filter(Station.station_name == station_name).scalar()
        return result or 0

    def average_duration_end(self, station_name: Text) -> float:
        result = self.session.query(func.avg(Rental.rental_time)).join(Station, Rental.return_station == Station.station_id).filter(Station.station_name == station_name).scalar()
        return result or 0

    def distinct_bikes_parked(self, station_name: Text) -> int:
        return self.session.query(Rental.bike_id).filter(Rental.return_station_rel.has(station_name=station_name)).distinct().count()

    def paid_rentals_number(self, station_name: Text) -> int:
        return self.session.query(Rental.rental_id).filter(Rental.rental_station_rel.has(station_name=station_name), Rental.rental_time > 20).count()

import sys
from typing import Text

from sqlalchemy import Column, Integer, String, ForeignKey, create_engine
from sqlalchemy.orm import relationship, declarative_base

Base = declarative_base()


class Station(Base):
    __tablename__ = 'stations'
    station_id = Column(Integer, primary_key=True, autoincrement=True)
    station_name = Column(String)


class Rental(Base):
    __tablename__ = 'rentals'
    rental_id = Column(Integer, primary_key=True)
    bike_id = Column(Integer)
    start_time = Column(String)
    end_time = Column(String)
    rental_station = Column(Integer, ForeignKey('stations.station_id'))
    return_station = Column(Integer, ForeignKey('stations.station_id'))
    rental_time = Column(Integer)
    rental_station_rel = relationship("Station", foreign_keys=[rental_station])
    return_station_rel = relationship("Station", foreign_keys=[return_station])


def create_database(db_name: Text = 'rentals_db'):
    if not db_name.endswith('.sqlite'):
        db_name += '.sqlite'

    engine = create_engine(f"sqlite:///{db_name}")
    Base.metadata.create_all(engine)


if __name__ == "__main__":
    if len(sys.argv) == 2:
        create_database(sys.argv[1])
    else:
        create_database()

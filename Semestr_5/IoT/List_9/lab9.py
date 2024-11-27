#!/usr/bin/env python3

from config import *
import w1thermsensor
import board
import busio
import adafruit_bme280.advanced as adafruit_bme280

def calculate_altitude(pressure):
    return 44330 * (1 - (pressure / 1022) ** (1 / 5.255))

def ds18b20():
    sensor = w1thermsensor.W1ThermSensor()
    temp = sensor.get_temperature()
    print(f"{'DS18B200 Temp':<15}{temp} "+chr(176)+'C')

def bme280():
    i2c = busio.I2C(board.SCL, board.SDA)
    bme280 = adafruit_bme280.Adafruit_BME280_I2C(i2c, 0x76)

    bme280.standby_period = adafruit_bme280.STANDBY_TC_500
    bme280.iir_filter = adafruit_bme280.IIR_FILTER_X16
    bme280.overscan_pressure = adafruit_bme280.OVERSCAN_X16
    bme280.overscan_humidity = adafruit_bme280.OVERSCAN_X1
    bme280.overscan_temperature = adafruit_bme280.OVERSCAN_X2

    print(f"{'BME280   Temp':<15}{bme280.temperature:0.1f} "+chr(176)+'C')
    print(f'{"Humidity":<15}{bme280.humidity:0.1f} %')
    print(f'{"Pressure":<15}{bme280.pressure:0.1f} hPa')
    print(f'{"Altitude":<15}{calculate_altitude(bme280.pressure):0.1f} n.p.m.')

def test():
    ds18b20()
    bme280()
    GPIO.cleanup()


if __name__ == "__main__":
    test()

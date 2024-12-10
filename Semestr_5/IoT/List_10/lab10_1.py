#!/usr/bin/env python3

from config import *
import w1thermsensor
import board
import busio
import adafruit_bme280.advanced as adafruit_bme280
from PIL import Image, ImageDraw, ImageFont
import lib.oled.SSD1331 as SSD1331


def calculate_altitude(pressure):
    return 44330 * (1 - (pressure / 1022) ** (1 / 5.255))

def oled():
    disp = SSD1331.SSD1331()
    disp.Init()
    disp.clear()

    i2c = busio.I2C(board.SCL, board.SDA)
    bme280 = adafruit_bme280.Adafruit_BME280_I2C(i2c, 0x76)

    bme280.standby_period = adafruit_bme280.STANDBY_TC_500
    bme280.iir_filter = adafruit_bme280.IIR_FILTER_X16
    bme280.overscan_pressure = adafruit_bme280.OVERSCAN_X16
    bme280.overscan_humidity = adafruit_bme280.OVERSCAN_X1
    bme280.overscan_temperature = adafruit_bme280.OVERSCAN_X2

    fontSmall = ImageFont.truetype('./kod10/lib/oled/Font.ttf', 13)

    image = Image.new("RGB", (disp.width, disp.height), "WHITE")
    draw = ImageDraw.Draw(image)

    draw.text((0, 0), f'Temp {bme280.temperature:0.1f}', font=fontSmall, fill="BLUE")
    draw.text((disp.width - 20, 0), '■', font=fontSmall, fill="GREEN" if bme280.temperature > 30 else "RED")
    draw.text((0, 14), f'Humidity {bme280.humidity:0.1f}', font=fontSmall, fill="BLUE")
    draw.text((0, 28), f'Pressure {bme280.pressure:0.1f}', font=fontSmall, fill="BLUE")
    draw.text((0, 42), f'Altitude {calculate_altitude(bme280.pressure):0.1f}', font=fontSmall, fill="BLUE")

    disp.ShowImage(image, 0, 0)

def main():
    oled()
    GPIO.cleanup()


if __name__ == "__main__":
    main()

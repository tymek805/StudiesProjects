#!/usr/bin/env python3

from config import *  # pylint: disable=unused-wildcard-import
import RPi.GPIO as GPIO
import time
from mfrc522 import MFRC522
from datetime import datetime

def buzzer(state):
    GPIO.output(buzzerPin, not state)  # pylint: disable=no-member

def play():
    buzzer(True)
    time.sleep(1)
    buzzer(False)

def blink():
    GPIO.output(led1, GPIO.HIGH)
    time.sleep(1)
    GPIO.output(led1, GPIO.LOW)
    time.sleep(1)

def rfidRead():
    MIFAREReader = MFRC522()
    while True:
        (status, TagType) = MIFAREReader.MFRC522_Request(MIFAREReader.PICC_REQIDL)
        if status == MIFAREReader.MI_OK:
            (status, uid) = MIFAREReader.MFRC522_Anticoll()
            if status == MIFAREReader.MI_OK:
                num = 0
                for i in range(0, len(uid)):
                    num += uid[i] << (i*8)
                print(f"{datetime.now()} : Card read UID: {uid} > {num}")
                blink()
                play()
                time.sleep(0.5)
                break

def test():
    print('Place the card close to the reader (on the right side of the set).')
    rfidRead()
    print("The RFID card registered successfully")

if __name__ == "__main__":
    test()
    GPIO.cleanup()  # pylint: disable=no-member

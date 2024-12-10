#!/usr/bin/env python3

from config import *  # pylint: disable=unused-wildcard-import
import RPi.GPIO as GPIO
import time
from mfrc522 import MFRC522
from datetime import datetime
import paho.mqtt.client as mqtt

terminal_id = "T0"
broker = "localhost"
client = mqtt.Client()

def buzzer(state):
    GPIO.output(buzzerPin, not state)  # pylint: disable=no-member

def play():
    buzzer(True)
    time.sleep(1)
    buzzer(False)

def rfidRead():
    last_card_UID = None
    MIFAREReader = MFRC522()

    while True:
        (status, TagType) = MIFAREReader.MFRC522_Request(MIFAREReader.PICC_REQIDL)
        if status == MIFAREReader.MI_OK:
            (status, uid) = MIFAREReader.MFRC522_Anticoll()

            if status == MIFAREReader.MI_OK:
                num = 0
                for i in range(0, len(uid)):
                    num += uid[i] << (i*8)
                
                if (last_card_UID != num):
                    print(f"{datetime.now()} : Card read UID: {num}")
                    last_card_UID = num
                    play()
                    print("The RFID card registered successfully")  
                    call_worker(num, datetime.now())
                    time.sleep(0.5)

def call_worker(worker_id, dt):
   client.publish("worker/card", str(dt) + " - " + str(worker_id))

def connect_to_broker():
   client.connect(broker)
   call_worker("Client connected", datetime.now())

def disconnect_from_broker():
   call_worker("Client disconnected", datetime.now())
   client.disconnect()


def test():
    print('Place the card close to the reader (on the right side of the set).')
    connect_to_broker()
    rfidRead()
    disconnect_from_broker()


if __name__ == "__main__":
    test()
    GPIO.cleanup()  # pylint: disable=no-member

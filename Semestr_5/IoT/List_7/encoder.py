#!/usr/bin/env python3

from config import *
import RPi.GPIO as GPIO
import time

step = 10
brightness = 0
execute = True
diodes = [GPIO.PWM(led1, 50), GPIO.PWM(led2, 50), GPIO.PWM(led3, 50), GPIO.PWM(led4, 50)]
currentDiode = 0

encoderLeftPrevoiusState = GPIO.input(encoderLeft)
encoderRightPrevoiusState = GPIO.input(encoderRight)

def turn_encoder(channel):
    global encoderLeftPrevoiusState
    global encoderRightPrevoiusState
    global brightness
    encoderLeftCurrentState = GPIO.input(encoderLeft)
    encoderRightCurrentState = GPIO.input(encoderRight)

    if (encoderLeftPrevoiusState == 1 and encoderLeftCurrentState == 0 and brightness < 100):
            brightness += step
            diodes[currentDiode].ChangeDutyCycle(brightness)
            print(brightness)
    if (encoderRightPrevoiusState == 1 and encoderRightCurrentState == 0 and brightness > 0):
            brightness -= step
            diodes[currentDiode].ChangeDutyCycle(brightness)
            print(brightness)
    
    encoderLeftPrevoiusState = encoderLeftCurrentState
    encoderRightPrevoiusState = encoderRightCurrentState

def buttonPressedCallback(channel):
    global currentDiode
    global brightness

    brightness = 0
    diodes[currentDiode].ChangeDutyCycle(brightness)
    diodes[currentDiode].stop()

    if (channel == 5): # Red
        currentDiode -= 1
    elif (channel == 6): # Green
        currentDiode += 1

    if (currentDiode > 3):
        currentDiode = 0
    elif (currentDiode < 0):
        currentDiode = 3

    diodes[currentDiode].start(0)

def main():
    dutyCycle = 0
    diodes[currentDiode].start(dutyCycle)
    
    GPIO.add_event_detect(encoderLeft, GPIO.FALLING, callback=turn_encoder, bouncetime=20)
    GPIO.add_event_detect(encoderRight, GPIO.FALLING, callback=turn_encoder, bouncetime=20)

    GPIO.add_event_detect(buttonRed, GPIO.FALLING, callback=buttonPressedCallback, bouncetime=200)
    GPIO.add_event_detect(buttonGreen, GPIO.FALLING, callback=buttonPressedCallback, bouncetime=200)

    while True:
        pass

if __name__ == "__main__":
    main()
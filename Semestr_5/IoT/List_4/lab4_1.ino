#include <Wire.h>
#include <LiquidCrystal_I2C.h>
#include <OneWire.h>
#include <DallasTemperature.h>

LiquidCrystal_I2C lcd(0x27,16,2);

OneWire oneWire(A1);
DallasTemperature sensors(&oneWire);

#define LED_RED 6
#define LED_GREEN 5
#define LED_BLUE 3

#define RED_BUTTON 2
#define GREEN_BUTTON 4

#define ENCODER1 A2
#define ENCODER2 A3

#define POTENTIOMETER A0

void initRGB() {
  pinMode(LED_RED, OUTPUT);
  pinMode(LED_GREEN, OUTPUT);
  pinMode(LED_BLUE, OUTPUT);
}

void initLCD()
{
  lcd.init();
  lcd.clear();
  lcd.backlight();
}

boolean isValidNumber(String str){
  for(byte i=0; i<str.length(); i++) {
    if(!isDigit(str.charAt(i))) return false;
  }
  return true;
}

void processCommand(String command) {
  if (command.length() < 1) return;
  int ledToUpdate = command.startsWith("R") ? LED_RED : (command.startsWith("G") ? LED_GREEN : (command.startsWith("B") ? LED_BLUE : -1));

  if (command.startsWith("OFF")) {
    analogWrite(LED_RED, 0);
    analogWrite(LED_GREEN, 0);
    analogWrite(LED_BLUE, 0);
  } else if (ledToUpdate != -1) {
    String value = command.substring(1);
    if (isValidNumber(value)) {
      int intValue = value.toInt();
      if (intValue >= 0 && intValue <= 255) {
        Serial.print("Changing brightness for ");
        Serial.print(ledToUpdate == LED_RED ? "RED" : (ledToUpdate == LED_GREEN ? "GREEN" : "BLUE"));
        Serial.print(" to: ");
        Serial.println(intValue);
        analogWrite(ledToUpdate, intValue);
      } else {
        Serial.println("Value has to be in range (0, 255)");
      }
    } else {
      Serial.println("Invalid number");
    }
  } else {
    Serial.println("Unrecognized command!");
  }
}

void setup() {
  pinMode(LED_BUILTIN, OUTPUT);
  Serial.begin(9600);
  Serial.println("Use commends: R<value>, G<value>, B<value> (0-255) to control LEDs and OFF to turn LED off");
  initRGB();
}

void loop() {
    if (Serial.available() > 0) {
        String command = Serial.readStringUntil('\n');
        processCommand(command);
    }
}

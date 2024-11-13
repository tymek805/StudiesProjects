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

bool greenButtonPressed = false;
bool redButtonPressed = false;
int currentMenu = 0;

void initLCD() {
  lcd.init();
  lcd.clear();
  lcd.backlight();
}

void initRGB() {
  pinMode(LED_RED, OUTPUT);
  pinMode(LED_GREEN, OUTPUT);
  pinMode(LED_BLUE, OUTPUT);
}

void initButtons()
{
  pinMode(RED_BUTTON, INPUT_PULLUP);
  pinMode(GREEN_BUTTON, INPUT_PULLUP);
}

void initSensors() {
  sensors.begin();
}


float outMin;
float outMax = 0.00;
float inMin;
float inMax = 0.00;

float inTemp = 0.00;
float outTemp = 0.00;

boolean done = false;

void setup() {
  pinMode(LED_BUILTIN, OUTPUT);
  Serial.begin(9600);

  initLCD();
  initRGB();
  initButtons();
  initSensors();

  sensors.requestTemperatures();
  inMin = sensors.getTempCByIndex(1);
  outMin = sensors.getTempCByIndex(0);
}

void printReadings() {
  if (!done) {
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("Cur  IN: ");
    lcd.setCursor(0, 1);
    lcd.print("Cur OUT: ");
    done = true;
  }
  lcd.setCursor(9, 0);
  lcd.print(inTemp);
  lcd.print("C  ");
  lcd.setCursor(9, 1);
  lcd.print(outTemp);
  lcd.print("C  ");
}

void printMaxReading() {
  if (!done) {
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print(" IN Max: ");
    lcd.print(inMax);
    lcd.setCursor(0, 1);
    lcd.print("OUT Max: ");
    lcd.print(outMax);
    done = true;
  }
  lcd.setCursor(9, 0);
  lcd.print(inMax);
  lcd.setCursor(9, 1);
  lcd.print(outMax);
}

void printMinReading() {
  if (!done) {
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print(" IN Min: ");
    lcd.print(inMin);
    lcd.setCursor(0, 1);
    lcd.print("OUT Min: ");
    lcd.print(outMin);
    done = true;
  }
  lcd.setCursor(9, 0);
  lcd.print(inMin);
  lcd.setCursor(9, 1);
  lcd.print(outMin);
}

void getReadings() {
  sensors.requestTemperatures();
  inTemp = sensors.getTempCByIndex(1);
  outTemp = sensors.getTempCByIndex(0);
  
  inMax = max(inMax, inTemp);
  inMin = min(inMin, inTemp);
  
  outMax = max(outMax, outTemp);
  outMin = min(outMin, outTemp);
}

void detectGreenButton() {
  if (digitalRead(GREEN_BUTTON) == LOW) {
    delay(50);
    if (!greenButtonPressed) {
      greenButtonPressed = true;
      currentMenu++;
      if (currentMenu > 2) {
        currentMenu = 0;
      }
      done = false;
    }
  } else {
    greenButtonPressed = false;
  }
}

void detectRedButton() {
  if (digitalRead(RED_BUTTON) == LOW) {
    delay(50);
    if (!redButtonPressed) {
      redButtonPressed = true;
      currentMenu--;
      if (currentMenu < 0) {
        currentMenu = 2;
      }
      done = false;
    }
  } else {
    redButtonPressed = false;
  }
}

void loop() {
  detectGreenButton();
  detectRedButton();
  if (currentMenu == 0) {
    getReadings();
    printReadings();
  } else if (currentMenu == 1) {
    printMaxReading();
  } else if (currentMenu == 2) {
    printMinReading();
  }
  delay(50);
}

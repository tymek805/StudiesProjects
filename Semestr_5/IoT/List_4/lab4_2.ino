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

void testPotencjometer()
{
  lcd.print("Potenciometer: ");
  lcd.setCursor(0, 1);
  lcd.print("Turn full range");

  int refval = analogRead(POTENTIOMETER);
  int val = refval;
  while (refval - 10 < val & val < refval + 10)
  {
    val = analogRead(POTENTIOMETER);
  }

  int min = val;
  int max = val;

  lcd.clear();
  lcd.print("Potentiometer");
  while (min > 100 || max < 1023 - 100)
  {
    val = analogRead(POTENTIOMETER); // read the input pin
    //Serial.println(val);
    lcd.setCursor(5, 1);
    lcd.print("ADC =     ");
    lcd.setCursor(11, 1);
    lcd.print(val);

    if (val < min)
    {
      min = val;
    }

    if (val > max)
    {
      max = val;
    }

    delay(20);
  }
  digitalWrite(LED_BLUE, HIGH);
  delay(1000);
  digitalWrite(LED_BLUE, LOW);
  lcd.clear();
}

void setup() {
  pinMode(LED_BUILTIN, OUTPUT);
  Serial.begin(9600);

  initLCD();
  initRGB();
  
  initPotentiometer();
}

int lastValue;

void initPotentiometer() {
  lcd.clear();
  lcd.print("Miernik A0");
  lcd.setCursor(0, 1);
  lcd.print("V=");
  lastValue = -1;
  displayPotentiometer();
}

void displayPotentiometer() {
  lcd.setCursor(2, 1);
  int value = analogRead(POTENTIOMETER);
  double voltage = 5.0 / 1024.0 * value;
  lcd.print(voltage);
  lcd.print("  ADC=");
  
  if (value < 1000) {
    lcd.print(" ");
    if (value < 100) {
      lcd.print(" ");
    } 
    if (value < 10) {
      lcd.print(" ");
    }
  }
  Serial.print(voltage);
  Serial.print(",");
  Serial.println(value);
  lastValue = value;
  lcd.print(analogRead(POTENTIOMETER));
}

void loop() {
  if (lastValue == -1 || abs(lastValue - analogRead(POTENTIOMETER)) > 2) {
    displayPotentiometer();
  }
}

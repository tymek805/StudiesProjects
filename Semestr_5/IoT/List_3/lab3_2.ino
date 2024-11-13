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

void initLCD()
{
  lcd.init();
  lcd.clear();
  lcd.backlight();
}

void initRGB()
{
  pinMode(LED_RED, OUTPUT);
  pinMode(LED_GREEN, OUTPUT);
  pinMode(LED_BLUE, OUTPUT);
}

void initButtons() {
  pinMode(RED_BUTTON, INPUT_PULLUP);
  pinMode(GREEN_BUTTON, INPUT_PULLUP);
}

void setup()
{
  pinMode(LED_BUILTIN, OUTPUT);
  Serial.begin(9600);

  initLCD();
  initRGB();
  initButtons();
    
  printTimes();
  digitalWrite(LED_RED, HIGH);
}

unsigned long redTime = 900UL;
unsigned long greenTime = 1000UL;
unsigned long blueTime = 1100UL;

unsigned long redLastChange = 0UL;
unsigned long greenLastChange = 0UL;
unsigned long blueLastChange = 0UL;

int counterValue = 0;
int ledToChange = LED_RED;

void printTimes() {
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("R-");
  lcd.print(redTime);
  lcd.print("   G-");
  lcd.print(greenTime);
  lcd.setCursor(0, 1);
  lcd.print("B-");
  lcd.print(blueTime);
}

void ledBlink() {
  if (millis() - redLastChange >= redTime) {
    if (digitalRead(LED_RED) == HIGH) {
      digitalWrite(LED_RED, LOW);
    } else {
       digitalWrite(LED_RED, HIGH);
    }
    redLastChange += redTime;
  }
  if (millis() - greenLastChange >= greenTime) {
    if (digitalRead(LED_GREEN) == HIGH) {
      digitalWrite(LED_GREEN, LOW);
    } else {
       digitalWrite(LED_GREEN, HIGH);
    }
    greenLastChange += greenTime;
  }
  if (millis() - blueLastChange >= blueTime) {
    if (digitalRead(LED_BLUE) == HIGH) {
      digitalWrite(LED_BLUE, LOW);
    } else {
       digitalWrite(LED_BLUE, HIGH);
    }
    blueLastChange += blueTime;
  }
}


void changeTime() {
  if (ledToChange == LED_RED) {
    redTime = (redTime == 2000UL) ? 500UL : (redTime + 100UL);
  } else if (ledToChange == LED_GREEN) {
    greenTime = (greenTime == 2000UL) ? 500UL : (greenTime + 100UL);
  } else if (ledToChange == LED_BLUE) {
    blueTime = (blueTime == 2000UL) ? 500UL : (blueTime + 100UL);
  }
}

#define DEBOUNCE_PERIOD 30UL
static int green_debounced_button_state = HIGH;
static int green_previous_reading = HIGH;
static unsigned long green_last_change_time = 0UL;

static int red_debounced_button_state = HIGH;
static int red_previous_reading = HIGH;
static unsigned long red_last_change_time = 0UL;

bool isGreenButtonPressed() {
  bool isGreenPressed = false;

  int current_reading = digitalRead(GREEN_BUTTON);
  if (green_previous_reading != current_reading) {
    green_last_change_time = millis();
  }

  if (millis() - green_last_change_time > DEBOUNCE_PERIOD) {
    if (current_reading != green_debounced_button_state) {
      if (green_debounced_button_state == HIGH && current_reading == LOW) {
        isGreenPressed = true;
      }
      green_debounced_button_state = current_reading;
    }
  }

  green_previous_reading = current_reading;
  return isGreenPressed;
}

bool isRedButtonPressed() {
  bool isRedPressed = false;

  int current_reading = digitalRead(RED_BUTTON);
  if (red_previous_reading != current_reading) {
    red_last_change_time = millis();
  }

  if (millis() - red_last_change_time > DEBOUNCE_PERIOD) {
    if (current_reading != red_debounced_button_state) {
      if (red_debounced_button_state == HIGH && current_reading == LOW) {
        isRedPressed = true;
      }
      red_debounced_button_state = current_reading;
    }
  }

  red_previous_reading = current_reading;
  return isRedPressed;
}

void detectButtons() {
  if (isRedButtonPressed()) {
    if (ledToChange == LED_RED) {
      ledToChange = LED_GREEN;
    } else if (ledToChange == LED_GREEN) {
      ledToChange = LED_BLUE;
    } else if (ledToChange == LED_BLUE) {
      ledToChange = LED_RED;
    }
  }
  if (isGreenButtonPressed()) {
    changeTime();
    printTimes();
  }
}

void loop()
{
  detectButtons();
  ledBlink();
}

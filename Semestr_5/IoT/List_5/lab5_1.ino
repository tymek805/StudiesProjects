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

  digitalWrite(LED_RED, HIGH);
  digitalWrite(LED_GREEN, HIGH);
  digitalWrite(LED_BLUE, HIGH);
}

void initButtons() {
  pinMode(RED_BUTTON, INPUT_PULLUP);
  pinMode(GREEN_BUTTON, INPUT_PULLUP);
}

void initEncoder() {
  pinMode(ENCODER1, INPUT_PULLUP);
  pinMode(ENCODER2, INPUT_PULLUP);
}

struct MenuOption {
  String name;
  MenuOption* options;
  void (*action)();
};

// Variables
bool greenButtonPressed = false;
bool redButtonPressed = false;
bool backlight = true;
bool isLedOn = true;
bool isCelcius = true;
bool charSelection = false;

char selector = '<';

bool isScreenNeeded = false;
int ledPinGlobal = 0;
int sensorInValue = 20;
int sensorOutValue = 14;
int ledColor[] = {255, 255, 255};

void functionLedPower() {
  if (isLedOn) {
    digitalWrite(LED_RED, LOW);
    digitalWrite(LED_GREEN, LOW);
    digitalWrite(LED_BLUE, LOW);
  } else {
    analogWrite(LED_RED, ledColor[0]);
    analogWrite(LED_GREEN, ledColor[1]);
    analogWrite(LED_BLUE, ledColor[2]);
  }
  isLedOn = !isLedOn;
}

void readPotentiometer(int ledPin) {
  lcd.setCursor(7, 1);
  int value = floor(analogRead(POTENTIOMETER) / 4);
  if (value < 100) {
    lcd.print(" ");
  } 
  if (value < 10) {
    lcd.print(" ");
  }
  lcd.print(value);
  ledColor[ledPin == LED_RED ? 0 : (ledPin == LED_GREEN ? 1 : 2)] = value;
  analogWrite(ledPin, value);
}

void functionLedIntensity(String ledName, int ledPin) {
  isScreenNeeded = true;
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print(ledName);
  lcd.print(" brightness");
  lcd.setCursor(0, 1);
  lcd.print("       ");
  ledPinGlobal = ledPin; 
  readPotentiometer(ledPinGlobal);
}

void functionLedIntensityRed() {
  functionLedIntensity("RED", LED_RED);
}
void functionLedIntensityGreen() {
  functionLedIntensity("GREEN", LED_GREEN);
}
void functionLedIntensityBlue() {
  functionLedIntensity("BLUE", LED_BLUE);
}

void changeBacklight() {
  if (backlight) {
    lcd.noBacklight();
  } else {
    lcd.backlight();
  }
  backlight = !backlight;
}

void changeSelector() {
  isScreenNeeded = true;
  charSelection = true;
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("Current selector:");
  lcd.setCursor(0, 1);
  Serial.println(int(selector));
  lcd.print(selector);
}

void displaySensorIn() {
  isScreenNeeded = true;
  int value = sensorInValue;
  if (!isCelcius) {
    value = floor(value * 9 / 5 + 32);
  }
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("IN temperature");
  lcd.setCursor(0, 1);
  lcd.print(value);
  lcd.print(isCelcius ? " C" : " F");
}

void displaySensorOut() {
  isScreenNeeded = true;
  int value = sensorOutValue;
  if (!isCelcius) {
    value = floor(value * 9 / 5 + 32);
  }
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("OUT temperature");
  lcd.setCursor(0, 1);
  lcd.print(value);
  lcd.print(isCelcius ? " C" : " F");
}

void changeTempUnit() {
  isCelcius = !isCelcius;
}

void displayAuthor() {
  isScreenNeeded = true;
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("Tymoteusz");
  lcd.setCursor(0, 1);
  lcd.print("Lango");
}

MenuOption ledMenu[] = {
  {"Power [ON/OFF]", nullptr, functionLedPower},
  {"Red", nullptr, functionLedIntensityRed},
  {"Green", nullptr, functionLedIntensityGreen},
  {"Blue", nullptr, functionLedIntensityBlue}
};

MenuOption displayMenu[] = {
  {"Backlight 0/1", nullptr, changeBacklight},
  {"Selector", nullptr, changeSelector}
};

MenuOption temperatureMenu[] = {
  {"Sensor IN", nullptr, displaySensorIn},
  {"Sensor OUT", nullptr, displaySensorOut},
  {"Units C/F", nullptr, changeTempUnit}
};

MenuOption mainMenu[4] = {
  {"LED options", ledMenu, nullptr},
  {"Display", displayMenu, nullptr},
  {"Temperature", temperatureMenu, nullptr},
  {"About", nullptr, displayAuthor}
};

MenuOption* currentMenu = mainMenu;
int selectedOption = 0;
int selectedOptionIdx = -1;
int optionsLength[] = {4, 2, 3, 4};

int length() {
  if (selectedOptionIdx == -1) 
    return 4;
  return optionsLength[selectedOptionIdx];
}

void printMenu() {
  Serial.println(length());
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print(currentMenu[selectedOption].name);
  lcd.print(" ");
  lcd.print(selector);
  lcd.setCursor(0, 1);
  lcd.print(currentMenu[(selectedOption + 1 < length()) ? (selectedOption + 1) : 0].name);
}

void setup() {
  pinMode(LED_BUILTIN, OUTPUT);
  Serial.begin(9600);

  initLCD();
  initRGB();
  initButtons();
  initEncoder();

  printMenu();
}

int state1 = digitalRead(ENCODER1);
int state2 = digitalRead(ENCODER2);
int counter1 = 0, counter2 = 0;
int e1, e2;

void incrementSelectedMenu() {
  selectedOption++;
  if (selectedOption > length() - 1) {
    selectedOption = 0;
  }
  printMenu();
}

void decrementSelectedMenu() {
  selectedOption--;
  if (selectedOption < 0) {
    selectedOption = length() - 1;
  }
  printMenu();
}

void moveUpChar() {
  int x = int(selector + 1);
  Serial.println(x);
  if (x > 126) {
    x = 33;
  }
  selector = char(x);
  lcd.setCursor(0, 1);
  lcd.print(selector);
}

void moveDownChar() {
  int x = int(selector - 1);
  Serial.println(x);
  if (x < 33) {
    x = 126;
  }
  selector = char(x);
  lcd.setCursor(0, 1);
  lcd.print(selector);
}

void manageEncoderNav() {
  e1 = digitalRead(ENCODER1);
  e2 = digitalRead(ENCODER2);

  if (state1 != e1) {
    if (state1 == LOW & e1 == HIGH) {
      counter1++;
      if (counter1 > counter2) {
        if (charSelection) {
          moveUpChar();
        } else {
          incrementSelectedMenu();
        }
      }
    }
    state1 = e1;
  }

  if (state2 != e2) {
    if (state2 == LOW & e2 == HIGH) {
      counter2++; 
      if (counter2 > counter1) {
        if (charSelection) {
          moveDownChar();
        } else {
          decrementSelectedMenu();
        }
      }
    }
    state2 = e2;
  }
}

void detectActionButton() {
  if (digitalRead(GREEN_BUTTON) == LOW) {
    delay(50);
    if (!greenButtonPressed) {
      greenButtonPressed = true;

      if (currentMenu[selectedOption].action != nullptr) {
        currentMenu[selectedOption].action();
      } else {
        currentMenu = currentMenu[selectedOption].options;
        selectedOptionIdx = selectedOption;
        selectedOption = 0;
      }

      if (!isScreenNeeded) {
        printMenu();
      }
    }
  } else {
    greenButtonPressed = false;
  }
}

void detectBackButton() {
  if (digitalRead(RED_BUTTON) == LOW) {
    delay(50);
    if (!redButtonPressed) {
      redButtonPressed = true;

      if (isScreenNeeded) {
        isScreenNeeded = false;
        charSelection = false;
        ledPinGlobal = 0;
        printMenu();
      } else if (selectedOptionIdx != -1) {
        currentMenu = mainMenu;
        selectedOptionIdx = -1;
        selectedOption = 0;
        printMenu();
      }
    }
  } else {
    redButtonPressed = false;
  }
}

void loop() {
  manageEncoderNav();
  detectActionButton();
  detectBackButton();
  if (ledPinGlobal != 0) {
    readPotentiometer(ledPinGlobal);
  }
}
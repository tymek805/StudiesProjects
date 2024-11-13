#define LED_RED 6
#define LED_GREEN 5
#define LED_BLUE 3

#define RED_BUTTON 2
#define GREEN_BUTTON 4

void initRGB() {
  pinMode(LED_BUILTIN, OUTPUT);
  digitalWrite(LED_BUILTIN, LOW);

  pinMode(LED_RED, OUTPUT);
  digitalWrite(LED_RED, HIGH);

  pinMode(LED_GREEN, OUTPUT);
  digitalWrite(LED_GREEN, LOW);

  pinMode(LED_BLUE, OUTPUT);
  digitalWrite(LED_BLUE, LOW);
}

void initButtons() {
  pinMode(RED_BUTTON, INPUT_PULLUP);
  pinMode(GREEN_BUTTON, INPUT_PULLUP);
}

void setup() {
  // put your setup code here, to run once:
  initRGB();
  initButtons();
}

int redValue;
int greenValue;
int blueValue;

void loop() {
  #define delayTime 10

  for (int i = 0; i < 255; i++) {
    redValue--;
    greenValue++;
    analogWrite(LED_RED, redValue);
    analogWrite(LED_GREEN, greenValue);
    delay(delayTime);
  }

  for (int i = 0; i < 255; i++) {
    greenValue--;
    blueValue++;

    analogWrite(LED_GREEN, greenValue);
    analogWrite(LED_BLUE, blueValue);
    delay(delayTime);
  }

  for (int i = 0; i < 255; i++) {
    blueValue--;
    redValue++;

    analogWrite(LED_BLUE, blueValue);
    analogWrite(LED_RED, redValue);
    delay(delayTime);
  }
}

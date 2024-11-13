#define LED_RED 6
#define LED_GREEN 5
#define LED_BLUE 3

#define RED_BUTTON 2
#define GREEN_BUTTON 4

void initRGB() {
  pinMode(LED_BUILTIN, OUTPUT);
  digitalWrite(LED_BUILTIN, LOW);

  pinMode(LED_RED, OUTPUT);
  digitalWrite(LED_RED, LOW);

  pinMode(LED_GREEN, OUTPUT);
  digitalWrite(LED_GREEN, LOW);

  pinMode(LED_BLUE, OUTPUT);
  digitalWrite(LED_BLUE, LOW);
}

void initButtons() {
  pinMode(RED_BUTTON, INPUT_PULLUP);
  pinMode(GREEN_BUTTON, INPUT_PULLUP);
  changeStatusLED(1);
}

bool status;
int currentLED;


void setup() {
  // put your setup code here, to run once:
  initRGB();
  initButtons();

  status = false;
  currentLED = LED_RED;
}


void loop() {
  // put your main code here, to run repeatedly:
  if (digitalRead(RED_BUTTON) == LOW) {
    while(digitalRead(RED_BUTTON) == LOW){delay(10);};
    status = !status;
    digitalWrite(currentLED, status);
    delay(50);
  }
  if (digitalRead(GREEN_BUTTON) == LOW) {
    while(digitalRead(GREEN_BUTTON) == LOW){delay(10);};

    digitalWrite(currentLED, LOW);

    if (currentLED == LED_RED) {
      currentLED = LED_GREEN;
    } else if (currentLED == LED_GREEN) {
      currentLED = LED_BLUE;
    } else {
      currentLED = LED_RED;
    }

    digitalWrite(currentLED, HIGH);
    delay(50);
  }
}
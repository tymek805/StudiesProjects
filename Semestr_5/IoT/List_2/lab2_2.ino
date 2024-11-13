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
  }

  int greenValue;

  void setup() {
    // put your setup code here, to run once:
    initRGB();
    initButtons();

    greenValue = 126;
  }



  void loop() {
    // put your main code here, to run repeatedly:
    if (digitalRead(RED_BUTTON) == LOW && greenValue < 255) {
      greenValue++;
      delay(10);
    }
    if (digitalRead(GREEN_BUTTON) == LOW && greenValue > 0) {
      greenValue--;
      delay(10);
    }

    analogWrite(LED_GREEN, greenValue);
  }

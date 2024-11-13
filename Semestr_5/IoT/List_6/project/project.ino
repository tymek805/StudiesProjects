  #include "ButtonController.h"

ButtonController button1(2); 
ButtonController button2(4);

void onButton1ShortPress() {
    Serial.println("Button 1 short press");
}

void onButton1LongPress() {
    Serial.println("Button 1 long press");
}

void onButton2ShortPress() {
    Serial.println("Button 2 short press");
}

void onButton2LongPress() {
    Serial.println("Button 2 long press");
}

void setup() {
    Serial.begin(9600);
    button1.setShortPressCallback(onButton1ShortPress);
    button1.setLongPressCallback(onButton1LongPress);

    button2.setShortPressCallback(onButton2ShortPress);
    button2.setLongPressCallback(onButton2LongPress);
}

void loop() {
}

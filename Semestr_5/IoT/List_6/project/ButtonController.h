#ifndef BUTTON_CONTROLLER_H
#define BUTTON_CONTROLLER_H

#include <Arduino.h>

class ButtonController {
public:
    typedef void (*CallbackFunction)();

    ButtonController(uint8_t pin, unsigned long debounceTime = 10UL, unsigned long longPressTime = 300UL);

    void setShortPressCallback(CallbackFunction callback);
    void setLongPressCallback(CallbackFunction callback);

    static void handleInterruptPin2();
    static void handleInterruptPin4();

private:
    uint8_t _pin;
    unsigned long _debounceTime;
    unsigned long _longPressTime;
    CallbackFunction _shortPressCallback;
    CallbackFunction _longPressCallback;

    unsigned long _lastPressTime;
    int debouncedState;
    unsigned long _lastInterruptTime;

    void processInterrupt();
};

#endif

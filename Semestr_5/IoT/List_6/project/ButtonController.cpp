#include "ButtonController.h"

ButtonController* btnPin2 = nullptr;
ButtonController* btnPin4 = nullptr;

ButtonController::ButtonController(uint8_t pin, unsigned long debounceTime, unsigned long longPressTime)
    : _pin(pin), _debounceTime(debounceTime), _longPressTime(longPressTime),
      _shortPressCallback(nullptr), _longPressCallback(nullptr),
      _lastPressTime(0UL), debouncedState(HIGH), _lastInterruptTime(0UL) {
    if (_pin == 2) {
        btnPin2 = this;
    } else if (_pin == 4) {
        btnPin4 = this;
    }
    
    if (_pin == 2) {
        attachInterrupt(digitalPinToInterrupt(_pin), handleInterruptPin2, CHANGE);        
    } else if (_pin == 4) {
        PCICR |= (1 << PCIE2);
        PCMSK2 |= (1 << PCINT20);
    } else {return;}
    pinMode(_pin, INPUT_PULLUP);
}

ISR(PCINT2_vect) {
    btnPin4->handleInterruptPin4();
}

void ButtonController::setShortPressCallback(CallbackFunction callback) {
    _shortPressCallback = callback;
}

void ButtonController::setLongPressCallback(CallbackFunction callback) {
    _longPressCallback = callback;
}

void ButtonController::handleInterruptPin2() {
    btnPin2->processInterrupt();
}

void ButtonController::handleInterruptPin4() {
    btnPin4->processInterrupt();
}

void ButtonController::processInterrupt() {
    int buttonState = digitalRead(_pin);
    unsigned long interruptTime = millis();

    if (interruptTime - _lastInterruptTime > _debounceTime) {
        if (debouncedState != buttonState) {
          _lastInterruptTime = interruptTime;
          debouncedState = buttonState;
            if (buttonState == LOW) {
                _lastPressTime = interruptTime;
            } else {
                if (interruptTime - _lastPressTime > _longPressTime && _longPressCallback) {
                    _longPressCallback();
                } else if (_shortPressCallback) {
                    _shortPressCallback();
                }
            }
        }
        
    }
    
}

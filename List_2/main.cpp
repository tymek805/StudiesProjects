#include "Number.h"
#include <iostream>

int main() {
    Number number = Number(123);
    Number number2 = Number(937);
    (number + number2).print();

    return 0;
}
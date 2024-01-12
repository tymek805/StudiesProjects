#include "Number.h"

int main() {
    Number number1 = Number(123);
    Number number2 = Number(937);
    (number1 + number2).print();

    (number2 - number1).print();
    number1 = 937;
    (number2 - number1).print();

    number1 = 10;
    number2 = 10;
    (number1 * number2).print();

    return 0;
}
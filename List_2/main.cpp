#include <iostream>
#include "Number.h"

void print(Number x, Number y, const std::string& operation){
    std::cout << x.toString() << " " << operation << " " << y.toString() << " = ";
}

int main() {
    Number number1 = Number(123);
    Number number2 = Number(937);
    print(number1, number2, "+");
    (number1 + number2).print();

    print(number2, number1, "-");
    (number2 - number1).print();
    number1 = 937;
    print(number2, number1, "-");
    (number2 - number1).print();

    number1 = 23958233;
    number2 = 5830;
    print(number1, number2, "*");
    (number1 * number2).print();

    number1 = 150;
    number2 = 50;
    print(number1, number2, "/");
    (number1 / number2).print();

    return 0;
}
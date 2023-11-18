#include <iostream>
#include "Number.h"


Number::Number() {
    length = DEFAULT_LENGTH;
    digits = new int[length];
    isNegative = false;
}

Number::Number(int number){
    setNumber(number);
}

Number::Number(int* digits, int length) {
    this->digits = digits;
    this->length = length;
    this->isNegative = false;
}

Number::Number(int* digits, int length, bool isNegative){
    this->digits = digits;
    this->length = length;
    this->isNegative = isNegative;
}

Number::Number(const Number& otherNumber){
    copyNumber(otherNumber);
}

Number::~Number() {
    delete digits;
}

void Number::operator=(const int value) {
    setNumber(value);
}

void Number::operator=(const Number& otherNumber){
    copyNumber(otherNumber);
}

Number Number::operator+(const Number& otherNumber){
    if (!isNegative && otherNumber.isNegative)
        return *this - otherNumber;

    int maxLength = std::max(length, otherNumber.length) + 1;
    int* addedValues = new int[maxLength];
    addedValues[maxLength - 1] = 0;

    int carry = 0;
    for (int i = 0; i < maxLength; i++){
        int sum = carry;
        if (i < length)
            sum += digits[i];
        if (i < otherNumber.length)
            sum += otherNumber.digits[i];

        if (sum >= 10)
            carry = 1;
        else
            carry = 0;

        addedValues[i] = ( sum % 10 );
    }

    return Number(addedValues, maxLength, (isNegative && otherNumber.isNegative));
}

Number Number::operator-(const Number& otherNumber){
    if (otherNumber.isNegative && isNegative)
        return *this + otherNumber;

    int maxLength = std::max(length, otherNumber.length);
    int* subtractedValues = new int[maxLength];

    int borrow = 0;
    for (int i = 0; i < maxLength; i++) {
        int diff = borrow;
        if (i < length)
            diff += digits[i];
        if (i < otherNumber.length)
            diff -= otherNumber.digits[i];

        if (diff < 0) {
            diff += 10;
            borrow = -1;
        }
        else {
            borrow = 0;
        }
        subtractedValues[i] = diff;
    }

    return Number(subtractedValues, maxLength);
}

Number Number::operator*(const Number& otherNumber){
    int maxLength = length + otherNumber.length;
    int* result = new int[maxLength];
    for (int i = 0; i < maxLength; i++)
        result[i] = 0;

    int shift = 0;

    int p = 0;
    int q;

    for (int i = 0; i < length; i++) {
        int carry = 0;
        q = 0;
        for (int j = 0; j < otherNumber.length; j++) {
            int mul = digits[i] * otherNumber.digits[i] + result[p + q] + carry;
            carry = mul / 10;
            result[p + q] = mul % 10;
            q++;
        }

        if (carry > 0)
            result[p + q] += carry;
        p++;
    }
    return Number(result, maxLength, !(isNegative == otherNumber.isNegative));
}

Number Number::operator/(const Number& otherNumber){
    int* result = new int[length];
    for (int i = 0; i < length; i++)
        result[i] = 0;

    int idx = 0;
    int temp = digits[idx];
    int divisor = 0;
    for (int i = 0; i < otherNumber.length; i++)
        divisor += i * 10 + otherNumber.digits[i];

    while (temp < divisor)
        temp = temp * 10 + digits[++idx];

    int sum = 0;
    while (length > idx) {
        sum += temp / divisor;
        temp = (temp % divisor) * 10 + digits[++idx];
    }

    for (int i = 0; sum > 0; i++){
        result[i] = sum % 10;
        sum /= 10;
    }

    return Number(result, length, !(isNegative == otherNumber.isNegative));
}

std::string Number::toStr(){
    std::string outputString = "";

    if (isNegative)
        outputString += '-';

    for (int i = length - 1; i >= 0; i--){
        if (length < 2 || i != 0 || digits[i] != 0 )
            outputString += ( digits[i] + 48 );
    }

    return outputString;
}

int Number::calculateLength(int number){
    int size = 0;
    while (number != 0) {
        number /= 10;
        size++;
    }
    return size;
}

void Number::setNumber(int number){
    delete digits;

    isNegative = number < 0;
    if (isNegative)
        number *= -1;

    length = calculateLength(number);
    digits = new int[length];

    for (int i = 0; i < length; i++) {
        digits[i] = number % 10;
        number /= 10;
    }
}

void Number::copyNumber(const Number& otherNumber){
    delete digits;

    length = otherNumber.length;
    isNegative = otherNumber.isNegative;
    digits = new int[length];
    for (int i = 0; i < length; i++) {
        digits[i] = otherNumber.digits[i];
    }
}

Number Number::divide(Number& otherNumber, Number** rest){
    Number divNumber = *this / otherNumber;
    Number temp = (*this - (divNumber * otherNumber));
    if (temp.toStr() == "0")
        rest = nullptr;
    else
        **rest = temp;
    return divNumber;
}

void Number::printNumber(){
    if (isNegative)
        std::cout << '-';
    bool isBeginning = true;
    for (int i = length - 1; i >= 0; i--) {
        if (digits[i] != 0 || !isBeginning) {
            std::cout << digits[i];
            isBeginning = false;
        }
    }

    std::cout << std::endl;
}

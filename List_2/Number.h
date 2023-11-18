#include <string>

#define DEFAULT_LENGTH 10

class Number {
public:
    Number();
    Number(int number);
    Number(int* digits, int length);
    Number(int* digits, int length, bool isNegative);
    Number(const Number& otherNumber);
    ~Number();

    void operator=(const int value);
    void operator=(const Number& otherNumber);

    Number operator+(const Number& otherNumber);
    Number operator-(const Number& otherNumber);
    Number operator*(const Number& otherNumber);
    Number operator/(const Number& otherNumber);

    std::string toStr();

    void printNumber();
    Number divide(Number& otherNumber, Number** rest);
private:
    int length;
    int* digits;

    bool isNegative;

    int calculateLength(int number);
    void setNumber(int number);
    void copyNumber(const Number& otherNumber);
};
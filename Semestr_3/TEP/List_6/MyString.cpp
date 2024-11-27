#include <vector>
#include <iostream>
#include "MyString.h"


MyString::MyString() {
    table = nullptr;
    length = 0;
}

MyString::MyString(const MyString &other) {
    length = other.length;
    table = new char[length];
    for (int i = 0; i < length; i++)
        table[i] = other.table[i];
}

MyString::~MyString() {
    delete table;
}

MyString& MyString::operator=(std::string newValue) {
    delete table;
    length = (int) newValue.length();
    table = new char[length];
    for (int i = 0; i < length; i++)
        table[i] = newValue[i];

    return *this;
}

MyString& MyString::operator+(std::string value) {
    int newLength = (int) value.length() + length;
    char* newTable = new char[newLength];
    for (int i = 0; i < length; i++)
        newTable[i] = table[i];

    for (int i = 0; i < value.length(); i++)
        newTable[i + length] = value[i];

    delete table;
    table = newTable;
    length = newLength;
    return *this;
}


MyString& MyString::operator+=(std::string value) {
    return (*this + std::move(value));
}

MyString &MyString::operator-(std::string value) {
    int stringLength = (int) value.length();
    int substringIdx = -1;

    int i = 0;
    while (i < length && stringLength <= length - i && substringIdx == -1) {
        bool foundSubstring = true;
        for (int j = 0; j < stringLength; j++){
            if (table[i + j] != value[j])
                foundSubstring = false;
        }
        if (foundSubstring)
            substringIdx = i;
        i++;
    }

    if (substringIdx != -1) {
        char* newTable;
        if (length == stringLength) {
            newTable = nullptr;
        } else {
            newTable = new char[length - stringLength];
            for (int j = 0; j < length; j++) {
                if (j < substringIdx)
                    newTable[j] = table[j];
                else if (j >= stringLength + substringIdx)
                    newTable[j - stringLength] = table[j];
            }
        }
        delete table;
        table = newTable;
        length = length - stringLength;
    }
    return *this;
}


MyString::operator bool() const {
    return length > 0;
}

std::string MyString::toStandard() {
    std::string string;
    for (int i = 0; i < length; i++)
        string += table[i];
    return string;
}

void MyString::print() {
    std::cout << toStandard() << std::endl;
}

void MyString::superAdd(std::vector<MyString*>* myStrings) {
    MyString newMyString;
    for (int i = 0; i < myStrings->size(); i++) {
        newMyString += myStrings->at(i)->toStandard();
    }
    delete table;
    length = newMyString.length;
    table = new char[length];
    for (int i = 0; i < length; i++)
        table[i] = newMyString.table[i];
}

#include "Table.h"
#include <iostream>


Table::Table() {
    name = DEFAULT_NAME;
    length = DEFAULT_LENGTH;
    values = new int[length];
    std::cout << "bezp: '" << name << "'\n";
}

Table::Table(std::string name, int length) {
    this->name = name;
    std::cout << "parametr: '" << this->name << "'\n";
    this->length = length;
    values = new int[this->length];
}

Table::Table(const Table &other) {
    name = other.name + "_copy";
    length = other.length;

    values = new int[length];
    for (int i = 0; i < length; i++)
        values[i] = other.values[i];
    std::cout << "kopiuj: '" << name << "'\n";
}

Table::~Table() {
    std::cout << "usuwam: '" << name << "'\n";
    delete values;
}

void Table::setName(std::string name) {
    this->name = name;
}

bool Table::setNewSize(int length) {
    if (length <= 0)
        return false;

    int* new_values = new int[length];
    for (int i = 0; i < length; i++)
        new_values[i] = values[i];

    delete values;
    values = new_values;
    this->length = length;

    return true;
}

Table* Table::clone() {
    Table* table = new Table(name, length);
    int* new_values = new int[length];
    for (int i = 0; i < length; i++)
        new_values[i] = values[i];
    delete table->values;
    table->values = new_values;

    return table;
}

int Table::getLength() {
    return length;
}

void Table::setValues(int *values, int length) {
    setNewSize(length);
    for (int i = 0; i < length; i++)
        this->values[i] = values[i];
}

void Table::printValues() {
    for (int i = 0; i < length; i++)
        std::cout << values[i] << " ";
    std::cout << "\n";
}

int* Table::getValues() {
    return values;
}
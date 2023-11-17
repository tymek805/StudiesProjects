#include <iostream>
#include "solution.h"
#include "Table.h"

void allocTableFill34(int size) {
    while (size <= 0) {
        std::cout << "Set value again: ";
        std::cin >> size;
    }

    int* table = new int[size];

    for (int i = 0; i < size; i++)
        table[i] = FILL_VALUE;

    for (int i = 0; i < size; i++)
        std::cout << table[i] << ' ';

    delete[] table;
}

bool allocTable2Dim(int*** table, int sizeX, int sizeY) {
    if (table == NULL || sizeX <= 0 || sizeY <= 0)
        return false;

    int** twoDimensionalTable = new int* [sizeX];
    for (int i = 0; i < sizeX; i++)
        twoDimensionalTable[i] = new int[sizeY];

    deallocTable2Dim(table, sizeX, sizeY);
    *table = twoDimensionalTable;

    return true;
}

bool deallocTable2Dim(int*** table, int sizeX, int sizeY) {
    if (table == NULL || sizeX <= 0 || sizeY <= 0)
        return false;

    int** twoDimensionalTable = *table;

    for (int i = 0; i < sizeX; i++)
        delete twoDimensionalTable[i];

    delete twoDimensionalTable;
    return true;
}

void modifyTable(Table* table, int newSize) {
    table->setNewSize(newSize);
}

void modifyTable(Table table, int newSize) {
    table.setNewSize(newSize);
}

void doubleSize(Table* table, int* values, int length) {
    Table doubleTable = Table();
    int* newValues = new int[length * 2];
    for (int i = 0; i < length * 2; i++) {
        if (i >= length)
            newValues[i] = values[i - length];
        else
            newValues[i] = values[i];
    }
    doubleTable.setValues(newValues, length * 2);
    table = &doubleTable;
}
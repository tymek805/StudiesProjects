#include <iostream>
#include "solution.h"

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
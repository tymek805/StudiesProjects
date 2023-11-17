#include <iostream>
#include "solution.h"

void alloc_table_fill_34(int size) {
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
#include "Table.h"

void allocTableFill34(int size);
bool allocTable2Dim(int*** table, int sizeX, int sizeY);
bool deallocTable2Dim(int*** table, int sizeX, int sizeY);

void modifyTable(Table* table, int newSize);
void modifyTable(Table table, int newSize);

void doubleSize(Table* table, int* values, int length);

const int FILL_VALUE = 34;
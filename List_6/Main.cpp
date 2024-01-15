#include <vector>
#include "MyString.h"

int main() {
    MyString myString;
    myString = "Ala ma";

    MyString otherString = MyString(myString);
    otherString.print();

    myString = myString + " kota";
    myString += " i psa";
    myString.print();

    MyString thirdString;
    thirdString = "SuperAdd";
    std::vector<MyString*> strings = {&otherString, &thirdString, &myString};
    myString.superAdd(&strings);
    myString.print();
}

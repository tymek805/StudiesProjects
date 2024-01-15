#include "MyString.h"

int main() {
    MyString myString;
    myString = "Ala ma kota";
    myString.print();

    MyString otherString = MyString(myString);
    otherString.print();

    myString = myString + "!";
    myString.print();
    myString += "kota ";
    myString.print();
    myString = myString + "i psa";
    myString.print();
    myString = myString - "kota i psa";
    myString.print();
    otherString.print();
}
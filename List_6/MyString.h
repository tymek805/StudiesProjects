#ifndef TEP_MYSTRING_H
#define TEP_MYSTRING_H


#include <string>

class MyString {
public:
    MyString();
    MyString(const MyString &other);

    ~MyString();

    MyString& operator=(std::string newValue);
    MyString& operator+(std::string value);
    MyString& operator+=(std::string value);
    MyString& operator-(std::string value);
    explicit operator bool () const;

    std::string toStandard();
    void print();
private:
    char* table;
    int length;
};


#endif

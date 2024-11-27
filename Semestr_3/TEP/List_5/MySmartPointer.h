#ifndef TEP_MYSMARTPOINTER_H
#define TEP_MYSMARTPOINTER_H

#include "RefCounter.h"

template < typename T >
class MySmartPointer {
public:
    explicit MySmartPointer(T* pointer);
    MySmartPointer();
    MySmartPointer(const MySmartPointer& other);
    ~MySmartPointer();

    MySmartPointer& operator=(const MySmartPointer& other);

    T& operator*();
    T* operator->();
private:
    T* pointer;
    RefCounter* refCounter;
};

template<typename T>
MySmartPointer<T>::MySmartPointer(T *pointer) : pointer(pointer), refCounter(new RefCounter()) {
    refCounter->add();
}

template<typename T>
MySmartPointer<T>::MySmartPointer() {
    pointer = nullptr;
    refCounter = new RefCounter();
}

template<typename T>
MySmartPointer<T>::MySmartPointer(const MySmartPointer &other) : pointer(other.pointer), refCounter(other.refCounter) {
    refCounter->add();
}

template<typename T>
MySmartPointer<T>::~MySmartPointer() {
    if (refCounter->dec() == 0) {
        delete refCounter;
        delete pointer;
    }
}

template<typename T>
MySmartPointer<T> &MySmartPointer<T>::operator=(const MySmartPointer &other) {
    if (refCounter->dec() == 0) {
        delete refCounter;
        delete pointer;
    }

    refCounter = other.refCounter;
    pointer = other.pointer;
    refCounter->add();

    return *this;
}

template<typename T>
T &MySmartPointer<T>::operator*() {
    return *pointer;
}

template<typename T>
T *MySmartPointer<T>::operator->() {
    return pointer;
}


#endif //TEP_MYSMARTPOINTER_H

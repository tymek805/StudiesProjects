#ifndef TEP_REFCOUNTER_H
#define TEP_REFCOUNTER_H


class RefCounter {
public:
    RefCounter();

    int add();
    int dec();
    int get();
private:
    int count;
};

#endif //TEP_REFCOUNTER_H

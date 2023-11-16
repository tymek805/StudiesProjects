#define DEFAULT_NAME "default";
#define DEFAULT_LENGTH 3;
#include "string"


class Table {
public:
    Table();
    Table(std::string name, int length);
    Table(const Table &other);
    ~Table();

    void setName(std::string name);
    bool setNewSize(int length);

    int getLength();

    int* getValues();
    void setValues(int* values, int length);
    void printValues();

    Table* clone();

private:
    std::string name;
    int length;
    int* values;
};
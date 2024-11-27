#include <string>
#include <vector>

class Command {
public:
    explicit Command(const std::string& line);

    ~Command();

    std::string getType();
    std::vector<std::string>* getElements();
private:
    std::string type;
    std::vector<std::string>* elements;

    void splitByWhitespace(const std::string& line);
};
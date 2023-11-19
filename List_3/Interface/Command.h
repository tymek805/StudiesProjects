#include <string>

class Command {
public:
    Command(char* line);
    std::string getType();
private:
    std::string type;

    std::string calculateType(char* line);
};
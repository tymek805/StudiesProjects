#include "Command.h"

#define MAX_LINE_LENGTH 200

class UserInput {
public:
    void run();
    bool commands(Command line);
};
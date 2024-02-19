#ifndef TEP_INTERFACE_H
#define TEP_INTERFACE_H

#define ENTER_KEYWORD "enter"
#define VARS_KEYWORD "vars"
#define COMP_KEYWORD "comp"
#define JOIN_KEYWORD "join"
#define EXIT_KEYWORD "exit"
#define PRINT_KEYWORD "print"
#define HELP_KEYWORD "help"

#include "Command.h"
#include "../Tree/Tree.h"

class Interface {
public:
    Interface();

    void run();

private:
    Tree *tree;

    bool enterCommand(Command command);
};


#endif //TEP_INTERFACE_H
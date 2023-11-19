#include <iostream>
#include "UserInput.h"

void UserInput::run() {
    bool exit = false;
    while (!exit){
        char* line = new char[MAX_LINE_LENGTH];
        std::cin.getline(line, MAX_LINE_LENGTH);
        exit = commands(Command(line));
    }
}

bool UserInput::commands(Command command) {
    std::string commandType = command.getType();
    if (commandType == "enter") {

    } else if (commandType == "vars") {

    } else if (commandType == "print") {

    } else if (commandType == "comp") {

    } else if (commandType == "join") {

    } else if (commandType == "exit")
        return true;
    else
        std::cout << "Command not recognized" << '\n';
    return false;
}
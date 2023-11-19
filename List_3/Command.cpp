#include <iostream>
#include "Command.h"

Command::Command(char* line) {
    type = calculateType(line);
}

std::string Command::calculateType(char* line) {
    int i = 0;
    char currentCharacter = line[i];
    std::string typeString;

    while (currentCharacter != ' ' && currentCharacter != '\0') {
        typeString += currentCharacter;
        currentCharacter = line[++i];
    }

    return typeString;
}

std::string Command::getType() {
    return type;
}
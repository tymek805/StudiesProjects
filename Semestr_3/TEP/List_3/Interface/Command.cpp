#include "Command.h"


Command::Command(const std::string &line) {
    elements = new std::vector<std::string>();
    splitByWhitespace(line);
}

Command::~Command() {
    delete elements;
}

void Command::splitByWhitespace(const std::string &line) {
    std::string currentString;

    for (char c: line) {
        if (c != ' ') {
            currentString += c;
        } else {
            if (type.empty())
                type = currentString;
            else
                elements->push_back(currentString);
            currentString.clear();
        }
    }
    if (!currentString.empty()) {
        if (type.empty())
            type = currentString;
        else
            elements->push_back(currentString);
    }
}

std::string Command::getType() {
    return type;
}

std::vector<std::string> *Command::getElements() {
    return elements;
}

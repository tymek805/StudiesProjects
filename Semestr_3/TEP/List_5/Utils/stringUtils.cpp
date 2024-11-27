#include <vector>
#include <iostream>
#include "stringUtils.h"
#include "../Tree/Tree.h"

bool isNumber(const std::string& str) {
    char* end = nullptr;
    std::strtod(str.c_str(), &end);
    return end != str.c_str() && *end == '\0';
}

bool hasInvalidChar(const std::string& str) {
    std::vector<char> invalidChars = {'$', '#', '&'};
    for (char c : str) {
        for (char invalidChar : invalidChars)
            if (invalidChar == c)
                std::cout << WARN_INV_CHAR << std::endl;
    }
}
#ifndef TEP_TREE_H
#define TEP_TREE_H

#define WARNING_MES "WARNING - incorrect number of elements"

#define ADD_OPERATOR "+"
#define SUB_OPERATOR "-"
#define MUL_OPERATOR "*"
#define DIV_OPERATOR "/"
#define SIN_OPERATOR "sin"
#define COS_OPERATOR "cos"

#define DEFAULT_VALUE "1"

#include <string>
#include <vector>
#include <map>
#include "Node.h"

class Tree {
public:
    Tree();

    void passElements(std::vector<std::string> *elements);
    std::string toString();
private:
    Node *root;
    std::map<std::string, int> functionMap;

    void createFunctionMap();
    std::string preorderTraversal(Node* node);

    int getNumberOfChildrenOrDefault(const std::string& key);
};

#endif //TEP_TREE_H

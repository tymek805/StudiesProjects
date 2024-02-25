#ifndef TEP_TREE_H
#define TEP_TREE_H

#define WARN_INV_ELEMS_LEN "WARNING - incorrect number of elements"
#define WARN_INV_CHAR "WARNING - variable contains invalid char"
#define WARN_INV_VARS_LEN "WARNING - given vars do not match with the variables in tree"
#define WARN_INV_VAR "WARNING - var is not a number"

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
    ~Tree();

    Tree& operator=(const Tree& other);
    Tree& operator+(Tree* other);

    void passElements(std::vector<std::string> *elements);
    void printVars();
    int comp(std::vector<std::string> *vars);
    void join(Tree* joiningTree);

    Node* getRoot();
    std::string toString();
private:
    Node *root;
    std::vector<char> invalidChars;
    std::map<std::string, int> functionMap;
    std::map<std::string, int> variableMap;
    std::vector<std::string> variableOrder;

    void createFunctionMap();
    int getNumberOfChildrenOrDefault(const std::string& key);

    NodeType evalNodeType(const std::string& elem);
    int calculate(Node* node);
    Node* findLeaf(Node* node, Node* insertNode);
    std::string preorderTraversal(Node* node);
};

#endif //TEP_TREE_H

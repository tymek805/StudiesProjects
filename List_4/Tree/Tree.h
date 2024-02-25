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
#include "../Utils/stringUtils.h"

template< typename T > class Tree {
public:
    Tree();
    ~Tree();

    Tree& operator=(const Tree& other);
    Tree& operator+(Tree* other);

    void passElements(std::vector<std::string> *elements);
    void printVars();
    T comp(std::vector<std::string> *vars);
    void join(Tree* joiningTree);

    Node* getRoot();
    std::string toString();
private:
    Node *root;
    std::vector<char> invalidChars;
    std::map<std::string, int> functionMap;
    std::map<std::string, T> variableMap;
    std::vector<std::string> variableOrder;

    void createFunctionMap();
    int getNumberOfChildrenOrDefault(const std::string& key);

    NodeType evalNodeType(const std::string& elem);
    T calculate(Node* node);
    Node* findLeaf(Node* node, Node* insertNode);
    std::string preorderTraversal(Node* node);
};

template < typename T >
Tree<T>::Tree() {
    root = nullptr;
    invalidChars = {'$', '#', '&'};
    createFunctionMap();
}

template < typename T >
Tree<T>::~Tree() {
    delete root;
}

template < typename T >
Tree<T> &Tree<T>::operator=(const Tree<T> &other) {
    root = other.root;
    variableMap = other.variableMap;
    variableOrder = other.variableOrder;
    return *this;
}

template < typename T >
Tree<T> &Tree<T>::operator+(Tree<T>* other) {
    Tree* tree = this;
    tree->join(other);
    return *tree;
}

template < typename T >
void Tree<T>::createFunctionMap() {
    functionMap[ADD_OPERATOR] = 2;
    functionMap[SUB_OPERATOR] = 2;
    functionMap[MUL_OPERATOR] = 2;
    functionMap[DIV_OPERATOR] = 2;
    functionMap[SIN_OPERATOR] = 1;
    functionMap[COS_OPERATOR] = 1;
}

template < typename T >
int Tree<T>::getNumberOfChildrenOrDefault(const std::string& key) {
    return (functionMap.find(key) == functionMap.end()) ? 0 : functionMap.at(key);
}

template < typename T >
NodeType Tree<T>::evalNodeType(const std::string &elem) {
    if (functionMap.find(elem) != functionMap.end())
        return OPERATOR;

    NodeType nodeType = VALUE;

    for (char c : elem) {
        if (isalpha(c) != 0) {
            for (char invalidChar : invalidChars)
                if (invalidChar == c)
                    std::cout << WARN_INV_CHAR << std::endl;
            nodeType = ARGUMENT;
        }
    }

    if (elem.front() == '"' && elem.back() == '"')
        nodeType = VALUE;

    if (nodeType == ARGUMENT) {
        if (variableMap.find(elem) == variableMap.end())
            variableOrder.push_back(elem);
        variableMap[elem] = -1;
    }

    return nodeType;
}

template < typename T >
void Tree<T>::passElements(std::vector<std::string> *elements) {
    delete root;

    std::vector<Node*> pendingNodes;
    bool errorOccurred = false;

    for (const std::string& elem: *elements) {
        if (root == nullptr) {
            root = new Node(elem, getNumberOfChildrenOrDefault(elem), evalNodeType(elem), nullptr);
            if (!root->hasSufficient())
                pendingNodes.push_back(root);
        } else if (!pendingNodes.empty()){
            Node* parentNode = pendingNodes.back();
            Node* newNode = new Node(elem, getNumberOfChildrenOrDefault(elem), evalNodeType(elem), parentNode);
            parentNode->addChild(newNode);

            if (parentNode->hasSufficient())
                pendingNodes.pop_back();

            if (!newNode->hasSufficient())
                pendingNodes.push_back(newNode);
        } else {
            errorOccurred = true;
        }
    }

    if (errorOccurred || !pendingNodes.empty())
        std::cout << WARN_INV_ELEMS_LEN << std::endl;

    while (!pendingNodes.empty()) {
        Node* node = pendingNodes.back();

        while (!node->hasSufficient())
            node->addChild(new Node(DEFAULT_VALUE, getNumberOfChildrenOrDefault(DEFAULT_VALUE), evalNodeType(DEFAULT_VALUE), node));

        pendingNodes.pop_back();
    }
    std::cout << "Interpreted expression: " << toString() << std::endl;
}

template < typename T >
void Tree<T>::printVars() {
    for (const std::string& str : variableOrder)
        std::cout << str << " ";
    std::cout << std::endl;
}

template <>
int Tree<int>::calculate(Node *node) {
    NodeType nodeType = node->getNodeType();
    if (nodeType == OPERATOR) {
        std::string oper = node->getValue();

        if (oper == ADD_OPERATOR) {
            return calculate(node->getChild(0)) + calculate(node->getChild(1));
        } else if (oper == SUB_OPERATOR) {
            return calculate(node->getChild(0)) - calculate(node->getChild(1));
        } else if (oper == MUL_OPERATOR) {
            return calculate(node->getChild(0)) * calculate(node->getChild(1));
        } else if (oper == DIV_OPERATOR) {
            int secondValue = calculate(node->getChild(1));

            if (secondValue == 0)
                throw std::invalid_argument("Division by zero");

            return calculate(node->getChild(0)) / secondValue;
        } else if (oper == SIN_OPERATOR) {
            return (int) sin(calculate(node->getChild(0)));
        } else if (oper == COS_OPERATOR) {
            return (int) cos(calculate(node->getChild(0)));
        }
    } else if (nodeType == VALUE) {
        return stoi(node->getValue());
    } else if (nodeType == ARGUMENT) {
        return variableMap.at(node->getValue());
    }
}

template <>
int Tree<int>::comp(std::vector<std::string> *vars) {
    if (vars == nullptr && !variableOrder.empty()) {
        std::cout << WARN_INV_VARS_LEN << std::endl;
        return -1;
    }
    if (vars != nullptr && vars->size() != variableOrder.size()) {
        std::cout << WARN_INV_VARS_LEN << std::endl;
        return -1;
    }

    // Setting given values as vars
    for (int i = 0; i < variableOrder.size(); i++) {
        const std::string& str = vars->at(i);
        std::stod(str);
        if (isNumber(str)) {
            variableMap.at(variableOrder.at(i)) = stoi(str);
        } else {
            std::cout << WARN_INV_VAR << std::endl;
            return -1;
        }
    }

    return calculate(root);
}

template < typename T >
void Tree<T>::join(Tree<T>* joiningTree) {
    Node* node = findLeaf(root, joiningTree->getRoot());
    node->getParent()->setChild(0, joiningTree->getRoot());
    std::cout << toString() << std::endl;
}

template < typename T >
Node* Tree<T>::findLeaf(Node* node, Node* insertNode) {
    return node->getChildren()->empty() ? node : findLeaf(node->getChild(0), insertNode);
}

template < typename T >
std::string Tree<T>::preorderTraversal(Node* node) {
    std::string value = node->getValue();

    for (Node* childNode : *node->getChildren()) {
        value += " " + preorderTraversal(childNode);
    }
    return value;
}

template < typename T >
Node *Tree<T>::getRoot() {
    return root;
}

template < typename T >
std::string Tree<T>::toString() {
    return preorderTraversal(root);
}

#endif //TEP_TREE_H

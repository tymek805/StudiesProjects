#include <iostream>
#include <cmath>
#include "Tree.h"
#include "../Utils/stringUtils.h"

Tree::Tree() {
    root = nullptr;
    invalidChars = {'$', '#', '&'};
    createFunctionMap();
}

void Tree::createFunctionMap() {
    functionMap[ADD_OPERATOR] = 2;
    functionMap[SUB_OPERATOR] = 2;
    functionMap[MUL_OPERATOR] = 2;
    functionMap[DIV_OPERATOR] = 2;
    functionMap[SIN_OPERATOR] = 1;
    functionMap[COS_OPERATOR] = 1;
}

int Tree::getNumberOfChildrenOrDefault(const std::string& key) {
    return (functionMap.find(key) == functionMap.end()) ? 0 : functionMap.at(key);
}

NodeType Tree::evalNodeType(const std::string &elem) {
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

    if (nodeType == ARGUMENT) {
        if (variableMap.find(elem) == variableMap.end())
            variableOrder.push_back(elem);
        variableMap[elem] = -1;
    }

    return nodeType;
}

void Tree::passElements(std::vector<std::string> *elements) {
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

void Tree::printVars() {
    for (const std::string& str : variableOrder)
        std::cout << str << " ";
    std::cout << std::endl;
}

int Tree::comp(std::vector<std::string> *vars) {
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

        if (isNumber(str)) {
            variableMap.at(variableOrder.at(i)) = stoi(str);
        } else {
            std::cout << WARN_INV_VAR << std::endl;
            return -1;
        }
    }

    return calculate(root);
}

int Tree::calculate(Node *node) {
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

void Tree::join(Tree* joiningTree) {
    Node* node = findLeaf(root, joiningTree->getRoot());
    node->getParent()->setChild(0, joiningTree->getRoot());
    std::cout << toString() << std::endl;
}

Node* Tree::findLeaf(Node* node, Node* insertNode) {
    if (node->getChildren()->empty())
        return node;
    return findLeaf(node->getChild(0), insertNode);
}

std::string Tree::preorderTraversal(Node* node) {
    std::string value = node->getValue();

    for (Node* childNode : *node->getChildren()) {
        value += " " + preorderTraversal(childNode);
    }
    return value;
}

Node *Tree::getRoot() {
    return root;
}

std::string Tree::toString() {
    return preorderTraversal(root);
}

#include <iostream>
#include "Tree.h"

Tree::Tree() {
    root = nullptr;
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

void Tree::passElements(std::vector<std::string> *elements) {
    delete root;
    std::vector<Node*> pendingNodes;
    bool errorOccurred = false;

    for (const std::string& elem: *elements) {
        if (root == nullptr) {
            root = new Node(elem, getNumberOfChildrenOrDefault(elem));
            if (!root->hasSufficient())
                pendingNodes.push_back(root);
        } else if (!pendingNodes.empty()){
            Node* parentNode = pendingNodes.back();
            Node* newNode = new Node(elem, getNumberOfChildrenOrDefault(elem));
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
        std::cout << WARNING_MES << std::endl;

    while (!pendingNodes.empty()) {
        Node* node = pendingNodes.back();

        while (!node->hasSufficient()) {
            node->addChild(new Node(DEFAULT_VALUE, getNumberOfChildrenOrDefault(DEFAULT_VALUE)));
        }

        pendingNodes.pop_back();
    }
    std::cout << "Interpreted expression: " << toString() << std::endl;
}

std::string Tree::preorderTraversal(Node* node) {
    std::string value = node->getValue();

    for (Node* childNode : node->getChildren()) {
        value += " " + preorderTraversal(childNode);
    }
    return value;
}

std::string Tree::toString() {
    return preorderTraversal(root);
}
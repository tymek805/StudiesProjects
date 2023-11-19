#include "Node.h"

Node::Node() {
    value = DEFAULT_FIX_VALUE;
    left = nullptr;
    right = nullptr;
}

Node::Node(const std::string& value) {
    this->value = value;
    left = nullptr;
    right = nullptr;
}

Node::Node(const std::string& value, Node *left, Node *right) {
    this->value = value;
    this->left = left;
    this->right = right;
}

Node::~Node() {
    delete left;
    delete right;
}

std::string Node::getValue() {
    return value;
}
Node *Node::getLeft() {
    return left;
}
Node *Node::getRight() {
    return right;
}

void Node::setValue(const std::string& newValue) {
    value = newValue;
}

void Node::setLeft(Node *newLeft) {
    delete left;
    left = newLeft;
}

void Node::setRight(Node *newRight) {
    delete right;
    right = newRight;
}

#include "Node.h"

#include <utility>
#include <iostream>

Node::Node(std::string value, int remainingChildren, NodeType nodeType, Node* parent) :
        value(std::move(value)), remainingChildren(remainingChildren), nodeType(nodeType), parent(parent) {
}

Node::~Node() {
    std::cout << "Destructor called " << &*this << std::endl;
    children.clear();
}

void Node::addChild(Node *childNode) {
    children.push_back(childNode);
    remainingChildren--;
}

void Node::setChild(int idx, Node *child) {
    if (idx >= 0 && idx < children.size()) {
        delete children.at(idx);
        children.at(idx) = child;
    }
}

Node *Node::getChild(int idx) {
    return ((idx >= 0 && idx < children.size()) ? children.at(idx) : nullptr);
}

std::string Node::getValue() {
    return value;
}

std::vector<Node *>* Node::getChildren() {
    return &children;
}

NodeType Node::getNodeType() {
    return nodeType;
}

Node *Node::getParent() {
    return parent;
}

bool Node::hasSufficient() const {
    return remainingChildren == 0;
}

#include "Node.h"

#include <utility>

Node::Node(std::string value, int remainingChildren) :
        value(std::move(value)), remainingChildren(remainingChildren) {
}

std::string Node::getValue() {
    return value;
}

void Node::setValue(std::string newValue) {
    this->value = std::move(newValue);
}

void Node::addChild(Node *childNode) {
    children.push_back(childNode);
    remainingChildren--;
}

std::vector<Node *> Node::getChildren() {
    return children;
}

bool Node::hasSufficient() {
    return remainingChildren == 0;
}

#include "Node.h"

#include <utility>

Node::Node(std::string value, int remainingChildren, NodeType nodeType) :
        value(std::move(value)), remainingChildren(remainingChildren), nodeType(nodeType) {
}

Node::~Node(){
    for (Node* node : children)
        delete node;
}

void Node::addChild(Node *childNode) {
    children.push_back(childNode);
    remainingChildren--;
}

Node *Node::getChild(int idx) {
    return ((idx >= 0 && idx < children.size()) ? children.at(idx) : nullptr);
}

std::string Node::getValue() {
    return value;
}

std::vector<Node *> Node::getChildren() {
    return children;
}

NodeType Node::getNodeType() {
    return nodeType;
}

bool Node::hasSufficient() {
    return remainingChildren == 0;
}
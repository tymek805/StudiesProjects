#ifndef TEP_NODE_H
#define TEP_NODE_H


#include <string>
#include <vector>

enum NodeType {
    OPERATOR,
    VALUE,
    ARGUMENT
};

class Node {
public:
    Node(std::string value, int remainingChildren);

    std::string getValue();
    void setValue(std::string newValue);

    std::vector<Node *> getChildren();

    void addChild(Node* childNode);
    bool hasSufficient();
private:
    std::string value;
    int remainingChildren;

    std::vector<Node *> children;
};


#endif //TEP_NODE_H

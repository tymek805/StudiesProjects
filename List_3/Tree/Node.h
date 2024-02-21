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
    Node(std::string value, int remainingChildren, NodeType nodeType);
    ~Node();

    std::string getValue();
    std::vector<Node *> getChildren();
    NodeType getNodeType();

    void addChild(Node *childNode);
    Node* getChild(int idx);

    bool hasSufficient();
private:
    std::string value;
    int remainingChildren;
    NodeType nodeType;

    std::vector<Node *> children;
};


#endif //TEP_NODE_H

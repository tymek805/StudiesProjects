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
    Node(std::string value, int remainingChildren, NodeType nodeType, Node* parent);
    ~Node();

    std::string getValue();
    std::vector<Node *>* getChildren();
    NodeType getNodeType();
    Node* getParent();

    void addChild(Node *childNode);

    void setChild(int idx, Node* child);
    Node* getChild(int idx);

    bool hasSufficient() const;
private:
    std::string value;
    int remainingChildren;
    NodeType nodeType;
    Node* parent;

    std::vector<Node *> children;
};


#endif //TEP_NODE_H

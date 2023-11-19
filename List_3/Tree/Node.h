#include <string>
#define DEFAULT_FIX_VALUE "1"

class Node {
public:
    Node();
    Node(const std::string &value);
    Node(const std::string &value, Node* left, Node* right);
    ~Node();

    std::string getValue();
    Node* getLeft();
    Node* getRight();

    void setValue(const std::string& newValue);
    void setLeft(Node* newLeft);
    void setRight(Node* newRight);
private:
    std::string value;
    Node* left;
    Node* right;
};
#ifndef AVLTREE
#define AVLTREE

#define NULL_NODE_ERROR "Node nulo ou não alocado"

typedef struct Node {
  int data;
  struct Node *left;
  struct Node *right;
  int balanced;
  int height;
} Node;

typedef struct TreeAVL {
  Node *root;
} TreeAVL;

Node *createNode(int data);

TreeAVL *startTree();

int maxValues(int nodeA, int nodeB);

int getHeight(Node *node);

int calculateBalance(Node *node);

int calculateHeight(Node *node);

Node *getMin(Node *node);

Node *getMax(Node *node);

Node *rotateRight(Node *node);

Node *rotateLeft(Node *node);

Node *rotateLeftRight(Node *node);

Node *rotateRightLeft(Node *node);

Node *searchNode(Node *tree, int data);

Node *insertNode(Node *node, int data);

Node *removeNode(Node *node, int data);

#endif

#ifndef BINARYTREE
#define BINARYTREE

#include <stdio.h>


typedef struct Node {
    int data;
    struct Node *left;
    struct Node *right;
} Node;

typedef struct {
    Node *root;
} Tree;

typedef struct {
    Node *node;
    int status;
} TreeReturn;

TreeReturn *returnDefine (Node *node, int status);

Node *createNode(int data);

Tree *createTree(void);

Node *insertTree(Node *node, int data);

TreeReturn *searchTree(Node *node, int data);

void printTree (Node *node);

void invertTree (Node *node);

Node *removeNode (Node *node, int data);

Node *getMaxNode(Node *node);

Node *getMinNode(Node *node);

#endif // BINARYTREE
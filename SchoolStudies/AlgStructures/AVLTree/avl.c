#include "avl.h"
#include <stdio.h>
#include <stdlib.h>

Node *createNode(int data) {
  Node *node = (Node *)malloc(sizeof(Node));
  node->data = data;
  node->balanced = 0;
  node->height = 0;
  node->right = NULL;
  node->left = NULL;

  return node;
}

TreeAVL *startTree() {
  TreeAVL *tree = (TreeAVL *)malloc(sizeof(TreeAVL));
  tree->root = NULL;

  return tree;
}

int maxValues(int nodeA, int nodeB) {
  if (nodeA > nodeB) {
    return nodeA;
  }

  return nodeB;
}

int getHeight(Node *node) {
  if (node == NULL) {
    printf("%s", NULL_NODE_ERROR);
    exit(EXIT_FAILURE);
  }
  return node->height;
}

int calculateBalance(Node *node) {
  if (node == NULL) {
    printf("%s", NULL_NODE_ERROR);
    exit(EXIT_FAILURE);
  }

  return getHeight(node->left) - getHeight(node->right);
}

int calculateHeight(Node *node) {
  if (node == NULL) {
    printf("%s", NULL_NODE_ERROR);
    exit(EXIT_FAILURE);
  }

  return 1 + maxValues(getHeight(node->left), getHeight(node->right));
}

Node *getMin(Node *node) {
  if (node == NULL) {
    return NULL;
  }

  if (node->left == NULL) {
    return node;
  }

  return getMin(node);
}

Node *getMax(Node *node) {
  if (node == NULL) {
    return NULL;
  }

  if (node->right == NULL) {
    return node;
  }

  return getMax(node);
}

Node *rotateRight(Node *node) {
  Node *newRoot = node->left;
  node->left = newRoot->right;
  newRoot->right = node;

  node->height = calculateHeight(node);
  newRoot->height = calculateHeight(newRoot);

  return newRoot;
}

Node *rotateLeft(Node *node) {
  Node *newRoot = node->right;
  node->right = newRoot->left;
  newRoot->left = node;

  node->height = calculateHeight(node);
  newRoot->height = calculateHeight(newRoot);

  return newRoot;
}

Node *rotateLeftRight(Node *node) {
  node->left = rotateLeft(node->left);
  Node *newRoot = rotateRight(node);

  return newRoot;
}

Node *rotateRightLeft(Node *node) {
  node->right = rotateRight(node->right);
  Node *newRoot = rotateLeft(node);

  return newRoot;
}

Node *searchNode(Node *node, int data) {
  if (node == NULL) {
    return NULL;
  }

  if (node->data > data) {
    return searchNode(node->right, data);
  }

  if (node->data <= data) {
    return searchNode(node->left, data);
  }

  return node;
}

Node *insertNode(Node *node, int data) {
  if (node == NULL) {
    Node *newNode = createNode(data);
    return newNode;
  }

  if (node->data > data) {
    node->right = insertNode(node->right, data);
  }

  if (node->data < data) {
    node->left = insertNode(node->left, data);
  }

  node->height = calculateHeight(node);

  int balance = calculateBalance(node);

  if (balance > 1 && data < node->left->data) {
    return rotateRight(node);
  }

  if (balance < -1 && data > node->right->data) {
    return rotateLeft(node);
  }

  if (balance > 1 && data > node->left->data) {
    return rotateLeftRight(node);
  }

  if (balance > 1 && data < node->right->data) {
    return rotateRightLeft(node);
  }

  return node;
}

Node *removeNode(Node *node, int data) {
  if (node == NULL) {
    return NULL;
  }

  if (node->data > data) {
    node->right = removeNode(node->right, data);
  }

  if (node->data < data) {
    node->left = removeNode(node->left, data);
  }

  if (node->right == NULL) {
    Node *tempNode = node->left;
    free(node);
    return tempNode;
  }

  if (node->left == NULL) {
    Node *tempNode = node->right;
    free(node);
    return tempNode;
  }

  Node *tempNode = getMin(node->right);
  node->data = tempNode->data;
  node->right = removeNode(node->right, tempNode->data);

  node->right = calculateHeight(node);
  int actualBalance = calculateBalance(node);
  int rightBalance = calculateBalance(node->right);
  int leftBalance = calculateBalance(node->left);

  if (actualBalance > 1 && leftBalance >= 0) {
    return rotateRight(node);
  }

  if (actualBalance > 1 && leftBalance < 0) {
    return rotateLeftRight(node);
  }

  if (actualBalance < -1 && rightBalance <= 0) {
    return rotateLeft(node);
  }

  if (actualBalance < -1 && rightBalance > 0) {
    return rotateRightLeft(node);
  }

  return node;
}

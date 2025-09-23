#include <stdio.h>
#include <stdlib.h>
#include "binaryTree.h"

TreeReturn *returnDefine (Node *node, int status) {
    TreeReturn *returnComponent;
    returnComponent->node = node;
    returnComponent->status = status;
    return returnComponent;
}

Node *createNode(int data) {
    Node *node = (Node*)malloc(sizeof(Node));

    if (node == NULL) {
        printf("Deu merda");
        exit(EXIT_FAILURE);
    }

    node->data = data;
    node->right = NULL;
    node->left = NULL;

    return node;
}

Tree *createTree() {
    Tree *tree = (Tree*)malloc(sizeof(Tree));

    if (tree == NULL) {
        printf("Deu merda");
        exit(EXIT_FAILURE);
    }

    tree->root = NULL;

    return tree;
}

Node *insertTree (Node *node, int data) {
    if (node == NULL) {
        Node *newNode = createNode(data);
        return newNode;
    }

    if (data < node->data) {
        node->left = insertTree(node->left, data);
    }

    if (data >= node->data) {
        node->right = insertTree(node->right, data);
    }

    // printf("%i \n", node->data);
    return node;
}

TreeReturn *searchTree(Node *node, int data) {
    if (node == NULL) {
        printf("Arvore nula");
        return returnDefine(NULL, 0);
    }

    if (data == node->data) {
        return returnDefine(node, 1);
    }

    if (data < node->data) {
        return searchTree(node->left, data);
    }

    if (data >= node->data) {
        return searchTree(node->right, data);
    }
}

void printTree(Node *node) {
    if (node == NULL) {
        return;
    }
    // Intra ordem
    printTree(node->left);
    printf("\n%i\n", node->data);
    printTree(node->right);

    return;
}

void invertTree(Node *node) {
    if (node == NULL) {
        return;
    }

    Node *tempLeft = node->left;
    node->left = node->right;
    node->right = tempLeft;
    invertTree(node->left);
    invertTree(node->right);

    return;
}

Node *getMinNode (Node *node) {
    if (node == NULL) {
        return NULL;
    }

    if (node->left == NULL) {
        // Encounter the last node
        return node;
    }

    return getMinNode(node->left);
}

Node *getMaxNode (Node *node) {
    if (node == NULL) {
        return NULL;
    }

    if (node->right == NULL) {
        // Encounter the last node
        return node;
    }

    return getMaxNode(node->right);
}

Node *removeNode (Node *node, int data) {
    TreeReturn *searchedNode = searchTree(node, data);

    if (searchTree->status == 0) {
        printf("Não enconrrado");
        return node;
    }

    if (searchedNode->node->left == NULL && searchedNode->node->right == NULL){
        free(searchedNode->node);
        return node;
    }

    if (searchedNode->node->left == NULL && searchedNode->node->right != NULL) {
        Node *tempNode = searchedNode->node->right;
        free(searchedNode->node);
        return tempNode;
    }

    if (searchedNode->node->right == NULL && searchedNode->node->left != NULL) {
        Node *tempNode = searchedNode->node->left;
        free(searchedNode->node);
        return tempNode;
    }

    /*

            50
           /  \
         30    70
        /  \  
      20    40
            /
          35


        50
       /  \
     20    70
       \  
       40
       /
     35
    */

    // Pego o maior nó da subárvore esquerda
    Node *maxNode = getMaxNode(searchedNode->node->left);
    // Assinalo esse valor ao nó encontrado que será deletado
    searchedNode->node->data = maxNode->data;
    // Removo o nó esquerdo com maior valor
    searchedNode->node->left = removeNode(searchedNode->node->left, maxNode->data);

    return node;
}
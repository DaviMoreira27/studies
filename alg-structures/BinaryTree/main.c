#include <stdio.h>
#include <stdlib.h>
#include "binaryTree.h"

int main()
{

    Tree* tree = createTree();

    tree->root = insertTree(tree->root, 17);

    tree->root = insertTree(tree->root, 6);

    tree->root = insertTree(tree->root, 14);

    tree->root = insertTree(tree->root, 12);

    tree->root = insertTree(tree->root, 2);

    invertTree(tree->root);

    printTree(tree->root);

    return 1000;
}

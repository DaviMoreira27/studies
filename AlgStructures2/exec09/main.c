#include <stdio.h>
#include <stdlib.h>

#define MAX_KEYS 3
#define MAX_SUB_NODES 4

typedef struct Node
{
    int id;
    int keys[MAX_KEYS];
    int numKeys;
    struct Node *subNodes[MAX_SUB_NODES];
    int level;
} Node;

int nextId = 1;

Node *createNode(int level)
{
    Node *new = (Node *)malloc(sizeof(Node));
    new->id = nextId++;
    new->numKeys = 0;
    new->level = level;
    for (int i = 0; i < MAX_SUB_NODES; i++)
    {
        new->subNodes[i] = NULL;
    }
    return new;
}

int findPosition(Node *no, int key)
{
    int pos = 0;
    while (pos < no->numKeys && key > no->keys[pos])
    {
        pos++;
    }
    return pos;
}

void insertKeyNode(Node *no, int key)
{
    int pos = findPosition(no, key);
    for (int i = no->numKeys; i > pos; i--)
    {
        no->keys[i] = no->keys[i - 1];
    }
    no->keys[pos] = key;
    no->numKeys++;
}

int isLeaf(Node *no)
{
    for (int i = 0; i < MAX_SUB_NODES; i++)
    {
        if (no->subNodes[i] != NULL)
        {
            return 0;
        }
    }
    return 1;
}

void insert(Node **root, int key)
{
    if (*root == NULL)
    {
        *root = createNode(0);
        insertKeyNode(*root, key);
        return;
    }

    Node *current = *root;
    while (!isLeaf(current))
    {
        int pos = findPosition(current, key);
        if (current->subNodes[pos] == NULL)
        {
            current->subNodes[pos] = createNode(current->level + 1);
        }
        current = current->subNodes[pos];
    }

    if (current->numKeys < MAX_KEYS)
    {
        insertKeyNode(current, key);
    }
    else
    {
        int pos = findPosition(current, key);
        current->subNodes[pos] = createNode(current->level + 1);
        insertKeyNode(current->subNodes[pos], key);
    }
}

void printTree(Node *root)
{
    if (root == NULL)
        return;

    Node *queue[1000];
    int start = 0, end = 0;
    queue[end++] = root;

    while (start < end)
    {
        Node *current = queue[start++];
        printf("Nó %d - Nível %d: chaves {", current->id, current->level);

        for (int i = 0; i < current->numKeys; i++)
        {
            printf("%d", current->keys[i]);
            if (i < current->numKeys - 1)
                printf(", ");
        }

        printf("} filhos [");

        for (int i = 0; i < MAX_SUB_NODES; i++)
        {
            if (current->subNodes[i] != NULL)
            {
                printf("%d", current->subNodes[i]->id);
            }
            else
            {
                printf("-");
            }
            if (i < MAX_SUB_NODES - 1)
                printf(", ");
        }

        printf("]\n");

        // Add children to queue in order they appear
        for (int i = 0; i < MAX_SUB_NODES; i++)
        {
            if (current->subNodes[i] != NULL)
            {
                queue[end++] = current->subNodes[i];
            }
        }
    }
}

void freeTree(Node *root)
{
    if (root == NULL)
        return;
    for (int i = 0; i < MAX_SUB_NODES; i++)
    {
        freeTree(root->subNodes[i]);
    }
    free(root);
}

int main()
{
    Node *root = NULL;
    int n;

    scanf("%d", &n);

    for (int i = 0; i < n; i++)
    {
        int value;
        scanf("%d", &value);
        insert(&root, value);
    }

    printTree(root);
    freeTree(root);

    return 0;
}
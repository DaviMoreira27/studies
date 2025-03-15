// If its not defined, define as DOUBLE_LINKED_LIST
#ifdef DOUBLE_LINKED_LIST
#define DOUBLE_LINKED_LIST

#define TRUE 1
#define FALSE 0

#include <stdio.h>
#include <stdlib.h>

typedef struct Node {
    int data;
    Node *prev;
    Node *next;
} Node;


typedef struct DoubleLinkedList {
    Node *head;
    Node *tail;
    int size;
} List;

List createList(void) {}

Node createNode(int data) {}

int isEmpty (List *list) {}

int getSize(List* list) {}

int searchItem(List *list, int data) {}

List insertHead(List *list, int data) {}

List insertTail(List *list, int data) {}

List insertItem(List *list, int position, int data) {}

List removeItem(List *list, int position) {}

List clearList(List *list) {}

void printList (List *list) {}

#endif 
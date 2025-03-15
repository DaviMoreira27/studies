#include "doubleLinkedList.h"
#include <stdio.h>
#include <stdlib.h>
#include <errno.h>

Node *createNode(int data)
{
    Node *node = (Node *)malloc(sizeof(Node));

    if (node == NULL)
    {
        perror("Error");
        exit(1);
    }
    node->data = data;
    node->prev = NULL;
    node->next = NULL;

    return node;
}

List *createList(void)
{
    List *list = (List *)malloc(sizeof(List));

    if (list == NULL)
    {
        perror("Error:");
        exit(EXIT_FAILURE);
    }

    list->size = 0;
    list->head = NULL;
    list->tail = NULL;

    return list;
}

int isEmpty(List *list)
{
    if (getSize(list) == 0)
    {
        return TRUE;
    }

    return FALSE;
}

int getSize(List *list)
{
    return list->size;
}

List insertHead(List *list, int data)
{

    if (list == NULL)
    {
        perror("Error:");
        exit(EXIT_FAILURE);
    }

    Node *newNode = createNode(data);
    Node *oldHead = list->head;

    if (oldHead != NULL)
    {
        oldHead->next = newNode;
        newNode->prev = oldHead;
    }

    list->head = newNode;
    list->size++;

    return list;
}

List insertTail(List *list, int data)
{

    if (list == NULL)
    {
        perror("Error:");
        exit(EXIT_FAILURE);
    }

    Node *newNode = createNode(data);
    Node *oldTail = list->tail;

    if (oldTail != NULL)
    {
        oldTail->prev = newNode;
        newNode->next = oldTail;
    }

    list->tail = newNode;
    list->size++;
    return list;
}

List insertItem(List *list, unsigned int position, int data)
{
    if (position > getSize(list) + 1)
    {
        printf("The given position is bigger than the list itself");
        exit(EXIT_FAILURE);
    }

    if (position < 0)
    {
        printf("Its not possible to pass a negative position");
        exit(EXIT_FAILURE);
    }

    int initialPosition = 0;
    Node *startList = list->tail;

    if (position == 0)
    {
        return insertTail(list, data);
    }

    if (position == getSize() + 1)
    {
        return insertHead(list, data);
    }

    do
    {
        if (initialPosition == position)
        {
            Node *nextNode = (startList->next)->next;
            Node *newNode = createNode(data);
            startList->next = newNode;
            newNode->prev = startList;
            newNode->next = nextNode->next;
            list->size++;
            break;
        }

        startList = startList->next;
        initialPosition++;

    } while (initialPosition <= position);

    return list;
}

int searchItem(List *list, int data)
{
    if (getSize(list) == 0)
    {
        printf("The list is empty");
        exit(EXIT_FAILURE);
    }

    Node *initialList = list->tail;

    while (initialList->data != data)
    {
        initialList = initialList->next;

        if (data == initialList->data)
        {
            return TRUE;
        }
    }

    return FALSE;
}

List removeItem(List *list, int position)
{
    if (getSize(list) == 0)
    {
        printf("The list is empty");
        exit(EXIT_FAILURE);
    }

    int initialPosition = 0;
    Node *startList = list->tail;

    do
    {
        if (initialPosition == position)
        {
            Node *prevNode = startList->prev;
            Node *nextNode = startList->next;

            if (nextNode != NULL) {
                prevNode->next = nextNode;
            }

            if (prevNode != NULL) {
                nextNode->prev = prevNode;
            }
            break;
        }

        startList = startList->next;
        initialPosition++;

    } while (initialPosition <= position);

    return list;
}

List clearList(List *list) {
    free(list);
    list->size = 0;
    list->tail = NULL;
    list->head = NULL;

    return list;
}


void printList (List *list) {
    if (getSize(list) == 0)
    {
        printf("The list is empty");
        exit(EXIT_FAILURE);
    }

    Node *initialList = list->tail;


    do {
        printf("%i \n", initialList->data)
        initialList = initialList->next;
    } while (initialList->next != NULL)

    exit(EXIT_SUCCESS);
}
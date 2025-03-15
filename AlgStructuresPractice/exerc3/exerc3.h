#ifndef EXERC3_H_INCLUDED
#define EXERC3_H_INCLUDED
#include <stdlib.h>
#include <stdio.h>

#define CAPACITY 10

typedef struct {
    int queueElements[CAPACITY];
    int queueSize, queueHead, queueTail;
} StaticQueue;

typedef struct {
    int listData[CAPACITY];
    int listNext[CAPACITY];
    int listSize, listHead, listTail;
    StaticQueue *queue;
} List;

List* createList();

void startQueue(StaticQueue *queue);

int dequeue(StaticQueue *queue);

void enqueue(StaticQueue *queue, int index);

void startLNSE(List *list);

void inserir(List *list, int data, int index);

int size(List *list);

void imprimir(List *list);

void clear(List *list);

int buscar(List *list, int element);

int remover(List *list, int index);

#endif // EXERC3_H_INCLUDED

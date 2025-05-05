#include <stdio.h>
#include <limits.h>
#include <stdlib.h>


#define Vertex int

typedef struct Node {
    Vertex vertex;
    int weight;
    int visited;
    struct Node *next;
} Node;

struct Graph {
    int V;
    int A;
    Node **adjList;

} typedef Graph;

Graph *initGraph(Vertex v) {
    Graph* g = malloc(sizeof(Graph));
    g->A = 0;
    g->V = v;
    g->adjList = malloc(v * sizeof(Node*));
    for (int i = 0; i < v; i++) {
        g->adjList[i] = NULL;
    }

    return g;
}

void insertVertex(Graph *g, Vertex v, int weight) {
    g->V++;
    Node *newNode = malloc(sizeof(Node));

    newNode->next = NULL;
    newNode->vertex = v;
    newNode->visited = 0;
    newNode->weight = weight;

    if (g->adjList[v]) {
        printf("Vertex already exists");
        exit(EXIT_FAILURE);
    }

    g->adjList[v] = newNode;
    
}

int main() {
    Graph *graph = initGraph(9);
    return 0;
}
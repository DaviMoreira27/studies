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

void insertArc (Graph *g, Vertex x, Vertex y, int weight1, int weight2) {
    if (!g->adjList[x]) {
        insertVertex(g, x, weight1);
    }

    if (!g->adjList[y]) {
        insertVertex(g, y, weight2);
    }

    g->adjList[x]->next = g->adjList[y];
    g->A++;
}

void insertEdge (Graph *g, Vertex x, Vertex y, int weight1, int weight2) {
    insertArc(g, x, y, weight1, weight2);
    insertArc(g, y, x, weight2, weight1);
    g->A++;

}

void removeVertex (Graph *g, Vertex x) {
    if (!g->adjList[x]) {
        printf("Vertex do not exists");
        exit(EXIT_FAILURE);
    }

    for (int i = 0; i < g->V; i++) {
        if (g->adjList[i]->vertex == x) {
            g->adjList[i] = NULL;
        }

        Node *nextNode = g->adjList[i]->next;

        while (nextNode != NULL) {
            if (nextNode->vertex == x) {
                
            }
        }
    }

}

int main() {
    Graph *graph = initGraph(9);
    return 0;
}
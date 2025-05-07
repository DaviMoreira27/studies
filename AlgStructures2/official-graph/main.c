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

void insertVertex(Graph *g, Vertex v) {
    if (g->adjList[v]) {
        printf("Vertex already exists\n");
        exit(EXIT_FAILURE);
    }

    g->adjList[v] = NULL;
}


void insertArc(Graph *g, Vertex x, Vertex y, int weight) {
    if (!g->adjList[x]) {
        insertVertex(g, x);
    }

    if (!g->adjList[y]) {
        insertVertex(g, y);
    }

    Node *newNode = malloc(sizeof(Node));
    newNode->vertex = y;
    newNode->weight = weight;
    newNode->visited = 0;

    newNode->next = g->adjList[x];
    g->adjList[x] = newNode;

    g->A++;
}

void insertEdge (Graph *g, Vertex x, Vertex y, int weight1, int weight2) {
    insertArc(g, x, y, weight1);
    insertArc(g, y, x, weight2);
    g->A++;

}

void removeArc (Graph *g, Vertex x, Vertex y) {
    if (!g->adjList[x]) {
        printf("There is no edge out of x");
        exit(EXIT_FAILURE);
    }

    Node *node = g->adjList[x];
    // Traversing the list
    for (Node *removeNode = g->adjList[x]; removeNode != NULL; removeNode = removeNode->next) {
        if (removeNode->vertex == y) {
            g->A--;
            if (g->adjList[x] == removeNode) { // If its the first in the list
              g->adjList[y] = removeNode->next;
              return;
            }
            node->next = removeNode->next;
            return;
        }
        node = removeNode;
    }

}

void printGraph(Graph *g) {
    for (int i = 0; i < g->V; i++) {
        printf("Vertex %d, arcs: ", i);
        for (Node *print = g->adjList[i]; print != NULL; print = print->next) {
            printf("  -> %d (weigth %d)", print->vertex, print->weight);
        }
        printf("\n");
    }
}

void DFS (Graph *g, Vertex v) {
    // Visits the vertex
    g->adjList[v]->visited = 1;
    printf("Visited Vertex: %d\n", g->adjList[v]->vertex);

    // Traverses the adjancency list of vertex v
    for (Node *visit = g->adjList[v]; visit != NULL; visit = visit->next) {
        if (!g->adjList[visit->vertex]->visited) {
            // if the adjancecy vertex was not visited yet, runs DFS recursively
            DFS(g, visit->vertex);
        }
    }
}

void startDFS (Graph *g) {
    for (int i = 0; i < g->V; i++) {
        g->adjList[i]->visited = 0;
    }
    printf("DFS\n");
    DFS(g, 0);
}

void BFS(Graph *g, Vertex v) {
    
}

void startBFS (Graph *g) {
    for (int i = 0; i < g->V; i++) {
        g->adjList[i]->visited = 0;
    }
    printf("BFS\n");
    BFS(g, 0);
}

int main() {
    int V, A, u, v, w;
    if (scanf("%d", &V) != 1) return 1;
    if (scanf("%d", &A) != 1) return 1;

    Graph* graph = initGraph(V);
    for (int i = 0; i < A; i++) {
        if (scanf("%d %d %d", &u, &v, &w) != 2) return 1;
        insertArc(graph, u, v, w);
    }

    printGraph(graph);
    return 0;
}
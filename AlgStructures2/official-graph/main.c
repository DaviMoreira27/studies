#include <stdio.h>
#include <limits.h>
#include <stdlib.h>
#include <stdbool.h>

#define Vertex int

typedef struct Node {
    Vertex vertex;
    int weight;
    int visited;
    struct Node *next;
} Node;

typedef struct Graph {
    int V;
    int A;
    Node **adjList;
} Graph;

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

void insertArc(Graph *g, Vertex x, Vertex y, int weight) {
    Node *newNode = malloc(sizeof(Node));
    newNode->vertex = y;
    newNode->weight = weight;
    newNode->visited = 0;
    newNode->next = g->adjList[x];
    g->adjList[x] = newNode;
    g->A++;
}

void insertEdge(Graph *g, Vertex x, Vertex y, int weight1, int weight2) {
    insertArc(g, x, y, weight1);
    insertArc(g, y, x, weight2);
    // g->A++; // Removido: já é incrementado em insertArc
}

void removeArc(Graph *g, Vertex x, Vertex y) {
    if (!g->adjList[x]) {
        printf("There is no edge out of x\n");
        exit(EXIT_FAILURE);
    }

    Node *prev = NULL;
    for (Node *curr = g->adjList[x]; curr != NULL; prev = curr, curr = curr->next) {
        if (curr->vertex == y) {
            if (prev == NULL) {
                g->adjList[x] = curr->next;
            } else {
                prev->next = curr->next;
            }
            free(curr);
            g->A--;
            return;
        }
    }
}

void printGraph(Graph *g) {
    for (int i = 0; i < g->V; i++) {
        printf("Vertex %d, arcs:", i);
        for (Node *print = g->adjList[i]; print != NULL; print = print->next) {
            printf("  -> %d (weight %d)", print->vertex, print->weight);
        }
        printf("\n");
    }
}

void DFS(Graph *g, Vertex v) {
    printf("Visited Vertex: %d\n", v);
    Node *list = g->adjList[v];
    for (Node *n = list; n != NULL; n = n->next) {
        if (g->adjList[n->vertex] && !g->adjList[n->vertex]->visited) {
            g->adjList[n->vertex]->visited = 1;
            DFS(g, n->vertex);
        }
    }
}

void startDFS(Graph *g) {
    for (int i = 0; i < g->V; i++) {
        if (g->adjList[i])
            g->adjList[i]->visited = 0;
    }
    printf("DFS\n");
    if (g->adjList[0]) {
        g->adjList[0]->visited = 1;
        DFS(g, 0);
    }
}

void BFS(Graph *g, Vertex v) {
    int *queue = malloc(sizeof(Vertex) * g->V);
    int start = 0, end = 0;

    queue[end++] = v;

    while (start != end) {
        Vertex element = queue[start++];
        if (!g->adjList[element]) continue;
        if (!g->adjList[element]->visited) {
            g->adjList[element]->visited = 1;
            printf("Vertex Visited: %d\n", element);

            for (Node *neighbor = g->adjList[element]; neighbor != NULL; neighbor = neighbor->next) {
                Vertex neighborVertex = neighbor->vertex;
                if (g->adjList[neighborVertex] && !g->adjList[neighborVertex]->visited) {
                    queue[end++] = neighborVertex;
                }
            }
        }
    }

    free(queue);
}

void startBFS(Graph *g) {
    for (int i = 0; i < g->V; i++) {
        if (g->adjList[i])
            g->adjList[i]->visited = 0;
    }
    printf("BFS\n");
    if (g->adjList[0]) {
        BFS(g, 0);
    }
}

bool simplePath(Graph *g, Vertex a, Vertex b, int counter) {
    if (!g->adjList[a] || g->adjList[a]->visited) {
        return false;
    }

    g->adjList[a]->visited = 1;
    printf("Vertex %d: %d\n", counter, a);
    counter++;

    for (Node *n = g->adjList[a]; n != NULL; n = n->next) {
        if (n->vertex == b) {
            printf("Vertex %d: %d\n", counter, b);
            return true;
        }

        if (!g->adjList[n->vertex] || g->adjList[n->vertex]->visited)
            continue;

        if (simplePath(g, n->vertex, b, counter))
            return true;
    }

    return false;
}

int main() {
    int V, A, u, v;
    if (scanf("%d", &V) != 1) return 1;
    if (scanf("%d", &A) != 1) return 1;

    Graph *graph = initGraph(V);
    for (int i = 0; i < A; i++) {
        if (scanf("%d %d", &u, &v) != 2) return 1;
        insertArc(graph, u, v, 5);
    }

    simplePath(graph, 0, 4, 0);
    // printGraph(graph);

    return 0;
}

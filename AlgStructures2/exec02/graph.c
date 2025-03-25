#include <stdio.h>
#include <stdlib.h>

typedef struct {
    int origin;
    int link;
} Arc;

typedef struct {
    int **matrix;
    int numVertex;
    int numArcs;
} Graph;

Graph initGraph(int numVertex) {
    Graph g;
    g.numVertex = numVertex;
    g.numArcs = 0;
    g.matrix = (int **)malloc(numVertex * sizeof(int *));
    for (int i = 0; i < numVertex; i++) {
        g.matrix[i] = (int *)calloc(numVertex, sizeof(int));
    }
    return g;
}

void destroyGraph(Graph *g) {
    for (int i = 0; i < g->numVertex; i++) {
        free(g->matrix[i]);
    }
    free(g->matrix);
    g->numVertex = 0;
    g->numArcs = 0;
}

void insertArc(Graph *g, Arc arc) {
    if (g->matrix[arc.origin][arc.link] == 0) {
        g->matrix[arc.origin][arc.link] = 1;
        g->matrix[arc.link][arc.origin] = 1;
        g->numArcs++;
    }
}

void removeArc(Graph *g, Arc arc) {
    if (g->matrix[arc.origin][arc.link] == 1) {
        g->matrix[arc.origin][arc.link] = 0;
        g->matrix[arc.link][arc.origin] = 0;
        g->numArcs--;
    }
}

void printGraph(Graph g) {
    printf("Total of vertices: %d, total of arcs: %d\n", g.numVertex, g.numArcs);
    for (int i = 0; i < g.numVertex; i++) {
        printf("Vertex %d, arcs:", i);
        int first = 1;
        for (int j = 0; j < g.numVertex; j++) {
            if (g.matrix[i][j]) {
                if (!first) printf(",");
                printf(" %d", j);
                first = 0;
            }
        }
        printf("\n");
    }
}

int main(int argc, char *argv[]) {
    FILE *file = stdin;
    if (!file) {
        printf("Error opening file");
        return 1;
    }
    
    int numVertex;
    fscanf(file, "%d", &numVertex);
    Graph g = initGraph(numVertex);
    
    int origin, link;
    while (fscanf(file, "%d %d", &origin, &link) != EOF) {
        Arc arc = {origin, link};
        insertArc(&g, arc);
    }
    fclose(file);
    
    printGraph(g);
    destroyGraph(&g);
    return 0;
}


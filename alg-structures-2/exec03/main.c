#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

typedef struct {
    int origin;
    int link;
} Arc;

typedef struct {
    int **matrix;
    int *visited;
    int numVertex;
    int numArcs;
} Graph;

Graph initGraph(int numVertex) {
    Graph g;
    g.numVertex = numVertex;
    g.numArcs = 0;
    g.matrix = (int **)malloc(numVertex * sizeof(int *));
    g.visited = (int *)malloc(numVertex * sizeof(int *));
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
    free(g->visited);
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

bool wasVisited (Graph *g, int num) {
    for (int i = 0; i < g->numVertex; i++) {
        if (g->visited[i] == num) {
            return true;
        }
    }

    return false;

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

void initiateBFS(Graph *g) {
    for (int i = 0; i < g->numVertex; i++) {
        g->visited[i] = __INT_MAX__;
    }

}

// More Slow
void BFS(Graph *g) {
    printf("BFS \n");
    int currentIndex = 0;
    for (int i = 0; i < g->numVertex; i++) {
        // verify if it was visited
        if (!wasVisited(g, i)) {
            printf("visited %d", i);
            printf("\n");
            g->visited[currentIndex] = i;
            currentIndex++;
        }
        for (int j = 0; j < g->numVertex; j++) {
            // Verify if it exists and if was already visited
            if (g->matrix[i][j] && !wasVisited(g, j)) {
                printf("visited %d", j);
                printf("\n");
                g->visited[currentIndex] = j;
                currentIndex++;
            }
        }
    }
}

void initiateDFS(Graph *g) {
    for (int i = 0; i < g->numVertex; i++) {
        g->visited[i] = 0;
    }
    printf("DFS \n");
}

// BFS AGAIN
// void BFSOptimized(Graph *g) {
//     printf("DFS \n");
//     for (int i = 0; i < g->numVertex; i++) {
//         if (i == 0) {
//             printf("visited %d \n", i);
//         }
//         for (int j = 0; j < g->numVertex; j++) {
//             if (g->matrix[i][j] == 1) {
//                 printf("visited %d \n", j);
//                 g->matrix[i][j] = 2;
//                 g->matrix[j][i] = 2;
//             }
//         }   
//     }
// }

void DFS(Graph *g, int node) {
    g->visited[node] = 1;
    printf("visited %d \n", node);
    for (int i = 0; i < g->numVertex; i++) {
        if (g->matrix[node][i] == 1 && !g->visited[i]) {
            DFS(g, i);
        }
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
    initiateDFS(&g);
    DFS(&g, 0);
    initiateBFS(&g);
    BFS(&g);
    destroyGraph(&g);
    return 0;
}


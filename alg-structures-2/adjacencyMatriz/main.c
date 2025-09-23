#include <stdio.h>
#include <stdlib.h>

#define Vertex int

typedef struct Link {
    Vertex x;
    Vertex y;
} Link;

typedef struct Graph {
    int **matrix;
    int totalVertex;
    int totalArcs;
} Graph;

Graph initGraph (int totalVertexs) {
    Graph graphConstruct;
    graphConstruct.totalArcs = 0;
    graphConstruct.totalVertex = totalVertexs;

    for(int i = 0; i <= totalVertexs; i++) {
        /*
            Allocating memory for the graph.matrix property that is a bi-dimensional array, the number of
            vertex times the space needed by an int
        */
        graphConstruct.matrix = (int **)malloc(totalVertexs * sizeof(int *));
        for(int j = 0; j <= totalVertexs; j++) {
            // ? Why cant I access like this
            // graphConstruct.matrix[i][j] = 0;
            graphConstruct.matrix[i] = 0;
        }
    }
}

/*
    X -> Line
    Y -> Column
*/

void newLink (Graph graph, Vertex x, Vertex y) {
    // Link newLink = *(Link *)malloc(sizeof(Link));

    // newLink.x = x;
    // newLink.y = y;

    if (graph.matrix[x][y] == 0) {
        graph.matrix[x][y] = 1;
        graph.totalArcs++;
    }
}

void createArc (Graph graph, Link path) {
    printf("ASDASDASDASD");
    newLink(graph, path.x, path.y);
    newLink(graph, path.y, path.x);
}

void removeLink (Graph graph, Vertex x, Vertex y) {
    if (graph.matrix[x][y] == 1) {
        graph.matrix[x][y] = 0;
        graph.totalArcs--;
    }
}

void removeArc (Graph graph, Link path) {
    removeLink(graph, path.x, path.y);
    removeLink(graph, path.y, path.x);
}

void printGraph (Graph graph) {
    for(int i = 0; i <= graph.totalVertex; i++) {
        for(int j = 0; j <= graph.totalVertex; j++) {
            printf("Vertex %d ", graph.matrix[i][j]);
        }
    }
}

int main () {
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
        Link arc = {origin, link};
        createArc(g, arc);
    }
    fclose(file);
    
    // printGraph(g);
    // destroyGraph(&g);
    return 0;
}
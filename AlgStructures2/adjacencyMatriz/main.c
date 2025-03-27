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
    Graph graphConstruct = *(Graph *)malloc(sizeof(Graph));
    graphConstruct.totalArcs = 0;
    graphConstruct.totalVertex = totalVertexs;

    for(int i = 0; i <= totalVertexs; i++) {
        for(int j = 0; j <= totalVertexs; j++) {
            graphConstruct.matrix[i][j] = 0;
        }
    }
}

/*
    X -> Line
    Y -> Column
*/

Link newLink (Graph graph, Vertex x, Vertex y) {
    Link newLink = *(Link *)malloc(sizeof(newLink));

    newLink.x = x;
    newLink.y = y;

    if (graph.matrix[x][y] == 0) {
        graph.matrix[x][y] = 1;
        graph.totalArcs++;
    }

    return newLink;
}

void createArc (Graph graph, Link path) {
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
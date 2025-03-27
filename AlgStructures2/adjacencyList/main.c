#include <stdlib.h>

#define Vertex int 

// Edge -> A double way Arc (Graph)

// Arc -> A link between two Vertex (Digraph)

// Vertex -> Each value of the Graph/Digraph

// Link is of type nodeStruct
typedef struct nodeStruct *Link;

// Each node has a link to the next node
struct nodeStruct {
    Vertex x;
    Link next; 
};

// The graph struct has the total number of vertex, arcs, and a link to the first node 
struct graphStruct {
    int numVertex;
    int numArcs;
    Link *adj;
};

typedef struct graphStruct *Graph;


/*
O que acontece se não inicializar adj[i] = NULL?

Se você simplesmente alocar o array sem inicializar os ponteiros para NULL, o C não garante que os valores dentro do array sejam NULL. Em vez disso, adj[i] pode conter um endereço inválido (lixo de memória). Isso pode causar:
*/
Graph initGraph (int graphSize) {
    Graph graph = *(Graph *)malloc(sizeof(*graph));
    graph->numArcs = 0;
    graph->numVertex = 0;
    graph->adj = (Link *)malloc(sizeof(Link *));
    for (int i = 0; i <= graphSize; i++) {
        graph->adj[i] = NULL;
    }

    return graph;
}


Link newArc (Vertex x, Link next) {
    Link node = (Link)malloc((sizeof(*node)));
    node->next = next;
    node->x = x;
}
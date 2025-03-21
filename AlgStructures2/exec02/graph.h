#include <stdio.h>
#include <stdlib.h>

#define Vertex int
#define Edge Arc

typedef struct {
    Vertex x;
    Vertex y;
} Arc;

typedef struct {
    int size;
    Arc *arcs;
} ArcList;

typedef struct ArcList *Graph;
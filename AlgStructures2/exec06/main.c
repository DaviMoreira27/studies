#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <limits.h>

#define Vertex int

typedef struct NodeS *Node;

struct NodeS {
  Vertex x;
  int weight;
  Node next;
};

struct graph {
  int V;
  int A;
  Node *adj;
  int *inDeg;
  int *outDeg;
};

typedef struct graph *Graph;

typedef struct {
  int vertex;
  int dist;
} PQNode;

typedef struct {
  PQNode *nodes;
  int size;
  int capacity;
} PriorityQueue;

Node newArc(Vertex w, int weight) {
  Node a = malloc(sizeof(*a));
  a->x = w;
  a->weight = weight;
  a->next = NULL;
  return a;
}

Graph initGraph(int V) {
  Graph G = malloc(sizeof(*G));
  G->V = V;
  G->A = 0;
  G->adj = malloc(V * sizeof(Node));
  G->inDeg = calloc(V, sizeof(int));
  G->outDeg = calloc(V, sizeof(int));
  for (int i = 0; i < V; i++) {
    G->adj[i] = NULL;
  }
  return G;
}

void insertArc(Graph G, Vertex v, Vertex w, int weight) {
  Node new = newArc(w, weight);
  new->next = G->adj[v];
  G->adj[v] = new;
  G->A++;
  G->outDeg[v]++;
  G->inDeg[w]++;
}

void destroyGraph(Graph G) {
  for (int i = 0; i < G->V; i++) {
    Node current = G->adj[i];
    while (current) {
      Node temp = current;
      current = current->next;
      free(temp);
    }
  }
  free(G->adj);
  free(G->inDeg);
  free(G->outDeg);
  free(G);
}

PriorityQueue *createPQ(int capacity) {
  PriorityQueue *pq = malloc(sizeof(PriorityQueue));
  pq->nodes = malloc(capacity * sizeof(PQNode));
  pq->size = 0;
  pq->capacity = capacity;
  return pq;
}

void swap(PQNode *a, PQNode *b) {
  PQNode tmp = *a;
  *a = *b;
  *b = tmp;
}

void heapifyUp(PriorityQueue *pq, int idx) {
  while (idx > 0 && pq->nodes[idx].dist < pq->nodes[(idx - 1) / 2].dist) {
    swap(&pq->nodes[idx], &pq->nodes[(idx - 1) / 2]);
    idx = (idx - 1) / 2;
  }
}

void heapifyDown(PriorityQueue *pq, int idx) {
  int smallest = idx;
  int left = 2 * idx + 1, right = 2 * idx + 2;

  if (left < pq->size && pq->nodes[left].dist < pq->nodes[smallest].dist)
    smallest = left;
  if (right < pq->size && pq->nodes[right].dist < pq->nodes[smallest].dist)
    smallest = right;

  if (smallest != idx) {
    swap(&pq->nodes[idx], &pq->nodes[smallest]);
    heapifyDown(pq, smallest);
  }
}

void insertPQ(PriorityQueue *pq, int vertex, int dist) {
  pq->nodes[pq->size].vertex = vertex;
  pq->nodes[pq->size].dist = dist;
  heapifyUp(pq, pq->size);
  pq->size++;
}

PQNode extractMin(PriorityQueue *pq) {
  PQNode min = pq->nodes[0];
  pq->nodes[0] = pq->nodes[--pq->size];
  heapifyDown(pq, 0);
  return min;
}

bool isEmpty(PriorityQueue *pq) {
  return pq->size == 0;
}

void dijkstra(Graph G, int src, int **dist, int **pred) {
  int *d = malloc(G->V * sizeof(int));
  int *p = malloc(G->V * sizeof(int));
  bool *visited = calloc(G->V, sizeof(bool));

  for (int i = 0; i < G->V; i++) {
    d[i] = INT_MAX;
    p[i] = -1;
  }
  d[src] = 0;

  PriorityQueue *pq = createPQ(G->V);
  insertPQ(pq, src, 0);

  while (!isEmpty(pq)) {
    PQNode node = extractMin(pq);
    int u = node.vertex;

    if (visited[u]) continue;
    visited[u] = true;

    for (Node adj = G->adj[u]; adj != NULL; adj = adj->next) {
      int v = adj->x;
      int w = adj->weight;
      if (d[u] != INT_MAX && d[u] + w < d[v]) {
        d[v] = d[u] + w;
        p[v] = u;
        insertPQ(pq, v, d[v]);
      }
    }
  }

  for (int i = 0; i < G->V; i++) {
    dist[src][i] = d[i];
    pred[src][i] = p[i];
  }

  free(d);
  free(p);
  free(visited);
  free(pq->nodes);
  free(pq);
}

bool detectNegativeCycle(Graph G) {
  int *dist = malloc(G->V * sizeof(int));
  for (int i = 0; i < G->V; i++) dist[i] = 0;

  for (int i = 0; i < G->V - 1; i++) {
    for (int u = 0; u < G->V; u++) {
      for (Node adj = G->adj[u]; adj != NULL; adj = adj->next) {
        int v = adj->x;
        int w = adj->weight;
        if (dist[u] != INT_MAX && dist[u] + w < dist[v]) {
          dist[v] = dist[u] + w;
        }
      }
    }
  }

  for (int u = 0; u < G->V; u++) {
    for (Node adj = G->adj[u]; adj != NULL; adj = adj->next) {
      int v = adj->x;
      int w = adj->weight;
      if (dist[u] != INT_MAX && dist[u] + w < dist[v]) {
        free(dist);
        return true;
      }
    }
  }

  free(dist);
  return false;
}

void printPath(int *pred, int u) {
  if (u < 0) return;
  if (pred[u] != -1) {
    printPath(pred, pred[u]);
    printf(" -> ");
  }
  printf("%d", u);
}

int main(void) {
  int V, u, v, w;
  if (scanf("%d", &V) != 1) return 1;

  Graph G = initGraph(V);

  while (scanf("%d %d %d", &u, &v, &w) == 3) {
    insertArc(G, u, v, w);
  }

  if (detectNegativeCycle(G)) {
    printf("Infinity loop.\n");
    destroyGraph(G);
    return 0;
  }

  int **dist = malloc(V * sizeof(int *));
  int **pred = malloc(V * sizeof(int *));
  for (int i = 0; i < V; i++) {
    dist[i] = malloc(V * sizeof(int));
    pred[i] = malloc(V * sizeof(int));
    dijkstra(G, i, dist, pred);
  }

  for (int i = 0; i < V; i++) {
    printf("Node %d:\n", i);
    for (int j = 0; j < V; j++) {
      if (dist[i][j] == INT_MAX) {
        printf("Distance from %d to %d = infinity\n", i, j);
      } else {
        printf("Distance from %d to %d = %d\n", i, j, dist[i][j]);
        if (i != j) {
          printf("Path: ");
          printPath(pred[i], j);
          printf("\n");
        }
      }
    }
    if (i < V - 1) printf("\n");

  }

  for (int i = 0; i < V; i++) {
    free(dist[i]);
    free(pred[i]);
  }
  free(dist);
  free(pred);
  destroyGraph(G);
  return 0;
}
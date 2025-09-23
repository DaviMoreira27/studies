#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

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

void dfsEuler(Graph G, int u, int *path, int *index) {
  Node *head = &G->adj[u];
  while (*head) {
    Vertex v = (*head)->x;
    Node tmp = *head;
    *head = (*head)->next;
    free(tmp);
    dfsEuler(G, v, path, index);
  }
  path[(*index)++] = u;
}

void findEulerianPath(Graph G) {
  int start = -1, end = -1;
  int start_count = 0, end_count = 0;

  for (int i = 0; i < G->V; i++) {
    int out = G->outDeg[i];
    int in = G->inDeg[i];
    if (out - in == 1) {
      start = i;
      start_count++;
    } else if (in - out == 1) {
      end = i;
      end_count++;
    } else if (in != out) {
      printf("An Euler path or cicle does NOT exist.\n");
      return;
    }
  }

  if ((start_count == 0 && end_count == 0) || (start_count == 1 && end_count == 1)) {
    if (start == -1) {
      for (int i = 0; i < G->V; i++) {
        if (G->outDeg[i] > 0) {
          start = i;
          break;
        }
      }
    }

    int *path = malloc((G->A + 1) * sizeof(int));
    int index = 0;

    dfsEuler(G, start, path, &index);

    if (index != G->A + 1) {
      printf("An Euler path or cicle does NOT exist.\n");
      free(path);
      return;
    }

    if (start_count == 0)
      printf("An Euler CICLE exists.\n");
    else
      printf("An Euler PATH exists.\n");

    printf("Euler Path: ");
    for (int i = 0; i < index; i++) {
      printf("%d", path[i]);
      if (i < index - 1) printf(" -> ");
    }
    printf("\n");

    free(path);
  } else {
    printf("An Euler path or cicle does NOT exist.\n");
  }
}

void destroyGraph(Graph G) {
  if (G) {
    for (int i = 0; i < G->V; i++) {
      Node curr = G->adj[i];
      while (curr) {
        Node tmp = curr;
        curr = curr->next;
        free(tmp);
      }
    }
    free(G->adj);
    free(G->inDeg);
    free(G->outDeg);
    free(G);
  }
}

int main(void) {
  int V, A, u, v;
  if (scanf("%d", &V) != 1) return 1;
  if (scanf("%d", &A) != 1) return 1;

  Graph G = initGraph(V);
  for (int i = 0; i < A; i++) {
    if (scanf("%d %d", &u, &v) != 2) return 1;
    insertArc(G, u, v, 1);
  }

  findEulerianPath(G);
  destroyGraph(G);
  return 0;
}

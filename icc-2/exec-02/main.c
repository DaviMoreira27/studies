#include <stdio.h>
#include <stdlib.h>

long long comparisons, changes;

void merge(int a[], int c, int f, int b[]) {
  if (c >= f) return;
  int m = (c + f) / 2;

  int i1 = c;
  int i2 = m + 1;
  int j = 0;

  while (i1 <= m && i2 <= f) {
    comparisons++;
    if (a[i1] <= a[i2]) {
      b[j++] = a[i1++];
      changes++;
    } else {
      b[j++] = a[i2++];
      changes++;
    }
  }

  while (i1 <= m) {
    b[j++] = a[i1++];
    changes++;
  }
  while (i2 <= f) {
    b[j++] = a[i2++];
    changes++;
  }

  for (int k = 0; k < j; k++) {
    a[c + k] = b[k];
    changes++; // copy back to the main array
  }
}

void mergeSort(int v[], int l, int r, int b[]) {
  if (l < r) {
    int m = l + (r - l) / 2;
    mergeSort(v, l, m, b);
    mergeSort(v, m + 1, r, b);
    merge(v, l, r, b);
  }
}

void insertionSort(int a[], int n) {
    comparisons = 0;
    changes = 0;

    for (int i = 1; i < n; i++) {
        int x = a[i];
        changes++;            // count read and write operations that involve elements of the array
        int j = i - 1;

        while (j >= 0) {
            comparisons++;   // count elements comparison
            if (a[j] > x) {
                a[j + 1] = a[j];
                changes++;
                j--;
            } else {
                break;
            }
        }

        a[j + 1] = x;
        changes++;
    }
}

int main() {
    int Q;
    if (scanf("%d", &Q) != 1) return 0;

    int *sizes = (int *)malloc(Q * sizeof(int));
    for (int i = 0; i < Q; i++) {
        if (scanf("%d", &sizes[i]) != 1) sizes[i] = 0;
    }

    for (int q = 0; q < Q; q++) {
        int N = sizes[q];

        int *v = (int *)malloc(N * sizeof(int));
        for (int i = 0; i < N; i++) {
            scanf("%d", &v[i]);
        }

        int *copy = (int *)malloc(N * sizeof(int));
        for (int i = 0; i < N; i++) copy[i] = v[i];

        insertionSort(v, N);
        printf("I %d %lld %lld\n", N, changes, comparisons);

        comparisons = 0;
        changes = 0;
        int *aux = (int *)malloc((N > 0 ? N : 1) * sizeof(int));
        mergeSort(copy, 0, N - 1, aux);
        free(aux);
        printf("M %d %lld %lld\n", N, changes, comparisons);

        free(v);
        free(copy);
    }

    free(sizes);
    return 0;
}

#include <stdio.h>
#include <stdlib.h>

int compara = 0;
int copia = 0;

void quick(int v[], int f, int l) {
  if (f >= l) {
    return;
  }

  int m = (l + f) / 2;
  copia++;
  int pivot = v[m];
  int i = f;
  int j = l;

  while (1) {
    while (v[i] < pivot) {
      compara++;
      i++;
    }
    compara++;

    while (v[j] > pivot) {
      compara++;
      j--;
    }
    compara++;

    if (i >= j) {
      break;
    }

    copia++;
    copia++;
    copia++;
    int aux = v[i];
    v[i] = v[j];
    v[j] = aux;
    i++;
    j--;
  }
  quick(v, f, j);
  quick(v, j + 1, l);
}

void shell(int v[], int n) {
  int gap = 1;

  while (gap <= n) {
    gap *= 2;
  }

  gap = gap / 2 - 1;
  while (gap > 0) {
    for (int i = gap; i < n; i++) {
      copia++;

      int x = v[i];
      int j = i - gap;

      while (j >= 0 && v[j] > x) {
        copia++;
        compara++;
        v[j + gap] = v[j];
        j -= gap;
      }

      if (j >= 0) {
        compara++;
      }

      copia++;
      v[j + gap] = x;
    }

    gap /= 2;
  }
}

int main() {
  int n;

  scanf("%d", &n);

  int *arr = (int *)malloc(n * sizeof(int));
  if (arr == NULL) {
    printf("Memory allocation error\n");
    return 1;
  }

  for (int i = 0; i < n; i++) {
    scanf("%d", &arr[i]);
  }

  int **subarrays = (int **)malloc(n * sizeof(int *));
  if (subarrays == NULL) {
    printf("Memory allocation error\n");
    free(arr);
    return 1;
  }

  for (int i = 0; i < n; i++) {
    subarrays[i] = (int *)malloc((i + 1) * sizeof(int));
    if (subarrays[i] == NULL) {
      printf("Memory allocation error\n");
      for (int j = 0; j < i; j++) {
        free(subarrays[j]);
      }
      free(subarrays);
      free(arr);
      return 1;
    }
    for (int j = 0; j <= i; j++) {
      subarrays[i][j] = arr[j];
    }
  }

  for (int i = 0; i < n; i++) {
    int *subCopy = (int *)malloc((i + 1) * sizeof(int));
    for (int k = 0; k <= i; k++)
      subCopy[k] = subarrays[i][k];

    compara = 0;
    copia = 0;
    quick(subCopy, 0, i);
    int countQuick = copia + compara;
    free(subCopy);

    subCopy = (int *)malloc((i + 1) * sizeof(int));
    for (int k = 0; k <= i; k++)
      subCopy[k] = subarrays[i][k];

    compara = 0;
    copia = 0;
    shell(subCopy, i + 1);
    int countShell = copia + compara;
    free(subCopy);

    if (countQuick > countShell) {
      printf("S ");
      // printf("QUICK COUNT %d \n", countQuick);
      // printf("SHELL COUNT %d \n", countShell);
    }

    if (countShell > countQuick) {
      printf("Q ");
      // printf("SHELL COUNT %d \n", countShell);
      // printf("QUICK COUNT %d \n", countQuick);
    }

    if (countShell == countQuick) {
      printf("- ");
      // printf("SHELL COUNT %d \n", countShell);
      // printf("QUICK COUNT %d \n", countQuick);
    }
  }

  for (int i = 0; i < n; i++) {
    free(subarrays[i]);
  }
  free(subarrays);
  free(arr);

  return 0;
}

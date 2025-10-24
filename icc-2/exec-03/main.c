#include <stdio.h>

void quick(int v[], int f, int l) {
  if (f >= l) {
    return;
  }

  int m = (l + f) / 2;
  int pivot = v[m];
  int i = f;
  int j = l;

  while (1) {
    while (v[i] < pivot) {
      i++;
    }

    while (v[j] > pivot) {
      j--;
    }

    if (i >= j) {
      break;
    }

    int aux = v[i];
    v[i] = v[j];
    v[j] = aux;
    i++;
    j--;
  }
  quick(v, f, j);
  quick(v, j + 1, l);
}


void binarySearch(int v[], int start, int end, int target) {
    if (start > end) {
        printf("%d", 0);
        return;
    }

    int middle = (start + end) / 2;

    if (v[middle] == target) {
        printf("%d", 1);
        return;
    }
    else if (v[middle] > target) {
        return binarySearch(v, start, middle - 1, target);
    }
    else {
        return binarySearch(v, middle + 1, end, target);
    }
}

int main() {
    int n1, n2;

    scanf("%d", &n1);
    int arr1[n1];
    for (int i = 0; i < n1; i++) {
        scanf("%d", &arr1[i]);
    }

    scanf("%d", &n2);
    int arr2[n2];
    for (int i = 0; i < n2; i++) {
        scanf("%d", &arr2[i]);
    }

    quick(arr1, 0, n1 - 1);
    for (int i = 0; i < n2; i++) {
        binarySearch(arr1, 0, n1 - 1, arr2[i]);
        if (i != n2 - 1) {
            printf("\n");
        }
    }

    printf("\n");
    return 0;
}

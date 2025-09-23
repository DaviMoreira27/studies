#include <stdio.h>
#include <string.h>

void selectionSort(int array[], int n) {
    int i, j, minIndex, temp;

    for (i = 0; i < n - 1; i++) {
        minIndex = i;
        for (j = i + 1; j < n; j++) {
            printf("C %d %d\n", minIndex, j);
            if (array[j] < array[minIndex]) {
                minIndex = j;
            }
        }

        printf("T %d %d\n", i, minIndex);
        temp = array[i];
        array[i] = array[minIndex];
        array[minIndex] = temp;
    }
}

void bubbleSort(int array[], int n) {
    int i, j, temp;
    int swapped;

    for (i = 0; i < n - 1; i++) {
        swapped = 0;
        for (j = 0; j < n - 1 - i; j++) {
            printf("C %d %d\n", j, j + 1);
            if (array[j] > array[j + 1]) {
                printf("T %d %d\n", j, j + 1);
                temp = array[j];
                array[j] = array[j + 1];
                array[j + 1] = temp;
                swapped = 1;
            }
        }
        if (!swapped) break;
    }
}


void printArray(int array[], int size) {
    for (int i = 0; i < size; i++) {
        printf("%d ", array[i]);
    }
    printf("\n");
}

int main() {
    char algorithm[10];
    int n, i;

    scanf("%s", algorithm);
    scanf("%d", &n);

    int array[n];

    for (i = 0; i < n; i++) {
        scanf("%d", &array[i]);
    }

    if (strcmp(algorithm, "selecao") == 0) {
        selectionSort(array, n);
    } else {
        bubbleSort(array, n);
    }

    printArray(array, n);

    return 0;
}

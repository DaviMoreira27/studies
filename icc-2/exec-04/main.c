#include <stdio.h>
#include <stdlib.h>

int insertValue(int *table, int size, int value)
{
    int index = value % size;
    int start = index;

    while (table[index] != -1 && table[index] != -2)
    {
        if (table[index] == value)
        {
            return 0;
        }
        index = (index + 1) % size;
        if (index == start)
        {
            return 0;
        }
    }

    table[index] = value;
    return 1;
}

int removeValue(int *table, int size, int value)
{
    int index = value % size;
    int start = index;

    while (table[index] != -1)
    {
        if (table[index] == value)
        {
            table[index] = -2;
            return 1;
        }
        index = (index + 1) % size;
        if (index == start)
        {
            break;
        }
    }
    return 0;
}

int searchValue(int *table, int size, int value)
{
    int index = value % size;
    int start = index;

    while (table[index] != -1)
    {
        if (table[index] == value)
        {
            return index;
        }
        index = (index + 1) % size;
        if (index == start)
        {
            break;
        }
    }
    return -1;
}

int main(void)
{
    int size;
    int *table;
    int i;

    if (scanf("%d", &size) != 1)
    {
        return 1;
    }
    if (size <= 0)
    {
        return 1;
    }

    table = (int *)malloc(size * sizeof(int));
    if (!table)
    {
        return 1;
    }

    for (i = 0; i < size; i++)
    {
        table[i] = -1;
    }

    int n;
    int value;

    if (scanf("%d", &n) != 1)
    {
        return 1;
    }

    for (int k = 0; k < n; k++)
    {
        if (scanf("%d", &value) != 1)
        {
            return 1;
        }
        insertValue(table, size, value);
    }

    int d;

    if (scanf("%d", &d) != 1)
    {
        return 1;
    }

    for (int k = 0; k < d; k++)
    {
        if (scanf("%d", &value) != 1)
        {
            return 1;
        }
        removeValue(table, size, value);
    }

    int b;

    if (scanf("%d", &b) != 1)
    {
        return 1;
    }

    for (int k = 0; k < b; k++)
    {
        if (scanf("%d", &value) != 1)
        {
            return 1;
        }
        int result = searchValue(table, size, value);
        printf("%d", result);
        if (k < b - 1)
        {
            printf(" ");
        }
    }

    printf("\n");

    free(table);
    return 0;
}

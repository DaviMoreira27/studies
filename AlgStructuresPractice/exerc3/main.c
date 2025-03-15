#include "exerc3.h"
#include <stdio.h>
#include <stdlib.h>

int main() {
    List *list = createList();  // Cria e inicializa a lista

    inserir(list, 10, 0);
    inserir(list, 20, 1);
    inserir(list, 30, 2);
    inserir(list, 55, 3);
    inserir(list, 91, 4);
    inserir(list, 32, 5);
    inserir(list, 123, 6);
    inserir(list, 87, 7);
    inserir(list, 5, 9); // Não pode ser inserido

    remover(list, 3);
    printf("BUSCA: %d\n", buscar(list, 30));
    imprimir(list);
    printf("SIZE: %d\n", size(list));
    clear(list);
    printf("SIZE: %d\n", size(list));

    free(list->queue);  // Libera a memória da fila
    free(list);  // Libera a memória da lista
    return 0;
}

#include "exerc3.h"
#include <stdlib.h>
#include <stdio.h>

// função para criar e inicializar a lista
List* createList() {
    List *list = (List *)malloc(sizeof(List));  // Aloca a estrutura List
    list->queue = (StaticQueue *)malloc(sizeof(StaticQueue));  // Aloca a estrutura StaticQueue
    startLNSE(list);  // Inicializa a lista
    return list;
}

// Inicializa a fila estática
void startQueue(StaticQueue *queue) {
    queue->queueSize = 0;
    queue->queueHead = 0;
    queue->queueTail = 0;
    for (int i = 0; i < CAPACITY; i++) {
        queue->queueElements[i] = i;  // Preenche a fila com índices de 0 a CAPACITY-1
    }
    queue->queueSize = CAPACITY;  // Define o tamanho da fila como a capacidade máxima
}

// Remove e retorna o índice do início da fila
int dequeue(StaticQueue *queue) {
    if (queue->queueSize == 0) {
        return -1;  // Retorna -1 se a fila está vazia
    }
    int index = queue->queueElements[queue->queueHead];  // Obtém o índice no início da fila
    queue->queueHead = (queue->queueHead + 1) % CAPACITY;  // Move o cabeçalho circularmente
    queue->queueSize--;
    return index;
}

// Insere um índice no final da fila
void enqueue(StaticQueue *queue, int index) {
    if (queue->queueSize == CAPACITY) {
        return;  // Não faz nada se a fila está cheia
    }
    queue->queueElements[queue->queueTail] = index;  // Adiciona o índice na posição da cauda
    queue->queueTail = (queue->queueTail + 1) % CAPACITY;  // Move a cauda circularmente
    queue->queueSize++;
}

// Inicializa a lista encadeada estática
void startLNSE(List *list) {
    list->listSize = 0;
    list->listHead = -1;
    list->listTail = -1;
    for (int i = 0; i < CAPACITY; i++) {
        list->listNext[i] = -1;  // Inicializa o ponteiro para o próximo de cada posição
        list->listData[i] = -1;  // Define cada posição como vazia
    }
    startQueue(list->queue);  // Inicializa a fila de posições disponíveis
}

// (Demais funções seguem iguais, como `inserir`, `remover`, `buscar`, etc.)


// Insere um elemento na lista em uma posição específica
void inserir(List *list, int data, int index) {
    if (list->listSize >= CAPACITY || index < 0 || index > list->listSize) {
        printf("Erro: nao eh possivel inserir na posicao %d\n", index);
        return;
    }

    int pos = dequeue(list->queue);  // Obtém uma posição livre da fila
    if (pos == -1) {
        printf("Erro: nao ha posicoes disponiveis no array\n");
        return;
    }

    list->listData[pos] = data;  // Armazena o dado na posição livre

    if (index == 0) {  // Insere no início da lista
        list->listNext[pos] = list->listHead;
        list->listHead = pos;
    } else {  // Insere em outra posição
        int prev = list->listHead;
        for (int i = 0; i < index - 1; i++) {
            prev = list->listNext[prev];
        }
        list->listNext[pos] = list->listNext[prev];
        list->listNext[prev] = pos;
    }

    if (index == list->listSize) {  // Atualiza o tail se o elemento foi inserido no final
        list->listTail = pos;
    }

    list->listSize++;
}

// Remove um elemento de uma posição específica da lista
int remover(List *list, int index) {
    if (list->listSize == 0 || index < 0 || index >= list->listSize) {
        printf("Erro: posição inválida\n");
        return -1;
    }

    int pos, prev = -1;
    if (index == 0) {  // Remove o primeiro elemento
        pos = list->listHead;
        list->listHead = list->listNext[pos];
    } else {  // Remove de outra posição
        prev = list->listHead;
        for (int i = 0; i < index - 1; i++) {
            prev = list->listNext[prev];
        }
        pos = list->listNext[prev];
        list->listNext[prev] = list->listNext[pos];
    }

    int removedValue = list->listData[pos];
    enqueue(list->queue, pos);  // Devolve a posição removida para a fila

    list->listData[pos] = -1;
    list->listNext[pos] = -1;

    if (index == list->listSize - 1) {  // Atualiza o tail se o último elemento foi removido
        list->listTail = prev;
    }

    list->listSize--;
    return removedValue;
}

// Busca um elemento específico na lista e retorna sua posição
int buscar(List *list, int element) {
    int current = list->listHead;
    int index = 0;
    while (current != -1) {
        if (list->listData[current] == element) {
            return index;  // Retorna o índice do elemento encontrado
        }
        current = list->listNext[current];
        index++;
    }
    return -1;  // Retorna -1 se o elemento não for encontrado
}

// Retorna o tamanho atual da lista
int size(List *list) {
    return list->listSize;
}

// Limpa a lista, a reiniciando
void clear(List *list) {
    startLNSE(list);
}

void imprimir(List *list) {
    printf("\nImprimir \n");
    int current = list->listHead;
    while (current != -1) {
        // Imprime Todos os dados
        printf("%d ", list->listData[current]);
        current = list->listNext[current];
    }
    printf("\n");

    printf("Head: %d\n", list->listHead);
    printf("Tail: %d\n", list->listTail);

    for (int i = 0; i < CAPACITY; i++) {
        if (list->listData[i] == -1) {
            // Espaço vazio
            printf("[%d]: (-)\n", i);
        } else {
            // Printa nessa ordem: Indice atual, a informação em si, próximo índice (-1 indica que é o ultimo indice com um valor inserido)
            printf("[%d]: %d -> %d\n", i, list->listData[i], list->listNext[i]);
        }
    }
}

#include <stdio.h>
#include <stdlib.h>

// nó da árvore
typedef struct No {
    int elemento;         
    struct No *esq;        
    struct No *dir;       
} No;

// criar novo nó
No* criarNo(int x) {
    No *novo = (No*) malloc(sizeof(No)); 

    novo->elemento = x; 
    novo->esq = NULL;  
    novo->dir = NULL;  

    return novo; 
}

// ve se a arvore esta vazia
int vazia(No *raiz) {
    return raiz == NULL; 
}

// inserir 
No* inserir(No *raiz, int x) {
    if (raiz == NULL) {
        raiz = criarNo(x);
    }
    // menor, insere na esquerda
    else if (x < raiz->elemento) {
        raiz->esq = inserir(raiz->esq, x);
    }
    // maior, insere na direita
    else if (x > raiz->elemento) {
        raiz->dir = inserir(raiz->dir, x);
    }

    return raiz;
}

// pesquisar
int pesquisar(No *raiz, int x) {
    int resp; 

    if (raiz == NULL) {
        resp = 0;
    } else {
        printf("%d ", raiz->elemento);

        if (x == raiz->elemento) {
            resp = 1;
        }
        else if (x < raiz->elemento) {
            resp = pesquisar(raiz->esq, x);
        }
        else {
            resp = pesquisar(raiz->dir, x);
        }
    }

    return resp; 
}

// pré ordem
void preOrdemRec(No *raiz) {
    if (raiz != NULL) {
        printf("%d ", raiz->elemento); // 1. Visita o nó atual
        preOrdemRec(raiz->esq);        // 2. Vai para a esquerda
        preOrdemRec(raiz->dir);        // 3. Vai para a direita
    }
}
void preOrdem(No *raiz) {
    if (vazia(raiz)) {
        printf("V\n");
    } else {
        preOrdemRec(raiz);
        printf("\n");
    }
}

// pós ordem
void posOrdemRec(No *raiz) {
    if (raiz != NULL) {
        posOrdemRec(raiz->esq);        // 1. Vai para a esquerda
        posOrdemRec(raiz->dir);        // 2. Vai para a direita
        printf("%d ", raiz->elemento); // 3. Visita o nó atual
    }
}
void posOrdem(No *raiz) {
    if (vazia(raiz)) {
        printf("V\n");
    } else {
        posOrdemRec(raiz);
        printf("\n");
    }
}

// em ordem
void emOrdemRec(No *raiz) {
    if (raiz != NULL) {
        emOrdemRec(raiz->esq);         // 1. Vai para a esquerda
        printf("%d ", raiz->elemento); // 2. Visita o nó atual
        emOrdemRec(raiz->dir);         // 3. Vai para a direita
    }
}
void emOrdem(No *raiz) {
    if (vazia(raiz)) {
        printf("V\n");
    } else {
        emOrdemRec(raiz);
        printf("\n");
    }
}

// liberar a memória da árvore
void liberar(No *raiz) {
    if (raiz != NULL) {
        liberar(raiz->esq); // Libera primeiro a esquerda
        liberar(raiz->dir); // Libera depois a direita
        free(raiz);         // Libera o nó atual
    }
}

int main() {
    No *raiz = NULL; 

    char comando[10];

    while (scanf("%s", comando) != EOF) {

        // insere um número
        if (comando[0] == 'I' && comando[1] == '\0') {
            int x; 

            scanf("%d", &x); 

            raiz = inserir(raiz, x); 
        }

        // pesquisa um número
        else if (comando[0] == 'P' && comando[1] == '\0') {
            int y; // Variável para o número pesquisado

            scanf("%d", &y); 

            int encontrou = pesquisar(raiz, y);

            if (encontrou) {
                printf("S\n");
            } else {
                printf("N\n");
            }
        }

        // pré ordem
        else if (
            comando[0] == 'P' &&
            comando[1] == 'R' &&
            comando[2] == 'E' &&
            comando[3] == '\0'
        ) {
            preOrdem(raiz);
        }

        // pós ordem
        else if (
            comando[0] == 'P' &&
            comando[1] == 'O' &&
            comando[2] == 'S' &&
            comando[3] == '\0'
        ) {
            posOrdem(raiz);
        }

        // em ordem
        else if (
            comando[0] == 'E' &&
            comando[1] == 'M' &&
            comando[2] == '\0'
        ) {
            emOrdem(raiz);
        }
    }

    liberar(raiz); 

    return 0; 
}
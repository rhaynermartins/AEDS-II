#include <stdio.h>
#include <stdlib.h>

typedef struct Celula {
    int elemento;              
    struct Celula *prox;       
} Celula;

typedef struct {
    Celula *primeiro;         
    Celula *ultimo;           
} Fila;

Celula* novaCelula(int elemento) {
    Celula *nova = (Celula*) malloc(sizeof(Celula)); // new no java 

    nova->elemento = elemento; 
    nova->prox = NULL;         

    return nova;               
}

void iniciarFila(Fila *fila) {
    fila->primeiro = novaCelula(0); 
    fila->ultimo = fila->primeiro;  
}

int estaVazia(Fila *fila) {
    return fila->primeiro == fila->ultimo; 
}

void enfileirar(Fila *fila, int x) {
    fila->ultimo->prox = novaCelula(x);    
    fila->ultimo = fila->ultimo->prox;     
}

int desenfileirar(Fila *fila) {
    if (estaVazia(fila)) {                 
        return -1;                         
    }

    Celula *removida = fila->primeiro->prox; 
    int elemento = removida->elemento;       

    fila->primeiro->prox = removida->prox;   

    if (removida == fila->ultimo) {          
        fila->ultimo = fila->primeiro;       
    }

    free(removida);                         

    return elemento;                        
}

void mostrar(Fila *fila) {
    if (estaVazia(fila)) {                   
        printf("V\n");                      
    } else {
        Celula *i = fila->primeiro->prox;    

        while (i != NULL) {                  
            printf("%d", i->elemento);      

            if (i->prox != NULL) {           
                printf(" ");                 
            }

            i = i->prox;                     
        }

        printf("\n");                        
    }
}

// Pesquisa um valor na fila
int pesquisar(Fila *fila, int y) {
    Celula *i = fila->primeiro->prox;       

    while (i != NULL) {                      
        if (i->elemento == y) {              
            return 1;                        
        }

        i = i->prox;                         
    }

    return 0;                                
}

void liberarFila(Fila *fila) {
    Celula *i = fila->primeiro;              

    while (i != NULL) {                      
        Celula *tmp = i;                     
        i = i->prox;                         
        free(tmp);                           
    }

    fila->primeiro = NULL;                   
    fila->ultimo = NULL;                     
}

int main() {
    Fila fila;                               
    iniciarFila(&fila);                      

    char operacao;                          
    int valor;                               

    while (scanf(" %c", &operacao) != EOF) { 

        if (operacao == 'E') {               
            scanf("%d", &valor);             
            enfileirar(&fila, valor);        

        } else if (operacao == 'D') {        
            printf("%d\n", desenfileirar(&fila)); 

        } else if (operacao == 'M') {        
            mostrar(&fila);                  

        } else if (operacao == 'P') {        
            scanf("%d", &valor);             

            if (pesquisar(&fila, valor)) {   
                printf("S\n");              
            } else {                        
                printf("N\n");             
            }
        }
    }

    liberarFila(&fila);                      

    return 0;                                
}
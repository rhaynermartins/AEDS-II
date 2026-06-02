#include <stdio.h>   
#include <string.h>  

typedef struct {
    char nome[51]; 
    int peso;      
} Atleta;

int main() {
    Atleta atletas[100]; // max 100 

    int n = 0; 

    // le até o fim do arquivo
    while (scanf("%s %d", atletas[n].nome, &atletas[n].peso) != EOF) {
        n++; 
    }

    // usando bubble sort
    for (int i = 0; i < n - 1; i++) {
        // percorre comparando pares vizinhos
        for (int j = 0; j < n - 1 - i; j++) {
            int trocar = 0;

            // se o atleta atual levantou menos peso que prox, troca
            if (atletas[j].peso < atletas[j + 1].peso) {
                trocar = 1;
            }

            // se os pesos forem iguais, compara os nomes em ordem alfabetica
            else if (atletas[j].peso == atletas[j + 1].peso) {
                
                // strcmp retorna valor positivo se o primeiro nome vem depois do segundo
                if (strcmp(atletas[j].nome, atletas[j + 1].nome) > 0) {
                    trocar = 1;
                }
            }

            // se precisar trocar
            if (trocar == 1) {
                Atleta temp = atletas[j];  // guarda o atual em temp

                atletas[j] = atletas[j + 1];  // coloca o proximo na atual

                atletas[j + 1] = temp;   // Coloca na proxima posição
            }
        }
    }

    // percorre e imprime os ordenados 
    for (int i = 0; i < n; i++) {
        printf("%s %d\n", atletas[i].nome, atletas[i].peso);
    }

    return 0;
}
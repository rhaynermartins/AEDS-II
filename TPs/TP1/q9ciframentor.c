#include <stdio.h> 
#include <stdlib.h> 

// função para verificar se a string é igual a "FIM"
int isFim(char str[]) { 
    return (str[0] == 'F' && str[1] == 'I' && str[2] == 'M' && str[3] == '\0'); // retorna 1 se for "FIM" e 0 caso contrário
}

// função recursiva que aplica o ciframento caractere por caractere
void aplicarCiframento(char str[], int i) { 
    if (str[i] == '\0') { // condição de parada: chegou ao final da string
        return; 
    }

    printf("%c", str[i] + 3); // imprime o caractere atual somado com 3 na tabela ASCII
    aplicarCiframento(str, i + 1); // chama a função novamente para o próximo caractere
}

// função principal do programa
int main() { 
    char msg[1000]; // vetor para armazenar a string digitada pelo usuário

    scanf(" %[^\n]", msg); // lê a primeira linha da entrada, incluindo espaços

    while (!isFim(msg)) { // continua enquanto a string digitada for diferente de "FIM"
        aplicarCiframento(msg, 0); // chama a função recursiva começando no índice 0
        printf("\n"); // imprime uma quebra de linha após o ciframento
        scanf(" %[^\n]", msg); // lê a próxima linha
    }

    return 0; 
}
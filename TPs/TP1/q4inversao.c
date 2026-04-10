#include <stdio.h>  
#include <stdlib.h>  

// 1) calcula manualmente o tamanho da string
int tamanho(char str[]) { // recebe uma string
    int i = 0; 

    while (str[i] != '\0') { // percorre até encontrar o final da string
        i++; 
    }

    return i; // resumo: retorna a quantidade de caracteres
}

// 2) compara manualmente se a string é "FIM"
int ehFim(char str[]) { 
    if (str[0] == 'F' && str[1] == 'I' && str[2] == 'M' && str[3] == '\0') { // verifica caractere por caractere
        return 1; // retorna 1 se for igual a "FIM"
    }

    return 0; // retorna 0 se não for "FIM"
}

// 3) imprime a string invertida
void inverte(char str[]) { 
    int i; // variável de controle
    int tam = tamanho(str); // guarda o tamanho da string

    for (i = tam - 1; i >= 0; i--) { // percorre do último caractere até o primeiro
        printf("%c", str[i]); // imprime cada caractere invertido
    }

    printf("\n"); // pula linha 
}

int main() { // programa principal
    char str[1000]; // vetor para armazenar a string digitada

    scanf(" %[^\n]", str); // lê a primeira linha da entrada, incluindo espaços

    while (!ehFim(str)) { // continua enquanto a string não for "FIM"
        inverte(str); // chama a função e imprime a string invertida
        scanf(" %[^\n]", str); // lê a próxima linha
    }

    return 0; // encerra o programa
}
#include <stdio.h> 

// função recursiva para somar os dígitos da string
int soma(char num[], int i) {
    if (num[i] == '\0') { // caso base, chegou ao final da string
        return 0; // não há mais dígitos para somar
    } else {
        int digito = num[i] - '0'; // converte o caractere atual para inteiro
        return digito + soma(num, i + 1); // soma o dígito atual com o restante da string
    }
}

int main() {
    char entrada[100]; // vetor para armazenar a entrada

    // lê enquanto conseguir ler uma linha da entrada
    while (scanf(" %[^\n]", entrada) == 1) { // só entra no laço se a leitura funcionar
        printf("%d\n", soma(entrada, 0)); // imprime a soma recursiva dos dígitos
    }

    return 0; // encerra o programa
}
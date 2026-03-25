#include <stdio.h>

// função recursiva para somar os dígitos de uma string numérica
int soma(char num[], int i) {
    // caso base: se chegar no final da string, retorna 0
    if(num[i] == '\0') {
        return 0;
    } else {
        int digito = num[i] - '0'; // converte o caractere atual para inteiro
        return digito + soma(num, i + 1); // soma recursivamente
    }
}
int main() {
    char entrada[100];  // criando array para armazenar a entrada do usuário

    // lê a primeira linha de entrada até o caractere '\n'
    scanf(" %[^\n]", entrada);

    // continua até o usuário digitar "FIM"
    while(entrada[0] != 'F' || entrada[1] != 'I' || entrada[2] != 'M' || entrada[3] != '\0') {
        // chama a função recursiva e imprime a soma 
        printf("%d\n", soma(entrada, 0));

        // lê a próxima linha de entrada
        scanf(" %[^\n]", entrada);
    }

    return 0;
}
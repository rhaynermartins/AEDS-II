#include <stdio.h> 

// função que recebe uma string e retorna o tamanho da maior substring sem repetição
int maiorSubstringSemRepeticao(char str[]) {
    int ultimaPosicao[256]; // guarda a última posição em que cada caractere apareceu
    int i; // variável de controle dos laços
    int inicio = 0; // marca o início da janela atual
    int maior = 0; // guarda o maior tamanho encontrado

    // inicializa o vetor com -1, indicando que nenhum caractere apareceu ainda
    for (i = 0; i < 256; i++) {
        ultimaPosicao[i] = -1;
    }

    // percorre a string até encontrar o caractere nulo '\0'
    for (i = 0; str[i] != '\0'; i++) {
        // verifica se o caractere atual já apareceu dentro da janela atual
        if (ultimaPosicao[(unsigned char)str[i]] >= inicio) {
            // move o início da janela para a posição depois da repetição
            inicio = ultimaPosicao[(unsigned char)str[i]] + 1;
        }

        // atualiza a última posição do caractere atual
        ultimaPosicao[(unsigned char)str[i]] = i;

        // calcula o tamanho atual da janela
        int tamanhoAtual = i - inicio + 1;

        // se o tamanho atual for maior que o maior registrado, atualiza
        if (tamanhoAtual > maior) {
            maior = tamanhoAtual;
        }
    }

    // retorna o maior tamanho encontrado
    return maior;
}

int main() {
    char str[1000]; // vetor para armazenar a string digitada

    // lê várias entradas até o fim do arquivo
    while (scanf("%s", str) != EOF) {
        // imprime o tamanho da maior substring sem repetição
        printf("%d\n", maiorSubstringSemRepeticao(str));
    }

    return 0; // encerra o programa
}
#include <stdio.h> 

// 1) função para verificar se a string lida é igual a "FIM"
int isFim(char str[]) { // recebe um vetor de caracteres
    // retorna 1 se a string for exatamente "FIM", senão retorna 0
    return (str[0] == 'F' && str[1] == 'I' && str[2] == 'M' && str[3] == '\0'); 
}

// 2)função que recebe uma string e retorna o tamanho da maior substring sem repetição
int maiorSubstringSemRepeticao(char str[]) { // recebe a string que será analisada
    int ultimaPosicao[256]; // vetor que guarda a última posição em que cada caractere apareceu
    int i; // variável de controle dos laços
    int inicio = 0; // guarda o início da janela atual sem repetição
    int maior = 0; // guarda o maior tamanho encontrado até o momento

    for (i = 0; i < 256; i++) { // percorre todas as posições do vetor ultimaPosicao
        ultimaPosicao[i] = -1; // inicializa todas com -1, indicando que nenhum caractere apareceu ainda
    }

    for (i = 0; str[i] != '\0'; i++) { // percorre a string até encontrar o caractere nulo que marca o fim
        if (ultimaPosicao[str[i]] >= inicio) { // verifica se o caractere atual já apareceu dentro da janela atual
            inicio = ultimaPosicao[str[i]] + 1; // move o início da janela para a posição seguinte da repetição
        }

        ultimaPosicao[str[i]] = i; // atualiza a última posição em que o caractere atual apareceu

        int tamanhoAtual = i - inicio + 1; // calcula o tamanho da substring atual sem repetição

        if (tamanhoAtual > maior) { // verifica se o tamanho atual é maior que o maior já encontrado
            maior = tamanhoAtual; // atualiza o valor do maior tamanho encontrado
        }
    }

    return maior; // retorna o tamanho da maior substring sem repetição
}

int main() { 
    char str[1000]; // vetor de caracteres para armazenar cada string digitada

    scanf("%s", str); // lê a primeira string da entrada

    while (!isFim(str)) { // repete enquanto a string lida não for "FIM"
        printf("%d\n", maiorSubstringSemRepeticao(str)); // imprime o tamanho da maior substring sem repetição da string atual
        scanf("%s", str); // lê a próxima string da entrada
    }

    return 0; // encerra o programa
}
#include <stdio.h> 
#include <stdlib.h> 

// 1) função para verificar se a string é igual a "FIM"
int ehFim(char str[]) {
    return (str[0] == 'F' && str[1] == 'I' && str[2] == 'M' && str[3] == '\0'); // retorna 1 se a string for exatamente "FIM"
}

// função para calcular manualmente o tamanho da string
int tamanho(char str[]) {
    int i = 0; // variável de controle

    while (str[i] != '\0') { // percorre a string até encontrar o caractere de fim
        i++; // incrementa o contador
    }

    return i; // retorna a quantidade de caracteres
}

// 3) função para converter uma letra maiúscula em minúscula
char paraMinusculo(char c) {
    if (c >= 'A' && c <= 'Z') { // verifica se o caractere está entre A e Z
        return c + 32; // converte para minúsculo usando a tabela ASCII
    }

    return c; // se já for minúsculo, retorna o próprio caractere
}

// 4) função que verifica se duas strings são anagramas
int anagrama(char str1[], char str2[]) {
    int i; // variável de controle do primeiro laço
    int j; // variável de controle do segundo laço
    int usado[1000]; // vetor para marcar quais posições da segunda string já foram usadas
    int tam1 = tamanho(str1); // guarda o tamanho da primeira string
    int tam2 = tamanho(str2); // guarda o tamanho da segunda string

    if (tam1 != tam2) { // verifica se as duas strings têm tamanhos diferentes
        return 0; // se tiverem tamanhos diferentes, não são anagramas
    }

    for (i = 0; i < 1000; i++) { // percorre todo o vetor usado
        usado[i] = 0; // inicializa todas as posições com 0
    }

    for (i = 0; str1[i] != '\0'; i++) { // percorre cada caractere da primeira string
        int achou = 0; // variável para indicar se encontrou o caractere correspondente na segunda string
        char c1 = paraMinusculo(str1[i]); // converte o caractere atual da primeira string para minúsculo

        for (j = 0; str2[j] != '\0'; j++) { // percorre a segunda string
            char c2 = paraMinusculo(str2[j]); // converte o caractere atual da segunda string para minúsculo

            if (c1 == c2 && usado[j] == 0) { // verifica se os caracteres são iguais e se a posição da segunda string ainda não foi usada
                usado[j] = 1; // marca a posição da segunda string como usada
                achou = 1; // marca que encontrou correspondência
                j = tam2; // força a saída do laço
            }
        }

        if (achou == 0) { // verifica se não encontrou correspondência para o caractere atual
            return 0; // se não encontrou, então não são anagramas
        }
    }

    return 1; // se todos os caracteres encontraram correspondência, então são anagramas
}

// função principal do programa
int main() {
    char str1[1000]; // vetor para armazenar a primeira string
    char str2[1000]; // vetor para armazenar a segunda string

    scanf("%s", str1); // lê a primeira string da entrada

    while (!ehFim(str1)) { // continua enquanto a primeira string for diferente de "FIM"
        scanf("%s", str2); // lê a segunda string do par

        if (anagrama(str1, str2)) { // chama a função que verifica se as strings são anagramas
            printf("SIM\n"); // imprime SIM se forem anagramas
        } else { 
            printf("NAO\n"); // caso contrario, imprime NAO
        }

        scanf("%s", str1); // lê a próxima primeira string
    }

    return 0; // encerra o programa
}
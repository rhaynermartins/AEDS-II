#include <stdio.h> 
#include <stdlib.h> 

// 1) função para verificar se a string é igual a "FIM"
int isFim(char str[]) { 
    return (str[0] == 'F' && str[1] == 'I' && str[2] == 'M' && str[3] == '\0'); 
}

// 2) função recursiva para verificar se a string tem apenas vogais
int vogaisRec(char str[], int i) { 
    if (str[i] == '\0') return 1; // se chegou ao fim da string, retorna verdadeiro

    if ((str[i] < 'A' || str[i] > 'Z') && (str[i] < 'a' || str[i] > 'z')) return 0; // se não for letra, retorna falso

    if (str[i] != 'a' && str[i] != 'e' && str[i] != 'i' && str[i] != 'o' && str[i] != 'u' &&
        str[i] != 'A' && str[i] != 'E' && str[i] != 'I' && str[i] != 'O' && str[i] != 'U') return 0; // se não for vogal, retorna falso

    return vogaisRec(str, i + 1); // chama a função para o próximo caractere
}

// 3) função para iniciar a recursão das vogais
int vogaisRecBase(char str[]) { 
    if (str[0] == '\0') return 0; // evita considerar string vazia como válida
    return vogaisRec(str, 0); // chama a função principal a partir da posição 0
}

// 4) função recursiva para verificar se a string tem apenas consoantes
int consoantesRec(char str[], int i) { 
    if (str[i] == '\0') return 1; // se chegou ao fim da string, retorna verdadeiro

    if ((str[i] < 'A' || str[i] > 'Z') && (str[i] < 'a' || str[i] > 'z')) return 0; // se não for letra, retorna falso

    if (str[i] == 'a' || str[i] == 'e' || str[i] == 'i' || str[i] == 'o' || str[i] == 'u' ||
        str[i] == 'A' || str[i] == 'E' || str[i] == 'I' || str[i] == 'O' || str[i] == 'U') return 0; // se for vogal, retorna falso

    return consoantesRec(str, i + 1); // chama a função para o próximo caractere
}

// 5) função para iniciar a recursão das consoantes
int consoantesRecBase(char str[]) { 
    if (str[0] == '\0') return 0; // evita considerar string vazia como válida
    return consoantesRec(str, 0); // chama a função principal a partir da posição 0
}

// 6) função recursiva para verificar se a string representa um número inteiro
int inteirosRec(char str[], int i) { 
    if (str[i] == '\0') return 1; // se chegou ao fim da string, retorna verdadeiro

    if (str[i] < '0' || str[i] > '9') return 0; // se o caractere atual não for dígito, retorna falso

    return inteirosRec(str, i + 1); // chama a função para o próximo caractere
}

// 7) função para iniciar a recursão dos inteiros
int inteirosRecBase(char str[]) { 
    if (str[0] == '\0') return 0; // evita considerar string vazia como válida
    return inteirosRec(str, 0); // chama a função principal a partir da posição 0
}

// 8) função recursiva para verificar se a string representa um número real
int reaisRec(char str[], int i, int separador, int digitos) { 
    if (str[i] == '\0') { // se chegou ao fim da string
        return (digitos > 0); // retorna verdadeiro se houver pelo menos um dígito
    }

    if (str[i] == '.' || str[i] == ',') { // verifica se o caractere atual é ponto ou vírgula
        if (separador == 1) return 0; // se já apareceu separador antes, retorna falso
        return reaisRec(str, i + 1, 1, digitos); // marca que já encontrou um separador e continua
    }

    if (str[i] >= '0' && str[i] <= '9') { // verifica se o caractere atual é dígito
        return reaisRec(str, i + 1, separador, digitos + 1); // incrementa a quantidade de dígitos e continua
    }

    return 0; // se não for dígito nem separador, retorna falso
}

// 9) função para iniciar a recursão dos reais
int reaisRecBase(char str[]) { 
    if (str[0] == '\0') return 0; // evita considerar string vazia como válida
    return reaisRec(str, 0, 0, 0); // chama a função principal começando do índice 0
}

// função principal do programa
int main() { 
    char str[500]; // vetor para armazenar cada linha lida

    scanf(" %[^\n]", str); // lê a primeira linha da entrada, incluindo espaços

    while (!isFim(str)) { // repete enquanto a string lida não for "FIM"
        printf("%s ", vogaisRecBase(str) ? "SIM" : "NAO"); // imprime SIM se for composta só por vogais
        printf("%s ", consoantesRecBase(str) ? "SIM" : "NAO"); // imprime SIM se for composta só por consoantes
        printf("%s ", inteirosRecBase(str) ? "SIM" : "NAO"); // imprime SIM se representar um número inteiro
        printf("%s\n", reaisRecBase(str) ? "SIM" : "NAO"); // imprime SIM se representar um número real

        scanf(" %[^\n]", str); // lê a próxima linha
    }

    return 0; // encerra o programa
}
#include <stdio.h> 
#include <stdlib.h> 

// função para verificar se a string é igual a "FIM"
int isFim(char str[]) { 
    return (str[0] == 'F' && str[1] == 'I' && str[2] == 'M' && str[3] == '\0'); // retorna 1 se for "FIM" e 0 caso contrário
}

// função recursiva para verificar se a string tem apenas vogais
int vogaisRec(char str[], int i) { 
    if (str[i] == '\0') return 1; // se chegou ao fim da string, retorna verdadeiro

    if ((str[i] < 'A' || str[i] > 'Z') && (str[i] < 'a' || str[i] > 'z')) return 0; // se não for letra, retorna falso

    if (str[i] != 'a' && str[i] != 'e' && str[i] != 'i' && str[i] != 'o' && str[i] != 'u' &&
        str[i] != 'A' && str[i] != 'E' && str[i] != 'I' && str[i] != 'O' && str[i] != 'U') return 0; // se não for vogal, retorna falso

    return vogaisRec(str, i + 1); // chama a função para o próximo caractere
}

// função auxiliar para iniciar a recursão das vogais
int vogaisRecBase(char str[]) { 
    if (str[0] == '\0') return 0; // evita considerar string vazia como válida
    return vogaisRec(str, 0); // chama a função principal a partir da posição 0
}

// função recursiva para verificar se a string tem apenas consoantes
int consoantesRec(char str[], int i) { 
    if (str[i] == '\0') return 1; // se chegou ao fim da string, retorna verdadeiro

    if ((str[i] < 'A' || str[i] > 'Z') && (str[i] < 'a' || str[i] > 'z')) return 0; // se não for letra, retorna falso

    if (str[i] == 'a' || str[i] == 'e' || str[i] == 'i' || str[i] == 'o' || str[i] == 'u' ||
        str[i] == 'A' || str[i] == 'E' || str[i] == 'I' || str[i] == 'O' || str[i] == 'U') return 0; // se for vogal, retorna falso

    return consoantesRec(str, i + 1); // chama a função para o próximo caractere
}

// função auxiliar para iniciar a recursão das consoantes
int consoantesRecBase(char str[]) { 
    if (str[0] == '\0') return 0; // evita considerar string vazia como válida
    return consoantesRec(str, 0); // chama a função principal a partir da posição 0
}

// função recursiva para verificar se a string representa um número inteiro
int inteirosRec(char str[], int i) { 
    if (str[i] == '\0') return 1; // se chegou ao fim da string, retorna verdadeiro

    if (str[i] < '0' || str[i] > '9') return 0; // se o caractere atual não for dígito, retorna falso

    return inteirosRec(str, i + 1); // chama a função para o próximo caractere
}

int inteirosRecBase(char str[]) { // função auxiliar para iniciar a recursão dos inteiros
    if (str[0] == '\0') return 0; // evita considerar string vazia como válida
    return inteirosRec(str, 0); // chama a função principal a partir da posição 0
}

// função recursiva para verificar se a string representa um número real
int reaisRec(char str[], int i, int separador, int antes, int depois) { 
    if (str[i] == '\0') { // se chegou ao fim da string
        if (separador == 1 && antes > 0 && depois > 0) return 1; // só é real se houver exatamente um separador, pelo menos um dígito antes e pelo menos um depois
        return 0; // caso contrário, retorna falso
    }

    if (str[i] == '.' || str[i] == ',') { // verifica se o caractere atual é ponto ou vírgula
        if (separador == 1) return 0; // se já apareceu separador antes, retorna falso
        return reaisRec(str, i + 1, 1, antes, depois); // marca que já encontrou o separador e continua
    }

    if (str[i] < '0' || str[i] > '9') return 0; // se não for dígito nem separador, retorna falso

    if (separador == 0) { // se ainda não apareceu separador
        return reaisRec(str, i + 1, separador, antes + 1, depois); // conta dígitos antes do separador
    } else { // se já apareceu separador
        return reaisRec(str, i + 1, separador, antes, depois + 1); // conta dígitos depois do separador
    }
}

// função auxiliar para iniciar a recursão dos reais
int reaisRecBase(char str[]) { 
    if (str[0] == '\0') return 0; // evita considerar string vazia como válida
    return reaisRec(str, 0, 0, 0, 0); // chama a função principal começando do índice 0, sem separador e sem dígitos contados
}

// função principal do programa
int main() { 
    char str[500]; // vetor de caracteres para armazenar cada linha lida

    scanf(" %[^\n]", str); // lê a primeira linha da entrada, incluindo espaços

    while (!isFim(str)) { // repete enquanto a string lida não for "FIM"
        printf("%s ", vogaisRecBase(str) ? "SIM" : "NAO"); // imprime SIM se for composta só por vogais, senão imprime NAO
        printf("%s ", consoantesRecBase(str) ? "SIM" : "NAO"); // imprime SIM se for composta só por consoantes, senão imprime NAO
        printf("%s ", inteirosRecBase(str) ? "SIM" : "NAO"); // imprime SIM se representar um número inteiro, senão imprime NAO
        printf("%s\n", reaisRecBase(str) ? "SIM" : "NAO"); // imprime SIM se representar um número real válido, senão imprime NAO

        scanf(" %[^\n]", str); // lê a próxima linha da entrada
    }

    return 0; 
}
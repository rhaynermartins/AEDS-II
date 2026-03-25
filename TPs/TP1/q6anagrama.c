#include <stdio.h>  
#include <stdlib.h>  

// calcula manualmente o tamanho de uma string
int tamanho(char str[]) { 
    int i = 0; 

    while (str[i] != '\0') { // percorre até encontrar o caractere de fim de string
        i++; 
    }

    return i; // resumo: retorna a quantidade de caracteres
}
// verifica manualmente se a string é "FIM"
int ehFim(char str[]) { 
    if (str[0] == 'F' && str[1] == 'I' && str[2] == 'M' && str[3] == '\0') { // compara caractere por caractere
        return 1; // retorna 1 se for "FIM"
    }

    return 0; // retorna 0 se não for "FIM"
}
// converte uma letra maiúscula para minúscula 
char paraMinusculo(char c) { 
    if (c >= 'A' && c <= 'Z') { // verifica se está entre A e Z
        return c + 32; // converte para minúscula usando a tabela ASCII
    }

    return c; // se não for maiúscula, retorna o próprio caractere
}

// verifica se duas strings são anagramas
int recebe(char str[], char str2[]) { // recebe duas strings
    int i, j; 
    int total1 = 0; // contador de caracteres válidos da primeira string
   
    // 1) percorre a primeira string
    for (i = 0; str[i] != '\0'; i++) { 
        char c = paraMinusculo(str[i]); // converte o caractere para minúsculo
        if (c != ' ' && c != '-') { // desconsidera espaços e traços
            total1++; // conta somente os caracteres válidos
        }
    }

    int total2 = 0; // contador de caracteres válidos da segunda string

    // 2) percorre a segunda string
    for (i = 0; str2[i] != '\0'; i++) { 
        char c = paraMinusculo(str2[i]); // converte o caractere para minúsculo
        if (c != ' ' && c != '-') { // desconsidera espaços e traços
            total2++; // conta somente os caracteres válidos
        }
    }

    // se as quantidades forem diferentes, não podem ser anagramas
    if (total1 != total2) { // compara as quantidades de caracteres válidos
        return 0; // retorna falso
    }

    int usado[1000]; // vetor para marcar quais posições da segunda string já foram usadas

    for (i = 0; i < 1000; i++) { // percorre o vetor
        usado[i] = 0; // inicializa todas as posições com 0
    }

    int conta = 0; // contador de caracteres correspondentes encontrados

    // percorre cada caractere da primeira string

    // 1) percorre a primeira string
    for (i = 0; str[i] != '\0'; i++) { 
        char c1 = paraMinusculo(str[i]); // converte o caractere atual para minúsculo

        if (c1 != ' ' && c1 != '-') { // ignora espaços e traços

            // 2) percorre a segunda string
            for (j = 0; str2[j] != '\0'; j++) { 
                char c2 = paraMinusculo(str2[j]); // converte o caractere da segunda string para minúsculo

                // verifica se encontrou o mesmo caractere e se ele ainda não foi usado
                if (c1 == c2 && usado[j] == 0) { // compara os caracteres e verifica se a posição ainda está livre
                    usado[j] = 1; // marca essa posição como usada
                    conta++; // incrementa 
                    j = tamanho(str2); // força a saída do loop
                }
            }
        }
    }

    // resumo: retorna verdadeiro se todos os caracteres válidos da primeira string foram encontrados na segunda
    return conta == total1; // retorna 1 se forem anagramas, ou 0 caso contrário
}

int main() { // programa principal 
    char entrada[1000]; // vetor para armazenar a linha completa lida
    char str1[1000]; // vetor para armazenar a primeira palavra 
    char str2[1000]; // vetor para armazenar a segunda palavra 

    scanf(" %[^\n]", entrada); // lê a primeira linha da entrada, incluindo espaços

    while (!ehFim(entrada)) { // continua enquanto a entrada não for "FIM"
        int i; // variável de controle do laço
        int j1 = 0; // indices para inserir caracteres em str1 e str2
        int j2 = 0; 
        int achouTraco = 0; // variável que indica se já encontrou o traço separador

        // percorre a string entrada
        for (i = 0; entrada[i] != '\0'; i++) { 
            char c = entrada[i]; // pega o caractere atual

            if (c == '-') { // se encontrar o traço 
                achouTraco = 1; // marca que a partir daqui começa a segunda parte

            } else if (c != ' ') { // ignora os espaços
                if (!achouTraco) { // se ainda estiver na primeira parte
                    str1[j1] = c; // coloca o caractere em str1
                    j1++; // avança a posição de inserção em str1

                } else { // se já passou do traço
                    str2[j2] = c; // coloca o caractere em str2
                    j2++; // avança a posição de inserção em str2
                }
            }
        }

        // coloca o fim de string em str1 e str2
        str1[j1] = '\0'; 
        str2[j2] = '\0'; 

        // chama a função que verifica se são anagramas
        if (recebe(str1, str2)) { 
            printf("SIM\n"); // se a função retornar verdadeiro, imprime SIM
        } else { 
            printf("NÃO\n"); // caso contrário, imprime NAO
        }

        scanf(" %[^\n]", entrada); // lê a próxima linha
    }

    return 0;
}
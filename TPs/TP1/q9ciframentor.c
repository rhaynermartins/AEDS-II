#include <stdio.h> 
#include <stdlib.h> 

// 1) função para verificar se a string é igual a "FIM"
int isFim(char str[]) {
    return (str[0] == 'F' && str[1] == 'I' && str[2] == 'M' && str[3] == '\0'); // retorna 1 se a string for exatamente "FIM"
}

// 2) função para remover o caractere '\n' no final da string, se ele existir
void removerEnter(char str[], int i) {
    if (str[i] == '\0') { // se chegou ao fim da string
        return; 
    }

    if (str[i] == '\n') { // verifica se o caractere atual é a quebra de linha
        str[i] = '\0'; // substitui o '\n' pelo fim da string
        return; 
    }

    removerEnter(str, i + 1); // chama a função para o próximo caractere
}

// 3) função recursiva que aplica o ciframento caractere por caractere
void aplicarCiframento(char str[], int i) {
    if (str[i] == '\0') { // condição de parada: chegou ao final da string
        return; 
    }

    printf("%c", str[i] + 3); // imprime o caractere atual deslocado em 3 posições na tabela ASCII
    aplicarCiframento(str, i + 1); // chama a função novamente para o próximo caractere
}

// função principal do programa
int main() {
    char msg[5000]; // vetor para armazenar as entradas

    if (fgets(msg, 5000, stdin) != NULL) { // lê a primeira linha da entrada com segurança
        removerEnter(msg, 0); // remove o '\n' do final da string, se existir

        while (!isFim(msg)) { // continua enquanto a linha lida for diferente de "FIM"
            aplicarCiframento(msg, 0); // chama a função recursiva começando do índice 0
            printf("\n"); // imprime uma quebra de linha após o ciframento

            if (fgets(msg, 5000, stdin) != NULL) { // lê a próxima linha da entrada com segurança
                removerEnter(msg, 0); // remove o '\n' do final da string, se existir
            } else { // se não conseguiu ler mais nenhuma linha
                msg[0] = 'F'; // coloca 'F' na primeira posição
                msg[1] = 'I'; // coloca 'I' na segunda posição
                msg[2] = 'M'; // coloca 'M' na terceira posição
                msg[3] = '\0'; // finaliza a string 
            }
        }
    }

    return 0; // encerra o programa
}
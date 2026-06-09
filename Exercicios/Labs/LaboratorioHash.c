#include <stdio.h>

#define M 11        // area principal
#define R 3         // area de reserva
#define TAM 14      // M + R
#define MAX_NOME 100

//tabela hash com area de reserva
typedef struct {
    char tabela[TAM][MAX_NOME]; // paises
    int ocupado[TAM];           // marca se a posicao esta ocupada
    int nr;                     // qnt de elementos na reserva
} TabelaHash;

// compara strings
int comparar(char a[], char b[]) {
    int i = 0;

    while (a[i] != '\0' && b[i] != '\0') {
        if (a[i] != b[i]) {
            return 0;
        }

        i++;
    }

    return a[i] == '\0' && b[i] == '\0';
}

// copia uma string para outra 
void copiar(char destino[], char origem[]) {
    int i = 0;

    while (origem[i] != '\0') {
        destino[i] = origem[i];
        i++;
    }

    destino[i] = '\0';
}

// calcula a funcao hash somando os codigos dos caracteres
int hash(char chave[]) {
    int soma = 0;

    for (int i = 0; chave[i] != '\0'; i++) {
        soma += chave[i];
    }

    return soma % M;
}

// inicializa a tabela hash
void iniciarTabela(TabelaHash *t) {
    t->nr = 0;

    for (int i = 0; i < TAM; i++) {
        t->ocupado[i] = 0;
        t->tabela[i][0] = '\0';
    }
}

// pesquisa um pais na tabela
int pesquisar(TabelaHash *t, char chave[]) {
    int pos = hash(chave);

    // verifica primeiro a posicao principal
    if (t->ocupado[pos] && comparar(t->tabela[pos], chave)) {
        return pos;
    }

    // procura na area de reserva
    for (int i = M; i < M + t->nr; i++) {
        if (t->ocupado[i] && comparar(t->tabela[i], chave)) {
            return i;
        }
    }

    return -1;
}

// insere um pais 
int inserir(TabelaHash *t, char chave[]) {
    // Nao permite inserir duplicado
    if (pesquisar(t, chave) != -1) {
        return 0;
    }

    int pos = hash(chave);

    if (!t->ocupado[pos]) {
        copiar(t->tabela[pos], chave);
        t->ocupado[pos] = 1;
        return 1;
    }

    // se houve colisao, tenta inserir na reserva
    if (t->nr < R) {
        int posReserva = M + t->nr;

        copiar(t->tabela[posReserva], chave);
        t->ocupado[posReserva] = 1;
        t->nr++;

        return 1;
    }

    // se a reserva estiver cheia, nao insere
    return 0;
}

// procura na reserva algum elemento que pertence ao mesmo hash
int procurarNaReservaMesmoHash(TabelaHash *t, int posHash) {
    for (int i = M; i < M + t->nr; i++) {
        if (t->ocupado[i] && hash(t->tabela[i]) == posHash) {
            return i;
        }
    }

    return -1;
}

// remove uma posicao da reserva e reorganiza os elementos
void removerDaReserva(TabelaHash *t, int pos) {
    for (int i = pos; i < M + t->nr - 1; i++) {
        copiar(t->tabela[i], t->tabela[i + 1]);
        t->ocupado[i] = t->ocupado[i + 1];
    }

    int ultima = M + t->nr - 1;

    t->tabela[ultima][0] = '\0';
    t->ocupado[ultima] = 0;
    t->nr--;
}

// remove um pais
int remover(TabelaHash *t, char chave[]) {
    int pos = hash(chave);

    if (t->ocupado[pos] && comparar(t->tabela[pos], chave)) {
        t->tabela[pos][0] = '\0';
        t->ocupado[pos] = 0;

        // verifica se existe na reserva alguem que deve ocupar essa posicao
        int posReserva = procurarNaReservaMesmoHash(t, pos);

        if (posReserva != -1) {
            copiar(t->tabela[pos], t->tabela[posReserva]);
            t->ocupado[pos] = 1;

            removerDaReserva(t, posReserva);
        }

        return 1;
    }

    // caso o pais esteja na area de reserva
    for (int i = M; i < M + t->nr; i++) {
        if (t->ocupado[i] && comparar(t->tabela[i], chave)) {
            removerDaReserva(t, i);
            return 1;
        }
    }

    return 0;
}

// mostra a tabela 
void mostrar(TabelaHash *t) {
    for (int i = 0; i < TAM; i++) {
        if (i < M) {
            printf("%d Principal ", i);
        } else {
            printf("%d Reserva ", i);
        }

        if (t->ocupado[i]) {
            printf("%s\n", t->tabela[i]);
        } else {
            printf("-\n");
        }
    }
}

int main() {
    TabelaHash tabela;
    iniciarTabela(&tabela);

    char comando;
    char pais[MAX_NOME];

    // ate o fim do arquivo
    while (scanf(" %c", &comando) != EOF) {
        if (comando == 'I') {
            scanf("%s", pais);

            if (!inserir(&tabela, pais)) {
                printf("Erro\n");
            }

        } else if (comando == 'P') {
            scanf("%s", pais);

            if (pesquisar(&tabela, pais) != -1) {
                printf("Sim\n");
            } else {
                printf("Nao\n");
            }

        } else if (comando == 'R') {
            scanf("%s", pais);

            if (!remover(&tabela, pais)) {
                printf("Erro\n");
            }

        } else if (comando == 'M') {
            mostrar(&tabela);
        }
    }

    return 0;
}
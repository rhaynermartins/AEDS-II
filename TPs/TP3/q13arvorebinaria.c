#include <stdio.h>
#include <stdlib.h>

#define MATRICULA "898128" // define a matricula

// struct Data
typedef struct Data {
    int ano; // guarda o ano
    int mes; // guarda o mes
    int dia; // guarda o dia
} Data;

// struct Hora
typedef struct Hora {
    int hora; // guarda a hora
    int minuto; // guarda o minuto
} Hora;

// struct Restaurante
typedef struct Restaurante {
    int id; // guarda o id
    char nome[120]; // guarda o nome
    char cidade[120]; // guarda a cidade
    int capacidade; // guarda a capacidade
    double avaliacao; // guarda a avaliacao
    char tipos_cozinha[20][80]; // guarda os tipos de cozinha
    int quantidade_tipos; // guarda a quantidade de tipos
    int faixa_preco; // guarda a faixa de preco
    Hora horario_abertura; // guarda o horario de abertura
    Hora horario_fechamento; // guarda o horario de fechamento
    Data data_abertura; // guarda a data de abertura
    int aberto; // guarda 1 para true e 0 para false
} Restaurante;

// struct ColecaoRestaurantes
typedef struct ColecaoRestaurantes {
    int tamanho; // guarda a quantidade de restaurantes
    Restaurante** restaurantes; // guarda o vetor de ponteiros para restaurantes
} ColecaoRestaurantes;

// struct No
typedef struct No {
    Restaurante* elemento; // guarda o restaurante
    struct No* esq; // aponta para a esquerda
    struct No* dir; // aponta para a direita
} No;

// struct ArvoreBinaria
typedef struct ArvoreBinaria {
    No* raiz; // guarda a raiz da arvore
    long long comparacoes; // guarda quantidade de comparacoes
} ArvoreBinaria;

// compara dois textos manualmente
int comparar_texto(char* a, char* b) {
    int i = 0; // indice para percorrer os textos

    while (a[i] != '\0' && b[i] != '\0') { // enquanto nenhum texto terminar
        if (a[i] != b[i]) { // se encontrar caractere diferente
            return a[i] - b[i]; // retorna a diferenca
        }

        i++; // avanca para o proximo caractere
    }

    return a[i] - b[i]; // retorna diferenca final
}

// remove \n e \r do final da linha
void limpar_final_linha(char* s) {
    int i = 0; // indice para percorrer a string
    int fim; // indice para voltar do final

    while (s[i] != '\0') { // percorre ate o fim
        i++; // avanca
    }

    fim = i - 1; // posiciona no ultimo caractere

    while (fim >= 0 && (s[fim] == '\n' || s[fim] == '\r')) { // enquanto houver quebra
        s[fim] = '\0'; // remove a quebra
        fim--; // volta uma posicao
    }
}

// verifica se a linha e FIM
int is_fim(char* s) {
    return comparar_texto(s, "FIM") == 0; // retorna 1 se for FIM
}

// parse data
Data parse_data(char* s) {
    Data data; // cria uma data

    sscanf(s, "%d-%d-%d", &data.ano, &data.mes, &data.dia); // le ano, mes e dia

    return data; // retorna data preenchida
}

// formata data
void formatar_data(Data* data, char* buffer) {
    char dia_string[10]; // guarda dia formatado
    char mes_string[10]; // guarda mes formatado

    if (data->dia < 10) { // se dia tiver um digito
        sprintf(dia_string, "0%d", data->dia); // coloca zero na frente
    } else {
        sprintf(dia_string, "%d", data->dia); // escreve normal
    }

    if (data->mes < 10) { // se mes tiver um digito
        sprintf(mes_string, "0%d", data->mes); // coloca zero na frente
    } else {
        sprintf(mes_string, "%d", data->mes); // escreve normal
    }

    sprintf(buffer, "%s/%s/%d", dia_string, mes_string, data->ano); // monta data final
}

// parse hora
Hora parse_hora(char* s) {
    Hora hora; // cria uma hora

    sscanf(s, "%d:%d", &hora.hora, &hora.minuto); // le hora e minuto

    return hora; // retorna hora preenchida
}

// formata hora
void formatar_hora(Hora* hora, char* buffer) {
    char hora_string[10]; // guarda hora formatada
    char minuto_string[10]; // guarda minuto formatado

    if (hora->hora < 10) { // se hora tiver um digito
        sprintf(hora_string, "0%d", hora->hora); // coloca zero na frente
    } else {
        sprintf(hora_string, "%d", hora->hora); // escreve normal
    }

    if (hora->minuto < 10) { // se minuto tiver um digito
        sprintf(minuto_string, "0%d", hora->minuto); // coloca zero na frente
    } else {
        sprintf(minuto_string, "%d", hora->minuto); // escreve normal
    }

    sprintf(buffer, "%s:%s", hora_string, minuto_string); // monta hora final
}

// converte faixa preco para inteiro
int parse_faixa_preco(char* s) {
    if (comparar_texto(s, "$") == 0) { // se for $
        return 1; // retorna 1
    } else if (comparar_texto(s, "$$") == 0) { // se for $$
        return 2; // retorna 2
    } else if (comparar_texto(s, "$$$") == 0) { // se for $$$
        return 3; // retorna 3
    } else if (comparar_texto(s, "$$$$") == 0) { // se for $$$$
        return 4; // retorna 4
    }

    return 0; // retorna 0 se nao encontrar
}

// formata faixa preco
void formatar_faixa_preco(int faixa_preco, char* buffer) {
    int i = 0; // indice

    while (i < faixa_preco) { // repete pela faixa
        buffer[i] = '$'; // coloca $
        i++; // avanca
    }

    buffer[i] = '\0'; // finaliza string
}

// conta tipos de cozinha
int contar_tipos_cozinha(char* s) {
    int i = 0; // indice
    int count = 1; // assume um tipo

    if (s[0] == '\0') { // se string vazia
        return 0; // retorna zero
    }

    while (s[i] != '\0') { // percorre string
        if (s[i] == ';') { // se achar separador
            count++; // aumenta contador
        }

        i++; // avanca
    }

    return count; // retorna quantidade
}

// separa tipos de cozinha
void parse_tipos_cozinha(char* s, Restaurante* restaurante) {
    int i = 0; // percorre string original
    int j = 0; // percorre tipo atual
    int indice_tipo = 0; // indice do tipo atual

    restaurante->quantidade_tipos = contar_tipos_cozinha(s); // guarda quantidade

    while (s[i] != '\0') { // percorre string
        if (s[i] == ';') { // se separador
            restaurante->tipos_cozinha[indice_tipo][j] = '\0'; // finaliza tipo
            indice_tipo++; // vai para proximo tipo
            j = 0; // reinicia indice interno
        } else {
            restaurante->tipos_cozinha[indice_tipo][j] = s[i]; // copia caractere
            j++; // avanca no tipo
        }

        i++; // avanca na string original
    }

    restaurante->tipos_cozinha[indice_tipo][j] = '\0'; // finaliza ultimo tipo
}

// parse restaurante
Restaurante* parse_restaurante(char* s) {
    Restaurante* restaurante = (Restaurante*) malloc(sizeof(Restaurante)); // aloca restaurante

    char tipos[200]; // guarda tipos
    char faixa[10]; // guarda faixa
    char horario[20]; // guarda horario
    char data[20]; // guarda data
    char aberto[10]; // guarda true ou false
    char abertura[10]; // guarda abertura
    char fechamento[10]; // guarda fechamento

    sscanf(
        s,
        "%d,%119[^,],%119[^,],%d,%lf,%199[^,],%9[^,],%19[^,],%19[^,],%9s",
        &restaurante->id,
        restaurante->nome,
        restaurante->cidade,
        &restaurante->capacidade,
        &restaurante->avaliacao,
        tipos,
        faixa,
        horario,
        data,
        aberto
    ); // le campos do csv

    parse_tipos_cozinha(tipos, restaurante); // separa tipos

    restaurante->faixa_preco = parse_faixa_preco(faixa); // converte faixa

    sscanf(horario, "%9[^-]-%9s", abertura, fechamento); // separa abertura e fechamento

    restaurante->horario_abertura = parse_hora(abertura); // converte abertura
    restaurante->horario_fechamento = parse_hora(fechamento); // converte fechamento
    restaurante->data_abertura = parse_data(data); // converte data

    if (comparar_texto(aberto, "true") == 0) { // se for true
        restaurante->aberto = 1; // guarda 1
    } else {
        restaurante->aberto = 0; // guarda 0
    }

    return restaurante; // retorna restaurante
}

// formata tipos de cozinha
void formatar_tipos_cozinha(Restaurante* restaurante, char* buffer) {
    int i; // indice dos tipos
    int j; // indice dos caracteres
    int pos = 0; // posicao do buffer

    buffer[pos] = '['; // abre colchete
    pos++; // avanca

    for (i = 0; i < restaurante->quantidade_tipos; i++) { // percorre tipos
        j = 0; // inicia caractere

        while (restaurante->tipos_cozinha[i][j] != '\0') { // copia tipo
            buffer[pos] = restaurante->tipos_cozinha[i][j]; // copia caractere
            pos++; // avanca buffer
            j++; // avanca tipo
        }

        if (i < restaurante->quantidade_tipos - 1) { // se nao for ultimo
            buffer[pos] = ','; // coloca virgula
            pos++; // avanca
        }
    }

    buffer[pos] = ']'; // fecha colchete
    pos++; // avanca
    buffer[pos] = '\0'; // finaliza
}

// formata restaurante
void formatar_restaurante(Restaurante* restaurante, char* buffer) {
    char tipos_string[300]; // guarda tipos
    char faixa_string[10]; // guarda faixa
    char abertura_string[10]; // guarda abertura
    char fechamento_string[10]; // guarda fechamento
    char data_string[20]; // guarda data

    formatar_tipos_cozinha(restaurante, tipos_string); // formata tipos
    formatar_faixa_preco(restaurante->faixa_preco, faixa_string); // formata faixa
    formatar_hora(&restaurante->horario_abertura, abertura_string); // formata abertura
    formatar_hora(&restaurante->horario_fechamento, fechamento_string); // formata fechamento
    formatar_data(&restaurante->data_abertura, data_string); // formata data

    sprintf(
        buffer,
        "[%d ## %s ## %s ## %d ## %.1lf ## %s ## %s ## %s-%s ## %s ## %s]",
        restaurante->id,
        restaurante->nome,
        restaurante->cidade,
        restaurante->capacidade,
        restaurante->avaliacao,
        tipos_string,
        faixa_string,
        abertura_string,
        fechamento_string,
        data_string,
        restaurante->aberto ? "true" : "false"
    ); // monta saida
}

// le csv e preenche colecao
void ler_csv_colecao(ColecaoRestaurantes* colecao, char* path) {
    FILE* arquivo = fopen(path, "r"); // abre arquivo
    char linha[512]; // guarda linha
    int count = 0; // contador
    int i; // indice

    if (arquivo == NULL) { // se nao abrir
        return; // encerra
    }

    fgets(linha, sizeof(linha), arquivo); // pula cabecalho

    while (fgets(linha, sizeof(linha), arquivo) != NULL) { // conta linhas
        count++; // aumenta contador
    }

    colecao->tamanho = count; // guarda tamanho
    colecao->restaurantes = (Restaurante**) malloc(sizeof(Restaurante*) * count); // aloca vetor

    fclose(arquivo); // fecha arquivo

    arquivo = fopen(path, "r"); // reabre arquivo

    if (arquivo == NULL) { // se nao abrir
        return; // encerra
    }

    fgets(linha, sizeof(linha), arquivo); // pula cabecalho

    for (i = 0; i < count; i++) { // percorre linhas
        fgets(linha, sizeof(linha), arquivo); // le linha
        limpar_final_linha(linha); // limpa linha
        colecao->restaurantes[i] = parse_restaurante(linha); // cria restaurante
    }

    fclose(arquivo); // fecha arquivo
}

// cria colecao e le csv
ColecaoRestaurantes* ler_csv() {
    ColecaoRestaurantes* colecao = (ColecaoRestaurantes*) malloc(sizeof(ColecaoRestaurantes)); // aloca colecao

    colecao->tamanho = 0; // inicia tamanho
    colecao->restaurantes = NULL; // inicia vetor

    ler_csv_colecao(colecao, "/tmp/restaurantes.csv"); // le csv

    return colecao; // retorna colecao
}

// busca restaurante por id
Restaurante* buscar_por_id(ColecaoRestaurantes* colecao, int id) {
    int i; // indice

    for (i = 0; i < colecao->tamanho; i++) { // percorre colecao
        if (colecao->restaurantes[i]->id == id) { // se encontrou id
            return colecao->restaurantes[i]; // retorna restaurante
        }
    }

    return NULL; // retorna NULL se nao encontrar
}

// cria novo no
No* novo_no(Restaurante* restaurante) {
    No* no = (No*) malloc(sizeof(No)); // aloca no

    no->elemento = restaurante; // guarda restaurante
    no->esq = NULL; // inicia esquerda
    no->dir = NULL; // inicia direita

    return no; // retorna no
}

// inicia arvore
void iniciar_arvore(ArvoreBinaria* arvore) {
    arvore->raiz = NULL; // raiz comeca vazia
    arvore->comparacoes = 0; // comparacoes comecam em zero
}

// insere recursivamente
No* inserir_rec(No* no, Restaurante* restaurante, long long* comparacoes) {
    if (no == NULL) { // se chegou em posicao vazia
        no = novo_no(restaurante); // cria novo no
    } else {
        int cmp = comparar_texto(restaurante->nome, no->elemento->nome); // compara nomes
        (*comparacoes)++; // conta comparacao

        if (cmp < 0) { // se nome menor
            no->esq = inserir_rec(no->esq, restaurante, comparacoes); // insere esquerda
        } else if (cmp > 0) { // se nome maior
            no->dir = inserir_rec(no->dir, restaurante, comparacoes); // insere direita
        }
    }

    return no; // retorna no
}

// insere na arvore
void inserir(ArvoreBinaria* arvore, Restaurante* restaurante) {
    arvore->raiz = inserir_rec(arvore->raiz, restaurante, &arvore->comparacoes); // chama recursivo
}

// pesquisa recursivamente imprimindo caminho
int pesquisar_rec(No* no, char* nome, long long* comparacoes) {
    int resp; // resposta

    if (no == NULL) { // se chegou em null
        resp = 0; // nao encontrou
    } else {
        int cmp = comparar_texto(nome, no->elemento->nome); // compara nomes
        (*comparacoes)++; // conta comparacao

        if (cmp == 0) { // se encontrou
            resp = 1; // retorna verdadeiro
        } else if (cmp < 0) { // se deve ir para esquerda
            printf(" esq"); // imprime esq
            resp = pesquisar_rec(no->esq, nome, comparacoes); // pesquisa esquerda
        } else {
            printf(" dir"); // imprime dir
            resp = pesquisar_rec(no->dir, nome, comparacoes); // pesquisa direita
        }
    }

    return resp; // retorna resposta
}

// pesquisa nome
void pesquisar(ArvoreBinaria* arvore, char* nome) {
    int achou; // guarda resultado

    printf("raiz"); // imprime inicio do caminho

    achou = pesquisar_rec(arvore->raiz, nome, &arvore->comparacoes); // pesquisa

    if (achou == 1) { // se encontrou
        printf(" SIM\n"); // imprime SIM
    } else {
        printf(" NAO\n"); // imprime NAO
    }
}

// caminha em ordem recursivamente
void caminhar_central_rec(No* no) {
    char buffer[1000]; // buffer de formatacao

    if (no != NULL) { // se no existe
        caminhar_central_rec(no->esq); // visita esquerda
        formatar_restaurante(no->elemento, buffer); // formata restaurante
        printf("%s\n", buffer); // imprime restaurante
        caminhar_central_rec(no->dir); // visita direita
    }
}

// caminha em ordem
void caminhar_central(ArvoreBinaria* arvore) {
    caminhar_central_rec(arvore->raiz); // chama recursivo
}

// cria log
void criar_log(long long comparacoes) {
    FILE* log = fopen(MATRICULA "_arvore_binaria.txt", "w"); // abre log

    if (log != NULL) { // se abriu
        fprintf(log, "%s\t%lld\t0.0", MATRICULA, comparacoes); // escreve log
        fclose(log); // fecha log
    }
}

// programa principal
int main() {
    ColecaoRestaurantes* colecao = ler_csv(); // le colecao
    ArvoreBinaria arvore; // cria arvore
    int id; // guarda id
    int lixo; // guarda caracteres restantes da linha
    char nome[200]; // guarda nome pesquisado
    Restaurante* restaurante; // guarda restaurante

    iniciar_arvore(&arvore); // inicia arvore

    scanf("%d", &id); // le primeiro id

    while (id != -1) { // enquanto nao for -1
        restaurante = buscar_por_id(colecao, id); // busca restaurante

        if (restaurante != NULL) { // se encontrou
            inserir(&arvore, restaurante); // insere na arvore
        }

        scanf("%d", &id); // le proximo id
    }

    do { // consome tudo que sobrou depois do -1
        lixo = getchar(); // le um caractere
    } while (lixo != '\n' && lixo != EOF); // para na quebra de linha ou fim do arquivo

    fgets(nome, sizeof(nome), stdin); // le primeiro nome completo
    limpar_final_linha(nome); // limpa final da linha

    while (!is_fim(nome)) { // enquanto nao for FIM
        pesquisar(&arvore, nome); // pesquisa o nome completo

        fgets(nome, sizeof(nome), stdin); // le proximo nome completo
        limpar_final_linha(nome); // limpa final da linha
    }

    caminhar_central(&arvore); // mostra arvore em ordem

    criar_log(arvore.comparacoes); // cria arquivo de log

    return 0; 
}
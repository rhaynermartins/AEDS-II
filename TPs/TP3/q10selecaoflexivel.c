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
    Hora horario_abertura; // guarda horario de abertura
    Hora horario_fechamento; // guarda horario de fechamento
    Data data_abertura; // guarda data de abertura
    int aberto; // guarda 1 para true e 0 para false
} Restaurante;

// struct ColecaoRestaurantes
typedef struct ColecaoRestaurantes {
    int tamanho; // guarda quantidade de restaurantes
    Restaurante** restaurantes; // guarda vetor de ponteiros
} ColecaoRestaurantes;

// struct Celula
typedef struct Celula {
    Restaurante* elemento; // guarda o restaurante
    struct Celula* prox; // aponta para a proxima celula
} Celula;

// struct Lista
typedef struct Lista {
    Celula* primeiro; // aponta para a celula cabeca
    Celula* ultimo; // aponta para a ultima celula
    int tamanho; // guarda o tamanho da lista
} Lista;

// compara dois textos manualmente
int comparar_texto(char* a, char* b) {
    int i = 0; // indice

    while (a[i] != '\0' && b[i] != '\0') { // enquanto os dois textos tiverem caracteres
        if (a[i] != b[i]) { // se forem diferentes
            return a[i] - b[i]; // retorna a diferenca
        }

        i++; // avanca
    }

    return a[i] - b[i]; // retorna diferenca final
}

// remove \n e \r do final da linha
void limpar_final_linha(char* s) {
    int i = 0; // indice
    int fim; // indice final

    while (s[i] != '\0') { // vai ate o fim da string
        i++; // avanca
    }

    fim = i - 1; // pega ultimo caractere

    while (fim >= 0 && (s[fim] == '\n' || s[fim] == '\r')) { // enquanto tiver quebra
        s[fim] = '\0'; // remove quebra
        fim--; // volta
    }
}

// parse data
Data parse_data(char* s) {
    Data data; // cria data

    sscanf(s, "%d-%d-%d", &data.ano, &data.mes, &data.dia); // le ano, mes e dia

    return data; // retorna data
}

// formata data
void formatar_data(Data* data, char* buffer) {
    char dia_string[10]; // guarda dia
    char mes_string[10]; // guarda mes

    if (data->dia < 10) { // se dia tiver um digito
        sprintf(dia_string, "0%d", data->dia); // coloca zero
    } else {
        sprintf(dia_string, "%d", data->dia); // escreve normal
    }

    if (data->mes < 10) { // se mes tiver um digito
        sprintf(mes_string, "0%d", data->mes); // coloca zero
    } else {
        sprintf(mes_string, "%d", data->mes); // escreve normal
    }

    sprintf(buffer, "%s/%s/%d", dia_string, mes_string, data->ano); // monta data
}

// parse hora
Hora parse_hora(char* s) {
    Hora hora; // cria hora

    sscanf(s, "%d:%d", &hora.hora, &hora.minuto); // le hora e minuto

    return hora; // retorna hora
}

// formata hora
void formatar_hora(Hora* hora, char* buffer) {
    char hora_string[10]; // guarda hora
    char minuto_string[10]; // guarda minuto

    if (hora->hora < 10) { // se hora tiver um digito
        sprintf(hora_string, "0%d", hora->hora); // coloca zero
    } else {
        sprintf(hora_string, "%d", hora->hora); // escreve normal
    }

    if (hora->minuto < 10) { // se minuto tiver um digito
        sprintf(minuto_string, "0%d", hora->minuto); // coloca zero
    } else {
        sprintf(minuto_string, "%d", hora->minuto); // escreve normal
    }

    sprintf(buffer, "%s:%s", hora_string, minuto_string); // monta hora
}

// converte faixa de preco para inteiro
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

    return 0; // retorna 0
}

// formata faixa de preco
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
    int count = 1; // contador inicial

    if (s[0] == '\0') { // se vazio
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
    int indice_tipo = 0; // indice do tipo

    restaurante->quantidade_tipos = contar_tipos_cozinha(s); // guarda quantidade

    while (s[i] != '\0') { // percorre string
        if (s[i] == ';') { // se separador
            restaurante->tipos_cozinha[indice_tipo][j] = '\0'; // finaliza tipo
            indice_tipo++; // proximo tipo
            j = 0; // reinicia indice
        } else {
            restaurante->tipos_cozinha[indice_tipo][j] = s[i]; // copia caractere
            j++; // avanca
        }

        i++; // avanca
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
    char aberto[10]; // guarda aberto
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
    ); // le os campos do csv

    parse_tipos_cozinha(tipos, restaurante); // separa tipos

    restaurante->faixa_preco = parse_faixa_preco(faixa); // converte faixa

    sscanf(horario, "%9[^-]-%9s", abertura, fechamento); // separa horarios

    restaurante->horario_abertura = parse_hora(abertura); // converte abertura
    restaurante->horario_fechamento = parse_hora(fechamento); // converte fechamento
    restaurante->data_abertura = parse_data(data); // converte data

    if (comparar_texto(aberto, "true") == 0) { // se true
        restaurante->aberto = 1; // guarda 1
    } else {
        restaurante->aberto = 0; // guarda 0
    }

    return restaurante; // retorna restaurante
}

// formata tipos de cozinha
void formatar_tipos_cozinha(Restaurante* restaurante, char* buffer) {
    int i; // indice tipos
    int j; // indice caracteres
    int pos = 0; // posicao buffer

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

    colecao->tamanho = count; // salva tamanho
    colecao->restaurantes = (Restaurante**) malloc(sizeof(Restaurante*) * count); // aloca vetor

    fclose(arquivo); // fecha

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

// busca por id
Restaurante* buscar_por_id(ColecaoRestaurantes* colecao, int id) {
    int i; // indice

    for (i = 0; i < colecao->tamanho; i++) { // percorre colecao
        if (colecao->restaurantes[i]->id == id) { // se encontrou
            return colecao->restaurantes[i]; // retorna restaurante
        }
    }

    return NULL; // retorna NULL
}

// cria nova celula
Celula* nova_celula(Restaurante* restaurante) {
    Celula* celula = (Celula*) malloc(sizeof(Celula)); // aloca celula

    celula->elemento = restaurante; // guarda restaurante
    celula->prox = NULL; // aponta para null

    return celula; // retorna celula
}

// inicia lista com cabeca
void iniciar_lista(Lista* lista) {
    lista->primeiro = nova_celula(NULL); // cria cabeca
    lista->ultimo = lista->primeiro; // ultimo aponta para cabeca
    lista->tamanho = 0; // tamanho zero
}

// insere no fim
void inserir_fim(Lista* lista, Restaurante* restaurante) {
    Celula* tmp = nova_celula(restaurante); // cria celula

    lista->ultimo->prox = tmp; // antigo ultimo aponta para nova
    lista->ultimo = tmp; // atualiza ultimo
    lista->tamanho++; // aumenta tamanho
}

// ordena lista por selecao usando nome
void selecao_lista(Lista* lista, long long* comparacoes, long long* movimentacoes) {
    Celula* i; // ponteiro externo
    Celula* j; // ponteiro interno
    Celula* menor; // ponteiro para menor
    Restaurante* tmp; // auxiliar para troca

    for (i = lista->primeiro->prox; i != NULL; i = i->prox) { // percorre lista
        menor = i; // assume atual como menor

        for (j = i->prox; j != NULL; j = j->prox) { // procura menor no restante
            (*comparacoes)++; // conta comparacao

            if (comparar_texto(j->elemento->nome, menor->elemento->nome) < 0) { // se j menor que menor
                menor = j; // atualiza menor
            }
        }

        if (menor != i) { // se achou menor diferente
            tmp = i->elemento; // guarda elemento de i
            i->elemento = menor->elemento; // coloca menor em i
            menor->elemento = tmp; // coloca antigo i no menor
            (*movimentacoes) += 3; // conta movimentacoes
        }
    }
}

// mostra lista
void mostrar_lista(Lista* lista) {
    Celula* atual = lista->primeiro->prox; // primeiro real
    char buffer[1000]; // buffer

    while (atual != NULL) { // percorre lista
        formatar_restaurante(atual->elemento, buffer); // formata
        printf("%s\n", buffer); // imprime
        atual = atual->prox; // avanca
    }
}

// cria log sem time.h
void criar_log(long long comparacoes, long long movimentacoes) {
    FILE* log = fopen(MATRICULA "_selecao_flexivel.txt", "w"); // abre log

    if (log != NULL) { // se abriu
        fprintf(log, "%s\t%lld\t%lld\t0.0", MATRICULA, comparacoes, movimentacoes); // escreve log com tempo fixo
        fclose(log); // fecha
    }
}

// programa principal
int main() {
    ColecaoRestaurantes* colecao = ler_csv(); // le csv
    Lista lista; // cria lista
    int id; // guarda id
    Restaurante* restaurante; // guarda restaurante
    long long comparacoes = 0; // contador comparacoes
    long long movimentacoes = 0; // contador movimentacoes

    iniciar_lista(&lista); // inicia lista

    scanf("%d", &id); // le primeiro id

    while (id != -1) { // enquanto nao for -1
        restaurante = buscar_por_id(colecao, id); // busca restaurante

        if (restaurante != NULL) { // se encontrou
            inserir_fim(&lista, restaurante); // insere no fim
        }

        scanf("%d", &id); // le proximo id
    }

    selecao_lista(&lista, &comparacoes, &movimentacoes); // ordena por selecao

    mostrar_lista(&lista); // mostra lista ordenada

    criar_log(comparacoes, movimentacoes); // cria arquivo de log

    return 0; 
}
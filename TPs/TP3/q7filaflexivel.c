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

// struct Celula
typedef struct Celula {
    Restaurante* elemento; // guarda o restaurante
    struct Celula* prox; // aponta para a proxima celula
} Celula;

// struct Fila
typedef struct Fila {
    Celula* primeiro; // aponta para a celula cabeca
    Celula* ultimo; // aponta para a ultima celula
    int tamanho; // guarda o tamanho da fila
} Fila;

// compara dois textos manualmente
int comparar_texto(char* a, char* b) {
    int i = 0; // indice para percorrer os textos

    while (a[i] != '\0' && b[i] != '\0') { // enquanto nenhum texto terminar
        if (a[i] != b[i]) { // se encontrar caractere diferente
            return a[i] - b[i]; // retorna a diferenca
        }

        i++; // avanca para o proximo caractere
    }

    return a[i] - b[i]; // retorna a diferenca final
}

// remove \n e \r do final da linha
void limpar_final_linha(char* s) {
    int i = 0; // indice para percorrer a string
    int fim; // indice para voltar do final

    while (s[i] != '\0') { // percorre ate o fim
        i++; // avanca
    }

    fim = i - 1; // pega ultimo caractere valido

    while (fim >= 0 && (s[fim] == '\n' || s[fim] == '\r')) { // enquanto houver quebra
        s[fim] = '\0'; // remove quebra
        fim--; // volta uma posicao
    }
}

// parse data
Data parse_data(char* s) {
    Data data; // cria uma data

    sscanf(s, "%d-%d-%d", &data.ano, &data.mes, &data.dia); // le ano, mes e dia

    return data; // retorna data
}

// formata data
void formatar_data(Data* data, char* buffer) {
    char dia_string[10]; // guarda dia formatado
    char mes_string[10]; // guarda mes formatado

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
    char hora_string[10]; // guarda hora formatada
    char minuto_string[10]; // guarda minuto formatado

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

    buffer[i] = '\0'; // finaliza
}

// conta tipos de cozinha
int contar_tipos_cozinha(char* s) {
    int i = 0; // indice
    int count = 1; // assume um tipo

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
    int i = 0; // indice da string original
    int j = 0; // indice do tipo atual
    int indice_tipo = 0; // indice do vetor de tipos

    restaurante->quantidade_tipos = contar_tipos_cozinha(s); // salva quantidade

    while (s[i] != '\0') { // percorre string original
        if (s[i] == ';') { // se separador
            restaurante->tipos_cozinha[indice_tipo][j] = '\0'; // finaliza tipo
            indice_tipo++; // vai para proximo tipo
            j = 0; // reinicia indice interno
        } else {
            restaurante->tipos_cozinha[indice_tipo][j] = s[i]; // copia caractere
            j++; // avanca no tipo
        }

        i++; // avanca na string
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
        s, // linha original
        "%d,%119[^,],%119[^,],%d,%lf,%199[^,],%9[^,],%19[^,],%19[^,],%9s", // formato csv
        &restaurante->id, // le id
        restaurante->nome, // le nome
        restaurante->cidade, // le cidade
        &restaurante->capacidade, // le capacidade
        &restaurante->avaliacao, // le avaliacao
        tipos, // le tipos
        faixa, // le faixa
        horario, // le horario
        data, // le data
        aberto // le aberto
    );

    parse_tipos_cozinha(tipos, restaurante); // separa tipos

    restaurante->faixa_preco = parse_faixa_preco(faixa); // converte faixa

    sscanf(horario, "%9[^-]-%9s", abertura, fechamento); // separa horario

    restaurante->horario_abertura = parse_hora(abertura); // converte abertura
    restaurante->horario_fechamento = parse_hora(fechamento); // converte fechamento
    restaurante->data_abertura = parse_data(data); // converte data

    if (comparar_texto(aberto, "true") == 0) { // se true
        restaurante->aberto = 1; // salva 1
    } else {
        restaurante->aberto = 0; // salva 0
    }

    return restaurante; // retorna restaurante
}

// formata tipos de cozinha
void formatar_tipos_cozinha(Restaurante* restaurante, char* buffer) {
    int i; // indice dos tipos
    int j; // indice dos caracteres
    int pos = 0; // posicao no buffer

    buffer[pos] = '['; // abre colchete
    pos++; // avanca

    for (i = 0; i < restaurante->quantidade_tipos; i++) { // percorre tipos
        j = 0; // reinicia caractere

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
        buffer, // buffer destino
        "[%d ## %s ## %s ## %d ## %.1lf ## %s ## %s ## %s-%s ## %s ## %s]", // formato
        restaurante->id, // id
        restaurante->nome, // nome
        restaurante->cidade, // cidade
        restaurante->capacidade, // capacidade
        restaurante->avaliacao, // avaliacao
        tipos_string, // tipos
        faixa_string, // faixa
        abertura_string, // abertura
        fechamento_string, // fechamento
        data_string, // data
        restaurante->aberto ? "true" : "false" // aberto
    );
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
        if (colecao->restaurantes[i]->id == id) { // se encontrou
            return colecao->restaurantes[i]; // retorna restaurante
        }
    }

    return NULL; // retorna NULL se nao achar
}

// cria nova celula
Celula* nova_celula(Restaurante* restaurante) {
    Celula* celula = (Celula*) malloc(sizeof(Celula)); // aloca celula

    celula->elemento = restaurante; // salva restaurante
    celula->prox = NULL; // aponta para NULL

    return celula; // retorna celula
}

// inicia fila com celula cabeca
void iniciar_fila(Fila* fila) {
    fila->primeiro = nova_celula(NULL); // cria cabeca
    fila->ultimo = fila->primeiro; // ultimo começa na cabeca
    fila->tamanho = 0; // tamanho zero
}

// insere no fim da fila
void inserir(Fila* fila, Restaurante* restaurante) {
    Celula* tmp = nova_celula(restaurante); // cria nova celula

    fila->ultimo->prox = tmp; // antigo ultimo aponta para nova celula
    fila->ultimo = tmp; // ultimo vira nova celula
    fila->tamanho++; // aumenta tamanho
}

// remove do inicio da fila
Restaurante* remover(Fila* fila) {
    Restaurante* resp = NULL; // guarda removido
    Celula* tmp; // guarda celula removida

    if (fila->tamanho > 0) { // se fila nao estiver vazia
        tmp = fila->primeiro->prox; // pega primeira celula real
        resp = tmp->elemento; // guarda restaurante

        fila->primeiro->prox = tmp->prox; // cabeca pula primeira celula

        if (tmp == fila->ultimo) { // se removeu ultimo elemento
            fila->ultimo = fila->primeiro; // ultimo volta para cabeca
        }

        free(tmp); // libera celula
        fila->tamanho--; // diminui tamanho
    }

    return resp; // retorna restaurante removido
}

// mostra fila do primeiro ao ultimo
void mostrar(Fila* fila) {
    Celula* atual = fila->primeiro->prox; // comeca no primeiro elemento real
    char buffer[1000]; // buffer de formatacao
    int i = 0; // indice

    while (atual != NULL) { // percorre fila
        formatar_restaurante(atual->elemento, buffer); // formata restaurante
        printf("[%d] %s\n", i, buffer); // imprime posicao e restaurante
        atual = atual->prox; // avanca
        i++; // aumenta indice
    }
}

// programa principal
int main() {
    ColecaoRestaurantes* colecao = ler_csv(); // le colecao
    Fila fila; // cria fila
    int id; // guarda id
    int quantidade_operacoes; // guarda quantidade de operacoes
    int i; // indice
    char comando[5]; // guarda comando
    Restaurante* restaurante; // guarda restaurante buscado
    Restaurante* removido; // guarda restaurante removido

    iniciar_fila(&fila); // inicia fila

    scanf("%d", &id); // le primeiro id

    while (id != -1) { // enquanto nao for -1
        restaurante = buscar_por_id(colecao, id); // busca restaurante

        if (restaurante != NULL) { // se encontrou
            inserir(&fila, restaurante); // enfileira
        }

        scanf("%d", &id); // le proximo id
    }

    scanf("%d", &quantidade_operacoes); // le quantidade de operacoes

    for (i = 0; i < quantidade_operacoes; i++) { // percorre operacoes
        scanf("%s", comando); // le comando

        if (comparar_texto(comando, "I") == 0) { // se for inserir
            scanf("%d", &id); // le id
            restaurante = buscar_por_id(colecao, id); // busca restaurante

            if (restaurante != NULL) { // se encontrou
                inserir(&fila, restaurante); // enfileira
            }
        } else if (comparar_texto(comando, "R") == 0) { // se for remover
            removido = remover(&fila); // desenfileira

            if (removido != NULL) { // se removeu
                printf("(R)%s\n", removido->nome); // imprime removido sem espaco
            }
        }
    }

    mostrar(&fila); // imprime fila final

    return 0; 
}
#include <stdio.h>
#include <stdlib.h>

#define MATRICULA "898128" // matricula

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

// celula da lista simplesmente encadeada
typedef struct CelulaLista {
    Restaurante* restaurante; // guarda o ponteiro para restaurante
    struct CelulaLista* prox; // aponta para a proxima celula
} CelulaLista;

// no da arvore do primeiro nivel
typedef struct NoArvore {
    char chave; // guarda o primeiro caractere do nome
    CelulaLista* lista; // aponta para a lista daquele caractere
    struct NoArvore* esq; // aponta para esquerda
    struct NoArvore* dir; // aponta para direita
} NoArvore;

// estrutura principal
typedef struct ArvoreLista {
    NoArvore* raiz; // guarda a raiz da arvore
    long long comparacoes; // conta comparacoes nas pesquisas
} ArvoreLista;

// compara dois textos manualmente
int comparar_texto(char* a, char* b) {
    int i = 0; // indice para percorrer as strings

    while (a[i] != '\0' && b[i] != '\0') { // enquanto nenhuma string terminar
        if (a[i] != b[i]) { // se os caracteres forem diferentes
            return a[i] - b[i]; // retorna a diferenca
        }

        i++; // avanca para o proximo caractere
    }

    return a[i] - b[i]; // retorna diferenca final
}

// remove \n e \r do final da linha
void limpar_final_linha(char* s) {
    int i = 0; // indice para percorrer a string
    int fim; // posicao final

    while (s[i] != '\0') { // percorre ate o fim
        i++; // avanca
    }

    fim = i - 1; // pega o ultimo caractere valido

    while (fim >= 0 && (s[fim] == '\n' || s[fim] == '\r')) { // enquanto tiver quebra de linha
        s[fim] = '\0'; // remove a quebra
        fim--; // volta uma posicao
    }
}

// verifica se o texto e FIM
int is_fim(char* s) {
    return comparar_texto(s, "FIM") == 0; // retorna 1 se for FIM
}

// parse da data
Data parse_data(char* s) {
    Data data; // cria uma data

    sscanf(s, "%d-%d-%d", &data.ano, &data.mes, &data.dia); // le ano, mes e dia

    return data; // retorna a data
}

// formata a data
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

// parse da hora
Hora parse_hora(char* s) {
    Hora hora; // cria uma hora

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

    sprintf(buffer, "%s:%s", hora_string, minuto_string); // monta hora final
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

    return 0; // retorna 0 se nao reconhecer
}

// formata faixa de preco
void formatar_faixa_preco(int faixa_preco, char* buffer) {
    int i = 0; // indice

    while (i < faixa_preco) { // repete conforme a faixa
        buffer[i] = '$'; // coloca $
        i++; // avanca
    }

    buffer[i] = '\0'; // finaliza a string
}

// conta tipos de cozinha
int contar_tipos_cozinha(char* s) {
    int i = 0; // indice
    int count = 1; // contador

    if (s[0] == '\0') { // se estiver vazio
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

    restaurante->quantidade_tipos = contar_tipos_cozinha(s); // salva quantidade

    while (s[i] != '\0') { // percorre texto
        if (s[i] == ';') { // se achar separador
            restaurante->tipos_cozinha[indice_tipo][j] = '\0'; // fecha tipo
            indice_tipo++; // proximo tipo
            j = 0; // reinicia indice interno
        } else {
            restaurante->tipos_cozinha[indice_tipo][j] = s[i]; // copia caractere
            j++; // avanca
        }

        i++; // avanca texto original
    }

    restaurante->tipos_cozinha[indice_tipo][j] = '\0'; // fecha ultimo tipo
}

// cria restaurante a partir da linha do csv
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
    ); // le os campos do csv

    parse_tipos_cozinha(tipos, restaurante); // separa tipos

    restaurante->faixa_preco = parse_faixa_preco(faixa); // converte faixa

    sscanf(horario, "%9[^-]-%9s", abertura, fechamento); // separa horario

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

// formata tipos
void formatar_tipos_cozinha(Restaurante* restaurante, char* buffer) {
    int i; // indice de tipo
    int j; // indice de caractere
    int pos = 0; // posicao no buffer

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
    char tipos_string[300]; // guarda tipos formatados
    char faixa_string[10]; // guarda faixa formatada
    char abertura_string[10]; // guarda abertura formatada
    char fechamento_string[10]; // guarda fechamento formatado
    char data_string[20]; // guarda data formatada

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
    ); // monta saida final
}

// le csv e preenche colecao
void ler_csv_colecao(ColecaoRestaurantes* colecao, char* path) {
    FILE* arquivo = fopen(path, "r"); // abre arquivo
    char linha[512]; // guarda linha
    int count = 0; // contador
    int i; // indice

    if (arquivo == NULL) { // se nao abriu
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

    if (arquivo == NULL) { // se nao abriu
        return; // encerra
    }

    fgets(linha, sizeof(linha), arquivo); // pula cabecalho

    for (i = 0; i < count; i++) { // percorre linhas
        fgets(linha, sizeof(linha), arquivo); // le linha
        limpar_final_linha(linha); // limpa final
        colecao->restaurantes[i] = parse_restaurante(linha); // cria restaurante
    }

    fclose(arquivo); // fecha arquivo
}

// cria colecao e le csv
ColecaoRestaurantes* ler_csv() {
    ColecaoRestaurantes* colecao = (ColecaoRestaurantes*) malloc(sizeof(ColecaoRestaurantes)); // aloca colecao

    colecao->tamanho = 0; // inicia tamanho
    colecao->restaurantes = NULL; // inicia vetor

    ler_csv_colecao(colecao, "/tmp/restaurantes.csv"); // le csv padrao

    return colecao; // retorna colecao
}

// busca restaurante por id
Restaurante* buscar_por_id(ColecaoRestaurantes* colecao, int id) {
    int i; // indice

    for (i = 0; i < colecao->tamanho; i++) { // percorre restaurantes
        if (colecao->restaurantes[i]->id == id) { // se id bate
            return colecao->restaurantes[i]; // retorna restaurante
        }
    }

    return NULL; // retorna NULL se nao encontrar
}

// cria nova celula da lista
CelulaLista* nova_celula_lista(Restaurante* restaurante) {
    CelulaLista* nova = (CelulaLista*) malloc(sizeof(CelulaLista)); // aloca celula

    nova->restaurante = restaurante; // guarda restaurante
    nova->prox = NULL; // inicia proximo como NULL

    return nova; // retorna celula
}

// cria novo no da arvore
NoArvore* novo_no_arvore(char chave) {
    NoArvore* novo = (NoArvore*) malloc(sizeof(NoArvore)); // aloca no

    novo->chave = chave; // guarda chave
    novo->lista = NULL; // inicia lista vazia
    novo->esq = NULL; // inicia esquerda
    novo->dir = NULL; // inicia direita

    return novo; // retorna no
}

// inicia a estrutura
void iniciar_arvore_lista(ArvoreLista* arvore) {
    arvore->raiz = NULL; // inicia raiz vazia
    arvore->comparacoes = 0; // zera comparacoes
}

// insere restaurante ordenado na lista por nome
void inserir_lista_ordenada(CelulaLista** lista, Restaurante* restaurante) {
    CelulaLista* nova = nova_celula_lista(restaurante); // cria celula nova

    if (*lista == NULL || comparar_texto(restaurante->nome, (*lista)->restaurante->nome) < 0) { // se deve inserir no inicio
        nova->prox = *lista; // nova aponta para antigo inicio
        *lista = nova; // nova vira inicio
    } else {
        CelulaLista* atual = *lista; // comeca no inicio

        while (atual->prox != NULL && comparar_texto(restaurante->nome, atual->prox->restaurante->nome) > 0) { // acha posicao
            atual = atual->prox; // avanca
        }

        nova->prox = atual->prox; // nova aponta para proximo
        atual->prox = nova; // atual aponta para nova
    }
}

// insere restaurante na arvore de listas
NoArvore* inserir_arvore(NoArvore* no, Restaurante* restaurante) {
    char chave = restaurante->nome[0]; // pega primeiro caractere do nome

    if (no == NULL) { // se posicao vazia
        no = novo_no_arvore(chave); // cria no
        inserir_lista_ordenada(&no->lista, restaurante); // insere na lista do no
    } else if (chave < no->chave) { // se chave menor
        no->esq = inserir_arvore(no->esq, restaurante); // insere na esquerda
    } else if (chave > no->chave) { // se chave maior
        no->dir = inserir_arvore(no->dir, restaurante); // insere na direita
    } else {
        inserir_lista_ordenada(&no->lista, restaurante); // mesma chave: insere na lista
    }

    return no; // retorna no
}

// insere na estrutura
void inserir(ArvoreLista* arvore, Restaurante* restaurante) {
    arvore->raiz = inserir_arvore(arvore->raiz, restaurante); // chama insercao
}

// pesquisa na lista imprimindo nomes percorridos
Restaurante* pesquisar_lista(CelulaLista* lista, char* nome, long long* comparacoes) {
    CelulaLista* atual = lista; // comeca no inicio da lista
    Restaurante* resp = NULL; // resposta inicial
    int cmp; // guarda a comparacao entre o nome atual e o pesquisado

    while (atual != NULL && resp == NULL) { // percorre enquanto existir celula e nao encontrar
        cmp = comparar_texto(atual->restaurante->nome, nome); // compara nome atual com o nome pesquisado
        (*comparacoes)++; // conta comparacao realizada

        if (cmp < 0) { // se o nome atual vem antes do pesquisado
            printf(" %s", atual->restaurante->nome); // imprime apenas nomes percorridos antes do alvo
            atual = atual->prox; // avanca para o proximo da lista
        } else if (cmp == 0) { // se encontrou o nome exato
            resp = atual->restaurante; // guarda restaurante encontrado sem imprimir o proprio nome
        } else {
            atual = NULL; // para a busca porque a lista esta ordenada e ja passou da posicao
        }
    }

    return resp; // retorna restaurante ou NULL
}

// pesquisa na arvore
Restaurante* pesquisar_arvore(NoArvore* no, char* nome, long long* comparacoes) {
    Restaurante* resp = NULL; // resposta inicial
    char chave = nome[0]; // primeiro caractere do nome pesquisado

    if (no != NULL) { // se no existe
        (*comparacoes)++; // conta comparacao da chave

        if (chave == no->chave) { // se achou no da letra
            resp = pesquisar_lista(no->lista, nome, comparacoes); // pesquisa na lista
        } else if (chave < no->chave) { // se vai esquerda
            printf(" ESQ"); // imprime caminho
            resp = pesquisar_arvore(no->esq, nome, comparacoes); // pesquisa esquerda
        } else {
            printf(" DIR"); // imprime caminho
            resp = pesquisar_arvore(no->dir, nome, comparacoes); // pesquisa direita
        }
    }

    return resp; // retorna restaurante ou NULL
}

// pesquisa nome
void pesquisar(ArvoreLista* arvore, char* nome) {
    Restaurante* resp; // guarda resultado
    char buffer[1000]; // guarda restaurante formatado

    printf("RAIZ"); // imprime inicio da pesquisa

    resp = pesquisar_arvore(arvore->raiz, nome, &arvore->comparacoes); // pesquisa

    if (resp != NULL) { // se encontrou
        formatar_restaurante(resp, buffer); // formata restaurante
        printf(" SIM %s\n", buffer); // imprime SIM e restaurante
    } else {
        printf(" NAO\n"); // imprime NAO
    }
}

// cria arquivo de log
void criar_log(long long comparacoes) {
    FILE* log = fopen(MATRICULA "_hibrida_arvore_lista.txt", "w"); // abre log

    if (log != NULL) { // se abriu
        fprintf(log, "%s\t%lld\t0.0", MATRICULA, comparacoes); // escreve log
        fclose(log); // fecha log
    }
}

// programa principal
int main() {
    ColecaoRestaurantes* colecao = ler_csv(); // le csv
    ArvoreLista arvore; // cria estrutura
    int id; // guarda id
    int lixo; // guarda caractere restante
    char nome[200]; // guarda nome pesquisado
    Restaurante* restaurante; // guarda restaurante encontrado

    iniciar_arvore_lista(&arvore); // inicia estrutura

    scanf("%d", &id); // le primeiro id

    while (id != -1) { // enquanto nao for -1
        restaurante = buscar_por_id(colecao, id); // busca restaurante no dataset

        if (restaurante != NULL) { // se encontrou
            inserir(&arvore, restaurante); // insere na estrutura
        }

        scanf("%d", &id); // le proximo id
    }

    do { // consome resto da linha depois do -1
        lixo = getchar(); // le caractere
    } while (lixo != '\n' && lixo != EOF); // para na quebra ou EOF

    fgets(nome, sizeof(nome), stdin); // le primeiro nome completo
    limpar_final_linha(nome); // limpa final

    while (!is_fim(nome)) { // enquanto nao for FIM
        pesquisar(&arvore, nome); // pesquisa e imprime resultado

        fgets(nome, sizeof(nome), stdin); // le proximo nome
        limpar_final_linha(nome); // limpa final
    }

    criar_log(arvore.comparacoes); // cria log

    return 0; 
}
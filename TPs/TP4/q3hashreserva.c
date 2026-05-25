#include <stdio.h>
#include <stdlib.h>

#define MATRICULA "898128" // matricula
#define TAM_TABELA 31 // tamanho area principal
#define TAM_RESERVA 19 // tamanho area de reserva
#define TAM_TOTAL 50 // tamanho total da tabela

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
    int tamanho; // guarda a quantidade de restaurantes
    Restaurante** restaurantes; // guarda o vetor de ponteiros para restaurantes
} ColecaoRestaurantes;

// struct TabelaHash
typedef struct TabelaHash {
    Restaurante* tabela[TAM_TOTAL]; // guarda os restaurantes da tabela
    int reserva_usada; // guarda quantas posicoes da reserva foram usadas
    long long comparacoes; // guarda comparacoes feitas nas pesquisas
} TabelaHash;

// compara dois textos manualmente
int comparar_texto(char* a, char* b) {
    int i = 0; // indice para percorrer os textos

    while (a[i] != '\0' && b[i] != '\0') { // enquanto os dois textos nao terminarem
        if (a[i] != b[i]) { // se achar caractere diferente
            return a[i] - b[i]; // retorna diferenca
        }

        i++; // avanca
    }

    return a[i] - b[i]; // retorna diferenca final
}

// remove \n e \r do final da linha
void limpar_final_linha(char* s) {
    int i = 0; // indice
    int fim; // ultima posicao valida

    while (s[i] != '\0') { // percorre ate o fim
        i++; // avanca
    }

    fim = i - 1; // pega ultimo caractere

    while (fim >= 0 && (s[fim] == '\n' || s[fim] == '\r')) { // remove quebras
        s[fim] = '\0'; // troca por fim de string
        fim--; // volta uma posicao
    }
}

// verifica se a string e FIM
int is_fim(char* s) {
    return comparar_texto(s, "FIM") == 0; // retorna 1 se for FIM
}

// parse data
Data parse_data(char* s) {
    Data data; // cria data

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

    sprintf(buffer, "%s/%s/%d", dia_string, mes_string, data->ano); // monta data final
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

    return 0; // retorna 0 se nao reconhecer
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
    int count = 1; // contador

    if (s[0] == '\0') { // se estiver vazio
        return 0; // retorna zero
    }

    while (s[i] != '\0') { // percorre texto
        if (s[i] == ';') { // se encontrar separador
            count++; // aumenta quantidade
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

    while (s[i] != '\0') { // percorre texto
        if (s[i] == ';') { // se encontrar separador
            restaurante->tipos_cozinha[indice_tipo][j] = '\0'; // finaliza tipo
            indice_tipo++; // proximo tipo
            j = 0; // reinicia caractere
        } else {
            restaurante->tipos_cozinha[indice_tipo][j] = s[i]; // copia caractere
            j++; // avanca
        }

        i++; // avanca texto original
    }

    restaurante->tipos_cozinha[indice_tipo][j] = '\0'; // finaliza ultimo tipo
}

// cria restaurante a partir da linha do csv
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
    ); // le campos do csv

    parse_tipos_cozinha(tipos, restaurante); // separa tipos

    restaurante->faixa_preco = parse_faixa_preco(faixa); // converte faixa

    sscanf(horario, "%9[^-]-%9s", abertura, fechamento); // separa horario

    restaurante->horario_abertura = parse_hora(abertura); // cria hora abertura
    restaurante->horario_fechamento = parse_hora(fechamento); // cria hora fechamento
    restaurante->data_abertura = parse_data(data); // cria data

    if (comparar_texto(aberto, "true") == 0) { // se for true
        restaurante->aberto = 1; // guarda 1
    } else {
        restaurante->aberto = 0; // guarda 0
    }

    return restaurante; // retorna restaurante criado
}

// formata tipos
void formatar_tipos_cozinha(Restaurante* restaurante, char* buffer) {
    int i; // indice do tipo
    int j; // indice do caractere
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
    ); // monta saida final
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
        count++; // aumenta
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

    for (i = 0; i < colecao->tamanho; i++) { // percorre colecao
        if (colecao->restaurantes[i]->id == id) { // se id bate
            return colecao->restaurantes[i]; // retorna restaurante
        }
    }

    return NULL; // retorna NULL se nao achar
}

// inicia tabela hash
void iniciar_hash(TabelaHash* hash) {
    int i; // indice

    for (i = 0; i < TAM_TOTAL; i++) { // percorre tabela toda
        hash->tabela[i] = NULL; // inicia posicao vazia
    }

    hash->reserva_usada = 0; // inicia reserva vazia
    hash->comparacoes = 0; // inicia comparacoes
}

// soma ASCII do nome
int soma_ascii(char* nome) {
    int soma = 0; // acumulador
    int i = 0; // indice

    while (nome[i] != '\0') { // percorre nome
        soma += nome[i]; // soma codigo ASCII
        i++; // avanca
    }

    return soma; // retorna soma
}

// calcula hash
int h(char* nome) {
    return soma_ascii(nome) % TAM_TABELA; // retorna posicao principal
}

// insere restaurante na hash
void inserir_hash(TabelaHash* hash, Restaurante* restaurante) {
    int pos = h(restaurante->nome); // calcula posicao principal

    if (hash->tabela[pos] == NULL) { // se posicao principal vazia
        hash->tabela[pos] = restaurante; // insere na posicao principal
    } else if (hash->reserva_usada < TAM_RESERVA) { // se tem reserva disponivel
        hash->tabela[TAM_TABELA + hash->reserva_usada] = restaurante; // insere na reserva
        hash->reserva_usada++; // aumenta uso da reserva
    } else {
        printf("%s\n", restaurante->nome); // imprime nome se nao couber
    }
}

// pesquisa restaurante na hash
int pesquisar_hash(TabelaHash* hash, char* nome) {
    int pos = h(nome); // calcula posicao principal
    int i; // indice

    hash->comparacoes++; // conta comparacao da posicao principal

    if (hash->tabela[pos] != NULL && comparar_texto(hash->tabela[pos]->nome, nome) == 0) { // se achou na principal
        return pos; // retorna posicao principal
    }

    for (i = TAM_TABELA; i < TAM_TABELA + hash->reserva_usada; i++) { // percorre reserva usada
        hash->comparacoes++; // conta comparacao na reserva

        if (hash->tabela[i] != NULL && comparar_texto(hash->tabela[i]->nome, nome) == 0) { // se achou na reserva
            return i; // retorna posicao da reserva
        }
    }

    return -1; // retorna -1 se nao achou
}

// imprime resultado da pesquisa
void imprimir_pesquisa(TabelaHash* hash, char* nome) {
    int pos = pesquisar_hash(hash, nome); // pesquisa posicao
    char buffer[1000]; // buffer de formatacao

    if (pos == -1) { // se nao encontrou
        printf("-1\n"); // imprime -1
    } else {
        formatar_restaurante(hash->tabela[pos], buffer); // formata restaurante
        printf("%d %s\n", pos, buffer); // imprime posicao e restaurante
    }
}

// cria arquivo de log
void criar_log(long long comparacoes) {
    FILE* log = fopen(MATRICULA "_hash_reserva.txt", "w"); // abre log

    if (log != NULL) { // se abriu
        fprintf(log, "%s\t%lld\t0.0", MATRICULA, comparacoes); // escreve log
        fclose(log); // fecha log
    }
}

// programa principal
int main() {
    ColecaoRestaurantes* colecao = ler_csv(); // le csv
    TabelaHash hash; // cria tabela hash
    int id; // guarda id
    int lixo; // guarda resto da linha
    char nome[200]; // guarda nome pesquisado
    Restaurante* restaurante; // guarda restaurante encontrado

    iniciar_hash(&hash); // inicia tabela

    scanf("%d", &id); // le primeiro id

    while (id != -1) { // enquanto nao for -1
        restaurante = buscar_por_id(colecao, id); // busca restaurante no csv

        if (restaurante != NULL) { // se encontrou
            inserir_hash(&hash, restaurante); // insere na tabela
        }

        scanf("%d", &id); // le proximo id
    }

    do { // consome resto da linha depois do -1
        lixo = getchar(); // le caractere
    } while (lixo != '\n' && lixo != EOF); // para na quebra ou EOF

    fgets(nome, sizeof(nome), stdin); // le primeiro nome completo
    limpar_final_linha(nome); // limpa final

    while (!is_fim(nome)) { // enquanto nao for FIM
        imprimir_pesquisa(&hash, nome); // imprime resultado da pesquisa

        fgets(nome, sizeof(nome), stdin); // le proximo nome completo
        limpar_final_linha(nome); // limpa final
    }

    criar_log(hash.comparacoes); // cria log

    return 0; 
}
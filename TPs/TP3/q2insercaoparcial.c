#include <stdio.h>   
#include <stdlib.h>  

#define MATRICULA "898128" // define a matricula
#define K 10 // valor de k da ordenacao parcial

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
    int capacidade; // guarda a capacidade maxima
    double avaliacao; // guarda a avaliacao

    char tipos_cozinha[20][80]; // guarda os tipos de cozinha em um vetor de strings
    int quantidade_tipos; // guarda quantos tipos de cozinha existem

    int faixa_preco; // guarda a faixa de preco convertida para 1, 2, 3 ou 4
    Hora horario_abertura; // guarda o horario de abertura
    Hora horario_fechamento; // guarda o horario de fechamento
    Data data_abertura; // guarda a data de abertura
    int aberto; // guarda 1 para true e 0 para false
} Restaurante;                  

// struct colecao restaurantes
typedef struct ColecaoRestaurantes { 
    int tamanho; // guarda a quantidade de restaurantes na colecao
    Restaurante** restaurantes; // guarda um vetor de ponteiros para restaurantes
} ColecaoRestaurantes;             

// compara dois textos manualmente
int comparar_texto(char* a, char* b) { 
    int i = 0; // indice para percorrer os textos

    while (a[i] != '\0' && b[i] != '\0') { // enquanto nenhum dos textos terminar
        if (a[i] != b[i]) { // se encontrar caracteres diferentes
            return a[i] - b[i]; // retorna a diferenca entre os caracteres
        }

        i++; // avanca para o proximo caractere
    }

    return a[i] - b[i]; // retorna a diferenca final caso um texto termine antes
}

// remove \n e \r do final da linha
void limpar_final_linha(char* s) {
    int i = 0; // indice para percorrer a string
    int fim; // indice para voltar do final

    while (s[i] != '\0') { // percorre a string ate o fim
        i++; // avanca
    }

    fim = i - 1; // posiciona no ultimo caractere valido

    while (fim >= 0 && (s[fim] == '\n' || s[fim] == '\r')) { // enquanto houver quebra de linha no final
        s[fim] = '\0'; // troca por fim de string
        fim--; // volta uma posicao
    }
}

// parse data
Data parse_data(char* s) { 
    Data data; // cria uma variavel do tipo Data

    sscanf(s, "%d-%d-%d", &data.ano, &data.mes, &data.dia); // le ano, mes e dia

    return data; // retorna a data preenchida
}

// formata data
void formatar_data(Data* data, char* buffer) {
    char dia_string[10]; // guarda o dia formatado
    char mes_string[10]; // guarda o mes formatado

    if (data->dia < 10) { // se o dia tiver um digito
        sprintf(dia_string, "0%d", data->dia); // coloca zero na frente
    } else {
        sprintf(dia_string, "%d", data->dia); // escreve normal
    }

    if (data->mes < 10) { // se o mes tiver um digito
        sprintf(mes_string, "0%d", data->mes); // coloca zero na frente
    } else {
        sprintf(mes_string, "%d", data->mes); // escreve normal
    }

    sprintf(buffer, "%s/%s/%d", dia_string, mes_string, data->ano); // monta a data final
}

// parse hora
Hora parse_hora(char* s) { 
    Hora hora; // cria uma variavel do tipo Hora

    sscanf(s, "%d:%d", &hora.hora, &hora.minuto); // le hora e minuto

    return hora; // retorna a hora preenchida
}

// formata hora
void formatar_hora(Hora* hora, char* buffer) {
    char hora_string[10]; // guarda a hora formatada
    char minuto_string[10]; // guarda o minuto formatado

    if (hora->hora < 10) { // se a hora tiver um digito
        sprintf(hora_string, "0%d", hora->hora); // coloca zero na frente
    } else {
        sprintf(hora_string, "%d", hora->hora); // escreve normal
    }

    if (hora->minuto < 10) { // se o minuto tiver um digito
        sprintf(minuto_string, "0%d", hora->minuto); // coloca zero na frente
    } else {
        sprintf(minuto_string, "%d", hora->minuto); // escreve normal
    }

    sprintf(buffer, "%s:%s", hora_string, minuto_string); // monta a hora final
}

// converte faixa preco para inteiro
int parse_faixa_preco(char* s) { 
    if (comparar_texto(s, "$") == 0) { // se for "$"
        return 1; // retorna 1
    } else if (comparar_texto(s, "$$") == 0) { // se for "$$"
        return 2; // retorna 2
    } else if (comparar_texto(s, "$$$") == 0) { // se for "$$$"
        return 3; // retorna 3
    } else if (comparar_texto(s, "$$$$") == 0) { // se for "$$$$"
        return 4; // retorna 4
    }

    return 0; // retorna 0 se nao encontrar
}

// formata faixa preco
void formatar_faixa_preco(int faixa_preco, char* buffer) {
    int i = 0; // indice do buffer

    while (i < faixa_preco) { // repete de acordo com a faixa de preco
        buffer[i] = '$'; // coloca um $
        i++; // avanca
    }

    buffer[i] = '\0'; // finaliza a string
}

// conta tipos de cozinha
int contar_tipos_cozinha(char* s) { 
    int i = 0; // indice para percorrer a string
    int count = 1; // assume um tipo inicialmente

    if (s[0] == '\0') { // se a string estiver vazia
        return 0; // retorna zero
    }

    while (s[i] != '\0') { // percorre a string
        if (s[i] == ';') { // se encontrar separador
            count++; // aumenta a quantidade
        }

        i++; // avanca
    }

    return count; // retorna a quantidade de tipos
}

// separa os tipos de cozinha
void parse_tipos_cozinha(char* s, Restaurante* restaurante) {
    int i = 0; // percorre a string original
    int j = 0; // percorre o tipo atual
    int indice_tipo = 0; // controla o indice do tipo

    restaurante->quantidade_tipos = contar_tipos_cozinha(s); // guarda a quantidade de tipos

    while (s[i] != '\0') { // percorre a string original
        if (s[i] == ';') { // se encontrar separador
            restaurante->tipos_cozinha[indice_tipo][j] = '\0'; // finaliza o tipo atual
            indice_tipo++; // vai para o proximo tipo
            j = 0; // reinicia o indice interno
        } else {
            restaurante->tipos_cozinha[indice_tipo][j] = s[i]; // copia o caractere
            j++; // avanca no tipo atual
        }

        i++; // avanca na string original
    }

    restaurante->tipos_cozinha[indice_tipo][j] = '\0'; // finaliza o ultimo tipo
}

// parse restaurante
Restaurante* parse_restaurante(char* s) {
    Restaurante* restaurante = (Restaurante*) malloc(sizeof(Restaurante)); // aloca memoria para um restaurante

    char tipos[200]; // guarda os tipos de cozinha
    char faixa[10]; // guarda a faixa de preco
    char horario[20]; // guarda o horario completo
    char data[20]; // guarda a data
    char aberto[10]; // guarda true ou false

    char abertura[10]; // guarda o horario de abertura
    char fechamento[10]; // guarda o horario de fechamento

    sscanf(
        s, // linha original
        "%d,%119[^,],%119[^,],%d,%lf,%199[^,],%9[^,],%19[^,],%19[^,],%9s", // formato de leitura
        &restaurante->id, // le o id
        restaurante->nome, // le o nome
        restaurante->cidade, // le a cidade
        &restaurante->capacidade, // le a capacidade
        &restaurante->avaliacao, // le a avaliacao
        tipos, // le os tipos
        faixa, // le a faixa de preco
        horario, // le o horario
        data, // le a data
        aberto // le se esta aberto
    );

    parse_tipos_cozinha(tipos, restaurante); // separa os tipos de cozinha

    restaurante->faixa_preco = parse_faixa_preco(faixa); // converte a faixa de preco

    sscanf(horario, "%9[^-]-%9s", abertura, fechamento); // separa abertura e fechamento

    restaurante->horario_abertura = parse_hora(abertura); // converte abertura
    restaurante->horario_fechamento = parse_hora(fechamento); // converte fechamento
    restaurante->data_abertura = parse_data(data); // converte data

    if (comparar_texto(aberto, "true") == 0) { // se for true
        restaurante->aberto = 1; // guarda 1
    } else {
        restaurante->aberto = 0; // guarda 0
    }

    return restaurante; // retorna o restaurante criado
}

// formata tipos de cozinha
void formatar_tipos_cozinha(Restaurante* restaurante, char* buffer) {
    int i = 0; // percorre os tipos
    int pos = 0; // controla a posicao no buffer
    int j; // percorre os caracteres do tipo atual

    buffer[pos] = '['; // coloca [
    pos++; // avanca
    buffer[pos] = '\0'; // finaliza temporariamente

    for (i = 0; i < restaurante->quantidade_tipos; i++) { // percorre os tipos
        j = 0; // comeca no primeiro caractere

        while (restaurante->tipos_cozinha[i][j] != '\0') { // copia o tipo
            buffer[pos] = restaurante->tipos_cozinha[i][j]; // copia caractere
            pos++; // avanca no buffer
            j++; // avanca no tipo
        }

        if (i < restaurante->quantidade_tipos - 1) { // se nao for o ultimo
            buffer[pos] = ','; // coloca virgula
            pos++; // avanca
        }
    }

    buffer[pos] = ']'; // coloca ]
    pos++; // avanca
    buffer[pos] = '\0'; // finaliza a string
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
        buffer, // escreve no buffer
        "[%d ## %s ## %s ## %d ## %.1lf ## %s ## %s ## %s-%s ## %s ## %s]", // formato da saida
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
    FILE* arquivo = fopen(path, "r"); // abre o arquivo
    char linha[512]; // guarda uma linha
    int count = 0; // conta registros
    int i = 0; // indice

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
        limpar_final_linha(linha); // limpa fim da linha
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

// busca por id
Restaurante* buscar_por_id(ColecaoRestaurantes* colecao, int id) {
    int i; // indice

    for (i = 0; i < colecao->tamanho; i++) { // percorre colecao
        if (colecao->restaurantes[i]->id == id) { // se achou id
            return colecao->restaurantes[i]; // retorna restaurante
        }
    }

    return NULL; // retorna NULL se nao achar
}

// compara restaurantes pela cidade
int comparar_por_cidade(Restaurante* a, Restaurante* b, long long* comparacoes) {
    (*comparacoes)++; // conta uma comparacao

    return comparar_texto(a->cidade, b->cidade); // compara as cidades
}

// ordenacao parcial por insercao usando cidade como chave
void insercao_parcial_por_cidade(Restaurante** array, int n, long long* comparacoes, long long* movimentacoes) {
    int i; // indice externo
    int j; // indice interno
    int limite = K; // guarda o limite da ordenacao parcial

    if (n < limite) { // se houver menos que K elementos
        limite = n; // ajusta o limite para a quantidade real
    }

    for (i = 1; i < n; i++) { // percorre todos os elementos do vetor
        Restaurante* tmp = array[i]; // guarda o elemento atual
        (*movimentacoes)++; // conta a movimentacao para tmp

        if (i < limite) { // se ainda esta dentro das primeiras K posicoes
            j = i - 1; // comeca comparando com a posicao anterior
        } else { // se ja passou das primeiras K posicoes
            j = limite - 1; // comeca comparando com a ultima posicao do top K
        }

        while (j >= 0 && comparar_por_cidade(array[j], tmp, comparacoes) > 0) { // enquanto a cidade anterior for maior
            array[j + 1] = array[j]; // desloca o restaurante para a direita
            (*movimentacoes)++; // conta a movimentacao
            j--; // volta uma posicao
        }

        array[j + 1] = tmp; // coloca o restaurante na posicao correta
        (*movimentacoes)++; // conta a movimentacao
    }
}

// cria arquivo de log
void criar_log(long long comparacoes, long long movimentacoes) {
    FILE* log = fopen(MATRICULA "_insercao_parcial.txt", "w"); // abre arquivo de log

    if (log != NULL) { // se conseguiu abrir
        fprintf(log, "%s\t%lld\t%lld\t0.0", MATRICULA, comparacoes, movimentacoes); // escreve log
        fclose(log); // fecha arquivo
    }
}

// programa principal
int main() {
    ColecaoRestaurantes* colecao = ler_csv(); // le todos os restaurantes
    Restaurante* selecionados[1000]; // vetor dos restaurantes escolhidos
    int quantidade = 0; // quantidade escolhida
    int id; // guarda id lido
    int i; // indice
    char buffer[1000]; // buffer para imprimir
    long long comparacoes = 0; // contador de comparacoes
    long long movimentacoes = 0; // contador de movimentacoes

    scanf("%d", &id); // le primeiro id

    while (id != -1) { // enquanto nao for -1
        Restaurante* restaurante = buscar_por_id(colecao, id); // busca restaurante

        if (restaurante != NULL) { // se encontrou
            selecionados[quantidade] = restaurante; // guarda no vetor
            quantidade++; // aumenta quantidade
        }

        scanf("%d", &id); // le proximo id
    }

    insercao_parcial_por_cidade(selecionados, quantidade, &comparacoes, &movimentacoes); // ordena parcialmente

    for (i = 0; i < quantidade; i++) { // imprime todos os registros
        formatar_restaurante(selecionados[i], buffer); // formata restaurante
        printf("%s\n", buffer); // imprime restaurante
    }

    criar_log(comparacoes, movimentacoes); // cria log

    return 0; // encerra
}
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
    char nome[120];   // guarda o nome 
    char cidade[120]; // guarda a cidade 
    int capacidade;   // guarda a capacidade maxima
    double avaliacao; // guarda a avaliacao

    char tipos_cozinha[20][80]; // vetor com os tipos de cozinha
    int quantidade_tipos;       // quantos tipos de cozinha existem

    int faixa_preco; // faixa de preco convertida para 1, 2, 3 ou 4
    Hora horario_abertura; // horario de abertura
    Hora horario_fechamento; // horario de fechamento
    Data data_abertura; // data de abertura
    int aberto; // 1 para true e 0 para false
} Restaurante;                  

// struct ColecaoRestaurantes
typedef struct ColecaoRestaurantes { 
    int tamanho; // quantidade de restaurantes na colecao
    Restaurante** restaurantes; // vetor de ponteiros para restaurantes
} ColecaoRestaurantes;             

// compara duas strings manualmente
int comparar_texto(char* a, char* b) { 
    int i = 0; // indice para percorrer as strings

    while (a[i] != '\0' && b[i] != '\0') { // enquanto nenhuma das strings terminar
        if (a[i] != b[i]) { // se encontrar caracteres diferentes
            return a[i] - b[i]; // retorna a diferenca entre os caracteres
        }
        i++; // avanca para o proximo caractere
    }

    return a[i] - b[i]; // se uma string terminar antes, retorna a diferenca final
}

// remove \n e \r do final da linha
void limpar_final_linha(char* s) {
    int i = 0; // indice para percorrer a string
    int fim; // indice para voltar do final

    while (s[i] != '\0') { // percorre a string ate o fim
        i++; // avanca
    }

    fim = i - 1; // volta para a ultima posicao valida

    while (fim >= 0 && (s[fim] == '\n' || s[fim] == '\r')) { // remove quebra de linha do final
        s[fim] = '\0'; // troca por fim de string
        fim--; // anda para a esquerda
    }
}

// converte faixa preco para int: "$", "$$", "$$$", "$$$$" em 1, 2, 3, 4
int parse_faixa_preco(char* s) { 
    if (comparar_texto(s, "$") == 0) {           // se a string for "$"
        return 1;                                // retorna 1
    } else if (comparar_texto(s, "$$") == 0) {   // se a string for "$$"
        return 2;                                // retorna 2
    } else if (comparar_texto(s, "$$$") == 0) {  // se a string for "$$$"
        return 3;                                // retorna 3
    } else if (comparar_texto(s, "$$$$") == 0) { // se a string for "$$$$"
        return 4;                                // retorna 4
    }

    return 0; // se nao encontrar, retorna 0
}

// converte faixa preco de inteiro para texto: 1, 2, 3, 4 em "$", "$$", "$$$", "$$$$"
void formatar_faixa_preco(int faixa_preco, char* buffer) {
    int i = 0; // indice do buffer

    while (i < faixa_preco) { // repete enquanto i for menor que faixa_preco
        buffer[i] = '$'; // coloca um '$' na posicao atual
        i++; // avanca no buffer
    }

    buffer[i] = '\0'; // finaliza a string
}

// parse data
Data parse_data(char* s) { // recebe "YYYY-MM-DD" e retorna uma struct Data
    Data data; // cria uma variavel do tipo Data

    sscanf(s, "%d-%d-%d", &data.ano, &data.mes, &data.dia); // le ano, mes e dia da string

    return data; // retorna a struct preenchida
}

// formata data
void formatar_data(Data* data, char* buffer) { // formata a data como DD/MM/YYYY
    char dia_string[10];  // string auxiliar para o dia
    char mes_string[10];  // string auxiliar para o mes

    if (data->dia < 10) {                      // se o dia tiver um digito
        sprintf(dia_string, "0%d", data->dia); // coloca zero na frente
    } else {                                   
        sprintf(dia_string, "%d", data->dia);  // se nao, escreve normal
    }

    if (data->mes < 10) {                      // se o mes tiver um digito
        sprintf(mes_string, "0%d", data->mes); // coloca zero na frente
    } else {                                   
        sprintf(mes_string, "%d", data->mes);  // se nao, escreve normal
    }

    sprintf(buffer, "%s/%s/%d", dia_string, mes_string, data->ano); // monta a data final
}

// parse hora
Hora parse_hora(char* s) { // recebe "HH:mm" e retorna uma struct Hora
    Hora hora; // cria uma variavel do tipo Hora

    sscanf(s, "%d:%d", &hora.hora, &hora.minuto); // le hora e minuto da string

    return hora; // retorna a struct preenchida
}

// formata hora
void formatar_hora(Hora* hora, char* buffer) { // formata a hora como HH:mm
    char hora_string[10]; // string auxiliar para a hora
    char minuto_string[10]; // string auxiliar para o minuto

    if (hora->hora < 10) {  // se a hora tiver um digito
        sprintf(hora_string, "0%d", hora->hora); // coloca zero na frente
    } else {                                     
        sprintf(hora_string, "%d", hora->hora);  // se nao, escreve normal
    }

    if (hora->minuto < 10) { // se o minuto tiver um digito
        sprintf(minuto_string, "0%d", hora->minuto); // coloca zero na frente
    } else {                                        
        sprintf(minuto_string, "%d", hora->minuto); // se nao, escreve normal
    }

    sprintf(buffer, "%s:%s", hora_string, minuto_string); // monta a hora final
}

// conta tipos de cozinha
int contar_tipos_cozinha(char* s) { 
    int i = 0; // indice para percorrer a string
    int count = 1; // assume 1 tipo inicialmente

    if (s[0] == '\0') { // se a string estiver vazia
        return 0; // nao existe nenhum tipo
    }

    while (s[i] != '\0') { // percorre toda a string
        if (s[i] == ';') { // cada ';' separa um novo tipo
            count++; // aumenta a quantidade
        }
        i++; // avanca
    }

    return count; // retorna a quantidade de tipos
}

// separa tipos de cozinha em varias strings 
void parse_tipos_cozinha(char* s, Restaurante* restaurante) {
    int i = 0;  // percorre a string original
    int j = 0;  // percorre a string do tipo atual
    int indice_tipo = 0; // controla em qual tipo estamos

    restaurante->quantidade_tipos = contar_tipos_cozinha(s); // calcula quantos tipos existem

    while (s[i] != '\0') {  // percorre a string original
        if (s[i] == ';') {  // se encontrar ';'
            restaurante->tipos_cozinha[indice_tipo][j] = '\0'; // termina a string do tipo atual
            indice_tipo++; // vai para o proximo tipo
            j = 0; // reinicia o indice interno
        } else { // se nao for ';'
            restaurante->tipos_cozinha[indice_tipo][j] = s[i]; // copia o caractere atual
            j++; // avanca dentro do tipo atual
        }
        i++; // avanca na string original
    }

    restaurante->tipos_cozinha[indice_tipo][j] = '\0'; // finaliza o ultimo tipo
}

// parse restaurante
Restaurante* parse_restaurante(char* s) { // recebe uma linha do csv e cria um restaurante
    Restaurante* restaurante = (Restaurante*) malloc(sizeof(Restaurante)); // aloca memoria para um restaurante

    char tipos[200];  // guarda a string dos tipos de cozinha
    char faixa[10];   // guarda a faixa de preco em string
    char horario[20]; // guarda o horario completo
    char data[20];    // guarda a data completa
    char aberto[10];  // guarda "true" ou "false"

    char abertura[10]; // guarda a parte de abertura do horario
    char fechamento[10]; // guarda a parte de fechamento do horario

    sscanf( // le todos os campos da linha do csv
        s,  // linha original
        "%d,%119[^,],%119[^,],%d,%lf,%199[^,],%9[^,],%19[^,],%19[^,],%9s", // formato de leitura
        &restaurante->id,         // le o id
        restaurante->nome,        // le o nome
        restaurante->cidade,      // le a cidade
        &restaurante->capacidade, // le a capacidade
        &restaurante->avaliacao,  // le a avaliacao
        tipos,                    // le os tipos de cozinha
        faixa,                    // le a faixa de preco
        horario,                  // le o horario
        data,                     // le a data
        aberto                    // le o campo aberto
    );

    parse_tipos_cozinha(tipos, restaurante); // separa os tipos de cozinha

    restaurante->faixa_preco = parse_faixa_preco(faixa); // converte a faixa de preco

    sscanf(horario, "%9[^-]-%9s", abertura, fechamento); // separa o horario em abertura e fechamento

    restaurante->horario_abertura = parse_hora(abertura); // converte a abertura para Hora
    restaurante->horario_fechamento = parse_hora(fechamento); // converte o fechamento para Hora

    restaurante->data_abertura = parse_data(data); // converte a data para Data

    if (comparar_texto(aberto, "true") == 0) { // se aberto for "true"
        restaurante->aberto = 1; // guarda 1
    } else {                                   
        restaurante->aberto = 0; // se nao, guarda 0
    }

    return restaurante; // retorna o ponteiro para o restaurante criado
}

// formata tipos cozinha em [a,b,c]
void formatar_tipos_cozinha(Restaurante* restaurante, char* buffer) { 
    int i = 0;    // percorre os tipos
    int pos = 0;  // controla a posicao atual no buffer
    int j;        // percorre os caracteres de cada tipo

    buffer[pos] = '[';  // coloca '[' no inicio
    pos++;              // avanca uma posicao
    buffer[pos] = '\0'; // coloca fim temporario

    for (i = 0; i < restaurante->quantidade_tipos; i++) { // percorre todos os tipos
        j = 0; // comeca no primeiro caractere do tipo atual

        while (restaurante->tipos_cozinha[i][j] != '\0') { // copia caractere por caractere
            buffer[pos] = restaurante->tipos_cozinha[i][j]; // copia o caractere atual
            pos++; // avanca no buffer
            j++; // avanca no tipo
        }

        if (i < restaurante->quantidade_tipos - 1) { // se nao for o ultimo tipo
            buffer[pos] = ','; // coloca virgula
            pos++; // avanca
        }
    }

    buffer[pos] = ']'; // fecha com ']'
    pos++; // avanca
    buffer[pos] = '\0'; // finaliza a string
}

// formata restaurante completo
void formatar_restaurante(Restaurante* restaurante, char* buffer) { 
    char tipos_string[300];      // guarda os tipos formatados
    char faixa_string[10];       // guarda a faixa formatada
    char abertura_string[10];    // guarda a hora de abertura formatada
    char fechamento_string[10];  // guarda a hora de fechamento formatada
    char data_string[20];        // guarda a data formatada

    formatar_tipos_cozinha(restaurante, tipos_string); // formata os tipos
    formatar_faixa_preco(restaurante->faixa_preco, faixa_string); // formata a faixa
    formatar_hora(&restaurante->horario_abertura, abertura_string); // formata a abertura
    formatar_hora(&restaurante->horario_fechamento, fechamento_string); // formata o fechamento
    formatar_data(&restaurante->data_abertura, data_string); // formata a data

    sprintf( // monta a string final do restaurante
        buffer, // escreve no buffer
        "[%d ## %s ## %s ## %d ## %.1lf ## %s ## %s ## %s-%s ## %s ## %s]", // formato de saida
        restaurante->id, // id
        restaurante->nome, // nome
        restaurante->cidade, // cidade
        restaurante->capacidade, // capacidade
        restaurante->avaliacao, // avaliacao
        tipos_string, // tipos de cozinha
        faixa_string, // faixa de preco
        abertura_string, // hora de abertura
        fechamento_string, // hora de fechamento
        data_string, // data de abertura
        restaurante->aberto ? "true" : "false" // aberto
    );
}

// le csv e preenche a colecao completa 
void ler_csv_colecao(ColecaoRestaurantes* colecao, char* path) {
    FILE* arquivo = fopen(path, "r"); // abre o arquivo para leitura
    char linha[512]; // guarda cada linha lida
    int count = 0; // conta quantos restaurantes existem
    int i = 0; // indice de repeticao

    if (arquivo == NULL) { // se nao conseguiu abrir o arquivo
        return; // encerra a funcao
    }

    fgets(linha, sizeof(linha), arquivo); // le e ignora o cabecalho

    while (fgets(linha, sizeof(linha), arquivo) != NULL) { // conta as linhas de dados
        count++; // aumenta a contagem
    }

    colecao->tamanho = count; // guarda o total de restaurantes na colecao
    colecao->restaurantes = (Restaurante**) malloc(sizeof(Restaurante*) * count); // aloca o vetor de ponteiros

    fclose(arquivo); // fecha o arquivo para reabrir do inicio

    arquivo = fopen(path, "r"); // reabre o arquivo

    if (arquivo == NULL) { // se nao conseguir abrir de novo
        return; // encerra
    }

    fgets(linha, sizeof(linha), arquivo); // le e ignora o cabecalho novamente

    for (i = 0; i < count; i++) { // percorre todas as linhas de dados
        fgets(linha, sizeof(linha), arquivo); // le uma linha
        limpar_final_linha(linha); // remove \n e \r do final
        colecao->restaurantes[i] = parse_restaurante(linha); // transforma a linha em restaurante
    }

    fclose(arquivo); // fecha o arquivo
}

// funcao que cria a colecao e le o arquivo padrao
ColecaoRestaurantes* ler_csv() { // cria a colecao e manda ler /tmp/restaurantes.csv
    ColecaoRestaurantes* colecao = (ColecaoRestaurantes*) malloc(sizeof(ColecaoRestaurantes)); // aloca memoria para a colecao

    colecao->tamanho = 0; // começa com tamanho 0
    colecao->restaurantes = NULL; // comeca sem vetor

    ler_csv_colecao(colecao, "/tmp/restaurantes.csv"); // le o arquivo padrao

    return colecao; // retorna a colecao pronta
}

// busca por id
Restaurante* buscar_por_id(ColecaoRestaurantes* colecao, int id) { // procura um restaurante pelo id
    int i;  // indice para percorrer a colecao

    for (i = 0; i < colecao->tamanho; i++) { // percorre todos os restaurantes
        if (colecao->restaurantes[i]->id == id) { // se o id atual for igual ao procurado
            return colecao->restaurantes[i]; // retorna o restaurante encontrado
        }
    }

    return NULL; // retorna NULL se nao encontrar
}

// compara dois restaurantes pela avaliacao e em caso de empate pelo nome
int comparar_restaurantes(Restaurante* a, Restaurante* b, long long* comparacoes) {
    (*comparacoes)++; // conta uma comparacao pela avaliacao

    if (a->avaliacao < b->avaliacao) { // se a avaliacao de a for menor
        return -1; // retorna negativo
    } else if (a->avaliacao > b->avaliacao) { // se a avaliacao de a for maior
        return 1; // retorna positivo
    }

    (*comparacoes)++; // conta uma comparacao pelo nome em caso de empate

    return comparar_texto(a->nome, b->nome); // desempata pelo nome
}

// troca dois restaurantes de posicao
void swap(Restaurante** a, Restaurante** b, long long* movimentacoes) {
    Restaurante* temp = *a; // guarda o valor de a
    *a = *b; // coloca b em a
    *b = temp; // coloca o antigo a em b
    (*movimentacoes) += 3; // conta 3 movimentacoes
}

// quicksort
void quicksort(Restaurante** array, int esq, int dir, long long* comparacoes, long long* movimentacoes) {
    int i = esq; // indice da esquerda
    int j = dir; // indice da direita
    Restaurante* pivo = array[(esq + dir) / 2]; // escolhe o elemento do meio como pivo

    while (i <= j) { // enquanto os indices nao se cruzarem
        while (comparar_restaurantes(array[i], pivo, comparacoes) < 0) { // avanca i enquanto elemento da esquerda for menor que o pivo
            i++; // avanca i
        }

        while (comparar_restaurantes(array[j], pivo, comparacoes) > 0) { // recua j enquanto elemento da direita for maior que o pivo
            j--; // recua j
        }

        if (i <= j) { // se ainda nao cruzaram
            swap(&array[i], &array[j], movimentacoes); // troca os elementos
            i++; // avanca i
            j--; // recua j
        }
    }

    if (esq < j) { // se ainda existir parte a esquerda
        quicksort(array, esq, j, comparacoes, movimentacoes); // ordena a metade esquerda
    }

    if (i < dir) { // se ainda existir parte a direita
        quicksort(array, i, dir, comparacoes, movimentacoes); // ordena a metade direita
    }
}

// grava arquivo de log
void criar_log(long long comparacoes, long long movimentacoes) { 
    FILE* log = fopen(MATRICULA "_quicksort.txt", "w"); // abre o arquivo no modo escrita

    if (log != NULL) { // se conseguiu abrir
        fprintf(log, "%s\t%lld\t%lld\t0.0", MATRICULA, comparacoes, movimentacoes); // escreve matricula, comparacoes, movimentacoes e tempo
        fclose(log); // fecha o arquivo
    }
}

// programa principal
int main() { 
    ColecaoRestaurantes* colecao = ler_csv(); // le o csv inteiro e monta a colecao completa
    Restaurante* selecionados[1000]; // vetor que vai guardar os restaurantes escolhidos pela entrada
    int quantidade = 0; // quantidade de restaurantes selecionados

    char linha[256]; // guarda cada linha lida da entrada
    int id; // guarda o id da primeira parte
    int i; // indice de repeticao
    char buffer[1000]; // buffer para imprimir o restaurante formatado

    long long comparacoes = 0; // contador de comparacoes da ordenacao
    long long movimentacoes = 0; // contador de movimentacoes da ordenacao

    int continuar = 1;

    while (continuar == 1 && fgets(linha, sizeof(linha), stdin) != NULL) {
        limpar_final_linha(linha);

        sscanf(linha, "%d", &id);

        if (id == -1) {
            continuar = 0;
        } else {
            Restaurante* restaurante = buscar_por_id(colecao, id);

            if (restaurante != NULL) {
                selecionados[quantidade] = restaurante;
                quantidade++;
        }
    }
}

    if (quantidade > 0) { // se existir pelo menos um restaurante
        quicksort(selecionados, 0, quantidade - 1, &comparacoes, &movimentacoes); // ordena com quicksort
    }

    for (i = 0; i < quantidade; i++) { // percorre o vetor ordenado
        formatar_restaurante(selecionados[i], buffer); // formata o restaurante atual
        printf("%s\n", buffer); // imprime o restaurante atual
    }

    criar_log(comparacoes, movimentacoes); // cria o arquivo de log

    return 0; // encerra
}
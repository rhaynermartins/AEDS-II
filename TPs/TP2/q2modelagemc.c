#include <stdio.h> 
#include <stdlib.h>  

// struct Data
typedef struct Data {
    int ano;  // guarda o ano
    int mes;  // guarda o mes
    int dia;  // guarda o dia
} Data;  

// struct Hora
typedef struct Hora {
    int hora;    // guarda a hora
    int minuto;  // guarda o minuto
} Hora;  

// struct Restaurante
typedef struct Restaurante {
    int id;                 // id do restaurante
    char nome[120];         // nome do restaurante
    char cidade[120];       // cidade do restaurante
    int capacidade;         // capacidade maxima
    double avaliacao;       // avaliacao do restaurante

    char tipos_cozinha[20][80];   // vetor de strings com os tipos de cozinha
    int quantidade_tipos;         // qnt de tipos existentes

    int faixa_preco;           // faixa de preco convertida para 1, 2, 3 ou 4
    Hora horario_abertura;     // horario de abertura
    Hora horario_fechamento;   // horario de fechamento
    Data data_abertura;        // data de abertura
    int aberto;                // 1 = true, 0 = false
} Restaurante;                 

// struct ColecaoRestaurantes
typedef struct ColecaoRestaurantes {
    int tamanho;                  // qnt de restaurantes na colecao
    Restaurante** restaurantes;   // vetor de ponteiros para Restaurante
} ColecaoRestaurantes;           

// funcao que calcula o tamanho de uma string 
int tamanho_texto(char* s) {
    int i = 0; // indice para percorrer a string

    while (s[i] != '\0') {  // enquanto nao chegar no fim da string
        i++;  // avanca uma posicao
    }

    return i; // retorna a quantidade de caracteres
}

// funcao que compara duas strings
int comparar_texto(char* a, char* b) {
    int i = 0;  // indice para percorrer as duas strings

    while (a[i] != '\0' && b[i] != '\0') {
        if (a[i] != b[i]) {        // se encontrar caracteres diferentes
            return a[i] - b[i];    // retorna a diferenca entre eles
        }
        i++;  // avanca para o proximo caractere
    }

    return a[i] - b[i]; // se uma terminou antes da outra, retorna a diferenca final
}

// funcao que copia uma string para outra 
void copiar_texto(char* destino, char* origem) {
    int i = 0;  // indice para percorrer a string de origem

    while (origem[i] != '\0') {    // enquanto nao chegar no fim da origem
        destino[i] = origem[i];    // copia o caractere atual para o destino
        i++; // avanca para o proximo caractere
    }

    destino[i] = '\0';  // coloca o fim da string no destino
}

// funcao que remove o caractere '\n' da linha lida 
void remover_quebra_linha(char* s) {
    int i = 0; // indice para percorrer a string

    while (s[i] != '\0') {   // enquanto nao chegar no fim da string
        if (s[i] == '\n') {  // se encontrar quebra de linha
            s[i] = '\0';     // troca por fim de string
        }
        i++; // avanca
    }
}

// funcao que converte "$", "$$", "$$$" ou "$$$$" em 1, 2, 3 ou 4
int parse_faixa_preco(char* s) {
    int resp = 0;  // variavel resposta

    if (comparar_texto(s, "$") == 0) {             // se a string for "$"
        resp = 1;                                  // vira 1
    } else if (comparar_texto(s, "$$") == 0) {     // se a string for "$$"
        resp = 2;                                  // vira 2
    } else if (comparar_texto(s, "$$$") == 0) {    // se a string for "$$$"
        resp = 3;                                  // vira 3
    } else if (comparar_texto(s, "$$$$") == 0) {   // se a string for "$$$$"
        resp = 4;                                  //  vira 4
    }

    return resp; // retorna a faixa convertida
}

// funcao que faz o contrario: recebe 1, 2, 3 ou 4 e monta "$", "$$", "$$$" ou "$$$$"
void formatar_faixa_preco(int faixa_preco, char* buffer) {
    int i = 0; // indice do buffer

    while (i < faixa_preco) {  // repete enquanto i for menor que a faixa
        buffer[i] = '$';       // coloca um '$' na posicao atual
        i++;                   // avanca para a proxima posicao
    }

    buffer[i] = '\0'; // finaliza a string
}

// funcao que recebe uma string "YYYY-MM-DD" e retorna um Data
Data parse_data(char* s) {
    Data data; // cria uma variavel do tipo Data

    sscanf(s, "%d-%d-%d", &data.ano, &data.mes, &data.dia); // le ano, mes e dia da string

    return data; // retorna a struct preenchida
}

// funcao que escreve a Data no formato "DD/MM/YYYY"
void formatar_data(Data* data, char* buffer) {
    char dia_string[10];  // string auxiliar para o dia
    char mes_string[10];  // string auxiliar para o mes

    if (data->dia < 10) {                           // se o dia tiver um digito
        sprintf(dia_string, "0%d", data->dia);      // coloca zero na frente
    } else {                                        
        sprintf(dia_string, "%d", data->dia);       // se nao, escreve normal
    }

    if (data->mes < 10) {                           // se o mes tiver um digito
        sprintf(mes_string, "0%d", data->mes);      // coloca zero na frente
    } else {                                      
        sprintf(mes_string, "%d", data->mes);       // se nao, escreve normal
    }

    sprintf(buffer, "%s/%s/%d", dia_string, mes_string, data->ano); // monta a data final
}

// funcao que recebe uma string "HH:mm" e retorna uma Hora
Hora parse_hora(char* s) {
    Hora hora;                                      // cria uma variavel do tipo Hora

    sscanf(s, "%d:%d", &hora.hora, &hora.minuto);   // le hora e minuto da string

    return hora; // retorna a struct preenchida
}

// funcao que escreve a Hora no formato "HH:mm"
void formatar_hora(Hora* hora, char* buffer) {
    char hora_string[10]; // string auxiliar para a hora
    char minuto_string[10]; // string auxiliar para o minuto

    if (hora->hora < 10) {                           // se a hora tiver um digito
        sprintf(hora_string, "0%d", hora->hora);     // coloca zero na frente
    } else {                                       
        sprintf(hora_string, "%d", hora->hora);      // se nao, escreve normal
    }

    if (hora->minuto < 10) {                         // se o minuto tiver um digito
        sprintf(minuto_string, "0%d", hora->minuto); // coloca zero na frente
    } else {                                        
        sprintf(minuto_string, "%d", hora->minuto);  // se nao, escreve normal
    }

    sprintf(buffer, "%s:%s", hora_string, minuto_string); // monta a hora final
}

// funcao que conta quantos tipos de cozinha existem na string
int contar_tipos_cozinha(char* s) {
    int i = 0;  // indice para percorrer a string
    int count = 1;  // comeca em 1 porque uma string sem ';' ja tem um tipo

    if (s[0] == '\0') {  // se a string estiver vazia
        return 0;  // nao existe nenhum tipo
    }

    while (s[i] != '\0') {    // percorre toda a string
        if (s[i] == ';') {    // cada ';' separa um novo tipo
            count++;          // aumenta a quantidade de tipos
        }
        i++; // avanca na string
    }

    return count; // retorna quantos tipos existem
}

// funcao que separa os tipos de cozinha da string e guarda dentro de restaurante
void parse_tipos_cozinha(char* s, Restaurante* restaurante) {
    int i = 0; // percorre a string original
    int j = 0; // percorre cada palavra do tipo atual
    int indice_tipo = 0; // controla em qual tipo esta sendo escrito

    restaurante->quantidade_tipos = contar_tipos_cozinha(s); // calcula a quantidade de tipos

    while (s[i] != '\0') {  // percorre toda a string
        if (s[i] == ';') {  // se encontrar ';'
            restaurante->tipos_cozinha[indice_tipo][j] = '\0'; // termina a string atual
            indice_tipo++;   // vai para o proximo tipo
            j = 0;  // reinicia o indice interno
        } else {  // se nao for ';'
            restaurante->tipos_cozinha[indice_tipo][j] = s[i]; // copia o caractere
            j++; // avanca dentro do tipo atual
        }
        i++;  // avanca na string original
    }

    restaurante->tipos_cozinha[indice_tipo][j] = '\0'; // finaliza o ultimo tipo
}

// funcao que recebe uma linha inteira do csv e cria um restaurante
Restaurante* parse_restaurante(char* s) {
    Restaurante* restaurante = (Restaurante*) malloc(sizeof(Restaurante)); // aloca memoria para um restaurante

    char tipos[200];   // guarda a string dos tipos de cozinha
    char faixa[10];    // guarda a faixa de preco em texto
    char horario[20];  // guarda o horario completo
    char data[20];     // guarda a data completa
    char aberto[10];   // guarda "true" ou "false"

    char abertura[10];    // guarda a parte de abertura do horario
    char fechamento[10];  // guarda a parte de fechamento do horario

    sscanf(  // le os campos da linha do csv
        s,   // string de origem
        "%d,%119[^,],%119[^,],%d,%lf,%199[^,],%9[^,],%19[^,],%19[^,],%9s", // formato
        &restaurante->id,          // le o id
        restaurante->nome,         // le o nome
        restaurante->cidade,       // le a cidade
        &restaurante->capacidade,  // le a capacidade
        &restaurante->avaliacao,   // le a avaliacao
        tipos,                     // le os tipos de cozinha
        faixa,                     // le a faixa de preco
        horario,                   // le o horario completo
        data,                      // le a data
        aberto                     // le o campo aberto
    );

    parse_tipos_cozinha(tipos, restaurante); // separa os tipos de cozinha e guarda no restaurante

    restaurante->faixa_preco = parse_faixa_preco(faixa); // converte a faixa de preco

    sscanf(horario, "%9[^-]-%9s", abertura, fechamento); // separa horario em abertura e fechamento

    restaurante->horario_abertura = parse_hora(abertura);     // converte a abertura em Hora
    restaurante->horario_fechamento = parse_hora(fechamento); // converte o fechamento em Hora

    restaurante->data_abertura = parse_data(data);   // converte a data em Data

    if (comparar_texto(aberto, "true") == 0) {  // se o campo aberto for "true"
        restaurante->aberto = 1; // guarda 1
    } else {                                       
        restaurante->aberto = 0; // se nao, guarda 0
    }

    return restaurante; // retorna o ponteiro para o restaurante criado
}

// funcao que transforma os tipos de cozinha em string no formato [a,b,c]
void formatar_tipos_cozinha(Restaurante* restaurante, char* buffer) {
    int i = 0; // percorre os tipos
    int pos = 0;  // controla a posicao atual no buffer
    int j; // percorre os caracteres de cada tipo

    buffer[pos] = '['; // coloca '[' no inicio
    pos++; // avanca uma posicao
    buffer[pos] = '\0'; // coloca fim de string temporario

    for (i = 0; i < restaurante->quantidade_tipos; i++) { // percorre cada tipo de cozinha
        j = 0; // comeca do primeiro caractere do tipo

        while (restaurante->tipos_cozinha[i][j] != '\0') { // copia caractere por caractere do tipo atual
            buffer[pos] = restaurante->tipos_cozinha[i][j]; // copia o caractere
            pos++; // avanca no buffer
            j++; // avanca no tipo atual
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

// funcao que escreve o restaurante inteiro no formato correto
void formatar_restaurante(Restaurante* restaurante, char* buffer) {
    char tipos_string[300];     // guarda os tipos formatados
    char faixa_string[10];      // guarda a faixa formatada
    char abertura_string[10];   // guarda a hora de abertura formatada
    char fechamento_string[10]; // guarda a hora de fechamento formatada
    char data_string[20];       // guarda a data formatada

    formatar_tipos_cozinha(restaurante, tipos_string); // formata os tipos
    formatar_faixa_preco(restaurante->faixa_preco, faixa_string); // formata a faixa
    formatar_hora(&restaurante->horario_abertura, abertura_string); // formata a abertura
    formatar_hora(&restaurante->horario_fechamento, fechamento_string); // formata o fechamento
    formatar_data(&restaurante->data_abertura, data_string); // formata a data

    sprintf(     // monta a string final do restaurante
        buffer,  // escreve no buffer
        "[%d ## %s ## %s ## %d ## %.1lf ## %s ## %s ## %s-%s ## %s ## %s]", // formato final
        restaurante->id,          // id
        restaurante->nome,        // nome
        restaurante->cidade,      // cidade
        restaurante->capacidade,  // capacidade
        restaurante->avaliacao,   // avaliacao
        tipos_string,             // tipos de cozinha
        faixa_string,             // faixa de preco
        abertura_string,          // horario de abertura
        fechamento_string,        // horario de fechamento
        data_string,              // data de abertura
        restaurante->aberto ? "true" : "false"  // escreve true ou false
    );
}

// funcao que le o csv e preenche a colecao
void ler_csv_colecao(ColecaoRestaurantes* colecao, char* path) {
    FILE* arquivo = fopen(path, "r");  // abre o arquivo para leitura
    char linha[512]; // guarda cada linha lida
    int count = 0; // conta quantos restaurantes existem
    int i = 0; // indice do vetor

    if (arquivo == NULL) {  // se nao conseguiu abrir o arquivo
        printf("Erro ao abrir o arquivo.\n"); // mostra mensagem d erro
        return; // encerra
    }

    fgets(linha, sizeof(linha), arquivo); // le e descarta o cabecalho

    while (fgets(linha, sizeof(linha), arquivo) != NULL) { // conta quantas linhas de dados existem
        count++; // aumenta a qnt
    }

    colecao->tamanho = count;  // guarda o tamanho da colecao

    colecao->restaurantes = (Restaurante**) malloc(sizeof(Restaurante*) * count); // aloca o vetor de ponteiros

    fclose(arquivo); // fecha o arquivo para reabrir do inicio

    arquivo = fopen(path, "r"); // reabre o arquivo

    if (arquivo == NULL) { // se der erro na reabertura
        printf("Erro ao abrir o arquivo.\n");  // mostra mensagem
        return; // encerra
    }

    fgets(linha, sizeof(linha), arquivo); // le e descarta o cabecalho novamente

    for (i = 0; i < count; i++) { // percorre todas as linhas de dados
        fgets(linha, sizeof(linha), arquivo); // le uma linha do arquivo
        remover_quebra_linha(linha); // remove a quebra de linha no final
        colecao->restaurantes[i] = parse_restaurante(linha); // converte a linha em restaurante
    }

    fclose(arquivo); // fecha o arquivo
}

// funcao que cria a colecao e le o arquivo padrao 
ColecaoRestaurantes* ler_csv() {
    ColecaoRestaurantes* colecao = (ColecaoRestaurantes*) malloc(sizeof(ColecaoRestaurantes)); // aloca memoria para a colecao

    colecao->tamanho = 0; // comeca com tamanho 0
    colecao->restaurantes = NULL; // comeca sem vetor

    ler_csv_colecao(colecao, "/tmp/restaurantes.csv"); // le o arquivo padrao

    return colecao; // retorna a colecao pronta
}

// funcao que busca um restaurante pelo id
Restaurante* buscar_por_id(ColecaoRestaurantes* colecao, int id) {
    Restaurante* resp = NULL; // comeca supondo que nao encontrou
    int i = 0; // indice para percorrer o vetor

    for (i = 0; i < colecao->tamanho; i++) { // percorre toda a colecao
        if (colecao->restaurantes[i]->id == id) {  // se o id atual for igual ao procurado
            resp = colecao->restaurantes[i]; // guarda o restaurante encontrado
            i = colecao->tamanho; // força a saida do laco
        }
    }

    return resp; // retorna o restaurante encontrado ou NULL
}

// programa principal
int main() {
    ColecaoRestaurantes* colecao = ler_csv(); // le o csv inteiro e monta a colecao
    int id; // guarda o id digitado
    Restaurante* restaurante; // guarda o restaurante encontrado
    char buffer[1000]; // buffer para a string formatada do restaurante

    scanf("%d", &id); // le o primeiro id

    while (id != -1) { // continua enquanto o id for diferente de -1
        restaurante = buscar_por_id(colecao, id); // busca o restaurante correspondente ao id

        if (restaurante != NULL) { // se encontrou o restaurante
            formatar_restaurante(restaurante, buffer); // formata o restaurante no buffer
            printf("%s\n", buffer); // imprime o restaurante formatado
        }

        scanf("%d", &id); // le o proximo id
    }

    return 0;                               
}
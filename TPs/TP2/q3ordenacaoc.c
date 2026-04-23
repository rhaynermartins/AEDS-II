#include <stdio.h>   
#include <stdlib.h>  
#include <time.h>    

#define MATRICULA "898128" // define a matricula

// struct Data
typedef struct Data { 
    int ano;   // guarda o ano
    int mes;   // guarda o mes
    int dia;   // guarda o dia
} Data;        

// struct Hora
typedef struct Hora { 
    int hora;    // guarda a hora
    int minuto;  // guarda o minuto
} Hora;          

// struct Restaurante
typedef struct Restaurante { 
    int id;            // guarda o id 
    char nome[120];    // guarda o nome 
    char cidade[120];  // guarda a cidade
    int capacidade;    // guarda a capacidade maxima
    double avaliacao;  // guarda a avaliacao

    char tipos_cozinha[20][80]; // guarda os tipos de cozinha em um vetor de strings
    int quantidade_tipos;  // guarda quantos tipos de cozinha existem

    int faixa_preco;            // guarda a faixa de preco convertida para 1, 2, 3 ou 4
    Hora horario_abertura;      // guarda o horario de abertura
    Hora horario_fechamento;    // guarda o horario de fechamento
    Data data_abertura;         // guarda a data de abertura
    int aberto;                 // guarda 1 para true e 0 para false
} Restaurante;                  // nome final do tipo

// struct ColecaoRestaurantes
typedef struct ColecaoRestaurantes { 
    int tamanho;   // guarda a quantidade de restaurantes na colecao
    Restaurante** restaurantes; // guarda um vetor de ponteiros para restaurantes
} ColecaoRestaurantes; 

// calcula tamanho de texto
int tamanho_texto(char* s) { // funcao que calcula o tamanho de uma string
    int i = 0; // comeca o indice em 0

    while (s[i] != '\0') {   // percorre a string ate fim
        i++; // aumenta o contador
    }

    return i; // retorna a quantidade de caracteres
}

// compara dois textos
int comparar_texto(char* a, char* b) { // funcao que compara duas strings 
    int i = 0; // indice para percorrer as strings

    while (a[i] != '\0' && b[i] != '\0') { 
        if (a[i] != b[i]) {      // se encontrar caracteres diferentes
            return a[i] - b[i];  // retorna a diferenca entre eles
        }
        i++; // avanca para o proximo caractere
    }

    return a[i] - b[i]; // se uma terminou antes da outra, retorna a diferenca final
}

// copia texto
void copiar_texto(char* destino, char* origem) {  // funcao que copia uma string para outra
    int i = 0; // indice para percorrer a origem

    while (origem[i] != '\0') { // enquanto nao chegar no fim da origem
        destino[i] = origem[i]; // copia o caractere atual
        i++; // avanca
    }

    destino[i] = '\0';  // coloca o fim da string no destino
}

// remove quebra de linha
void remover_quebra_linha(char* s) { // funcao que remove '\n' da string
    int i = 0; // indice para percorrer a string

    while (s[i] != '\0') {   // percorre a string toda
        if (s[i] == '\n') {  // se encontrar quebra de linha
            s[i] = '\0';     // troca por fim de string
        }
        i++; // avanca
    }
}

// converte faixa preco para int
int parse_faixa_preco(char* s) { // funcao que transforma "$", "$$", "$$$", "$$$$" em 1, 2, 3, 4
    int resp = 0; // variavel de resposta

    if (comparar_texto(s, "$") == 0) {            // se a string for "$"
        resp = 1;                                 // resposta vira 1
    } else if (comparar_texto(s, "$$") == 0) {    // se a string for "$$"
        resp = 2;                                 // resposta vira 2
    } else if (comparar_texto(s, "$$$") == 0) {   // se a string for "$$$"
        resp = 3;                                 // resposta vira 3
    } else if (comparar_texto(s, "$$$$") == 0) {  // se a string for "$$$$"
        resp = 4;                                 // resposta vira 4
    }

    return resp; // retorna o valor convertido
}

// converte inteiro -> faixa preco texto
void formatar_faixa_preco(int faixa_preco, char* buffer) { // funcao que faz o caminho contrario
    int i = 0;                                             // indice do buffer

    while (i < faixa_preco) {                              // repete faixa_preco vezes
        buffer[i] = '$';                                   // coloca um '$' na posicao atual
        i++;                                               // avanca
    }

    buffer[i] = '\0';                                      // finaliza a string
}

// parse data
Data parse_data(char* s) { // funcao que recebe "YYYY-MM-DD" e retorna uma struct Data
    Data data;             // cria uma variavel do tipo Data

    sscanf(s, "%d-%d-%d", &data.ano, &data.mes, &data.dia); // le ano, mes e dia da string

    return data; // retorna a struct preenchida
}

// formata data
void formatar_data(Data* data, char* buffer) { // funcao que formata a data como DD/MM/YYYY
    char dia_string[10];                       // string auxiliar para o dia
    char mes_string[10];                       // string auxiliar para o mes

    if (data->dia < 10) {                      // se o dia tiver um digito
        sprintf(dia_string, "0%d", data->dia); // coloca zero na frente
    } else {                                   // senao
        sprintf(dia_string, "%d", data->dia);  // escreve normal
    }

    if (data->mes < 10) {                      // se o mes tiver um digito
        sprintf(mes_string, "0%d", data->mes); // coloca zero na frente
    } else {                                   // senao
        sprintf(mes_string, "%d", data->mes);  // escreve normal
    }

    sprintf(buffer, "%s/%s/%d", dia_string, mes_string, data->ano); // monta a data final
}

// parse hora
Hora parse_hora(char* s) { // funcao que recebe "HH:mm" e retorna uma struct Hora
    Hora hora;             // cria uma variavel do tipo Hora

    sscanf(s, "%d:%d", &hora.hora, &hora.minuto); // le hora e minuto da string

    return hora; // retorna a struct preenchida
}

// formata hora
void formatar_hora(Hora* hora, char* buffer) { // funcao que formata a hora como HH:mm
    char hora_string[10];                      // string auxiliar para a hora
    char minuto_string[10];                    // string auxiliar para o minuto

    if (hora->hora < 10) {                     // se a hora tiver um digito
        sprintf(hora_string, "0%d", hora->hora); // coloca zero na frente
    } else {                                     // senao
        sprintf(hora_string, "%d", hora->hora);  // escreve normal
    }

    if (hora->minuto < 10) {                     // se o minuto tiver um digito
        sprintf(minuto_string, "0%d", hora->minuto); // coloca zero na frente
    } else {                                        // senao
        sprintf(minuto_string, "%d", hora->minuto); // escreve normal
    }

    sprintf(buffer, "%s:%s", hora_string, minuto_string); // monta a hora final
}

// conta tipos de cozinha
int contar_tipos_cozinha(char* s) { // funcao que conta quantos tipos existem na string
    int i = 0;                      // indice para percorrer a string
    int count = 1;                  // assume 1 tipo inicialmente

    if (s[0] == '\0') {             // se a string estiver vazia
        return 0;                   // nao existe nenhum tipo
    }

    while (s[i] != '\0') {          // percorre toda a string
        if (s[i] == ';') {          // cada ';' separa um novo tipo
            count++;                // aumenta a contagem
        }
        i++;                        // avanca
    }

    return count;                   // retorna a quantidade de tipos
}

// separa tipos de cozinha
void parse_tipos_cozinha(char* s, Restaurante* restaurante) { // separa a string dos tipos em varias strings
    int i = 0;                                            // percorre a string original
    int j = 0;                                            // percorre a string do tipo atual
    int indice_tipo = 0;                                  // controla em qual tipo estamos

    restaurante->quantidade_tipos = contar_tipos_cozinha(s); // calcula quantos tipos existem

    while (s[i] != '\0') {                                   // percorre a string original
        if (s[i] == ';') {                                   // se achar ';'
            restaurante->tipos_cozinha[indice_tipo][j] = '\0'; // termina o tipo atual
            indice_tipo++;                                   // vai para o proximo tipo
            j = 0;                                           // reinicia a posicao interna
        } else {                                             // se nao for ';'
            restaurante->tipos_cozinha[indice_tipo][j] = s[i]; // copia o caractere
            j++;                                             // avanca na string do tipo
        }
        i++;                                                 // avanca na string original
    }

    restaurante->tipos_cozinha[indice_tipo][j] = '\0';      // finaliza o ultimo tipo
}

// parse restaurante
Restaurante* parse_restaurante(char* s) { // funcao que recebe uma linha do csv e cria um restaurante
    Restaurante* restaurante = (Restaurante*) malloc(sizeof(Restaurante)); // aloca memoria para um restaurante

    char tipos[200];     // guarda a string dos tipos de cozinha
    char faixa[10];      // guarda a faixa de preco em string
    char horario[20];    // guarda o horario completo
    char data[20];       // guarda a data completa
    char aberto[10];     // guarda "true" ou "false"

    char abertura[10];   // guarda a parte antes do '-'
    char fechamento[10]; // guarda a parte depois do '-'

    sscanf( // le todos os campos da linha do csv
        s,   // string de origem
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

    restaurante->horario_abertura = parse_hora(abertura);       // converte a abertura para Hora
    restaurante->horario_fechamento = parse_hora(fechamento);   // converte o fechamento para Hora

    restaurante->data_abertura = parse_data(data); // converte a data para Data

    if (comparar_texto(aberto, "true") == 0) { // se aberto for "true"
        restaurante->aberto = 1;               // guarda 1
    } else {                                   // senao
        restaurante->aberto = 0;               // guarda 0
    }

    return restaurante; // retorna o ponteiro para o restaurante criado
}

// formata tipos cozinha
void formatar_tipos_cozinha(Restaurante* restaurante, char* buffer) { // transforma os tipos no formato [a,b,c]
    int i = 0;     // percorre os tipos
    int pos = 0;   // controla a posicao atual no buffer
    int j;         // percorre os caracteres de cada tipo

    buffer[pos] = '['; // comeca com '['
    pos++;             // avanca
    buffer[pos] = '\0'; // coloca fim temporario

    for (i = 0; i < restaurante->quantidade_tipos; i++) { // percorre todos os tipos
        j = 0;                                             // comeca no primeiro caractere do tipo atual

        while (restaurante->tipos_cozinha[i][j] != '\0') { // copia caractere por caractere
            buffer[pos] = restaurante->tipos_cozinha[i][j]; // copia o caractere atual
            pos++;                                          // avanca no buffer
            j++;                                            // avanca no tipo
        }

        if (i < restaurante->quantidade_tipos - 1) { // se nao for o ultimo tipo
            buffer[pos] = ',';                       // coloca virgula
            pos++;                                   // avanca
        }
    }

    buffer[pos] = ']'; // fecha com ']'
    pos++;             // avanca
    buffer[pos] = '\0'; // finaliza a string
}

// formata restaurante
void formatar_restaurante(Restaurante* restaurante, char* buffer) { // monta a string completa do restaurante
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

    sprintf( // monta a string final
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

// le csv e preenche colecao
void ler_csv_colecao(ColecaoRestaurantes* colecao, char* path) { // le o arquivo csv e monta a colecao completa
    FILE* arquivo = fopen(path, "r"); // abre o arquivo para leitura
    char linha[512];                  // guarda cada linha lida
    int count = 0;                    // conta quantos restaurantes existem
    int i = 0;                        // indice de repeticao

    if (arquivo == NULL) {            // se nao conseguiu abrir o arquivo
        printf("Erro ao abrir o arquivo.\n"); // mostra erro
        return;                       // encerra a funcao
    }

    fgets(linha, sizeof(linha), arquivo); // le e ignora o cabecalho

    while (fgets(linha, sizeof(linha), arquivo) != NULL) { // conta as linhas de dados
        count++;                                           // aumenta a quantidade
    }

    colecao->tamanho = count; // guarda o total de restaurantes

    colecao->restaurantes = (Restaurante**) malloc(sizeof(Restaurante*) * count); // aloca o vetor de ponteiros

    fclose(arquivo); // fecha o arquivo para reabrir do inicio

    arquivo = fopen(path, "r"); // reabre o arquivo

    if (arquivo == NULL) { // se der erro na reabertura
        printf("Erro ao abrir o arquivo.\n"); // mostra erro
        return; // encerra
    }

    fgets(linha, sizeof(linha), arquivo); // le e ignora o cabecalho novamente

    for (i = 0; i < count; i++) { // percorre todas as linhas de dados
        fgets(linha, sizeof(linha), arquivo); // le uma linha
        remover_quebra_linha(linha);          // remove o '\n'
        colecao->restaurantes[i] = parse_restaurante(linha); // transforma a linha em restaurante
    }

    fclose(arquivo); // fecha o arquivo
}

// funcao que cria a colecao e le o arquivo padrao
ColecaoRestaurantes* ler_csv() { // cria a colecao e manda ler /tmp/restaurantes.csv
    ColecaoRestaurantes* colecao = (ColecaoRestaurantes*) malloc(sizeof(ColecaoRestaurantes)); // aloca memoria

    colecao->tamanho = 0;   // comeca com tamanho 0
    colecao->restaurantes = NULL; // comeca sem vetor

    ler_csv_colecao(colecao, "/tmp/restaurantes.csv"); // le o arquivo padrao

    return colecao; // retorna a colecao pronta
}

// busca por id
Restaurante* buscar_por_id(ColecaoRestaurantes* colecao, int id) { // procura um restaurante pelo id
    Restaurante* resp = NULL; // comeca supondo que nao encontrou
    int i = 0;                // indice para percorrer a colecao

    for (i = 0; i < colecao->tamanho; i++) { // percorre todos os restaurantes
        if (colecao->restaurantes[i]->id == id) { // se o id atual for igual ao procurado
            resp = colecao->restaurantes[i];      // guarda o restaurante encontrado
            i = colecao->tamanho;                 // força a saida do laco
        }
    }

    return resp; // retorna o restaurante ou NULL
}

// selection sort por nome
void selecao_por_nome(Restaurante** array, int n, long long* comparacoes, long long* movimentacoes) { // ordena por nome usando selection sort
    int i;            // indice externo
    int j;            // indice interno
    int menor;        // guarda a posicao do menor nome
    Restaurante* temp; // variavel auxiliar para troca

    for (i = 0; i < n - 1; i++) { // percorre o vetor da primeira ate a penultima posicao
        menor = i;                // assume que o menor esta na posicao atual

        for (j = i + 1; j < n; j++) { // procura o menor nome no restante do vetor
            (*comparacoes)++;         // conta uma comparacao entre elementos

            if (comparar_texto(array[j]->nome, array[menor]->nome) < 0) { // se o nome atual for menor
                menor = j; // atualiza a posicao do menor
            }
        }

        if (menor != i) {      // se o menor nao estiver na posicao atual
            temp = array[i];   // guarda o elemento da posicao i
            array[i] = array[menor]; // coloca o menor na posicao i
            array[menor] = temp;     // coloca o antigo array[i] na posicao do menor
            (*movimentacoes) += 3;   // conta 3 movimentacoes pela troca
        }
    }
}

// grava arquivo de log
void criar_log(long long comparacoes, long long movimentacoes, double tempo) { // cria o arquivo de log pedido
    FILE* log = fopen(MATRICULA "_selecao.txt", "w"); // abre o arquivo no modo escrita

    if (log != NULL) { // se conseguiu abrir
        fprintf(log, "%s\t%lld\t%lld\t%lf", MATRICULA, comparacoes, movimentacoes, tempo); // escreve os dados
        fclose(log); // fecha o arquivo
    }
}

// programa principal
int main() { // funcao principal
    ColecaoRestaurantes* colecao = ler_csv(); // le o csv inteiro e monta a colecao completa
    Restaurante* selecionados[1000];          // vetor que vai guardar so os restaurantes escolhidos pela entrada
    int quantidade = 0;                       // quantidade de restaurantes selecionados
    int id;                                   // guarda o id lido da entrada
    int i;                                    // indice de repeticao
    char buffer[1000];                        // buffer para imprimir o restaurante formatado

    long long comparacoes = 0;                // contador de comparacoes da ordenacao
    long long movimentacoes = 0;              // contador de movimentacoes da ordenacao

    clock_t inicio;                           // guarda o instante inicial
    clock_t fim;                              // guarda o instante final
    double tempo;                             // guarda o tempo total da ordenacao

    scanf("%d", &id);                         // le o primeiro id digitado

    while (id != -1) {                       // continua ate o usuario digitar -1
        Restaurante* restaurante = buscar_por_id(colecao, id); // busca o restaurante correspondente

        if (restaurante != NULL) {           // se encontrou o restaurante
            selecionados[quantidade] = restaurante; // guarda no vetor de selecionados
            quantidade++;                    // aumenta a quantidade
        }

        scanf("%d", &id);                    // le o proximo id
    }

    inicio = clock();                        // marca o tempo inicial
    selecao_por_nome(selecionados, quantidade, &comparacoes, &movimentacoes); // ordena por nome
    fim = clock();                           // marca o tempo final

    tempo = ((double)(fim - inicio)) / CLOCKS_PER_SEC; // calcula o tempo em segundos

    for (i = 0; i < quantidade; i++) {      // percorre o vetor ja ordenado
        formatar_restaurante(selecionados[i], buffer); // formata o restaurante atual
        printf("%s\n", buffer);             // imprime o restaurante atual
    }

    criar_log(comparacoes, movimentacoes, tempo); // cria o arquivo de log

    return 0;                               // encerra o programa
}
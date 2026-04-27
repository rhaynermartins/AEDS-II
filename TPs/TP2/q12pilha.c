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
    int hora;   // guarda a hora
    int minuto; // guarda o minuto
} Hora;               

// struct Restaurante
typedef struct Restaurante { 
    int id;             // guarda o id
    char nome[120];     // guarda o nome
    char cidade[120];   // guarda a cidade
    int capacidade;     // guarda a capacidade maxima
    double avaliacao;   // guarda a avaliacao

    char tipos_cozinha[20][80]; // vetor com os tipos de cozinha
    int quantidade_tipos;       // quantos tipos de cozinha existem

    int faixa_preco;          // faixa de preco convertida para 1, 2, 3 ou 4
    Hora horario_abertura;    // horario de abertura
    Hora horario_fechamento;  // horario de fechamento
    Data data_abertura;       // data de abertura
    int aberto;               // 1 para true e 0 para false
} Restaurante;                  

// struct ColecaoRestaurantes
typedef struct ColecaoRestaurantes { 
    int tamanho; // quantidade de restaurantes na colecao
    Restaurante** restaurantes; // vetor de ponteiros para restaurantes
} ColecaoRestaurantes;             

// struct Pilha
typedef struct Pilha {
    Restaurante** array; // vetor da pilha
    int topo; // posicao do topo
    int tamanho; // capacidade maxima da pilha
} Pilha;

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

// parse data
Data parse_data(char* s) { // recebe "YYYY-MM-DD" e retorna uma struct Data
    Data data; // cria uma variavel do tipo Data

    sscanf(s, "%d-%d-%d", &data.ano, &data.mes, &data.dia); // le ano, mes e dia da string

    return data; // retorna a struct preenchida
}

// formata data
void formatar_data(Data* data, char* buffer) { // formata a data como DD/MM/YYYY
    char dia_string[10]; // string auxiliar para o dia
    char mes_string[10]; // string auxiliar para o mes

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
Hora parse_hora(char* s) { // recebe "HH:mm" e retorna uma struct Hora
    Hora hora; // cria uma variavel do tipo Hora

    sscanf(s, "%d:%d", &hora.hora, &hora.minuto); // le hora e minuto da string

    return hora; // retorna a struct preenchida
}

// formata hora
void formatar_hora(Hora* hora, char* buffer) { // formata a hora como HH:mm
    char hora_string[10]; // string auxiliar para a hora
    char minuto_string[10]; // string auxiliar para o minuto

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

// converte faixa preco para int
int parse_faixa_preco(char* s) { 
    if (comparar_texto(s, "$") == 0) { // se a string for "$"
        return 1; // retorna 1
    } else if (comparar_texto(s, "$$") == 0) { // se a string for "$$"
        return 2; // retorna 2
    } else if (comparar_texto(s, "$$$") == 0) { // se a string for "$$$"
        return 3; // retorna 3
    } else if (comparar_texto(s, "$$$$") == 0) { // se a string for "$$$$"
        return 4; // retorna 4
    }

    return 0; // se nao encontrar, retorna 0
}

// converte faixa preco de inteiro para texto
void formatar_faixa_preco(int faixa_preco, char* buffer) {
    int i = 0; // indice do buffer

    while (i < faixa_preco) { // repete enquanto i for menor que faixa_preco
        buffer[i] = '$'; // coloca um '$'
        i++; // avanca
    }

    buffer[i] = '\0'; // finaliza a string
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
    int i = 0; // percorre a string original
    int j = 0; // percorre a string do tipo atual
    int indice_tipo = 0; // controla em qual tipo estamos

    restaurante->quantidade_tipos = contar_tipos_cozinha(s); // calcula quantos tipos existem

    while (s[i] != '\0') { // percorre a string original
        if (s[i] == ';') { // se encontrar ';'
            restaurante->tipos_cozinha[indice_tipo][j] = '\0'; // termina o tipo atual
            indice_tipo++; // vai para o proximo tipo
            j = 0; // reinicia o indice interno
        } else {
            restaurante->tipos_cozinha[indice_tipo][j] = s[i]; // copia o caractere atual
            j++; // avanca dentro do tipo atual
        }
        i++; // avanca na string original
    }

    restaurante->tipos_cozinha[indice_tipo][j] = '\0'; // finaliza o ultimo tipo
}

// parse restaurante
Restaurante* parse_restaurante(char* s) {
    Restaurante* restaurante = (Restaurante*) malloc(sizeof(Restaurante)); // aloca memoria para um restaurante

    char tipos[200]; // guarda a string dos tipos de cozinha
    char faixa[10]; // guarda a faixa de preco em string
    char horario[20]; // guarda o horario completo
    char data[20]; // guarda a data completa
    char aberto[10]; // guarda "true" ou "false"

    char abertura[10]; // guarda a abertura do horario
    char fechamento[10]; // guarda o fechamento do horario

    sscanf(
        s, // linha original
        "%d,%119[^,],%119[^,],%d,%lf,%199[^,],%9[^,],%19[^,],%19[^,],%9s", // formato de leitura
        &restaurante->id, // le o id
        restaurante->nome, // le o nome
        restaurante->cidade, // le a cidade
        &restaurante->capacidade, // le a capacidade
        &restaurante->avaliacao, // le a avaliacao
        tipos, // le os tipos de cozinha
        faixa, // le a faixa de preco
        horario, // le o horario
        data, // le a data
        aberto // le o campo aberto
    );

    parse_tipos_cozinha(tipos, restaurante); // separa os tipos de cozinha

    restaurante->faixa_preco = parse_faixa_preco(faixa); // converte a faixa de preco

    sscanf(horario, "%9[^-]-%9s", abertura, fechamento); // separa o horario

    restaurante->horario_abertura = parse_hora(abertura); // converte a abertura
    restaurante->horario_fechamento = parse_hora(fechamento); // converte o fechamento
    restaurante->data_abertura = parse_data(data); // converte a data

    if (comparar_texto(aberto, "true") == 0) { // se aberto for true
        restaurante->aberto = 1; // guarda 1
    } else {
        restaurante->aberto = 0; // senao guarda 0
    }

    return restaurante; // retorna o restaurante criado
}

// formata tipos cozinha em [a,b,c]
void formatar_tipos_cozinha(Restaurante* restaurante, char* buffer) {
    int i = 0; // percorre os tipos
    int pos = 0; // controla a posicao atual no buffer
    int j; // percorre os caracteres de cada tipo

    buffer[pos] = '['; // coloca '[' no inicio
    pos++; // avanca
    buffer[pos] = '\0'; // coloca fim temporario

    for (i = 0; i < restaurante->quantidade_tipos; i++) { // percorre todos os tipos
        j = 0; // comeca no primeiro caractere do tipo atual

        while (restaurante->tipos_cozinha[i][j] != '\0') { // copia caractere por caractere
            buffer[pos] = restaurante->tipos_cozinha[i][j]; // copia o caractere atual
            pos++; // avanca no buffer
            j++; // avanca no tipo
        }

        if (i < restaurante->quantidade_tipos - 1) { // se nao for o ultimo
            buffer[pos] = ','; // coloca virgula
            pos++; // avanca
        }
    }

    buffer[pos] = ']'; // fecha com ]
    pos++; // avanca
    buffer[pos] = '\0'; // finaliza a string
}

// formata restaurante completo
void formatar_restaurante(Restaurante* restaurante, char* buffer) {
    char tipos_string[300]; // guarda os tipos formatados
    char faixa_string[10]; // guarda a faixa formatada
    char abertura_string[10]; // guarda a hora de abertura formatada
    char fechamento_string[10]; // guarda a hora de fechamento formatada
    char data_string[20]; // guarda a data formatada

    formatar_tipos_cozinha(restaurante, tipos_string); // formata os tipos
    formatar_faixa_preco(restaurante->faixa_preco, faixa_string); // formata a faixa
    formatar_hora(&restaurante->horario_abertura, abertura_string); // formata a abertura
    formatar_hora(&restaurante->horario_fechamento, fechamento_string); // formata o fechamento
    formatar_data(&restaurante->data_abertura, data_string); // formata a data

    sprintf(
        buffer, // escreve no buffer
        "[%d ## %s ## %s ## %d ## %.1lf ## %s ## %s ## %s-%s ## %s ## %s]", // formato de saida
        restaurante->id, // id
        restaurante->nome, // nome
        restaurante->cidade, // cidade
        restaurante->capacidade, // capacidade
        restaurante->avaliacao, // avaliacao
        tipos_string, // tipos de cozinha
        faixa_string, // faixa de preco
        abertura_string, // abertura
        fechamento_string, // fechamento
        data_string, // data
        restaurante->aberto ? "true" : "false" // aberto
    );
}

// le csv e preenche a colecao completa
void ler_csv_colecao(ColecaoRestaurantes* colecao, char* path) {
    FILE* arquivo = fopen(path, "r"); // abre o arquivo para leitura
    char linha[512]; // guarda cada linha lida
    int count = 0; // conta quantos restaurantes existem
    int i = 0; // indice de repeticao

    if (arquivo == NULL) { // se nao conseguiu abrir
        return; // encerra
    }

    fgets(linha, sizeof(linha), arquivo); // le e ignora o cabecalho

    while (fgets(linha, sizeof(linha), arquivo) != NULL) { // conta as linhas de dados
        count++; // aumenta a contagem
    }

    colecao->tamanho = count; // guarda o total de restaurantes
    colecao->restaurantes = (Restaurante**) malloc(sizeof(Restaurante*) * count); // aloca o vetor de ponteiros

    fclose(arquivo); // fecha o arquivo para reabrir

    arquivo = fopen(path, "r"); // reabre o arquivo

    if (arquivo == NULL) { // se nao conseguiu abrir de novo
        return; // encerra
    }

    fgets(linha, sizeof(linha), arquivo); // le e ignora o cabecalho novamente

    for (i = 0; i < count; i++) { // percorre todas as linhas de dados
        fgets(linha, sizeof(linha), arquivo); // le uma linha
        limpar_final_linha(linha); // remove \n e \r
        colecao->restaurantes[i] = parse_restaurante(linha); // transforma a linha em restaurante
    }

    fclose(arquivo); // fecha o arquivo
}

// cria a colecao e le o arquivo padrao
ColecaoRestaurantes* ler_csv() {
    ColecaoRestaurantes* colecao = (ColecaoRestaurantes*) malloc(sizeof(ColecaoRestaurantes)); // aloca memoria

    colecao->tamanho = 0; // começa com tamanho 0
    colecao->restaurantes = NULL; // começa sem vetor

    ler_csv_colecao(colecao, "/tmp/restaurantes.csv"); // le o arquivo padrao

    return colecao; // retorna a colecao pronta
}

// busca um restaurante pelo id
Restaurante* buscar_por_id(ColecaoRestaurantes* colecao, int id) {
    int i; // indice para percorrer a colecao

    for (i = 0; i < colecao->tamanho; i++) { // percorre todos os restaurantes
        if (colecao->restaurantes[i]->id == id) { // se o id atual for igual ao procurado
            return colecao->restaurantes[i]; // retorna o restaurante encontrado
        }
    }

    return NULL; // retorna NULL se nao encontrar
}

// cria a pilha
void iniciar_pilha(Pilha* pilha, int tamanho) {
    pilha->array = (Restaurante**) malloc(sizeof(Restaurante*) * tamanho); // aloca o vetor da pilha
    pilha->topo = 0; // começa com topo zero
    pilha->tamanho = tamanho; // guarda o tamanho maximo
}

// empilha um restaurante
void empilhar(Pilha* pilha, Restaurante* restaurante) {
    if (pilha->topo < pilha->tamanho) { // se ainda houver espaco
        pilha->array[pilha->topo] = restaurante; // coloca o restaurante no topo atual
        pilha->topo++; // avanca o topo
    }
}

// desempilha e retorna o restaurante do topo
Restaurante* desempilhar(Pilha* pilha) {
    Restaurante* resp = NULL; // começa com resposta nula

    if (pilha->topo > 0) { // se a pilha nao estiver vazia
        pilha->topo--; // volta uma posicao no topo
        resp = pilha->array[pilha->topo]; // pega o elemento do topo
    }

    return resp; // retorna o restaurante removido
}

// mostra a pilha do topo para a base
void mostrar_pilha(Pilha* pilha) {
    int i; // indice de repeticao
    char buffer[1000]; // buffer para formatar o restaurante

    for (i = pilha->topo - 1; i >= 0; i--) { // percorre da ultima posicao ocupada ate a primeira
        formatar_restaurante(pilha->array[i], buffer); // formata o restaurante atual
        printf("[%d] %s\n", i, buffer); // imprime a posicao e o restaurante
    }
}

// programa principal
int main() {
    ColecaoRestaurantes* colecao = ler_csv(); // le o csv inteiro e monta a colecao
    Pilha pilha; // declara a pilha
    char linha[256]; // guarda cada linha da entrada
    int id; // guarda os ids lidos
    int n; // quantidade de comandos
    int i; // indice de repeticao
    int continuar = 1; // controla a leitura da primeira parte

    iniciar_pilha(&pilha, 1000); // inicia a pilha com tamanho suficiente

    while (continuar == 1 && fgets(linha, sizeof(linha), stdin) != NULL) { // le a primeira parte da entrada
        limpar_final_linha(linha); // limpa \n e \r

        sscanf(linha, "%d", &id); // converte a linha para inteiro

        if (id == -1) { // se encontrar -1
            continuar = 0; // encerra a primeira parte
        } else {
            Restaurante* restaurante = buscar_por_id(colecao, id); // busca o restaurante pelo id

            if (restaurante != NULL) { // se encontrou
                empilhar(&pilha, restaurante); // empilha o restaurante
            }
        }
    }

    fgets(linha, sizeof(linha), stdin); // le a linha com a quantidade de comandos
    limpar_final_linha(linha); // limpa \n e \r
    sscanf(linha, "%d", &n); // converte para inteiro

    for (i = 0; i < n; i++) { // percorre todos os comandos
        fgets(linha, sizeof(linha), stdin); // le uma linha de comando
        limpar_final_linha(linha); // limpa \n e \r

        if (linha[0] == 'I') { // se o comando for inserir
            sscanf(linha + 2, "%d", &id); // le o id depois do "I "
            Restaurante* restaurante = buscar_por_id(colecao, id); // busca o restaurante pelo id

            if (restaurante != NULL) { // se encontrou
                empilhar(&pilha, restaurante); // empilha o restaurante
            }
        } else if (linha[0] == 'R') { // se o comando for remover
            Restaurante* removido = desempilhar(&pilha); // desempilha o restaurante do topo

            if (removido != NULL) { // se conseguiu remover
                printf("(R)%s\n", removido->nome); // imprime o nome removido sem espaco
            }
        }
    }

    mostrar_pilha(&pilha); // mostra a pilha final do topo para a base

    return 0; 
}
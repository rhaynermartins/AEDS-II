#include <stdio.h>
#include <stdlib.h>

#define MATRICULA "898128" // matricula
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

    while (s[i] != '\0') { // percorre ate o fim da string
        i++; // avanca
    }

    fim = i - 1; // posiciona no ultimo caractere valido

    while (fim >= 0 && (s[fim] == '\n' || s[fim] == '\r')) { // enquanto houver quebra de linha
        s[fim] = '\0'; // troca por fim de string
        fim--; // volta uma posicao
    }
}

// parse data
Data parse_data(char* s) {
    Data data; // cria uma data

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
    Hora hora; // cria uma hora

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

    while (i < faixa_preco) { // repete de acordo com a faixa
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

    return count; // retorna a quantidade
}

// separa tipos de cozinha
void parse_tipos_cozinha(char* s, Restaurante* restaurante) {
    int i = 0; // percorre a string original
    int j = 0; // percorre o tipo atual
    int indice_tipo = 0; // controla o tipo atual

    restaurante->quantidade_tipos = contar_tipos_cozinha(s); // guarda a quantidade de tipos

    while (s[i] != '\0') { // percorre a string original
        if (s[i] == ';') { // se encontrar separador
            restaurante->tipos_cozinha[indice_tipo][j] = '\0'; // finaliza o tipo atual
            indice_tipo++; // vai para o proximo tipo
            j = 0; // reinicia o indice interno
        } else {
            restaurante->tipos_cozinha[indice_tipo][j] = s[i]; // copia o caractere
            j++; // avanca no tipo
        }

        i++; // avanca na string original
    }

    restaurante->tipos_cozinha[indice_tipo][j] = '\0'; // finaliza o ultimo tipo
}

// parse restaurante
Restaurante* parse_restaurante(char* s) {
    Restaurante* restaurante = (Restaurante*) malloc(sizeof(Restaurante)); // aloca memoria para restaurante

    char tipos[200]; // guarda os tipos
    char faixa[10]; // guarda a faixa
    char horario[20]; // guarda o horario
    char data[20]; // guarda a data
    char aberto[10]; // guarda true ou false

    char abertura[10]; // guarda abertura
    char fechamento[10]; // guarda fechamento

    sscanf(
        s, // linha original
        "%d,%119[^,],%119[^,],%d,%lf,%199[^,],%9[^,],%19[^,],%19[^,],%9s", // formato de leitura
        &restaurante->id, // le o id
        restaurante->nome, // le o nome
        restaurante->cidade, // le a cidade
        &restaurante->capacidade, // le a capacidade
        &restaurante->avaliacao, // le a avaliacao
        tipos, // le os tipos
        faixa, // le a faixa
        horario, // le o horario
        data, // le a data
        aberto // le aberto
    );

    parse_tipos_cozinha(tipos, restaurante); // separa os tipos

    restaurante->faixa_preco = parse_faixa_preco(faixa); // converte a faixa

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
    int j; // percorre caracteres do tipo

    buffer[pos] = '['; // coloca [
    pos++; // avanca
    buffer[pos] = '\0'; // finaliza temporariamente

    for (i = 0; i < restaurante->quantidade_tipos; i++) { // percorre tipos
        j = 0; // inicia no primeiro caractere

        while (restaurante->tipos_cozinha[i][j] != '\0') { // copia tipo
            buffer[pos] = restaurante->tipos_cozinha[i][j]; // copia caractere
            pos++; // avanca no buffer
            j++; // avanca no tipo
        }

        if (i < restaurante->quantidade_tipos - 1) { // se nao for ultimo
            buffer[pos] = ','; // coloca virgula
            pos++; // avanca
        }
    }

    buffer[pos] = ']'; // coloca ]
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
        buffer, // escreve no buffer
        "[%d ## %s ## %s ## %d ## %.1lf ## %s ## %s ## %s-%s ## %s ## %s]", // formato de saida
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
    char linha[512]; // guarda linha
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

// busca por id
Restaurante* buscar_por_id(ColecaoRestaurantes* colecao, int id) {
    int i; // indice

    for (i = 0; i < colecao->tamanho; i++) { // percorre colecao
        if (colecao->restaurantes[i]->id == id) { // se encontrar id
            return colecao->restaurantes[i]; // retorna restaurante
        }
    }

    return NULL; // retorna NULL se nao encontrar
}

// compara por data de abertura e desempata por nome
int comparar_restaurantes(Restaurante* a, Restaurante* b, long long* comparacoes) {
    int resp = 0; // guarda o resultado da comparacao

    (*comparacoes)++; // conta a comparacao do ano

    if (a->data_abertura.ano < b->data_abertura.ano) { // se o ano de a for menor
        resp = -1; // a vem antes
    } else if (a->data_abertura.ano > b->data_abertura.ano) { // se o ano de a for maior
        resp = 1; // a vem depois
    } else { // se o ano for igual
        (*comparacoes)++; // conta a comparacao do mes

        if (a->data_abertura.mes < b->data_abertura.mes) { // se o mes de a for menor
            resp = -1; // a vem antes
        } else if (a->data_abertura.mes > b->data_abertura.mes) { // se o mes de a for maior
            resp = 1; // a vem depois
        } else { // se o mes for igual
            (*comparacoes)++; // conta a comparacao do dia

            if (a->data_abertura.dia < b->data_abertura.dia) { // se o dia de a for menor
                resp = -1; // a vem antes
            } else if (a->data_abertura.dia > b->data_abertura.dia) { // se o dia de a for maior
                resp = 1; // a vem depois
            } else { // se a data inteira for igual
                (*comparacoes)++; // conta a comparacao do nome
                resp = comparar_texto(a->nome, b->nome); // desempata pelo nome
            }
        }
    }

    return resp; // retorna o resultado final
}

// troca dois restaurantes
void trocar(Restaurante** array, int i, int j, long long* movimentacoes) {
    Restaurante* temp = array[i]; // guarda o elemento i
    array[i] = array[j]; // coloca j em i
    array[j] = temp; // coloca temp em j
    (*movimentacoes) += 3; // conta 3 movimentacoes
}

// reconstruir heap maximo descendo a raiz
void reconstruir(Restaurante** array, int tamHeap, long long* comparacoes, long long* movimentacoes) {
    int i = 0; // comeca pela raiz
    int filho; // guarda o maior filho
    int continuar = 1; // controla o laco sem usar break

    while (continuar == 1 && (2 * i + 1) < tamHeap) { // enquanto existir filho esquerdo
        filho = 2 * i + 1; // assume que o maior filho e o esquerdo

        if (filho + 1 < tamHeap && comparar_restaurantes(array[filho + 1], array[filho], comparacoes) > 0) { // se existir filho direito e ele for maior
            filho = filho + 1; // atualiza para o filho direito

        }
        if (comparar_restaurantes(array[filho], array[i], comparacoes) > 0) { // se o maior filho for maior que o pai
            trocar(array, i, filho, movimentacoes); // troca pai com filho
            i = filho; // continua descendo

        } else {
            continuar = 0; // encerra o laco

        }
    }
}

// constroi o heap subindo o ultimo elemento inserido
void construir(Restaurante** array, int tamHeap, long long* comparacoes, long long* movimentacoes) {
    int i = tamHeap - 1; // pega a ultima posicao do heap
    int pai = (i - 1) / 2; // calcula o pai da ultima posicao

    while (i > 0 && comparar_restaurantes(array[i], array[pai], comparacoes) > 0) { // enquanto o filho for maior que o pai
        trocar(array, i, pai, movimentacoes); // troca filho com pai
        i = pai; // atualiza a posicao atual
        pai = (i - 1) / 2; // recalcula o pai

    }
}

// heapsort parcial
void heapsort_parcial(Restaurante** array, int n, long long* comparacoes, long long* movimentacoes) {
    int limite = K; // define o limite da ordenacao parcial
    int tamHeap; // guarda o tamanho atual do heap
    int i; // indice de repeticao

    if (n < limite) { // se houver menos que K elementos
        limite = n; // ajusta o limite

    }

    for (tamHeap = 2; tamHeap <= limite; tamHeap++) { // constroi o heap com os K primeiros
        construir(array, tamHeap, comparacoes, movimentacoes); // insere o elemento atual no heap

    }

    for (i = limite; i < n; i++) { // percorre o restante do vetor
        if (comparar_restaurantes(array[i], array[0], comparacoes) < 0) { // se o elemento atual for menor que a raiz
            trocar(array, i, 0, movimentacoes); // troca o elemento atual com a raiz
            reconstruir(array, limite, comparacoes, movimentacoes); // reconstrói o heap com os K menores

        }

    }

    tamHeap = limite; // define o tamanho do heap parcial

    while (tamHeap > 1) { // ordena apenas os K primeiros
        trocar(array, 0, tamHeap - 1, movimentacoes); // coloca o maior do heap no fim da parte parcial
        tamHeap--; // diminui o tamanho do heap
        reconstruir(array, tamHeap, comparacoes, movimentacoes); // reconstrói o heap reduzido

    }
}

// cria arquivo de log
void criar_log(long long comparacoes, long long movimentacoes) {
    FILE* log = fopen(MATRICULA "_heapsort_parcial.txt", "w"); // abre log

    if (log != NULL) { // se abriu
        fprintf(log, "%s\t%lld\t%lld\t0.0", MATRICULA, comparacoes, movimentacoes); // escreve log
        fclose(log); // fecha log
    }
}

// programa principal
int main() {
    ColecaoRestaurantes* colecao = ler_csv(); // le colecao
    Restaurante** selecionados = (Restaurante**) malloc(sizeof(Restaurante*) * colecao->tamanho); // vetor de selecionados com tamanho do csv
    int quantidade = 0; // quantidade selecionada
    int id; // guarda id
    int i; // indice
    char buffer[1000]; // buffer de impressao
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

    heapsort_parcial(selecionados, quantidade, &comparacoes, &movimentacoes); // ordena parcialmente

    for (i = 0; i < quantidade; i++) { // imprime todos
        formatar_restaurante(selecionados[i], buffer); // formata
        printf("%s\n", buffer); // imprime
    }

    criar_log(comparacoes, movimentacoes); // cria log

    free(selecionados); // libera o vetor de selecionados

    return 0; 
}
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
    Restaurante* elemento; // guarda o restaurante da celula
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
        s[fim] = '\0'; // remove o caractere
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
        buffer[i] = '$'; // coloca $
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
    Restaurante* restaurante = (Restaurante*) malloc(sizeof(Restaurante)); // aloca restaurante

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

    for (i = 0; i < restaurante->quantidade_tipos; i++) { // percorre os tipos
        j = 0; // inicia no primeiro caractere do tipo

        while (restaurante->tipos_cozinha[i][j] != '\0') { // enquanto houver caractere
            buffer[pos] = restaurante->tipos_cozinha[i][j]; // copia o caractere
            pos++; // avanca no buffer
            j++; // avanca no tipo
        }

        if (i < restaurante->quantidade_tipos - 1) { // se nao for o ultimo tipo
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
    char tipos_string[300]; // guarda os tipos formatados
    char faixa_string[10]; // guarda a faixa formatada
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

    for (i = 0; i < colecao->tamanho; i++) { // percorre a colecao
        if (colecao->restaurantes[i]->id == id) { // se encontrar id
            return colecao->restaurantes[i]; // retorna restaurante
        }
    }

    return NULL; // retorna NULL se nao encontrar
}

// cria uma nova celula
Celula* nova_celula(Restaurante* restaurante) {
    Celula* celula = (Celula*) malloc(sizeof(Celula)); // aloca uma celula

    celula->elemento = restaurante; // guarda o restaurante
    celula->prox = NULL; // inicia sem proxima celula

    return celula; // retorna a celula criada
}

// inicia a lista com celula cabeca
void iniciar_lista(Lista* lista) {
    lista->primeiro = nova_celula(NULL); // cria a celula cabeca
    lista->ultimo = lista->primeiro; // ultimo aponta para a cabeca
    lista->tamanho = 0; // tamanho comeca em zero
}

// insere no inicio
void inserir_inicio(Lista* lista, Restaurante* restaurante) {
    Celula* tmp = nova_celula(restaurante); // cria nova celula

    tmp->prox = lista->primeiro->prox; // nova celula aponta para o antigo primeiro
    lista->primeiro->prox = tmp; // cabeca aponta para a nova celula

    if (lista->ultimo == lista->primeiro) { // se a lista estava vazia
        lista->ultimo = tmp; // ultimo passa a ser a nova celula
    }

    lista->tamanho++; // aumenta o tamanho
}

// insere no fim
void inserir_fim(Lista* lista, Restaurante* restaurante) {
    Celula* tmp = nova_celula(restaurante); // cria nova celula

    lista->ultimo->prox = tmp; // antigo ultimo aponta para nova celula
    lista->ultimo = tmp; // ultimo passa a ser a nova celula
    lista->tamanho++; // aumenta o tamanho
}

// insere em uma posicao especifica
void inserir_pos(Lista* lista, Restaurante* restaurante, int pos) {
    int i; // indice
    Celula* anterior = lista->primeiro; // comeca na cabeca
    Celula* tmp; // nova celula

    if (pos == 0) { // se for inicio
        inserir_inicio(lista, restaurante); // insere no inicio
    } else if (pos == lista->tamanho) { // se for fim
        inserir_fim(lista, restaurante); // insere no fim
    } else if (pos > 0 && pos < lista->tamanho) { // se for posicao intermediaria
        for (i = 0; i < pos; i++) { // anda ate a celula anterior
            anterior = anterior->prox; // avanca
        }

        tmp = nova_celula(restaurante); // cria nova celula
        tmp->prox = anterior->prox; // nova aponta para a proxima
        anterior->prox = tmp; // anterior aponta para nova
        lista->tamanho++; // aumenta tamanho
    }
}

// remove do inicio
Restaurante* remover_inicio(Lista* lista) {
    Restaurante* resp = NULL; // guarda o restaurante removido
    Celula* tmp; // guarda a celula removida

    if (lista->tamanho > 0) { // se a lista nao estiver vazia
        tmp = lista->primeiro->prox; // pega a primeira celula real
        resp = tmp->elemento; // guarda o restaurante removido

        lista->primeiro->prox = tmp->prox; // cabeca pula a primeira celula

        if (tmp == lista->ultimo) { // se removeu o ultimo elemento
            lista->ultimo = lista->primeiro; // ultimo volta para cabeca
        }

        free(tmp); // libera a celula removida
        lista->tamanho--; // diminui tamanho
    }

    return resp; // retorna removido
}

// remove do fim
Restaurante* remover_fim(Lista* lista) {
    Restaurante* resp = NULL; // guarda o restaurante removido
    Celula* anterior = lista->primeiro; // comeca na cabeca
    Celula* tmp; // guarda a celula removida

    if (lista->tamanho > 0) { // se a lista nao estiver vazia
        while (anterior->prox != lista->ultimo) { // vai ate a celula antes da ultima
            anterior = anterior->prox; // avanca
        }

        tmp = lista->ultimo; // pega a ultima celula
        resp = tmp->elemento; // guarda o restaurante

        anterior->prox = NULL; // nova ultima aponta para NULL
        lista->ultimo = anterior; // atualiza ultimo

        free(tmp); // libera celula removida
        lista->tamanho--; // diminui tamanho
    }

    return resp; // retorna removido
}

// remove de uma posicao especifica
Restaurante* remover_pos(Lista* lista, int pos) {
    int i; // indice
    Restaurante* resp = NULL; // guarda o restaurante removido
    Celula* anterior = lista->primeiro; // comeca na cabeca
    Celula* tmp; // guarda celula removida

    if (pos == 0) { // se for inicio
        resp = remover_inicio(lista); // remove inicio
    } else if (pos == lista->tamanho - 1) { // se for fim
        resp = remover_fim(lista); // remove fim
    } else if (pos > 0 && pos < lista->tamanho - 1) { // se for meio
        for (i = 0; i < pos; i++) { // anda ate anterior da posicao
            anterior = anterior->prox; // avanca
        }

        tmp = anterior->prox; // pega a celula removida
        resp = tmp->elemento; // guarda o restaurante

        anterior->prox = tmp->prox; // pula a celula removida

        free(tmp); // libera a celula
        lista->tamanho--; // diminui tamanho
    }

    return resp; // retorna removido
}

// mostra a lista
void mostrar(Lista* lista) {
    int i = 0; // indice de impressao
    char buffer[1000]; // buffer de formatacao
    Celula* atual = lista->primeiro->prox; // comeca no primeiro elemento real

    while (atual != NULL) { // percorre a lista
        formatar_restaurante(atual->elemento, buffer); // formata o restaurante
        printf("[%d] %s\n", i, buffer); // imprime posicao e restaurante
        atual = atual->prox; // avanca
        i++; // aumenta indice
    }
}

// programa principal
int main() {
    ColecaoRestaurantes* colecao = ler_csv(); // le o csv
    Lista lista; // cria a lista
    int id; // guarda id
    int quantidade_operacoes; // guarda a quantidade de operacoes
    int i; // indice
    int pos; // guarda posicao
    char comando[5]; // guarda comando
    Restaurante* restaurante; // guarda restaurante buscado
    Restaurante* removido; // guarda restaurante removido

    iniciar_lista(&lista); // inicia a lista

    scanf("%d", &id); // le o primeiro id

    while (id != -1) { // enquanto nao for -1
        restaurante = buscar_por_id(colecao, id); // busca o restaurante pelo id

        if (restaurante != NULL) { // se encontrou
            inserir_fim(&lista, restaurante); // insere no fim da lista
        }

        scanf("%d", &id); // le o proximo id
    }

    scanf("%d", &quantidade_operacoes); // le quantidade de operacoes

    for (i = 0; i < quantidade_operacoes; i++) { // percorre as operacoes
        scanf("%s", comando); // le o comando

        if (comparar_texto(comando, "II") == 0) { // inserir no inicio
            scanf("%d", &id); // le id
            restaurante = buscar_por_id(colecao, id); // busca restaurante

            if (restaurante != NULL) { // se encontrou
                inserir_inicio(&lista, restaurante); // insere no inicio
            }
        } else if (comparar_texto(comando, "IF") == 0) { // inserir no fim
            scanf("%d", &id); // le id
            restaurante = buscar_por_id(colecao, id); // busca restaurante

            if (restaurante != NULL) { // se encontrou
                inserir_fim(&lista, restaurante); // insere no fim
            }
        } else if (comparar_texto(comando, "I*") == 0) { // inserir em posicao
            scanf("%d %d", &pos, &id); // le posicao e id
            restaurante = buscar_por_id(colecao, id); // busca restaurante

            if (restaurante != NULL) { // se encontrou
                inserir_pos(&lista, restaurante, pos); // insere na posicao
            }
        } else if (comparar_texto(comando, "RI") == 0) { // remover inicio
            removido = remover_inicio(&lista); // remove do inicio

            if (removido != NULL) { // se removeu
                printf("(R)%s\n", removido->nome); // imprime removido sem espaco
            }
        } else if (comparar_texto(comando, "RF") == 0) { // remover fim
            removido = remover_fim(&lista); // remove do fim

            if (removido != NULL) { // se removeu
                printf("(R)%s\n", removido->nome); // imprime removido sem espaco
            }
        } else if (comparar_texto(comando, "R*") == 0) { // remover em posicao
            scanf("%d", &pos); // le posicao
            removido = remover_pos(&lista, pos); // remove da posicao

            if (removido != NULL) { // se removeu
                printf("(R)%s\n", removido->nome); // imprime removido sem espaco
            }
        }
    }

    mostrar(&lista); // mostra a lista final

    return 0; 
}
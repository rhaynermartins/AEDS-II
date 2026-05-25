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
    Hora horario_abertura; // guarda horario de abertura
    Hora horario_fechamento; // guarda horario de fechamento
    Data data_abertura; // guarda data de abertura
    int aberto; // guarda 1 para true e 0 para false
} Restaurante;

// struct ColecaoRestaurantes
typedef struct ColecaoRestaurantes {
    int tamanho; // guarda quantidade de restaurantes
    Restaurante** restaurantes; // guarda vetor de ponteiros para restaurantes
} ColecaoRestaurantes;

// struct NoBicolor
typedef struct NoBicolor {
    Restaurante* elemento; // guarda o restaurante do no
    struct NoBicolor* esq; // aponta para esquerda
    struct NoBicolor* dir; // aponta para direita
    int cor; // guarda a cor: 1 vermelho, 0 preto
} NoBicolor;

// struct ArvoreBicolor
typedef struct ArvoreBicolor {
    NoBicolor* raiz; // guarda a raiz
    long long comparacoes; // guarda comparacoes das pesquisas
} ArvoreBicolor;

// compara dois textos manualmente
int comparar_texto(char* a, char* b) {
    int i = 0; // indice para percorrer os textos

    while (a[i] != '\0' && b[i] != '\0') { // enquanto nenhum texto terminar
        if (a[i] != b[i]) { // se achar caractere diferente
            return a[i] - b[i]; // retorna a diferenca
        }

        i++; // avanca
    }

    return a[i] - b[i]; // retorna diferenca final
}

// remove \n e \r do final
void limpar_final_linha(char* s) {
    int i = 0; // indice
    int fim; // posicao final

    while (s[i] != '\0') { // percorre ate o fim
        i++; // avanca
    }

    fim = i - 1; // pega ultimo caractere valido

    while (fim >= 0 && (s[fim] == '\n' || s[fim] == '\r')) { // remove quebras
        s[fim] = '\0'; // troca por fim de string
        fim--; // volta
    }
}

// verifica se e FIM
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
    char dia_string[10]; // guarda dia
    char mes_string[10]; // guarda mes

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

    sprintf(buffer, "%s:%s", hora_string, minuto_string); // monta hora
}

// converte faixa de preco
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

// formata faixa de preco
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
    int count = 1; // contador

    if (s[0] == '\0') { // se vazio
        return 0; // retorna zero
    }

    while (s[i] != '\0') { // percorre string
        if (s[i] == ';') { // se separador
            count++; // aumenta
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
        if (s[i] == ';') { // se separador
            restaurante->tipos_cozinha[indice_tipo][j] = '\0'; // fecha tipo
            indice_tipo++; // proximo tipo
            j = 0; // reinicia
        } else {
            restaurante->tipos_cozinha[indice_tipo][j] = s[i]; // copia caractere
            j++; // avanca
        }

        i++; // avanca original
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

    if (comparar_texto(aberto, "true") == 0) { // se true
        restaurante->aberto = 1; // guarda 1
    } else {
        restaurante->aberto = 0; // guarda 0
    }

    return restaurante; // retorna restaurante
}

// formata tipos
void formatar_tipos_cozinha(Restaurante* restaurante, char* buffer) {
    int i; // indice tipo
    int j; // indice caractere
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
    ); // monta string final
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
        count++; // aumenta
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

// busca por id
Restaurante* buscar_por_id(ColecaoRestaurantes* colecao, int id) {
    int i; // indice

    for (i = 0; i < colecao->tamanho; i++) { // percorre restaurantes
        if (colecao->restaurantes[i]->id == id) { // se id bate
            return colecao->restaurantes[i]; // retorna restaurante
        }
    }

    return NULL; // retorna NULL se nao achar
}

// cria novo no bicolor
NoBicolor* novo_no(Restaurante* restaurante, int cor) {
    NoBicolor* no = (NoBicolor*) malloc(sizeof(NoBicolor)); // aloca no

    no->elemento = restaurante; // guarda restaurante
    no->esq = NULL; // inicia esquerda
    no->dir = NULL; // inicia direita
    no->cor = cor; // guarda cor

    return no; // retorna no
}

// verifica se no e vermelho
int is_vermelho(NoBicolor* no) {
    return no != NULL && no->cor == 1; // retorna 1 se for vermelho
}

// inicia arvore
void iniciar_arvore(ArvoreBicolor* arvore) {
    arvore->raiz = NULL; // raiz nula
    arvore->comparacoes = 0; // comparacoes zeradas
}

// rotacao simples para esquerda
NoBicolor* rotacionar_esq(NoBicolor* no) {
    NoBicolor* no_dir = no->dir; // guarda filho direito
    NoBicolor* no_dir_esq = no_dir->esq; // guarda subarvore esquerda do filho direito

    no_dir->esq = no; // filho direito vira pai
    no->dir = no_dir_esq; // subarvore vai para direita do antigo pai

    return no_dir; // retorna nova raiz
}

// rotacao simples para direita
NoBicolor* rotacionar_dir(NoBicolor* no) {
    NoBicolor* no_esq = no->esq; // guarda filho esquerdo
    NoBicolor* no_esq_dir = no_esq->dir; // guarda subarvore direita do filho esquerdo

    no_esq->dir = no; // filho esquerdo vira pai
    no->esq = no_esq_dir; // subarvore vai para esquerda do antigo pai

    return no_esq; // retorna nova raiz
}

// rotacao esquerda-direita
NoBicolor* rotacionar_esq_dir(NoBicolor* no) {
    no->esq = rotacionar_esq(no->esq); // rotaciona filho esquerdo para esquerda
    return rotacionar_dir(no); // rotaciona no para direita
}

// rotacao direita-esquerda
NoBicolor* rotacionar_dir_esq(NoBicolor* no) {
    no->dir = rotacionar_dir(no->dir); // rotaciona filho direito para direita
    return rotacionar_esq(no); // rotaciona no para esquerda
}

// balanceia apos conflito vermelho-vermelho
void balancear(ArvoreBicolor* arvore, NoBicolor* bisavo, NoBicolor* avo, NoBicolor* pai, NoBicolor* i) {
    if (is_vermelho(pai)) { // se pai vermelho
        int cmp_pai_avo = comparar_texto(pai->elemento->nome, avo->elemento->nome); // compara pai e avo
        int cmp_i_pai = comparar_texto(i->elemento->nome, pai->elemento->nome); // compara i e pai

        if (cmp_pai_avo > 0) { // pai esta a direita do avo
            if (cmp_i_pai > 0) { // caso direita-direita
                avo = rotacionar_esq(avo); // rotacao esquerda
            } else {
                avo = rotacionar_dir_esq(avo); // rotacao direita-esquerda
            }
        } else { // pai esta a esquerda do avo
            if (cmp_i_pai < 0) { // caso esquerda-esquerda
                avo = rotacionar_dir(avo); // rotacao direita
            } else {
                avo = rotacionar_esq_dir(avo); // rotacao esquerda-direita
            }
        }

        if (bisavo == NULL) { // se avo era raiz
            arvore->raiz = avo; // atualiza raiz
        } else if (comparar_texto(avo->elemento->nome, bisavo->elemento->nome) < 0) { // se avo fica a esquerda
            bisavo->esq = avo; // liga no bisavo
        } else {
            bisavo->dir = avo; // liga no bisavo
        }

        avo->cor = 0; // novo pai fica preto

        if (avo->esq != NULL) { // se existe esquerda
            avo->esq->cor = 1; // esquerda fica vermelha
        }

        if (avo->dir != NULL) { // se existe direita
            avo->dir->cor = 1; // direita fica vermelha
        }
    }
}

// insercao recursiva da arvore bicolor
void inserir_rec(ArvoreBicolor* arvore, Restaurante* restaurante, NoBicolor* bisavo, NoBicolor* avo, NoBicolor* pai, NoBicolor* i) {
    if (i == NULL) { // se chegou em posicao vazia
        NoBicolor* novo = novo_no(restaurante, 1); // cria novo no vermelho

        if (comparar_texto(restaurante->nome, pai->elemento->nome) < 0) { // se menor que pai
            pai->esq = novo; // insere esquerda
        } else {
            pai->dir = novo; // insere direita
        }

        balancear(arvore, bisavo, avo, pai, novo); // balanceia
    } else {
        if (is_vermelho(i->esq) && is_vermelho(i->dir)) { // se i e no 4
            i->cor = 1; // i fica vermelho
            i->esq->cor = 0; // filho esquerdo fica preto
            i->dir->cor = 0; // filho direito fica preto

            if (i == arvore->raiz) { // se i e raiz
                i->cor = 0; // raiz fica preta
            } else {
                balancear(arvore, bisavo, avo, pai, i); // balanceia se necessario
            }
        }

        int cmp = comparar_texto(restaurante->nome, i->elemento->nome); // compara nomes

        if (cmp < 0) { // se menor
            inserir_rec(arvore, restaurante, avo, pai, i, i->esq); // vai esquerda
        } else if (cmp > 0) { // se maior
            inserir_rec(arvore, restaurante, avo, pai, i, i->dir); // vai direita
        }
    }
}

// insere restaurante na arvore
void inserir(ArvoreBicolor* arvore, Restaurante* restaurante) {
    if (arvore->raiz == NULL) { // se arvore vazia
        arvore->raiz = novo_no(restaurante, 0); // cria raiz preta
    } else if (arvore->raiz->esq == NULL && arvore->raiz->dir == NULL) { // se raiz sem filhos
        if (comparar_texto(restaurante->nome, arvore->raiz->elemento->nome) < 0) { // se menor
            arvore->raiz->esq = novo_no(restaurante, 1); // insere esquerda vermelho
        } else if (comparar_texto(restaurante->nome, arvore->raiz->elemento->nome) > 0) { // se maior
            arvore->raiz->dir = novo_no(restaurante, 1); // insere direita vermelho
        }
    } else {
        inserir_rec(arvore, restaurante, NULL, NULL, NULL, arvore->raiz); // chama recursivo
    }

    arvore->raiz->cor = 0; // garante raiz preta
}

// pesquisa recursiva
int pesquisar_rec(NoBicolor* no, char* nome, long long* comparacoes) {
    int resp; // resposta

    if (no == NULL) { // se chegou em null
        resp = 0; // nao encontrou
    } else {
        int cmp = comparar_texto(nome, no->elemento->nome); // compara nomes
        (*comparacoes)++; // conta comparacao

        if (cmp == 0) { // se encontrou
            resp = 1; // achou
        } else if (cmp < 0) { // se deve ir esquerda
            printf(" esq"); // imprime caminho
            resp = pesquisar_rec(no->esq, nome, comparacoes); // pesquisa esquerda
        } else {
            printf(" dir"); // imprime caminho
            resp = pesquisar_rec(no->dir, nome, comparacoes); // pesquisa direita
        }
    }

    return resp; // retorna resultado
}

// pesquisa nome
void pesquisar(ArvoreBicolor* arvore, char* nome) {
    int achou; // guarda resultado

    printf("raiz"); // imprime raiz

    achou = pesquisar_rec(arvore->raiz, nome, &arvore->comparacoes); // pesquisa

    if (achou == 1) { // se achou
        printf(" SIM\n"); // imprime SIM
    } else {
        printf(" NAO\n"); // imprime NAO
    }
}

// caminhada central
void caminhar_central_rec(NoBicolor* no) {
    char buffer[1000]; // buffer de impressao

    if (no != NULL) { // se no existe
        caminhar_central_rec(no->esq); // visita esquerda
        formatar_restaurante(no->elemento, buffer); // formata restaurante
        printf("%s\n", buffer); // imprime restaurante
        caminhar_central_rec(no->dir); // visita direita
    }
}

// caminhada central
void caminhar_central(ArvoreBicolor* arvore) {
    caminhar_central_rec(arvore->raiz); // chama recursivo
}

// cria log
void criar_log(long long comparacoes) {
    FILE* log = fopen(MATRICULA "_arvore_bicolor.txt", "w"); // abre log

    if (log != NULL) { // se abriu
        fprintf(log, "%s\t%lld\t0.0", MATRICULA, comparacoes); // escreve log
        fclose(log); // fecha log
    }
}

// programa principal
int main() {
    ColecaoRestaurantes* colecao = ler_csv(); // le csv
    ArvoreBicolor arvore; // cria arvore
    int id; // guarda id
    int lixo; // guarda caracteres restantes
    char nome[200]; // guarda nome pesquisado
    Restaurante* restaurante; // guarda restaurante encontrado

    iniciar_arvore(&arvore); // inicia arvore

    scanf("%d", &id); // le primeiro id

    while (id != -1) { // enquanto nao for -1
        restaurante = buscar_por_id(colecao, id); // busca restaurante

        if (restaurante != NULL) { // se encontrou
            inserir(&arvore, restaurante); // insere na arvore bicolor
        }

        scanf("%d", &id); // le proximo id
    }

    do { // consome o resto da linha depois do -1
        lixo = getchar(); // le caractere
    } while (lixo != '\n' && lixo != EOF); // para na quebra ou fim

    fgets(nome, sizeof(nome), stdin); // le primeiro nome completo
    limpar_final_linha(nome); // limpa final

    while (!is_fim(nome)) { // enquanto nao for FIM
        pesquisar(&arvore, nome); // pesquisa nome

        fgets(nome, sizeof(nome), stdin); // le proximo nome completo
        limpar_final_linha(nome); // limpa final
    }

    caminhar_central(&arvore); // mostra em ordem

    criar_log(arvore.comparacoes); // cria log

    return 0; 
}
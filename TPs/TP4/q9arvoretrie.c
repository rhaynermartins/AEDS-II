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

// declaracao antecipada do NoTrie
typedef struct NoTrie NoTrie;

// celula da lista de filhos da Trie
typedef struct CelulaFilho {
    char caractere; // guarda o caractere do filho
    NoTrie* filho; // aponta para o no filho
    struct CelulaFilho* prox; // aponta para o proximo filho da lista
} CelulaFilho;

// no da Trie
struct NoTrie {
    char caractere; // guarda o caractere do no
    int fim; // guarda 1 se for fim de palavra
    Restaurante* restaurante; // guarda o restaurante no final do nome
    CelulaFilho* filhos; // aponta para a lista flexivel de filhos
};

// estrutura da Trie
typedef struct ArvoreTrie {
    NoTrie* raiz; // guarda a raiz da Trie
    long long comparacoes; // guarda comparacoes feitas nas pesquisas
} ArvoreTrie;

// compara dois textos manualmente
int comparar_texto(char* a, char* b) {
    int i = 0; // indice para percorrer os textos

    while (a[i] != '\0' && b[i] != '\0') { // enquanto os dois textos tiverem caracteres
        if (a[i] != b[i]) { // se encontrou caractere diferente
            return a[i] - b[i]; // retorna a diferenca
        }

        i++; // avanca para o proximo caractere
    }

    return a[i] - b[i]; // retorna a diferenca final
}

// remove \n e \r do final da linha
void limpar_final_linha(char* s) {
    int i = 0; // indice para percorrer a string
    int fim; // guarda a ultima posicao valida

    while (s[i] != '\0') { // percorre ate o fim da string
        i++; // avanca
    }

    fim = i - 1; // pega a ultima posicao

    while (fim >= 0 && (s[fim] == '\n' || s[fim] == '\r')) { // enquanto tiver quebra de linha
        s[fim] = '\0'; // remove a quebra
        fim--; // volta uma posicao
    }
}

// verifica se a string e FIM
int is_fim(char* s) {
    return comparar_texto(s, "FIM") == 0; // retorna 1 se for FIM
}

// converte string YYYY-MM-DD em Data
Data parse_data(char* s) {
    Data data; // cria uma data

    sscanf(s, "%d-%d-%d", &data.ano, &data.mes, &data.dia); // le ano, mes e dia

    return data; // retorna data
}

// formata data para DD/MM/YYYY
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

    sprintf(buffer, "%s/%s/%d", dia_string, mes_string, data->ano); // monta data
}

// converte string HH:mm em Hora
Hora parse_hora(char* s) {
    Hora hora; // cria uma hora

    sscanf(s, "%d:%d", &hora.hora, &hora.minuto); // le hora e minuto

    return hora; // retorna hora
}

// formata hora para HH:mm
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

    sprintf(buffer, "%s:%s", hora_string, minuto_string); // monta hora
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
    int i = 0; // indice do buffer

    while (i < faixa_preco) { // repete de acordo com a faixa
        buffer[i] = '$'; // coloca $
        i++; // avanca
    }

    buffer[i] = '\0'; // finaliza string
}

// conta tipos de cozinha
int contar_tipos_cozinha(char* s) {
    int i = 0; // indice
    int count = 1; // contador inicial

    if (s[0] == '\0') { // se string vazia
        return 0; // retorna zero
    }

    while (s[i] != '\0') { // percorre string
        if (s[i] == ';') { // se encontrar separador
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
    int indice_tipo = 0; // indice do tipo atual

    restaurante->quantidade_tipos = contar_tipos_cozinha(s); // salva quantidade

    while (s[i] != '\0') { // percorre texto
        if (s[i] == ';') { // se achar separador
            restaurante->tipos_cozinha[indice_tipo][j] = '\0'; // fecha tipo
            indice_tipo++; // vai para proximo tipo
            j = 0; // reinicia posicao do tipo
        } else {
            restaurante->tipos_cozinha[indice_tipo][j] = s[i]; // copia caractere
            j++; // avanca
        }

        i++; // avanca texto
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
    ); // le campos do csv

    parse_tipos_cozinha(tipos, restaurante); // separa tipos

    restaurante->faixa_preco = parse_faixa_preco(faixa); // converte faixa

    sscanf(horario, "%9[^-]-%9s", abertura, fechamento); // separa horario

    restaurante->horario_abertura = parse_hora(abertura); // cria horario abertura
    restaurante->horario_fechamento = parse_hora(fechamento); // cria horario fechamento
    restaurante->data_abertura = parse_data(data); // cria data

    if (comparar_texto(aberto, "true") == 0) { // se aberto for true
        restaurante->aberto = 1; // salva 1
    } else {
        restaurante->aberto = 0; // salva 0
    }

    return restaurante; // retorna restaurante
}

// formata tipos de cozinha
void formatar_tipos_cozinha(Restaurante* restaurante, char* buffer) {
    int i; // indice de tipos
    int j; // indice de caracteres
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

// formata restaurante completo
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

    if (arquivo == NULL) { // se nao abrir
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

    for (i = 0; i < colecao->tamanho; i++) { // percorre restaurantes
        if (colecao->restaurantes[i]->id == id) { // se id bate
            return colecao->restaurantes[i]; // retorna restaurante
        }
    }

    return NULL; // retorna NULL se nao encontrar
}

// cria novo no da Trie
NoTrie* novo_no_trie(char caractere) {
    NoTrie* novo = (NoTrie*) malloc(sizeof(NoTrie)); // aloca no

    novo->caractere = caractere; // guarda caractere
    novo->fim = 0; // inicia como nao final
    novo->restaurante = NULL; // inicia restaurante vazio
    novo->filhos = NULL; // inicia lista de filhos vazia

    return novo; // retorna no
}

// cria nova celula de filho
CelulaFilho* nova_celula_filho(char caractere, NoTrie* filho) {
    CelulaFilho* nova = (CelulaFilho*) malloc(sizeof(CelulaFilho)); // aloca celula

    nova->caractere = caractere; // guarda caractere
    nova->filho = filho; // guarda filho
    nova->prox = NULL; // inicia proximo como NULL

    return nova; // retorna celula
}

// busca filho pelo caractere
NoTrie* buscar_filho(NoTrie* no, char caractere) {
    CelulaFilho* atual = no->filhos; // comeca na lista de filhos

    while (atual != NULL) { // percorre lista
        if (atual->caractere == caractere) { // se achou caractere
            return atual->filho; // retorna filho
        }

        atual = atual->prox; // avanca
    }

    return NULL; // retorna NULL se nao achar
}

// adiciona filho se nao existir
NoTrie* adicionar_filho(NoTrie* no, char caractere) {
    NoTrie* filho = buscar_filho(no, caractere); // tenta buscar filho existente

    if (filho == NULL) { // se filho nao existe
        filho = novo_no_trie(caractere); // cria novo no

        CelulaFilho* nova = nova_celula_filho(caractere, filho); // cria celula

        nova->prox = no->filhos; // nova aponta para antigo inicio
        no->filhos = nova; // nova vira inicio da lista
    }

    return filho; // retorna filho
}

// inicia Trie
void iniciar_trie(ArvoreTrie* trie) {
    trie->raiz = novo_no_trie('/'); // cria raiz simbolica
    trie->comparacoes = 0; // zera comparacoes
}

// insere restaurante na Trie
void inserir_trie(ArvoreTrie* trie, Restaurante* restaurante) {
    NoTrie* atual = trie->raiz; // comeca na raiz
    int i = 0; // indice do nome

    while (restaurante->nome[i] != '\0') { // percorre o nome
        atual = adicionar_filho(atual, restaurante->nome[i]); // cria ou pega filho
        i++; // avanca
    }

    atual->fim = 1; // marca fim da palavra
    atual->restaurante = restaurante; // guarda restaurante
}

// pesquisa nome na Trie
Restaurante* pesquisar_trie(ArvoreTrie* trie, char* nome) {
    NoTrie* atual = trie->raiz; // comeca na raiz
    Restaurante* resp = NULL; // resposta inicial
    int achou_caminho = 1; // controla se caminho existe
    int i = 0; // indice do nome

    while (nome[i] != '\0' && achou_caminho == 1) { // percorre nome enquanto caminho existir
        printf("%c", nome[i]); // imprime caractere visitado

        trie->comparacoes++; // conta comparacao

        NoTrie* filho = buscar_filho(atual, nome[i]); // busca filho

        if (filho == NULL) { // se nao existe caminho
            achou_caminho = 0; // marca falha
        } else {
            atual = filho; // avanca
        }

        if (nome[i + 1] != '\0') { // se nao for ultimo caractere
            printf(" "); // imprime espaco
        }

        i++; // avanca
    }

    if (achou_caminho == 1 && atual->fim == 1) { // se achou palavra completa
        resp = atual->restaurante; // guarda restaurante
    }

    return resp; // retorna restaurante ou NULL
}

// imprime resultado da pesquisa
void imprimir_pesquisa(ArvoreTrie* trie, char* nome) {
    Restaurante* restaurante; // guarda restaurante encontrado
    char buffer[1000]; // buffer do restaurante formatado

    restaurante = pesquisar_trie(trie, nome); // pesquisa na Trie

    if (restaurante != NULL) { // se encontrou
        formatar_restaurante(restaurante, buffer); // formata restaurante
        printf(" SIM %s\n", buffer); // imprime SIM e restaurante
    } else {
        printf(" NAO\n"); // imprime NAO
    }
}

// cria arquivo de log
void criar_log(long long comparacoes) {
    FILE* log = fopen(MATRICULA "_arvore_trie_lista.txt", "w"); // abre arquivo de log

    if (log != NULL) { // se abriu
        fprintf(log, "%s\t%lld\t0.0", MATRICULA, comparacoes); // escreve log
        fclose(log); // fecha log
    }
}

// programa principal
int main() {
    ColecaoRestaurantes* colecao = ler_csv(); // le csv
    ArvoreTrie trie; // cria Trie
    int id; // guarda id lido
    int lixo; // guarda caractere restante
    char nome[200]; // guarda nome pesquisado
    Restaurante* restaurante; // guarda restaurante encontrado

    iniciar_trie(&trie); // inicia Trie

    scanf("%d", &id); // le primeiro id

    while (id != -1) { // enquanto nao for -1
        restaurante = buscar_por_id(colecao, id); // busca restaurante no dataset

        if (restaurante != NULL) { // se encontrou
            inserir_trie(&trie, restaurante); // insere na Trie
        }

        scanf("%d", &id); // le proximo id
    }

    do { // consome resto da linha depois do -1
        lixo = getchar(); // le caractere
    } while (lixo != '\n' && lixo != EOF); // para na quebra ou EOF

    fgets(nome, sizeof(nome), stdin); // le primeiro nome
    limpar_final_linha(nome); // limpa final

    while (!is_fim(nome)) { // enquanto nao for FIM
        imprimir_pesquisa(&trie, nome); // pesquisa e imprime

        fgets(nome, sizeof(nome), stdin); // le proximo nome
        limpar_final_linha(nome); // limpa final
    }

    criar_log(trie.comparacoes); // cria log

    return 0; 
}
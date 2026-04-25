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
    int tamanho;    // quantidade de restaurantes na colecao
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

// parse data
Data parse_data(char* s) { // recebe "YYYY-MM-DD" e retorna uma struct Data
    Data data; // cria uma variavel do tipo Data

    sscanf(s, "%d-%d-%d", &data.ano, &data.mes, &data.dia); // le ano, mes e dia da string

    return data; // retorna a struct preenchida
}

// parse hora
Hora parse_hora(char* s) { // recebe "HH:mm" e retorna uma struct Hora
    Hora hora; // cria uma variavel do tipo Hora

    sscanf(s, "%d:%d", &hora.hora, &hora.minuto); // le hora e minuto da string

    return hora; // retorna a struct preenchida
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
        restaurante->aberto = 1;  // guarda 1
    } else {                                   
        restaurante->aberto = 0;  // se nao, guarda 0
    }

    return restaurante; // retorna o ponteiro para o restaurante criado
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

// selection sort por nome
void selecao_por_nome(Restaurante** array, int n) { // ordena por nome 
    int i; // indice externo
    int j; // indice interno
    int menor; // guarda a posicao do menor nome
    Restaurante* temp; // variavel auxiliar para a troca

    for (i = 0; i < n - 1; i++) { // percorre da primeira ate a penultima posicao
        menor = i; // assume que o menor esta na posicao atual

        for (j = i + 1; j < n; j++) { // procura o menor nome no restante do vetor
            if (comparar_texto(array[j]->nome, array[menor]->nome) < 0) { // se encontrar nome menor
                menor = j; // atualiza a posicao do menor
            }
        }

        if (menor != i) { // se o menor nao estiver na posicao atual
            temp = array[i]; // guarda o elemento atual
            array[i] = array[menor]; // coloca o menor na posicao i
            array[menor] = temp; // coloca o antigo array[i] na posicao do menor
        }
    }
}

// busca binaria por nome
int pesquisa_binaria(Restaurante** array, int n, char* nome, long long* comparacoes) { 
    int esq = 0; // limite esquerdo
    int dir = n - 1; // limite direito
    int meio; // guarda a posicao do meio
    int resultado; // guarda o resultado da comparacao

    while (esq <= dir) { // enquanto ainda existir intervalo valido
        meio = (esq + dir) / 2; // calcula a posicao do meio

        (*comparacoes)++; // conta uma comparacao
        resultado = comparar_texto(array[meio]->nome, nome); // compara o nome do meio com o nome procurado

        if (resultado == 0) { // se forem iguais
            return 1; // encontrou
        } else if (resultado < 0) { // se o nome do meio for menor que o procurado
            esq = meio + 1; // busca na metade da direita
        } else { // se o nome do meio for maior
            dir = meio - 1; // busca na metade da esquerda
        }
    }

    return 0; // se sair do while, nao encontrou
}

// grava arquivo de log
void criar_log(long long comparacoes) { 
    FILE* log = fopen(MATRICULA "_binaria.txt", "w"); // abre o arquivo no modo escrita

    if (log != NULL) { // se conseguiu abrir
        fprintf(log, "%s\t%lld\t0.0", MATRICULA, comparacoes); // escreve matricula, comparacoes e tempo
        fclose(log); // fecha o arquivo
    }
}

// programa principal
int main() { 
    ColecaoRestaurantes* colecao = ler_csv(); // le o csv inteiro e monta a colecao completa
    Restaurante* base_pesquisa[1000]; // vetor que vai guardar os restaurantes escolhidos
    int quantidade = 0; // quantidade de restaurantes selecionados

    char linha[256]; // guarda cada linha lida da entrada
    int id; // guarda o id da primeira parte
    int achou; // guarda o resultado da busca
    long long comparacoes = 0; // contador de comparacoes da pesquisa

    int continuar_ids = 1; // controla a leitura da primeira parte
    int continuar_nomes = 1; // controla a leitura da segunda parte

    while (continuar_ids == 1 && fgets(linha, sizeof(linha), stdin) != NULL) { // le a primeira parte da entrada
        limpar_final_linha(linha); // limpa \n e \r

        sscanf(linha, "%d", &id); // converte a linha para inteiro

        if (id == -1) { // se encontrar -1
            continuar_ids = 0; // encerra a primeira parte
        } else {
            Restaurante* restaurante = buscar_por_id(colecao, id); // busca o restaurante pelo id

            if (restaurante != NULL) { // se encontrou
                base_pesquisa[quantidade] = restaurante; // guarda no vetor base
                quantidade++; // aumenta a quantidade
            }
        }
    }

    selecao_por_nome(base_pesquisa, quantidade); // ordena a base por nome

    while (continuar_nomes == 1 && fgets(linha, sizeof(linha), stdin) != NULL) { // le a segunda parte da entrada
        limpar_final_linha(linha); // limpa \n e \r

        if (comparar_texto(linha, "FIM") == 0) { // se a linha for FIM
            continuar_nomes = 0; // encerra a leitura
        } else {
            achou = pesquisa_binaria(base_pesquisa, quantidade, linha, &comparacoes); // pesquisa o nome lido

            if (achou == 1) { // se encontrou
                printf("SIM\n"); // imprime SIM
            } else { // se nao encontrou
                printf("NAO\n"); // imprime NAO
            }
        }
    }

    criar_log(comparacoes); // cria o arquivo de log

    return 0; // encerra
}

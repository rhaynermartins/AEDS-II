#include <stdio.h>

typedef struct Data {
    int ano;
    int mes;
    int dia;
} Data;

Data parseData(char *s) {
    Data d;
    sscanf(s, "%d-%d-%d", &d.ano, &d.mes, &d.dia);
    return d;
}

typedef struct Hora {
    int hora;
    int minutos;
    int segundos;
} Hora;

void formatarData(char *buffer, Data *d) {
    sprintf(buffer, "%02d/%02d/%04d", d->dia, d->mes, d->ano);
}

int main() {
    Data d = parseData("2026-04-07");
    char buffer[11];

    formatarData(buffer, &d);
    printf("%s", buffer);

    return 0;
}
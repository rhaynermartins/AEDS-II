//etapa 1
class No {
    int elemento;
    int nivel;
    No esq;
    No dir;

    No(int elemento) {
        this.elemento = elemento;
        this.nivel = 1;
        this.esq = null;
        this.dir = null;
    }
}
//etapa 2
public int getNivel(No i) {
    if (i == null) {
        return 0;
    } else {
        return i.nivel;
    }
}
//etapa 3
//nivel = 1 + maior nível entre o filho esquerdo e o filho direito
public void setNivel() {
    int nivelEsq = getNivel(esq);
    int nivelDir = getNivel(dir);

    if (nivelEsq > nivelDir) {
        nivel = 1 + nivelEsq;
    } else {
        nivel = 1 + nivelDir;
    }
}
//etapa 4
//balanceamento = nível(filho direito) - nível(filho esquerdo)
public int getFatorBalanceamento() {
    return getNivel(dir) - getNivel(esq);
}
//etapa 5
class ArvoreAVL {
    private No raiz;

    ArvoreAVL() {
        raiz = null;
    }
}
//etapa 6
//insercao basica sem balanceamento
public void inserir(int x) {
    raiz = inserir(x, raiz);
}

private No inserir(int x, No i) {
    if (i == null) {
        i = new No(x);
    } else if (x < i.elemento) {
        i.esq = inserir(x, i.esq);
    } else if (x > i.elemento) {
        i.dir = inserir(x, i.dir);
    }

    i.setNivel();

    return i;
}
//etapa 7
//caminhamento central mostrando o nível de cada nó
public void caminharCentralNivel() {
    caminharCentralNivel(raiz);
    System.out.println();
}

private void caminharCentralNivel(No i) {
    if (i != null) {
        caminharCentralNivel(i.esq);

        System.out.print(i.elemento + "(" + i.nivel + ") ");

        caminharCentralNivel(i.dir);
    }
}



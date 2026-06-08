import java.util.Scanner;

//bicolor
class No {
    int elemento;
    boolean cor;
    No esq;
    No dir;

    No(int elemento) {
        this.elemento = elemento;
        this.cor = true;
        this.esq = null;
        this.dir = null;
    }

    No(int elemento, boolean cor) {
        this.elemento = elemento;
        this.cor = cor;
        this.esq = null;
        this.dir = null;
    }

    public boolean isNoTipo4() {
        return esq != null && dir != null && esq.cor == true && dir.cor == true;
    }
}

class Bicolor {
    private No raiz;

    Bicolor() {
        raiz = null;
    }

    private void fragmentar(No i) {
        i.cor = true;

        if (i.esq != null) {
            i.esq.cor = false;
        }

        if (i.dir != null) {
            i.dir.cor = false;
        }

        if (i == raiz) {
            i.cor = false;
        }
    }

    private No rotacionarEsq(No i) {
        No j = i.dir;

        i.dir = j.esq;
        j.esq = i;

        return j;
    }

    private No rotacionarDir(No i) {
        No j = i.esq;

        i.esq = j.dir;
        j.dir = i;

        return j;
    }

    private No rotacionarDirEsq(No i) {
        i.dir = rotacionarDir(i.dir);

        return rotacionarEsq(i);
    }

    private No rotacionarEsqDir(No i) {
        i.esq = rotacionarEsq(i.esq);

        return rotacionarDir(i);
    }

    private void balancear(No bisavo, No avo, No pai, No i) {
        if (pai.cor == true) {
            No novaRaiz;

            if (pai.elemento > avo.elemento) {
                if (i.elemento > pai.elemento) {
                    novaRaiz = rotacionarEsq(avo);
                } else {
                    novaRaiz = rotacionarDirEsq(avo);
                }
            } else {
                if (i.elemento < pai.elemento) {
                    novaRaiz = rotacionarDir(avo);
                } else {
                    novaRaiz = rotacionarEsqDir(avo);
                }
            }

            if (bisavo == null) {
                raiz = novaRaiz;
            } else if (avo.elemento < bisavo.elemento) {
                bisavo.esq = novaRaiz;
            } else {
                bisavo.dir = novaRaiz;
            }

            novaRaiz.cor = false;

            if (novaRaiz.esq != null) {
                novaRaiz.esq.cor = true;
            }

            if (novaRaiz.dir != null) {
                novaRaiz.dir.cor = true;
            }
        }
    }

    public void inserir(int x) {
        if (raiz == null) {
            raiz = new No(x, false);
        } else {
            inserir(x, null, null, null, raiz);
        }

        raiz.cor = false;
    }

    private void inserir(int x, No bisavo, No avo, No pai, No i) {
        if (i == null) {
            if (x < pai.elemento) {
                i = pai.esq = new No(x, true);
            } else {
                i = pai.dir = new No(x, true);
            }

            if (pai.cor == true) {
                balancear(bisavo, avo, pai, i);
            }

        } else {
            if (i.isNoTipo4()) {
                fragmentar(i);

                if (pai != null && pai.cor == true) {
                    balancear(bisavo, avo, pai, i);
                }
            }

            if (x < i.elemento) {
                inserir(x, avo, pai, i, i.esq);
            } else if (x > i.elemento) {
                inserir(x, avo, pai, i, i.dir);
            }
        }
    }

    public void caminharCentral() {
        caminharCentral(raiz);
        System.out.println();
    }

    private void caminharCentral(No i) {
        if (i != null) {
            caminharCentral(i.esq);

            int corNumero;

            if (i.cor == true) {
                corNumero = 1;
            } else {
                corNumero = 0;
            }

            System.out.print(i.elemento + "(cor=" + corNumero + ") ");

            caminharCentral(i.dir);
        }
    }
}

public class ArvoreBicolor {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        Bicolor arvore = new Bicolor();

        while (teclado.hasNextInt()) {
            int x = teclado.nextInt();

            arvore.inserir(x);
        }

        arvore.caminharCentral();

        teclado.close();
    }
}
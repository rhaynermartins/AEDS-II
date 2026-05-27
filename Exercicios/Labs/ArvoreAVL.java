import java.util.Scanner;

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

    private static int getNivel(No i) {
        if (i == null) {
            return 0;
        }

        return i.nivel;
    }

    public void setNivel() {
        int nivelEsq = getNivel(esq);
        int nivelDir = getNivel(dir);

        if (nivelEsq > nivelDir) {
            nivel = 1 + nivelEsq;
        } else {
            nivel = 1 + nivelDir;
        }
    }

    public int getFatorBalanceamento() {
        return getNivel(dir) - getNivel(esq);
    }
}

class AVL {
    private No raiz;

    AVL() {
        raiz = null;
    }

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

        i = balancear(i);

        return i;
    }

    private No balancear(No i) {
        if (i != null) {
            int fator = i.getFatorBalanceamento();

            if (fator == 2) {
                int fatorFilhoDir = i.dir.getFatorBalanceamento();

                if (fatorFilhoDir >= 0) {
                    i = rotacionarEsq(i);
                } else {
                    i = rotacionarDirEsq(i);
                }

            } else if (fator == -2) {
                int fatorFilhoEsq = i.esq.getFatorBalanceamento();

                if (fatorFilhoEsq <= 0) {
                    i = rotacionarDir(i);
                } else {
                    i = rotacionarEsqDir(i);
                }

            } else {
                i.setNivel();
            }
        }

        return i;
    }

    private No rotacionarEsq(No i) {
        No j = i.dir;

        i.dir = j.esq;
        j.esq = i;

        i.setNivel();
        j.setNivel();

        return j;
    }

    private No rotacionarDir(No i) {
        No j = i.esq;

        i.esq = j.dir;
        j.dir = i;

        i.setNivel();
        j.setNivel();

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

    public void caminharCentralCompleto() {
        caminharCentralCompleto(raiz);
        System.out.println();
    }

    private void caminharCentralCompleto(No i) {
        if (i != null) {
            caminharCentralCompleto(i.esq);

            System.out.print(i.elemento + "(" + i.nivel + ")(fb=" + i.getFatorBalanceamento() + ") ");

            caminharCentralCompleto(i.dir);
        }
    }
}

public class ArvoreAVL {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        AVL arvore = new AVL();

        while (teclado.hasNextInt()) {
            int x = teclado.nextInt();

            arvore.inserir(x);
        }

        arvore.caminharCentralCompleto();

        teclado.close();
    }
}
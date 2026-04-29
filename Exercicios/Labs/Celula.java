import java.util.Scanner;

class Celula {
    int elemento;
    Celula prox;

    Celula() {
        this.elemento = 0;
        this.prox = null;
    }

    Celula(int elemento) {
        this.elemento = elemento;
        this.prox = null;
    }
}

class Fila {
    private Celula primeiro;
    private Celula ultimo;

    Fila() {
        primeiro = new Celula();
        ultimo = primeiro;
    }

    public void enfileirar(int x) {
        ultimo.prox = new Celula(x);
        ultimo = ultimo.prox;
    }

    public int desenfileirar() {
        if (primeiro == ultimo) {
            return -1;
        }

        Celula removida = primeiro.prox;
        int elemento = removida.elemento;

        primeiro.prox = removida.prox;

        if (removida == ultimo) {
            ultimo = primeiro;
        }

        return elemento;
    }

    public void mostrar() {
        if (primeiro == ultimo) {
            System.out.println("V");
        } else {
            for (Celula i = primeiro.prox; i != null; i = i.prox) {
                System.out.print(i.elemento);

                if (i.prox != null) {
                    System.out.print(" ");
                }
            }

            System.out.println();
        }
    }

    public boolean pesquisar(int y) {
        for (Celula i = primeiro.prox; i != null; i = i.prox) {
            if (i.elemento == y) {
                return true;
            }
        }

        return false;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        Fila fila = new Fila();

        while (teclado.hasNext()) {
            String usuario = teclado.next();

            if (usuario.equals("E")) {
                int x = teclado.nextInt();
                fila.enfileirar(x);

            } else if (usuario.equals("D")) {
                System.out.println(fila.desenfileirar());

            } else if (usuario.equals("M")) {
                fila.mostrar();

            } else if (usuario.equals("P")) {
                int y = teclado.nextInt();

                if (fila.pesquisar(y)) {
                    System.out.println("S");
                } else {
                    System.out.println("N");
                }
            }
        }

        teclado.close();
    }
}
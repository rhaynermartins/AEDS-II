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

    public void enfileirar(int x) { //enfileirar
        ultimo.prox = new Celula(x);
        ultimo = ultimo.prox;
    }

    public int desenfileirar() { //desenfileirar
        if (primeiro == ultimo) {
            return -1;
        }
        Celularemovida = primeiro.prox;
        int elemento = removida.elemento;

        primeiro.prox = removida.prox;

        if(removida == ultimo) {
            ultimo = primeiro;
        }
        return elemento;
    } 
    public void mostrar() { //mostrar
        if (primeiro == ultimo) {
            System.out.println("V");
        } else {
            Celula i = primeiro.prox;

            while (i != null) {
                System.out.print(i.elemento);

                if (i.prox != null) {
                    System.out.print(" ");
                }
                i = i.prox; 
            }
        }
    } 
    public boolean pesquisar(int y) { //pesquisar
        Celula i = primeiro.prox;

        while (i != null) {
            if (i.elemento == y) {
                resp = true;
            } 
            
            i = i.prox;
        }
        return false;
    }
}    
public class Main { //principal

    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);

        Fila fila = new Fila();

        while (sc.hasNext()) {
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

            
        
    
    

import java.util.Scanner;

class No {
    int elemento;
    No esq;
    No dir;

   No(int elemento) {
    this.elemento = elemento;
    this.esq = null;
    this.dir = null;
   } 
}

class ArvoreBinaria {
    No raiz;

    ArvoreBinaria() {
      raiz = null;
    }

    public boolean vazia() {
        return raiz == null;
    }    
    //inserir
    public void inserir(int x){
        raiz = inserir(x, raiz);
    }
    public No inserir(int x, No i) {
        if (i == null) {
            i = new No(x);
        } else if (x < i.elemento) {
            i.esq = inserir(x, i.esq);
        } else if (x > i.elemento) {
            i.dir = inserir(x, i.dir);
        }
        return i;
    }
    //pesquisar
    public boolean pesquisar(int x) {
        return pesquisar(x, raiz);
    }

    public boolean pesquisar(int x, No i) {
        boolean resp;
        
        if (i == null) {
            resp = false;
        } else {
            System.out.print(i.elemento + " ");

            if (x == i.elemento) {
                resp = true;
            } else if (x < i.elemento) {
                resp = pesquisar(x, i.esq);
            } else {
                resp = pesquisar(x, i.dir);
            }
        }

        return resp;
    }    
    //pre ordem
    public void preOrdem() { //imprimir
        if (vazia()) {
            System.out.println("V");
        } else {
            preOrdem(raiz);
            System.out.println();
        }
    }
    public void preOrdem(No i) { //chama o metodo recursivo
        if (i != null) {
            System.out.print(i.elemento + " "); //1 visita o no atual
            preOrdem(i.esq); //2 vai pra esquerda
            preOrdem(i.dir); //3 vai pra direita
        }
    }
    //pos ordem
    public void posOrdem() { //impirmir
        if (vazia()) {
            System.out.println("V");
        } else {
            posOrdem(raiz);
            System.out.println();
        }
    }
    public void posOrdem(No i) { //chama o metodo recursivo
        if (i != null) {
            posOrdem(i.esq); //1 vai pra esquerda
            posOrdem(i.dir); //2 vai pra direita 
            System.out.print(i.elemento + " "); //3 visita o no atual
        }
    }
    //em ordem
    public void emOrdem() { //imprime
        if (vazia()) {
            System.out.println("V");
        } else {
            emOrdem(raiz);
            System.out.println();
        }
    }
    public void emOrdem(No i) { //chama o metodo recursivo
        if (i != null) {
            emOrdem(i.esq); //1 vai pra esquerda
            System.out.print(i.elemento + " "); //2 visita o no atual
            emOrdem(i.dir); //3 vai pra direita 

        }
    }
}
public class Arvore {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        ArvoreBinaria arvore = new ArvoreBinaria();

        while (teclado.hasNext()) {
            String comando = teclado.next();

            if (comando.equals("I")) { //inserir
                int x = teclado.nextInt();
                arvore.inserir(x);

            }
            else if (comando.equals("P")) { //pesquisar 
                int y = teclado.nextInt(); 

                boolean encontrou = arvore.pesquisar(y);

                if (encontrou) {
                    System.out.println("S");
                } else {
                    System.out.println("N");
                }
            } 
            else if (comando.equals("PRE")) { //pre ordem
                arvore.preOrdem();
            } 
            else if (comando.equals("POS")) { //pos ordem
                arvore.posOrdem();
            } 
            else if (comando.equals("EM")) { //em ordem 
                arvore.emOrdem();
            }
        }
        teclado.close();
    }
}
    











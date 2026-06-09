public class Atividades {

    // Pilha simples usando vetor: o ultimo elemento inserido e o primeiro a sair.
    static class PilhaInteiros {
        private int[] dados;
        private int topo;

        PilhaInteiros(int capacidade) {
            dados = new int[capacidade];
            topo = 0;
        }

        void empilhar(int valor) {
            if (topo == dados.length) {
                System.out.println("Pilha cheia");
            } else {
                dados[topo] = valor;
                topo++;
            }
        }

        int desempilhar() {
            if (topo == 0) {
                System.out.println("Pilha vazia");
                return -1;
            }

            topo--;
            return dados[topo];
        }

        void mostrar() {
            System.out.print("Pilha: ");
            for (int i = topo - 1; i >= 0; i--) {
                System.out.print(dados[i] + " ");
            }
            System.out.println();
        }
    }

    // Fila circular usando vetor: o primeiro elemento inserido e o primeiro a sair.
    static class FilaCircular {
        private int[] dados;
        private int inicio;
        private int fim;
        private int tamanho;

        FilaCircular(int capacidade) {
            dados = new int[capacidade];
            inicio = 0;
            fim = 0;
            tamanho = 0;
        }

        void enfileirar(int valor) {
            if (tamanho == dados.length) {
                System.out.println("Fila cheia");
            } else {
                dados[fim] = valor;
                fim = (fim + 1) % dados.length;
                tamanho++;
            }
        }

        int desenfileirar() {
            if (tamanho == 0) {
                System.out.println("Fila vazia");
                return -1;
            }

            int valor = dados[inicio];
            inicio = (inicio + 1) % dados.length;
            tamanho--;
            return valor;
        }

        void mostrar() {
            System.out.print("Fila: ");
            for (int i = 0; i < tamanho; i++) {
                int posicao = (inicio + i) % dados.length;
                System.out.print(dados[posicao] + " ");
            }
            System.out.println();
        }
    }

    private static void exemploPilha() {
        PilhaInteiros pilha = new PilhaInteiros(5);

        pilha.empilhar(10);
        pilha.empilhar(20);
        pilha.empilhar(30);
        pilha.mostrar();

        System.out.println("Removido da pilha: " + pilha.desempilhar());
        pilha.mostrar();
    }

    private static void exemploFila() {
        FilaCircular fila = new FilaCircular(5);

        fila.enfileirar(1);
        fila.enfileirar(2);
        fila.enfileirar(3);
        fila.mostrar();

        System.out.println("Removido da fila: " + fila.desenfileirar());
        fila.enfileirar(4);
        fila.mostrar();
    }

    public static void main(String[] args) {
        exemploPilha();
        exemploFila();
    }
}

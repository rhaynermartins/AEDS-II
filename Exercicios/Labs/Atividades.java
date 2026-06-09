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

    private static void exemploPilha() {
        PilhaInteiros pilha = new PilhaInteiros(5);

        pilha.empilhar(10);
        pilha.empilhar(20);
        pilha.empilhar(30);
        pilha.mostrar();

        System.out.println("Removido da pilha: " + pilha.desempilhar());
        pilha.mostrar();
    }

    public static void main(String[] args) {
        exemploPilha();
    }
}

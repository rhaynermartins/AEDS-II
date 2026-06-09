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

    // Ordenacao por insercao: boa para estudar deslocamento de elementos no vetor.
    private static void ordenarPorInsercao(int[] valores) {
        for (int i = 1; i < valores.length; i++) {
            int chave = valores[i];
            int j = i - 1;

            while (j >= 0 && valores[j] > chave) {
                valores[j + 1] = valores[j];
                j--;
            }

            valores[j + 1] = chave;
        }
    }

    private static int buscaSequencial(int[] valores, int procurado) {
        for (int i = 0; i < valores.length; i++) {
            if (valores[i] == procurado) {
                return i;
            }
        }

        return -1;
    }

    // Busca binaria exige vetor ordenado e divide o intervalo de pesquisa ao meio.
    private static int buscaBinaria(int[] valores, int procurado) {
        int esquerda = 0;
        int direita = valores.length - 1;

        while (esquerda <= direita) {
            int meio = (esquerda + direita) / 2;

            if (valores[meio] == procurado) {
                return meio;
            } else if (valores[meio] < procurado) {
                esquerda = meio + 1;
            } else {
                direita = meio - 1;
            }
        }

        return -1;
    }

    private static void mostrarVetor(String nome, int[] valores) {
        System.out.print(nome + ": ");
        for (int valor : valores) {
            System.out.print(valor + " ");
        }
        System.out.println();
    }

    private static void exemploOrdenacaoEBusca() {
        int[] valores = {7, 2, 9, 1, 5};

        mostrarVetor("Vetor original", valores);
        System.out.println("Busca sequencial do 9: posicao " + buscaSequencial(valores, 9));

        ordenarPorInsercao(valores);
        mostrarVetor("Vetor ordenado", valores);
        System.out.println("Busca binaria do 5: posicao " + buscaBinaria(valores, 5));
    }

    public static void main(String[] args) {
        exemploPilha();
        exemploFila();
        exemploOrdenacaoEBusca();
    }
}

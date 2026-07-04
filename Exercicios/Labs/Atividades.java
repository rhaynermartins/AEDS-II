public class Atividades {

    public static void main(String[] args) {
        System.out.println("Exercicio de estudo gerado em: 2026-07-04 09:04:11");
        System.out.println("Numero base: 14689");

        int[] valores = {5, 3, 8, 1, 2};

        ordenar(valores);

        System.out.println("Vetor ordenado:");
        for (int valor : valores) {
            System.out.print(valor + " ");
        }

        System.out.println();
        System.out.println("Fatorial de 5: " + fatorial(5));
    }

    // Metodo que ordena um vetor usando insertion sort
    public static void ordenar(int[] vetor) {
        for (int i = 1; i < vetor.length; i++) {
            int chave = vetor[i];
            int j = i - 1;

            while (j >= 0 && vetor[j] > chave) {
                vetor[j + 1] = vetor[j];
                j--;
            }

            vetor[j + 1] = chave;
        }
    }

    // Metodo recursivo para calcular fatorial
    public static int fatorial(int n) {
        if (n <= 1) {
            return 1;
        }

        return n * fatorial(n - 1);
    }
}

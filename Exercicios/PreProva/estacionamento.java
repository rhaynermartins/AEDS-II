package Exercicios.PreProva;

import java.util.Scanner;

public class estacionamento {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            int N = sc.nextInt();
            int K = sc.nextInt();

            if (N == 0 && K == 0) {
                break;
            }

            int[] saidas = new int[K];
            int topo = -1;
            boolean possivel = true;

            for (int i = 0; i < N; i++) {
                int C = sc.nextInt();
                int S = sc.nextInt();

                if (!possivel) {
                    continue;
                }

                while (topo >= 0 && saidas[topo] <= C) {
                    topo--;
                }

                if (topo + 1 == K) {
                    possivel = false;
                } else if (topo >= 0 && S > saidas[topo]) {
                    possivel = false;
                } else {
                    topo++;
                    saidas[topo] = S;
                }
            }

            if (possivel) {
                System.out.println("Sim");
            } else {
                System.out.println("Nao");
            }
        }

        sc.close();
    }
}
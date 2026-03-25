package Exercicios.PreProva;

import java.util.Scanner;

public class Medalhas {
    public static void main(String[] args) {
        Scanner teclado = new Scanner (System.in);

        String[] paises = new String[1000];
        int[] ouro = new int[10000];
        int[] prata = new int[10000];
        int[] bronze = new int[10000];

        int n = teclado.nextInt();

        for(int i = 0; i < n - 1; i++) {
            for(int j = 0; j < n - 1 - i; j++) {
              
                boolean troca = false;

                if (ouro[j+1] > ouro[j]) {
                    troca = true;
                } else if (ouro[j+1] == ouro[j] && prata[j+1] > prata[j]) {
                    troca = true;
                } else if (ouro[j+1] == ouro[j] && prata[j+1] == prata[j] && bronze[j+1] > bronze[j]) {
                    troca = true 
                } else if (ouro[j+1] == ouro[j] && prata[j+1] == prata[j] && bronze[j+1] == bronze[j] && paises[j+1] .compareTo)

            }
        }



    }
    
}

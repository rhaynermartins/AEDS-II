package Exercicios.PreProva;
import java.util.Scanner;

public class Tenis {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        int vitoria = 0;

        for(int i = 0; i < 6; i++) {
            char c = teclado.next().charAt(0);

            if ( c == 'v' || c == 'V') {
                vitoria++;
            }
        }
        if (vitoria >= 5) {
            System.out.println("5");
        } else if (vitoria >= 3) {
            System.out.println("2");
        } else if (vitoria >= 1) {
            System.out.println("3");
        } else {
            System.out.println("-1");
        } 
       
       teclado.close(); 
    }  
}

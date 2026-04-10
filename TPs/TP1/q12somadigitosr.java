import java.util.Scanner; 

public class q12somadigitosr { 

    public static Scanner sc = new Scanner(System.in); 

    // método auxiliar para verificar se a string é igual a "FIM"
    public static boolean isFim(String str) { 
        return (str.length() == 3 && // verifica se a string tem exatamente 3 caracteres
                str.charAt(0) == 'F' && // verifica se o primeiro caractere é 'F'
                str.charAt(1) == 'I' && // verifica se o segundo caractere é 'I'
                str.charAt(2) == 'M'); // verifica se o terceiro caractere é 'M'
    } 

    // método recursivo para somar cada caractere do número inteiro
    public static int somaDigitos(String str, int i) { 
        if (i >= str.length()) // verifica se o índice chegou ao final da string
            return 0; // condição de parada da recursão: não há mais dígitos para somar
        else
            return (str.charAt(i) - '0') + somaDigitos(str, i + 1); // converte o caractere atual para número e soma com a próxima chamada recursiva
    } 

    public static void main(String[] args) { // método principal do programa
        String num = sc.nextLine(); // lê a primeira linha da entrada e armazena como string

        while (!isFim(num)) { // repete enquanto a string lida for diferente de "FIM"
            System.out.println(somaDigitos(num, 0)); // chama o método recursivo começando da posição 0 e imprime o resultado
            num = sc.nextLine(); // lê a próxima linha da entrada
        } 
            
        sc.close(); 
    } 
} 
import java.util.Scanner; 

public class q12somadigitosr { 

    public static Scanner teclado = new Scanner(System.in); // Scanner estático para ler a entrada

    // 1) método para verificar se a string é igual a "FIM"
    public static boolean isFim(String str) { // recebe a string que será verificada
        return (str.length() == 3 && // verifica se a string tem exatamente 3 caracteres
                str.charAt(0) == 'F' && // verifica se o primeiro caractere é 'F'
                str.charAt(1) == 'I' && // verifica se o segundo caractere é 'I'
                str.charAt(2) == 'M'); // verifica se o terceiro caractere é 'M'
    } 

    // 2) método recursivo para somar os dígitos da string
    public static int somaDigitos(String str, int i) { // recebe a string e a posição atual
        if (i >= str.length()) { // verifica se o índice chegou ao final da string
            return 0; // condição de parada da recursão
        } else { // caso ainda exista caractere para processar
            return (str.charAt(i) - '0') + somaDigitos(str, i + 1); // soma o dígito atual com o resultado da próxima chamada recursiva
        } 
    } 

    public static void main(String[] args) { 

        while (teclado.hasNextLine()) { // repete enquanto ainda houver linha para ler
            String num = teclado.nextLine(); // lê a linha atual da entrada

            if (!isFim(num)) { // verifica se a linha lida é diferente de "FIM"
                System.out.println(somaDigitos(num, 0)); // imprime a soma dos dígitos da string
            } 
        } 

        teclado.close(); 
    } 
}
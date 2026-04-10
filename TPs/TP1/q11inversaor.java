import java.util.Scanner; 

public class q11inversaor { 

    // função recursiva para calcular o tamanho da string
    public static int tamanho(String str, int i) {
        if (i == str.length()) { // caso base: chegou ao final da string
            return 0; // não há mais caracteres para contar
        }
        return 1 + tamanho(str, i + 1); // conta o caractere atual e chama para o próximo
    }
    
    // função para verificar se a string é "FIM"
    public static boolean ehFim(String str) {
        if (str.length() == 3 && // verifica se o tamanho é 3
            str.charAt(0) == 'F' && // verifica o primeiro caractere
            str.charAt(1) == 'I' && // verifica o segundo caractere
            str.charAt(2) == 'M') { // verifica o terceiro caractere
            return true; // retorna verdadeiro se for "FIM"
        }

        return false; // retorna falso caso contrário
    }

    // função recursiva para imprimir a string invertida
    public static void inverte(String str, int i) {
        if (i < 0) { // caso base: passou do início da string
            return; // encerra a recursão
        }

        System.out.print(str.charAt(i)); // imprime o caractere atual
        inverte(str, i - 1); // chama a função para o caractere anterior
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); // cria o Scanner

        String str = sc.nextLine(); // lê a primeira linha

        while (!ehFim(str)) { // continua enquanto a string não for "FIM"
            int tam = tamanho(str, 0); // calcula o tamanho da string recursivamente
            inverte(str, tam - 1); // imprime a string invertida
            System.out.println(); // pula linha
            str = sc.nextLine(); // lê a próxima linha
        }

        sc.close(); 
    }
}
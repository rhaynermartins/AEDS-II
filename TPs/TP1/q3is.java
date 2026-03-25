import java.util.Scanner;

public class q3is {
    
    // 1) verifica se a string é composta apenas por vogais
    public static boolean vogal(String str) {
        int i = 0;
        while (i < str.length()) { // percorre toda a string
            char c = str.charAt(i);

            // é maiúscula ou minúscula?
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
                return false;
            }

            // é uma vogal?
            if (!(c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' ||
                  c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U')) {
                return false;
            }

            i++;
        }
        return true; // resumo: se percorreu toda a string e todos eram vogais, retorna true
    }
    // 2) verifica se a string é composta apenas por consoantes
    public static boolean consoante(String str) {
        int i = 0;
        while (i < str.length()) {
            char c = str.charAt(i);

            // é maiúscula ou minúscula?
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
                return false;
            }

            // é uma vogal?
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' ||
                c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U') {
                return false; // se for vogal, então não é consoante
            }

            i++;
        }
        return true; // resumo: se todos os caracteres eram letras e nenhuma era vogal, retorna true
    }
    // 3) verifica se a string representa um número inteiro
    public static boolean inte(String str) {
        int i = 0;
        while (i < str.length()) { // percorre todos os caracteres da string
            char c = str.charAt(i); // pega o caractere atual

            // se algum caractere não for um numero, retorna falso
            if (!(c >= '0' && c <= '9')) {
                return false;
            }

            i++;
        }
        return true; // resumo: se todos os caracteres forem numeros, retorna true
    }
    // 4) verifica se a string representa um número real
    public static boolean real(String str) {
        int i = 0;
        int ponto = 0;   // contador de pontos
        int virgula = 0; // contador de vírgulas

        while (i < str.length()) {
            char c = str.charAt(i);

            // Permite apenas dígitos, ponto ou vírgula
            if (!((c >= '0' && c <= '9') || c == '.' || c == ',')) {
                return false;
            }

            // conta quantos pontos existem
            if (c == '.') {
                ponto++;
            }
            // conta quantas virgulas existem
            if (c == ',') {
                virgula++;
            }

            // se houver mais de um ponto ou virgula, não é real
            if (ponto > 1 || virgula > 1) {
                return false;
            }

            i++;
        }

        return true;  // resumo: se a string só tem números e no máximo um ponto ou uma vírgula, retorna true
    }
    // 5) programa principal
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in); // inicialização do scanner

        String s = teclado.nextLine(); // lê a primeira linha digitada pelo usuário

        // loop até que a entrada seja "FIM"
        while (!(s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M')) {

            // verifica se é vogal e imprime "SIM" ou "NAO"
            if (vogal(s)) // chama o método vogal passando a string lida
                System.out.print("SIM ");
            else
                System.out.print("NAO ");

            // verifica se é consoante e imprime "SIM" ou "NAO"
            if (consoante(s)) // chama o método consoante passando a string lida
                System.out.print("SIM ");
            else
                System.out.print("NAO ");

            // verifica se é inteiro e imprime "SIM" ou "NAO"
            if (inte(s)) // chama o método inte passando a string lida
                System.out.print("SIM ");
            else
                System.out.print("NAO ");

            // verifica se é real e imprime "SIM" ou "NAO"
            if (real(s)) // chama o método real passando a string lida
                System.out.println("SIM");
            else
                System.out.println("NAO");

            s = teclado.nextLine(); // lê a próxima linha para continuar o loop
        }

        teclado.close();
    }
}
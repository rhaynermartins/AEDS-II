import java.util.Scanner; 

public class q8senha { 

    // 1) método para verificar se a string é igual a "FIM"
    public static boolean isFim(String str) { 
        return (str.length() == 3 && 
                str.charAt(0) == 'F' && // verifica se o primeiro caractere é F
                str.charAt(1) == 'I' && // verifica se o segundo caractere é I
                str.charAt(2) == 'M'); // verifica se o terceiro caractere é M
    } 

    // 2) método que verifica se a senha é válida
    public static boolean senhaValida(String senha) { 
        boolean temMaiuscula = false; // guarda se encontrou pelo menos uma letra maiúscula
        boolean temMinuscula = false; // guarda se encontrou pelo menos uma letra minúscula
        boolean temNumero = false; // guarda se encontrou pelo menos um número
        boolean temEspecial = false; // guarda se encontrou pelo menos um caractere especial

        if (senha.length() < 8) { // verifica se a senha possui menos de 8 caracteres
            return false; // se tiver menos de 8 caracteres, retorna falso
        }

        for (int i = 0; i < senha.length(); i++) { // percorre todos os caracteres da senha
            char c = senha.charAt(i); // pega o caractere atual da string

            if (c >= 'A' && c <= 'Z') { // verifica se o caractere é letra maiúscula
                temMaiuscula = true; // marca que encontrou uma letra maiúscula
            } else if (c >= 'a' && c <= 'z') { // verifica se o caractere é letra minúscula
                temMinuscula = true; // marca que encontrou uma letra minúscula
            } else if (c >= '0' && c <= '9') { // verifica se o caractere é número
                temNumero = true; // marca que encontrou um número
            } else { // se não for letra nem número
                temEspecial = true; // considera o caractere como especial
            } 
        } 

        // retorna true somente se todas as condições forem satisfeitas
        return (temMaiuscula && temMinuscula && temNumero && temEspecial); 
    } 

    public static void main(String[] args) { 
        Scanner teclado = new Scanner(System.in); 
        String senha; // variável que armazenará cada linha lida

        if (teclado.hasNextLine()) { // verifica se existe pelo menos uma linha na entrada
            senha = teclado.nextLine(); // lê a primeira linha da entrada
        } else { // caso não exista nenhuma linha
            senha = "FIM"; // atribui "FIM" para evitar execução do while
        } 

        while (!isFim(senha)) { // repete enquanto a string lida for diferente de "FIM"
            if (senhaValida(senha)) { // verifica se a senha atual é válida
                System.out.println("SIM"); // imprime SIM se a senha for válida
            } else { // caso a senha não seja válida
                System.out.println("NAO"); // imprime NAO
            } 

            if (teclado.hasNextLine()) { // verifica se ainda existe outra linha para ler
                senha = teclado.nextLine(); // lê a próxima linha da entrada
            } else { // caso não exista próxima linha
                senha = "FIM"; // atribui "FIM" para encerrar o while naturalmente
            } 
        } 

        teclado.close(); 
    }
} 
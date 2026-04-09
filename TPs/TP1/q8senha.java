import java.util.Scanner; 

public class q8senha { 

    // método que verifica se a senha é válida
    public static boolean senhaValida(String senha) {
        boolean temMaiuscula = false; // guarda se encontrou letra maiúscula
        boolean temMinuscula = false; // guarda se encontrou letra minúscula
        boolean temNumero = false; // guarda se encontrou número
        boolean temEspecial = false; // guarda se encontrou caractere especial

        // primeiro verifica se a senha tem pelo menos 8 caracteres
        if (senha.length() < 8) {
            return false; // se tiver menos de 8, já retorna falso
        }

        // percorre cada caractere da string
        for (int i = 0; i < senha.length(); i++) {
            char c = senha.charAt(i); // pega o caractere da posição i

            // verifica se o caractere é letra maiúscula
            if (c >= 'A' && c <= 'Z') {
                temMaiuscula = true;
            }
            // verifica se o caractere é letra minúscula
            else if (c >= 'a' && c <= 'z') {
                temMinuscula = true;
            }
            // verifica se o caractere é número
            else if (c >= '0' && c <= '9') {
                temNumero = true;
            }
            // se não for nenhuma das opções acima, considera especial
            else {
                temEspecial = true;
            }
        }

        // só será válida se todas as condições forem verdadeiras
        return temMaiuscula && temMinuscula && temNumero && temEspecial;
    }

    public static void main(String[] args) { // método principal
        Scanner sc = new Scanner(System.in); // cria o Scanner para ler a entrada

        // lê linha por linha até acabar a entrada
        while (sc.hasNextLine()) {
            String senha = sc.nextLine(); // lê uma linha inteira

            // chama o método e imprime o resultado
            if (senhaValida(senha)) {
                System.out.println("SIM");
            } else {
                System.out.println("NÃO");
            }
        }

        sc.close(); // fecha o Scanner
    }
}
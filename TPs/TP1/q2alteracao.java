package TPs.TP1;

import java.util.Random;

public class q2alteracao {
    private Random gerador; // criando objeto para gerar números aleatórios

    // construtor parainicializa o gerador de números aleatórios com semente fixa
    public q2alteracao() {
        gerador = new Random(4);
    }

    // Método que altera todas as ocorrências de uma letra aleatória por outra letra aleatória
    public String altera(String str) {
        // Gera duas letras aleatórias
        char letra1 = (char) ('a' + (Math.abs(gerador.nextInt()) % 26));
        char letra2 = (char) ('a' + (Math.abs(gerador.nextInt()) % 26));

        String nova = ""; // String que vai receber a versão alterada

        // Percorre cada caractere da string original
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == letra1) { // Se o caractere for a letra1
                nova += letra2; // Substitui por letra2
            } else {
                nova += str.charAt(i); // Caso contrário, mantém o caractere original
            }
        }

        return nova; // Retorna a string alterada
    }

    // Método que compara duas strings manualmente
    public boolean compara(String string, String str){
        if (string.length() != str.length()) { // Se os tamanhos forem diferentes
            return false; 
        }
        // Compara caractere por caractere
        for (int i = 0; i < string.length(); i++){
            if(string.charAt(i) != str.charAt(i))
                return false; // Caracteres diferentes
        }
        return true; // Strings são iguais
    }

    public static void main(String[] args) {
        q2alteracao a = new q2alteracao(); // Cria objeto da classe
        String str = MyIO.readLine(); // Lê a primeira linha de entrada

        // Continua lendo enquanto a string não for "FIM"
        while (!a.compara("FIM", str)) {
            MyIO.println(a.altera(str)); // Imprime a string alterada
            str = MyIO.readLine(); // Lê a próxima linha
        }
    }
}
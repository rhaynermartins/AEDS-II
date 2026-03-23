
import java.util.Random;

public class q2alteracao {
    private Random gerador; // criando objeto para gerar números aleatórios

    // construtor para inicializar o gerador de números aleatórios com seed fixa
    public q2alteracao() {
        gerador = new Random(4);
    }

    // altera todas as ocorrencias de uma letra aleatoria por outra letra aleatória
    public String altera(String str) {
        // gera duas letras aleatórias
        char letra1 = (char) ('a' + (Math.abs(gerador.nextInt()) % 26));
        char letra2 = (char) ('a' + (Math.abs(gerador.nextInt()) % 26));

        String nova = ""; // string que vai receber a versão alterada

        // percorre toda string original
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == letra1) { // se o caractere for a letra1
                nova += letra2; // substitui por letra2
            } else {
                nova += str.charAt(i); // caso contrario, mantem o caractere original
            }
        }

        return nova; // retorna a string alterada
    }

    // compara duas strings manualmente
    public boolean compara(String string, String str){
        if (string.length() != str.length()) { // se os tamanhos forem diferentes
            return false; 
        }
        // compara caractere por caractere
        for (int i = 0; i < string.length(); i++){
            if(string.charAt(i) != str.charAt(i))
                return false; // caracteres diferentes
        }
        return true; // strings são iguais
    }

    public static void main(String[] args) {
        q2alteracao a = new q2alteracao(); // criando objeto da classe principal
        String str = MyIO.readLine(); // le a primeira linha de entrada

        // continua lendo enquanto "FIM" nao for digitado pelo usuario
        while (!a.compara("FIM", str)) {
            MyIO.println(a.altera(str)); // imprime a string alterada
            str = MyIO.readLine(); // le a próxima linha
        }
    }
}
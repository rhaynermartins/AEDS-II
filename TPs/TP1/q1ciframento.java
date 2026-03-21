package TPs.TP1;

public class q1ciframento {

     // aplica o ciframento em cada caractere da string
    public void aplicar (String msg) {
        
        for(int i = 0; i < msg.length(); i++) {
            char caractere = msg.charAt(i); // pega o caractere atual da string
            char cifrado = (char) (caractere + 3); // desloca 3 posições
            MyIO.print(cifrado); // imprime o caractere cifrado
        }
        System.out.println(); // quebra de linha após imprimir a string
    }

    // compara duas strings manualmente
    public boolean compara(String string, String str){
        if (string.length() != str.length()) { // se os tamanhos forem diferentes... retorna false
            return false;
        }

        // compara caractere por caractere
        for (int i = 0; i < string.length(); i++){
            if(string.charAt(i) != str.charAt(i))
                return false; // caracteres diferentes -> strings diferentes -> resultando false
        }

        return true; // Strings são iguais -> resultando true
    }
    public static void main (String[] args) {

        String msg; //armazena as strings do usuario

        q1ciframento c = new q1ciframento(); // cria objeto da classe
        msg = MyIO.readLine(); // lê a primeira linha de entrada do teclado

         // loop principal: continua lendo e cifrando strings enquanto não encontrar "FIM" digitado pelo usuario
        while(!c.compara("FIM", msg)){
          c.aplicar(msg); // metodo que aplica o ciframento
          msg = MyIO.readLine(); // lê a próxima linha
        }
    }
}

import java.util.Scanner;

class Atleta {
    String nome; 
    int peso;   

    Atleta(String nome, int peso) { // construtor Atleta
        this.nome = nome;          
        this.peso = peso;           
    }
}

public class Peso {

    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in); 

        Atleta[] atletas = new Atleta[100]; // vetor para armazenar no max 100 atletas

        int n = 0; 

        while (teclado.hasNext()) { 

            String nome = teclado.next(); // le o nome 

            int peso = teclado.nextInt(); // le o peso max

            atletas[n] = new Atleta(nome, peso); // cria um atleta e guarda no vetor

            n++; // ++ quantidade de atletas lidos
        }

        for (int i = 0; i < n - 1; i++) { 

            for (int j = 0; j < n - 1 - i; j++) { 

                boolean trocar = false; // boolean: atletas devem trocar de posição

                // se o peso atual for menor que o prox, troca para deixar o maior peso antes
                if (atletas[j].peso < atletas[j + 1].peso) { 
                    trocar = true;    
                } else if (atletas[j].peso == atletas[j + 1].peso) { // Se os pesos forem iguais

                    if (atletas[j].nome.compareTo(atletas[j + 1].nome) > 0) { // se o nome atual vem depois alfabeticamente
                        trocar = true; // troca para colocar em ordem alfabetica
                    }
                }

                if (trocar) { // se precisar trocar

                    Atleta temp = atletas[j]; // guarda o atleta em temp

                    atletas[j] = atletas[j + 1]; // coloca o prox atleta em atual

                    atletas[j + 1] = temp; // coloca o atleta salvo em prox
                }
            }
        }

        for (int i = 0; i < n; i++) { // percorre e imprime

            System.out.println(atletas[i].nome + " " + atletas[i].peso); 
        }

        teclado.close(); 
    }
}
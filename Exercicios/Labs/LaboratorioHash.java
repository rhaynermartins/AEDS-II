import java.util.Scanner;

//tabela hash com área de reserva
class TabelaHashComReserva {
    private String[] tabela; 
    private int m;           //área principal
    private int r;           //área de reserva
    private int nr;          //qnt de elementos 

    // Construtor da tabela hash
    public TabelaHashComReserva(int m, int r) {
        this.m = m;                  
        this.r = r;                  
        this.nr = 0;                
        this.tabela = new String[m + r]; 
    }

    //hash que calcula a posição da chave 
    private int hash(String chave) {
        int soma = 0; 

        for (int i = 0; i < chave.length(); i++) {
            soma += chave.charAt(i); 
        }

        return soma % m; // retorna a posição dentro da área principal
    }

    //verifica se uma posição está livre
    private boolean isPosicaoLivre(int pos) {
        return tabela[pos] == null; 
    }

    public boolean inserir(String chave) {

        if (pesquisar(chave) != null) {
            return false;
        }

        int pos = hash(chave); //calcula a posição usando a função hash

        //insere se a posicao estiver livre
        if (isPosicaoLivre(pos)) {
            tabela[pos] = chave;
            return true;
        }

        //se houve colisão, tenta inserir na área de reserva
        if (nr < r) {
            tabela[m + nr] = chave; // Insere 
            nr++;                   // Atualiza 
            return true;
        }

        return false;
    }

    //pesquisa uma chave na tabela
    public String pesquisar(String chave) {
        int pos = hash(chave); 

        if (tabela[pos] != null && tabela[pos].equals(chave)) {
            return tabela[pos];
        }

        for (int i = m; i < m + nr; i++) {
            if (tabela[i] != null && tabela[i].equals(chave)) {
                return tabela[i];
            }
        }

        return null;
    }

    //remove uma chave da tabela
    public String remover(String chave) {
        int pos = hash(chave); 

        if (tabela[pos] != null && tabela[pos].equals(chave)) {
            String removido = tabela[pos]; // Guarda 
            tabela[pos] = null;            // Remove 

            int posReserva = procurarNaReservaMesmoHash(pos);

            if (posReserva != -1) {
                tabela[pos] = tabela[posReserva]; // Move 
                removerDaReserva(posReserva);     // Remove 
            }

            return removido; 
        }

        for (int i = m; i < m + nr; i++) {
            if (tabela[i] != null && tabela[i].equals(chave)) {
                String removido = tabela[i]; // Guarda 
                removerDaReserva(i);         // Remove 
                return removido;             // Retorna 
            }
        }

        return null;
    }

    //procura na reserva algum elemento que tenha o mesmo hash da posição removida
    private int procurarNaReservaMesmoHash(int posHash) {

        for (int i = m; i < m + nr; i++) {
            if (tabela[i] != null && hash(tabela[i]) == posHash) {
                return i;
            }
        }

        return -1; 
    }

    //remove um elemento da reserva e reorganiza a reserva
    private void removerDaReserva(int pos) {

        for (int i = pos; i < m + nr - 1; i++) {
            tabela[i] = tabela[i + 1];
        }

        tabela[m + nr - 1] = null; // Limpa 
        nr--;                      // Diminui 
    }

    //mostra a tabela completa
    public void mostrar() {
        for (int i = 0; i < tabela.length; i++) {
            String area; 

            //define se a posição pertence à área principal ou à reserva
            if (i < m) {
                area = "Principal";
            } else {
                area = "Reserva";
            }

            String conteudo; 

            //mostra "-"
            if (tabela[i] == null) {
                conteudo = "-";
            } else {
                conteudo = tabela[i];
            }

            //imprimir índice, área e conteúdo
            System.out.println(i + " " + area + " " + conteudo);
        }
    }
}

//principal
public class LaboratorioHash {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in); 

        //cria a tabela hash 
        TabelaHashComReserva tabela = new TabelaHashComReserva(11, 3);

        //até o fim do arquivo
        while (teclado.hasNext()) {
            String comando = teclado.next(); 

            // I: inserir
            if (comando.equals("I")) {
                String pais = teclado.next(); 

                // Se não, imprime Erro
                if (!tabela.inserir(pais)) {
                    System.out.println("Erro");
                }

            // P: pesquisar
            } else if (comando.equals("P")) {
                String pais = teclado.next(); 

                // Se encontrar, imprime Sim
                if (tabela.pesquisar(pais) != null) {
                    System.out.println("Sim");
                } else {
                    System.out.println("Nao");
                }

            // R: remover
            } else if (comando.equals("R")) {
                String pais = teclado.next();

                // Se não, imprime Erro
                if (tabela.remover(pais) == null) {
                    System.out.println("Erro");
                }

            // M: mostrar tabela
            } else if (comando.equals("M")) {
                tabela.mostrar();
            }
        }

        teclado.close();
    }
}
class TabelaHashComReserva {
    private String[] tabela;
    private int m;
    private int r;
    private int nr;

    public TabelaHashComReserva(int m, int r) {
        this.m = m;
        this.r = r;
        this.nr = 0;
        this.tabela = new String[m + r];
    }

    private int hash(String chave) {
        int soma = 0;

        for (int i = 0; i < chave.length(); i++) {
            soma += chave.charAt(i);
        }

        return soma % m;
    }

    private boolean isPosicaoLivre(int pos) {
        return tabela[pos] == null;
    }

    public void inserir(String chave) throws Exception {
        if (pesquisar(chave) != null) {
            throw new Exception("Erro: insercao duplicada");
        }

        int pos = hash(chave);

        if (isPosicaoLivre(pos)) {
            tabela[pos] = chave;
        } else if (nr < r) {
            tabela[m + nr] = chave;
            nr++;
        } else {
            throw new Exception("Erro: area de reserva cheia");
        }
    }

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

    public String remover(String chave) {
        int pos = hash(chave);

        if (tabela[pos] != null && tabela[pos].equals(chave)) {
            String removido = tabela[pos];
            tabela[pos] = null;

            int posReserva = procurarNaReservaMesmoHash(pos);

            if (posReserva != -1) {
                tabela[pos] = tabela[posReserva];
                removerDaReserva(posReserva);
            }

            return removido;
        }

        for (int i = m; i < m + nr; i++) {
            if (tabela[i] != null && tabela[i].equals(chave)) {
                String removido = tabela[i];
                removerDaReserva(i);
                return removido;
            }
        }

        return null;
    }

    private int procurarNaReservaMesmoHash(int posHash) {
        for (int i = m; i < m + nr; i++) {
            if (tabela[i] != null && hash(tabela[i]) == posHash) {
                return i;
            }
        }

        return -1;
    }

    private void removerDaReserva(int pos) {
        for (int i = pos; i < m + nr - 1; i++) {
            tabela[i] = tabela[i + 1];
        }

        tabela[m + nr - 1] = null;
        nr--;
    }

    public void mostrar() {
        System.out.println("Indice Area Conteudo");

        for (int i = 0; i < tabela.length; i++) {
            String area;

            if (i < m) {
                area = "Principal";
            } else {
                area = "Reserva";
            }

            String conteudo;

            if (tabela[i] == null) {
                conteudo = "-";
            } else {
                conteudo = tabela[i];
            }

            System.out.println(i + " " + area + " " + conteudo);
        }
    }
}

class TabelaHashComRehash {
    private String[] tabela;
    private int m;

    public TabelaHashComRehash(int m) {
        this.m = m;
        this.tabela = new String[m];
    }

    private int hash(String chave) {
        int soma = 0;

        for (int i = 0; i < chave.length(); i++) {
            soma += chave.charAt(i);
        }

        return soma % m;
    }

    private int rehash(String chave) {
        int soma = 0;

        for (int i = 0; i < chave.length(); i++) {
            soma += chave.charAt(i);
        }

        return (soma + 1) % m;
    }

    public void inserir(String chave) throws Exception {
        if (pesquisar(chave) != null) {
            throw new Exception("Erro: insercao duplicada");
        }

        int pos = hash(chave);

        if (tabela[pos] == null) {
            tabela[pos] = chave;
        } else {
            pos = rehash(chave);

            if (tabela[pos] == null) {
                tabela[pos] = chave;
            } else {
                throw new Exception("Erro: nao foi possivel inserir");
            }
        }
    }

    public String pesquisar(String chave) {
        int pos = hash(chave);

        if (tabela[pos] != null && tabela[pos].equals(chave)) {
            return tabela[pos];
        }

        pos = rehash(chave);

        if (tabela[pos] != null && tabela[pos].equals(chave)) {
            return tabela[pos];
        }

        return null;
    }

    public String remover(String chave) {
        int pos = hash(chave);

        if (tabela[pos] != null && tabela[pos].equals(chave)) {
            String removido = tabela[pos];
            tabela[pos] = null;
            return removido;
        }

        pos = rehash(chave);

        if (tabela[pos] != null && tabela[pos].equals(chave)) {
            String removido = tabela[pos];
            tabela[pos] = null;
            return removido;
        }

        return null;
    }

    public void mostrar() {
        System.out.println("Indice Conteudo");

        for (int i = 0; i < tabela.length; i++) {
            String conteudo;

            if (tabela[i] == null) {
                conteudo = "-";
            } else {
                conteudo = tabela[i];
            }

            System.out.println(i + " " + conteudo);
        }
    }
}

class Celula {
    String elemento;
    Celula prox;

    Celula(String elemento) {
        this.elemento = elemento;
        this.prox = null;
    }
}

class TabelaHashEncadeada {
    private Celula[] tabela;
    private int m;

    public TabelaHashEncadeada(int m) {
        this.m = m;
        this.tabela = new Celula[m];
    }

    private int hash(String chave) {
        int soma = 0;

        for (int i = 0; i < chave.length(); i++) {
            soma += chave.charAt(i);
        }

        return soma % m;
    }

    public void inserir(String chave) throws Exception {
        if (pesquisar(chave) != null) {
            throw new Exception("Erro: insercao duplicada");
        }

        int pos = hash(chave);

        Celula nova = new Celula(chave);
        nova.prox = tabela[pos];
        tabela[pos] = nova;
    }

    public String pesquisar(String chave) {
        int pos = hash(chave);

        Celula atual = tabela[pos];

        while (atual != null) {
            if (atual.elemento.equals(chave)) {
                return atual.elemento;
            }

            atual = atual.prox;
        }

        return null;
    }

    public String remover(String chave) {
        int pos = hash(chave);

        Celula atual = tabela[pos];
        Celula anterior = null;

        while (atual != null) {
            if (atual.elemento.equals(chave)) {
                if (anterior == null) {
                    tabela[pos] = atual.prox;
                } else {
                    anterior.prox = atual.prox;
                }

                return atual.elemento;
            }

            anterior = atual;
            atual = atual.prox;
        }

        return null;
    }

    public void mostrar() {
        System.out.println("Indice Conteudo");

        for (int i = 0; i < tabela.length; i++) {
            System.out.print(i + " ");

            Celula atual = tabela[i];

            if (atual == null) {
                System.out.print("-");
            } else {
                while (atual != null) {
                    System.out.print(atual.elemento);

                    if (atual.prox != null) {
                        System.out.print(" -> ");
                    }

                    atual = atual.prox;
                }
            }

            System.out.println();
        }
    }
}

public class LaboratorioHash {
    public static void main(String[] args) {
        String[] paises = {
            "Brasil", "Canada", "Franca", "China", "Egito",
            "Australia", "Alemanha", "Japao", "Mexico", "Marrocos"
        };

        System.out.println("=== PARTE 1 - HASH COM AREA DE RESERVA ===");

        TabelaHashComReserva reserva = new TabelaHashComReserva(11, 3);

        try {
            for (int i = 0; i < paises.length; i++) {
                reserva.inserir(paises[i]);
            }

            reserva.mostrar();

            System.out.println();
            System.out.println("Pesquisando paises inseridos:");

            for (int i = 0; i < paises.length; i++) {
                if (reserva.pesquisar(paises[i]) != null) {
                    System.out.println(paises[i] + " encontrado");
                } else {
                    System.out.println(paises[i] + " nao encontrado");
                }
            }

            System.out.println();
            System.out.println("Pesquisando paises nao inseridos:");

            if (reserva.pesquisar("Estados Unidos") == null) {
                System.out.println("Estados Unidos nao encontrado");
            }

            if (reserva.pesquisar("Argentina") == null) {
                System.out.println("Argentina nao encontrado");
            }

            System.out.println();
            System.out.println("Testando insercao duplicada:");

            try {
                reserva.inserir("Brasil");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            System.out.println();
            System.out.println("Testando overflow:");

            try {
                reserva.inserir("Espanha");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            System.out.println();
            System.out.println("Removendo Brasil, China e Alemanha:");

            reserva.remover("Brasil");
            reserva.remover("China");
            reserva.remover("Alemanha");

            reserva.mostrar();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println();
        System.out.println("=== PARTE 2 - HASH COM REHASH ===");

        TabelaHashComRehash rehash = new TabelaHashComRehash(11);

        try {
            for (int i = 0; i < paises.length; i++) {
                rehash.inserir(paises[i]);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        rehash.mostrar();

        System.out.println();
        System.out.println("=== PARTE 3 - HASH COM LISTA ENCADEADA ===");

        TabelaHashEncadeada encadeada = new TabelaHashEncadeada(11);

        try {
            for (int i = 0; i < paises.length; i++) {
                encadeada.inserir(paises[i]);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        encadeada.mostrar();
    }
}
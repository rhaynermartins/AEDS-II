import java.util.Scanner; 
import java.io.File; 
import java.io.FileNotFoundException; 
import java.io.PrintWriter; 

// classe Data
class Data {
    private int ano; // guarda o ano
    private int mes; // guarda o mes
    private int dia; // guarda o dia

    // construtor
    public Data(int ano, int mes, int dia) {
        this.ano = ano; // salva o ano
        this.mes = mes; // salva o mes
        this.dia = dia; // salva o dia
    }

    // transforma uma string no formato YYYY-MM-DD em Data
    public static Data parseData(String s) {
        Scanner sc = new Scanner(s); // cria Scanner para ler a string
        sc.useDelimiter("-"); // usa "-" como separador

        int ano = sc.nextInt(); // le o ano
        int mes = sc.nextInt(); // le o mes
        int dia = sc.nextInt(); // le o dia

        sc.close(); // fecha Scanner

        return new Data(ano, mes, dia); // retorna a data criada
    }

    // formata a data em DD/MM/YYYY
    public String formatar() {
        String diaString; // guarda dia formatado
        String mesString; // guarda mes formatado

        if (this.dia < 10) { // se dia tiver um digito
            diaString = "0" + this.dia; // coloca zero na frente
        } else {
            diaString = "" + this.dia; // usa dia normal
        }

        if (this.mes < 10) { // se mes tiver um digito
            mesString = "0" + this.mes; // coloca zero na frente
        } else {
            mesString = "" + this.mes; // usa mes normal
        }

        return diaString + "/" + mesString + "/" + this.ano; // retorna data formatada
    }
}

// classe Hora
class Hora {
    private int hora; // guarda hora
    private int minuto; // guarda minuto

    // construtor
    public Hora(int hora, int minuto) {
        this.hora = hora; // salva hora
        this.minuto = minuto; // salva minuto
    }

    // transforma uma string HH:mm em Hora
    public static Hora parseHora(String s) {
        Scanner sc = new Scanner(s); // cria Scanner para a string
        sc.useDelimiter(":"); // usa ":" como separador

        int hora = sc.nextInt(); // le hora
        int minuto = sc.nextInt(); // le minuto

        sc.close(); // fecha Scanner

        return new Hora(hora, minuto); // retorna hora criada
    }

    // formata hora em HH:mm
    public String formatar() {
        String horaString; // guarda hora formatada
        String minutoString; // guarda minuto formatado

        if (this.hora < 10) { // se hora tiver um digito
            horaString = "0" + this.hora; // coloca zero na frente
        } else {
            horaString = "" + this.hora; // usa hora normal
        }

        if (this.minuto < 10) { // se minuto tiver um digito
            minutoString = "0" + this.minuto; // coloca zero na frente
        } else {
            minutoString = "" + this.minuto; // usa minuto normal
        }

        return horaString + ":" + minutoString; // retorna hora formatada
    }
}

// classe Restaurante
class Restaurante {
    private int id; // guarda id
    private String nome; // guarda nome
    private String cidade; // guarda cidade
    private int capacidade; // guarda capacidade
    private double avaliacao; // guarda avaliacao
    private String[] tiposCozinha; // guarda tipos de cozinha
    private int faixaPreco; // guarda faixa de preco
    private Hora horarioAbertura; // guarda horario de abertura
    private Hora horarioFechamento; // guarda horario de fechamento
    private Data dataAbertura; // guarda data de abertura
    private boolean aberto; // guarda se esta aberto

    // construtor
    public Restaurante(int id, String nome, String cidade, int capacidade, double avaliacao,
                       String[] tiposCozinha, int faixaPreco, Hora horarioAbertura,
                       Hora horarioFechamento, Data dataAbertura, boolean aberto) {
        this.id = id; // salva id
        this.nome = nome; // salva nome
        this.cidade = cidade; // salva cidade
        this.capacidade = capacidade; // salva capacidade
        this.avaliacao = avaliacao; // salva avaliacao
        this.tiposCozinha = tiposCozinha; // salva tipos
        this.faixaPreco = faixaPreco; // salva faixa
        this.horarioAbertura = horarioAbertura; // salva abertura
        this.horarioFechamento = horarioFechamento; // salva fechamento
        this.dataAbertura = dataAbertura; // salva data
        this.aberto = aberto; // salva aberto
    }

    // retorna id
    public int getId() {
        return this.id; // devolve id
    }

    // retorna nome
    public String getNome() {
        return this.nome; // devolve nome
    }

    // converte faixa de preco em inteiro
    public static int parseFaixaPreco(String s) {
        int resp = 0; // resposta inicial

        if (s.compareTo("$") == 0) { // se for $
            resp = 1; // guarda 1
        } else if (s.compareTo("$$") == 0) { // se for $$
            resp = 2; // guarda 2
        } else if (s.compareTo("$$$") == 0) { // se for $$$
            resp = 3; // guarda 3
        } else if (s.compareTo("$$$$") == 0) { // se for $$$$
            resp = 4; // guarda 4
        }

        return resp; // retorna faixa
    }

    // converte faixa de preco em string
    public static String formatarFaixaPreco(int faixaPreco) {
        String resp = ""; // resposta inicial

        for (int i = 0; i < faixaPreco; i++) { // repete de acordo com a faixa
            resp += "$"; // adiciona $
        }

        return resp; // retorna texto
    }

    // conta tipos de cozinha
    public static int contarTiposCozinha(String s) {
        Scanner sc = new Scanner(s); // cria Scanner
        sc.useDelimiter(";"); // usa ; como separador

        int count = 0; // contador

        while (sc.hasNext()) { // enquanto houver tipo
            sc.next(); // le tipo
            count++; // aumenta contador
        }

        sc.close(); // fecha Scanner

        return count; // retorna quantidade
    }

    // separa tipos de cozinha em vetor
    public static String[] parseTiposCozinha(String s) {
        int n = contarTiposCozinha(s); // conta tipos
        String[] resp = new String[n]; // cria vetor

        Scanner sc = new Scanner(s); // cria Scanner
        sc.useDelimiter(";"); // usa ; como separador

        for (int i = 0; i < n; i++) { // percorre tipos
            resp[i] = sc.next(); // guarda tipo
        }

        sc.close(); // fecha Scanner

        return resp; // retorna vetor
    }

    // cria restaurante a partir de uma linha do csv
    public static Restaurante parseRestaurante(String s) {
        Scanner sc = new Scanner(s); // cria Scanner da linha
        sc.useDelimiter(","); // usa virgula como separador

        int id = sc.nextInt(); // le id
        String nome = sc.next(); // le nome
        String cidade = sc.next(); // le cidade
        int capacidade = sc.nextInt(); // le capacidade
        double avaliacao = Double.parseDouble(sc.next()); // le avaliacao
        String tipos = sc.next(); // le tipos
        String faixa = sc.next(); // le faixa
        String horario = sc.next(); // le horario
        String data = sc.next(); // le data
        boolean aberto = Boolean.parseBoolean(sc.next()); // le aberto

        sc.close(); // fecha Scanner

        String[] tiposCozinha = parseTiposCozinha(tipos); // separa tipos
        int faixaPreco = parseFaixaPreco(faixa); // converte faixa

        Scanner scHorario = new Scanner(horario); // cria Scanner do horario
        scHorario.useDelimiter("-"); // separa por -

        String abertura = scHorario.next(); // le abertura
        String fechamento = scHorario.next(); // le fechamento

        scHorario.close(); // fecha Scanner

        Hora horarioAbertura = Hora.parseHora(abertura); // cria horario abertura
        Hora horarioFechamento = Hora.parseHora(fechamento); // cria horario fechamento
        Data dataAbertura = Data.parseData(data); // cria data abertura

        return new Restaurante(id, nome, cidade, capacidade, avaliacao,
                tiposCozinha, faixaPreco, horarioAbertura,
                horarioFechamento, dataAbertura, aberto); // retorna restaurante
    }

    // formata tipos de cozinha
    public String formatarTiposCozinha() {
        String resp = "["; // abre colchete

        for (int i = 0; i < this.tiposCozinha.length; i++) { // percorre tipos
            resp += this.tiposCozinha[i]; // adiciona tipo

            if (i < this.tiposCozinha.length - 1) { // se nao for ultimo
                resp += ","; // adiciona virgula
            }
        }

        resp += "]"; // fecha colchete

        return resp; // retorna tipos formatados
    }

    // formata restaurante completo
    public String formatar() {
        return "[" + this.id
                + " ## " + this.nome
                + " ## " + this.cidade
                + " ## " + this.capacidade
                + " ## " + this.avaliacao
                + " ## " + this.formatarTiposCozinha()
                + " ## " + formatarFaixaPreco(this.faixaPreco)
                + " ## " + this.horarioAbertura.formatar() + "-" + this.horarioFechamento.formatar()
                + " ## " + this.dataAbertura.formatar()
                + " ## " + this.aberto
                + "]"; // retorna restaurante formatado
    }
}

// classe ColecaoRestaurantes
class ColecaoRestaurantes {
    private int tamanho; // guarda quantidade
    private Restaurante[] restaurantes; // guarda vetor de restaurantes

    // construtor
    public ColecaoRestaurantes() {
        this.tamanho = 0; // inicia tamanho
        this.restaurantes = new Restaurante[0]; // inicia vetor
    }

    // le csv pelo caminho
    public void lerCsv(String path) {
        try {
            File arquivo = new File(path); // cria objeto File
            Scanner sc = new Scanner(arquivo); // abre Scanner do arquivo

            int count = 0; // contador

            if (sc.hasNextLine()) { // se tem cabecalho
                sc.nextLine(); // pula cabecalho
            }

            while (sc.hasNextLine()) { // conta linhas
                sc.nextLine(); // le linha
                count++; // aumenta contador
            }

            sc.close(); // fecha Scanner

            this.tamanho = count; // salva tamanho
            this.restaurantes = new Restaurante[this.tamanho]; // cria vetor

            sc = new Scanner(arquivo); // reabre Scanner

            if (sc.hasNextLine()) { // se tem cabecalho
                sc.nextLine(); // pula cabecalho
            }

            for (int i = 0; i < this.tamanho; i++) { // percorre linhas
                String linha = sc.nextLine(); // le linha
                this.restaurantes[i] = Restaurante.parseRestaurante(linha); // cria restaurante
            }

            sc.close(); // fecha Scanner

        } catch (FileNotFoundException e) { // se arquivo nao existir
            System.out.println("Erro ao abrir o arquivo."); // imprime erro
        }
    }

    // le csv padrao
    public static ColecaoRestaurantes lerCsv() {
        ColecaoRestaurantes colecao = new ColecaoRestaurantes(); // cria colecao
        colecao.lerCsv("/tmp/restaurantes.csv"); // le dataset
        return colecao; // retorna colecao
    }

    // busca restaurante por id
    public Restaurante buscarPorId(int id) {
        Restaurante resp = null; // resposta inicial

        for (int i = 0; i < this.tamanho; i++) { // percorre vetor
            if (this.restaurantes[i].getId() == id) { // se id bate
                resp = this.restaurantes[i]; // guarda restaurante
                i = this.tamanho; // encerra laco
            }
        }

        return resp; // retorna restaurante ou null
    }
}

// no da arvore binaria de filhos da Trie
class NoFilho {
    public char caractere; // guarda caractere do filho
    public NoTrie filho; // aponta para o no filho da Trie
    public NoFilho esq; // aponta esquerda
    public NoFilho dir; // aponta direita

    // construtor
    public NoFilho(char caractere, NoTrie filho) {
        this.caractere = caractere; // salva caractere
        this.filho = filho; // salva filho
        this.esq = null; // inicia esquerda
        this.dir = null; // inicia direita
    }
}

// no da Trie
class NoTrie {
    public char caractere; // guarda caractere do no
    public boolean fim; // indica se termina uma palavra
    public Restaurante restaurante; // guarda restaurante no final do nome
    public NoFilho raizFilhos; // raiz da arvore binaria de filhos

    // construtor
    public NoTrie(char caractere) {
        this.caractere = caractere; // salva caractere
        this.fim = false; // inicia nao sendo fim
        this.restaurante = null; // inicia restaurante nulo
        this.raizFilhos = null; // inicia sem filhos
    }

    // busca filho por caractere
    public NoTrie buscarFilho(char c) {
        return buscarFilho(c, this.raizFilhos); // chama busca recursiva
    }

    // busca filho recursivamente na arvore de filhos
    private NoTrie buscarFilho(char c, NoFilho no) {
        NoTrie resp = null; // resposta inicial

        if (no != null) { // se no existe
            if (c == no.caractere) { // se achou caractere
                resp = no.filho; // guarda filho
            } else if (c < no.caractere) { // se caractere menor
                resp = buscarFilho(c, no.esq); // busca esquerda
            } else {
                resp = buscarFilho(c, no.dir); // busca direita
            }
        }

        return resp; // retorna filho ou null
    }

    // adiciona filho se ele nao existir
    public NoTrie adicionarFilho(char c) {
        NoTrie filho = buscarFilho(c); // tenta buscar filho

        if (filho == null) { // se filho nao existe
            filho = new NoTrie(c); // cria novo no da Trie
            this.raizFilhos = inserirFilho(c, filho, this.raizFilhos); // insere na arvore de filhos
        }

        return filho; // retorna filho
    }

    // insere filho na arvore binaria de filhos
    private NoFilho inserirFilho(char c, NoTrie filho, NoFilho no) {
        if (no == null) { // se posicao vazia
            no = new NoFilho(c, filho); // cria novo no filho
        } else if (c < no.caractere) { // se menor
            no.esq = inserirFilho(c, filho, no.esq); // insere esquerda
        } else if (c > no.caractere) { // se maior
            no.dir = inserirFilho(c, filho, no.dir); // insere direita
        }

        return no; // retorna no
    }
}

// arvore Trie com arvore binaria nos filhos
class ArvoreTrieArvore {
    private NoTrie raiz; // raiz da Trie
    private long comparacoes; // contador de comparacoes

    // construtor
    public ArvoreTrieArvore() {
        this.raiz = new NoTrie('/'); // cria raiz simbolica
        this.comparacoes = 0; // zera comparacoes
    }

    // retorna comparacoes
    public long getComparacoes() {
        return this.comparacoes; // devolve comparacoes
    }

    // insere restaurante
    public void inserir(Restaurante restaurante) {
        NoTrie atual = this.raiz; // comeca na raiz
        String nome = restaurante.getNome(); // pega nome

        for (int i = 0; i < nome.length(); i++) { // percorre caracteres do nome
            char c = nome.charAt(i); // pega caractere
            atual = atual.adicionarFilho(c); // cria ou pega filho
        }

        atual.fim = true; // marca final do nome
        atual.restaurante = restaurante; // guarda restaurante
    }

    // pesquisa nome
    public void pesquisar(String nome) {
        NoTrie atual = this.raiz; // comeca na raiz
        boolean caminho = true; // controla se o caminho ainda existe
        boolean imprimiu = false; // controla espaco entre caracteres impressos
        int i = 0; // indice do nome

        while (i < nome.length() && caminho) { // percorre caracteres enquanto existir caminho
            char c = nome.charAt(i); // pega caractere atual

            this.comparacoes++; // conta tentativa de acesso ao proximo no

            NoTrie filho = atual.buscarFilho(c); // busca filho antes de imprimir

            if (filho == null) { // se nao existe filho
                caminho = false; // para sem imprimir o caractere inexistente
            } else {
                if (imprimiu) { // se ja imprimiu algum caractere antes
                    System.out.print(" "); // separa os caracteres por espaco
                }

                System.out.print(c); // imprime somente caractere realmente visitado
                imprimiu = true; // marca que ja imprimiu caractere
                atual = filho; // avanca na Trie
                i++; // avanca para o proximo caractere
            }
        }

        if (caminho && i == nome.length() && atual.fim && atual.restaurante != null) { // se achou palavra completa
            System.out.println(" SIM " + atual.restaurante.formatar()); // imprime SIM separado do ultimo caractere visitado
        } else {
            System.out.println(" NAO"); // imprime NAO apos os caracteres visitados
        }
    }
}

// classe principal
public class q10arvoreTrieArvore {
    public static final String MATRICULA = "898128"; // matricula

    // verifica se texto e FIM
    public static boolean isFim(String s) {
        return s.compareTo("FIM") == 0; // retorna true se for FIM
    }

    // cria arquivo de log
    public static void criarLog(long comparacoes) {
        try {
            PrintWriter pw = new PrintWriter(MATRICULA + "_arvore_trie_arvore.txt"); // cria log
            pw.println(MATRICULA + "\t" + comparacoes + "\t0.0"); // escreve log
            pw.close(); // fecha log
        } catch (FileNotFoundException e) { // se der erro
            System.out.println("Erro ao criar arquivo de log."); // imprime erro
        }
    }

    // metodo principal
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); // cria Scanner de entrada
        ColecaoRestaurantes colecao = ColecaoRestaurantes.lerCsv(); // le dataset
        ArvoreTrieArvore trie = new ArvoreTrieArvore(); // cria Trie

        int id; // guarda id
        String nome; // guarda nome pesquisado

        id = sc.nextInt(); // le primeiro id

        while (id != -1) { // enquanto nao for -1
            Restaurante restaurante = colecao.buscarPorId(id); // busca restaurante

            if (restaurante != null) { // se encontrou
                trie.inserir(restaurante); // insere na Trie
            }

            id = sc.nextInt(); // le proximo id
        }

        sc.nextLine(); // consome quebra depois do -1

        nome = sc.nextLine(); // le primeiro nome completo

        while (!isFim(nome)) { // enquanto nao for FIM
            trie.pesquisar(nome); // pesquisa e imprime
            nome = sc.nextLine(); // le proximo nome completo
        }

        criarLog(trie.getComparacoes()); // cria log

        sc.close(); 
    }
}

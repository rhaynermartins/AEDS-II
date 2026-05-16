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
        this.ano = ano; // salva ano
        this.mes = mes; // salva mes
        this.dia = dia; // salva dia
    }

    // transforma string YYYY-MM-DD em Data
    public static Data parseData(String s) {
        Scanner sc = new Scanner(s); // cria Scanner para a string
        sc.useDelimiter("-"); // define "-" como separador

        int ano = sc.nextInt(); // le ano
        int mes = sc.nextInt(); // le mes
        int dia = sc.nextInt(); // le dia

        sc.close(); 

        return new Data(ano, mes, dia); // retorna data criada
    }

    // formata data em DD/MM/YYYY
    public String formatar() {
        String diaString; // guarda dia formatado
        String mesString; // guarda mes formatado

        if (this.dia < 10) { // se dia tiver um digito
            diaString = "0" + this.dia; // coloca zero
        } else {
            diaString = "" + this.dia; // usa dia normal
        }

        if (this.mes < 10) { // se mes tiver um digito
            mesString = "0" + this.mes; // coloca zero
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

    // transforma string HH:mm em Hora
    public static Hora parseHora(String s) {
        Scanner sc = new Scanner(s); // cria Scanner para a string
        sc.useDelimiter(":"); // define ":" como separador

        int hora = sc.nextInt(); // le hora
        int minuto = sc.nextInt(); // le minuto

        sc.close(); 

        return new Hora(hora, minuto); // retorna hora criada
    }

    // formata hora em HH:mm
    public String formatar() {
        String horaString; // guarda hora formatada
        String minutoString; // guarda minuto formatado

        if (this.hora < 10) { // se hora tiver um digito
            horaString = "0" + this.hora; // coloca zero
        } else {
            horaString = "" + this.hora; // usa hora normal
        }

        if (this.minuto < 10) { // se minuto tiver um digito
            minutoString = "0" + this.minuto; // coloca zero
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
    private Hora horarioAbertura; // guarda abertura
    private Hora horarioFechamento; // guarda fechamento
    private Data dataAbertura; // guarda data de abertura
    private boolean aberto; // guarda aberto

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

    // converte "$" para numero
    public static int parseFaixaPreco(String s) {
        int resp = 0; // resposta

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

    // converte numero para "$"
    public static String formatarFaixaPreco(int faixaPreco) {
        String resp = ""; // resposta

        for (int i = 0; i < faixaPreco; i++) { // repete pela faixa
            resp += "$"; // adiciona $
        }

        return resp; // retorna texto
    }

    // conta tipos de cozinha
    public static int contarTiposCozinha(String s) {
        Scanner sc = new Scanner(s); // cria Scanner
        sc.useDelimiter(";"); // define separador

        int count = 0; // contador

        while (sc.hasNext()) { // enquanto houver tipo
            sc.next(); // le tipo
            count++; // aumenta contador
        }

        sc.close(); 

        return count; // retorna quantidade
    }

    // separa tipos de cozinha
    public static String[] parseTiposCozinha(String s) {
        int n = contarTiposCozinha(s); // conta tipos
        String[] resp = new String[n]; // cria vetor

        Scanner sc = new Scanner(s); // cria Scanner
        sc.useDelimiter(";"); // define separador

        for (int i = 0; i < n; i++) { // percorre tipos
            resp[i] = sc.next(); // salva tipo
        }

        sc.close(); 

        return resp; // retorna vetor
    }

    // cria restaurante a partir da linha csv
    public static Restaurante parseRestaurante(String s) {
        Scanner sc = new Scanner(s); // cria Scanner
        sc.useDelimiter(","); // define "," como separador

        int id = sc.nextInt(); // le id
        String nome = sc.next(); // le nome
        String cidade = sc.next(); // le cidade
        int capacidade = sc.nextInt(); // le capacidade
        double avaliacao = sc.nextDouble(); // le avaliacao
        String tipos = sc.next(); // le tipos
        String faixa = sc.next(); // le faixa
        String horario = sc.next(); // le horario
        String data = sc.next(); // le data
        boolean aberto = sc.nextBoolean(); // le aberto

        sc.close(); // fecha Scanner da linha

        String[] tiposCozinha = parseTiposCozinha(tipos); // converte tipos
        int faixaPreco = parseFaixaPreco(faixa); // converte faixa

        Scanner scHorario = new Scanner(horario); // cria Scanner para horario
        scHorario.useDelimiter("-"); // define "-" como separador

        String abertura = scHorario.next(); // le abertura
        String fechamento = scHorario.next(); // le fechamento

        scHorario.close(); // fecha Scanner do horario

        Hora horarioAbertura = Hora.parseHora(abertura); // cria hora abertura
        Hora horarioFechamento = Hora.parseHora(fechamento); // cria hora fechamento
        Data dataAbertura = Data.parseData(data); // cria data

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

        return resp; // retorna string
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
                + "]";
    }
}

// colecao de restaurantes
class ColecaoRestaurantes {
    private int tamanho; // guarda tamanho
    private Restaurante[] restaurantes; // guarda restaurantes

    // construtor
    public ColecaoRestaurantes() {
        this.tamanho = 0; // inicia tamanho
        this.restaurantes = new Restaurante[0]; // inicia vetor
    }

    // le csv
    public void lerCsv(String path) {
        try {
            File arquivo = new File(path); // cria arquivo
            Scanner sc = new Scanner(arquivo); // abre Scanner

            int count = 0; // contador

            if (sc.hasNextLine()) { // se tiver cabecalho
                sc.nextLine(); // pula cabecalho
            }

            while (sc.hasNextLine()) { // enquanto tiver linha
                sc.nextLine(); // le linha
                count++; // conta
            }

            sc.close(); // fecha Scanner

            this.tamanho = count; // salva tamanho
            this.restaurantes = new Restaurante[this.tamanho]; // cria vetor

            sc = new Scanner(arquivo); // reabre Scanner

            if (sc.hasNextLine()) { // se tiver cabecalho
                sc.nextLine(); // pula cabecalho
            }

            for (int i = 0; i < this.tamanho; i++) { // percorre linhas
                String linha = sc.nextLine(); // le linha
                this.restaurantes[i] = Restaurante.parseRestaurante(linha); // cria restaurante
            }

            sc.close(); // fecha Scanner
        } catch (FileNotFoundException e) { // se nao encontrar
            System.out.println("Erro ao abrir o arquivo."); // imprime erro
        }
    }

    // le csv padrao
    public static ColecaoRestaurantes lerCsv() {
        ColecaoRestaurantes colecao = new ColecaoRestaurantes(); // cria colecao
        colecao.lerCsv("/tmp/restaurantes.csv"); // le csv
        return colecao; // retorna colecao
    }

    // busca por id
    public Restaurante buscarPorId(int id) {
        Restaurante resp = null; // resposta

        for (int i = 0; i < this.tamanho; i++) { // percorre vetor
            if (this.restaurantes[i].getId() == id) { // se id bate
                resp = this.restaurantes[i]; // salva
                i = this.tamanho; // encerra laco
            }
        }

        return resp; // retorna restaurante
    }
}

// classe No
class No {
    public Restaurante elemento; // guarda restaurante
    public No esq; // aponta esquerda
    public No dir; // aponta direita

    // construtor
    public No(Restaurante elemento) {
        this.elemento = elemento; // salva restaurante
        this.esq = null; // inicia esquerda
        this.dir = null; // inicia direita
    }
}

// classe ArvoreBinaria
class ArvoreBinaria {
    private No raiz; // guarda raiz
    private long comparacoes; // conta comparacoes

    // construtor
    public ArvoreBinaria() {
        this.raiz = null; // inicia vazia
        this.comparacoes = 0; // inicia comparacoes
    }

    // retorna comparacoes
    public long getComparacoes() {
        return this.comparacoes; // devolve comparacoes
    }

    // insere restaurante
    public void inserir(Restaurante restaurante) {
        this.raiz = inserir(restaurante, this.raiz); // chama insercao recursiva
    }

    // insere recursivamente por nome
    private No inserir(Restaurante restaurante, No no) {
        if (no == null) { // se chegou em posicao vazia
            no = new No(restaurante); // cria novo no
        } else {
            int cmp = restaurante.getNome().compareTo(no.elemento.getNome()); // compara nomes
            this.comparacoes++; // conta comparacao

            if (cmp < 0) { // se nome menor
                no.esq = inserir(restaurante, no.esq); // insere esquerda
            } else if (cmp > 0) { // se nome maior
                no.dir = inserir(restaurante, no.dir); // insere direita
            }
        }

        return no; // retorna no
    }

    // pesquisa e imprime caminho
    public void pesquisar(String nome) {
        System.out.print("raiz"); // sempre começa pela raiz
        boolean achou = pesquisar(nome, this.raiz); // chama pesquisa

        if (achou) { // se encontrou
            System.out.println(" SIM"); // imprime SIM
        } else {
            System.out.println(" NAO"); // imprime NAO
        }
    }

    // pesquisa recursiva
    private boolean pesquisar(String nome, No no) {
        boolean resp; // resposta

        if (no == null) { // se chegou em null
            resp = false; // nao achou
        } else {
            int cmp = nome.compareTo(no.elemento.getNome()); // compara nomes
            this.comparacoes++; // conta comparacao

            if (cmp == 0) { // se encontrou
                resp = true; // achou
            } else if (cmp < 0) { // se deve ir para esquerda
                System.out.print(" esq"); // imprime caminho
                resp = pesquisar(nome, no.esq); // pesquisa esquerda
            } else {
                System.out.print(" dir"); // imprime caminho
                resp = pesquisar(nome, no.dir); // pesquisa direita
            }
        }

        return resp; // retorna resposta
    }

    // mostra em ordem
    public void caminharCentral() {
        caminharCentral(this.raiz); // chama recursivo
    }

    // percorre em ordem
    private void caminharCentral(No no) {
        if (no != null) { // se no existe
            caminharCentral(no.esq); // visita esquerda
            System.out.println(no.elemento.formatar()); // imprime restaurante
            caminharCentral(no.dir); // visita direita
        }
    }
}

// classe principal
public class q12arvoreBinaria {
    public static final String MATRICULA = "898128"; // matricula

    // verifica se palavra e FIM
    public static boolean isFim(String s) {
        return s.compareTo("FIM") == 0; // retorna true se for FIM
    }

    // cria log
    public static void criarLog(long comparacoes, double tempo) {
        try {
            PrintWriter pw = new PrintWriter(MATRICULA + "_arvore_binaria.txt"); // cria arquivo

            pw.println(MATRICULA + "\t" + comparacoes + "\t" + tempo); // escreve log

            pw.close(); // fecha arquivo
        } catch (FileNotFoundException e) { // se der erro
            System.out.println("Erro ao criar arquivo de log."); // imprime erro
        }
    }

    // metodo principal
    // metodo principal
public static void main(String[] args) {
    Scanner sc = new Scanner(System.in); // cria Scanner
    ColecaoRestaurantes colecao = ColecaoRestaurantes.lerCsv(); // le csv
    ArvoreBinaria arvore = new ArvoreBinaria(); // cria arvore

    int id; // guarda id
    String nome; // guarda nome pesquisado
    long inicio; // tempo inicial
    long fim; // tempo final
    double tempo; // tempo total

    id = sc.nextInt(); // le primeiro id

    inicio = System.currentTimeMillis(); // marca inicio

    while (id != -1) { // enquanto nao for -1
        Restaurante restaurante = colecao.buscarPorId(id); // busca restaurante

        if (restaurante != null) { // se encontrou
            arvore.inserir(restaurante); // insere na arvore
        }

        id = sc.nextInt(); // le proximo id
    }

    sc.nextLine(); // consome a quebra de linha depois do -1

    nome = sc.nextLine(); // le o primeiro nome completo

    while (!isFim(nome)) { // enquanto nao for FIM
        arvore.pesquisar(nome); // pesquisa nome completo
        nome = sc.nextLine(); // le proximo nome completo
    }

    arvore.caminharCentral(); // mostra registros em ordem

    fim = System.currentTimeMillis(); // marca fim

    tempo = (fim - inicio) / 1000.0; // calcula tempo

    criarLog(arvore.getComparacoes(), tempo); // cria log

    sc.close(); // fecha Scanner
   }
}
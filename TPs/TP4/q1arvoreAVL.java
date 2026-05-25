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

    // recebe uma string YYYY-MM-DD e cria uma Data
    public static Data parseData(String s) {
        Scanner sc = new Scanner(s); // cria Scanner para a string
        sc.useDelimiter("-"); // usa - como separador

        int ano = sc.nextInt(); // le o ano
        int mes = sc.nextInt(); // le o mes
        int dia = sc.nextInt(); // le o dia

        sc.close(); // fecha Scanner

        return new Data(ano, mes, dia); // retorna Data criada
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
    private int hora; // guarda a hora
    private int minuto; // guarda o minuto

    // construtor
    public Hora(int hora, int minuto) {
        this.hora = hora; // salva a hora
        this.minuto = minuto; // salva o minuto
    }

    // recebe uma string HH:mm e cria uma Hora
    public static Hora parseHora(String s) {
        Scanner sc = new Scanner(s); // cria Scanner para a string
        sc.useDelimiter(":"); // usa : como separador

        int hora = sc.nextInt(); // le a hora
        int minuto = sc.nextInt(); // le o minuto

        sc.close(); // fecha Scanner

        return new Hora(hora, minuto); // retorna Hora criada
    }

    // formata a hora em HH:mm
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
    private int id; // guarda o id
    private String nome; // guarda o nome
    private String cidade; // guarda a cidade
    private int capacidade; // guarda a capacidade
    private double avaliacao; // guarda a avaliacao
    private String[] tiposCozinha; // guarda os tipos de cozinha
    private int faixaPreco; // guarda a faixa de preco
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

    // converte faixa de preco de string para inteiro
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

    // converte faixa de preco de inteiro para string
    public static String formatarFaixaPreco(int faixaPreco) {
        String resp = ""; // resposta inicial

        for (int i = 0; i < faixaPreco; i++) { // repete pela faixa
            resp += "$"; // adiciona $
        }

        return resp; // retorna string
    }

    // conta tipos de cozinha separados por ;
    public static int contarTiposCozinha(String s) {
        Scanner sc = new Scanner(s); // cria Scanner
        sc.useDelimiter(";"); // usa ; como separador

        int count = 0; // contador

        while (sc.hasNext()) { // enquanto tiver tipo
            sc.next(); // le tipo
            count++; // aumenta contador
        }

        sc.close(); // fecha Scanner

        return count; // retorna quantidade
    }

    // transforma os tipos de cozinha em vetor
    public static String[] parseTiposCozinha(String s) {
        int n = contarTiposCozinha(s); // conta tipos
        String[] resp = new String[n]; // cria vetor

        Scanner sc = new Scanner(s); // cria Scanner
        sc.useDelimiter(";"); // usa ; como separador

        for (int i = 0; i < n; i++) { // percorre vetor
            resp[i] = sc.next(); // salva tipo
        }

        sc.close(); // fecha Scanner

        return resp; // retorna vetor
    }

    // cria Restaurante a partir da linha do csv
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

        sc.close(); // fecha Scanner da linha

        String[] tiposCozinha = parseTiposCozinha(tipos); // converte tipos
        int faixaPreco = parseFaixaPreco(faixa); // converte faixa

        Scanner scHorario = new Scanner(horario); // cria Scanner para horario
        scHorario.useDelimiter("-"); // usa - como separador

        String abertura = scHorario.next(); // le abertura
        String fechamento = scHorario.next(); // le fechamento

        scHorario.close(); // fecha Scanner do horario

        Hora horarioAbertura = Hora.parseHora(abertura); // cria hora de abertura
        Hora horarioFechamento = Hora.parseHora(fechamento); // cria hora de fechamento
        Data dataAbertura = Data.parseData(data); // cria data

        return new Restaurante(id, nome, cidade, capacidade, avaliacao,
                tiposCozinha, faixaPreco, horarioAbertura,
                horarioFechamento, dataAbertura, aberto); // retorna restaurante
    }

    // formata os tipos de cozinha
    public String formatarTiposCozinha() {
        String resp = "["; // abre colchete

        for (int i = 0; i < this.tiposCozinha.length; i++) { // percorre tipos
            resp += this.tiposCozinha[i]; // adiciona tipo

            if (i < this.tiposCozinha.length - 1) { // se nao for ultimo
                resp += ","; // adiciona virgula
            }
        }

        resp += "]"; // fecha colchete

        return resp; // retorna texto
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

// classe ColecaoRestaurantes
class ColecaoRestaurantes {
    private int tamanho; // guarda quantidade de restaurantes
    private Restaurante[] restaurantes; // guarda restaurantes

    // construtor
    public ColecaoRestaurantes() {
        this.tamanho = 0; // inicia tamanho
        this.restaurantes = new Restaurante[0]; // inicia vetor vazio
    }

    // le csv pelo caminho
    public void lerCsv(String path) {
        try {
            File arquivo = new File(path); // representa o arquivo
            Scanner sc = new Scanner(arquivo); // abre Scanner

            int count = 0; // contador

            if (sc.hasNextLine()) { // se tem cabecalho
                sc.nextLine(); // pula cabecalho
            }

            while (sc.hasNextLine()) { // enquanto tem linha
                sc.nextLine(); // le linha
                count++; // conta
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
        colecao.lerCsv("/tmp/restaurantes.csv"); // le csv padrao
        return colecao; // retorna colecao
    }

    // busca restaurante por id
    public Restaurante buscarPorId(int id) {
        Restaurante resp = null; // resposta inicial

        for (int i = 0; i < this.tamanho; i++) { // percorre vetor
            if (this.restaurantes[i].getId() == id) { // se id bate
                resp = this.restaurantes[i]; // salva restaurante
                i = this.tamanho; // encerra laco
            }
        }

        return resp; // retorna restaurante ou null
    }
}

// classe NoAVL
class NoAVL {
    public Restaurante elemento; // guarda restaurante
    public NoAVL esq; // aponta para esquerda
    public NoAVL dir; // aponta para direita
    public int nivel; // guarda nivel do no

    // construtor
    public NoAVL(Restaurante elemento) {
        this.elemento = elemento; // salva restaurante
        this.esq = null; // inicia esquerda
        this.dir = null; // inicia direita
        this.nivel = 1; // no novo começa no nivel 1
    }

    // retorna nivel do no recebido
    public static int getNivel(NoAVL no) {
        int resp = 0; // nivel de no nulo

        if (no != null) { // se no existe
            resp = no.nivel; // pega nivel
        }

        return resp; // retorna nivel
    }

    // atualiza nivel do no atual
    public void setNivel() {
        int nivelEsq = getNivel(this.esq); // pega nivel da esquerda
        int nivelDir = getNivel(this.dir); // pega nivel da direita

        if (nivelEsq > nivelDir) { // se esquerda maior
            this.nivel = 1 + nivelEsq; // usa esquerda
        } else {
            this.nivel = 1 + nivelDir; // usa direita
        }
    }

    // calcula fator de balanceamento
    public int getFator() {
        return getNivel(this.dir) - getNivel(this.esq); // retorna direita - esquerda
    }
}

// classe ArvoreAVL
class ArvoreAVL {
    private NoAVL raiz; // guarda raiz da arvore
    private long comparacoes; // guarda comparacoes das pesquisas

    // construtor
    public ArvoreAVL() {
        this.raiz = null; // inicia raiz nula
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

    // insere recursivamente
    private NoAVL inserir(Restaurante restaurante, NoAVL no) {
        if (no == null) { // se posicao vazia
            no = new NoAVL(restaurante); // cria novo no
        } else {
            int cmp = restaurante.getNome().compareTo(no.elemento.getNome()); // compara nomes

            if (cmp < 0) { // se nome menor
                no.esq = inserir(restaurante, no.esq); // insere na esquerda
            } else if (cmp > 0) { // se nome maior
                no.dir = inserir(restaurante, no.dir); // insere na direita
            }
        }

        no = balancear(no); // balanceia o no

        return no; // retorna no balanceado
    }

    // balanceia um no
    private NoAVL balancear(NoAVL no) {
        if (no != null) { // se no existe
            no.setNivel(); // atualiza nivel

            int fator = no.getFator(); // calcula fator

            if (fator == 2) { // se esta pesado para direita
                if (no.dir.getFator() < 0) { // se caso direita-esquerda
                    no.dir = rotacionarDir(no.dir); // rotaciona filho para direita
                }

                no = rotacionarEsq(no); // rotaciona no para esquerda

            } else if (fator == -2) { // se esta pesado para esquerda
                if (no.esq.getFator() > 0) { // se caso esquerda-direita
                    no.esq = rotacionarEsq(no.esq); // rotaciona filho para esquerda
                }

                no = rotacionarDir(no); // rotaciona no para direita
            }
        }

        return no; // retorna no
    }

    // rotacao simples para esquerda
    private NoAVL rotacionarEsq(NoAVL no) {
        NoAVL noDir = no.dir; // guarda filho direito
        NoAVL noDirEsq = noDir.esq; // guarda subarvore esquerda do filho direito

        noDir.esq = no; // filho direito vira pai
        no.dir = noDirEsq; // antiga subarvore vira direita do no

        no.setNivel(); // atualiza nivel do antigo pai
        noDir.setNivel(); // atualiza nivel do novo pai

        return noDir; // retorna nova raiz da subarvore
    }

    // rotacao simples para direita
    private NoAVL rotacionarDir(NoAVL no) {
        NoAVL noEsq = no.esq; // guarda filho esquerdo
        NoAVL noEsqDir = noEsq.dir; // guarda subarvore direita do filho esquerdo

        noEsq.dir = no; // filho esquerdo vira pai
        no.esq = noEsqDir; // antiga subarvore vira esquerda do no

        no.setNivel(); // atualiza nivel do antigo pai
        noEsq.setNivel(); // atualiza nivel do novo pai

        return noEsq; // retorna nova raiz da subarvore
    }

    // pesquisa nome imprimindo caminho
    public void pesquisar(String nome) {
        System.out.print("raiz"); // imprime inicio do caminho

        boolean achou = pesquisar(nome, this.raiz); // faz pesquisa

        if (achou) { // se achou
            System.out.println(" SIM"); // imprime SIM
        } else {
            System.out.println(" NAO"); // imprime NAO
        }
    }

    // pesquisa recursiva
    private boolean pesquisar(String nome, NoAVL no) {
        boolean resp; // resposta

        if (no == null) { // se chegou em null
            resp = false; // nao achou
        } else {
            int cmp = nome.compareTo(no.elemento.getNome()); // compara nomes
            this.comparacoes++; // conta comparacao de pesquisa

            if (cmp == 0) { // se encontrou
                resp = true; // achou
            } else if (cmp < 0) { // se deve ir esquerda
                System.out.print(" esq"); // imprime caminho
                resp = pesquisar(nome, no.esq); // pesquisa esquerda
            } else {
                System.out.print(" dir"); // imprime caminho
                resp = pesquisar(nome, no.dir); // pesquisa direita
            }
        }

        return resp; // retorna resultado
    }

    // mostra em ordem
    public void caminharCentral() {
        caminharCentral(this.raiz); // chama recursivo
    }

    // caminhamento em ordem
    private void caminharCentral(NoAVL no) {
        if (no != null) { // se no existe
            caminharCentral(no.esq); // visita esquerda
            System.out.println(no.elemento.formatar()); // imprime restaurante
            caminharCentral(no.dir); // visita direita
        }
    }
}

// classe principal
public class q1arvoreAVL {
    public static final String MATRICULA = "898128"; // matricula

    // verifica se texto e FIM
    public static boolean isFim(String s) {
        return s.compareTo("FIM") == 0; // retorna true se for FIM
    }

    // cria arquivo de log
    public static void criarLog(long comparacoes, double tempo) {
        try {
            PrintWriter pw = new PrintWriter(MATRICULA + "_arvore_avl.txt"); // cria log

            pw.println(MATRICULA + "\t" + comparacoes + "\t" + tempo); // escreve log

            pw.close(); // fecha log

        } catch (FileNotFoundException e) { // se der erro
            System.out.println("Erro ao criar arquivo de log."); // imprime erro
        }
    }

    // metodo principal
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); // cria Scanner da entrada
        ColecaoRestaurantes colecao = ColecaoRestaurantes.lerCsv(); // le csv
        ArvoreAVL arvore = new ArvoreAVL(); // cria AVL

        int id; // guarda id
        String nome; // guarda nome pesquisado
        long inicio; // tempo inicial
        long fim; // tempo final
        double tempo; // tempo total

        id = sc.nextInt(); // le primeiro id

        while (id != -1) { // enquanto nao for -1
            Restaurante restaurante = colecao.buscarPorId(id); // busca restaurante no csv

            if (restaurante != null) { // se encontrou
                arvore.inserir(restaurante); // insere na AVL
            }

            id = sc.nextInt(); // le proximo id
        }

        sc.nextLine(); // consome quebra de linha depois do -1

        inicio = System.currentTimeMillis(); // marca inicio das pesquisas

        nome = sc.nextLine(); // le primeiro nome completo

        while (!isFim(nome)) { // enquanto nao for FIM
            arvore.pesquisar(nome); // pesquisa na AVL
            nome = sc.nextLine(); // le proximo nome completo
        }

        fim = System.currentTimeMillis(); // marca fim das pesquisas

        tempo = (fim - inicio) / 1000.0; // calcula tempo

        arvore.caminharCentral(); // mostra registros em ordem

        criarLog(arvore.getComparacoes(), tempo); // cria log

        sc.close(); 
    }
}
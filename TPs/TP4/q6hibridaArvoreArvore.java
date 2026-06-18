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
        sc.useDelimiter("-"); // define - como separador

        int ano = sc.nextInt(); // le ano
        int mes = sc.nextInt(); // le mes
        int dia = sc.nextInt(); // le dia

        sc.close(); // fecha Scanner

        return new Data(ano, mes, dia); // retorna Data criada
    }

    // formata data em DD/MM/YYYY
    public String formatar() {
        String diaString; // guarda dia formatado
        String mesString; // guarda mes formatado

        if (this.dia < 10) { // se dia tiver um digito
            diaString = "0" + this.dia; // coloca zero na frente
        } else {
            diaString = "" + this.dia; // escreve normal
        }

        if (this.mes < 10) { // se mes tiver um digito
            mesString = "0" + this.mes; // coloca zero na frente
        } else {
            mesString = "" + this.mes; // escreve normal
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
        this.hora = hora; // salva hora
        this.minuto = minuto; // salva minuto
    }

    // recebe uma string HH:mm e cria uma Hora
    public static Hora parseHora(String s) {
        Scanner sc = new Scanner(s); // cria Scanner para a string
        sc.useDelimiter(":"); // define : como separador

        int hora = sc.nextInt(); // le hora
        int minuto = sc.nextInt(); // le minuto

        sc.close(); // fecha Scanner

        return new Hora(hora, minuto); // retorna Hora criada
    }

    // formata hora em HH:mm
    public String formatar() {
        String horaString; // guarda hora formatada
        String minutoString; // guarda minuto formatado

        if (this.hora < 10) { // se hora tiver um digito
            horaString = "0" + this.hora; // coloca zero na frente
        } else {
            horaString = "" + this.hora; // escreve normal
        }

        if (this.minuto < 10) { // se minuto tiver um digito
            minutoString = "0" + this.minuto; // coloca zero na frente
        } else {
            minutoString = "" + this.minuto; // escreve normal
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

    // retorna capacidade
    public int getCapacidade() {
        return this.capacidade; // devolve capacidade
    }

    // converte faixa de preco para inteiro
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

    // converte faixa de preco para string
    public static String formatarFaixaPreco(int faixaPreco) {
        String resp = ""; // resposta inicial

        for (int i = 0; i < faixaPreco; i++) { // repete conforme a faixa
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

    // transforma tipos em vetor
    public static String[] parseTiposCozinha(String s) {
        int n = contarTiposCozinha(s); // conta tipos
        String[] resp = new String[n]; // cria vetor

        Scanner sc = new Scanner(s); // cria Scanner
        sc.useDelimiter(";"); // usa ; como separador

        for (int i = 0; i < n; i++) { // percorre vetor
            resp[i] = sc.next(); // salva tipo
        }

        sc.close(); 

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

        sc.close(); // fecha Scanner

        String[] tiposCozinha = parseTiposCozinha(tipos); // converte tipos
        int faixaPreco = parseFaixaPreco(faixa); // converte faixa

        Scanner scHorario = new Scanner(horario); // cria Scanner do horario
        scHorario.useDelimiter("-"); // usa - como separador

        String abertura = scHorario.next(); // le abertura
        String fechamento = scHorario.next(); // le fechamento

        scHorario.close(); // fecha Scanner

        Hora horarioAbertura = Hora.parseHora(abertura); // cria hora abertura
        Hora horarioFechamento = Hora.parseHora(fechamento); // cria hora fechamento
        Data dataAbertura = Data.parseData(data); // cria data

        return new Restaurante(id, nome, cidade, capacidade, avaliacao,
                tiposCozinha, faixaPreco, horarioAbertura,
                horarioFechamento, dataAbertura, aberto); // retorna restaurante
    }

    // formata tipos
    public String formatarTiposCozinha() {
        String resp = "["; // abre colchete

        for (int i = 0; i < this.tiposCozinha.length; i++) { // percorre tipos
            resp += this.tiposCozinha[i]; // adiciona tipo

            if (i < this.tiposCozinha.length - 1) { // se nao for ultimo
                resp += ","; // coloca virgula
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
    private int tamanho; // guarda quantidade de restaurantes
    private Restaurante[] restaurantes; // guarda restaurantes

    // construtor
    public ColecaoRestaurantes() {
        this.tamanho = 0; // inicia tamanho
        this.restaurantes = new Restaurante[0]; // inicia vetor
    }

    // le csv
    public void lerCsv(String path) {
        try {
            File arquivo = new File(path); // representa arquivo
            Scanner sc = new Scanner(arquivo); // abre Scanner

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

        } catch (FileNotFoundException e) { // se nao encontrar arquivo
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
            if (this.restaurantes[i].getId() == id) { // se achou id
                resp = this.restaurantes[i]; // guarda restaurante
                i = this.tamanho; // encerra laco
            }
        }

        return resp; // retorna restaurante ou null
    }
}

// no da AVL do segundo nivel
class NoAVL {
    public Restaurante elemento; // guarda restaurante
    public NoAVL esq; // aponta esquerda
    public NoAVL dir; // aponta direita
    public int nivel; // guarda nivel

    // construtor
    public NoAVL(Restaurante elemento) {
        this.elemento = elemento; // salva restaurante
        this.esq = null; // inicia esquerda
        this.dir = null; // inicia direita
        this.nivel = 1; // no novo tem nivel 1
    }

    // retorna nivel do no
    public static int getNivel(NoAVL no) {
        int resp = 0; // nivel de nulo

        if (no != null) { // se no existe
            resp = no.nivel; // pega nivel
        }

        return resp; // retorna nivel
    }

    // atualiza nivel
    public void setNivel() {
        int nivelEsq = getNivel(this.esq); // nivel esquerda
        int nivelDir = getNivel(this.dir); // nivel direita

        if (nivelEsq > nivelDir) { // se esquerda maior
            this.nivel = nivelEsq + 1; // usa esquerda
        } else {
            this.nivel = nivelDir + 1; // usa direita
        }
    }

    // retorna fator
    public int getFator() {
        return getNivel(this.dir) - getNivel(this.esq); // direita - esquerda
    }
}

// arvore AVL do segundo nivel
class ArvoreAVL {
    private NoAVL raiz; // raiz da AVL

    // construtor
    public ArvoreAVL() {
        this.raiz = null; // inicia vazia
    }

    // insere restaurante
    public void inserir(Restaurante restaurante) {
        this.raiz = inserir(restaurante, this.raiz); // chama recursivo
    }

    // insere recursivo
    private NoAVL inserir(Restaurante restaurante, NoAVL no) {
        if (no == null) { // se chegou em posicao vazia
            no = new NoAVL(restaurante); // cria no
        } else {
            int cmp = restaurante.getNome().compareTo(no.elemento.getNome()); // compara nomes

            if (cmp < 0) { // se menor
                no.esq = inserir(restaurante, no.esq); // insere esquerda
            } else if (cmp > 0) { // se maior
                no.dir = inserir(restaurante, no.dir); // insere direita
            }
        }

        no = balancear(no); // balanceia

        return no; // retorna no
    }

    // balanceia no
    private NoAVL balancear(NoAVL no) {
        if (no != null) { // se no existe
            no.setNivel(); // atualiza nivel

            int fator = no.getFator(); // pega fator

            if (fator == 2) { // se pesado para direita
                if (no.dir.getFator() < 0) { // caso direita-esquerda
                    no.dir = rotacionarDir(no.dir); // rotaciona filho
                }

                no = rotacionarEsq(no); // rotaciona para esquerda

            } else if (fator == -2) { // se pesado para esquerda
                if (no.esq.getFator() > 0) { // caso esquerda-direita
                    no.esq = rotacionarEsq(no.esq); // rotaciona filho
                }

                no = rotacionarDir(no); // rotaciona para direita
            }
        }

        return no; // retorna no
    }

    // rotacao para esquerda
    private NoAVL rotacionarEsq(NoAVL no) {
        NoAVL noDir = no.dir; // guarda filho direito
        NoAVL noDirEsq = noDir.esq; // guarda subarvore

        noDir.esq = no; // filho direito vira pai
        no.dir = noDirEsq; // subarvore vai para direita do antigo pai

        no.setNivel(); // atualiza antigo pai
        noDir.setNivel(); // atualiza novo pai

        return noDir; // retorna nova raiz
    }

    // rotacao para direita
    private NoAVL rotacionarDir(NoAVL no) {
        NoAVL noEsq = no.esq; // guarda filho esquerdo
        NoAVL noEsqDir = noEsq.dir; // guarda subarvore

        noEsq.dir = no; // filho esquerdo vira pai
        no.esq = noEsqDir; // subarvore vai para esquerda do antigo pai

        no.setNivel(); // atualiza antigo pai
        noEsq.setNivel(); // atualiza novo pai

        return noEsq; // retorna nova raiz
    }

    // pesquisa imprimindo caminho da AVL
    public Restaurante pesquisar(String nome, long[] comparacoes) {
        System.out.print(" raiz"); // imprime entrada na AVL
        return pesquisar(nome, this.raiz, comparacoes); // chama recursivo
    }

    // pesquisa recursiva
    private Restaurante pesquisar(String nome, NoAVL no, long[] comparacoes) {
        Restaurante resp = null; // resposta inicial

        if (no != null) { // se no existe
            int cmp = nome.compareTo(no.elemento.getNome()); // compara nomes
            comparacoes[0]++; // conta comparacao

            if (cmp == 0) { // se encontrou
                resp = no.elemento; // guarda restaurante
            } else if (cmp < 0) { // se vai esquerda
                System.out.print(" esq"); // imprime caminho
                resp = pesquisar(nome, no.esq, comparacoes); // pesquisa esquerda
            } else {
                System.out.print(" dir"); // imprime caminho
                resp = pesquisar(nome, no.dir, comparacoes); // pesquisa direita
            }
        }

        return resp; // retorna restaurante ou null
    }
}

// no da arvore do primeiro nivel
class NoPrimeiro {
    public int chave; // guarda chave capacidade mod 15
    public ArvoreAVL segunda; // guarda AVL do segundo nivel
    public NoPrimeiro esq; // aponta esquerda
    public NoPrimeiro dir; // aponta direita

    // construtor
    public NoPrimeiro(int chave) {
        this.chave = chave; // salva chave
        this.segunda = new ArvoreAVL(); // cria AVL interna
        this.esq = null; // inicia esquerda
        this.dir = null; // inicia direita
    }
}

// arvore de arvores
class ArvoreDeArvores {
    private NoPrimeiro raiz; // raiz do primeiro nivel
    private long comparacoes; // comparacoes das pesquisas

    // construtor
    public ArvoreDeArvores() {
        this.raiz = null; // inicia vazia, sem chaves pre-inseridas
        this.comparacoes = 0; // zera comparacoes
    }

    // retorna comparacoes
    public long getComparacoes() {
        return this.comparacoes; // devolve comparacoes
    }

    // insere restaurante na estrutura
    public void inserir(Restaurante restaurante) {
        int chave = restaurante.getCapacidade() % 15; // calcula capacidade mod 15
        this.raiz = inserir(restaurante, chave, this.raiz); // insere na arvore do primeiro nivel
    }

    // insere no primeiro nivel pela chave e no segundo nivel pelo nome
    private NoPrimeiro inserir(Restaurante restaurante, int chave, NoPrimeiro no) {
        if (no == null) { // se ainda nao existe no com essa chave no caminho
            no = new NoPrimeiro(chave); // cria no do primeiro nivel
            no.segunda.inserir(restaurante); // insere restaurante na AVL interna
        } else if (chave < no.chave) { // se chave for menor
            no.esq = inserir(restaurante, chave, no.esq); // vai para esquerda
        } else if (chave > no.chave) { // se chave for maior
            no.dir = inserir(restaurante, chave, no.dir); // vai para direita
        } else { // se encontrou a chave
            no.segunda.inserir(restaurante); // insere na AVL interna da chave
        }

        return no; // retorna no atualizado
    }

    // pesquisa por nome
    public void pesquisar(String nome) {
        System.out.print("RAIZ"); // imprime inicio da arvore do primeiro nivel

        long[] comps = new long[1]; // vetor para contar comparacoes
        Restaurante resp = pesquisar(nome, this.raiz, comps); // pesquisa em pre-ordem

        this.comparacoes += comps[0]; // acumula comparacoes

        if (resp != null) { // se encontrou
            System.out.println(" SIM " + resp.formatar()); // imprime SIM e restaurante formatado
        } else {
            System.out.println(" NAO"); // imprime NAO
        }
    }

    // pesquisa em pre-ordem: no atual, esquerda, direita
    private Restaurante pesquisar(String nome, NoPrimeiro no, long[] comps) {
        Restaurante resp = null; // resposta inicial

        if (no != null) { // se no existe
            resp = no.segunda.pesquisar(nome, comps); // pesquisa na AVL interna do no atual

            if (resp == null) { // se nao encontrou no no atual
                System.out.print(" ESQ"); // imprime ida para esquerda do primeiro nivel
                resp = pesquisar(nome, no.esq, comps); // pesquisa esquerda
            }

            if (resp == null) { // se ainda nao encontrou
                System.out.print(" DIR"); // imprime ida para direita do primeiro nivel
                resp = pesquisar(nome, no.dir, comps); // pesquisa direita
            }
        }

        return resp; // retorna restaurante ou null
    }
}

// classe principal
public class q6hibridaArvoreArvore {
    public static final String MATRICULA = "898128"; // matricula

    // verifica FIM
    public static boolean isFim(String s) {
        return s.compareTo("FIM") == 0; // retorna true se for FIM
    }

    // cria arquivo de log
    public static void criarLog(long comparacoes) {
        try {
            PrintWriter pw = new PrintWriter(MATRICULA + "_hibrida_arvore_arvore.txt"); // cria log
            pw.println(MATRICULA + "\t" + comparacoes + "\t0.0"); // escreve log
            pw.close(); // fecha log
        } catch (FileNotFoundException e) { // se der erro
            System.out.println("Erro ao criar arquivo de log."); // imprime erro
        }
    }

    // metodo principal
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); // cria Scanner
        ColecaoRestaurantes colecao = ColecaoRestaurantes.lerCsv(); // le csv
        ArvoreDeArvores arvore = new ArvoreDeArvores(); // cria estrutura

        int id; // guarda id
        String nome; // guarda nome

        id = sc.nextInt(); // le primeiro id

        while (id != -1) { // enquanto nao for -1
            Restaurante restaurante = colecao.buscarPorId(id); // busca restaurante

            if (restaurante != null) { // se encontrou
                arvore.inserir(restaurante); // insere na estrutura
            }

            id = sc.nextInt(); // le proximo id
        }

        sc.nextLine(); // consome quebra depois do -1

        nome = sc.nextLine(); // le primeiro nome

        while (!isFim(nome)) { // enquanto nao for FIM
            arvore.pesquisar(nome); // pesquisa
            nome = sc.nextLine(); // le proximo nome
        }

        criarLog(arvore.getComparacoes()); // cria log

        sc.close(); 
    }
}
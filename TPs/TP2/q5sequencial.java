import java.util.Scanner; 
import java.io.File;
import java.io.FileNotFoundException; 
import java.io.PrintWriter; 

// classe Data
class Data {
    private int ano; // guarda o ano
    private int mes; // guarda o mes
    private int dia; // guarda o dia

    // construtor da classe Data
    public Data(int ano, int mes, int dia) {
        this.ano = ano; // salva o ano
        this.mes = mes; // salva o mes
        this.dia = dia; // salva o dia
    }

    // retorna o ano
    public int getAno() {
        return this.ano;
    }

    // retorna o mes
    public int getMes() {
        return this.mes;
    }

    // retorna o dia
    public int getDia() {
        return this.dia;
    }

    // recebe uma string no formato YYYY-MM-DD e cria um objeto Data
    public static Data parseData(String s) {
        Scanner sc = new Scanner(s); 
        sc.useDelimiter("-"); // define "-" como separador

        int ano = sc.nextInt(); // le o ano
        int mes = sc.nextInt(); // le o mes
        int dia = sc.nextInt(); // le o dia

        sc.close();

        return new Data(ano, mes, dia); // retorna o objeto Data criado
    }

    // formata a data como DD/MM/YYYY
    public String formatar() {
        String diaString = ""; // string auxiliar para o dia
        String mesString = ""; // string auxiliar para o mes

        if (this.dia < 10) { // se o dia tiver um digito
            diaString = "0" + this.dia; // coloca zero na frente
        } else {
            diaString = "" + this.dia; // escreve normal
        }

        if (this.mes < 10) { // se o mes tiver um digito
            mesString = "0" + this.mes; // coloca zero na frente
        } else {
            mesString = "" + this.mes; // escreve normal
        }

        return diaString + "/" + mesString + "/" + this.ano; // monta a data final
    }
}

// classe Hora
class Hora {
    private int hora; // guarda a hora
    private int minuto; // guarda o minuto

    // construtor da classe Hora
    public Hora(int hora, int minuto) {
        this.hora = hora; // salva a hora
        this.minuto = minuto; // salva o minuto
    }

    // retorna a hora
    public int getHora() {
        return this.hora;
    }

    // retorna o minuto
    public int getMinuto() {
        return this.minuto;
    }

    // recebe uma string no formato HH:mm e cria um objeto Hora
    public static Hora parseHora(String s) {
        Scanner sc = new Scanner(s); 
        sc.useDelimiter(":"); // define ":" como separador

        int hora = sc.nextInt(); // le a hora
        int minuto = sc.nextInt(); // le o minuto

        sc.close(); 

        return new Hora(hora, minuto); // retorna o objeto Hora criado
    }

    // formata a hora como HH:mm
    public String formatar() {
        String horaString = ""; // string auxiliar da hora
        String minutoString = ""; // string auxiliar do minuto

        if (this.hora < 10) { // se a hora tiver um digito
            horaString = "0" + this.hora; // coloca zero na frente
        } else {
            horaString = "" + this.hora; // escreve normal
        }

        if (this.minuto < 10) { // se o minuto tiver um digito
            minutoString = "0" + this.minuto; // coloca zero na frente
        } else {
            minutoString = "" + this.minuto; // escreve normal
        }

        return horaString + ":" + minutoString; // monta a hora final
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
    private int faixaPreco; // guarda a faixa de preco em numero
    private Hora horarioAbertura; // guarda a hora de abertura
    private Hora horarioFechamento; // guarda a hora de fechamento
    private Data dataAbertura; // guarda a data de abertura
    private boolean aberto; // guarda se esta aberto

    // construtor da classe Restaurante
    public Restaurante(int id, String nome, String cidade, int capacidade, double avaliacao,
                       String[] tiposCozinha, int faixaPreco, Hora horarioAbertura,
                       Hora horarioFechamento, Data dataAbertura, boolean aberto) {
        this.id = id; // salva o id
        this.nome = nome; // salva o nome
        this.cidade = cidade; // salva a cidade
        this.capacidade = capacidade; // salva a capacidade
        this.avaliacao = avaliacao; // salva a avaliacao
        this.tiposCozinha = tiposCozinha; // salva os tipos de cozinha
        this.faixaPreco = faixaPreco; // salva a faixa de preco
        this.horarioAbertura = horarioAbertura; // salva a hora de abertura
        this.horarioFechamento = horarioFechamento; // salva a hora de fechamento
        this.dataAbertura = dataAbertura; // salva a data
        this.aberto = aberto; // salva aberto
    }

    // getters
    public int getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public String getCidade() {
        return this.cidade;
    }

    public int getCapacidade() {
        return this.capacidade;
    }

    public double getAvaliacao() {
        return this.avaliacao;
    }

    public String[] getTiposCozinha() {
        return this.tiposCozinha;
    }

    public int getFaixaPreco() {
        return this.faixaPreco;
    }

    public Hora getHorarioAbertura() {
        return this.horarioAbertura;
    }

    public Hora getHorarioFechamento() {
        return this.horarioFechamento;
    }

    public Data getDataAbertura() {
        return this.dataAbertura;
    }

    public boolean getAberto() {
        return this.aberto;
    }

    // converte "$", "$$", "$$$", "$$$$" em 1, 2, 3, 4
    public static int parseFaixaPreco(String s) {
        int resp = 0; // resposta

        if (s.compareTo("$") == 0) {
            resp = 1;
        } else if (s.compareTo("$$") == 0) {
            resp = 2;
        } else if (s.compareTo("$$$") == 0) {
            resp = 3;
        } else if (s.compareTo("$$$$") == 0) {
            resp = 4;
        }

        return resp; // retorna a faixa convertida
    }

    // converte 1, 2, 3, 4 em "$", "$$", "$$$", "$$$$"
    public static String formatarFaixaPreco(int faixaPreco) {
        String resp = ""; // string de resposta

        for (int i = 0; i < faixaPreco; i++) {
            resp += "$"; // adiciona um $ por vez
        }

        return resp; // retorna a string final
    }

    // conta quantos tipos de cozinha existem na string
    public static int contarTiposCozinha(String s) {
        Scanner sc = new Scanner(s); 
        sc.useDelimiter(";"); // define ";" como separador

        int count = 0; // contador

        while (sc.hasNext()) {
            sc.next(); // le um tipo
            count++; // aumenta a contagem
        }

        sc.close(); 

        return count; // retorna a quantidade
    }

    // transforma a string dos tipos em vetor
    public static String[] parseTiposCozinha(String s) {
        int n = contarTiposCozinha(s); // descobre quantos tipos existem
        String[] resp = new String[n]; // cria o vetor

        Scanner sc = new Scanner(s); // cria Scanner para ler a string
        sc.useDelimiter(";"); // define ";" como separador

        for (int i = 0; i < n; i++) {
            resp[i] = sc.next(); // guarda cada tipo no vetor
        }

        sc.close(); 

        return resp; // retorna o vetor
    }

    // recebe uma linha do csv e cria um restaurante
    public static Restaurante parseRestaurante(String s) {
        Scanner sc = new Scanner(s); 
        sc.useDelimiter(","); // define "," como separador

        int id = sc.nextInt(); // le o id
        String nome = sc.next(); // le o nome
        String cidade = sc.next(); // le a cidade
        int capacidade = sc.nextInt(); // le a capacidade
        double avaliacao = sc.nextDouble(); // le a avaliacao
        String tipos = sc.next(); // le os tipos de cozinha
        String faixa = sc.next(); // le a faixa de preco
        String horario = sc.next(); // le o horario
        String data = sc.next(); // le a data
        boolean aberto = sc.nextBoolean(); // le aberto

        sc.close(); 

        String[] tiposCozinha = parseTiposCozinha(tipos); // converte tipos em vetor
        int faixaPreco = parseFaixaPreco(faixa); // converte faixa em inteiro

        Scanner scHorario = new Scanner(horario); // cria Scanner para separar abertura e fechamento
        scHorario.useDelimiter("-"); // define "-" como separador

        String abertura = scHorario.next(); // le a parte da abertura
        String fechamento = scHorario.next(); // le a parte do fechamento

        scHorario.close(); // fecha o Scanner do horario

        Hora horarioAbertura = Hora.parseHora(abertura); // cria Hora de abertura
        Hora horarioFechamento = Hora.parseHora(fechamento); // cria Hora de fechamento
        Data dataAbertura = Data.parseData(data); // cria Data

        return new Restaurante(id, nome, cidade, capacidade, avaliacao, tiposCozinha,
                               faixaPreco, horarioAbertura, horarioFechamento,
                               dataAbertura, aberto); // retorna o restaurante criado
    }

    // formata os tipos de cozinha em [a,b,c]
    public String formatarTiposCozinha() {
        String resp = "["; // começa com [

        for (int i = 0; i < this.tiposCozinha.length; i++) {
            resp += this.tiposCozinha[i]; // adiciona o tipo atual

            if (i < this.tiposCozinha.length - 1) {
                resp += ","; // se nao for o ultimo, adiciona virgula
            }
        }

        resp += "]"; // fecha com ]

        return resp; // retorna a string
    }

    // formata o restaurante completo
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

// classe da colecao de restaurantes
class ColecaoRestaurantes {
    private int tamanho; // guarda o tamanho da colecao
    private Restaurante[] restaurantes; // guarda o vetor de restaurantes

    // construtor padrao
    public ColecaoRestaurantes() {
        this.tamanho = 0; // começa com 0
        this.restaurantes = new Restaurante[0]; // começa vazio
    }

    // getters
    public int getTamanho() {
        return this.tamanho;
    }

    public Restaurante[] getRestaurantes() {
        return this.restaurantes;
    }

    // lê o csv de um caminho
    public void lerCsv(String path) {
        try {
            File arquivo = new File(path); // representa o arquivo
            Scanner sc = new Scanner(arquivo); // cria Scanner para ler

            int count = 0; // conta quantas linhas de dados existem

            if (sc.hasNextLine()) {
                sc.nextLine(); // pula o cabecalho
            }

            while (sc.hasNextLine()) {
                sc.nextLine(); // lê uma linha
                count++; // aumenta a contagem
            }

            sc.close(); // fecha o Scanner

            this.tamanho = count; // guarda a quantidade de restaurantes
            this.restaurantes = new Restaurante[this.tamanho]; // cria o vetor do tamanho certo

            sc = new Scanner(arquivo); // reabre o Scanner

            if (sc.hasNextLine()) {
                sc.nextLine(); // pula o cabecalho de novo
            }

            for (int i = 0; i < this.tamanho; i++) {
                String linha = sc.nextLine(); // lê a linha atual
                this.restaurantes[i] = Restaurante.parseRestaurante(linha); // transforma em restaurante
            }

            sc.close(); // fecha o Scanner

        } catch (FileNotFoundException e) {
            System.out.println("Erro ao abrir o arquivo."); // mostra erro se nao encontrar o arquivo
        }
    }

    // lê o csv padrao
    public static ColecaoRestaurantes lerCsv() {
        ColecaoRestaurantes colecao = new ColecaoRestaurantes(); // cria a colecao
        colecao.lerCsv("/tmp/restaurantes.csv"); // lê o arquivo padrao
        return colecao; // retorna a colecao pronta
    }

    // busca um restaurante pelo id
    public Restaurante buscarPorId(int id) {
        Restaurante resp = null; // começa supondo que nao encontrou

        for (int i = 0; i < this.tamanho; i++) {
            if (this.restaurantes[i].getId() == id) {
                resp = this.restaurantes[i]; // guarda o restaurante encontrado
                i = this.tamanho; // força saida do laço
            }
        }

        return resp; // retorna o restaurante ou null
    }
}

// classe principal 
public class q5sequencial {
    public static final String MATRICULA = "898128"; // matricula usada no log

    // faz pesquisa sequencial por nome
    public static boolean pesquisaSequencial(Restaurante[] array, int n, String nome, long[] comparacoes) {
        boolean resp = false; // começa supondo que nao encontrou

        for (int i = 0; i < n; i++) { // percorre o vetor
            comparacoes[0]++; // conta uma comparacao

            if (array[i].getNome().compareTo(nome) == 0) { // se encontrou o nome
                resp = true; // muda a resposta para true
                i = n; // força a saida do laço
            }
        }

        return resp; // retorna true ou false
    }

    // cria o arquivo de log
    public static void criarLog(long comparacoes, double tempo) {
        try {
            PrintWriter pw = new PrintWriter(MATRICULA + "_sequencial.txt"); // cria o arquivo

            pw.println(MATRICULA + "\t" + comparacoes + "\t" + tempo); // escreve matricula, comparacoes e tempo

            pw.close(); // fecha o arquivo
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao criar arquivo de log."); // mostra erro se nao conseguir criar
        }
    }

    // metodo principal
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); // Scanner para ler a entrada
        ColecaoRestaurantes colecao = ColecaoRestaurantes.lerCsv(); // lê todo o csv

        Restaurante[] basePesquisa = new Restaurante[1000]; // vetor que vai guardar a base da pesquisa
        int quantidade = 0; // quantidade de restaurantes guardados
        int id; // guarda o id lido

        long[] comparacoes = new long[1]; // contador de comparacoes
        long inicio; // instante inicial
        long fim; // instante final
        double tempo; // tempo total

        // primeira parte da entrada: ids
        id = sc.nextInt(); // lê o primeiro id

        while (id != -1) { // continua até ler -1
            Restaurante restaurante = colecao.buscarPorId(id); // busca o restaurante pelo id

            if (restaurante != null) { // se encontrou
                basePesquisa[quantidade] = restaurante; // guarda na base
                quantidade++; // aumenta a quantidade
            }

            id = sc.nextInt(); // lê o próximo id
        }

        sc.nextLine(); // consome a quebra de linha depois do -1

        inicio = System.currentTimeMillis(); // marca o tempo inicial

        // segunda parte da entrada: nomes a pesquisar até FIM
        String nome = sc.nextLine(); // lê o primeiro nome

        while (nome.compareTo("FIM") != 0) { // continua enquanto nao for FIM
            boolean achou = pesquisaSequencial(basePesquisa, quantidade, nome, comparacoes); // pesquisa o nome

            if (achou) { // se encontrou
                System.out.println("SIM"); // imprime SIM
            } else {
                System.out.println("NAO"); // imprime NAO
            }

            nome = sc.nextLine(); // lê o próximo nome
        }

        fim = System.currentTimeMillis(); // marca o tempo final

        tempo = (fim - inicio) / 1000.0; // calcula o tempo em segundos

        criarLog(comparacoes[0], tempo); // cria o log

        sc.close(); 
    }
}
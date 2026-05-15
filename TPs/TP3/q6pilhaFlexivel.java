import java.util.Scanner; 
import java.io.File; 
import java.io.FileNotFoundException; 

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
        return this.ano; // devolve o ano
    }

    // retorna o mes
    public int getMes() {
        return this.mes; // devolve o mes
    }

    // retorna o dia
    public int getDia() {
        return this.dia; // devolve o dia
    }

    // recebe uma string no formato YYYY-MM-DD e cria uma Data
    public static Data parseData(String s) {
        Scanner sc = new Scanner(s); // cria Scanner para a string
        sc.useDelimiter("-"); // define "-" como separador

        int ano = sc.nextInt(); // le o ano
        int mes = sc.nextInt(); // le o mes
        int dia = sc.nextInt(); // le o dia

        sc.close(); // fecha o Scanner

        return new Data(ano, mes, dia); // retorna a data criada
    }

    // formata a data no formato DD/MM/YYYY
    public String formatar() {
        String diaString = ""; // guarda o dia formatado
        String mesString = ""; // guarda o mes formatado

        if (this.dia < 10) { // se o dia tiver um digito
            diaString = "0" + this.dia; // coloca zero na frente
        } else {
            diaString = "" + this.dia; // usa o dia normal
        }

        if (this.mes < 10) { // se o mes tiver um digito
            mesString = "0" + this.mes; // coloca zero na frente
        } else {
            mesString = "" + this.mes; // usa o mes normal
        }

        return diaString + "/" + mesString + "/" + this.ano; // retorna data formatada
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
        return this.hora; // devolve a hora
    }

    // retorna o minuto
    public int getMinuto() {
        return this.minuto; // devolve o minuto
    }

    // recebe uma string no formato HH:mm e cria uma Hora
    public static Hora parseHora(String s) {
        Scanner sc = new Scanner(s); // cria Scanner para a string
        sc.useDelimiter(":"); // define ":" como separador

        int hora = sc.nextInt(); // le a hora
        int minuto = sc.nextInt(); // le o minuto

        sc.close(); // fecha o Scanner

        return new Hora(hora, minuto); // retorna a hora criada
    }

    // formata a hora no formato HH:mm
    public String formatar() {
        String horaString = ""; // guarda a hora formatada
        String minutoString = ""; // guarda o minuto formatado

        if (this.hora < 10) { // se a hora tiver um digito
            horaString = "0" + this.hora; // coloca zero na frente
        } else {
            horaString = "" + this.hora; // usa a hora normal
        }

        if (this.minuto < 10) { // se o minuto tiver um digito
            minutoString = "0" + this.minuto; // coloca zero na frente
        } else {
            minutoString = "" + this.minuto; // usa o minuto normal
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
    private int faixaPreco; // guarda a faixa de preco como inteiro
    private Hora horarioAbertura; // guarda o horario de abertura
    private Hora horarioFechamento; // guarda o horario de fechamento
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
        this.horarioAbertura = horarioAbertura; // salva horario de abertura
        this.horarioFechamento = horarioFechamento; // salva horario de fechamento
        this.dataAbertura = dataAbertura; // salva data de abertura
        this.aberto = aberto; // salva aberto
    }

    // retorna o id
    public int getId() {
        return this.id; // devolve o id
    }

    // retorna o nome
    public String getNome() {
        return this.nome; // devolve o nome
    }

    // retorna a cidade
    public String getCidade() {
        return this.cidade; // devolve a cidade
    }

    // retorna a capacidade
    public int getCapacidade() {
        return this.capacidade; // devolve a capacidade
    }

    // retorna a avaliacao
    public double getAvaliacao() {
        return this.avaliacao; // devolve a avaliacao
    }

    // retorna os tipos de cozinha
    public String[] getTiposCozinha() {
        return this.tiposCozinha; // devolve os tipos de cozinha
    }

    // retorna a faixa de preco
    public int getFaixaPreco() {
        return this.faixaPreco; // devolve a faixa de preco
    }

    // retorna o horario de abertura
    public Hora getHorarioAbertura() {
        return this.horarioAbertura; // devolve horario de abertura
    }

    // retorna o horario de fechamento
    public Hora getHorarioFechamento() {
        return this.horarioFechamento; // devolve horario de fechamento
    }

    // retorna a data de abertura
    public Data getDataAbertura() {
        return this.dataAbertura; // devolve data de abertura
    }

    // retorna se esta aberto
    public boolean getAberto() {
        return this.aberto; // devolve aberto
    }

    // converte "$", "$$", "$$$", "$$$$" em 1, 2, 3, 4
    public static int parseFaixaPreco(String s) {
        int resp = 0; // guarda a resposta

        if (s.compareTo("$") == 0) { // se for "$"
            resp = 1; // guarda 1
        } else if (s.compareTo("$$") == 0) { // se for "$$"
            resp = 2; // guarda 2
        } else if (s.compareTo("$$$") == 0) { // se for "$$$"
            resp = 3; // guarda 3
        } else if (s.compareTo("$$$$") == 0) { // se for "$$$$"
            resp = 4; // guarda 4
        }

        return resp; // retorna a faixa convertida
    }

    // converte 1, 2, 3, 4 em "$", "$$", "$$$", "$$$$"
    public static String formatarFaixaPreco(int faixaPreco) {
        String resp = ""; // cria string vazia

        for (int i = 0; i < faixaPreco; i++) { // repete conforme a faixa
            resp += "$"; // adiciona $
        }

        return resp; // retorna a faixa formatada
    }

    // conta os tipos de cozinha separados por ;
    public static int contarTiposCozinha(String s) {
        Scanner sc = new Scanner(s); // cria Scanner para a string
        sc.useDelimiter(";"); // define ";" como separador

        int count = 0; // contador

        while (sc.hasNext()) { // enquanto houver tipo
            sc.next(); // le o tipo
            count++; // aumenta contador
        }

        sc.close(); // fecha Scanner

        return count; // retorna quantidade
    }

    // transforma tipos de cozinha em vetor
    public static String[] parseTiposCozinha(String s) {
        int n = contarTiposCozinha(s); // conta os tipos
        String[] resp = new String[n]; // cria vetor

        Scanner sc = new Scanner(s); // cria Scanner para a string
        sc.useDelimiter(";"); // define ";" como separador

        for (int i = 0; i < n; i++) { // percorre os tipos
            resp[i] = sc.next(); // guarda tipo
        }

        sc.close(); // fecha Scanner

        return resp; // retorna vetor
    }

    // transforma uma linha do csv em Restaurante
    public static Restaurante parseRestaurante(String s) {
        Scanner sc = new Scanner(s); // cria Scanner para linha
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

        sc.close(); // fecha Scanner

        String[] tiposCozinha = parseTiposCozinha(tipos); // converte tipos
        int faixaPreco = parseFaixaPreco(faixa); // converte faixa

        Scanner scHorario = new Scanner(horario); // cria Scanner para horario
        scHorario.useDelimiter("-"); // define "-" como separador

        String abertura = scHorario.next(); // le abertura
        String fechamento = scHorario.next(); // le fechamento

        scHorario.close(); // fecha Scanner do horario

        Hora horarioAbertura = Hora.parseHora(abertura); // cria hora abertura
        Hora horarioFechamento = Hora.parseHora(fechamento); // cria hora fechamento
        Data dataAbertura = Data.parseData(data); // cria data abertura

        return new Restaurante(id, nome, cidade, capacidade, avaliacao,
                tiposCozinha, faixaPreco, horarioAbertura,
                horarioFechamento, dataAbertura, aberto); // retorna restaurante
    }

    // formata os tipos no formato [a,b]
    public String formatarTiposCozinha() {
        String resp = "["; // abre colchete

        for (int i = 0; i < this.tiposCozinha.length; i++) { // percorre os tipos
            resp += this.tiposCozinha[i]; // adiciona tipo

            if (i < this.tiposCozinha.length - 1) { // se nao for ultimo
                resp += ","; // coloca virgula
            }
        }

        resp += "]"; // fecha colchete

        return resp; // retorna tipos formatados
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

// colecao de restaurantes
class ColecaoRestaurantes {
    private int tamanho; // guarda tamanho
    private Restaurante[] restaurantes; // guarda restaurantes

    // construtor
    public ColecaoRestaurantes() {
        this.tamanho = 0; // inicia tamanho
        this.restaurantes = new Restaurante[0]; // inicia vetor vazio
    }

    // le csv pelo caminho
    public void lerCsv(String path) {
        try {
            File arquivo = new File(path); // cria File
            Scanner sc = new Scanner(arquivo); // abre Scanner do arquivo

            int count = 0; // contador de linhas

            if (sc.hasNextLine()) { // se tiver cabecalho
                sc.nextLine(); // pula cabecalho
            }

            while (sc.hasNextLine()) { // enquanto houver linha
                sc.nextLine(); // le linha
                count++; // aumenta contador
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
        } catch (FileNotFoundException e) { // se arquivo nao for encontrado
            System.out.println("Erro ao abrir o arquivo."); // imprime erro
        }
    }

    // le csv padrao
    public static ColecaoRestaurantes lerCsv() {
        ColecaoRestaurantes colecao = new ColecaoRestaurantes(); // cria colecao
        colecao.lerCsv("/tmp/restaurantes.csv"); // le arquivo padrao
        return colecao; // retorna colecao
    }

    // busca restaurante por id
    public Restaurante buscarPorId(int id) {
        Restaurante resp = null; // resposta inicial

        for (int i = 0; i < this.tamanho; i++) { // percorre restaurantes
            if (this.restaurantes[i].getId() == id) { // se encontrou
                resp = this.restaurantes[i]; // salva resposta
                i = this.tamanho; // encerra laco
            }
        }

        return resp; // retorna restaurante
    }
}

// celula da pilha
class Celula {
    public Restaurante elemento; // guarda restaurante
    public Celula prox; // aponta para proxima celula

    // construtor da celula
    public Celula(Restaurante elemento) {
        this.elemento = elemento; // salva restaurante
        this.prox = null; // inicia sem proxima
    }
}

// pilha flexivel
class Pilha {
    private Celula topo; // aponta para o topo da pilha
    private int tamanho; // guarda tamanho da pilha

    // construtor da pilha
    public Pilha() {
        this.topo = null; // pilha comeca vazia
        this.tamanho = 0; // tamanho comeca zero
    }

    // empilha no topo
    public void inserir(Restaurante restaurante) {
        Celula tmp = new Celula(restaurante); // cria nova celula

        tmp.prox = this.topo; // nova celula aponta para antigo topo
        this.topo = tmp; // topo vira nova celula
        this.tamanho++; // aumenta tamanho
    }

    // remove do topo
    public Restaurante remover() {
        Restaurante resp = null; // guarda removido

        if (this.topo != null) { // se a pilha nao estiver vazia
            Celula tmp = this.topo; // guarda topo atual
            resp = tmp.elemento; // guarda elemento removido
            this.topo = this.topo.prox; // topo passa para o proximo
            tmp.prox = null; // desconecta celula
            this.tamanho--; // diminui tamanho
        }

        return resp; // retorna removido
    }

    // mostra pilha do topo para a base
    public void mostrar() {
        Celula atual = this.topo; // comeca no topo
        int i = 0; // indice de impressao

        while (atual != null) { // enquanto houver celula
            System.out.println("[" + i + "] " + atual.elemento.formatar()); // imprime posicao e restaurante
            atual = atual.prox; // avanca para proxima celula
            i++; // aumenta indice
        }
    }
}

// classe principal
public class q6pilhaFlexivel {
    // metodo principal
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); // cria Scanner da entrada
        ColecaoRestaurantes colecao = ColecaoRestaurantes.lerCsv(); // le csv

        Pilha pilha = new Pilha(); // cria pilha
        int id; // guarda id
        int quantidadeOperacoes; // guarda quantidade de operacoes
        String comando; // guarda comando
        Restaurante restaurante; // guarda restaurante buscado
        Restaurante removido; // guarda restaurante removido

        id = sc.nextInt(); // le primeiro id

        while (id != -1) { // enquanto nao for -1
            restaurante = colecao.buscarPorId(id); // busca restaurante

            if (restaurante != null) { // se encontrou
                pilha.inserir(restaurante); // empilha
            }

            id = sc.nextInt(); // le proximo id
        }

        quantidadeOperacoes = sc.nextInt(); // le quantidade de operacoes

        for (int i = 0; i < quantidadeOperacoes; i++) { // percorre operacoes
            comando = sc.next(); // le comando

            if (comando.compareTo("I") == 0) { // se for inserir
                id = sc.nextInt(); // le id
                restaurante = colecao.buscarPorId(id); // busca restaurante

                if (restaurante != null) { // se encontrou
                    pilha.inserir(restaurante); // empilha
                }
            } else if (comando.compareTo("R") == 0) { // se for remover
                removido = pilha.remover(); // desempilha

                if (removido != null) { // se removeu
                    System.out.println("(R)" + removido.getNome()); // imprime removido sem espaco
                }
            }
        }

        pilha.mostrar(); // imprime pilha final

        sc.close(); 
    }
}
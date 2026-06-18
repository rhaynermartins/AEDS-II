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

    // recebe uma string YYYY-MM-DD e cria uma Data
    public static Data parseData(String s) {
        Scanner sc = new Scanner(s); // cria scanner da string
        sc.useDelimiter("-"); // separa por -

        int ano = sc.nextInt(); // le ano
        int mes = sc.nextInt(); // le mes
        int dia = sc.nextInt(); // le dia

        sc.close(); 

        return new Data(ano, mes, dia); // retorna data
    }

    // formata a data em DD/MM/YYYY
    public String formatar() {
        String diaString; // guarda dia formatado
        String mesString; // guarda mes formatado

        if (this.dia < 10) { // se dia tiver um digito
            diaString = "0" + this.dia; // coloca zero na frente
        } else {
            diaString = "" + this.dia; // usa normal
        }

        if (this.mes < 10) { // se mes tiver um digito
            mesString = "0" + this.mes; // coloca zero na frente
        } else {
            mesString = "" + this.mes; // usa normal
        }

        return diaString + "/" + mesString + "/" + this.ano; // retorna formatado
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

    // recebe uma string HH:mm e cria Hora
    public static Hora parseHora(String s) {
        Scanner sc = new Scanner(s); // cria scanner da string
        sc.useDelimiter(":"); // separa por :

        int hora = sc.nextInt(); // le hora
        int minuto = sc.nextInt(); // le minuto

        sc.close(); 

        return new Hora(hora, minuto); // retorna hora
    }

    // formata em HH:mm
    public String formatar() {
        String horaString; // guarda hora formatada
        String minutoString; // guarda minuto formatado

        if (this.hora < 10) { // se hora tiver um digito
            horaString = "0" + this.hora; // coloca zero
        } else {
            horaString = "" + this.hora; // usa normal
        }

        if (this.minuto < 10) { // se minuto tiver um digito
            minutoString = "0" + this.minuto; // coloca zero
        } else {
            minutoString = "" + this.minuto; // usa normal
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
    private Hora horarioAbertura; // guarda horario abertura
    private Hora horarioFechamento; // guarda horario fechamento
    private Data dataAbertura; // guarda data abertura
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

    // converte faixa preco para inteiro
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

    // converte faixa preco para string
    public static String formatarFaixaPreco(int faixaPreco) {
        String resp = ""; // resposta

        for (int i = 0; i < faixaPreco; i++) { // repete pela faixa
            resp += "$"; // adiciona $
        }

        return resp; // retorna texto
    }

    // conta tipos de cozinha
    public static int contarTiposCozinha(String s) {
        Scanner sc = new Scanner(s); // cria scanner
        sc.useDelimiter(";"); // separa por ;

        int count = 0; // contador

        while (sc.hasNext()) { // enquanto houver tipo
            sc.next(); // le tipo
            count++; // aumenta
        }

        sc.close(); 

        return count; // retorna quantidade
    }

    // separa tipos de cozinha
    public static String[] parseTiposCozinha(String s) {
        int n = contarTiposCozinha(s); // conta tipos
        String[] resp = new String[n]; // cria vetor

        Scanner sc = new Scanner(s); // cria scanner
        sc.useDelimiter(";"); // separa por ;

        for (int i = 0; i < n; i++) { // percorre vetor
            resp[i] = sc.next(); // guarda tipo
        }

        sc.close(); 

        return resp; // retorna vetor
    }

    // cria restaurante a partir da linha do csv
    public static Restaurante parseRestaurante(String s) {
        Scanner sc = new Scanner(s); // cria scanner da linha
        sc.useDelimiter(","); // separa por virgula

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

        sc.close(); // fecha scanner

        String[] tiposCozinha = parseTiposCozinha(tipos); // converte tipos
        int faixaPreco = parseFaixaPreco(faixa); // converte faixa

        Scanner scHorario = new Scanner(horario); // scanner do horario
        scHorario.useDelimiter("-"); // separa por -

        String abertura = scHorario.next(); // le abertura
        String fechamento = scHorario.next(); // le fechamento

        scHorario.close(); // fecha scanner

        Hora horarioAbertura = Hora.parseHora(abertura); // cria abertura
        Hora horarioFechamento = Hora.parseHora(fechamento); // cria fechamento
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

// classe ColecaoRestaurantes
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
            File arquivo = new File(path); // representa arquivo
            Scanner sc = new Scanner(arquivo); // abre scanner

            int count = 0; // contador

            if (sc.hasNextLine()) { // se tem cabecalho
                sc.nextLine(); // pula cabecalho
            }

            while (sc.hasNextLine()) { // conta linhas
                sc.nextLine(); // le linha
                count++; // aumenta contador
            }

            sc.close(); // fecha scanner

            this.tamanho = count; // salva tamanho
            this.restaurantes = new Restaurante[this.tamanho]; // cria vetor

            sc = new Scanner(arquivo); // reabre scanner

            if (sc.hasNextLine()) { // se tem cabecalho
                sc.nextLine(); // pula cabecalho
            }

            for (int i = 0; i < this.tamanho; i++) { // percorre linhas
                String linha = sc.nextLine(); // le linha
                this.restaurantes[i] = Restaurante.parseRestaurante(linha); // cria restaurante
            }

            sc.close();

        } catch (FileNotFoundException e) { // se nao encontrou arquivo
            System.out.println("Erro ao abrir o arquivo."); // imprime erro
        }
    }

    // le csv padrao
    public static ColecaoRestaurantes lerCsv() {
        ColecaoRestaurantes colecao = new ColecaoRestaurantes(); // cria colecao
        colecao.lerCsv("/tmp/restaurantes.csv"); // le csv padrao
        return colecao; // retorna colecao
    }

    // busca por id
    public Restaurante buscarPorId(int id) {
        Restaurante resp = null; // resposta

        for (int i = 0; i < this.tamanho; i++) { // percorre vetor
            if (this.restaurantes[i].getId() == id) { // se id bate
                resp = this.restaurantes[i]; // salva restaurante
                i = this.tamanho; // encerra laco
            }
        }

        return resp; // retorna restaurante ou null
    }
}

// classe TabelaHash
class TabelaHash {
    private Restaurante[] tabela; // guarda tabela
    private int tamanho; // guarda tamanho
    private long comparacoes; // guarda comparacoes

    // construtor
    public TabelaHash() {
        this.tamanho = 83; // tamanho definido pelo enunciado
        this.tabela = new Restaurante[this.tamanho]; // cria tabela
        this.comparacoes = 0; // inicia comparacoes

        for (int i = 0; i < this.tamanho; i++) { // percorre tabela
            this.tabela[i] = null; // inicia vazio
        }
    }

    // retorna comparacoes
    public long getComparacoes() {
        return this.comparacoes; // devolve comparacoes
    }

    // soma os codigos ASCII do nome
    private int asciiNome(String nome) {
        int soma = 0; // acumulador

        for (int i = 0; i < nome.length(); i++) { // percorre nome
            soma += nome.charAt(i); // soma codigo ASCII
        }

        return soma; // retorna soma
    }

    // primeira funcao hash
    private int h(String nome) {
        return asciiNome(nome) % this.tamanho; // retorna hash principal
    }

    // segunda funcao hash com rehash
    private int reh(String nome) {
        return (asciiNome(nome) + 1) % this.tamanho; // retorna posicao alternativa
    }

    // insere restaurante
    // insere restaurante
    public void inserir(Restaurante restaurante) {
        int pos = h(restaurante.getNome()); // calcula posicao principal

        if (this.tabela[pos] == null) { // se posicao principal esta vazia
            this.tabela[pos] = restaurante; // insere na posicao principal
        } else {
            int posReh = reh(restaurante.getNome()); // calcula segunda posicao com rehash

            if (this.tabela[posReh] == null) { // se a posicao do rehash esta vazia
                this.tabela[posReh] = restaurante; // insere na posicao do rehash
            } else {
                System.out.println(restaurante.getNome()); // imprime nome se nao conseguiu inserir
            }
        }
    }

    // pesquisa nome e retorna posicao
    public int pesquisar(String nome) {
        int pos = h(nome); // calcula posicao principal

        this.comparacoes++; // conta comparacao principal

        if (this.tabela[pos] != null && this.tabela[pos].getNome().compareTo(nome) == 0) { // se achou principal
            return pos; // retorna posicao principal
        }

        int posReh = reh(nome); // calcula rehash

        this.comparacoes++; // conta comparacao do rehash

        if (this.tabela[posReh] != null && this.tabela[posReh].getNome().compareTo(nome) == 0) { // se achou no rehash
            return posReh; // retorna posicao rehash
        }

        return -1; // nao achou
    }

    // imprime resultado da pesquisa
    public void imprimirPesquisa(String nome) {
        int pos = pesquisar(nome); // pesquisa posicao

        if (pos == -1) { // se nao encontrou
            System.out.println("-1"); // imprime -1
        } else {
            System.out.println(pos + " " + this.tabela[pos].formatar()); // imprime posicao e restaurante
        }
    }
}

// classe principal
public class q4hashRehash {
    public static final String MATRICULA = "898128"; // matricula

    // verifica FIM
    public static boolean isFim(String s) {
        return s.compareTo("FIM") == 0; // retorna true se for FIM
    }

    // cria log
    public static void criarLog(long comparacoes) {
        try {
            PrintWriter pw = new PrintWriter(MATRICULA + "_hash_rehash.txt"); // cria log
            pw.println(MATRICULA + "\t" + comparacoes + "\t0.0"); // escreve log
            pw.close(); // fecha log
        } catch (FileNotFoundException e) { // se der erro
            System.out.println("Erro ao criar arquivo de log."); // imprime erro
        }
    }

    // metodo principal
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); // cria scanner
        ColecaoRestaurantes colecao = ColecaoRestaurantes.lerCsv(); // le csv
        TabelaHash hash = new TabelaHash(); // cria hash

        int id; // guarda id
        String nome; // guarda nome pesquisado

        id = sc.nextInt(); // le primeiro id

        while (id != -1) { // enquanto nao for -1
            Restaurante restaurante = colecao.buscarPorId(id); // busca restaurante

            if (restaurante != null) { // se encontrou
                hash.inserir(restaurante); // insere na hash
            }

            id = sc.nextInt(); // le proximo id
        }

        sc.nextLine(); // consome quebra depois do -1

        nome = sc.nextLine(); // le primeiro nome completo

        while (!isFim(nome)) { // enquanto nao for FIM
            hash.imprimirPesquisa(nome); // pesquisa e imprime
            nome = sc.nextLine(); // le proximo nome completo
        }

        criarLog(hash.getComparacoes()); // cria log

        sc.close(); 
    }
}
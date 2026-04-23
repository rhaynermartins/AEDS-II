import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

// classe Data
class Data {
    private int ano; // guarda o ano
    private int mes; // guarda o mês
    private int dia; // guarda o dia

    // construtor
    public Data(int ano, int mes, int dia) {
        this.ano = ano; // recebe o valor do parâmetro ano
        this.mes = mes; // recebe o valor do parâmetro mes
        this.dia = dia; // recebe o valor do parâmetro dia
    }

    // método get do ano
    public int getAno() {
        return this.ano; // devolve o ano armazenado no objeto
    }

    // método get do mês
    public int getMes() {
        return this.mes; // devolve o mês armazenado no objeto
    }

    // método get do dia
    public int getDia() {
        return this.dia; // devolve o dia armazenado no objeto
    }

    // método que recebe uma string no formato YYYY-MM-DD e transforma em objeto Data
    public static Data parseData(String s) {
        Scanner sc = new Scanner(s);

        // "2024-03-31" será dividido em 2024 | 03 | 31
        sc.useDelimiter("-");

        // lê a primeira parte da string e guarda no ano
        int ano = sc.nextInt();

        // lê a segunda parte da string e guarda no mês
        int mes = sc.nextInt();

        // lê a terceira parte da string e guarda no dia
        int dia = sc.nextInt();

        sc.close();

        // cria e retorna um novo objeto Data com os valores lidos
        return new Data(ano, mes, dia);
    }

    // método que devolve a data formatada como DD/MM/YYYY
    public String formatar() {
        String diaString = "";
        String mesString = "";

        if (this.dia < 10) {
            diaString = "0" + this.dia;
        } else {
            diaString = "" + this.dia;
        }

        if (this.mes < 10) {
            mesString = "0" + this.mes;
        } else {
            mesString = "" + this.mes;
        }

        return diaString + "/" + mesString + "/" + this.ano;
    }
}

// classe Hora
class Hora {
    private int hora; // guarda a hora
    private int minuto; // guarda o min

    // construtor
    public Hora(int hora, int minuto) {
        this.hora = hora; // recebe o valor do parâmetro hora
        this.minuto = minuto; // recebe o valor do parâmetro minuto
    }

    // get da hora
    public int getHora() {
        return this.hora; // retorna a hora
    }

    // get do minuto
    public int getMinuto() {
        return this.minuto; // retorna o minuto 
    }

    // método que recebe uma string no formato HH:mm e cria um objeto Hora
    public static Hora parseHora(String s) {
        Scanner sc = new Scanner(s);

        // "11:30" vira 11 | 30, define ":" como separador
        sc.useDelimiter(":");

        // lê a parte da hora
        int hora = sc.nextInt();

        // lê a parte do minuto
        int minuto = sc.nextInt();

        sc.close();

        // cria e retorna novo objeto Hora
        return new Hora(hora, minuto);
    }

    // método que devolve a hora no formato HH:mm
    public String formatar() {
        String horaString = "";
        String minutoString = "";

        if (this.hora < 10) {
            horaString = "0" + this.hora;
        } else {
            horaString = "" + this.hora;
        }

        if (this.minuto < 10) {
            minutoString = "0" + this.minuto;
        } else {
            minutoString = "" + this.minuto;
        }

        return horaString + ":" + minutoString;
    }
}
// inicio da classe Restaurante
class Restaurante {
    private int id; // id do restaurante
    private String nome; // nome 
    private String cidade; // cidade 
    private int capacidade; // capacidade máxima 
    private double avaliacao; // nota média 
    private String[] tiposCozinha; // vetor com os tipos de cozinha
    private int faixaPreco; // faixa de preço convertida para inteiro de 1 a 4
    private Hora horarioAbertura; // horário que abre
    private Hora horarioFechamento; // horário que fecha
    private Data dataAbertura; // data de abertura
    private boolean aberto; // mostra se o restaurante está funcionando

    // construtor, recebe todos os valores e monta um objeto completo
    public Restaurante(int id, String nome, String cidade, int capacidade, double avaliacao,
                       String[] tiposCozinha, int faixaPreco, Hora horarioAbertura,
                       Hora horarioFechamento, Data dataAbertura, boolean aberto) {
        this.id = id; // guarda o id
        this.nome = nome; // guarda o nome
        this.cidade = cidade; // guarda a cidade
        this.capacidade = capacidade; // guarda a capacidade
        this.avaliacao = avaliacao; // guarda a avaliação
        this.tiposCozinha = tiposCozinha; // guarda o vetor de tipos de cozinha
        this.faixaPreco = faixaPreco; // guarda a faixa de preço
        this.horarioAbertura = horarioAbertura; // guarda o horário de abertura
        this.horarioFechamento = horarioFechamento; // guarda o horário de fechamento
        this.dataAbertura = dataAbertura; // guarda a data de abertura
        this.aberto = aberto; // guarda se está aberto ou não
    }
    // get do id
    public int getId() {
        return this.id; // retorna o id
    }
    // get do nome
    public String getNome() {
        return this.nome; // retorna o nome
    }
    // get da cidade
    public String getCidade() {
        return this.cidade; // retorna a cidade
    }
    // get da capacidade
    public int getCapacidade() {
        return this.capacidade; // retorna a capacidade
    }
    // get da avaliação
    public double getAvaliacao() {
        return this.avaliacao; // retorna a avaliação
    }
    // get do vetor de tipos de cozinha
    public String[] getTiposCozinha() {
        return this.tiposCozinha; // retorna o vetor de tipos
    }
    // get da faixa de preço
    public int getFaixaPreco() {
        return this.faixaPreco; // retorna a faixa de preço 
    }
    // get do horário de abertura
    public Hora getHorarioAbertura() {
        return this.horarioAbertura; // retorna o objeto Hora que abre
    }
    // get do horário de fechamento
    public Hora getHorarioFechamento() {
        return this.horarioFechamento; // retorna o objeto Hora que fecha
    }
    // get da data de abertura
    public Data getDataAbertura() {
        return this.dataAbertura; // retorna o objeto Data de abertura
    }
    // get do atributo aberto
    public boolean getAberto() {
        return this.aberto; // retorna true ou false
    }

    // método que converte a faixa de preço do CSV para número, "$" vira 1, "$$" vira 2, e assim por diante
    public static int parseFaixaPreco(String s) {
        int resp = 0; // guardará a resposta

        // retorna 0 quando as strings são iguais
        if (s.compareTo("$") == 0) {
            resp = 1; // se for "$", retorna 1
        } else if (s.compareTo("$$") == 0) {
            resp = 2; // se for "$$", retorna 2
        } else if (s.compareTo("$$$") == 0) {
            resp = 3; // se for "$$$", retorna 3
        } else if (s.compareTo("$$$$") == 0) {
            resp = 4; // se for "$$$$", retorna 4
        }

        return resp; // devolve o valor encontrado
    }

    // método que faz o contrário, recebe 1,2,3,4 e devolve $, $$, $$$, $$$$
    public static String formatarFaixaPreco(int faixaPreco) {
        String resp = ""; // inicializa uma string

        // repete faixaPreco adicionando "$"
        for (int i = 0; i < faixaPreco; i++) {
            resp += "$";
        }

        return resp; // devolve a string final
    }

    // método que conta quantos tipos de cozinha existem em uma string
    public static int contarTiposCozinha(String s) {
        Scanner sc = new Scanner(s);

        // define ";" como separador
        sc.useDelimiter(";");

        int count = 0; // contador para os tipos 

        while (sc.hasNext()) {
            sc.next(); // lê uma parte
            count++; // aumenta o contador
        }

        sc.close();

        return count;  // retorna quantos tipos foram encontrados
    }

    // método que transforma a string dos tipos de cozinha em vetor
    public static String[] parseTiposCozinha(String s) {
        // descobre quantos tipos existem
        int n = contarTiposCozinha(s);

        // cria um vetor com esse tamanho
        String[] resp = new String[n];

        // lê novamente a string
        Scanner sc = new Scanner(s);

        // define ";" como separador
        sc.useDelimiter(";");

        // percorre o vetor preenchendo cada posição
        for (int i = 0; i < n; i++) {
            resp[i] = sc.next(); // lê o próximo tipo de cozinha
        }

        sc.close();

        return resp; // retorna o vetor pronto
    }

    // método que recebe uma linha inteira do CSV e transforma essa linha em um objeto Restaurante
    public static Restaurante parseRestaurante(String s) {
        Scanner sc = new Scanner(s);

        // define "," como separador 
        sc.useDelimiter(",");

        // lê os campos na ordem do arquivo CSV
        int id = sc.nextInt(); // lê o id
        String nome = sc.next(); // lê o nome
        String cidade = sc.next(); // lê a cidade
        int capacidade = sc.nextInt(); // lê a capacidade
        double avaliacao = sc.nextDouble(); // lê a avaliação
        String tipos = sc.next(); // lê a string dos tipos de cozinha
        String faixa = sc.next(); // lê a faixa de preço em string
        String horario = sc.next(); // lê o horário completo no formato HH:mm-HH:mm
        String data = sc.next(); // lê a data de abertura
        boolean aberto = sc.nextBoolean(); // lê se está aberto

        sc.close();

        // converte a string dos tiposcozinha em vetor
        String[] tiposCozinha = parseTiposCozinha(tipos);

        // converte faixapreço em inteiro
        int faixaPreco = parseFaixaPreco(faixa);

        // separa o horário em abertura e fechamento
        Scanner scHorario = new Scanner(horario);

        // define "-" como separador
        scHorario.useDelimiter("-");

        // lê a parte da abertura
        String abertura = scHorario.next();

        // lê a parte do fechamento
        String fechamento = scHorario.next();

        scHorario.close();

        // transforma as strings em objetos Hora
        Hora horarioAbertura = Hora.parseHora(abertura);
        Hora horarioFechamento = Hora.parseHora(fechamento);

        // transforma a string da data em objeto Data
        Data dataAbertura = Data.parseData(data);

        // cria e retorna o restaurante completo
        return new Restaurante(id, nome, cidade, capacidade, avaliacao, tiposCozinha,
                               faixaPreco, horarioAbertura, horarioFechamento,
                               dataAbertura, aberto);
    }

    // método que transforma o vetor tiposCozinha em string
    public String formatarTiposCozinha() {
        String resp = "["; // começa com colchete aberto

        // percorre todo o vetor de tipos
        for (int i = 0; i < this.tiposCozinha.length; i++) {
            resp += this.tiposCozinha[i]; // adiciona o tipo atual

            // se não for o último elemento, adiciona vírgula
            if (i < this.tiposCozinha.length - 1) {
                resp += ",";
            }
        }
        resp += "]";  // fecha o colchete

        return resp; // devolve a string pronta
    }

    // método que devolve o restaurante no formato correto
    public String formatar() {
        return "[" + this.id // coloca o id
                + " ## " + this.nome // coloca o nome
                + " ## " + this.cidade // coloca a cidade
                + " ## " + this.capacidade // coloca a capacidade
                + " ## " + this.avaliacao // coloca a avaliação
                + " ## " + this.formatarTiposCozinha() // coloca os tipos de cozinha formatados
                + " ## " + formatarFaixaPreco(this.faixaPreco) // coloca a faixa de preço em $
                + " ## " + this.horarioAbertura.formatar() + "-" + this.horarioFechamento.formatar() // coloca horários
                + " ## " + this.dataAbertura.formatar() // coloca a data formatada
                + " ## " + this.aberto // coloca true ou false
                + "]"; // fecha o colchete do registro
    }
}

// classe que representa a coleção de restaurantes
class ColecaoRestaurantes {
    private int tamanho; // qnt de restaurantes armazenados
    private Restaurante[] restaurantes; // vetor de restaurantes

    // construtor
    public ColecaoRestaurantes() {
        this.tamanho = 0; // começa com zero 
        this.restaurantes = new Restaurante[0]; // começa vazio
    }
    // get do tamanho
    public int getTamanho() {
        return this.tamanho; // retorna a qnt de restaurantes
    }
    // get do vetor de restaurantes
    public Restaurante[] getRestaurantes() {
        return this.restaurantes; // retorna o vetor 
    }

    // método que lê o arquivo CSV 
    public void lerCsv(String path) {
        try {
            File arquivo = new File(path); // cria objeto File 

            Scanner sc = new Scanner(arquivo); // lê o arquivo

            int count = 0; // contador de linhas, sem contar o cabeçalho

            // se existir pelo menos uma linha, lê a primeira
            // se a primeira linha for o cabeçalho, será ignorada
            if (sc.hasNextLine()) {
                sc.nextLine();
            }

            // percorre o restante do arquivo para contar quantos restaurantes existem
            while (sc.hasNextLine()) {
                sc.nextLine();
                count++; // soma 1 no contador
            }

            sc.close();

            // guarda o total de restaurantes no atributo tamanho
            this.tamanho = count;

            // cria o vetor com o tamanho exato
            this.restaurantes = new Restaurante[this.tamanho];

            // lê o arquivo novamente
            sc = new Scanner(arquivo);

            // pula novamente o cabeçalho
            if (sc.hasNextLine()) {
                sc.nextLine();
            }

            // lê linha por linha e monta os objetos Restaurante
            for (int i = 0; i < this.tamanho; i++) {
                String linha = sc.nextLine(); // lê a linha atual do arquivo CSV
                this.restaurantes[i] = Restaurante.parseRestaurante(linha); // transforma a linha em restaurante
            }

            sc.close();

        } catch (FileNotFoundException e) {
            // caso o arquivo não seja encontrado
            System.out.println("Erro ao abrir o arquivo.");
        }
    }

    // método que lê o arquivo CSV padrão em /tmp/restaurantes.csv
    public static ColecaoRestaurantes lerCsv() {
        // cria uma coleção vazia
        ColecaoRestaurantes colecao = new ColecaoRestaurantes();

        // manda essa coleção ler o arquivo padrão
        colecao.lerCsv("/tmp/restaurantes.csv");

        return colecao;  // retorna a coleção já preenchida
    }

    // método que busca um restaurante pelo id
    public Restaurante buscarPorId(int id) {
        Restaurante resp = null; // começa em null

        // percorre todo o vetor
        for (int i = 0; i < this.tamanho; i++) {
            // se o id atual for igual ao id procurado
            if (this.restaurantes[i].getId() == id) {
                resp = this.restaurantes[i]; // guarda o restaurante encontrado
                i = this.tamanho; // força a saída do laço
            }
        }

        return resp;  // retorna o restaurante encontrado ou null
    }
}

// classe principal 
public class q1modeleagem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // lê todo o arquivo CSV e cria a coleção de restaurantes
        ColecaoRestaurantes colecao = ColecaoRestaurantes.lerCsv();

        // lê o primeiro id digitado
        int id = sc.nextInt();

        // continua enquanto o id for diferente de -1
        while (id != -1) {
            // busca o restaurante correspondente ao id
            Restaurante restaurante = colecao.buscarPorId(id);

            // se encontrou o restaurante
            if (restaurante != null) {
                // imprime no formato pedido
                System.out.println(restaurante.formatar());
            }
            // lê o próximo id
            id = sc.nextInt();
        }

        sc.close();
    }
}
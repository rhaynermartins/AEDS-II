import java.util.Scanner; 
import java.io.File; 
import java.io.FileNotFoundException; 

// inicio da classe Data
class Data {
    // atributos privados da classe Data
    // private significa que só a própria classe acessa diretamente esses atributos
    private int ano; // guarda o ano
    private int mes; // guarda o mês
    private int dia; // guarda o dia

    // construtor da classe
    // toda vez que criarmos um objeto Data, passaremos ano, mes e dia
    public Data(int ano, int mes, int dia) {
        this.ano = ano; // this.ano representa o atributo da classe
        this.mes = mes; // recebe o valor do parâmetro mes
        this.dia = dia; // recebe o valor do parâmetro dia
    }

    // método get do ano
    // serve para retornar o valor do atributo ano
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

    // método estático que recebe uma string no formato YYYY-MM-DD
    // e transforma essa string em um objeto Data
    public static Data parseData(String s) {
        // cria um Scanner para "ler" a string recebida
        Scanner sc = new Scanner(s);

        // diz que o separador dos dados será o hífen
        // então "2024-03-31" será dividido em 2024 | 03 | 31
        sc.useDelimiter("-");

        // lê a primeira parte da string e guarda no ano
        int ano = sc.nextInt();

        // lê a segunda parte da string e guarda no mês
        int mes = sc.nextInt();

        // lê a terceira parte da string e guarda no dia
        int dia = sc.nextInt();

        // fecha o scanner para liberar recurso
        sc.close();

        // cria e retorna um novo objeto Data com os valores lidos
        return new Data(ano, mes, dia);
    }

    // método que devolve a data formatada como DD/MM/YYYY
    public String formatar() {
        // String.format permite montar uma string formatada
        // %02d significa inteiro com 2 casas, completando com zero à esquerda
        // %04d significa inteiro com 4 casas
        return String.format("%02d/%02d/%04d", this.dia, this.mes, this.ano);
    }
}

// inicio da classe Hora
class Hora {
    // atributos privados da hora
    private int hora; // guarda a hora
    private int minuto; // guarda o minuto

    // construtor da classe Hora
    public Hora(int hora, int minuto) {
        this.hora = hora; // atributo hora recebe o valor passado
        this.minuto = minuto; // atributo minuto recebe o valor passado
    }

    // get da hora
    public int getHora() {
        return this.hora; // retorna a hora armazenada
    }

    // get do minuto
    public int getMinuto() {
        return this.minuto; // retorna o minuto armazenado
    }

    // método estático que recebe uma string no formato HH:mm
    // e cria um objeto Hora
    public static Hora parseHora(String s) {
        // cria scanner para ler a string
        Scanner sc = new Scanner(s);

        // define ":" como separador
        // exemplo: "11:30" vira 11 | 30
        sc.useDelimiter(":");

        // lê a parte da hora
        int hora = sc.nextInt();

        // lê a parte do minuto
        int minuto = sc.nextInt();

        // fecha o scanner
        sc.close();

        // cria e retorna novo objeto Hora
        return new Hora(hora, minuto);
    }

    // método que devolve a hora no formato HH:mm
    public String formatar() {
        // formata com dois dígitos para hora e minuto
        return String.format("%02d:%02d", this.hora, this.minuto);
    }
}

// inicio da classe Restaurante
class Restaurante {
    // atributos privados do restaurante
    private int id; // identificador único do restaurante
    private String nome; // nome do restaurante
    private String cidade; // cidade do restaurante
    private int capacidade; // capacidade máxima de clientes
    private double avaliacao; // nota média do restaurante
    private String[] tiposCozinha; // vetor com os tipos de cozinha
    private int faixaPreco; // faixa de preço convertida para inteiro de 1 a 4
    private Hora horarioAbertura; // horário de abertura
    private Hora horarioFechamento; // horário de fechamento
    private Data dataAbertura; // data de abertura
    private boolean aberto; // indica se o restaurante está funcionando

    // construtor da classe Restaurante
    // recebe todos os valores e monta um objeto completo
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
        return this.faixaPreco; // retorna a faixa de preço numérica
    }

    // get do horário de abertura
    public Hora getHorarioAbertura() {
        return this.horarioAbertura; // retorna o objeto Hora de abertura
    }

    // get do horário de fechamento
    public Hora getHorarioFechamento() {
        return this.horarioFechamento; // retorna o objeto Hora de fechamento
    }

    // get da data de abertura
    public Data getDataAbertura() {
        return this.dataAbertura; // retorna o objeto Data de abertura
    }

    // get do atributo aberto
    public boolean getAberto() {
        return this.aberto; // retorna true ou false
    }

    // método estático que converte a faixa de preço do CSV para número
    // "$" vira 1
    // "$$" vira 2
    // "$$$" vira 3
    // "$$$$" vira 4
    public static int parseFaixaPreco(String s) {
        int resp = 0; // variável que guardará a resposta

        // compareTo retorna 0 quando as strings são iguais
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

    // método que faz o contrário:
    // recebe 1,2,3,4 e devolve $, $$, $$$, $$$$
    public static String formatarFaixaPreco(int faixaPreco) {
        String resp = ""; // começa com string vazia

        // repete faixaPreco vezes adicionando "$"
        // se faixaPreco = 3, resultado será "$$$"
        for (int i = 0; i < faixaPreco; i++) {
            resp += "$";
        }

        return resp; // devolve a string final
    }

    // método que conta quantos tipos de cozinha existem em uma string
    // exemplo: "japonesa;sushi" tem 2 tipos
    public static int contarTiposCozinha(String s) {
        // cria scanner para ler a string
        Scanner sc = new Scanner(s);

        // define ";" como separador
        sc.useDelimiter(";");

        int count = 0; // contador de tipos

        // enquanto ainda houver partes na string
        while (sc.hasNext()) {
            sc.next(); // lê uma parte
            count++; // aumenta o contador
        }

        // fecha o scanner
        sc.close();

        // retorna quantos tipos foram encontrados
        return count;
    }

    // método que transforma a string dos tipos de cozinha em vetor
    // exemplo: "japonesa;sushi" vira ["japonesa", "sushi"]
    public static String[] parseTiposCozinha(String s) {
        // primeiro descobre quantos tipos existem
        int n = contarTiposCozinha(s);

        // cria um vetor com exatamente esse tamanho
        String[] resp = new String[n];

        // cria scanner para ler novamente a string
        Scanner sc = new Scanner(s);

        // define ";" como separador
        sc.useDelimiter(";");

        // percorre o vetor preenchendo cada posição
        for (int i = 0; i < n; i++) {
            resp[i] = sc.next(); // lê o próximo tipo de cozinha
        }

        // fecha o scanner
        sc.close();

        // retorna o vetor pronto
        return resp;
    }

    // método estático que recebe uma linha inteira do CSV
    // e transforma essa linha em um objeto Restaurante
    public static Restaurante parseRestaurante(String s) {
        // cria scanner para ler a linha do CSV
        Scanner sc = new Scanner(s);

        // define "," como separador dos campos do CSV
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

        // fecha o scanner da linha
        sc.close();

        // converte a string dos tipos de cozinha em vetor
        String[] tiposCozinha = parseTiposCozinha(tipos);

        // converte a faixa de preço em inteiro
        int faixaPreco = parseFaixaPreco(faixa);

        // agora precisa separar o horário em abertura e fechamento
        Scanner scHorario = new Scanner(horario);

        // define "-" como separador
        scHorario.useDelimiter("-");

        // lê a parte da abertura
        String abertura = scHorario.next();

        // lê a parte do fechamento
        String fechamento = scHorario.next();

        // fecha o scanner do horário
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

    // método auxiliar que transforma o vetor tiposCozinha em string
    // exemplo: ["japonesa", "sushi"] vira [japonesa,sushi]
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

        // fecha o colchete
        resp += "]";

        // devolve a string pronta
        return resp;
    }

    // método que devolve o restaurante no formato pedido pelo enunciado
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

// inicio da classe que representa a coleção de restaurantes
class ColecaoRestaurantes {
    // atributos da coleção
    private int tamanho; // quantidade de restaurantes armazenados
    private Restaurante[] restaurantes; // vetor de restaurantes

    // construtor padrão
    public ColecaoRestaurantes() {
        this.tamanho = 0; // começa com zero elementos
        this.restaurantes = new Restaurante[0]; // começa com vetor vazio
    }

    // get do tamanho
    public int getTamanho() {
        return this.tamanho; // retorna a quantidade de restaurantes
    }

    // get do vetor de restaurantes
    public Restaurante[] getRestaurantes() {
        return this.restaurantes; // retorna o vetor de restaurantes
    }

    // método que lê o CSV a partir de um caminho
    public void lerCsv(String path) {
        try {
            // cria objeto File representando o arquivo
            File arquivo = new File(path);

            // cria Scanner para ler o arquivo
            Scanner sc = new Scanner(arquivo);

            int count = 0; // contador de linhas do arquivo, sem contar o cabeçalho

            // se existir pelo menos uma linha, lê a primeira
            // essa primeira linha é o cabeçalho, então será ignorada
            if (sc.hasNextLine()) {
                sc.nextLine();
            }

            // percorre o restante do arquivo só para contar quantos restaurantes existem
            while (sc.hasNextLine()) {
                sc.nextLine(); // lê uma linha
                count++; // soma 1 no contador
            }

            // fecha o scanner depois da contagem
            sc.close();

            // guarda o total de restaurantes no atributo tamanho
            this.tamanho = count;

            // cria o vetor com o tamanho exato
            this.restaurantes = new Restaurante[this.tamanho];

            // reabre o scanner para ler o arquivo novamente
            sc = new Scanner(arquivo);

            // pula novamente o cabeçalho
            if (sc.hasNextLine()) {
                sc.nextLine();
            }

            // agora lê linha por linha e monta os objetos Restaurante
            for (int i = 0; i < this.tamanho; i++) {
                String linha = sc.nextLine(); // lê a linha atual do CSV
                this.restaurantes[i] = Restaurante.parseRestaurante(linha); // transforma a linha em restaurante
            }

            // fecha o scanner ao final
            sc.close();

        } catch (FileNotFoundException e) {
            // caso o arquivo não seja encontrado, mostra mensagem
            System.out.println("Erro ao abrir o arquivo.");
        }
    }

    // método estático que lê o CSV padrão em /tmp/restaurantes.csv
    public static ColecaoRestaurantes lerCsv() {
        // cria uma coleção vazia
        ColecaoRestaurantes colecao = new ColecaoRestaurantes();

        // manda essa coleção ler o arquivo padrão
        colecao.lerCsv("/tmp/restaurantes.csv");

        // retorna a coleção já preenchida
        return colecao;
    }

    // método que busca um restaurante pelo id
    public Restaurante buscarPorId(int id) {
        Restaurante resp = null; // começa supondo que não encontrou

        // percorre todo o vetor
        for (int i = 0; i < this.tamanho; i++) {
            // se o id atual for igual ao id procurado
            if (this.restaurantes[i].getId() == id) {
                resp = this.restaurantes[i]; // guarda o restaurante encontrado
                i = this.tamanho; // força a saída do laço
            }
        }

        // retorna o restaurante encontrado ou null
        return resp;
    }
}

// classe principal do programa
public class q1modeleagem {
    public static void main(String[] args) {
        // scanner para ler a entrada padrão
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

        // fecha o scanner de entrada
        sc.close();
    }
}
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
        this.ano = ano; // atributo ano recebe o valor passado
        this.mes = mes; // atributo mes recebe o valor passado
        this.dia = dia; // atributo dia recebe o valor passado
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

    // recebe uma string no formato YYYY-MM-DD e cria um objeto Data
    public static Data parseData(String s) {
        Scanner sc = new Scanner(s); 
        sc.useDelimiter("-"); // define "-" como separador

        int ano = sc.nextInt(); // le o ano
        int mes = sc.nextInt(); // le o mes
        int dia = sc.nextInt(); // le o dia

        sc.close();

        return new Data(ano, mes, dia); // cria e retorna a nova Data
    }

    // retorna a data no formato DD/MM/YYYY
    public String formatar() {
        String diaString = ""; // string auxiliar do dia
        String mesString = ""; // string auxiliar do mes

        if (this.dia < 10) { // se o dia tiver um digito
            diaString = "0" + this.dia; // coloca 0 na frente
        } else {
            diaString = "" + this.dia; // senao escreve normal
        }

        if (this.mes < 10) { // se o mes tiver um digito
            mesString = "0" + this.mes; // coloca 0 na frente
        } else {
            mesString = "" + this.mes; // senao escreve normal
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
        this.hora = hora; // atributo hora recebe o valor passado
        this.minuto = minuto; // atributo minuto recebe o valor passado
    }

    // retorna a hora
    public int getHora() {
        return this.hora; // devolve a hora
    }

    // retorna o minuto
    public int getMinuto() {
        return this.minuto; // devolve o minuto
    }

    // recebe uma string no formato HH:mm e cria um objeto Hora
    public static Hora parseHora(String s) {
        Scanner sc = new Scanner(s); 
        sc.useDelimiter(":"); // define ":" como separador

        int hora = sc.nextInt(); // le a hora
        int minuto = sc.nextInt(); // le o minuto

        sc.close();

        return new Hora(hora, minuto); // cria e retorna a nova Hora
    }

    // retorna a hora no formato HH:mm
    public String formatar() {
        String horaString = ""; // string auxiliar da hora
        String minutoString = ""; // string auxiliar do minuto

        if (this.hora < 10) { // se a hora tiver um digito
            horaString = "0" + this.hora; // coloca 0 na frente
        } else {
            horaString = "" + this.hora; // senao escreve normal
        }

        if (this.minuto < 10) { // se o minuto tiver um digito
            minutoString = "0" + this.minuto; // coloca 0 na frente
        } else {
            minutoString = "" + this.minuto; // senao escreve normal
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
    private Hora horarioAbertura; // guarda horario de abertura
    private Hora horarioFechamento; // guarda horario de fechamento
    private Data dataAbertura; // guarda a data de abertura
    private boolean aberto; // guarda true ou false

    // construtor da classe Restaurante
    public Restaurante(int id, String nome, String cidade, int capacidade, double avaliacao,
                       String[] tiposCozinha, int faixaPreco, Hora horarioAbertura,
                       Hora horarioFechamento, Data dataAbertura, boolean aberto) {
        this.id = id; // guarda o id
        this.nome = nome; // guarda o nome
        this.cidade = cidade; // guarda a cidade
        this.capacidade = capacidade; // guarda a capacidade
        this.avaliacao = avaliacao; // guarda a avaliacao
        this.tiposCozinha = tiposCozinha; // guarda os tipos de cozinha
        this.faixaPreco = faixaPreco; // guarda a faixa de preco
        this.horarioAbertura = horarioAbertura; // guarda horario de abertura
        this.horarioFechamento = horarioFechamento; // guarda horario de fechamento
        this.dataAbertura = dataAbertura; // guarda a data
        this.aberto = aberto; // guarda aberto
    }

    // retorna o id
    public int getId() {
        return this.id;
    }

    // retorna o nome
    public String getNome() {
        return this.nome;
    }

    // retorna a cidade
    public String getCidade() {
        return this.cidade;
    }

    // retorna a capacidade
    public int getCapacidade() {
        return this.capacidade;
    }

    // retorna a avaliacao
    public double getAvaliacao() {
        return this.avaliacao;
    }

    // retorna os tipos de cozinha
    public String[] getTiposCozinha() {
        return this.tiposCozinha;
    }

    // retorna a faixa de preco
    public int getFaixaPreco() {
        return this.faixaPreco;
    }

    // retorna o horario de abertura
    public Hora getHorarioAbertura() {
        return this.horarioAbertura;
    }

    // retorna o horario de fechamento
    public Hora getHorarioFechamento() {
        return this.horarioFechamento;
    }

    // retorna a data de abertura
    public Data getDataAbertura() {
        return this.dataAbertura;
    }

    // retorna se esta aberto
    public boolean getAberto() {
        return this.aberto;
    }

    // converte "$", "$$", "$$$", "$$$$" para 1, 2, 3, 4
    public static int parseFaixaPreco(String s) {
        int resp = 0; // variavel de resposta

        if (s.compareTo("$") == 0) { // se for "$"
            resp = 1; // vira 1
        } else if (s.compareTo("$$") == 0) { // se for "$$"
            resp = 2; // vira 2
        } else if (s.compareTo("$$$") == 0) { // se for "$$$"
            resp = 3; // vira 3
        } else if (s.compareTo("$$$$") == 0) { // se for "$$$$"
            resp = 4; // vira 4
        }

        return resp; // retorna a resposta
    }

    // converte 1, 2, 3, 4 para "$", "$$", "$$$", "$$$$"
    public static String formatarFaixaPreco(int faixaPreco) {
        String resp = ""; // comeca com string vazia

        for (int i = 0; i < faixaPreco; i++) { // repete faixaPreco vezes
            resp += "$"; // acrescenta um $
        }

        return resp; // retorna a string final
    }

    // conta quantos tipos de cozinha existem na string
    public static int contarTiposCozinha(String s) {
        Scanner sc = new Scanner(s); 
        sc.useDelimiter(";"); // define ";" como separador

        int count = 0; // contador de tipos

        while (sc.hasNext()) { // enquanto houver partes
            sc.next(); // le uma parte
            count++; // aumenta a contagem
        }

        sc.close(); 

        return count; // retorna a quantidade
    }

    // transforma a string dos tipos de cozinha em vetor
    public static String[] parseTiposCozinha(String s) {
        int n = contarTiposCozinha(s); // descobre quantos tipos existem
        String[] resp = new String[n]; // cria o vetor do tamanho certo

        Scanner sc = new Scanner(s); // cria Scanner para ler a string novamente
        sc.useDelimiter(";"); // define ";" como separador

        for (int i = 0; i < n; i++) { // percorre o vetor
            resp[i] = sc.next(); // guarda cada tipo em uma posicao
        }

        sc.close(); 

        return resp; // retorna o vetor pronto
    }

    // transforma uma linha do csv em um objeto Restaurante
    public static Restaurante parseRestaurante(String s) {
        Scanner sc = new Scanner(s); 
        sc.useDelimiter(","); // define "," como separador

        int id = sc.nextInt(); // le o id
        String nome = sc.next(); // le o nome
        String cidade = sc.next(); // le a cidade
        int capacidade = sc.nextInt(); // le a capacidade
        double avaliacao = sc.nextDouble(); // le a avaliacao
        String tipos = sc.next(); // le a string dos tipos
        String faixa = sc.next(); // le a faixa de preco
        String horario = sc.next(); // le o horario
        String data = sc.next(); // le a data
        boolean aberto = sc.nextBoolean(); // le o campo aberto

        sc.close(); // fecha o Scanner da linha

        String[] tiposCozinha = parseTiposCozinha(tipos); // converte tipos em vetor
        int faixaPreco = parseFaixaPreco(faixa); // converte faixa para inteiro

        Scanner scHorario = new Scanner(horario); // cria Scanner para separar o horario
        scHorario.useDelimiter("-"); // define "-" como separador

        String abertura = scHorario.next(); // le a parte da abertura
        String fechamento = scHorario.next(); // le a parte do fechamento

        scHorario.close(); // fecha o Scanner do horario

        Hora horarioAbertura = Hora.parseHora(abertura); // cria objeto Hora da abertura
        Hora horarioFechamento = Hora.parseHora(fechamento); // cria objeto Hora do fechamento
        Data dataAbertura = Data.parseData(data); // cria objeto Data da abertura

        return new Restaurante(id, nome, cidade, capacidade, avaliacao, tiposCozinha,
                               faixaPreco, horarioAbertura, horarioFechamento,
                               dataAbertura, aberto); // cria e retorna o restaurante
    }

    // transforma o vetor tiposCozinha em string no formato [a,b,c]
    public String formatarTiposCozinha() {
        String resp = "["; // comeca com colchete aberto

        for (int i = 0; i < this.tiposCozinha.length; i++) { // percorre o vetor de tipos
            resp += this.tiposCozinha[i]; // acrescenta o tipo atual

            if (i < this.tiposCozinha.length - 1) { // se nao for o ultimo
                resp += ","; // coloca virgula
            }
        }

        resp += "]"; // fecha o colchete

        return resp; // retorna a string pronta
    }

    // retorna o restaurante completo no formato pedido
    public String formatar() {
        return "[" + this.id // coloca o id
                + " ## " + this.nome // coloca o nome
                + " ## " + this.cidade // coloca a cidade
                + " ## " + this.capacidade // coloca a capacidade
                + " ## " + this.avaliacao // coloca a avaliacao
                + " ## " + this.formatarTiposCozinha() // coloca os tipos formatados
                + " ## " + formatarFaixaPreco(this.faixaPreco) // coloca a faixa de preco
                + " ## " + this.horarioAbertura.formatar() + "-" + this.horarioFechamento.formatar() // coloca os horarios
                + " ## " + this.dataAbertura.formatar() // coloca a data
                + " ## " + this.aberto // coloca aberto
                + "]"; // fecha o registro
    }
}

// classe que representa a colecao de restaurantes
class ColecaoRestaurantes {
    private int tamanho; // guarda o tamanho da colecao
    private Restaurante[] restaurantes; // guarda o vetor de restaurantes

    // construtor padrao
    public ColecaoRestaurantes() {
        this.tamanho = 0; // comeca com tamanho 0
        this.restaurantes = new Restaurante[0]; // comeca com vetor vazio
    }

    // retorna o tamanho
    public int getTamanho() {
        return this.tamanho;
    }

    // retorna o vetor de restaurantes
    public Restaurante[] getRestaurantes() {
        return this.restaurantes;
    }

    // le o csv a partir do caminho informado
    public void lerCsv(String path) {
        try {
            File arquivo = new File(path); // representa o arquivo
            Scanner sc = new Scanner(arquivo); // cria Scanner para ler o arquivo

            int count = 0; // conta quantas linhas de dados existem

            if (sc.hasNextLine()) { // se houver pelo menos uma linha
                sc.nextLine(); // pula o cabecalho
            }

            while (sc.hasNextLine()) { // percorre o resto do arquivo
                sc.nextLine(); // le uma linha
                count++; // aumenta a contagem
            }

            sc.close(); 

            this.tamanho = count; // guarda o total de restaurantes
            this.restaurantes = new Restaurante[this.tamanho]; // cria o vetor do tamanho certo

            sc = new Scanner(arquivo); // reabre o Scanner para ler de novo

            if (sc.hasNextLine()) { // se houver cabecalho
                sc.nextLine(); // pula o cabecalho de novo
            }

            for (int i = 0; i < this.tamanho; i++) { // percorre todas as linhas de dados
                String linha = sc.nextLine(); // le a linha atual
                this.restaurantes[i] = Restaurante.parseRestaurante(linha); // transforma a linha em restaurante
            }

            sc.close(); 

        } catch (FileNotFoundException e) { // se der erro ao abrir o arquivo
            System.out.println("Erro ao abrir o arquivo."); // mostra mensagem
        }
    }

    // le o csv padrao
    public static ColecaoRestaurantes lerCsv() {
        ColecaoRestaurantes colecao = new ColecaoRestaurantes(); // cria a colecao
        colecao.lerCsv("/tmp/restaurantes.csv"); // manda ler o csv padrao
        return colecao; // retorna a colecao pronta
    }

    // busca um restaurante pelo id
    public Restaurante buscarPorId(int id) {
        Restaurante resp = null; // comeca supondo que nao encontrou

        for (int i = 0; i < this.tamanho; i++) { // percorre o vetor todo
            if (this.restaurantes[i].getId() == id) { // se o id atual for o procurado
                resp = this.restaurantes[i]; // guarda o restaurante encontrado
                i = this.tamanho; // força a saida do laco
            }
        }

        return resp; // retorna o restaurante ou null
    }
}

// classe principal
public class q4insercao {
    public static final String MATRICULA = "898128"; // guarda a matricula usada no log

    // metodo de ordenacao por insercao usando a cidade como chave
    public static void insercaoPorCidade(Restaurante[] array, int n, long comparacoes[], long movimentacoes[]) {
    int i; // indice do for externo
    int j; // indice usado para voltar no vetor
    Restaurante tmp; // variavel auxiliar para guardar o elemento atual

    for (i = 1; i < n; i++) { // comeca da segunda posicao, porque a primeira ja é considerada ordenada
        tmp = array[i]; // guarda o restaurante atual em tmp
        movimentacoes[0]++; // conta essa copia como movimentacao

        j = i - 1; // começa comparando com o elemento anterior

        while (j >= 0 && array[j].getCidade().compareTo(tmp.getCidade()) > 0) { 
            // enquanto j for valido e a cidade da esquerda for maior que a cidade de tmp

            comparacoes[0]++; // conta uma comparacao
            array[j + 1] = array[j]; // empurra o elemento maior uma posicao para a direita
            movimentacoes[0]++; // conta essa troca de posicao como movimentacao
            j--; // anda uma posicao para a esquerda
        }

        if (j >= 0) { // se o while parou porque encontrou um elemento menor ou igual
            comparacoes[0]++; // conta a ultima comparacao que fez parar o while
        }

        array[j + 1] = tmp; // coloca tmp na posicao correta dentro da parte ordenada
        movimentacoes[0]++; // conta essa insercao final como movimentacao
    }
}

    // cria o arquivo de log
    public static void criarLog(long comparacoes, long movimentacoes, double tempo) {
        try {
            PrintWriter pw = new PrintWriter(MATRICULA + "_insercao.txt"); // cria o arquivo de log

            pw.println(MATRICULA + "\t" + comparacoes + "\t" + movimentacoes + "\t" + tempo); // escreve a linha do log

            pw.close(); // fecha o arquivo
        } catch (FileNotFoundException e) { // se der erro ao criar o arquivo
            System.out.println("Erro ao criar arquivo de log."); // mostra mensagem
        }
    }

    // metodo principal
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); // Scanner para ler a entrada
        ColecaoRestaurantes colecao = ColecaoRestaurantes.lerCsv(); // le o csv inteiro e monta a colecao

        Restaurante[] selecionados = new Restaurante[1000]; // vetor que vai guardar os restaurantes escolhidos
        int quantidade = 0; // quantidade de restaurantes escolhidos
        int id; // guarda o id lido da entrada

        long[] comparacoes = new long[1]; // vetor de uma posicao para contar comparacoes
        long[] movimentacoes = new long[1]; // vetor de uma posicao para contar movimentacoes

        long inicio; // guarda o instante inicial
        long fim; // guarda o instante final
        double tempo; // guarda o tempo total em segundos

        id = sc.nextInt(); // le o primeiro id

        while (id != -1) { // continua enquanto o id for diferente de -1
            Restaurante restaurante = colecao.buscarPorId(id); // busca o restaurante correspondente ao id

            if (restaurante != null) { // se encontrou o restaurante
                selecionados[quantidade] = restaurante; // guarda no vetor de selecionados
                quantidade++; // aumenta a quantidade
            }

            id = sc.nextInt(); // le o proximo id
        }

        inicio = System.currentTimeMillis(); // marca o tempo inicial

        insercaoPorCidade(selecionados, quantidade, comparacoes, movimentacoes); // ordena por cidade

        fim = System.currentTimeMillis(); // marca o tempo final

        tempo = (fim - inicio) / 1000.0; // calcula o tempo em segundos

        for (int i = 0; i < quantidade; i++) { // percorre o vetor ja ordenado
            System.out.println(selecionados[i].formatar()); // imprime cada restaurante formatado
        }

        criarLog(comparacoes[0], movimentacoes[0], tempo); // cria o arquivo de log

        sc.close(); 
    }
}
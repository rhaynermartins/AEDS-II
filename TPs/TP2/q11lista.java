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

    // recebe uma string no formato YYYY-MM-DD e cria um objeto Data
    public static Data parseData(String s) {
        Scanner sc = new Scanner(s); // cria Scanner para ler a string
        sc.useDelimiter("-"); // define "-" como separador

        int ano = sc.nextInt(); // le o ano
        int mes = sc.nextInt(); // le o mes
        int dia = sc.nextInt(); // le o dia

        sc.close(); // fecha o Scanner

        return new Data(ano, mes, dia); // retorna o objeto Data criado
    }

    // retorna a data no formato DD/MM/YYYY
    public String formatar() {
        String diaString = ""; // string auxiliar do dia
        String mesString = ""; // string auxiliar do mes

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
        return this.hora; // devolve a hora
    }

    // retorna o minuto
    public int getMinuto() {
        return this.minuto; // devolve o minuto
    }

    // recebe uma string no formato HH:mm e cria um objeto Hora
    public static Hora parseHora(String s) {
        Scanner sc = new Scanner(s); // cria Scanner para ler a string
        sc.useDelimiter(":"); // define ":" como separador

        int hora = sc.nextInt(); // le a hora
        int minuto = sc.nextInt(); // le o minuto

        sc.close(); // fecha o Scanner

        return new Hora(hora, minuto); // retorna o objeto Hora criado
    }

    // retorna a hora no formato HH:mm
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
        this.horarioAbertura = horarioAbertura; // salva o horario de abertura
        this.horarioFechamento = horarioFechamento; // salva o horario de fechamento
        this.dataAbertura = dataAbertura; // salva a data de abertura
        this.aberto = aberto; // salva se esta aberto
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
        return this.horarioAbertura; // devolve o horario de abertura
    }

    // retorna o horario de fechamento
    public Hora getHorarioFechamento() {
        return this.horarioFechamento; // devolve o horario de fechamento
    }

    // retorna a data de abertura
    public Data getDataAbertura() {
        return this.dataAbertura; // devolve a data de abertura
    }

    // retorna se esta aberto
    public boolean getAberto() {
        return this.aberto; // devolve se esta aberto
    }

    // converte "$", "$$", "$$$", "$$$$" em 1, 2, 3, 4
    public static int parseFaixaPreco(String s) {
        int resp = 0; // variavel de resposta

        if (s.compareTo("$") == 0) { // se a string for "$"
            resp = 1; // resposta vira 1
        } else if (s.compareTo("$$") == 0) { // se a string for "$$"
            resp = 2; // resposta vira 2
        } else if (s.compareTo("$$$") == 0) { // se a string for "$$$"
            resp = 3; // resposta vira 3
        } else if (s.compareTo("$$$$") == 0) { // se a string for "$$$$"
            resp = 4; // resposta vira 4
        }

        return resp; // retorna o valor convertido
    }

    // converte 1, 2, 3, 4 em "$", "$$", "$$$", "$$$$"
    public static String formatarFaixaPreco(int faixaPreco) {
        String resp = ""; // string de resposta

        for (int i = 0; i < faixaPreco; i++) { // repete faixaPreco vezes
            resp += "$"; // adiciona um $
        }

        return resp; // retorna a string final
    }

    // conta quantos tipos de cozinha existem na string
    public static int contarTiposCozinha(String s) {
        Scanner sc = new Scanner(s); // cria Scanner para ler a string
        sc.useDelimiter(";"); // define ";" como separador

        int count = 0; // contador de tipos

        while (sc.hasNext()) { // enquanto houver partes
            sc.next(); // le uma parte
            count++; // aumenta a contagem
        }

        sc.close(); // fecha o Scanner

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

        sc.close(); // fecha o Scanner

        return resp; // retorna o vetor pronto
    }

    // recebe uma linha do csv e cria um restaurante
    public static Restaurante parseRestaurante(String s) {
        Scanner sc = new Scanner(s); // cria Scanner para ler a linha
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
        boolean aberto = sc.nextBoolean(); // le o campo aberto

        sc.close(); // fecha o Scanner da linha

        String[] tiposCozinha = parseTiposCozinha(tipos); // converte tipos em vetor
        int faixaPreco = parseFaixaPreco(faixa); // converte faixa em inteiro

        Scanner scHorario = new Scanner(horario); // cria Scanner para separar o horario
        scHorario.useDelimiter("-"); // define "-" como separador

        String abertura = scHorario.next(); // le a parte da abertura
        String fechamento = scHorario.next(); // le a parte do fechamento

        scHorario.close(); // fecha o Scanner do horario

        Hora horarioAbertura = Hora.parseHora(abertura); // cria Hora da abertura
        Hora horarioFechamento = Hora.parseHora(fechamento); // cria Hora do fechamento
        Data dataAbertura = Data.parseData(data); // cria Data da abertura

        return new Restaurante(id, nome, cidade, capacidade, avaliacao, tiposCozinha,
                               faixaPreco, horarioAbertura, horarioFechamento,
                               dataAbertura, aberto); // retorna o restaurante criado
    }

    // formata os tipos de cozinha no formato [a,b,c]
    public String formatarTiposCozinha() {
        String resp = "["; // começa com colchete aberto

        for (int i = 0; i < this.tiposCozinha.length; i++) { // percorre o vetor de tipos
            resp += this.tiposCozinha[i]; // adiciona o tipo atual

            if (i < this.tiposCozinha.length - 1) { // se nao for o ultimo
                resp += ","; // coloca virgula
            }
        }

        resp += "]"; // fecha o colchete

        return resp; // retorna a string pronta
    }

    // formata o restaurante completo
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
        this.tamanho = 0; // começa com 0
        this.restaurantes = new Restaurante[0]; // começa vazio
    }

    // retorna o tamanho
    public int getTamanho() {
        return this.tamanho; // devolve o tamanho
    }

    // retorna o vetor
    public Restaurante[] getRestaurantes() {
        return this.restaurantes; // devolve o vetor
    }

    // le o csv do caminho informado
    public void lerCsv(String path) {
        try {
            File arquivo = new File(path); // representa o arquivo
            Scanner sc = new Scanner(arquivo); // cria Scanner para ler o arquivo

            int count = 0; // conta quantas linhas de dados existem

            if (sc.hasNextLine()) { // se houver cabecalho
                sc.nextLine(); // pula o cabecalho
            }

            while (sc.hasNextLine()) { // percorre o resto do arquivo
                sc.nextLine(); // le uma linha
                count++; // aumenta a contagem
            }

            sc.close(); // fecha o Scanner

            this.tamanho = count; // guarda a quantidade de restaurantes
            this.restaurantes = new Restaurante[this.tamanho]; // cria o vetor do tamanho certo

            sc = new Scanner(arquivo); // reabre o Scanner do arquivo

            if (sc.hasNextLine()) { // se houver cabecalho
                sc.nextLine(); // pula o cabecalho novamente
            }

            for (int i = 0; i < this.tamanho; i++) { // percorre todas as linhas de dados
                String linha = sc.nextLine(); // le a linha atual
                this.restaurantes[i] = Restaurante.parseRestaurante(linha); // transforma a linha em restaurante
            }

            sc.close(); // fecha o Scanner

        } catch (FileNotFoundException e) { // se der erro ao abrir o arquivo
            System.out.println("Erro ao abrir o arquivo."); // mostra mensagem
        }
    }

    // cria a colecao e le o arquivo padrao
    public static ColecaoRestaurantes lerCsv() {
        ColecaoRestaurantes colecao = new ColecaoRestaurantes(); // cria a colecao
        colecao.lerCsv("/tmp/restaurantes.csv"); // manda ler o arquivo padrao
        return colecao; // retorna a colecao pronta
    }

    // busca um restaurante pelo id
    public Restaurante buscarPorId(int id) {
        Restaurante resp = null; // começa em null

        for (int i = 0; i < this.tamanho; i++) { // percorre todos os restaurantes
            if (this.restaurantes[i].getId() == id) { // se o id atual for igual ao procurado
                resp = this.restaurantes[i]; // guarda o restaurante encontrado
                i = this.tamanho; // força a saida do laço
            }
        }

        return resp; // retorna o restaurante ou null
    }
}

// classe Lista
class Lista {
    private Restaurante[] array; // vetor da lista
    private int n; // quantidade de elementos na lista

    // construtor da lista
    public Lista(int tamanho) {
        this.array = new Restaurante[tamanho]; // cria o vetor com o tamanho pedido
        this.n = 0; // começa com zero elementos
    }

    // insere um restaurante no inicio
    public void inserirInicio(Restaurante restaurante) throws Exception {
        if (n >= array.length) { // se a lista estiver cheia
            throw new Exception("Erro ao inserir"); // lança excecao
        }

        for (int i = n; i > 0; i--) { // desloca os elementos para a direita
            array[i] = array[i - 1]; // copia o elemento anterior
        }

        array[0] = restaurante; // coloca o novo restaurante na primeira posicao
        n++; // aumenta a quantidade de elementos
    }

    // insere um restaurante no fim
    public void inserirFim(Restaurante restaurante) throws Exception {
        if (n >= array.length) { // se a lista estiver cheia
            throw new Exception("Erro ao inserir"); // lança excecao
        }

        array[n] = restaurante; // coloca o restaurante no fim
        n++; // aumenta a quantidade de elementos
    }

    // insere um restaurante em uma posicao especifica
    public void inserir(Restaurante restaurante, int pos) throws Exception {
        if (n >= array.length || pos < 0 || pos > n) { // se a posicao for invalida ou a lista estiver cheia
            throw new Exception("Erro ao inserir"); // lança excecao
        }

        for (int i = n; i > pos; i--) { // desloca os elementos para a direita
            array[i] = array[i - 1]; // copia o elemento anterior
        }

        array[pos] = restaurante; // coloca o restaurante na posicao desejada
        n++; // aumenta a quantidade de elementos
    }

    // remove e retorna o primeiro restaurante
    public Restaurante removerInicio() throws Exception {
        if (n == 0) { // se a lista estiver vazia
            throw new Exception("Erro ao remover"); // lança excecao
        }

        Restaurante resp = array[0]; // guarda o elemento que sera removido

        n--; // diminui a quantidade de elementos

        for (int i = 0; i < n; i++) { // desloca os elementos para a esquerda
            array[i] = array[i + 1]; // copia o proximo elemento
        }

        return resp; // retorna o restaurante removido
    }

    // remove e retorna o ultimo restaurante
    public Restaurante removerFim() throws Exception {
        if (n == 0) { // se a lista estiver vazia
            throw new Exception("Erro ao remover"); // lança excecao
        }

        return array[--n]; // diminui n e retorna o ultimo elemento
    }

    // remove e retorna o restaurante de uma posicao especifica
    public Restaurante remover(int pos) throws Exception {
        if (n == 0 || pos < 0 || pos >= n) { // se a lista estiver vazia ou a posicao for invalida
            throw new Exception("Erro ao remover"); // lança excecao
        }

        Restaurante resp = array[pos]; // guarda o elemento que sera removido

        n--; // diminui a quantidade de elementos

        for (int i = pos; i < n; i++) { // desloca os elementos para a esquerda
            array[i] = array[i + 1]; // copia o proximo elemento
        }

        return resp; // retorna o restaurante removido
    }

    // mostra a lista do primeiro ao ultimo
    public void mostrar() {
        for (int i = 0; i < n; i++) { // percorre a lista inteira
            System.out.println("[" + i + "] " + array[i].formatar()); // imprime a posicao e o restaurante
        }
    }
}

// classe principal
public class q11lista {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in); // cria Scanner para ler a entrada
        ColecaoRestaurantes colecao = ColecaoRestaurantes.lerCsv(); // le o csv completo
        Lista lista = new Lista(1000); // cria a lista com tamanho suficiente

        int id; // guarda o id lido da entrada
        int n; // guarda a quantidade de comandos
        String linha; // guarda cada linha de comando

        id = sc.nextInt(); // le o primeiro id

        while (id != -1) { // enquanto nao encontrar -1
            Restaurante restaurante = colecao.buscarPorId(id); // busca o restaurante pelo id

            if (restaurante != null) { // se encontrou
                lista.inserirFim(restaurante); // insere no fim da lista
            }

            id = sc.nextInt(); // le o proximo id
        }

        n = sc.nextInt(); // le a quantidade de comandos
        sc.nextLine(); // consome a quebra de linha pendente

        for (int i = 0; i < n; i++) { // percorre os n comandos
            linha = sc.nextLine(); // le a linha inteira do comando

            Scanner scLinha = new Scanner(linha); // cria Scanner para ler a linha do comando
            String comando = scLinha.next(); // le o comando

            if (comando.compareTo("II") == 0) { // se o comando for inserir no inicio
                int idComando = scLinha.nextInt(); // le o id do restaurante
                Restaurante restaurante = colecao.buscarPorId(idComando); // busca o restaurante pelo id
                lista.inserirInicio(restaurante); // insere no inicio
            } else if (comando.compareTo("IF") == 0) { // se o comando for inserir no fim
                int idComando = scLinha.nextInt(); // le o id do restaurante
                Restaurante restaurante = colecao.buscarPorId(idComando); // busca o restaurante pelo id
                lista.inserirFim(restaurante); // insere no fim
            } else if (comando.compareTo("I*") == 0) { // se o comando for inserir em posicao
                int pos = scLinha.nextInt(); // le a posicao
                int idComando = scLinha.nextInt(); // le o id do restaurante
                Restaurante restaurante = colecao.buscarPorId(idComando); // busca o restaurante pelo id
                lista.inserir(restaurante, pos); // insere na posicao informada
            } else if (comando.compareTo("RI") == 0) { // se o comando for remover no inicio
                Restaurante removido = lista.removerInicio(); // remove do inicio
                System.out.println("(R)" + removido.getNome()); // imprime o nome removido
            } else if (comando.compareTo("RF") == 0) { // se o comando for remover no fim
                Restaurante removido = lista.removerFim(); // remove do fim
                System.out.println("(R)" + removido.getNome()); // imprime o nome removido
            } else if (comando.compareTo("R*") == 0) { // se o comando for remover em posicao
                int pos = scLinha.nextInt(); // le a posicao
                Restaurante removido = lista.remover(pos); // remove da posicao informada
                System.out.println("(R)" + removido.getNome()); // imprime o nome removido
            }

            scLinha.close(); // fecha o scanner da linha
        }

        lista.mostrar(); // mostra a lista final

        sc.close(); 
    }
}
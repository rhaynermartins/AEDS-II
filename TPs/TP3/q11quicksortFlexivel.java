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

    // recebe uma string YYYY-MM-DD e cria uma Data
    public static Data parseData(String s) {
        Scanner sc = new Scanner(s); 
        sc.useDelimiter("-"); // define "-" como separador

        int ano = sc.nextInt(); // le ano
        int mes = sc.nextInt(); // le mes
        int dia = sc.nextInt(); // le dia

        sc.close(); // fecha Scanner

        return new Data(ano, mes, dia); // retorna data criada
    }

    // formata a data em DD/MM/YYYY
    public String formatar() {
        String diaString; // guarda dia formatado
        String mesString; // guarda mes formatado

        if (this.dia < 10) { // se dia tiver um digito
            diaString = "0" + this.dia; // coloca zero
        } else {
            diaString = "" + this.dia; // usa normal
        }

        if (this.mes < 10) { // se mes tiver um digito
            mesString = "0" + this.mes; // coloca zero
        } else {
            mesString = "" + this.mes; // usa normal
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

    // recebe HH:mm e cria Hora
    public static Hora parseHora(String s) {
        Scanner sc = new Scanner(s); // cria Scanner
        sc.useDelimiter(":"); // define ":" como separador

        int hora = sc.nextInt(); // le hora
        int minuto = sc.nextInt(); // le minuto

        sc.close(); // fecha Scanner

        return new Hora(hora, minuto); // retorna hora criada
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
    private String[] tiposCozinha; // guarda tipos
    private int faixaPreco; // guarda faixa
    private Hora horarioAbertura; // guarda abertura
    private Hora horarioFechamento; // guarda fechamento
    private Data dataAbertura; // guarda data
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

    // retorna avaliacao
    public double getAvaliacao() {
        return this.avaliacao; // devolve avaliacao
    }

    // converte faixa textual em inteiro
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

    // converte faixa inteira para texto
    public static String formatarFaixaPreco(int faixaPreco) {
        String resp = ""; // resposta

        for (int i = 0; i < faixaPreco; i++) { // repete pela faixa
            resp += "$"; // adiciona $
        }

        return resp; // retorna faixa formatada
    }

    // conta tipos separados por ;
    public static int contarTiposCozinha(String s) {
        Scanner sc = new Scanner(s); // cria Scanner
        sc.useDelimiter(";"); // define separador

        int count = 0; // contador

        while (sc.hasNext()) { // enquanto tiver tipo
            sc.next(); // le tipo
            count++; // aumenta contador
        }

        sc.close();

        return count; // retorna quantidade
    }

    // transforma tipos em vetor
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

    // transforma linha do csv em restaurante
    public static Restaurante parseRestaurante(String s) {
        Scanner sc = new Scanner(s); // cria Scanner da linha
        sc.useDelimiter(","); // define separador

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

        Scanner scHorario = new Scanner(horario); // cria Scanner do horario
        scHorario.useDelimiter("-"); // define separador

        String abertura = scHorario.next(); // le abertura
        String fechamento = scHorario.next(); // le fechamento

        scHorario.close(); // fecha Scanner

        Hora horarioAbertura = Hora.parseHora(abertura); // cria abertura
        Hora horarioFechamento = Hora.parseHora(fechamento); // cria fechamento
        Data dataAbertura = Data.parseData(data); // cria data

        return new Restaurante(id, nome, cidade, capacidade, avaliacao,
                tiposCozinha, faixaPreco, horarioAbertura,
                horarioFechamento, dataAbertura, aberto); // retorna restaurante
    }

    // formata tipos como [a,b]
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

    // formata restaurante
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
    private Restaurante[] restaurantes; // guarda vetor

    // construtor
    public ColecaoRestaurantes() {
        this.tamanho = 0; // inicia tamanho
        this.restaurantes = new Restaurante[0]; // inicia vetor
    }

    // le csv pelo caminho
    public void lerCsv(String path) {
        try {
            File arquivo = new File(path); // cria File
            Scanner sc = new Scanner(arquivo); // abre Scanner

            int count = 0; // contador

            if (sc.hasNextLine()) { // se tiver cabecalho
                sc.nextLine(); // pula cabecalho
            }

            while (sc.hasNextLine()) { // enquanto tiver linha
                sc.nextLine(); // le linha
                count++; // conta
            }

            sc.close(); 

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
        } catch (FileNotFoundException e) { // se nao achar arquivo
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

        for (int i = 0; i < this.tamanho; i++) { // percorre restaurantes
            if (this.restaurantes[i].getId() == id) { // se achou
                resp = this.restaurantes[i]; // salva
                i = this.tamanho; // encerra laco
            }
        }

        return resp; // retorna restaurante
    }
}

// celula dupla
class CelulaDupla {
    public Restaurante elemento; // guarda restaurante
    public CelulaDupla ant; // aponta anterior
    public CelulaDupla prox; // aponta proxima

    // construtor
    public CelulaDupla(Restaurante elemento) {
        this.elemento = elemento; // salva elemento
        this.ant = null; // inicia anterior
        this.prox = null; // inicia proxima
    }
}

// lista dupla
class ListaDupla {
    private CelulaDupla primeiro; // celula cabeca
    private CelulaDupla ultimo; // ultima celula
    private int tamanho; // tamanho da lista

    // construtor
    public ListaDupla() {
        this.primeiro = new CelulaDupla(null); // cria cabeca
        this.ultimo = this.primeiro; // ultimo começa na cabeca
        this.tamanho = 0; // tamanho zero
    }

    // insere no fim
    public void inserirFim(Restaurante restaurante) {
        CelulaDupla tmp = new CelulaDupla(restaurante); // cria celula

        tmp.ant = this.ultimo; // anterior da nova e ultimo
        this.ultimo.prox = tmp; // ultimo aponta para nova
        this.ultimo = tmp; // atualiza ultimo
        this.tamanho++; // aumenta tamanho
    }

    // retorna primeira celula real
    public CelulaDupla getPrimeiroReal() {
        return this.primeiro.prox; // retorna depois da cabeca
    }

    // retorna ultima celula
    public CelulaDupla getUltimo() {
        return this.ultimo; // retorna ultimo
    }

    // retorna tamanho
    public int getTamanho() {
        return this.tamanho; // retorna tamanho
    }

    // mostra lista
    public void mostrar() {
        CelulaDupla atual = this.primeiro.prox; // comeca no primeiro real

        while (atual != null) { // percorre lista
            System.out.println(atual.elemento.formatar()); // imprime restaurante
            atual = atual.prox; // avanca
        }
    }
}

// classe principal
public class q11quicksortFlexivel {
    public static final String MATRICULA = "898128"; // matricula

    // compara por avaliacao e desempata por nome
    public static int compararRestaurantes(Restaurante a, Restaurante b, long[] comparacoes) {
        comparacoes[0]++; // conta comparacao da avaliacao

        if (a.getAvaliacao() < b.getAvaliacao()) { // se avaliacao menor
            return -1; // a vem antes
        } else if (a.getAvaliacao() > b.getAvaliacao()) { // se avaliacao maior
            return 1; // a vem depois
        }

        comparacoes[0]++; // conta comparacao do nome

        return a.getNome().compareTo(b.getNome()); // desempata por nome
    }

    // troca elementos das celulas
    public static void swap(CelulaDupla a, CelulaDupla b, long[] movimentacoes) {
        Restaurante tmp = a.elemento; // guarda elemento de a
        a.elemento = b.elemento; // coloca b em a
        b.elemento = tmp; // coloca antigo a em b
        movimentacoes[0] += 3; // conta movimentacoes
    }

    // verifica se os ponteiros ainda estao no intervalo
    public static boolean antesOuIgual(CelulaDupla a, CelulaDupla b) {
        CelulaDupla atual = a; // comeca em a
        boolean resp = false; // resposta

        while (atual != null && resp == false) { // percorre ate achar b ou acabar
            if (atual == b) { // se encontrou b
                resp = true; // a vem antes ou igual
            }

            atual = atual.prox; // avanca
        }

        return resp; // retorna resposta
    }

    // particiona lista
    public static CelulaDupla particionar(CelulaDupla esq, CelulaDupla dir, long[] comparacoes, long[] movimentacoes) {
        Restaurante pivo = dir.elemento; // usa o ultimo elemento como pivo
        CelulaDupla i = esq.ant; // i comeca antes da esquerda

        for (CelulaDupla j = esq; j != dir; j = j.prox) { // percorre ate antes do pivo
            if (compararRestaurantes(j.elemento, pivo, comparacoes) <= 0) { // se j <= pivo
                if (i == null) { // se i estava antes da lista
                    i = esq; // i vira esquerda
                } else {
                    i = i.prox; // avanca i
                }

                swap(i, j, movimentacoes); // troca i e j
            }
        }

        if (i == null) { // se i ficou antes da lista
            i = esq; // i vira esquerda
        } else {
            i = i.prox; // avanca i
        }

        swap(i, dir, movimentacoes); // coloca pivo na posicao correta

        return i; // retorna posicao do pivo
    }

    // quicksort em lista dupla
    public static void quicksort(CelulaDupla esq, CelulaDupla dir, long[] comparacoes, long[] movimentacoes) {
        if (esq != null && dir != null && esq != dir && antesOuIgual(esq, dir)) { // se intervalo valido
            CelulaDupla pivo = particionar(esq, dir, comparacoes, movimentacoes); // particiona

            if (pivo != null && pivo.ant != null) { // se existe parte esquerda
                quicksort(esq, pivo.ant, comparacoes, movimentacoes); // ordena esquerda
            }

            if (pivo != null && pivo.prox != null) { // se existe parte direita
                quicksort(pivo.prox, dir, comparacoes, movimentacoes); // ordena direita
            }
        }
    }

    // cria log
    public static void criarLog(long comparacoes, long movimentacoes, double tempo) {
        try {
            PrintWriter pw = new PrintWriter(MATRICULA + "_quicksort_flexivel.txt"); // cria log
            pw.println(MATRICULA + "\t" + comparacoes + "\t" + movimentacoes + "\t" + tempo); // escreve log
            pw.close(); // fecha log
        } catch (FileNotFoundException e) { // se der erro
            System.out.println("Erro ao criar arquivo de log."); // imprime erro
        }
    }

    // metodo principal
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); // cria Scanner
        ColecaoRestaurantes colecao = ColecaoRestaurantes.lerCsv(); // le csv
        ListaDupla lista = new ListaDupla(); // cria lista

        int id = sc.nextInt(); // le primeiro id

        long[] comparacoes = new long[1]; // contador comparacoes
        long[] movimentacoes = new long[1]; // contador movimentacoes

        long inicio; // tempo inicio
        long fim; // tempo fim
        double tempo; // tempo total

        while (id != -1) { // enquanto nao for -1
            Restaurante restaurante = colecao.buscarPorId(id); // busca restaurante

            if (restaurante != null) { // se encontrou
                lista.inserirFim(restaurante); // insere no fim
            }

            id = sc.nextInt(); // le proximo id
        }

        inicio = System.currentTimeMillis(); // marca inicio

        if (lista.getTamanho() > 1) { // se tiver mais de um elemento
            quicksort(lista.getPrimeiroReal(), lista.getUltimo(), comparacoes, movimentacoes); // ordena
        }

        fim = System.currentTimeMillis(); // marca fim
        tempo = (fim - inicio) / 1000.0; // calcula tempo

        lista.mostrar(); // mostra lista

        criarLog(comparacoes[0], movimentacoes[0], tempo); // cria log

        sc.close(); 
    }
}
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

    // recebe uma string YYYY-MM-DD e cria uma Data
    public static Data parseData(String s) {
        Scanner sc = new Scanner(s); // cria Scanner para a string
        sc.useDelimiter("-"); // define "-" como separador

        int ano = sc.nextInt(); // le o ano
        int mes = sc.nextInt(); // le o mes
        int dia = sc.nextInt(); // le o dia

        sc.close(); // fecha Scanner

        return new Data(ano, mes, dia); // retorna data criada
    }

    // formata data em DD/MM/YYYY
    public String formatar() {
        String diaString = ""; // guarda dia formatado
        String mesString = ""; // guarda mes formatado

        if (this.dia < 10) { // se dia tiver um digito
            diaString = "0" + this.dia; // coloca zero
        } else {
            diaString = "" + this.dia; // usa dia normal
        }

        if (this.mes < 10) { // se mes tiver um digito
            mesString = "0" + this.mes; // coloca zero
        } else {
            mesString = "" + this.mes; // usa mes normal
        }

        return diaString + "/" + mesString + "/" + this.ano; // retorna data formatada
    }
}

// classe Hora
class Hora {
    private int hora; // guarda hora
    private int minuto; // guarda minuto

    // construtor da classe Hora
    public Hora(int hora, int minuto) {
        this.hora = hora; // salva hora
        this.minuto = minuto; // salva minuto
    }

    // retorna hora
    public int getHora() {
        return this.hora; // devolve hora
    }

    // retorna minuto
    public int getMinuto() {
        return this.minuto; // devolve minuto
    }

    // recebe string HH:mm e cria Hora
    public static Hora parseHora(String s) {
        Scanner sc = new Scanner(s); // cria Scanner
        sc.useDelimiter(":"); // define ":" como separador

        int hora = sc.nextInt(); // le hora
        int minuto = sc.nextInt(); // le minuto

        sc.close(); // fecha Scanner

        return new Hora(hora, minuto); // retorna hora criada
    }

    // formata hora em HH:mm
    public String formatar() {
        String horaString = ""; // guarda hora formatada
        String minutoString = ""; // guarda minuto formatado

        if (this.hora < 10) { // se hora tiver um digito
            horaString = "0" + this.hora; // coloca zero
        } else {
            horaString = "" + this.hora; // usa hora normal
        }

        if (this.minuto < 10) { // se minuto tiver um digito
            minutoString = "0" + this.minuto; // coloca zero
        } else {
            minutoString = "" + this.minuto; // usa minuto normal
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
    private Hora horarioAbertura; // guarda horario de abertura
    private Hora horarioFechamento; // guarda horario de fechamento
    private Data dataAbertura; // guarda data de abertura
    private boolean aberto; // guarda aberto

    // construtor da classe Restaurante
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

    // converte faixa textual para inteiro
    public static int parseFaixaPreco(String s) {
        int resp = 0; // guarda resposta

        if (s.compareTo("$") == 0) { // se for $
            resp = 1; // guarda 1
        } else if (s.compareTo("$$") == 0) { // se for $$
            resp = 2; // guarda 2
        } else if (s.compareTo("$$$") == 0) { // se for $$$
            resp = 3; // guarda 3
        } else if (s.compareTo("$$$$") == 0) { // se for $$$$
            resp = 4; // guarda 4
        }

        return resp; // retorna resposta
    }

    // converte faixa inteira para texto
    public static String formatarFaixaPreco(int faixaPreco) {
        String resp = ""; // cria string vazia

        for (int i = 0; i < faixaPreco; i++) { // repete pela faixa
            resp += "$"; // adiciona $
        }

        return resp; // retorna faixa
    }

    // conta tipos separados por ;
    public static int contarTiposCozinha(String s) {
        Scanner sc = new Scanner(s); // cria Scanner
        sc.useDelimiter(";"); // separador ;

        int count = 0; // contador

        while (sc.hasNext()) { // enquanto houver tipo
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
        sc.useDelimiter(";"); // separador ;

        for (int i = 0; i < n; i++) { // percorre tipos
            resp[i] = sc.next(); // salva tipo
        }

        sc.close(); 

        return resp; // retorna vetor
    }

    // transforma linha csv em restaurante
    public static Restaurante parseRestaurante(String s) {
        Scanner sc = new Scanner(s); // cria Scanner da linha
        sc.useDelimiter(","); // separador ,

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
        scHorario.useDelimiter("-"); // separador -

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

// colecao de restaurantes
class ColecaoRestaurantes {
    private int tamanho; // guarda tamanho
    private Restaurante[] restaurantes; // guarda restaurantes

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

            if (sc.hasNextLine()) { // se houver cabecalho
                sc.nextLine(); // pula cabecalho
            }

            while (sc.hasNextLine()) { // enquanto houver linha
                sc.nextLine(); // le linha
                count++; // aumenta contador
            }

            sc.close(); 

            this.tamanho = count; // salva tamanho
            this.restaurantes = new Restaurante[this.tamanho]; // cria vetor

            sc = new Scanner(arquivo); // reabre Scanner

            if (sc.hasNextLine()) { // se houver cabecalho
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

    // busca por id
    public Restaurante buscarPorId(int id) {
        Restaurante resp = null; // resposta inicial

        for (int i = 0; i < this.tamanho; i++) { // percorre restaurantes
            if (this.restaurantes[i].getId() == id) { // se encontrou
                resp = this.restaurantes[i]; // salva restaurante
                i = this.tamanho; // encerra laco
            }
        }

        return resp; // retorna restaurante
    }
}

// celula dupla
class CelulaDupla {
    public Restaurante elemento; // guarda restaurante
    public CelulaDupla ant; // aponta para anterior
    public CelulaDupla prox; // aponta para proxima

    // construtor
    public CelulaDupla(Restaurante elemento) {
        this.elemento = elemento; // salva elemento
        this.ant = null; // inicia anterior null
        this.prox = null; // inicia proxima null
    }
}

// lista duplamente encadeada
class ListaDupla {
    private CelulaDupla primeiro; // celula cabeca
    private CelulaDupla ultimo; // ultima celula
    private int tamanho; // tamanho da lista

    // construtor
    public ListaDupla() {
        this.primeiro = new CelulaDupla(null); // cria cabeca
        this.ultimo = this.primeiro; // ultimo comeca na cabeca
        this.tamanho = 0; // tamanho zero
    }

    // insere no inicio
    public void inserirInicio(Restaurante restaurante) {
        CelulaDupla tmp = new CelulaDupla(restaurante); // cria nova celula

        tmp.ant = this.primeiro; // anterior da nova e a cabeca
        tmp.prox = this.primeiro.prox; // nova aponta para antigo primeiro
        this.primeiro.prox = tmp; // cabeca aponta para nova

        if (this.ultimo == this.primeiro) { // se lista estava vazia
            this.ultimo = tmp; // ultimo vira nova
        } else {
            tmp.prox.ant = tmp; // antigo primeiro aponta de volta para nova
        }

        this.tamanho++; // aumenta tamanho
    }

    // insere no fim
    public void inserirFim(Restaurante restaurante) {
        CelulaDupla tmp = new CelulaDupla(restaurante); // cria nova celula

        tmp.ant = this.ultimo; // anterior da nova e o ultimo atual
        this.ultimo.prox = tmp; // ultimo atual aponta para nova
        this.ultimo = tmp; // ultimo vira nova
        this.tamanho++; // aumenta tamanho
    }

    // insere em posicao especifica
    public void inserir(Restaurante restaurante, int pos) {
        if (pos == 0) { // se for inicio
            inserirInicio(restaurante); // insere inicio
        } else if (pos == this.tamanho) { // se for fim
            inserirFim(restaurante); // insere fim
        } else if (pos > 0 && pos < this.tamanho) { // se for meio
            CelulaDupla anterior = this.primeiro; // comeca na cabeca

            for (int i = 0; i < pos; i++) { // anda ate anterior
                anterior = anterior.prox; // avanca
            }

            CelulaDupla tmp = new CelulaDupla(restaurante); // cria nova celula

            tmp.ant = anterior; // anterior da nova
            tmp.prox = anterior.prox; // proxima da nova
            anterior.prox.ant = tmp; // proxima aponta de volta para nova
            anterior.prox = tmp; // anterior aponta para nova
            this.tamanho++; // aumenta tamanho
        }
    }

    // remove do inicio
    public Restaurante removerInicio() {
        Restaurante resp = null; // guarda removido

        if (this.tamanho > 0) { // se nao estiver vazia
            CelulaDupla tmp = this.primeiro.prox; // pega primeira real
            resp = tmp.elemento; // guarda restaurante

            this.primeiro.prox = tmp.prox; // cabeca pula removida

            if (tmp == this.ultimo) { // se removeu unico elemento
                this.ultimo = this.primeiro; // ultimo volta para cabeca
            } else {
                tmp.prox.ant = this.primeiro; // novo primeiro aponta para cabeca
            }

            tmp.prox = null; // desconecta proxima
            tmp.ant = null; // desconecta anterior
            this.tamanho--; // diminui tamanho
        }

        return resp; // retorna removido
    }

    // remove do fim
    public Restaurante removerFim() {
        Restaurante resp = null; // guarda removido

        if (this.tamanho > 0) { // se nao estiver vazia
            CelulaDupla tmp = this.ultimo; // pega ultimo
            resp = tmp.elemento; // guarda elemento

            this.ultimo = this.ultimo.ant; // ultimo volta
            this.ultimo.prox = null; // novo ultimo aponta para null

            tmp.ant = null; // desconecta celula
            this.tamanho--; // diminui tamanho
        }

        return resp; // retorna removido
    }

    // remove de posicao especifica
    public Restaurante remover(int pos) {
        Restaurante resp = null; // guarda removido

        if (pos == 0) { // se inicio
            resp = removerInicio(); // remove inicio
        } else if (pos == this.tamanho - 1) { // se fim
            resp = removerFim(); // remove fim
        } else if (pos > 0 && pos < this.tamanho - 1) { // se meio
            CelulaDupla atual = this.primeiro.prox; // comeca no primeiro real

            for (int i = 0; i < pos; i++) { // anda ate posicao
                atual = atual.prox; // avanca
            }

            resp = atual.elemento; // guarda removido
            atual.ant.prox = atual.prox; // anterior pula atual
            atual.prox.ant = atual.ant; // proxima aponta para anterior

            atual.prox = null; // desconecta proxima
            atual.ant = null; // desconecta anterior
            this.tamanho--; // diminui tamanho
        }

        return resp; // retorna removido
    }

    // mostra lista
    public void mostrar() {
        CelulaDupla atual = this.primeiro.prox; // comeca no primeiro real
        int i = 0; // indice

        while (atual != null) { // percorre ate o fim
            System.out.println("[" + i + "] " + atual.elemento.formatar()); // imprime posicao
            atual = atual.prox; // avanca
            i++; // aumenta indice
        }
    }
}

// classe principal
public class q8listaDuplaFlexivel {
    // metodo principal
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); // cria Scanner
        ColecaoRestaurantes colecao = ColecaoRestaurantes.lerCsv(); // le csv

        ListaDupla lista = new ListaDupla(); // cria lista
        int id; // guarda id
        int quantidadeOperacoes; // guarda quantidade de operacoes
        int pos; // guarda posicao
        String comando; // guarda comando
        Restaurante restaurante; // guarda restaurante buscado
        Restaurante removido; // guarda restaurante removido

        id = sc.nextInt(); // le primeiro id

        while (id != -1) { // enquanto nao for -1
            restaurante = colecao.buscarPorId(id); // busca restaurante

            if (restaurante != null) { // se encontrou
                lista.inserirFim(restaurante); // insere no fim
            }

            id = sc.nextInt(); // le proximo id
        }

        quantidadeOperacoes = sc.nextInt(); // le quantidade de operacoes

        for (int i = 0; i < quantidadeOperacoes; i++) { // percorre operacoes
            comando = sc.next(); // le comando

            if (comando.compareTo("II") == 0) { // inserir inicio
                id = sc.nextInt(); // le id
                restaurante = colecao.buscarPorId(id); // busca restaurante

                if (restaurante != null) { // se encontrou
                    lista.inserirInicio(restaurante); // insere inicio
                }
            } else if (comando.compareTo("IF") == 0) { // inserir fim
                id = sc.nextInt(); // le id
                restaurante = colecao.buscarPorId(id); // busca restaurante

                if (restaurante != null) { // se encontrou
                    lista.inserirFim(restaurante); // insere fim
                }
            } else if (comando.compareTo("I*") == 0) { // inserir posicao
                pos = sc.nextInt(); // le posicao
                id = sc.nextInt(); // le id
                restaurante = colecao.buscarPorId(id); // busca restaurante

                if (restaurante != null) { // se encontrou
                    lista.inserir(restaurante, pos); // insere posicao
                }
            } else if (comando.compareTo("RI") == 0) { // remover inicio
                removido = lista.removerInicio(); // remove inicio

                if (removido != null) { // se removeu
                    System.out.println("(R)" + removido.getNome()); // imprime removido
                }
            } else if (comando.compareTo("RF") == 0) { // remover fim
                removido = lista.removerFim(); // remove fim

                if (removido != null) { // se removeu
                    System.out.println("(R)" + removido.getNome()); // imprime removido
                }
            } else if (comando.compareTo("R*") == 0) { // remover posicao
                pos = sc.nextInt(); // le posicao
                removido = lista.remover(pos); // remove posicao

                if (removido != null) { // se removeu
                    System.out.println("(R)" + removido.getNome()); // imprime removido
                }
            }
        }

        lista.mostrar(); // mostra lista final

        sc.close(); 
    }
}
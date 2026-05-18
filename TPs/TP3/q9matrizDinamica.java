import java.util.Scanner; 

// classe Celula
class Celula {
    public int elemento; // guarda o valor da celula
    public Celula esq; // aponta para a celula da esquerda
    public Celula dir; // aponta para a celula da direita
    public Celula sup; // aponta para a celula de cima
    public Celula inf; // aponta para a celula de baixo

    // construtor da celula
    public Celula(int elemento) {
        this.elemento = elemento; // salva o valor recebido
        this.esq = null; // inicia esquerda como null
        this.dir = null; // inicia direita como null
        this.sup = null; // inicia superior como null
        this.inf = null; // inicia inferior como null
    }
}

// classe auxiliar para guardar os numeros de uma linha
class LinhaNumeros {
    public int[] valores; // guarda os numeros da linha
    public int quantidade; // guarda quantos numeros existem na linha

    // construtor
    public LinhaNumeros(int[] valores, int quantidade) {
        this.valores = valores; // salva o vetor
        this.quantidade = quantidade; // salva a quantidade
    }
}

// classe Matriz
class Matriz {
    private Celula inicio; // aponta para a primeira celula da matriz
    private int linha; // guarda a quantidade de linhas
    private int coluna; // guarda a quantidade de colunas

    // construtor padrao
    public Matriz() {
        this(3, 3); // cria uma matriz 3x3 por padrao
    }

    // construtor com linhas e colunas
    public Matriz(int linha, int coluna) {
        this.linha = linha; // salva a quantidade de linhas
        this.coluna = coluna; // salva a quantidade de colunas
        construir(); // constroi a matriz flexivel
    }

    // constroi a matriz flexivel sem usar Celula[][]
    private void construir() {
        this.inicio = new Celula(0); // cria a primeira celula

        Celula lin = this.inicio; // aponta para o inicio da linha atual
        Celula col = this.inicio; // aponta para a celula atual

        for (int j = 1; j < this.coluna; j++) { // cria as demais celulas da primeira linha
            col.dir = new Celula(0); // cria uma celula a direita
            col.dir.esq = col; // liga a nova celula com a anterior
            col = col.dir; // avanca para a direita
        }

        for (int i = 1; i < this.linha; i++) { // cria as demais linhas
            lin.inf = new Celula(0); // cria a primeira celula da linha de baixo
            lin.inf.sup = lin; // liga essa nova celula com a de cima

            lin = lin.inf; // desce para a nova linha
            col = lin; // comeca no inicio da nova linha

            for (int j = 1; j < this.coluna; j++) { // cria as demais colunas da linha
                col.dir = new Celula(0); // cria uma celula a direita
                col.dir.esq = col; // liga com a celula da esquerda
                col = col.dir; // avanca para a direita

                col.sup = col.esq.sup.dir; // liga com a celula de cima
                col.sup.inf = col; // liga a celula de cima com a atual
            }
        }
    }

    // retorna quantidade de linhas
    public int getLinha() {
        return this.linha; // devolve linhas
    }

    // retorna quantidade de colunas
    public int getColuna() {
        return this.coluna; // devolve colunas
    }

    // verifica se a matriz e quadrada
    public boolean isQuadrada() {
        return this.linha == this.coluna; // retorna true se linha e coluna forem iguais
    }

    // retorna uma celula especifica
    private Celula getCelula(int linha, int coluna) {
        Celula atual = this.inicio; // comeca no inicio

        for (int i = 0; i < linha; i++) { // desce ate a linha desejada
            atual = atual.inf; // vai para baixo
        }

        for (int j = 0; j < coluna; j++) { // anda ate a coluna desejada
            atual = atual.dir; // vai para a direita
        }

        return atual; // retorna a celula
    }

    // altera o valor de uma celula
    public void setElemento(int linha, int coluna, int valor) {
        Celula celula = getCelula(linha, coluna); // pega a celula desejada
        celula.elemento = valor; // altera o valor
    }

    // retorna o valor de uma celula
    public int getElemento(int linha, int coluna) {
        Celula celula = getCelula(linha, coluna); // pega a celula desejada
        return celula.elemento; // retorna o valor
    }

    // preenche uma linha da matriz
    public void preencherLinha(int indiceLinha, int[] valores) {
        Celula atual = this.inicio; // comeca no inicio da matriz

        for (int i = 0; i < indiceLinha; i++) { // desce ate a linha desejada
            atual = atual.inf; // vai para baixo
        }

        for (int j = 0; j < this.coluna; j++) { // percorre as colunas
            atual.elemento = valores[j]; // guarda o valor na celula
            atual = atual.dir; // anda para a direita
        }
    }

    // soma duas matrizes
    public Matriz somar(Matriz m) {
        Matriz resp = null; // resposta comeca nula

        if (this.linha == m.linha && this.coluna == m.coluna) { // verifica se pode somar
            resp = new Matriz(this.linha, this.coluna); // cria matriz resposta

            Celula linhaA = this.inicio; // inicio da linha da primeira matriz
            Celula linhaB = m.inicio; // inicio da linha da segunda matriz
            Celula linhaC = resp.inicio; // inicio da linha da resposta

            for (int i = 0; i < this.linha; i++) { // percorre linhas
                Celula a = linhaA; // celula atual da primeira matriz
                Celula b = linhaB; // celula atual da segunda matriz
                Celula c = linhaC; // celula atual da resposta

                for (int j = 0; j < this.coluna; j++) { // percorre colunas
                    c.elemento = a.elemento + b.elemento; // soma os elementos
                    a = a.dir; // avanca na primeira matriz
                    b = b.dir; // avanca na segunda matriz
                    c = c.dir; // avanca na resposta
                }

                linhaA = linhaA.inf; // desce na primeira matriz
                linhaB = linhaB.inf; // desce na segunda matriz
                linhaC = linhaC.inf; // desce na resposta
            }
        }

        return resp; // retorna a soma
    }

    // multiplica duas matrizes
    public Matriz multiplicar(Matriz m) {
        Matriz resp = null; // resposta comeca nula

        if (this.coluna == m.linha) { // verifica se pode multiplicar
            resp = new Matriz(this.linha, m.coluna); // cria matriz resposta

            for (int i = 0; i < this.linha; i++) { // percorre linhas da primeira matriz
                for (int j = 0; j < m.coluna; j++) { // percorre colunas da segunda matriz
                    int soma = 0; // acumulador da multiplicacao

                    for (int k = 0; k < this.coluna; k++) { // percorre dimensao interna
                        soma += this.getElemento(i, k) * m.getElemento(k, j); // multiplica e soma
                    }

                    resp.setElemento(i, j, soma); // salva resultado
                }
            }
        }

        return resp; // retorna a multiplicacao
    }

    // retorna a diagonal principal como String
    public String diagonalPrincipal() {
        String resp = ""; // cria uma String vazia

        if (isQuadrada()) { // so monta se for quadrada
            Celula atual = this.inicio; // comeca na primeira celula

            for (int i = 0; i < this.linha; i++) { // percorre a diagonal
                resp += atual.elemento + " "; // adiciona elemento com espaco

                if (i < this.linha - 1) { // se ainda tem proximo
                    atual = atual.dir.inf; // anda para direita e para baixo
                }
            }

            resp += "\n"; // quebra linha
        }

        return resp; // retorna a diagonal
    }

    // retorna a diagonal secundaria como String
    public String diagonalSecundaria() {
        String resp = ""; // cria uma String vazia

        if (isQuadrada()) { // so monta se for quadrada
            Celula atual = this.inicio; // comeca na primeira celula

            for (int j = 1; j < this.coluna; j++) { // vai ate a ultima coluna
                atual = atual.dir; // anda para a direita
            }

            for (int i = 0; i < this.linha; i++) { // percorre a diagonal
                resp += atual.elemento + " "; // adiciona elemento com espaco

                if (i < this.linha - 1) { // se ainda tem proximo
                    atual = atual.esq.inf; // anda para esquerda e para baixo
                }
            }

            resp += "\n"; // quebra linha
        }

        return resp; // retorna a diagonal
    }

    // mostra diagonal principal diretamente
    public void mostrarDiagonalPrincipal() {
        System.out.print(diagonalPrincipal()); // imprime a diagonal principal
    }

    // mostra diagonal secundaria diretamente
    public void mostrarDiagonalSecundaria() {
        System.out.print(diagonalSecundaria()); // imprime a diagonal secundaria
    }

    // retorna a matriz completa como String
    public String matrizComoString() {
        String resp = ""; // cria uma String vazia
        Celula inicioLinha = this.inicio; // aponta para o inicio da linha atual

        for (int i = 0; i < this.linha; i++) { // percorre linhas
            Celula atual = inicioLinha; // comeca na primeira celula da linha

            for (int j = 0; j < this.coluna; j++) { // percorre colunas
                resp += atual.elemento + " "; // adiciona elemento com espaco
                atual = atual.dir; // anda para direita
            }

            resp += "\n"; // quebra linha
            inicioLinha = inicioLinha.inf; // desce para proxima linha
        }

        return resp; // retorna a matriz formatada
    }

    // mostra matriz diretamente
    public void mostrar() {
        System.out.print(matrizComoString()); // imprime a matriz
    }
}

// classe Resultado
class Resultado {
    public String saida; // guarda a saida
    public int pos; // guarda a posicao final
    public boolean ok; // guarda se deu certo

    // construtor
    public Resultado(String saida, int pos, boolean ok) {
        this.saida = saida; // salva a saida
        this.pos = pos; // salva a posicao
        this.ok = ok; // salva o status
    }
}

// classe principal
public class q9matrizDinamica {

    // converte uma linha em numeros inteiros
    public static LinhaNumeros converterLinha(String linha) {
        Scanner scLinha = new Scanner(linha); // cria Scanner da linha
        int[] valores = new int[1000]; // vetor temporario
        int quantidade = 0; // quantidade de numeros

        while (scLinha.hasNextInt()) { // enquanto houver inteiro
            valores[quantidade] = scLinha.nextInt(); // guarda inteiro
            quantidade++; // aumenta quantidade
        }

        scLinha.close(); // fecha Scanner da linha

        int[] exato = new int[quantidade]; // cria vetor exato

        for (int i = 0; i < quantidade; i++) { // copia valores
            exato[i] = valores[i]; // copia valor atual
        }

        return new LinhaNumeros(exato, quantidade); // retorna linha convertida
    }

    // tenta ler uma matriz a partir das linhas
    public static Matriz lerMatriz(String[] linhas, int pos, int qtdLinhas, int qtdColunas) {
        Matriz matriz = null; // matriz inicial

        if (qtdLinhas > 0 && qtdColunas > 0 && pos + qtdLinhas <= linhas.length) { // verifica limites
            matriz = new Matriz(qtdLinhas, qtdColunas); // cria matriz
            boolean valido = true; // controla se as linhas sao validas

            for (int i = 0; i < qtdLinhas; i++) { // percorre as linhas da matriz
                LinhaNumeros linhaAtual = converterLinha(linhas[pos + i]); // converte linha

                if (linhaAtual.quantidade != qtdColunas) { // se quantidade nao bate
                    valido = false; // marca invalido
                    i = qtdLinhas; // encerra laco
                } else {
                    matriz.preencherLinha(i, linhaAtual.valores); // preenche linha
                }
            }

            if (!valido) { // se nao foi valido
                matriz = null; // descarta matriz
            }
        }

        return matriz; // retorna matriz ou null
    }

    // resolve todos os casos tentando os dois formatos
    public static Resultado resolver(String[] linhas, int pos, int casoAtual, int totalCasos) {
        if (casoAtual == totalCasos) { // se resolveu todos os casos
            if (pos == linhas.length) { // se consumiu todas as linhas
                return new Resultado("", pos, true); // sucesso
            }

            return new Resultado("", pos, false); // falha por sobra
        }

        if (pos >= linhas.length) { // se acabou entrada
            return new Resultado("", pos, false); // falha
        }

        LinhaNumeros dimensao1 = converterLinha(linhas[pos]); // le dimensoes da primeira matriz

        if (dimensao1.quantidade != 2) { // dimensao precisa ter dois numeros
            return new Resultado("", pos, false); // falha
        }

        int l1 = dimensao1.valores[0]; // linhas da primeira matriz
        int c1 = dimensao1.valores[1]; // colunas da primeira matriz

        Matriz m1 = lerMatriz(linhas, pos + 1, l1, c1); // le primeira matriz

        if (m1 == null) { // se nao conseguiu ler
            return new Resultado("", pos, false); // falha
        }

        int posDepoisM1 = pos + 1 + l1; // posicao depois da primeira matriz

        Resultado comDim = tentarComDimensaoSegunda(linhas, posDepoisM1, casoAtual, totalCasos, m1); // tenta com dimensao propria

        if (comDim.ok) { // se deu certo
            return comDim; // retorna
        }

        Resultado semDim = tentarSemDimensaoSegunda(linhas, posDepoisM1, casoAtual, totalCasos, m1); // tenta sem dimensao

        if (semDim.ok) { // se deu certo
            return semDim; // retorna
        }

        return new Resultado("", pos, false); // falha geral
    }

    // tenta segunda matriz com dimensoes proprias
    public static Resultado tentarComDimensaoSegunda(String[] linhas, int pos, int casoAtual, int totalCasos, Matriz m1) {
        if (pos >= linhas.length) { // se acabou entrada
            return new Resultado("", pos, false); // falha
        }

        LinhaNumeros dimensao2 = converterLinha(linhas[pos]); // tenta ler dimensoes da segunda matriz

        if (dimensao2.quantidade != 2) { // precisa ter dois numeros
            return new Resultado("", pos, false); // falha
        }

        int l2 = dimensao2.valores[0]; // linhas da segunda matriz
        int c2 = dimensao2.valores[1]; // colunas da segunda matriz

        Matriz m2 = lerMatriz(linhas, pos + 1, l2, c2); // tenta ler segunda matriz

        if (m2 == null) { // se nao conseguiu
            return new Resultado("", pos, false); // falha
        }

        int novaPos = pos + 1 + l2; // posicao depois da segunda matriz

        Resultado resto = resolver(linhas, novaPos, casoAtual + 1, totalCasos); // resolve proximos casos

        if (!resto.ok) { // se resto falhou
            return new Resultado("", pos, false); // falha
        }

        String saidaAtual = montarSaida(m1, m2); // monta saida atual

        return new Resultado(saidaAtual + resto.saida, resto.pos, true); // retorna sucesso
    }

    // tenta segunda matriz com mesmas dimensoes da primeira
    public static Resultado tentarSemDimensaoSegunda(String[] linhas, int pos, int casoAtual, int totalCasos, Matriz m1) {
        int l2 = m1.getLinha(); // linhas iguais as da primeira
        int c2 = m1.getColuna(); // colunas iguais as da primeira

        Matriz m2 = lerMatriz(linhas, pos, l2, c2); // tenta ler segunda matriz

        if (m2 == null) { // se nao conseguiu
            return new Resultado("", pos, false); // falha
        }

        int novaPos = pos + l2; // posicao depois da segunda matriz

        Resultado resto = resolver(linhas, novaPos, casoAtual + 1, totalCasos); // resolve proximos casos

        if (!resto.ok) { // se resto falhou
            return new Resultado("", pos, false); // falha
        }

        String saidaAtual = montarSaida(m1, m2); // monta saida atual

        return new Resultado(saidaAtual + resto.saida, resto.pos, true); // retorna sucesso
    }

    // monta a saida de um caso
    public static String montarSaida(Matriz m1, Matriz m2) {
        String saida = ""; // cria saida vazia

        saida += m1.diagonalPrincipal(); // adiciona diagonal principal
        saida += m2.diagonalSecundaria(); // adiciona diagonal secundaria

        Matriz soma = m1.somar(m2); // calcula soma

        if (soma != null) { // se soma existe
            saida += soma.matrizComoString(); // adiciona soma
        }

        Matriz multiplicacao = m1.multiplicar(m2); // calcula multiplicacao

        if (multiplicacao != null) { // se multiplicacao existe
            saida += multiplicacao.matrizComoString(); // adiciona multiplicacao
        }

        return saida; // retorna saida
    }

    // metodo principal
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); // cria Scanner da entrada
        String[] temporario = new String[200000]; // vetor temporario de linhas
        int quantidade = 0; // quantidade de linhas uteis

        while (sc.hasNextLine()) { // enquanto houver linha
            String linha = sc.nextLine(); // le linha
            linha = linha.trim(); // remove espacos das pontas

            if (linha.length() > 0) { // se nao for vazia
                temporario[quantidade] = linha; // guarda linha
                quantidade++; // aumenta quantidade
            }
        }

        String[] linhas = new String[quantidade]; // cria vetor exato

        for (int i = 0; i < quantidade; i++) { // copia linhas
            linhas[i] = temporario[i]; // copia linha atual
        }

        if (quantidade > 0) { // se tem entrada
            LinhaNumeros primeira = converterLinha(linhas[0]); // converte primeira linha

            if (primeira.quantidade > 0) { // se conseguiu ler casos
                int casos = primeira.valores[0]; // quantidade de casos

                Resultado resultado = resolver(linhas, 1, 0, casos); // resolve entrada

                if (resultado.ok) { // se deu certo
                    System.out.print(resultado.saida); // imprime saida
                }
            }
        }

        sc.close(); 
    }
}
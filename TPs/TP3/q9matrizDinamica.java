import java.util.Scanner; 

// classe Celula
class Celula {
    public int elemento; // guarda o valor inteiro da celula
    public Celula sup; // aponta para a celula de cima
    public Celula inf; // aponta para a celula de baixo
    public Celula esq; // aponta para a celula da esquerda
    public Celula dir; // aponta para a celula da direita

    // construtor padrao
    public Celula() {
        this(0); // chama o construtor com valor 0
    }

    // construtor com elemento
    public Celula(int elemento) {
        this.elemento = elemento; // salva o elemento
        this.sup = null; // inicia ponteiro superior como null
        this.inf = null; // inicia ponteiro inferior como null
        this.esq = null; // inicia ponteiro esquerdo como null
        this.dir = null; // inicia ponteiro direito como null
    }
}

// classe Matriz
class Matriz {
    private Celula inicio; // aponta para a primeira celula da matriz
    private int linha; // guarda a quantidade de linhas
    private int coluna; // guarda a quantidade de colunas

    // construtor da matriz
    public Matriz(int linha, int coluna) {
        this.linha = linha; // salva a quantidade de linhas
        this.coluna = coluna; // salva a quantidade de colunas
        construirMatriz(); // cria e conecta as celulas
    }

    // constroi a matriz dinamica com celulas ligadas
    private void construirMatriz() {
        Celula[][] temporaria = new Celula[this.linha][this.coluna]; // vetor auxiliar apenas para conectar as celulas

        for (int i = 0; i < this.linha; i++) { // percorre as linhas
            for (int j = 0; j < this.coluna; j++) { // percorre as colunas
                temporaria[i][j] = new Celula(); // cria uma nova celula
            }
        }

        for (int i = 0; i < this.linha; i++) { // percorre as linhas
            for (int j = 0; j < this.coluna; j++) { // percorre as colunas

                if (i > 0) { // se existe linha acima
                    temporaria[i][j].sup = temporaria[i - 1][j]; // liga com a celula de cima
                }

                if (i < this.linha - 1) { // se existe linha abaixo
                    temporaria[i][j].inf = temporaria[i + 1][j]; // liga com a celula de baixo
                }

                if (j > 0) { // se existe coluna a esquerda
                    temporaria[i][j].esq = temporaria[i][j - 1]; // liga com a celula da esquerda
                }

                if (j < this.coluna - 1) { // se existe coluna a direita
                    temporaria[i][j].dir = temporaria[i][j + 1]; // liga com a celula da direita
                }
            }
        }

        this.inicio = temporaria[0][0]; // guarda a primeira celula da matriz
    }

    // retorna a quantidade de linhas
    public int getLinha() {
        return this.linha; // devolve linha
    }

    // retorna a quantidade de colunas
    public int getColuna() {
        return this.coluna; // devolve coluna
    }

    // retorna a celula da posicao indicada
    private Celula getCelula(int linha, int coluna) {
        Celula atual = this.inicio; // comeca na primeira celula

        for (int i = 0; i < linha; i++) { // desce ate a linha desejada
            atual = atual.inf; // vai para baixo
        }

        for (int j = 0; j < coluna; j++) { // anda ate a coluna desejada
            atual = atual.dir; // vai para a direita
        }

        return atual; // retorna a celula encontrada
    }

    // altera o valor de uma posicao
    public void setElemento(int linha, int coluna, int valor) {
        Celula celula = getCelula(linha, coluna); // pega a celula desejada
        celula.elemento = valor; // altera o valor da celula
    }

    // retorna o valor de uma posicao
    public int getElemento(int linha, int coluna) {
        Celula celula = getCelula(linha, coluna); // pega a celula desejada
        return celula.elemento; // retorna o valor da celula
    }

    // le os elementos da matriz
    public void ler(Scanner sc) {
        Celula inicioLinha = this.inicio; // aponta para o inicio da linha atual

        for (int i = 0; i < this.linha; i++) { // percorre as linhas
            Celula atual = inicioLinha; // comeca na primeira celula da linha

            for (int j = 0; j < this.coluna; j++) { // percorre as colunas
                atual.elemento = sc.nextInt(); // le e guarda o elemento
                atual = atual.dir; // avanca para a direita
            }

            inicioLinha = inicioLinha.inf; // desce para a proxima linha
        }
    }

    // mostra a diagonal principal
    public void mostrarDiagonalPrincipal() {
        Celula atual = this.inicio; // comeca na primeira celula
        int limite = this.linha; // define o limite inicial

        if (this.coluna < limite) { // se colunas for menor
            limite = this.coluna; // usa colunas como limite
        }

        for (int i = 0; i < limite; i++) { // percorre a diagonal
            if (i > 0) { // se nao for o primeiro elemento
                System.out.print(" "); // imprime espaco antes
            }

            System.out.print(atual.elemento); // imprime o elemento atual

            if (atual.inf != null && atual.inf.dir != null) { // se existe proximo na diagonal
                atual = atual.inf.dir; // vai uma linha abaixo e uma coluna a direita
            }
        }

        System.out.println(); // quebra linha no final
    }

    // mostra a diagonal secundaria
    public void mostrarDiagonalSecundaria() {
        Celula atual = this.inicio; // comeca na primeira celula
        int limite = this.linha; // define o limite inicial

        if (this.coluna < limite) { // se colunas for menor
            limite = this.coluna; // usa colunas como limite
        }

        for (int j = 0; j < this.coluna - 1; j++) { // vai ate a ultima coluna da primeira linha
            atual = atual.dir; // anda para a direita
        }

        for (int i = 0; i < limite; i++) { // percorre a diagonal secundaria
            if (i > 0) { // se nao for o primeiro elemento
                System.out.print(" "); // imprime espaco antes
            }

            System.out.print(atual.elemento); // imprime o elemento atual

            if (atual.inf != null && atual.inf.esq != null) { // se existe proximo na diagonal secundaria
                atual = atual.inf.esq; // vai uma linha abaixo e uma coluna a esquerda
            }
        }

        System.out.println(); // quebra linha no final
    }

    // soma duas matrizes
    public Matriz somar(Matriz m) {
        Matriz resp = new Matriz(this.linha, this.coluna); // cria matriz resposta

        Celula linhaA = this.inicio; // inicio da linha da primeira matriz
        Celula linhaB = m.inicio; // inicio da linha da segunda matriz
        Celula linhaR = resp.inicio; // inicio da linha da resposta

        for (int i = 0; i < this.linha; i++) { // percorre as linhas
            Celula atualA = linhaA; // celula atual da primeira matriz
            Celula atualB = linhaB; // celula atual da segunda matriz
            Celula atualR = linhaR; // celula atual da resposta

            for (int j = 0; j < this.coluna; j++) { // percorre as colunas
                atualR.elemento = atualA.elemento + atualB.elemento; // soma os elementos
                atualA = atualA.dir; // avanca na primeira matriz
                atualB = atualB.dir; // avanca na segunda matriz
                atualR = atualR.dir; // avanca na resposta
            }

            linhaA = linhaA.inf; // desce linha da primeira matriz
            linhaB = linhaB.inf; // desce linha da segunda matriz
            linhaR = linhaR.inf; // desce linha da resposta
        }

        return resp; // retorna a matriz somada
    }

    // multiplica duas matrizes
    public Matriz multiplicar(Matriz m) {
        Matriz resp = new Matriz(this.linha, m.coluna); // cria matriz resposta

        for (int i = 0; i < this.linha; i++) { // percorre as linhas da primeira matriz
            for (int j = 0; j < m.coluna; j++) { // percorre as colunas da segunda matriz
                int soma = 0; // acumulador da multiplicacao

                for (int k = 0; k < this.coluna; k++) { // percorre colunas/linhas internas
                    soma += this.getElemento(i, k) * m.getElemento(k, j); // multiplica e soma
                }

                resp.setElemento(i, j, soma); // guarda resultado
            }
        }

        return resp; // retorna matriz multiplicada
    }

    // mostra a matriz completa
    public void mostrar() {
        Celula inicioLinha = this.inicio; // aponta para o inicio da linha atual

        for (int i = 0; i < this.linha; i++) { // percorre as linhas
            Celula atual = inicioLinha; // comeca na primeira celula da linha

            for (int j = 0; j < this.coluna; j++) { // percorre as colunas
                if (j > 0) { // se nao for a primeira coluna
                    System.out.print(" "); // imprime espaco antes
                }

                System.out.print(atual.elemento); // imprime elemento atual
                atual = atual.dir; // avanca para direita
            }

            System.out.println(); // quebra linha no final da linha
            inicioLinha = inicioLinha.inf; // desce para a proxima linha
        }
    }
}

// classe principal
public class q9matrizDinamica {
    // metodo principal
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); // cria Scanner para entrada

        int casos = sc.nextInt(); // le quantidade de casos

        for (int caso = 0; caso < casos; caso++) { // percorre os casos
            int l1 = sc.nextInt(); // le linhas da primeira matriz
            int c1 = sc.nextInt(); // le colunas da primeira matriz

            Matriz matriz1 = new Matriz(l1, c1); // cria primeira matriz
            matriz1.ler(sc); // le elementos da primeira matriz

            int l2 = sc.nextInt(); // le linhas da segunda matriz
            int c2 = sc.nextInt(); // le colunas da segunda matriz

            Matriz matriz2 = new Matriz(l2, c2); // cria segunda matriz
            matriz2.ler(sc); // le elementos da segunda matriz

            matriz1.mostrarDiagonalPrincipal(); // mostra diagonal principal da primeira matriz
            matriz2.mostrarDiagonalSecundaria(); // mostra diagonal secundaria da segunda matriz

            Matriz soma = matriz1.somar(matriz2); // soma as matrizes
            soma.mostrar(); // mostra matriz soma

            Matriz multiplicacao = matriz1.multiplicar(matriz2); // multiplica as matrizes
            multiplicacao.mostrar(); // mostra matriz multiplicacao
        }

        sc.close(); 
    }
}
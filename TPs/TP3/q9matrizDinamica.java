import java.util.Scanner;
import java.util.Arrays;

public class q9matrizDinamica {

    static class Celula {
        int elemento;
        Celula esq, dir, sup, inf;

        Celula(int elemento) {
            this.elemento = elemento;
            this.esq = null;
            this.dir = null;
            this.sup = null;
            this.inf = null;
        }
    }

    static class Matriz {
        private Celula inicio;
        private int linha, coluna;

        public Matriz() {
            this(3, 3);
        }

        public Matriz(int linha, int coluna) {
            this.linha = linha;
            this.coluna = coluna;
            construir();
        }

        private void construir() {
            inicio = new Celula(0);

            Celula primeiraDaLinha = inicio;
            Celula atual = inicio;

            for (int j = 1; j < coluna; j++) {
                atual.dir = new Celula(0);
                atual.dir.esq = atual;
                atual = atual.dir;
            }

            for (int i = 1; i < linha; i++) {
                primeiraDaLinha.inf = new Celula(0);
                primeiraDaLinha.inf.sup = primeiraDaLinha;

                Celula acima = primeiraDaLinha;
                primeiraDaLinha = primeiraDaLinha.inf;
                atual = primeiraDaLinha;

                for (int j = 1; j < coluna; j++) {
                    atual.dir = new Celula(0);
                    atual.dir.esq = atual;
                    atual = atual.dir;

                    acima = acima.dir;
                    atual.sup = acima;
                    acima.inf = atual;
                }
            }
        }

        public boolean isQuadrada() {
            return linha == coluna;
        }

        public void preencher(int[] dados, int pos) {
            Celula inicioLinha = inicio;

            for (int i = 0; i < linha; i++) {
                Celula atual = inicioLinha;

                for (int j = 0; j < coluna; j++) {
                    atual.elemento = dados[pos++];
                    atual = atual.dir;
                }

                inicioLinha = inicioLinha.inf;
            }
        }

        public Matriz somar(Matriz m) {
            Matriz resp = null;

            if (m != null && linha == m.linha && coluna == m.coluna) {
                resp = new Matriz(linha, coluna);

                Celula linhaA = inicio;
                Celula linhaB = m.inicio;
                Celula linhaR = resp.inicio;

                for (int i = 0; i < linha; i++) {
                    Celula a = linhaA;
                    Celula b = linhaB;
                    Celula r = linhaR;

                    for (int j = 0; j < coluna; j++) {
                        r.elemento = a.elemento + b.elemento;

                        a = a.dir;
                        b = b.dir;
                        r = r.dir;
                    }

                    linhaA = linhaA.inf;
                    linhaB = linhaB.inf;
                    linhaR = linhaR.inf;
                }
            }

            return resp;
        }

        public Matriz soma(Matriz m) {
            return somar(m);
        }

        public Matriz multiplicar(Matriz m) {
            Matriz resp = null;

            if (m != null && coluna == m.linha) {
                resp = new Matriz(linha, m.coluna);

                Celula linhaA = inicio;
                Celula linhaR = resp.inicio;

                for (int i = 0; i < linha; i++) {
                    Celula r = linhaR;
                    Celula colunaB = m.inicio;

                    for (int j = 0; j < m.coluna; j++) {
                        int soma = 0;

                        Celula a = linhaA;
                        Celula b = colunaB;

                        for (int k = 0; k < coluna; k++) {
                            soma += a.elemento * b.elemento;

                            a = a.dir;
                            b = b.inf;
                        }

                        r.elemento = soma;
                        r = r.dir;
                        colunaB = colunaB.dir;
                    }

                    linhaA = linhaA.inf;
                    linhaR = linhaR.inf;
                }
            }

            return resp;
        }

        public Matriz multiplicacao(Matriz m) {
            return multiplicar(m);
        }

        public void mostrarDiagonalPrincipal() {
            if (isQuadrada()) {
                Celula atual = inicio;

                for (int i = 0; i < linha; i++) {
                    if (i > 0) {
                        System.out.print(" ");
                    }

                    System.out.print(atual.elemento);
                    atual = atual.inf != null ? atual.inf.dir : null;
                }

                System.out.println();
            }
        }

        public void mostrarDiagonalSecundaria() {
            if (isQuadrada()) {
                Celula atual = inicio;

                while (atual.dir != null) {
                    atual = atual.dir;
                }

                for (int i = 0; i < linha; i++) {
                    if (i > 0) {
                        System.out.print(" ");
                    }

                    System.out.print(atual.elemento);
                    atual = atual.inf != null ? atual.inf.esq : null;
                }

                System.out.println();
            }
        }

        public void mostrar() {
            Celula inicioLinha = inicio;

            for (int i = 0; i < linha; i++) {
                Celula atual = inicioLinha;

                for (int j = 0; j < coluna; j++) {
                    if (j > 0) {
                        System.out.print(" ");
                    }

                    System.out.print(atual.elemento);
                    atual = atual.dir;
                }

                System.out.println();
                inicioLinha = inicioLinha.inf;
            }
        }

        public void adicionarDiagonalPrincipal(StringBuilder saida) {
            if (isQuadrada()) {
                Celula atual = inicio;

                for (int i = 0; i < linha; i++) {
                    if (i > 0) {
                        saida.append(' ');
                    }

                    saida.append(atual.elemento);
                    atual = atual.inf != null ? atual.inf.dir : null;
                }

                saida.append('\n');
            }
        }

        public void adicionarDiagonalSecundaria(StringBuilder saida) {
            if (isQuadrada()) {
                Celula atual = inicio;

                while (atual.dir != null) {
                    atual = atual.dir;
                }

                for (int i = 0; i < linha; i++) {
                    if (i > 0) {
                        saida.append(' ');
                    }

                    saida.append(atual.elemento);
                    atual = atual.inf != null ? atual.inf.esq : null;
                }

                saida.append('\n');
            }
        }

        public void adicionarMatriz(StringBuilder saida) {
            Celula inicioLinha = inicio;

            for (int i = 0; i < linha; i++) {
                Celula atual = inicioLinha;

                for (int j = 0; j < coluna; j++) {
                    if (j > 0) {
                        saida.append(' ');
                    }

                    saida.append(atual.elemento);
                    atual = atual.dir;
                }

                saida.append('\n');
                inicioLinha = inicioLinha.inf;
            }
        }
    }

    static int[] dados;
    static int total;
    static int casos;
    static byte[][] memo;

    static int tamanho(int l, int c) {
        long resp = (long) l * c;
        return resp > Integer.MAX_VALUE ? -1 : (int) resp;
    }

    static boolean podeLer(int pos, int caso) {
        if (caso == casos) {
            return pos == total;
        }

        if (pos + 2 > total) {
            return false;
        }

        if (memo[caso][pos] != 0) {
            return memo[caso][pos] == 1;
        }

        int l = dados[pos];
        int c = dados[pos + 1];
        int tam = tamanho(l, c);

        if (l <= 0 || c <= 0 || tam < 0 || pos + 2 + tam > total) {
            memo[caso][pos] = 2;
            return false;
        }

        int aposPrimeira = pos + 2 + tam;
        boolean ok = false;

        if (aposPrimeira + tam <= total && podeLer(aposPrimeira + tam, caso + 1)) {
            ok = true;
        }

        if (!ok && aposPrimeira + 2 <= total) {
            int l2 = dados[aposPrimeira];
            int c2 = dados[aposPrimeira + 1];
            int tam2 = tamanho(l2, c2);

            if (l2 > 0 && c2 > 0 && tam2 >= 0 && aposPrimeira + 2 + tam2 <= total) {
                ok = podeLer(aposPrimeira + 2 + tam2, caso + 1);
            }
        }

        memo[caso][pos] = ok ? (byte) 1 : (byte) 2;
        return ok;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        dados = new int[1024];
        total = 0;

        while (sc.hasNextInt()) {
            if (total == dados.length) {
                dados = Arrays.copyOf(dados, dados.length * 2);
            }

            dados[total++] = sc.nextInt();
        }

        sc.close();

        if (total == 0) {
            return;
        }

        casos = dados[0];
        memo = new byte[casos + 1][total + 1];

        StringBuilder saida = new StringBuilder();
        int pos = 1;

        for (int caso = 0; caso < casos; caso++) {
            int l1 = dados[pos++];
            int c1 = dados[pos++];
            int tam1 = l1 * c1;

            Matriz m1 = new Matriz(l1, c1);
            m1.preencher(dados, pos);
            pos += tam1;

            int l2 = l1;
            int c2 = c1;
            int inicioM2 = pos;

            if (!podeLer(pos + tam1, caso + 1)) {
                l2 = dados[pos++];
                c2 = dados[pos++];
                inicioM2 = pos;
            }

            int tam2 = l2 * c2;

            Matriz m2 = new Matriz(l2, c2);
            m2.preencher(dados, inicioM2);
            pos = inicioM2 + tam2;

            m1.adicionarDiagonalPrincipal(saida);
            m2.adicionarDiagonalSecundaria(saida);

            Matriz soma = m1.somar(m2);
            if (soma != null) {
                soma.adicionarMatriz(saida);
            }

            Matriz multiplicacao = m1.multiplicar(m2);
            if (multiplicacao != null) {
                multiplicacao.adicionarMatriz(saida);
            }
        }

        System.out.print(saida.toString());
    }
}
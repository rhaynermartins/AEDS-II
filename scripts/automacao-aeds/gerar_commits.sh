#!/bin/bash

# Caminho do repositório
REPO="/Users/macintosh/Documents/PUC/AEDS-II"

# Arquivo que pode ser alterado
ARQUIVO="Exercicios/Labs/Atividades.java"

# Entra no repositório
cd "$REPO" || exit 1

# Garante que está na branch main
git checkout main

# Se houver alterações pendentes, para para não bagunçar nada
if [ -n "$(git status --porcelain)" ]; then
  exit 1
fi

# Sorteia uma quantidade entre 2 e 10 commits
QTD_COMMITS=$(( RANDOM % 9 + 2 ))

for ((i=1; i<=QTD_COMMITS; i++))
do
  DATA=$(date "+%Y-%m-%d %H:%M:%S")
  NUM=$RANDOM

  cat > "$ARQUIVO" <<EOF
public class Atividades {

    public static void main(String[] args) {
        System.out.println("Exercicio de estudo gerado em: $DATA");
        System.out.println("Numero base: $NUM");

        int[] valores = {5, 3, 8, 1, 2};

        ordenar(valores);

        System.out.println("Vetor ordenado:");
        for (int valor : valores) {
            System.out.print(valor + " ");
        }

        System.out.println();
        System.out.println("Fatorial de 5: " + fatorial(5));
    }

    // Metodo que ordena um vetor usando insertion sort
    public static void ordenar(int[] vetor) {
        for (int i = 1; i < vetor.length; i++) {
            int chave = vetor[i];
            int j = i - 1;

            while (j >= 0 && vetor[j] > chave) {
                vetor[j + 1] = vetor[j];
                j--;
            }

            vetor[j + 1] = chave;
        }
    }

    // Metodo recursivo para calcular fatorial
    public static int fatorial(int n) {
        if (n <= 1) {
            return 1;
        }

        return n * fatorial(n - 1);
    }
}
EOF

  # Testa se compila
  javac -d /tmp/aeds-atividades "$ARQUIVO"

  # Adiciona somente o arquivo permitido
  git add "$ARQUIVO"

  # Cria commit
  git commit -m "study: update Java study exercise"
done

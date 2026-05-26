#!/usr/bin/env bash
set -euo pipefail

mode="${1:-run}"
source_file="${2:-}"
workspace_folder="${3:-}"

if [[ -z "$source_file" || -z "$workspace_folder" ]]; then
    echo "Uso: run-java-current.sh <compile|run> <arquivo.java> <workspace>"
    exit 2
fi

if [[ ! -f "$source_file" ]]; then
    echo "Arquivo nao encontrado: $source_file"
    exit 2
fi

if [[ "${source_file##*.}" != "java" ]]; then
    echo "Abra um arquivo .java antes de executar esta tarefa."
    exit 2
fi

main_class="$(
    sed -nE 's/^[[:space:]]*public[[:space:]]+class[[:space:]]+([A-Za-z_][A-Za-z0-9_]*).*/\1/p' "$source_file" | head -n 1
)"

if [[ -z "$main_class" ]]; then
    main_class="$(basename "$source_file" .java)"
fi

build_dir="$workspace_folder/.vscode/java-bin"
source_dir="$(mktemp -d "${TMPDIR:-/tmp}/aeds-java-src.XXXXXX")"
target_file="$source_dir/$main_class.java"

trap 'rm -rf "$source_dir"' EXIT

mkdir -p "$build_dir"
find "$build_dir" -type f -name '*.class' -delete

cp "$source_file" "$target_file"

sources=("$target_file")
if [[ -f "$workspace_folder/MyIO/MyIO.java" ]]; then
    sources+=("$workspace_folder/MyIO/MyIO.java")
fi

javac -encoding UTF-8 -d "$build_dir" "${sources[@]}"

if [[ "$mode" == "run" ]]; then
    java -cp "$build_dir" "$main_class"
else
    echo "Compilado: $main_class"
fi

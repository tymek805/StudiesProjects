#!/bin/bash
PATH_FILE="$1"

for FILE in "$@"; do
    if [ -f "$FILE" ]; then
        echo "Prawa dostępu do pliku $FILE:"
        ls -l "$FILE"
    else
        echo "Plik $FILE nie istnieje."
    fi
done

#!/bin/bash
DIR_PATH="$1"
for FILE in "$DIR_PATH"*; do
    FILE_EXT=${FILE##*.}
    if [ -f "$FILE" ]; then
        case $FILE_EXT in 
        html|htm|php|css|gif|jpg) echo $FILE ;;
        *) ;;
        esac
    fi
done

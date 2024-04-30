#!/bin/bash
COUNTER=1

echo "Pętla while:"
while [ $COUNTER -le 10 ]; do
    echo $COUNTER
    COUNTER=$((COUNTER+1))
done

COUNTER=1
echo "Pętla until:"
until [ $COUNTER -gt 10 ]; do
    echo "$COUNTER"
    COUNTER=$((COUNTER+1))
done

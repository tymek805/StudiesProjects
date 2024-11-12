#!/bin/bash

read FIRST_NUM
read SECOND_NUM

if [ $FIRST_NUM -eq $SECOND_NUM ]; then
    echo "Liczba $FIRST_NUM i liczba $SECOND_NUM są równe"
else
    echo "Liczba $FIRST_NUM i liczba $SECOND_NUM są różne"
fi

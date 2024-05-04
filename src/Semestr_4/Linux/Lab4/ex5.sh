#!/bin/bash
echo "Czy jest wieczór? (tak/nie)"
read ANS

if [ "$ANS" == "tak" ]; then
    echo "Dobry wieczór!"
elif [ "$ANS" == "nie" ]; then
    echo "Dzień dobry!"
else
    echo "Nie rozpoznana odpowiedź: $ANS"
fi

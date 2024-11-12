#!/bin/bash
echo "Czy jest wieczór? (tak/nie)"
read ANS

case $ANS in
    "tak") echo "Dobry wieczór!" ;;
    "nie") echo "Dzień dobry!" ;;
    *) echo "Nie rozpoznana odpowiedź: $ANS" ;;
esac

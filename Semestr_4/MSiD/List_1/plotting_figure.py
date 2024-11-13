"""Zadanie z kreślenia wykresów."""

import matplotlib.pyplot as plt
import numpy as np

# Chcemy zapisać dwa wykresy ułożone w jednym wierszu i dwóh kolumnach
fig, ax = plt.subplots(nrows=1, ncols=2, figsize=(12, 6))


# Wykres pierwszy
# x to dziedzina: 50 próbek z zakresu [-3, 3] wygenerowanych liniowo
# y to exp(-x^2)
# y_err to szum pochodzący z rozkładu normalnego o zadanych parametrach
x = np.linspace(-3., 3., 50)
y = np.exp(-x ** 2)
y_err = np.random.normal(loc=np.mean(y), scale=0.1, size=len(y))

# Zaznaczamy x, y oraz obszar szumu wokół funkcji
ax[0].plot(x, y, '-o', label="exp(-x^2)")
ax[0].fill_between(x, y - y_err, y + y_err, alpha=0.2, label="+/- szum")

# Dodajemy oznaczenia osi i legendę na górze po lewej stronie
ax[0].set_xlabel("x")
ax[0].set_ylabel("y")
ax[0].legend(loc="upper left")


# Wykres drugi
# Definiujemy dziedzinę (x) oraz funkcje do wykreślenia (y_1, y_2)
x = np.arange(start=-50.0, stop=50.0, step=0.1)
y_1 = np.cos(x / 3.0)
y_2 = np.sin(x)

# Kreślimy obie funkcje
ax[1].plot(x, y_1, label="cos(x/3)")
ax[1].plot(x, y_2, label="sin(x)")

# Ustawiamy skalę osi x na symetryczną-logarytmiczną oraz dodajemy siatkę w
# tle kreślonych krzywych
ax[1].set_xscale('symlog')
ax[1].grid(True)

# Dodajemy oznaczenia osi i legendę na dole po prawej stronie
ax[1].set_xlabel("x")
ax[1].set_ylabel("y")
ax[1].legend(loc="lower right")

# Dodajemy tytuł
plt.suptitle("Funkcje wygenerowane w 'numpy' i wykreślone w 'matplotlib'")
plt.savefig("wykres_odpowiedz.png")
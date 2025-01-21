ALTER SESSION SET NLS_DATE_FORMAT = 'YYYY-MM-DD';

-- Zadanie 1
SELECT imie_wroga "WROG", opis_incydentu "PRZEWINA" 
FROM Wrogowie_kocurow 
WHERE EXTRACT(YEAR FROM data_incydentu) = 2009;

-- Zadanie 2
SELECT imie, funkcja, w_stadku_od "Z NAMI OD"
FROM Kocury 
WHERE plec = 'D' AND w_stadku_od BETWEEN '2005-09-01' AND '2007-07-31';

-- Zadanie 3
SELECT imie_wroga "WROG", gatunek, stopien_wrogosci "STOPIEN WROGOSCI" 
FROM Wrogowie 
WHERE lapowka IS NULL 
ORDER BY stopien_wrogosci;


-- Zadanie 4
SELECT imie || ' zwany ' || pseudo || ' (fun.' || funkcja || ') lowi myszki w bandzie ' || nr_bandy || ' od ' || w_stadku_od AS "WSZYSTKO O KOTACH" 
FROM Kocury 
WHERE plec = 'M' 
ORDER BY w_stadku_od DESC, pseudo;

-- Zadanie 5
SELECT pseudo, REGEXP_REPLACE(REGEXP_REPLACE(pseudo, 'A', '#', 1, 1), 'L', '%', 1, 1) "Po wymianie A na # oraz L na %" 
FROM Kocury
WHERE pseudo != REGEXP_REPLACE(pseudo, 'L', '%', 1, 1) AND pseudo != REGEXP_REPLACE(pseudo, 'A', '#', 1, 1);

-- Zadanie 6
SELECT imie, w_stadku_od "W stadku", FLOOR(NVL(przydzial_myszy, 0) / 1.1) "Zjadal", ADD_MONTHS(w_stadku_od, 6) "Podwyzka", przydzial_myszy "Zjada"
FROM Kocury
WHERE MONTHS_BETWEEN(CURRENT_DATE, w_stadku_od) >= 180 
AND EXTRACT(MONTH FROM w_stadku_od) BETWEEN 3 AND 9;

-- Zadanie 7
SELECT imie, przydzial_myszy * 3 "MYSZY KWARTALNE", NVL(myszy_ekstra, 0) * 3 "KWARTALNE DODATKI"
FROM Kocury
WHERE przydzial_myszy > 2 * NVL(myszy_ekstra, 0) AND przydzial_myszy >= 55;

-- Zadanie 8
SELECT imie, 
    CASE 
        WHEN ((NVL(przydzial_myszy, 0) + NVL(myszy_ekstra, 0)) * 12) = 660 THEN 'LIMIT'
        WHEN ((NVL(przydzial_myszy, 0) + NVL(myszy_ekstra, 0)) * 12) > 660 THEN TO_CHAR(((NVL(przydzial_myszy, 0) + NVL(myszy_ekstra, 0)) * 12))
        ELSE 'Ponizej 660'
        END "Zjada rocznie"
FROM Kocury;

-- Zadanie 9
-- 2024-10-29
SELECT pseudo, w_stadku_od "W STADKU",
    CASE 
        WHEN NEXT_DAY(LAST_DAY('2024-10-29') - 7, 'ŒRODA') >= '2024-10-29' THEN
            CASE
                WHEN (EXTRACT(DAY FROM w_stadku_od) <= 15) THEN NEXT_DAY(LAST_DAY('2024-10-29') - 7, 'ŒRODA')
                ELSE NEXT_DAY(LAST_DAY(ADD_MONTHS('2024-10-29', 1)) - 7, 'ŒRODA')
            END
        ELSE NEXT_DAY(LAST_DAY(ADD_MONTHS('2024-10-29', 1)) - 7, 'ŒRODA')
    END "WYPLATA"
FROM Kocury
ORDER BY w_stadku_od;
-- 2024-10-31
SELECT pseudo, w_stadku_od "W STADKU",
    CASE 
        WHEN NEXT_DAY(LAST_DAY('2024-10-31') - 7, 'ŒRODA') >= '2024-10-31' THEN
            CASE
                WHEN (EXTRACT(DAY FROM w_stadku_od) <= 15) THEN NEXT_DAY(LAST_DAY('2024-10-31') - 7, 'ŒRODA')
                ELSE NEXT_DAY(LAST_DAY(ADD_MONTHS('2024-10-31', 1)) - 7, 'ŒRODA')
            END
        ELSE NEXT_DAY(LAST_DAY(ADD_MONTHS('2024-10-31', 1)) - 7, 'ŒRODA')
    END "WYPLATA"
FROM Kocury
ORDER BY w_stadku_od;

-- Zadanie 10
-- pseudo
SELECT pseudo || ' - ' || 
    CASE COUNT(pseudo)
        WHEN 1 THEN 'Unikalny'
        ELSE 'nieunikalny'
    END "Unikalnosc atr. PSEUDO"
FROM Kocury
WHERE pseudo IS NOT NULL
GROUP BY pseudo
ORDER BY pseudo;

-- szef
SELECT szef || ' - ' || 
    CASE COUNT(szef)
        WHEN 1 THEN 'Unikalny'
        ELSE 'nieunikalny'
    END "Unikalnosc atr. SZEF"
FROM Kocury
WHERE szef IS NOT NULL
GROUP BY szef
ORDER BY szef;

-- Zadanie 11
SELECT pseudo, COUNT(pseudo) "Liczba wrogow"
FROM Wrogowie_kocurow
GROUP BY pseudo
HAVING COUNT(pseudo) > 1;

-- Zadanie 12
SELECT 'Liczba kotow= ' || COUNT(*) || ' lowi jako ' || funkcja || ' i zjada max. ' || TO_CHAR(MAX(NVL(przydzial_myszy,0)+NVL(myszy_ekstra,0)), '99.99') || ' myszy misiecznie' "MAKSYMALNY PRZYDZIAL"
FROM Kocury
WHERE plec != 'M' AND funkcja != 'SZEFUNIO'
GROUP BY funkcja
HAVING AVG(NVL(myszy_ekstra, 0) + NVL(przydzial_myszy, 0)) > 50;

-- Zadanie 13
SELECT nr_bandy "Nr bandy", plec "Plec", MIN(NVL(przydzial_myszy, 0)) "Minimalny przydzial"
FROM Kocury
GROUP BY plec, nr_bandy;

-- Zadanie 14
SELECT LEVEL "Poziom", pseudo "Pseudonim", funkcja "Funkcja", nr_bandy "Nr bandy"
FROM Kocury
WHERE plec = 'M'
CONNECT BY PRIOR pseudo = szef
START WITH funkcja = 'BANDZIOR';

-- Zadanie 15
SELECT LPAD((LEVEL - 1), (LEVEL - 1) * 4 + length(to_char(LEVEL)), '===>') || ' ' || imie "Hierarchia",
NVL(szef, 'Sam sobie panem') "Pseudo szefa",
funkcja "Funkcja"
FROM Kocury
WHERE myszy_ekstra IS NOT NULL
CONNECT BY PRIOR pseudo = szef
START WITH szef is NULL;

-- Zadanie 16
SELECT LPAD(' ', 4 * (LEVEL - 1)) || pseudo "Droga s³u¿bowa"
FROM Kocury 
CONNECT BY pseudo = PRIOR szef
START WITH plec = 'M' AND MONTHS_BETWEEN(CURRENT_DATE, w_stadku_od) > 180 AND myszy_ekstra IS NULL
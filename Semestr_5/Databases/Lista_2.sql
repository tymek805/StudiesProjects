ALTER SESSION SET NLS_DATE_FORMAT = 'YYYY-MM-DD';

-- Zadanie 17
SELECT pseudo "POLUJE W POLU", przydzial_myszy "PRZYDZIAL MYSZY", Bandy.nazwa "BANDA" FROM Kocury 
JOIN Bandy ON Kocury.nr_bandy = Bandy.nr_bandy
WHERE (Bandy.teren = 'CALOSC' OR Bandy.teren = 'POLE') AND przydzial_myszy > 50
ORDER BY przydzial_myszy DESC;

-- Zadanie 18
SELECT K1.imie, K1.w_stadku_od "POLUJE OD" 
FROM Kocury K1, Kocury K2
WHERE K2.imie = 'JACEK' AND K1.w_stadku_od < K2.w_stadku_od
ORDER BY K1.w_stadku_od DESC;

-- Zadanie 19
-- Podpunkt a
SELECT K1.imie "Imie", K1.funkcja, NVL(K2.imie, ' ') "Szef 1", NVL(K3.imie, ' ') "Szef 2", NVL(K4.imie, ' ') "Szef 3"
FROM Kocury K1 LEFT JOIN 
    (Kocury K2 LEFT JOIN 
    (Kocury K3 LEFT JOIN Kocury K4 ON K3.szef = K4.pseudo)
        ON K2.szef = K3.pseudo)
        ON K1.szef = K2.pseudo
WHERE K1.funkcja IN ('KOT', 'MILUSIA');

-- Podpunkt b
SELECT imie "Imie", funkcja "Funkcja", NVL(szef1, ' ') "Szef 1", NVL(szef2, ' ') "Szef 2", NVL(szef3, ' ') "Szef 3"
FROM (
    SELECT CONNECT_BY_ROOT imie imie, CONNECT_BY_ROOT funkcja funkcja, LEVEL l, imie tmpImie
    FROM Kocury
    CONNECT BY PRIOR szef = pseudo
    START WITH funkcja IN ('MILUSIA', 'KOT')
) PIVOT (MIN(tmpImie) FOR l IN (2 szef1, 3 szef2, 4 szef3));

-- Podpunkt c
SELECT imie, funkcja, SUBSTR(MAX(szefowie), 15) "Imiona kolejnych szefow"
FROM (
    SELECT  CONNECT_BY_ROOT imie imie, 
            CONNECT_BY_ROOT funkcja funkcja, 
            SYS_CONNECT_BY_PATH(RPAD(imie, 10), ' | ') || ' |' szefowie
    FROM Kocury
    CONNECT BY PRIOR szef = pseudo
    START WITH funkcja in ('KOT', 'MILUSIA'))
GROUP BY funkcja, imie;

-- Zadanie 20
SELECT Kocury.imie "Imie kotki", Bandy.nazwa "Nazwa bandy", Wrogowie.imie_wroga "Imie wroga", Wrogowie.stopien_wrogosci "Ocena wroga", Wrogowie_kocurow.data_incydentu "Data inc."
FROM Kocury
    JOIN Bandy ON Kocury.nr_bandy = Bandy.nr_bandy
    JOIN Wrogowie_kocurow ON Kocury.pseudo = Wrogowie_kocurow.pseudo
    JOIN Wrogowie ON Wrogowie.imie_wroga = Wrogowie_kocurow.imie_wroga
WHERE plec = 'D' AND Wrogowie_kocurow.data_incydentu > '2007-01-01'
ORDER BY imie;

-- Zadanie 21
SELECT nazwa "Nazwa bandy", COUNT(DISTINCT pseudo) "Koty z wrogami"
FROM Kocury
    JOIN Bandy USING (nr_bandy)
    JOIN Wrogowie_kocurow USING (pseudo)
GROUP BY nazwa;

-- Zadanie 22
SELECT funkcja "Funkcja", pseudo "Pseudonim kota", COUNT(pseudo) "Liczba wrogow"
FROM Kocury JOIN Wrogowie_kocurow USING (pseudo)
GROUP BY pseudo, funkcja
HAVING COUNT(pseudo) >= 2;

-- Zadanie 23
SELECT imie, (przydzial_myszy + myszy_ekstra) * 12 "Dawka roczna", 'ponizej 864' "Dawka"
FROM Kocury
WHERE myszy_ekstra IS NOT NULL AND (przydzial_myszy + myszy_ekstra) * 12 < 864

UNION

SELECT imie, (przydzial_myszy + myszy_ekstra) * 12 "Dawka roczna", '864' "Dawka"
FROM Kocury
WHERE myszy_ekstra IS NOT NULL AND (przydzial_myszy + myszy_ekstra) * 12 = 864

UNION

SELECT imie, (przydzial_myszy + myszy_ekstra) * 12 "Dawka roczna", 'powyzej 864' "Dawka"
FROM Kocury
WHERE myszy_ekstra IS NOT NULL AND (przydzial_myszy + myszy_ekstra) * 12 > 864

ORDER BY "Dawka roczna" DESC;

-- Zadanie 24
-- sposób 1
SELECT nr_bandy "NR BANDY", nazwa, teren
FROM Bandy LEFT JOIN Kocury USING (nr_bandy)
WHERE pseudo IS NULL;

-- sposób 2
SELECT nr_bandy "NR BANDY", nazwa, teren
FROM Bandy

MINUS

SELECT DISTINCT Kocury.nr_bandy, nazwa, teren
FROM Bandy LEFT JOIN Kocury ON Bandy.nr_bandy = Kocury.nr_bandy;

-- Zadanie 25
SELECT imie, funkcja, przydzial_myszy "PRZYDZIAL MYSZY"
FROM Kocury
WHERE przydzial_myszy >= 3 * (SELECT NVL(przydzial_myszy, 0)
                                FROM Kocury JOIN Bandy USING (nr_bandy)
                                WHERE teren IN ('SAD', 'CALOSC') AND funkcja = 'MILUSIA'
                                ORDER BY przydzial_myszy DESC
                                FETCH FIRST ROWS ONLY)
;

-- Zadanie 26
SELECT K2.funkcja "Funkcja", K2.AVG "Srednio najw. i najm. myszy"
FROM
  (SELECT MIN(AVG) "MIN_AVG", MAX(AVG) "MAX_AVG"
  FROM (
    SELECT funkcja, ROUND(AVG(przydzial_myszy + NVL(myszy_ekstra, 0))) "AVG"
    FROM Kocury
    WHERE funkcja <> 'SZEFUNIO'
    GROUP BY funkcja
  )) K1

  JOIN

  (SELECT funkcja, ROUND(AVG(przydzial_myszy + NVL(myszy_ekstra, 0))) "AVG"
  FROM Kocury
  WHERE funkcja <> 'SZEFUNIO'
  GROUP BY funkcja) K2
  ON K1.MIN_AVG = K2.AVG OR K1.MAX_AVG = K2.AVG
ORDER BY K2.AVG;

-- Zadanie 27
-- podpunkt a
SELECT pseudo, przydzial_myszy + NVL(myszy_ekstra,0) "ZJADA"
FROM Kocury K
WHERE &n > (SELECT COUNT(DISTINCT przydzial_myszy + NVL(myszy_ekstra, 0))
            FROM Kocury
            WHERE K.przydzial_myszy + NVL(K.myszy_ekstra, 0) < przydzial_myszy + NVL(myszy_ekstra, 0)
            )
ORDER BY 2 DESC;

-- podpunkt b
SELECT pseudo, przydzial_myszy + NVL(myszy_ekstra, 0) "ZJADA"
FROM Kocury
WHERE przydzial_myszy + NVL(myszy_ekstra, 0) IN (
  SELECT *
  FROM (
    SELECT DISTINCT przydzial_myszy + NVL(myszy_ekstra, 0)
    FROM Kocury
    ORDER BY 1 DESC
  ) WHERE ROWNUM <= &n
);

-- podpunkt c
SELECT K1.pseudo, AVG(K1.przydzial_myszy + NVL(K1.myszy_ekstra, 0)) "ZJADA"
FROM Kocury K1 LEFT JOIN Kocury K2 ON K1.przydzial_myszy + NVL(K1.myszy_ekstra, 0) <= (K2.przydzial_myszy + NVL(K2.myszy_ekstra, 0))
GROUP BY K1.pseudo
HAVING COUNT(DISTINCT K2.przydzial_myszy + NVL(K2.myszy_ekstra, 0)) <= &n
ORDER BY 2 DESC;

-- podpunkt d
SELECT  pseudo, ZJADA
FROM (
  SELECT  pseudo,
    NVL(przydzial_myszy, 0) + NVL(myszy_ekstra, 0) "ZJADA",
    DENSE_RANK() OVER (
      ORDER BY przydzial_myszy + NVL(myszy_ekstra, 0) DESC
    ) RANK
  FROM Kocury
  )
WHERE RANK <= &n;

-- Zadanie 28
SELECT TO_CHAR(EXTRACT(YEAR FROM w_stadku_od)) "ROK", count(*) "LICZBA WSTAPIEN"
FROM Kocury
GROUP BY EXTRACT(YEAR FROM w_stadku_od)
HAVING COUNT(*) IN (
    (SELECT * FROM
        (SELECT DISTINCT COUNT(pseudo)
         FROM Kocury
         GROUP BY EXTRACT(YEAR FROM w_stadku_od)
         HAVING COUNT(pseudo) > (
            SELECT AVG(COUNT(EXTRACT(YEAR FROM w_stadku_od)))
            FROM Kocury
            GROUP BY EXTRACT(YEAR FROM w_stadku_od)
         )
         ORDER BY COUNT(pseudo))
    WHERE ROWNUM = 1
    ),
    (SELECT * FROM
        (SELECT DISTINCT COUNT(pseudo)
         FROM Kocury
         GROUP BY EXTRACT(YEAR FROM w_stadku_od)
         HAVING COUNT(pseudo) < (
            SELECT AVG(COUNT(EXTRACT(YEAR FROM w_stadku_od)))
            FROM Kocury
            GROUP BY EXTRACT(YEAR FROM w_stadku_od)
         )
         ORDER BY COUNT(pseudo) DESC)
    WHERE ROWNUM = 1
    )
)

UNION ALL

SELECT 'Srednia', ROUND(AVG(COUNT(*)), 7)
FROM Kocury
GROUP BY EXTRACT(YEAR FROM w_stadku_od)
ORDER BY 2;

-- Zadanie 28 modyfikacja
SELECT TO_CHAR(EXTRACT(YEAR FROM w_stadku_od)) "ROK", COUNT(*) "LICZBA WSTAPIEN"
FROM Kocury
GROUP BY EXTRACT(YEAR FROM w_stadku_od)
HAVING COUNT(*) IN (
    (SELECT MIN(MIN_COUNT)
     FROM (
         SELECT COUNT(*) "MIN_COUNT"
         FROM Kocury
         GROUP BY EXTRACT(YEAR FROM w_stadku_od)
         HAVING COUNT(*) > (
             SELECT AVG(COUNT(*))
             FROM Kocury
             GROUP BY EXTRACT(YEAR FROM w_stadku_od)
         )
     )),
    (SELECT MAX(MAX_COUNT)
     FROM (
         SELECT COUNT(*) "MAX_COUNT"
         FROM Kocury
         GROUP BY EXTRACT(YEAR FROM w_stadku_od)
         HAVING COUNT(*) < (
             SELECT AVG(COUNT(*))
             FROM Kocury
             GROUP BY EXTRACT(YEAR FROM w_stadku_od)
         )
     ))
)

UNION ALL

SELECT 'Srednia', ROUND(AVG(COUNT(*)), 7)
FROM Kocury
GROUP BY EXTRACT(YEAR FROM w_stadku_od)
ORDER BY 2;

-- Zadanie 29
-- podpunkt a
SELECT K1.imie, MIN(K1.przydzial_myszy + NVL(K1.myszy_ekstra, 0)) "ZJADA", K1.nr_bandy "NR BANDY", TO_CHAR(AVG(K2.przydzial_myszy + NVL(K2.myszy_ekstra, 0)), '99.99') "SREDNIA BANDY"
FROM Kocury K1 JOIN Kocury K2 ON K1.nr_bandy = K2.nr_bandy
WHERE K1.plec = 'M'
GROUP BY K1.imie, K1.nr_bandy
HAVING MIN(K1.przydzial_myszy + NVL(K1.myszy_ekstra, 0)) <= AVG(K2.przydzial_myszy + NVL(K2.myszy_ekstra, 0))
ORDER BY 4;

-- podpunkt b
SELECT imie, przydzial_myszy + NVL(myszy_ekstra, 0) "ZJADA", K1.nr_bandy "NR BANDY", TO_CHAR(AVG, '99.99') "SREDNIA BANDY"
FROM Kocury K1 JOIN (
    SELECT nr_bandy, AVG(przydzial_myszy + NVL(myszy_ekstra, 0)) "AVG" FROM Kocury GROUP BY nr_bandy) K2
    ON K1.nr_bandy = K2.nr_bandy AND przydzial_myszy + NVL(myszy_ekstra, 0) <= AVG
WHERE plec = 'M'
ORDER BY 4;

-- podpunkt c
SELECT imie, przydzial_myszy + NVL(myszy_ekstra, 0) "ZJADA", nr_bandy "NR BANDY", TO_CHAR((
    SELECT AVG(przydzial_myszy + NVL(myszy_ekstra, 0)) "AVG" FROM Kocury K WHERE Kocury.nr_bandy = K.nr_bandy
    ), '99.99') "SREDNIA BANDY"
FROM Kocury
WHERE PLEC = 'M' AND przydzial_myszy + NVL(myszy_ekstra, 0) <= (
    SELECT AVG(przydzial_myszy + NVL(myszy_ekstra, 0)) "AVG" FROM Kocury K WHERE Kocury.nr_bandy= K.nr_bandy
);

-- Zadanie 30
SELECT imie, TO_CHAR(w_stadku_od, 'YYYY-MM-DD') || ' <--- NAJSTARSZY STAZEM W BANDZIE ' || nazwa "WSTAPIL DO STADKA"
FROM Kocury K1 JOIN Bandy ON K1.nr_bandy = Bandy.nr_bandy
WHERE w_stadku_od = (
    SELECT MAX(w_stadku_od) FROM Kocury
    WHERE K1.nr_bandy = nr_bandy
)

UNION

SELECT imie, TO_CHAR(w_stadku_od, 'YYYY-MM-DD') || ' <--- NAJMLODSZY STAZEM W BANDZIE ' || nazwa "WSTAPIL DO STADKA"
FROM Kocury K1 JOIN Bandy ON K1.nr_bandy = Bandy.nr_bandy
WHERE w_stadku_od = (
    SELECT MIN(w_stadku_od) FROM Kocury
    WHERE K1.nr_bandy = nr_bandy
)

UNION

SELECT imie, TO_CHAR(w_stadku_od, 'YYYY-MM-DD') "WSTAPIL DO STADKA"
FROM Kocury K1
WHERE w_stadku_od != (SELECT MIN(w_stadku_od) from Kocury WHERE K1.nr_bandy = nr_bandy) 
AND w_stadku_od != (SELECT MAX(w_stadku_od) FROM Kocury WHERE K1.nr_bandy = nr_bandy)
ORDER BY imie;

-- Zadanie 31
CREATE VIEW Statystyki(nazwa_bandy, sre_spoz, max_spoz, min_spoz, koty, koty_z_dod)
AS 
SELECT nazwa, AVG(przydzial_myszy), MAX(przydzial_myszy), MIN(przydzial_myszy), COUNT(pseudo), COUNT(myszy_ekstra)
FROM Kocury JOIN Bandy ON Kocury.nr_bandy = Bandy.nr_bandy
GROUP BY nazwa;

SELECT * FROM Statystyki;

SELECT pseudo "PSEUDONIM", imie "IMIE", funkcja, przydzial_myszy "ZJADA", 'OD ' || min_spoz || ' DO ' || max_spoz "GRANICE SPOZYCIA", w_stadku_od "LOWI OD"
FROM (Kocury JOIN Bandy ON Kocury.nr_bandy = Bandy.nr_bandy JOIN Statystyki ON Bandy.nazwa = Statystyki.nazwa_bandy)
WHERE pseudo = '&pseudonim_kota';

-- Zadanie 32
CREATE VIEW Staruszkowie(pseudo, plec, przydzial_myszy, myszy_ekstra, nr_bandy)
AS
SELECT pseudo, plec, przydzial_myszy, myszy_ekstra, nr_bandy
FROM Kocury
WHERE pseudo IN (
      SELECT pseudo
      FROM Kocury JOIN Bandy USING (nr_bandy)
      WHERE nazwa = 'CZARNI RYCERZE'
      ORDER BY w_stadku_od
      FETCH NEXT 3 ROWS ONLY
    )
    OR pseudo IN (
      SELECT pseudo
      FROM Kocury JOIN Bandy USING (nr_bandy)
      WHERE nazwa = 'LACIACI MYSLIWI'
      ORDER BY w_stadku_od
      FETCH NEXT 3 ROWS ONLY
);

-- Przed podwy¿k¹
SELECT pseudo, plec, przydzial_myszy "Myszy przed podw.", NVL(myszy_ekstra, 0) "Extra przed podw." FROM Staruszkowie;

-- Po podwy¿ce
UPDATE Staruszkowie
SET przydzial_myszy = przydzial_myszy + DECODE(plec, 'D', 0.1 * (SELECT MIN(przydzial_myszy) FROM Kocury), 10),
    myszy_ekstra = NVL(myszy_ekstra, 0) + 0.15 * (SELECT AVG(NVl(myszy_ekstra, 0)) FROM Kocury WHERE Staruszkowie.nr_bandy = nr_bandy);

SELECT pseudo, plec, przydzial_myszy "Myszy po podw.", NVL(myszy_ekstra, 0) "Extra po podw." FROM Staruszkowie;

ROLLBACK;

-- Zadanie 33
-- podpunkt a
SELECT DECODE(plec, 'Kocur', ' ', nazwa) "NAZWA BANDY", plec, ile, szefunio, bandzior, lowczy, lapacz, kot, milusia, dzielczy, suma
FROM (SELECT nazwa,
             DECODE(plec, 'D', 'Kotka', 'Kocur') "PLEC",
             TO_CHAR(COUNT(pseudo)) "ILE",
             TO_CHAR(SUM(DECODE(funkcja, 'SZEFUNIO', NVL(przydzial_myszy, 0) + NVL(myszy_ekstra, 0), 0))) "SZEFUNIO",
             TO_CHAR(SUM(DECODE(funkcja, 'BANDZIOR', NVL(przydzial_myszy, 0) + NVL(myszy_ekstra, 0), 0))) "BANDZIOR",
             TO_CHAR(SUM(DECODE(funkcja, 'LOWCZY',   NVL(przydzial_myszy, 0) + NVL(myszy_ekstra, 0), 0))) "LOWCZY",
             TO_CHAR(SUM(DECODE(funkcja, 'LAPACZ',   NVL(przydzial_myszy, 0) + NVL(myszy_ekstra, 0), 0))) "LAPACZ",
             TO_CHAR(SUM(DECODE(funkcja, 'KOT',      NVL(przydzial_myszy, 0) + NVL(myszy_ekstra, 0), 0))) "KOT",
             TO_CHAR(SUM(DECODE(funkcja, 'MILUSIA',  NVL(przydzial_myszy, 0) + NVL(myszy_ekstra, 0), 0))) "MILUSIA",
             TO_CHAR(SUM(DECODE(funkcja, 'DZIELCZY', NVL(przydzial_myszy, 0) + NVL(myszy_ekstra, 0), 0))) "DZIELCZY",
             TO_CHAR(SUM(NVL(przydzial_myszy, 0) + NVL(myszy_ekstra, 0))) "SUMA"
        FROM Kocury JOIN Bandy USING (nr_bandy)
        GROUP BY nazwa, plec

        UNION ALL

        SELECT 'Z----------------', '--------', '----------', '-----------', '-----------', '----------', '----------', '----------', '----------', '----------', '----------' FROM DUAL
    
        UNION ALL

        SELECT 'ZJADA RAZEM', ' ', ' ',
               TO_CHAR(SUM(DECODE(funkcja, 'SZEFUNIO', NVL(przydzial_myszy, 0) + NVL(myszy_ekstra, 0), 0))),
               TO_CHAR(SUM(DECODE(funkcja, 'BANDZIOR', NVL(przydzial_myszy, 0) + NVL(myszy_ekstra, 0), 0))),
               TO_CHAR(SUM(DECODE(funkcja, 'LOWCZY',   NVL(przydzial_myszy, 0) + NVL(myszy_ekstra, 0), 0))),
               TO_CHAR(SUM(DECODE(funkcja, 'LAPACZ',   NVL(przydzial_myszy, 0) + NVL(myszy_ekstra, 0), 0))),
               TO_CHAR(SUM(DECODE(funkcja, 'KOT',      NVL(przydzial_myszy, 0) + NVL(myszy_ekstra, 0), 0))),
               TO_CHAR(SUM(DECODE(funkcja, 'MILUSIA',  NVL(przydzial_myszy, 0) + NVL(myszy_ekstra, 0), 0))),
               TO_CHAR(SUM(DECODE(funkcja, 'DZIELCZY', NVL(przydzial_myszy, 0) + NVL(myszy_ekstra, 0), 0))),
               TO_CHAR(SUM(NVL(przydzial_myszy, 0) + NVL(myszy_ekstra, 0)))
        FROM Kocury JOIN BANDY USING (nr_bandy)
        ORDER BY 1, 2 DESC
);

-- podpunkt b
SELECT * FROM (
    SELECT DECODE(plec, 'D', nazwa, ' ') "NAZWA BANDY", 
           DECODE(plec, 'D', 'Kotka' , 'Kocur') "PLEC", 
           TO_CHAR(ile) "ILE",
           TO_CHAR(NVL(szefunio, 0)) "SZEFUNIO",
           TO_CHAR(NVL(bandzior, 0)) "BANDZIOR",
           TO_CHAR(NVL(lowczy, 0)) "LOWCZY",
           TO_CHAR(NVL(lapacz, 0)) "LAPACZ",
           TO_CHAR(NVL(kot, 0)) "KOT",
           TO_CHAR(NVL(milusia, 0)) "MILUSIA",
           TO_CHAR(NVL(dzielczy, 0)) "DZIELCZY",
           TO_CHAR(NVL(suma, 0)) "SUMA"
    FROM (
        SELECT nazwa, plec, funkcja, przydzial_myszy + NVL(myszy_ekstra, 0) przydzial
        FROM Kocury JOIN Bandy ON Kocury.nr_bandy= Bandy.nr_bandy
    ) PIVOT (
        SUM(przydzial) FOR funkcja IN (
            'SZEFUNIO' szefunio, 
            'BANDZIOR' bandzior, 
            'LOWCZY' lowczy, 
            'LAPACZ' lapacz,
            'KOT' kot, 
            'MILUSIA' milusia, 
            'DZIELCZY' dzielczy
        )
    ) JOIN (
        SELECT nazwa "TMPN", plec "TMPP", COUNT(pseudo) ile, SUM(przydzial_myszy + NVL(myszy_ekstra, 0)) suma
        FROM Kocury JOIN Bandy USING (nr_bandy)
        GROUP BY nazwa, plec
        ORDER BY nazwa
    ) ON TMPN = nazwa AND TMPP = plec
)

UNION ALL

SELECT 'Z--------------', '------', '--------', '---------', '---------', '--------', '--------', '--------', '--------', '--------', '--------' FROM DUAL

UNION ALL

SELECT  'ZJADA RAZEM', ' ', ' ',
        TO_CHAR(NVL(szefunio, 0)) "SZEFUNIO",
        TO_CHAR(NVL(bandzior, 0)) "BANDZIOR",
        TO_CHAR(NVL(lowczy, 0)) "LOWCZY",
        TO_CHAR(NVL(lapacz, 0)) "LAPACZ",
        TO_CHAR(NVL(kot, 0)) "KOT",
        TO_CHAR(NVL(milusia, 0)) "MILUSIA",
        TO_CHAR(NVL(dzielczy, 0)) "DZIELCZY",
        TO_CHAR(NVL(suma, 0)) "SUMA"
FROM (
  SELECT funkcja, przydzial_myszy + NVL(myszy_ekstra, 0) przydzial
  FROM Kocury JOIN Bandy USING (nr_bandy)
) PIVOT (
    SUM(przydzial) FOR funkcja IN (
            'SZEFUNIO' szefunio, 
            'BANDZIOR' bandzior, 
            'LOWCZY' lowczy, 
            'LAPACZ' lapacz,
            'KOT' kot, 
            'MILUSIA' milusia, 
            'DZIELCZY' dzielczy
        )
) NATURAL JOIN (
  SELECT SUM(przydzial_myszy + NVL(myszy_ekstra, 0)) "SUMA"
  FROM Kocury
);
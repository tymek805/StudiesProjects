-- Zadanie 34
DECLARE
    funkcja_kocura KOCURY.funkcja%TYPE;
BEGIN
    SELECT funkcja INTO funkcja_kocura
    FROM KOCURY
    WHERE funkcja = UPPER('&nazwa_funkcji')
    GROUP BY funkcja;
    DBMS_OUTPUT.PUT_LINE('Znaleziono kota o funkcji: ' || funkcja_kocura);
EXCEPTION
    WHEN NO_DATA_FOUND 
        THEN DBMS_OUTPUT.PUT_LINE('Nie znaleziono kota o danej funkcji');
END;

--Zadanie 35
DECLARE 
    imie_kocura Kocury.imie%TYPE;
    pzydzial NUMBER;
    miesiac NUMBER;
BEGIN
    SELECT imie, (NVL(przydzial_myszy, 0) + NVL(myszy_ekstra, 0)) * 12, EXTRACT(MONTH FROM w_stadku_od)
    INTO imie_kocura, pzydzial, miesiac
    FROM Kocury
    WHERE pseudo = UPPER('&pseudonim');
    IF pzydzial > 700 
        THEN DBMS_OUTPUT.PUT_LINE(imie_kocura || ' calkowity roczny przydzial myszy >700');
    ELSIF imie LIKE '%A%'
        THEN DBMS_OUTPUT.PUT_LINE(imie_kocura || ' imiê zawiera litere A');
    ELSIF miesiac = 5 
        THEN DBMS_OUTPUT.PUT_LINE(imie_kocura || ' maj jest miesiacem przystapienia do stada');
    ELSE DBMS_OUTPUT.PUT_LINE(imie_kocura || ' nie odpowiada kryteriom');
    END IF;
EXCEPTION 
    WHEN NO_DATA_FOUND
        THEN DBMS_OUTPUT.PUT_LINE('BRAK TAKIEGO KOTA');
    WHEN OTHERS 
        THEN DBMS_OUTPUT.PUT_LINE(sqlerrm);
END;

-- Zadanie 36
DECLARE
  CURSOR kocury_cursor IS SELECT * FROM Kocury JOIN Funkcje ON Kocury.funkcja = Funkcje.funkcja
  FOR UPDATE OF przydzial_myszy;
  kocur kocury_cursor%ROWTYPE;
  suma NUMBER;
  max_funkcji NUMBER;
  przydzial NUMBER;
BEGIN
    SELECT SUM(przydzial_myszy) INTO suma FROM Kocury;
    LOOP
        EXIT WHEN suma > 1050;
        OPEN kocury_cursor;
        LOOP
            FETCH kocury_cursor INTO kocur;
            EXIT WHEN kocury_cursor%NOTFOUND;
    
            przydzial := kocur.przydzial_myszy * 1.1;
            IF przydzial > kocur.max_myszy THEN
                przydzial := kocur.max_myszy;
            END IF;
            suma := suma - kocur.przydzial_myszy + przydzial;
            UPDATE Kocury
            SET przydzial_myszy = przydzial
            WHERE CURRENT OF kocur;
    END LOOP;
    CLOSE kocury_cursor;
  END LOOP;
END;

SELECT imie, przydzial_myszy 
FROM Kocury 
ORDER BY przydzial_myszy DESC;

ROLLBACK;

-- Zadanie 37
DECLARE
  CURSOR kocury_cursor IS
    SELECT pseudo, przydzial_myszy + NVL(myszy_ekstra, 0) "zjada"
    FROM Kocury
    ORDER BY 2 DESC;
  najwiekzy_przydzial kocury_cursor%ROWTYPE;
BEGIN
  DBMS_OUTPUT.PUT_LINE('Nr   Pseudonim   Zjada');
  DBMS_OUTPUT.PUT_LINE('----------------------');
  OPEN kocury_cursor;
  FOR i IN 1..5
  LOOP
    FETCH kocury_cursor INTO najwiekzy_przydzial;
    EXIT WHEN kocury_cursor%NOTFOUND;
    DBMS_OUTPUT.PUT_LINE(TO_CHAR(i) ||'    '|| RPAD(najwiekzy_przydzial.pseudo, 8) || '    ' || LPAD(TO_CHAR(najwiekzy_przydzial."zjada"), 5));
  END LOOP;
END;

-- Zadanie 38
DECLARE
    liczba_przelozonych     NUMBER;
    max_liczba_przelozonych NUMBER;
    szerokosc_kol           NUMBER := 15;
    pseudo_aktualny         Kocury.pseudo%TYPE;
    imie_aktualny           Kocury.imie%TYPE;
    pseudo_nastepny         Kocury.szef%TYPE;
    CURSOR podwladni IS SELECT pseudo, imie
                        FROM Kocury
                        WHERE Funkcja IN ('MILUSIA', 'KOT');
BEGIN
    SELECT MAX(LEVEL) - 1
    INTO max_liczba_przelozonych
    FROM Kocury
    CONNECT BY PRIOR szef = pseudo
    START WITH funkcja IN ('KOT', 'MILUSIA');
    liczba_przelozonych := LEAST(max_liczba_przelozonych, &liczba_przelozonych);

    DBMS_OUTPUT.PUT(RPAD('IMIE ', szerokosc_kol));
    FOR licznik IN 1..liczba_przelozonych
        LOOP
            DBMS_OUTPUT.PUT(RPAD('|  SZEF ' || licznik, szerokosc_kol));
        END LOOP;
    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE(RPAD('-', szerokosc_kol * (liczba_przelozonych + 1), '-'));

    FOR kot IN podwladni
        LOOP
            DBMS_OUTPUT.PUT(RPAD(kot.imie, szerokosc_kol));
            SELECT szef INTO pseudo_nastepny FROM Kocury WHERE pseudo = kot.pseudo;
            FOR counter IN 1..liczba_przelozonych
                LOOP
                    IF pseudo_nastepny IS NULL THEN
                        DBMS_OUTPUT.PUT(RPAD('|  ', szerokosc_kol));
                    ELSE
                        SELECT K.imie, K.pseudo, K.szef
                        INTO imie_aktualny, pseudo_aktualny, pseudo_nastepny
                        FROM Kocury K
                        WHERE K.pseudo = pseudo_nastepny;
                        DBMS_OUTPUT.PUT(RPAD('|  ' || imie_aktualny, szerokosc_kol));
                    END IF;
                END LOOP;
            DBMS_OUTPUT.PUT_LINE('');
        END LOOP;
END;

-- Zadanie 39
DECLARE
    nr_bandy NUMBER := &numer;
    nazwa_bandy VARCHAR2(20) := UPPER('&nazwa');
    teren_bandy VARCHAR2(15) := UPPER('&teren');
    wiadomosc_bledu VARCHAR2(256) := '';
    ilosc NUMBER := 0;
    zly_nr_bandy EXCEPTION;
    istnieje EXCEPTION;
BEGIN
    IF nr_bandy <=0 THEN RAISE zly_nr_bandy; END IF;

    SELECT COUNT(nr_bandy) INTO ilosc FROM Bandy WHERE nr_bandy = nr_bandy;
    IF ilosc > 0 THEN wiadomosc_bledu := TO_CHAR(nr_bandy) || ' | '; END IF;

    SELECT COUNT(nazwa) INTO ilosc FROM Bandy WHERE nazwa_bandy = nazwa;
    IF ilosc > 0 THEN
        wiadomosc_bledu := wiadomosc_bledu || nazwa_bandy || ' | ';
    END IF;
    
    SELECT COUNT(teren) INTO ilosc FROM Bandy WHERE teren_bandy=teren ;
    IF ilosc > 0 THEN
        wiadomosc_bledu := wiadomosc_bledu || teren_bandy || ' | ';
    END IF;

    IF LENGTH(wiadomosc_bledu) > 0 THEN RAISE istnieje; END IF;

    INSERT INTO Bandy (nr_bandy, nazwa, teren) VALUES (nr_bandy, nazwa_bandy, teren_bandy) ;

    EXCEPTION
        WHEN zly_nr_bandy THEN DBMS_OUTPUT.PUT_LINE('Numer bandy musi byæ liczb¹ dodatni¹!');
        WHEN istnieje THEN DBMS_OUTPUT.PUT_LINE(wiadomosc_bledu || 'wartoœæ ju¿ istnieje!');
END;
ROLLBACK;

-- Zadanie 40
CREATE OR REPLACE PROCEDURE nowa_banda(nr_b Bandy.nr_bandy%TYPE, nazwa_bandy Bandy.nazwa%TYPE, teren_bandy Bandy.teren%TYPE)
    IS
        wiadomosc_bledu VARCHAR2(256) := '';
        ilosc NUMBER := 0;
        zly_nr_bandy EXCEPTION;
        istnieje EXCEPTION;
BEGIN
    IF nr_b <=0 THEN RAISE zly_nr_bandy; END IF;

    SELECT COUNT(nr_b) INTO ilosc FROM Bandy WHERE nr_b = nr_bandy;
    IF ilosc > 0 THEN wiadomosc_bledu := TO_CHAR(nr_b) || ' | '; END IF;

    SELECT COUNT(nazwa) INTO ilosc FROM Bandy WHERE nazwa_bandy = nazwa;
    IF ilosc > 0 THEN
        wiadomosc_bledu := wiadomosc_bledu || nazwa_bandy || ' | ';
    END IF;
    
    SELECT COUNT(teren) INTO ilosc FROM Bandy WHERE teren_bandy=teren ;
    IF ilosc > 0 THEN
        wiadomosc_bledu := wiadomosc_bledu || teren_bandy || ' | ';
    END IF;

    IF LENGTH(wiadomosc_bledu) > 0 THEN RAISE istnieje; END IF;

    INSERT INTO Bandy (nr_bandy, nazwa, teren) VALUES (nr_b, nazwa_bandy, teren_bandy) ;

    EXCEPTION
        WHEN zly_nr_bandy THEN DBMS_OUTPUT.PUT_LINE('Numer bandy musi byæ liczb¹ dodatni¹!');
        WHEN istnieje THEN DBMS_OUTPUT.PUT_LINE(wiadomosc_bledu || 'wartoœæ ju¿ istnieje!');
END;
ROLLBACK;

-- Zadanie 41
CREATE OR REPLACE TRIGGER greater_by_one
BEFORE INSERT ON Bandy
FOR EACH ROW
BEGIN
  SELECT MAX(nr_bandy) + 1 INTO :NEW.nr_bandy FROM Bandy;
END;

EXECUTE nowa_banda(10, 'NOWI', 'NOWE');
SELECT * FROM Bandy;
ROLLBACK;
DROP TRIGGER greater_by_one;

-- Zadanie 42
-- Wersja a
CREATE OR REPLACE PACKAGE wirus IS
    kara NUMBER := 0;
    nagroda NUMBER := 0;
    przydzial_tygrysa Kocury.przydzial_myszy%TYPE;
END;

CREATE OR REPLACE TRIGGER przydzial_tygrysa
BEFORE UPDATE ON KOCURY
BEGIN
    SELECT przydzial_myszy INTO wirus.przydzial_tygrysa FROM Kocury WHERE pseudo = 'TYGRYS';
END;

CREATE OR REPLACE TRIGGER zmiany
    BEFORE UPDATE OF przydzial_myszy
    ON Kocury
    FOR EACH ROW
BEGIN
    IF :NEW.funkcja = 'MILUSIA' THEN
        IF :NEW.przydzial_myszy <= :OLD.przydzial_myszy THEN
            DBMS_OUTPUT.PUT_LINE('Zmiana na minus - nie ma mowy!');
            :NEW.przydzial_myszy := :OLD.przydzial_myszy;
        ELSIF :NEW.przydzial_myszy - :OLD.przydzial_myszy < 0.1 * wirus.przydzial_tygrysa THEN
            DBMS_OUTPUT.PUT_LINE('Podwy¿ka mniejsza ni¿ 10% przydzia³u Tygrysa');
            :NEW.przydzial_myszy := :NEW.przydzial_myszy + ROUND(0.1 * wirus.przydzial_tygrysa);
            :NEW.myszy_ekstra := NVL(:NEW.myszy_ekstra, 0) + 5;
            wirus.kara := wirus.kara + ROUND(0.1 * wirus.przydzial_tygrysa);
        ELSE
            wirus.nagroda := wirus.nagroda + 5;
        END IF;
    END IF;
END;

CREATE OR REPLACE TRIGGER zmiany_u_tygrysa
    AFTER UPDATE OF przydzial_myszy
    ON Kocury
DECLARE
    przydzial Kocury.przydzial_myszy%TYPE;
    ekstra    Kocury.myszy_ekstra%TYPE;
BEGIN
    SELECT przydzial_myszy, myszy_ekstra
    INTO przydzial, ekstra
    FROM Kocury
    WHERE pseudo = 'TYGRYS';
    
    przydzial := przydzial - wirus.kara;
    ekstra := ekstra + wirus.nagroda;
    
    IF wirus.kara <> 0 OR wirus.nagroda <> 0 THEN
        DBMS_OUTPUT.PUT_LINE('Nowy przydzial Tygrysa: ' || przydzial);
        DBMS_OUTPUT.PUT_LINE('Nowe myszy ekstra Tygrysa: ' || ekstra);
        wirus.kara := 0;
        wirus.nagroda := 0;
        UPDATE Kocury
        SET przydzial_myszy = przydzial,
            myszy_ekstra = ekstra
        WHERE pseudo = 'TYGRYS';
    END IF;
END;

UPDATE KOCURY
SET PRZYDZIAL_MYSZY = 50
WHERE PSEUDO = 'PUSZYSTA';

UPDATE Kocury
SET przydzial_myszy = przydzial_myszy + 1
WHERE funkcja = 'MILUSIA';

UPDATE Kocury
SET przydzial_myszy = przydzial_myszy + 20
WHERE funkcja = 'MILUSIA';

SELECT *
FROM KOCURY
WHERE PSEUDO IN ('PUSZYSTA', 'TYGRYS');

ROLLBACK;

DROP TRIGGER zmiany;
DROP TRIGGER przydzial_tygrysa;
DROP TRIGGER zmiany_u_tygrysa;

-- Wersja b
CREATE OR REPLACE TRIGGER wirus_compound
    FOR UPDATE OF przydzial_myszy
    ON Kocury
    COMPOUND TRIGGER
    przydzial_tygrysa Kocury.przydzial_myszy%TYPE;
    ekstra Kocury.myszy_ekstra%TYPE;
    kara NUMBER := 0;
    nagroda NUMBER := 0;
    BEFORE STATEMENT IS
BEGIN
    SELECT przydzial_myszy INTO przydzial_tygrysa
    FROM KOCURY
    WHERE pseudo = 'TYGRYS';
END BEFORE STATEMENT;
BEFORE EACH ROW IS
BEGIN
    IF :NEW.funkcja = 'MILUSIA' THEN
        IF :NEW.przydzial_myszy <= :OLD.przydzial_myszy THEN
            DBMS_OUTPUT.PUT_LINE('Zmiana na minus - nie ma mowy!');
            :NEW.PRZYDZIAL_MYSZY := :OLD.PRZYDZIAL_MYSZY;
        ELSIF :NEW.przydzial_myszy - :OLD.przydzial_myszy < 0.1 * przydzial_tygrysa THEN
            DBMS_OUTPUT.PUT_LINE('Podwy¿ka mniejsza ni¿ 10% przydzia³u Tygrysa');
            :NEW.przydzial_myszy := :NEW.przydzial_myszy + ROUND(0.1 * przydzial_tygrysa);
            :NEW.myszy_ekstra := NVL(:NEW.myszy_ekstra, 0) + 5;
            kara := kara + ROUND(0.1 * przydzial_tygrysa);
        ELSE
            nagroda := nagroda + 5;
        END IF;
    END IF;
END BEFORE EACH ROW;

AFTER STATEMENT IS
BEGIN
    SELECT myszy_ekstra INTO ekstra
    FROM Kocury
    WHERE pseudo = 'TYGRYS';
    przydzial_tygrysa := przydzial_tygrysa - kara;
    ekstra := ekstra + nagroda;
    IF kara <> 0 OR nagroda <> 0 THEN
        DBMS_OUTPUT.PUT_LINE('Nowy przydzial Tygrysa: ' || przydzial_tygrysa);
        DBMS_OUTPUT.PUT_LINE('Nowe myszy ekstra Tygrysa: ' || ekstra);
        kara := 0;
        nagroda := 0;
        UPDATE Kocury
        SET przydzial_myszy = przydzial_tygrysa,
            myszy_ekstra = ekstra
        WHERE pseudo = 'TYGRYS';
    END IF;
END AFTER STATEMENT;
END;

UPDATE Kocury
SET przydzial_myszy = 25
WHERE pseudo = 'PUSZYSTA';

UPDATE Kocury
SET przydzial_myszy = przydzial_myszy + 1
WHERE funkcja = 'MILUSIA';

UPDATE Kocury
SET przydzial_myszy = przydzial_myszy + 20
WHERE funkcja = 'MILUSIA';

SELECT *
FROM Kocury
WHERE pseudo IN ('PUSZYSTA', 'TYGRYS');

ROLLBACK;
DROP TRIGGER wirus_compound;

-- Zadanie 43
DECLARE
    CURSOR funkcje IS 
        SELECT Kocury.funkcja, SUM(NVL(przydzial_myszy, 0) + NVL(myszy_ekstra, 0)) suma_dla_funkcji FROM Kocury GROUP BY funkcja ORDER BY funkcja;
    CURSOR bandy IS 
        SELECT Bandy.nazwa, Bandy.nr_bandy FROM Bandy RIGHT OUTER JOIN Kocury ON Kocury.nr_bandy = Bandy.nr_bandy GROUP BY Bandy.nazwa, Bandy.nr_bandy ORDER BY Bandy.nazwa;
    CURSOR plci IS 
        SELECT plec FROM Kocury GROUP BY plec ORDER BY plec;
    CURSOR funkcjezBand IS SELECT SUM(NVL(przydzial_myszy, 0) + NVL(myszy_ekstra, 0)) sumaMyszy, Kocury.Funkcja funkcja, Bandy.nazwa naz, Kocury.plec pl
                FROM Kocury, Bandy WHERE Kocury.nr_bandy = Bandy.nr_bandy
                GROUP BY Bandy.nazwa, Kocury.plec, Kocury.funkcja
                ORDER BY Bandy.nazwa, Kocury.plec, Kocury.funkcja;
    ilosc NUMBER;
    poszegolny_kot funkcjezBand%ROWTYPE;
BEGIN
    DBMS_OUTPUT.PUT('NAZWA BANDY       PLEC    ILE ');
    FOR funkcja IN funkcje LOOP
      DBMS_OUTPUT.PUT(LPAD(funkcja.funkcja, 10));
    END LOOP;

    DBMS_OUTPUT.PUT_LINE('    SUMA');
    DBMS_OUTPUT.PUT('----------------- ------ ----');

    FOR funkcja IN funkcje LOOP
          DBMS_OUTPUT.PUT(' ---------');
    END LOOP;
    
    DBMS_OUTPUT.PUT_LINE(' --------');
    OPEN funkcjezBand;
    FOR banda IN bandy LOOP
        FETCH funkcjezBand INTO poszegolny_kot;
        FOR plec IN plci LOOP
            DBMS_OUTPUT.PUT(CASE WHEN plec.plec = 'D' THEN RPAD(banda.nazwa,18) ELSE  RPAD(' ', 18) END);
            DBMS_OUTPUT.PUT(CASE WHEN plec.plec = 'D' THEN 'Kotka' else 'Kocor' END);
    
            SELECT COUNT(*) INTO ilosc FROM Kocury WHERE Kocury.nr_bandy = banda.nr_bandy AND Kocury.plec = plec.plec;
            DBMS_OUTPUT.PUT(LPAD(ilosc, 5));
            
            FOR funkcja IN funkcje LOOP
                IF funkcja.funkcja = poszegolny_kot.funkcja AND banda.nazwa = poszegolny_kot.naz AND plec.plec = poszegolny_kot.pl  THEN 
                    DBMS_OUTPUT.PUT(LPAD(NVL(funkcja.suma_dla_funkcji, 0), 10, ' '));
                END IF;
            END LOOP;
            
            FOR fun IN funkcje
            LOOP            
                DBMS_OUTPUT.put(LPAD(NVL(fun.suma_dla_funkcji, 0), 10));
            END LOOP;
            
            DBMS_OUTPUT.PUT(LPAD(NVL(ilosc,0), 10));
            DBMS_OUTPUT.NEW_LINE();
        END LOOP;
    END LOOP;
    CLOSE funkcjezBand;
    DBMS_OUTPUT.PUT('Z---------------- ------ ----');
    FOR funkcja IN funkcje LOOP DBMS_OUTPUT.PUT(' ---------'); END LOOP;
    DBMS_OUTPUT.PUT_LINE(' --------');

    DBMS_OUTPUT.PUT('Zjada razem                ');
    FOR funkcja IN funkcje LOOP
        DBMS_OUTPUT.PUT(LPAD(NVL(funkcja.suma_dla_funkcji, 0), 10));
    END LOOP;

    SELECT SUM(NVL(przydzial_myszy, 0) + NVL(myszy_ekstra, 0)) INTO ilosc FROM Kocury;
    DBMS_OUTPUT.PUT(LPAD(ilosc,10));
    DBMS_OUTPUT.NEW_LINE();
END;

-- Zadanie 44
CREATE OR REPLACE FUNCTION podatek(pseudonim Kocury.pseudo%TYPE) RETURN NUMBER 
IS
    wysokosc_podatku NUMBER;
    podwladni NUMBER;
    wrogowie NUMBER;
BEGIN
    SELECT CEIL(0.05 * (NVL(przydzial_myszy, 0) + NVL(myszy_ekstra, 0))) INTO wysokosc_podatku FROM Kocury WHERE pseudo = pseudonim;
    SELECT COUNT(*) INTO podwladni FROM Kocury WHERE szef = pseudonim;
    SELECT COUNT(*) INTO wrogowie FROM Wrogowie_kocurow WHERE pseudo = pseudonim;

    IF podwladni = 0 THEN
      wysokosc_podatku := wysokosc_podatku + 2;
    END IF;

    IF wrogowie = 0 THEN
      wysokosc_podatku := wysokosc_podatku + 1;
    END IF;

    RETURN wysokosc_podatku;
END;

SELECT podatek('PUSZYSTA') FROM DUAL;

CREATE OR REPLACE PACKAGE pkg IS
    FUNCTION podatek(pseudonim Kocury.pseudo%TYPE) RETURN NUMBER;
    PROCEDURE nowa_banda(nr_b Bandy.nr_bandy%TYPE, nazwa_bandy Bandy.nazwa%TYPE, teren_bandy Bandy.teren%TYPE);
END pkg;

CREATE OR REPLACE PACKAGE BODY pkg IS
    PROCEDURE nowa_banda(nr_b Bandy.nr_bandy%TYPE, nazwa_bandy Bandy.nazwa%TYPE, teren_bandy Bandy.teren%TYPE)
    IS
        wiadomosc_bledu VARCHAR2(256) := '';
        ilosc NUMBER := 0;
        zly_nr_bandy EXCEPTION;
        istnieje EXCEPTION;
    BEGIN
        IF nr_b <=0 THEN RAISE zly_nr_bandy; END IF;
    
        SELECT COUNT(nr_b) INTO ilosc FROM Bandy WHERE nr_b = nr_bandy;
        IF ilosc > 0 THEN wiadomosc_bledu := TO_CHAR(nr_b) || ' | '; END IF;
    
        SELECT COUNT(nazwa) INTO ilosc FROM Bandy WHERE nazwa_bandy = nazwa;
        IF ilosc > 0 THEN
            wiadomosc_bledu := wiadomosc_bledu || nazwa_bandy || ' | ';
        END IF;
        
        SELECT COUNT(teren) INTO ilosc FROM Bandy WHERE teren_bandy=teren ;
        IF ilosc > 0 THEN
            wiadomosc_bledu := wiadomosc_bledu || teren_bandy || ' | ';
        END IF;
    
        IF LENGTH(wiadomosc_bledu) > 0 THEN RAISE istnieje; END IF;
    
        INSERT INTO Bandy (nr_bandy, nazwa, teren) VALUES (nr_b, nazwa_bandy, teren_bandy) ;
    
        EXCEPTION
            WHEN zly_nr_bandy THEN DBMS_OUTPUT.PUT_LINE('Numer bandy musi byæ liczb¹ dodatni¹!');
            WHEN istnieje THEN DBMS_OUTPUT.PUT_LINE(wiadomosc_bledu || 'wartoœæ ju¿ istnieje!');
    END;
    
    FUNCTION podatek(pseudonim Kocury.pseudo%TYPE) RETURN NUMBER 
    IS
        wysokosc_podatku NUMBER;
        podwladni NUMBER;
        wrogowie NUMBER;
    BEGIN
        SELECT CEIL(0.05 * (NVL(przydzial_myszy, 0) + NVL(myszy_ekstra, 0))) INTO wysokosc_podatku FROM Kocury WHERE pseudo = pseudonim;
        SELECT COUNT(*) INTO podwladni FROM Kocury WHERE szef = pseudonim;
        SELECT COUNT(*) INTO wrogowie FROM Wrogowie_kocurow WHERE pseudo = pseudonim;
    
        IF podwladni = 0 THEN
          wysokosc_podatku := wysokosc_podatku + 2;
        END IF;
    
        IF wrogowie = 0 THEN
          wysokosc_podatku := wysokosc_podatku + 1;
        END IF;
    
        RETURN wysokosc_podatku;
    END;
END pkg;

-- Zadanie 45
CREATE TABLE Dodatki_extra(
    pseudo VARCHAR2(15) CONSTRAINT dodatki_pseudo_fk REFERENCES Kocury(pseudo),
    dodatek_extra NUMBER(3) DEFAULT 0
);

CREATE OR REPLACE TRIGGER kontrwywiad
    BEFORE UPDATE OF przydzial_myszy ON Kocury
    FOR EACH ROW
DECLARE
    CURSOR milusie IS SELECT pseudo FROM Kocury WHERE funkcja = 'MILUSIA';
    PRAGMA AUTONOMOUS_TRANSACTION;
BEGIN
    IF LOGIN_USER <> 'TYGRYS' AND :NEW.przydzial_myszy > :OLD.przydzial_myszy AND :NEW.funkcja = 'MILUSIA' THEN
    FOR milusia IN milusie
        LOOP
            EXECUTE IMMEDIATE 'INSERT INTO Dodatki_extra (pseudo, dodatek_extra) VALUES (:mil_ps, -10)' USING milusia.pseudo;
        END LOOP;
        COMMIT;
    END IF;
END;

UPDATE Kocury
SET przydzial_myszy = 80
WHERE imie = 'RUDA';
SELECT * FROM Dodatki_extra;
ROLLBACK;
DROP TRIGGER kontrwywiad;

-- Zadanie 46
CREATE TABLE Proby_wykroczenia (kto VARCHAR2(15), kiedy DATE, komu VARCHAR2(15), operacja VARCHAR2(15));

CREATE OR REPLACE TRIGGER boundaries
    BEFORE INSERT OR UPDATE OF przydzial_myszy
    ON Kocury
    FOR EACH ROW
DECLARE
    min_num NUMBER;
    max_num NUMBER;
    operacja VARCHAR2(15);
BEGIN
    SELECT min_myszy, max_myszy INTO min_num, max_num FROM Funkcje WHERE funkcja = :NEW.funkcja;
    IF INSERTING THEN
        operacja := 'INSERT';
    ELSIF UPDATING THEN
        operacja := 'UPDATE';
    END IF;
    
    IF :NEW.przydzial_myszy < min_num OR :NEW.przydzial_myszy > max_num THEN
        INSERT INTO Proby_wykroczenia(kto, kiedy, komu, operacja) VALUES (SYS.LOGIN_USER, CURRENT_DATE, :NEW.pseudo, operacja);
        :NEW.przydzial_myszy := :OLD.przydzial_myszy;
    END IF;
END;

UPDATE Kocury
SET przydzial_myszy = 80
WHERE imie = 'JACEK';

SELECT * FROM Kocury;
SELECT * FROM Proby_wykroczenia;
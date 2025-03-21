CREATE OR REPLACE TYPE KocuryO AS OBJECT
(
    imie            VARCHAR2(15),
    plec            VARCHAR2(1),
    pseudo          VARCHAR2(15),
    funkcja         VARCHAR2(10),
    szef	        VARCHAR2(15),
    w_stadku_od     DATE,
    przydzial_myszy NUMBER(3),
    myszy_ekstra    NUMBER(3),
    nr_bandy        NUMBER(2),
    MEMBER FUNCTION caly_przydzial RETURN NUMBER,
    MEMBER FUNCTION w_stadku_od_format RETURN VARCHAR2
);

CREATE OR REPLACE TYPE BODY KocuryO AS 
    MEMBER FUNCTION caly_przydzial RETURN NUMBER IS
        BEGIN
	    RETURN przydzial_myszy + NVL(myszy_ekstra, 0);
	END;
    MEMBER FUNCTION w_stadku_od_format RETURN VARCHAR2 IS
	BEGIN
	    RETURN TO_CHAR(w_stadku_od, 'yyyy-mm-dd');
	END;
END;

CREATE OR REPLACE TYPE PlebsO AS OBJECT
(
    pseudo VARCHAR2(15),
    kot    REF KocuryO,
    MEMBER FUNCTION dane_kota RETURN VARCHAR2
);

CREATE OR REPLACE TYPE BODY PlebsO AS
    MEMBER FUNCTION dane_kota RETURN VARCHAR2 IS
	dane VARCHAR2(1000);
    	BEGIN
	    SELECT 'imie: ' || DEREF(kot).imie || 'plec: ' || DEREF(kot).plec || 'pseudo: ' || DEREF(kot).pseudo || 'funkcja: ' || DEREF(kot).funkcja 
	    INTO dane FROM dual;
	    RETURN dane;
	END;
END;

CREATE OR REPLACE TYPE ElitaO AS OBJECT
(
    pseudo VARCHAR2(15),
    kot    REF KocuryO,
    sluga  REF PlebsO,
    MEMBER FUNCTION czy_ma_sluge RETURN VARCHAR2
);

CREATE OR REPLACE TYPE BODY ElitaO AS
    MEMBER FUNCTION czy_ma_sluge RETURN VARCHAR2 IS
	BEGIN
	    IF sluga IS NOT NULL THEN
		RETURN 'TAK';
	    ELSE
		RETURN 'NIE';
	    END IF;
	END;
END;

CREATE OR REPLACE TYPE KontoO AS OBJECT
(
    nr_myszy 	      INTEGER,
    data_wprowadzenia DATE,
    data_usuniecia    DATE,
    kot               REF ElitaO,
    MEMBER PROCEDURE usun_mysz(dat DATE)
);

CREATE OR REPLACE TYPE BODY KontoO AS 
    MEMBER PROCEDURE usun_mysz(dat DATE) IS
	BEGIN
	    data_usuniecia := dat;
	END;
END;


CREATE TABLE KocuryT OF KocuryO
(CONSTRAINT kt_ps_pk PRIMARY KEY (pseudo));

CREATE TABLE PlebsT OF PlebsO
(CONSTRAINT pt_ps_pk PRIMARY KEY (pseudo));

CREATE TABLE ElitaT OF ElitaO
(CONSTRAINT et_ps_pk PRIMARY KEY (pseudo));

CREATE TABLE KontoT OF KontoO
(CONSTRAINT kt_nrm_pk PRIMARY KEY (nr_myszy));



INSERT ALL
    INTO KocuryT VALUES ('JACEK','M','PLACEK','LOWCZY','LYSY','2008-12-01',67,NULL,2)
    INTO KocuryT VALUES ('BARI','M','RURA','LAPACZ','LYSY','2009-09-01',56,NULL,2)
    INTO KocuryT VALUES ('MICKA','D','LOLA','MILUSIA','TYGRYS','2009-10-14',25,47,1)
    INTO KocuryT VALUES ('LUCEK','M','ZERO','KOT','KURKA','2010-03-01',43,NULL,3)
    INTO KocuryT VALUES ('SONIA','D','PUSZYSTA','MILUSIA','ZOMBI','2010-11-18',20,35,3)
    INTO KocuryT VALUES ('LATKA','D','UCHO','KOT','RAFA','2011-01-01',40,NULL,4)
    INTO KocuryT VALUES ('DUDEK','M','MALY','KOT','RAFA','2011-05-15',40,NULL,4)
    INTO KocuryT VALUES ('MRUCZEK','M','TYGRYS','SZEFUNIO',NULL,'2002-01-01',103,33,1)
    INTO KocuryT VALUES ('CHYTRY','M','BOLEK','DZIELCZY','TYGRYS','2002-05-05',50,NULL,1)
    INTO KocuryT VALUES ('KOREK','M','ZOMBI','BANDZIOR','TYGRYS','2004-03-16',75,13,3)
    INTO KocuryT VALUES ('BOLEK','M','LYSY','BANDZIOR','TYGRYS','2006-08-15',72,21,2)
    INTO KocuryT VALUES ('ZUZIA','D','SZYBKA','LOWCZY','LYSY','2006-07-21',65,NULL,2)
    INTO KocuryT VALUES ('RUDA','D','MALA','MILUSIA','TYGRYS','2006-09-17',22,42,1)
    INTO KocuryT VALUES ('PUCEK','M','RAFA','LOWCZY','TYGRYS','2006-10-15',65,NULL,4)
    INTO KocuryT VALUES ('PUNIA','D','KURKA','LOWCZY','ZOMBI','2008-01-01',61,NULL,3)
    INTO KocuryT VALUES ('BELA','D','LASKA','MILUSIA','LYSY','2008-02-01',24,28,2)
    INTO KocuryT VALUES ('KSAWERY','M','MAN','LAPACZ','RAFA','2008-07-12',51,NULL,4)
    INTO KocuryT VALUES ('MELA','D','DAMA','LAPACZ','RAFA','2008-11-01',51,NULL,4)
SELECT DUMMY FROM dual;
COMMIT;

INSERT INTO PlebsT
    SELECT PlebsO(K.pseudo, REF(K))
    FROM KocuryT K
    WHERE K.funkcja IN ('KOT', 'LAPACZ');
COMMIT;

INSERT INTO ElitaT
  SELECT ElitaO(K.pseudo, REF(K), NULL)
  FROM KocuryT K
  WHERE K.szef = 'TYGRYS'
        OR K.szef IS NULL;
COMMIT;

UPDATE ElitaT
SET sluga = (SELECT REF(T) FROM PlebsT T WHERE pseudo = 'UCHO')
WHERE DEREF(kot).pseudo = 'TYGRYS';
COMMIT;

INSERT INTO KontoT
  SELECT KontoO(ROWNUM, ADD_MONTHS(CURRENT_DATE, -TRUNC(DBMS_RANDOM.VALUE(0, 12))), NULL, REF(K))
  FROM ElitaT K;
COMMIT;

-- Referencja
SELECT E.pseudo, DEREF(E.sluga).pseudo SLUGA FROM ElitaT E WHERE DEREF(E.sluga).kot IS NOT NULL;

-- Podzapytanie
SELECT K.imie, K.pseudo, K.caly_przydzial() "CALY_PRZYDZIAL"
FROM KocuryT K
JOIN (SELECT DEREF(E.sluga) "plebs_kot" FROM ElitaT E)
ON REF(K) = ("plebs_kot").kot;

-- Grupowanie
SELECT DEREF(kot).pseudo "KOT", COUNT(sluga) "SLUGA"
FROM ElitaT E
GROUP BY DEREF(kot).pseudo;

-- Zadania z listy 2
-- Zadanie 18
SELECT K1.imie, K1.w_stadku_od_format() "Poluje od"
FROM KocuryT K1 JOIN KocuryT K2 ON K2.imie = 'JACEK'
WHERE K1.w_stadku_od < K2.w_stadku_od
ORDER BY K1.w_stadku_od DESC;

-- Zadanie 23
SELECT imie, K.caly_przydzial() * 12 "Dawka roczna", 'ponizej 864' "Dawka"
FROM KocuryT K
WHERE myszy_ekstra IS NOT NULL AND K.caly_przydzial() * 12 < 864

UNION ALL

SELECT imie, K.caly_przydzial() * 12 "Dawka roczna", '864' "Dawka"
FROM KocuryT K
WHERE myszy_ekstra IS NOT NULL AND K.caly_przydzial() * 12 = 864

UNION ALL

SELECT imie, K.caly_przydzial() * 12 "Dawka roczna", 'powyzej 864' "Dawka"
FROM KocuryT K
WHERE myszy_ekstra IS NOT NULL AND K.caly_przydzial() * 12 > 864

ORDER BY "Dawka roczna" DESC;

-- Zadania z listy 3
-- Zadanie 34
DECLARE
    funkcja_kocura KocuryT.funkcja%TYPE;
BEGIN
    SELECT funkcja INTO funkcja_kocura
    FROM KocuryT
    WHERE funkcja = UPPER('&nazwa_funkcji')
    GROUP BY funkcja;
    DBMS_OUTPUT.PUT_LINE('Znaleziono kota o funkcji: ' || funkcja_kocura);
EXCEPTION
    WHEN NO_DATA_FOUND 
        THEN DBMS_OUTPUT.PUT_LINE('Nie znaleziono kota o danej funkcji');
END;

-- Zadanie 35
DECLARE 
    imie_kocura KocuryT.imie%TYPE;
    pzydzial NUMBER;
    miesiac NUMBER;
BEGIN
    SELECT imie, K.caly_przydzial() * 12, EXTRACT(MONTH FROM w_stadku_od)
    INTO imie_kocura, pzydzial, miesiac
    FROM KocuryT K
    WHERE pseudo = UPPER('&pseudonim');
    IF pzydzial > 700 
        THEN DBMS_OUTPUT.PUT_LINE(imie_kocura || ' calkowity roczny przydzial myszy >700');
    ELSIF imie_kocura LIKE '%A%'
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

-- Zadanie 49
BEGIN
    EXECUTE IMMEDIATE '
    CREATE TABLE Myszy(
        nr_myszy NUMBER CONSTRAINT my_nrm_pk PRIMARY KEY,
        lowca VARCHAR2(15) CONSTRAINT my_lo_fk REFERENCES Kocury(pseudo),
        zjadacz VARCHAR2(15) CONSTRAINT my_zj_fk REFERENCES Kocury(pseudo),
        waga_myszy NUMBER(2) CONSTRAINT my_wa_my_ch CHECK (waga_myszy BETWEEN 15 AND 25),
        data_zlowienia DATE CONSTRAINT my_dat_nn NOT NULL,
        data_wydania DATE,
        CONSTRAINT my_daty_ch CHECK (data_zlowienia <= data_wydania)
        )';
END;

DROP TABLE Myszy;

ALTER SESSION SET NLS_DATE_FORMAT ='YYYY-MM-DD';

CREATE SEQUENCE myszy_seq;
DROP SEQUENCE myszy_seq;

DECLARE
    aktualna_data   DATE := '2004-01-01';
    ostatnia_sroda  DATE := NEXT_DAY(LAST_DAY(aktualna_data) - 7, 'ŒRODA');
    data_koncowa    DATE := '2025-01-21';
    
    zjedzone_myszy NUMBER(8);
    srednia_myszy  NUMBER(8);
    
    indeks_zjadacza NUMBER;
    nr_myszy BINARY_INTEGER := 0;
    
    TYPE tp IS TABLE OF Kocury.pseudo%TYPE;
    tab_pseudo tp := tp();
    
    TYPE tpm IS TABLE OF NUMBER(4);
    tab_przydzial tpm := tpm();
    
    TYPE tm IS TABLE OF Myszy%ROWTYPE INDEX BY BINARY_INTEGER;
    tab_myszy tm;
BEGIN
    LOOP
        EXIT WHEN aktualna_data >= data_koncowa;
        
        IF aktualna_data < NEXT_DAY(LAST_DAY(aktualna_data), 'ŒRODA') - 7 THEN
            ostatnia_sroda := LEAST(NEXT_DAY(LAST_DAY(aktualna_data), 'ŒRODA') - 7, data_koncowa);
        ELSE
            ostatnia_sroda := LEAST(NEXT_DAY(LAST_DAY(ADD_MONTHS(aktualna_data, 1)), 'ŒRODA') - 7, data_koncowa);
        END IF;
        
        SELECT SUM(NVL(przydzial_myszy, 0) + NVL(myszy_ekstra, 0))
        INTO zjedzone_myszy
        FROM Kocury
        WHERE w_stadku_od < ostatnia_sroda;
        
        SELECT pseudo, NVL(przydzial_myszy, 0) + NVL(myszy_ekstra, 0)
        BULK COLLECT INTO tab_pseudo, tab_przydzial
        FROM Kocury
        WHERE w_stadku_od < ostatnia_sroda;
        
        srednia_myszy := CEIL(zjedzone_myszy / tab_pseudo.COUNT);
        indeks_zjadacza := 1;
        
        FOR i IN 1..zjedzone_myszy
            LOOP
                nr_myszy := nr_myszy + 1;
                tab_myszy(nr_myszy).nr_myszy := nr_myszy;
                
                tab_myszy(nr_myszy).lowca := tab_pseudo(MOD(i, tab_pseudo.COUNT) + 1);

                IF ostatnia_sroda < data_koncowa THEN
                    tab_myszy(nr_myszy).data_wydania := ostatnia_sroda;
                    
                    IF tab_przydzial(indeks_zjadacza) = 0 THEN
                        indeks_zjadacza := indeks_zjadacza + 1;
                    ELSE 
                        tab_przydzial(indeks_zjadacza) := tab_przydzial(indeks_zjadacza) - 1;
                    END IF;
                    
                    IF indeks_zjadacza > tab_myszy.COUNT THEN
                        indeks_zjadacza := DBMS_RANDOM.VALUE(1, tab_myszy.COUNT);
                    END IF;
                    
                    tab_myszy(nr_myszy).zjadacz := tab_pseudo(indeks_zjadacza);
                END IF;
                tab_myszy(nr_myszy).waga_myszy := DBMS_RANDOM.VALUE(15, 25);
                tab_myszy(nr_myszy).data_zlowienia := aktualna_data + MOD(nr_myszy, TRUNC(ostatnia_sroda) - TRUNC(aktualna_data));
            END LOOP;
            aktualna_data := ostatnia_sroda + 1;
            ostatnia_sroda := NEXT_DAY(LAST_DAY(ADD_MONTHS(aktualna_data, 1)) - 7, 'ŒRODA');
    END LOOP;
    
    FORALL i in 1..tab_myszy.COUNT
        INSERT INTO Myszy(nr_myszy, lowca, zjadacz, waga_myszy, data_zlowienia, data_wydania)
        VALUES (
            myszy_seq.NEXTVAL, 
            tab_myszy(i).lowca, 
            tab_myszy(i).zjadacz, 
            tab_myszy(i).waga_myszy, 
            tab_myszy(i).data_zlowienia,
            tab_myszy(i).data_wydania);
END;

SELECT * FROM Myszy;
SELECT COUNT(*) FROM Myszy;
SELECT lowca, COUNT(*) FROM Myszy GROUP BY lowca ORDER BY lowca;

BEGIN
   FOR kot in (SELECT pseudo FROM Kocury)
    LOOP
       EXECUTE IMMEDIATE 
            'CREATE TABLE Konto_' || kot.pseudo ||
            '( nr_myszy NUMBER CONSTRAINT ko_nm_pk' || kot.pseudo || ' PRIMARY KEY,' ||
            'waga_myszy NUMBER(2) CONSTRAINT ko_wa_ch' || kot.pseudo || ' CHECK (waga_myszy BETWEEN 15 AND 25),' ||
            'data_zlowienia DATE CONSTRAINT ko_data_nn' || kot.pseudo || ' NOT NULL)';
       END LOOP;
END;

BEGIN
    FOR kot IN (SELECT pseudo FROM Kocury)
    LOOP
        EXECUTE IMMEDIATE 'DROP TABLE Konto_' || kot.pseudo;
        END LOOP;
END;

CREATE OR REPLACE PROCEDURE przyjmij_na_stan(pseudonim Kocury.pseudo%TYPE, data_zlowienia DATE) AS
    TYPE tw IS TABLE OF NUMBER(2);
    tab_wagi tw := tw();
    
    TYPE tn IS TABLE OF NUMBER;
    tab_nr tn := tn();
    
    czy_istnieje NUMBER;
    
    zla_data EXCEPTION;
    brak_kota EXCEPTION;
    brak_myszy_o_dacie EXCEPTION;
BEGIN
    IF data_zlowienia > SYSDATE THEN
        RAISE zla_data;
    END IF;
    
    SELECT COUNT(*) INTO czy_istnieje FROM Kocury WHERE pseudo = UPPER(pseudonim);
    IF czy_istnieje = 0 THEN
        RAISE brak_kota;
    END IF;
    
    EXECUTE IMMEDIATE
        'SELECT nr_myszy, waga_myszy FROM Konto_' || UPPER(pseudonim) || ' WHERE data_zlowienia = ''' || data_zlowienia || ''''
    BULK COLLECT INTO tab_nr, tab_wagi;
    
    IF tab_nr.COUNT = 0 THEN
        RAISE brak_myszy_o_dacie;
    END IF;
    
    FORALL i IN 1..tab_nr.COUNT
        INSERT INTO Myszy VALUES (tab_nr(i), UPPER(pseudonim), NULL, tab_wagi(i), data_zlowienia, NULL);
    
        EXECUTE IMMEDIATE 'DELETE FROM Konto_' || UPPER(pseudonim) ||' WHERE data_zlowienia = ''' || data_zlowienia || '''';
        EXCEPTION
            WHEN zla_data THEN DBMS_OUTPUT.PUT_LINE('Zla data');
            WHEN brak_kota THEN DBMS_OUTPUT.PUT_LINE('Brak kota o pseudonimie '|| UPPER(pseudonim));
            WHEN brak_myszy_o_dacie THEN DBMS_OUTPUT.PUT_LINE('Brak myszy zlowionych w dniu ' || data_zlowienia || ' przez ' || UPPER(pseudonim));
END;

CREATE OR REPLACE PROCEDURE Wyplata AS
    TYPE tp IS TABLE OF Kocury.pseudo%TYPE;
    tab_pseudo tp := tp();

    TYPE tz IS TABLE OF Kocury.pseudo%TYPE INDEX BY BINARY_INTEGER;
    tab_zjadaczy tz;    

    TYPE tpm is TABLE OF NUMBER;
    tab_przydzial tpm := tpm();
    
    TYPE tm IS TABLE OF Myszy%ROWTYPE;
    tab_myszy tm;
    
    wyplacone NUMBER(5);
    liczba_najedzonych NUMBER(2) := 0;
    indeks_zjadacza NUMBER(2) := 1;
    
    ponowna_wyplata EXCEPTION;
    brak_myszy EXCEPTION;
BEGIN
    SELECT pseudo, NVL(przydzial_myszy, 0) + NVL(myszy_ekstra, 0)
    BULK COLLECT INTO tab_pseudo, tab_przydzial
    FROM Kocury CONNECT BY PRIOR pseudo = szef
    START WITH szef IS NULL
    ORDER BY level;
    
    SELECT COUNT(nr_myszy) INTO wyplacone
    FROM Myszy WHERE data_wydania = NEXT_DAY(LAST_DAY(TRUNC(SYSDATE)) - 7, 'ŒRODA');
    
    IF wyplacone > 0 THEN 
        RAISE ponowna_wyplata;
    END IF;
    
    SELECT * BULK COLLECT INTO tab_myszy
    FROM Myszy
    WHERE data_wydania IS NULL;
    
    IF tab_myszy.COUNT < 1 THEN
        RAISE brak_myszy;
    END IF;
    
    FOR i IN 1..tab_myszy.COUNT
    LOOP
        WHILE tab_przydzial(indeks_zjadacza) = 0 AND liczba_najedzonych < tab_pseudo.COUNT
        LOOP
            liczba_najedzonych := liczba_najedzonych + 1;
            indeks_zjadacza := MOD(indeks_zjadacza + 1, tab_pseudo.COUNT) + 1;
        END LOOP;
            
        IF liczba_najedzonych = tab_pseudo.COUNT THEN
            tab_zjadaczy(i) := 'TYGRYS';
        ELSE
            tab_zjadaczy(i) := tab_pseudo(indeks_zjadacza);
            tab_przydzial(indeks_zjadacza) := tab_przydzial(indeks_zjadacza) - 1;
        END IF;
        
        IF NEXT_DAY(LAST_DAY(tab_myszy(i).data_zlowienia) - 7, 'ŒRODA') < tab_myszy(i).data_zlowienia THEN
            tab_myszy(i).data_wydania := NEXT_DAY(LAST_DAY(ADD_MONTHS(tab_myszy(i).data_zlowienia,1)) - 7, 'ŒRODA');
        ELSE
            tab_myszy(i).data_wydania := NEXT_DAY(LAST_DAY(tab_myszy(i).data_wydania) - 7, 'ŒRODA');
        END IF;
    END LOOP;
    
    FORALL i IN 1..tab_myszy.COUNT
        UPDATE Myszy SET data_wydania = tab_myszy(i).data_wydania , zjadacz = tab_zjadaczy(i)
        WHERE nr_myszy = tab_myszy(i).nr_myszy;
    EXCEPTION
        WHEN ponowna_wyplata THEN DBMS_OUTPUT.PUT_LINE('Nie mo¿na wyp³aciæ 2 razy w jednym miesiacu.');
        WHEN brak_myszy THEN DBMS_OUTPUT.PUT_LINE('Brak myszy do wyplacenia.');
END;    

ALTER SESSION SET NLS_DATE_FORMAT ='YYYY-MM-DD';
INSERT INTO Konto_ZOMBI VALUES(myszy_seq.NEXTVAL, 15, '2025-01-17');
INSERT INTO Konto_ZOMBI VALUES(myszy_seq.NEXTVAL, 25, '2025-01-16');
INSERT INTO Konto_ZOMBI VALUES(myszy_seq.NEXTVAL, 16, '2025-01-17');
INSERT INTO Konto_ZOMBI VALUES(myszy_seq.NEXTVAL, 20, '2025-01-16');

INSERT INTO Konto_TYGRYS VALUES(myszy_seq.NEXTVAL, 23, '2025-01-01');
INSERT INTO Konto_TYGRYS VALUES(myszy_seq.NEXTVAL, 24, '2025-01-01');
INSERT INTO Konto_TYGRYS VALUES(myszy_seq.NEXTVAL, 25, '2025-01-10');
INSERT INTO Konto_TYGRYS VALUES(myszy_seq.NEXTVAL, 25, '2025-01-13');
INSERT INTO Konto_TYGRYS VALUES(myszy_seq.NEXTVAL, 28, '2025-0-13');

INSERT INTO Konto_RAFA VALUES(myszy_seq.NEXTVAL, 23, '2025-01-01');
INSERT INTO Konto_RAFA VALUES(myszy_seq.NEXTVAL, 24, '2025-01-01');
INSERT INTO Konto_RAFA VALUES(myszy_seq.NEXTVAL, 25, '2025-01-10');
INSERT INTO Konto_RAFA VALUES(myszy_seq.NEXTVAL, 25, '2025-01-13');
INSERT INTO Konto_RAFA VALUES(myszy_seq.NEXTVAL, 28, '2025-01-13');

BEGIN
    przyjmij_na_stan('ZOMBI', '2025-01-17');
END;

BEGIN
    przyjmij_na_stan('TYGRYS', '2025-01-13');
END;

BEGIN
    przyjmij_na_stan('RAFA', '2025-01-17');
END;


SELECT * FROM Konto_TYGRYS;
SELECT * FROM Konto_ZOMBI;
BEGIN
    Wyplata();
END;

SELECT COUNT(*)
FROM MYSZY
WHERE data_wydania = '2025-01-29';

SELECT * FROM Myszy WHERE data_wydania = '2025-01-29';

-- 
CREATE OR REPLACE TYPE IncydentO AS OBJECT
(
    pseudo VARCHAR2(15),
    kot REF KocuryO,
    imie_wroga VARCHAR2(15),
    data_incydentu DATE,
    opis_incydentu VARCHAR2(100),
    MEMBER FUNCTION czy_aktualny RETURN BOOLEAN,
    MEMBER FUNCTION czy_ma_opis RETURN BOOLEAN
);
-- IncydentyO Body
CREATE OR REPLACE TYPE BODY IncydentO
AS
    MEMBER FUNCTION czy_ma_opis RETURN BOOLEAN
    IS
    BEGIN
        RETURN opis_incydentu IS NOT NULL;
    END;

    MEMBER FUNCTION czy_aktualny RETURN BOOLEAN
    IS
    BEGIN
        RETURN data_incydentu >= '2010-01-01';
    END;
END;
-- IncydentyT Table

CREATE TABLE IncydentyT OF IncydentO (
    CONSTRAINT incydento_pk PRIMARY KEY(pseudo, imie_wroga),
    kot SCOPE IS KocuryT CONSTRAINT incydentyo_kot_nn NOT NULL,
    pseudo CONSTRAINT incydentyo_pseudo_fk REFERENCES KocuryT(pseudo),
    imie_wroga CONSTRAINT incydento_imie_wroga_fk REFERENCES Wrogowie(imie_wroga),
    data_incydentu CONSTRAINT incydentyo_data_nn NOT NULL
);

DECLARE
CURSOR zdarzenia IS SELECT * FROM Wrogowie_kocurow;
dyn_sql VARCHAR2(1000);
BEGIN
    FOR zdarzenie IN zdarzenia
    LOOP
      dyn_sql:='DECLARE
            kot REF KocuryO;
        BEGIN
            SELECT REF(K) INTO kot FROM KocuryT K WHERE K.pseudo='''|| zdarzenie.pseudo||''';
            INSERT INTO IncydentyT VALUES
                    (IncydentO(''' || zdarzenie.pseudo || ''',  kot , ''' || zdarzenie.imie_wroga || ''', ''' || zdarzenie.data_incydentu
                    || ''',''' || zdarzenie.opis_incydentu|| '''));
            END;';
       DBMS_OUTPUT.PUT_LINE(dyn_sql);
       EXECUTE IMMEDIATE  dyn_sql;
    END LOOP;
END;

-- Modyfikacja
SELECT M.nr_myszy, M.lowca, M.waga_myszy, M.data_zlowienia
FROM Myszy M JOIN (SELECT E.pseudo FROM ElitaT E LEFT JOIN IncydentyT ON E.kot = IncydentyT.kot
WHERE DEREF(E.kot).plec = 'M' AND IncydentyT.pseudo IS NULL)Q1 ON M.lowca = Q1.pseudo
WHERE M.data_wydania IS NULL;

SELECT K.nr_myszy, K.data_wprowadzenia, K.data_usuniecia, DEREF(K.kot.kot).pseudo pseudo
FROM KontoT K 
WHERE DEREF(K.kot.kot).plec = 'M' AND K.kot.kot NOT IN (
    SELECT kot FROM IncydentyT
);
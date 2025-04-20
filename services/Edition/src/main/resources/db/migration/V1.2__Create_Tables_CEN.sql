-- V1.2__Create_Tables_CEN.sql

-- Type d'opération CEN
CREATE TABLE IF NOT EXISTS "typeOperCEN"
(
    codtypop  NUMERIC(4),
    libtypop  VARCHAR(60),
    natuoper  CHAR(1),
    compordr  NUMERIC(8),
    famioper  CHAR(1),
    contauto  CHAR(1),
    codunisa  VARCHAR(5),
    codunico  VARCHAR(5),
    regllivr  VARCHAR(1),
    libabrop  VARCHAR(8),
    priooper  NUMERIC(2, 0),
    cptetaxe  NUMERIC(12),
    cptedebo  NUMERIC(12),
    cptedeco  NUMERIC(12),
    cptereje  NUMERIC(12),
    cptataxe  NUMERIC(12),
    cpttaxte  NUMERIC(12),
    codearti  NUMERIC(3),
    cpteordr  NUMERIC(12),
    caisagen  VARCHAR(1),
    menimpliv VARCHAR(42)
);

-- Catégorie socio-professionnelle CEN
CREATE TABLE IF NOT EXISTS "catSociProfCEN"
(
    cocasopr NUMERIC(3) NOT NULL,
    licasopr VARCHAR(60),
    codcatcl NUMERIC(2),
    PRIMARY KEY (cocasopr)
);

-- Bureau poste CEN
CREATE TABLE IF NOT EXISTS "burePostCEN"
(
    codburpo NUMERIC(5, 0) NOT NULL,
    cobupodo NUMERIC(5),
    cobupora NUMERIC(5),
    coderegi NUMERIC(2),
    codeprov NUMERIC(3),
    codecomm NUMERIC(4),
    desburpo VARCHAR(60),
    adrburpo VARCHAR(60),
    codepost VARCHAR(7),
    numetele VARCHAR(15),
    nume_fax VARCHAR(15),
    numetelx VARCHAR(15),
    chai_dsn VARCHAR(30),
    nomliven NUMERIC(16),
    nomcomou NUMERIC(16),
    stominli NUMERIC(6),
    stomaxli NUMERIC(6),
    attautco CHAR(1),
    etaburpo CHAR(1),
    PRIMARY KEY (codburpo)
);

-- Client CEN
CREATE TABLE IF NOT EXISTS "clientCEN"
(
    idenclie  NUMERIC(8) NOT NULL,
    codcatcl  NUMERIC(2),
    cocasopr  NUMERIC(3),
    desiclie  VARCHAR(120),
    adreclie  VARCHAR(120),
    codepost  VARCHAR(7),
    numetele  VARCHAR(15),
    nume_fax  VARCHAR(15),
    numetelx  VARCHAR(15),
    datenais  DATE,
    lieunaiss VARCHAR(30),
    sexeclie  CHAR(1),
    situfami  CHAR(1),
    typepiec  CHAR(1),
    numpieid  VARCHAR(15),
    datpieid  DATE,
    numregco  VARCHAR(15),
    numepate  VARCHAR(15),
    datecrea  DATE,
    codepays  VARCHAR(3),
    datogmaj  DATE,
    bpogemaj  NUMERIC(5, 0),
    ordogmaj  NUMERIC(6, 0),
    PRIMARY KEY (idenclie)
);

-- Compte CEN
CREATE TABLE IF NOT EXISTS "compteCEN"
(
    codeprod NUMERIC(2) NOT NULL,
    idencomp NUMERIC(8) NOT NULL,
    codcatco NUMERIC(2),
    idenclie NUMERIC(8),
    codetaco NUMERIC(2),
    codclare NUMERIC(2),
    datprere DATE,
    codburpo NUMERIC(5, 0),
    codmotet NUMERIC(2),
    inticomp VARCHAR(120),
    adrecomp VARCHAR(120),
    codepost VARCHAR(7),
    villcomp VARCHAR(60),
    dateouve DATE,
    soldminu NUMERIC(14, 2),
    soldcour NUMERIC(14, 2),
    datesold DATE,
    soldoppo NUMERIC(14, 2),
    soldbloc NUMERIC(14, 2),
    tauintde NUMERIC(7, 4),
    tauintcr NUMERIC(7, 4),
    taumajin NUMERIC(7, 4),
    durecomp NUMERIC(3),
    revemens NUMERIC(14, 2),
    discapin CHAR(1),
    numcomcc NUMERIC(12),
    monpreve NUMERIC(14, 2),
    monverul NUMERIC(14, 2),
    moncrede NUMERIC(14, 2),
    moncreac NUMERIC(14, 2),
    datetaco DATE,
    dateclot DATE,
    inteacqu NUMERIC(16, 2),
    compliai NUMERIC(8),
    livdepce VARCHAR(1),
    datogmaj DATE,
    bpogemaj NUMERIC(5, 0),
    ordogmaj NUMERIC(6, 0),
    datderog DATE,
    datderoc DATE,
    PRIMARY KEY (codeprod, idencomp)
);

-- Opérations comptables CEN
CREATE TABLE IF NOT EXISTS "operCompCEN"
(
    dateoper    DATE       NOT NULL,
    codburpo    NUMERIC(5) NOT NULL,
    numeordr    NUMERIC(8) NOT NULL,
    datebord    DATE,
    cobupobo    NUMERIC(5),
    numebord    NUMERIC(6),
    codprode    NUMERIC(2),
    compdebi    NUMERIC(8),
    codprocr    NUMERIC(2),
    compcred    NUMERIC(8),
    codtypop    NUMERIC(4),
    comoreop    NUMERIC(2),
    datevale    DATE,
    commenta    VARCHAR(120),
    reftitju    VARCHAR(15),
    typepiec    CHAR(1),
    datvalcen   DATE,
    numpieid    VARCHAR(15),
    datpieid    DATE,
    nom_tire    VARCHAR(60),
    montoper    NUMERIC(14, 2),
    codutisa    NUMERIC(6),
    codpgmsa    VARCHAR(8),
    datesais    TIMESTAMP,
    codutiva    NUMERIC(6),
    codpgmva    VARCHAR(8),
    cobupova    NUMERIC(5),
    numeauto    NUMERIC(5),
    datevali    TIMESTAMP,
    commreje    VARCHAR(60),
    menmarde    VARCHAR(30),
    menmarcr    VARCHAR(30),
    codstaop    CHAR(1),
    ancstaop    CHAR(1),
    datogori    DATE,
    bupoogor    NUMERIC(5),
    ordogori    NUMERIC(6),
    tracounter  INTEGER,
    modepaie    VARCHAR(1),
    refoper     VARCHAR(50),
    numcomccp   NUMERIC(12),
    datocori    DATE,
    bupoocor    NUMERIC(5),
    ordocori    NUMERIC(5),
    imprim      INTEGER        DEFAULT -1,
    soldepadebi NUMERIC(14, 2) DEFAULT 0.00,
    soldepacred NUMERIC(14, 2),
    PRIMARY KEY (dateoper, codburpo, numeordr)
);

-- Tables supplémentaires du microservice Edition
CREATE TABLE IF NOT EXISTS "rapport"
(
    id           SERIAL PRIMARY KEY,
    titre        VARCHAR(100) NOT NULL,
    description  TEXT,
    typerapport  VARCHAR(50)  NOT NULL,
    dateCreation TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS "parametreRapport"
(
    id        SERIAL PRIMARY KEY,
    idRapport INTEGER     NOT NULL REFERENCES "rapport" (id),
    nom       VARCHAR(50) NOT NULL,
    valeur    TEXT,
    type      VARCHAR(30)
);

CREATE TABLE IF NOT EXISTS "clientCCP100"
(
    idencomp NUMERIC(12, 0) NOT NULL,
    inticomp VARCHAR(120),
    adrecomp VARCHAR(120),
    libsocpr VARCHAR(40),
    codburpo NUMERIC(5, 0),
    desburpo VARCHAR(60),
    libesrp  VARCHAR(60),
    soldcour NUMERIC(14, 2)
);
-- V2__Create_Tables_CCP.sql

-- Type d'opération CCP
CREATE TABLE IF NOT EXISTS typeOperationCCP
(
    codtypop NUMERIC(4, 0) NOT NULL,
    libtypop VARCHAR(80),
    natuoper VARCHAR(1),
    joudatva SMALLINT,
    codfamop NUMERIC(2, 0),
    codpgmco VARCHAR(8),
    codecrsa VARCHAR(8),
    cotyopte NUMERIC(3, 0),
    cosoopte NUMERIC(2, 0),
    nbrepoin NUMERIC(8, 1),
    cpteordr NUMERIC(12, 0),
    cptetaxe NUMERIC(12, 0),
    cptedebo NUMERIC(12, 0),
    cptedeco NUMERIC(12, 0),
    debeauto VARCHAR(1),
    delrepfo NUMERIC(4, 0),
    grouemis NUMERIC(3, 0),
    groupaie NUMERIC(3, 0),
    priooper INTEGER,
    cptereje NUMERIC(12, 0),
    cptataxe CHAR(1),
    cpttaxte NUMERIC(12, 0),
    deptaxpa VARCHAR(1),
    codearti NUMERIC(4),
    PRIMARY KEY (codtypop)
);

-- Catégorie socio-professionnelle CCP
CREATE TABLE IF NOT EXISTS catSocioProfCCP
(
    codsocpr NUMERIC(3, 0) NOT NULL,
    libsocpr VARCHAR(40),
    codtyppe VARCHAR(2),
    cosfacas NUMERIC(1, 0),
    codcatcl NUMERIC(2, 0),
    cofacasp NUMERIC(1, 0),
    PRIMARY KEY (codsocpr)
);

-- Bureau poste CCP
CREATE TABLE IF NOT EXISTS bureauPosteCCP
(
    codburpo NUMERIC(5, 0) NOT NULL,
    desburpo VARCHAR(60),
    adrburpo VARCHAR(60),
    codepost VARCHAR(7),
    numetele VARCHAR(15),
    nume_fax VARCHAR(15),
    numetelx VARCHAR(15),
    typeequi VARCHAR(1),
    coderegi NUMERIC(2, 0),
    codeprov NUMERIC(3, 0),
    codecomm NUMERIC(4, 0),
    cobupora NUMERIC(5, 0),
    cptecabp NUMERIC(12, 0),
    chaindsn VARCHAR(20),
    nbreessa NUMERIC(3, 0),
    codville NUMERIC(3, 0),
    PRIMARY KEY (codburpo)
);

-- Client CCP
CREATE TABLE IF NOT EXISTS clientCCP
(
    idenclie  NUMERIC(8, 0) NOT NULL,
    codsocpr  NUMERIC(3, 0),
    desiclie  VARCHAR(120),
    adreclie  VARCHAR(120),
    codepost  VARCHAR(7),
    codepays  VARCHAR(4),
    numetele  VARCHAR(12),
    nume_fax  VARCHAR(12),
    numetelx  VARCHAR(10),
    datenais  DATE,
    lieunaiss VARCHAR(30),
    sexeclie  VARCHAR(1),
    situfami  VARCHAR(1),
    typepiec  VARCHAR(1),
    numpieid  VARCHAR(15),
    datpieid  DATE,
    numedoti  VARCHAR(8),
    numematr  VARCHAR(15),
    codetati  NUMERIC(4, 0),
    codforju  VARCHAR(4),
    numregco  VARCHAR(20),
    numepate  VARCHAR(11),
    numecnss  VARCHAR(10),
    numercar  VARCHAR(10),
    nume_cmr  VARCHAR(10),
    numecimr  VARCHAR(10),
    numecfps  VARCHAR(10),
    numecnop  VARCHAR(10),
    nume_cen  NUMERIC(10, 0),
    nume_amo  VARCHAR(16),
    datecrea  DATE,
    ordrcpte  SMALLINT,
    datogmaj  DATE,
    bpogemaj  NUMERIC(5, 0),
    ordogmaj  NUMERIC(6, 0),
    PRIMARY KEY (idenclie)
);

-- Compte CCP
CREATE TABLE IF NOT EXISTS compteCCP
(
    idencomp NUMERIC(12, 0) NOT NULL,
    codcatcp NUMERIC(2, 0),
    idenclie NUMERIC(8, 0),
    compcomp VARCHAR(4),
    cle_comp SMALLINT,
    inticomp VARCHAR(120),
    inticond VARCHAR(60),
    adrecomp VARCHAR(120),
    codepost VARCHAR(7),
    dateouve DATE,
    perirele VARCHAR(1),
    numepate VARCHAR(11),
    numregco VARCHAR(20),
    soldminu NUMERIC(14, 2),
    soldcour NUMERIC(14, 2),
    datesold DATE,
    solopenc NUMERIC(14, 2),
    soldoppo NUMERIC(14, 2),
    soldtaxe NUMERIC(14, 2),
    solddebo NUMERIC(14, 2),
    solddeco NUMERIC(14, 2),
    solopede NUMERIC(14, 2),
    soldcert NUMERIC(14, 2),
    totadebe NUMERIC(14, 2),
    nochsapr SMALLINT,
    codbpcpt NUMERIC(5, 0),
    codetacp VARCHAR(1),
    cmptmand VARCHAR(1),
    commulti VARCHAR(1),
    cptedeco VARCHAR(1),
    credregu VARCHAR(1),
    daminscl DATE,
    comoclco NUMERIC(2, 0),
    dateclot DATE,
    refdeman VARCHAR(30),
    servdoss VARCHAR(30),
    numedoss VARCHAR(8),
    codbpini NUMERIC(5, 0),
    datogmaj DATE,
    bpogemaj NUMERIC(5, 0),
    ordogmaj NUMERIC(6, 0),
    exontaxe VARCHAR(1),
    dadeopfi DATE,
    adresign NUMERIC(9, 0),
    soltaxte NUMERIC(14, 2),
    codepinn NUMERIC(4, 0),
    codeprod NUMERIC(3, 0),
    PRIMARY KEY (idencomp)
);

-- Mouvements financiers CCP
CREATE TABLE IF NOT EXISTS mvtFinancierCCP
(
    cptemouv     NUMERIC(12, 0) NOT NULL,
    datemouv     DATE           NOT NULL,
    numemouv     INTEGER        NOT NULL,
    sensmouv     VARCHAR(1)     NOT NULL,
    montmouv     NUMERIC(20, 2),
    datevale     DATE,
    solddepa     NUMERIC(14, 2),
    codburpo     NUMERIC(5, 0)  NOT NULL,
    numeordr     NUMERIC(6, 0)  NOT NULL,
    dateoper     DATE           NOT NULL,
    codtypop     NUMERIC(4, 0),
    refopeor     VARCHAR(30),
    menmarmo     VARCHAR(120),
    natumouv     VARCHAR(1),
    nom_tire     VARCHAR(100),
    datcreatemvt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (numemouv, cptemouv, datemouv, codtypop)
);
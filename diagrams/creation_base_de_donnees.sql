-- PostgreSQL version of the Informix tables
-- Note: Some data types and constraints have been adjusted for PostgreSQL compatibility

-- Table: sgc_ref_agence
CREATE TABLE sgc_ref_agence (
                                codburpo VARCHAR(6) NOT NULL,
                                codreg INTEGER,
                                libreg VARCHAR(200),
                                typbur VARCHAR(10),
                                chaindsn VARCHAR(20),
                                ip VARCHAR(20) NOT NULL,
                                src_cre VARCHAR(4),
                                pole VARCHAR(2),
                                codezone VARCHAR(3),
                                libelle_zone VARCHAR(50),
                                codegroupe VARCHAR(6),
                                libelle_groupe VARCHAR(50),
                                codelocalite VARCHAR(3),
                                libelle_localite VARCHAR(50),
                                categorie VARCHAR(3),
                                responsable VARCHAR(200),
                                remontee VARCHAR(1),
                                statut VARCHAR(1),
                                codburpo_ra VARCHAR(6),
                                observation VARCHAR(100),
                                typeconnexion VARCHAR(5),
                                codelocbkam VARCHAR(4),
                                liblocbkam VARCHAR(50),
                                codregbkam VARCHAR(2),
                                libregbkam VARCHAR(50),
                                adresse VARCHAR(200),
                                codburbkam VARCHAR(5),
                                codesrbm VARCHAR(2),
                                codebkam VARCHAR(4),
                                ips VARCHAR(10),
                                sig VARCHAR(10),
                                codepostale VARCHAR(10),
                                telephone_fixe VARCHAR(15),
                                telephone_mobile VARCHAR(15),
                                catagence VARCHAR(10),
                                codburpo_wu VARCHAR(10),
                                libelle_burpo VARCHAR(200),
                                code_pole INTEGER,
                                deploye VARCHAR(1),
                                datedeploiement DATE,
                                adeployer VARCHAR(1),
                                matriculereceveur VARCHAR(10),
                                typeagence INTEGER,
                                faxe VARCHAR(20),
                                email VARCHAR(20),
                                PRIMARY KEY(codburpo)
);

-- Table: ctr_souscription_pack
CREATE TABLE ctr_souscription_pack (
                                       idsoupackprod SERIAL NOT NULL,
                                       idencomp DECIMAL(12) NOT NULL,
                                       idcontrat INTEGER,
                                       codeprod DECIMAL(5),
                                       idenclie DECIMAL(10) NOT NULL,
                                       statut VARCHAR(1),
                                       date_statut TIMESTAMP(0),
                                       montant DECIMAL(20),
                                       usersaisi DECIMAL(7),
                                       codburposai DECIMAL(5),
                                       date_saisi TIMESTAMP,
                                       numordog DECIMAL(6),
                                       usermaj DECIMAL(7),
                                       codburpomaj DECIMAL(5),
                                       datemaj DATE,
                                       userres DECIMAL(7),
                                       codburpores DECIMAL(5),
                                       dateres DATE,
                                       date_premier_prel DATE,
                                       date_proch_prel DATE,
                                       identifiant_extern VARCHAR(40),
                                       PRIMARY KEY(idsoupackprod)
);

-- Table: ctr_produit
CREATE TABLE ctr_produit (
                             codeprod DECIMAL(5) NOT NULL,
                             libeprod VARCHAR(200),
                             com_com DECIMAL(2),
                             datdebeff TIMESTAMP(0),
                             datfineff TIMESTAMP(0),
                             commercialiseon VARCHAR(1),
                             idfamprod INTEGER,
                             unicite_acces VARCHAR(1),
                             redirection_obli VARCHAR(1),
                             url_sousc VARCHAR(255),
                             rattach CHAR(1),
                             tarif DECIMAL(15,5),
                             localis_exige VARCHAR(1),
                             ordre INTEGER,
                             statut_deploiement INTEGER DEFAULT NULL,
                             codprodrempl DECIMAL(16),
                             PRIMARY KEY(codeprod)
);

-- Table: ctr_pack
CREATE TABLE ctr_pack (
                          codepack DECIMAL(5) NOT NULL,
                          codeperi VARCHAR(2),
                          libepack VARCHAR(80) NOT NULL,
                          datdebeff DATE NOT NULL,
                          datfineff DATE,
                          montant DECIMAL(15,5),
                          codtypop DECIMAL(15,5),
                          codpackrempl DECIMAL(15,5),
                          cond_general VARCHAR(255),
                          localis_exige VARCHAR(1),
                          url_clause_juridique VARCHAR(255),
                          url_entete VARCHAR(255),
                          url_resiliation VARCHAR(255),
                          statut_deploiement INTEGER DEFAULT NULL,
                          prelevement_differe INTEGER,
                          conventionne VARCHAR(1),
                          ordre INTEGER,
                          est_conv_obligatoire BOOLEAN,
                          nat_migration DECIMAL(16),
                          PRIMARY KEY(codepack)
);

-- Table: situation_juridiqu
CREATE TABLE situation_juridiqu (
                                    codsitju DECIMAL(2) NOT NULL,
                                    libesitu VARCHAR(32),
                                    datdebeff DATE,
                                    datfineff DATE,
                                    PRIMARY KEY(codsitju)
);

-- Table: compte
CREATE TABLE compte (
                        codcatcp DECIMAL(3) NOT NULL,
                        codscatcp DECIMAL(2) NOT NULL,
                        idencomp DECIMAL(12) NOT NULL,
                        idenclie DECIMAL(10) NOT NULL,
                        inticomp VARCHAR(120) NOT NULL,
                        dateouve DATE NOT NULL,
                        dateclot DATE,
                        datogmaj DATE,
                        bloqadr CHAR(1),
                        datetacp DATE,
                        n_sig INTEGER,
                        codeinst DECIMAL(1),
                        codclaure DECIMAL(2),
                        codetacp VARCHAR(1) NOT NULL,
                        cle_comp SMALLINT,
                        codebpcpt DECIMAL(5,0) NOT NULL,
                        dateremb DATE,
                        cmptmand CHAR(1),
                        cptedeco CHAR(1),
                        codbpini DECIMAL(5,0),
                        usrgestcp VARCHAR(7) NOT NULL,
                        comoetco DECIMAL(3,0),
                        codepinn DECIMAL(4),
                        comptmon CHAR(1),
                        nserliv DECIMAL(7),
                        typerela VARCHAR(2),
                        perirele VARCHAR(2),
                        codedevi VARCHAR(4),
                        PRIMARY KEY(idencomp)
);

-- Table: client
CREATE TABLE client (
                        idenclie DECIMAL(10) NOT NULL,
                        cateclie DECIMAL(2) NOT NULL,
                        typeclie VARCHAR(5),
                        nomrais VARCHAR(120) NOT NULL,
                        prenclie VARCHAR(20),
                        civiclie VARCHAR(5),
                        nume_telb VARCHAR(15),
                        nume_teld VARCHAR(15),
                        nume_gsm VARCHAR(15),
                        nume_fax VARCHAR(15),
                        datenais DATE,
                        lieunais VARCHAR(30),
                        sexeclie VARCHAR(2),
                        typepiec VARCHAR(1),
                        numpieid VARCHAR(15),
                        datpieid DATE,
                        revemens DECIMAL(15,2),
                        situfami DECIMAL(2),
                        situjuri DECIMAL(2),
                        codsocpr DECIMAL(3),
                        sectacti VARCHAR(5),
                        ageneco VARCHAR(5),
                        codforju DECIMAL(2),
                        statclie DECIMAL(1),
                        numregco VARCHAR(20),
                        adremail VARCHAR(60),
                        numepate VARCHAR(11),
                        numevisa VARCHAR(11),
                        capisoci DECIMAL(14),
                        nombempl INTEGER,
                        centrc VARCHAR(4),
                        titre VARCHAR(2),
                        domisala VARCHAR(1),
                        misecont VARCHAR(1),
                        segment INTEGER,
                        cotarisq VARCHAR(8),
                        datecrea DATE,
                        datogmaj DATE,
                        proplog VARCHAR(1),
                        idenbam VARCHAR(12),
                        numcenrs VARCHAR(15),
                        agegescl DECIMAL(5,0),
                        usegescl VARCHAR(7),
                        codetati DECIMAL(4),
                        codpayrs VARCHAR(2),
                        codenate VARCHAR(2),
                        donneerro VARCHAR(1),
                        cliefiab SMALLINT NOT NULL,
                        cliepros SMALLINT NOT NULL,
                        nompere VARCHAR(50),
                        nommere VARCHAR(50),
                        precture INTEGER,
                        patente VARCHAR(50),
                        codpaysnaiss VARCHAR(2),
                        codpayspass VARCHAR(2),
                        adressemailnov VARCHAR(120),
                        code_fdc_user INTEGER,
                        code_info_clt INTEGER,
                        PRIMARY KEY(idenclie)
);

-- -- Foreign keys for client table (assumed these referenced tables exist)
-- ALTER TABLE client
--     ADD CONSTRAINT fkthr5eeeoo336w953u5gjgt72s
--         FOREIGN KEY (civiclie) REFERENCES civilite(codecivi);
--
-- ALTER TABLE client
--     ADD CONSTRAINT fkof2nsvistkqdb47gtc4iatpo8
--         FOREIGN KEY (statclie) REFERENCES statut_client(codstacl);
--
-- ALTER TABLE client
--     ADD CONSTRAINT fkjvnjxmuoagml5w76lqamd65rc
--         FOREIGN KEY (sexeclie) REFERENCES sexe(codesexe);
--
-- ALTER TABLE client
--     ADD CONSTRAINT fk_client_info
--         FOREIGN KEY (code_info_clt) REFERENCES bdd_info_clt(code);
--
-- ALTER TABLE client
--     ADD CONSTRAINT fk9qplxq94c8j6hudaecl4ob1sq
--         FOREIGN KEY (codenate) REFERENCES pays(codepays);

-- Table: cate_socio_prof
CREATE TABLE cate_socio_prof (
                                 codsocpr DECIMAL(3) NOT NULL,
                                 libsocpr VARCHAR(200) NOT NULL,
                                 cofacasp DECIMAL(2) NOT NULL,
                                 datdebeff DATE NOT NULL,
                                 datfineff DATE,
                                 PRIMARY KEY(codsocpr)
);

-- Table: adresse_link
CREATE TABLE adresse_link (
                              numeiden SERIAL NOT NULL,
                              idenclie DECIMAL(10) NOT NULL,
                              idencomp DECIMAL(12),
                              codtypad VARCHAR(2) NOT NULL,
                              codcatad VARCHAR(2) NOT NULL,
                              intitule1 VARCHAR(120),
                              intitule2 VARCHAR(60),
                              intitule3 VARCHAR(60),
                              intitule4 VARCHAR(60),
                              codepays VARCHAR(2),
                              codevill INTEGER,
                              codepost VARCHAR(5),
                              codburpo DECIMAL(5,0),
                              datogmaj DATE,
                              codburog DECIMAL(5,0),
                              numordog DECIMAL(6,0),
                              libeville VARCHAR(100),
                              PRIMARY KEY(numeiden)
);

-- Table: ctr_contrat
CREATE TABLE ctr_contrat (
                             idcontrat SERIAL NOT NULL,
                             numcontrat VARCHAR(20),
                             idenclie DECIMAL(10) NOT NULL,
                             idencomp DECIMAL(12) NOT NULL,
                             codtypcontrat DECIMAL(5),
                             codeprod DECIMAL(5),
                             sousc_statut VARCHAR(2),
                             codmotif DECIMAL(5),
                             codepack DECIMAL(5),
                             codtyptarif DECIMAL(5),
                             usersaisi DECIMAL(7),
                             numordog DECIMAL(6),
                             usermaj DECIMAL(7),
                             datemaj TIMESTAMP(0),
                             userres DECIMAL(7),
                             codburpores DECIMAL(5),
                             codburpomaj DECIMAL(5),
                             codburposai DECIMAL(5),
                             dateres TIMESTAMP(0),
                             date_premier_prel TIMESTAMP(0),
                             sousc_date TIMESTAMP(0),
                             date_proch_prel DATE,
                             montant_res DECIMAL(20),
                             echeance_res DECIMAL(5),
                             datedernierprelv TIMESTAMP(0),
                             ref_externe VARCHAR(25),
                             contrat_attach INTEGER,
                             montant DECIMAL(15,5),
                             codeconv DECIMAL(15,5),
                             dt_basculement TIMESTAMP(0),
                             idoldpack DECIMAL(5),
                             idoldconv DECIMAL(5),
                             dt_modifconv TIMESTAMP(0),
                             canal_sousc INTEGER,
                             canal_resil INTEGER,
                             PRIMARY KEY(idcontrat)
);

-- Add foreign key relationships between tables
ALTER TABLE ctr_souscription_pack
    ADD CONSTRAINT fk_souspacprod_idenclie
        FOREIGN KEY (idenclie) REFERENCES client(idenclie);

ALTER TABLE ctr_souscription_pack
    ADD CONSTRAINT fk_souspacprod_idencomp
        FOREIGN KEY (idencomp) REFERENCES compte(idencomp);

ALTER TABLE ctr_souscription_pack
    ADD CONSTRAINT fk_souspacprod_codeprod
        FOREIGN KEY (codeprod) REFERENCES ctr_produit(codeprod);

ALTER TABLE compte
    ADD CONSTRAINT fk_compte_idenclie
        FOREIGN KEY (idenclie) REFERENCES client(idenclie);

ALTER TABLE ctr_contrat
    ADD CONSTRAINT fk_contrat_idenclie
        FOREIGN KEY (idenclie) REFERENCES client(idenclie);

ALTER TABLE ctr_contrat
    ADD CONSTRAINT fk_contrat_idencomp
        FOREIGN KEY (idencomp) REFERENCES compte(idencomp);

ALTER TABLE ctr_contrat
    ADD CONSTRAINT fk_contrat_codeprod
        FOREIGN KEY (codeprod) REFERENCES ctr_produit(codeprod);

ALTER TABLE ctr_contrat
    ADD CONSTRAINT fk_contrat_codepack
        FOREIGN KEY (codepack) REFERENCES ctr_pack(codepack);

ALTER TABLE adresse_link
    ADD CONSTRAINT fk_adresselink_idenclie
        FOREIGN KEY (idenclie) REFERENCES client(idenclie);

ALTER TABLE adresse_link
    ADD CONSTRAINT fk_adresselink_idencomp
        FOREIGN KEY (idencomp) REFERENCES compte(idencomp);

-- Comment on tables for documentation
COMMENT ON TABLE sgc_ref_agence IS 'Agency reference table';
COMMENT ON TABLE ctr_souscription_pack IS 'Pack subscription table';
COMMENT ON TABLE ctr_produit IS 'Product table';
COMMENT ON TABLE ctr_pack IS 'Pack table';
COMMENT ON TABLE situation_juridiqu IS 'Legal situation table';
COMMENT ON TABLE compte IS 'Account table';
COMMENT ON TABLE client IS 'Client table';
COMMENT ON TABLE cate_socio_prof IS 'Socio-professional category table';
COMMENT ON TABLE adresse_link IS 'Address link table';
COMMENT ON TABLE ctr_contrat IS 'Contract table';
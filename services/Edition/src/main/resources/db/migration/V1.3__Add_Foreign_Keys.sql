-- V1.3__Add_Foreign_Keys.sql

-- Relations pour les tables CCP
ALTER TABLE "bureauPosteCCP"
    ADD CONSTRAINT fk_bureau_poste_parent
        FOREIGN KEY (cobupora) REFERENCES "bureauPosteCCP" (codburpo);

ALTER TABLE "clientCCP"
    ADD CONSTRAINT fk_client_ccp_socio_prof
        FOREIGN KEY (codsocpr) REFERENCES "catSocioProfCCP" (codsocpr);

ALTER TABLE "compteCCP"
    ADD CONSTRAINT fk_compte_ccp_client
        FOREIGN KEY (idenclie) REFERENCES "clientCCP" (idenclie);

ALTER TABLE "compteCCP"
    ADD CONSTRAINT fk_compte_ccp_bureau
        FOREIGN KEY (codbpcpt) REFERENCES "bureauPosteCCP" (codburpo);

ALTER TABLE "mvtFinancierCCP"
    ADD CONSTRAINT fk_mvt_ccp_compte
        FOREIGN KEY (cptemouv) REFERENCES "compteCCP" (idencomp);

ALTER TABLE "mvtFinancierCCP"
    ADD CONSTRAINT fk_mvt_ccp_type_op
        FOREIGN KEY (codtypop) REFERENCES "typeOperationCCP" (codtypop);

-- Relations pour les tables CEN
ALTER TABLE "burePostCEN"
    ADD CONSTRAINT fk_bureau_post_cen_parent
        FOREIGN KEY (cobupora) REFERENCES "burePostCEN" (codburpo);

ALTER TABLE "clientCEN"
    ADD CONSTRAINT fk_client_cen_socio_prof
        FOREIGN KEY (cocasopr) REFERENCES "catSociProfCEN" (cocasopr);

ALTER TABLE "compteCEN"
    ADD CONSTRAINT fk_compte_cen_client
        FOREIGN KEY (idenclie) REFERENCES "clientCEN" (idenclie);

ALTER TABLE "compteCEN"
    ADD CONSTRAINT fk_compte_cen_bureau
        FOREIGN KEY (codburpo) REFERENCES "burePostCEN" (codburpo);

ALTER TABLE "operCompCEN"
    ADD CONSTRAINT fk_oper_cen_type_op
        FOREIGN KEY (codtypop) REFERENCES "typeOperCEN" (codtypop);
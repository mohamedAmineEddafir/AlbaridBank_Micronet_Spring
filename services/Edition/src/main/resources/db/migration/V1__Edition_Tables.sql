-- Tables spécifiques au microservice Edition

-- Création de la table rapport
CREATE TABLE IF NOT EXISTS rapport (
    id BIGSERIAL PRIMARY KEY,
    titre VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    typerapport VARCHAR(50),
    date_creation TIMESTAMP,
    nom_fichier VARCHAR(100),
    contenu_fichier BYTEA
);

-- Création de la table parametre_rapport
CREATE TABLE IF NOT EXISTS parametre_rapport (
    id BIGSERIAL PRIMARY KEY,
    id_rapport BIGINT NOT NULL,
    nom VARCHAR(50) NOT NULL,
    valeur VARCHAR(200),
    type VARCHAR(20),
    CONSTRAINT fk_rapport FOREIGN KEY (id_rapport) REFERENCES rapport(id)
);

-- Ajout d'index pour les tables du module Edition
CREATE INDEX IF NOT EXISTS idx_rapport_typerapport ON rapport(typerapport);
CREATE INDEX IF NOT EXISTS idx_parametre_rapport_id_rapport ON parametre_rapport(id_rapport);
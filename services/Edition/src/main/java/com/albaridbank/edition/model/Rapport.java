package com.albaridbank.edition.model;

    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import jakarta.persistence.*;
    import org.hibernate.annotations.JdbcTypeCode;
    import org.hibernate.type.SqlTypes;

    import java.time.LocalDateTime;
    import java.util.HashSet;
    import java.util.Set;

    /**
     * Entity class representing a report.
     * This class is mapped to the "rapport" table in the database.
     * It contains information about the report such as title, description, type, creation date, file name, and file content.
     * It also has a relationship with the ParametreRapport entity.
     * @author Mohamed Amine Eddafir
     */
    @Entity
    @Table(name = "rapport")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Rapport {

        /**
         * The unique identifier for the report.
         * This value is generated automatically.
         */
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long id;

        /**
         * The title of the report.
         * This field is required and has a maximum length of 100 characters.
         */
        @Column(name = "titre", length = 100, nullable = false)
        private String titre;

        /**
         * A brief description of the report.
         * This field is optional and has a maximum length of 255 characters.
         */
        @Column(name = "description", length = 255)
        private String description;

        /**
         * The type of the report.
         * This field has a maximum length of 50 characters.
         */
        @Column(name = "typerapport", length = 50)
        private String typeRapport;

        /**
         * The date and time when the report was created.
         */
        @Column(name = "date_creation")
        private LocalDateTime dateCreation;

        /**
         * The name of the file associated with the report.
         * This field has a maximum length of 100 characters.
         */
        @Column(name = "nom_fichier", length = 100)
        private String nomFichier;

        /**
         * The content of the file associated with the report.
         * This field is stored as a binary large object (BLOB) in the database.
         */
        @Column(name = "contenu_fichier")
        @JdbcTypeCode(SqlTypes.BINARY) // L'annotation @JdbcTypeCode(SqlTypes.BINARY) remplace la combinaison @Lob pour les versions récentes d'Hibernate
        private byte[] contenuFichier;

        /**
         * The set of parameters associated with the report.
         * This field is mapped by the "rapport" field in the ParametreRapport entity.
         * The cascade type is set to ALL, and orphan removal is enabled.
         */
        @OneToMany(mappedBy = "rapport", cascade = CascadeType.ALL, orphanRemoval = true)
        private Set<ParametreRapport> parametres = new HashSet<>();
    }
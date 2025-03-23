package com.albaridbank.BankinApp.models;

    import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import jakarta.persistence.*;
    import jakarta.validation.constraints.NotBlank;

    import java.io.Serial;
    import java.io.Serializable;
    import java.time.LocalDate;

    /**
     * Entity class representing a link between an address and a client or account.
     * This class is mapped to the "adresse_link" table in the database.
     * Implements Serializable for object serialization.
     */
    @Entity
    @Table(name = "adresse_link", indexes = {
            @Index(name = "idx_adresselink_idenclie", columnList = "idenclie"),
            @Index(name = "idx_adresselink_idencomp", columnList = "idencomp")
    })
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class AdresseLink implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;

        /**
         * Unique identifier for the AdresseLink.
         */
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "numeiden")
        private Long numeiden;

        /**
         * The client associated with this address link.
         */
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "idenclie", nullable = false)
        @JsonIgnoreProperties({"adresses", "hibernateLazyInitializer", "handler"})
        private Client client;

        /**
         * The account associated with this address link.
         */
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "idencomp")
        @JsonIgnoreProperties({"adresseLinks", "hibernateLazyInitializer", "handler"})
        private Compte compte;

        /**
         * Code representing the type of address.
         * Cannot be empty.
         */
        @NotBlank(message = "Address type code cannot be empty")
        @Column(name = "codtypad", nullable = false, length = 2)
        private String codtypad;

        /**
         * Code representing the category of address.
         * Cannot be empty.
         */
        @NotBlank(message = "Address category code cannot be empty")
        @Column(name = "codcatad", nullable = false, length = 2)
        private String codcatad;

        /**
         * First line of the address.
         */
        @Column(name = "intitule1", length = 120)
        private String intitule1;

        /**
         * Second line of the address.
         */
        @Column(name = "intitule2", length = 60)
        private String intitule2;

        /**
         * Third line of the address.
         */
        @Column(name = "intitule3", length = 60)
        private String intitule3;

        /**
         * Fourth line of the address.
         */
        @Column(name = "intitule4", length = 60)
        private String intitule4;

        /**
         * Country code of the address.
         */
        @Column(name = "codepays", length = 2)
        private String codepays;

        /**
         * City code of the address.
         */
        @Column(name = "codevill")
        private Integer codevill;

        /**
         * Postal code of the address.
         */
        @Column(name = "codepost", length = 5)
        private String codepost;

        /**
         * Post office code of the address.
         */
        @Column(name = "codburpo")
        private Long codburpo;

        /**
         * Date of the last update.
         */
        @Column(name = "datogmaj")
        @Temporal(TemporalType.DATE)
        private LocalDate datogmaj;

        /**
         * Office code of the address.
         */
        @Column(name = "codburog")
        private Long codburog;

        /**
         * Order number of the address.
         */
        @Column(name = "numordog")
        private Long numordog;

        /**
         * City name of the address.
         */
        @Column(name = "libeville", length = 100)
        private String libeville;
    }
package com.albaridbank.BankinApp.models;

    import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
    import jakarta.persistence.*;
    import jakarta.validation.constraints.Email;
    import jakarta.validation.constraints.NotBlank;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;
    import lombok.ToString;

    import java.io.Serial;
    import java.io.Serializable;
    import java.math.BigDecimal;
    import java.util.Date;
    import java.util.List;

    /**
     * Entity class representing a client.
     * This class is mapped to the "client" table in the database.
     * Implements Serializable for object serialization.
     */
    @Entity
    @Table(name = "client", indexes = {
            @Index(name = "idx_client_statclie", columnList = "statclie"),
            @Index(name = "idx_client_typeclie", columnList = "typeclie"),
            @Index(name = "idx_client_nomrais", columnList = "nomrais")
    })
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString(exclude = {"comptes", "adresses"}) // Prevent circular references in toString()
    public class Client implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;

        /**
         * Unique identifier for the Client.
         */
        @Id
        @Column(name = "idenclie", nullable = false, precision = 10)
        private BigDecimal idenclie;

        /**
         * Category of the client.
         */
        @Column(name = "cateclie", nullable = false)
        private Integer cateclie;

        /**
         * Type of the client.
         */
        @Column(name = "typeclie", length = 5)
        private String typeclie;

        /**
         * Name of the client.
         * Cannot be empty.
         */
        @NotBlank(message = "Client name cannot be empty")
        @Column(name = "nomrais", nullable = false, length = 120)
        private String nomrais;

        /**
         * First name of the client.
         */
        @Column(name = "prenclie", length = 20)
        private String prenclie;

        /**
         * Civic number of the client.
         */
        @Column(name = "civiclie", length = 5)
        private String civiclie;

        /**
         * Business phone number of the client.
         */
        @Column(name = "nume_telb", length = 15)
        private String numeTelb;

        /**
         * Home phone number of the client.
         */
        @Column(name = "nume_teld", length = 15)
        private String numeTeld;

        /**
         * Mobile phone number of the client.
         */
        @Column(name = "nume_gsm", length = 15)
        private String numeGsm;

        /**
         * Fax number of the client.
         */
        @Column(name = "nume_fax", length = 15)
        private String numeFax;

        /**
         * Date of birth of the client.
         */
        @Column(name = "datenais")
        @Temporal(TemporalType.DATE)
        private Date datenais;

        /**
         * Place of birth of the client.
         */
        @Column(name = "lieunais", length = 30)
        private String lieunais;

        /**
         * Gender of the client.
         */
        @Column(name = "sexeclie", length = 2)
        private String sexeclie;

        /**
         * Type of identification document.
         */
        @Column(name = "typepiec", length = 1)
        private String typepiec;

        /**
         * Identification document number.
         */
        @Column(name = "numpieid", length = 15)
        private String numpieid;

        /**
         * Date of issue of the identification document.
         */
        @Column(name = "datpieid")
        @Temporal(TemporalType.DATE)
        private Date datpieid;

        /**
         * Monthly income of the client.
         */
        @Column(name = "revemens", precision = 15, scale = 2)
        private BigDecimal revemens;

        /**
         * Family situation of the client.
         */
        @Column(name = "situfami")
        private Integer situfami;

        /**
         * Juridical situation of the client.
         */
        @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "clients"})
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "situjuri", referencedColumnName = "codsitju")
        private SituationJuridiqu situationJuridiqu;

        /**
         * Socio-professional category of the client.
         */
        @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "clients"})
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "codsocpr", referencedColumnName = "codsocpr")
        private CateSocioProf cateSocioProf;

        /**
         * Activity sector of the client.
         */
        @Column(name = "sectacti", length = 5)
        private String sectacti;

        /**
         * Economic agent code.
         */
        @Column(name = "ageneco", length = 5)
        private String ageneco;

        /**
         * Juridical form code.
         */
        @Column(name = "codforju")
        private Integer codforju;

        /**
         * Client status.
         */
        @Column(name = "statclie")
        private Integer statclie;

        /**
         * Registration number.
         */
        @Column(name = "numregco", length = 20)
        private String numregco;

        /**
         * Email address of the client.
         * Must be a valid email format.
         */
        @Email(message = "Invalid email format")
        @Column(name = "adremail", length = 60)
        private String adremail;

        /**
         * Patent number.
         */
        @Column(name = "numepate", length = 11)
        private String numepate;

        /**
         * Visa number.
         */
        @Column(name = "numevisa", length = 11)
        private String numevisa;

        /**
         * Social capital of the client.
         */
        @Column(name = "capisoci", precision = 14)
        private BigDecimal capisoci;

        /**
         * Number of employees.
         */
        @Column(name = "nombempl")
        private Integer nombempl;

        /**
         * Center code.
         */
        @Column(name = "centrc", length = 4)
        private String centrc;

        /**
         * Title of the client.
         */
        @Column(name = "titre", length = 2)
        private String titre;

        /**
         * Salary domicile indicator.
         */
        @Column(name = "domisala", length = 1)
        private String domisala;

        /**
         * Contact indicator.
         */
        @Column(name = "misecont", length = 1)
        private String misecont;

        /**
         * Segment of the client.
         */
        @Column(name = "segment")
        private Integer segment;

        /**
         * Risk rating code.
         */
        @Column(name = "cotarisq", length = 8)
        private String cotarisq;

        /**
         * Creation date of the client record.
         */
        @Column(name = "datecrea")
        @Temporal(TemporalType.DATE)
        private Date datecrea;

        /**
         * Last update date of the client record.
         */
        @Column(name = "datogmaj")
        @Temporal(TemporalType.DATE)
        private Date datogmaj;

        /**
         * Housing ownership indicator.
         */
        @Column(name = "proplog", length = 1)
        private String proplog;

        /**
         * Bank identifier.
         */
        @Column(name = "idenbam", length = 12)
        private String idenbam;

        /**
         * Central registry number.
         */
        @Column(name = "numcenrs", length = 15)
        private String numcenrs;

        /**
         * Client management age.
         */
        @Column(name = "agegescl", precision = 5)
        private BigDecimal agegescl;

        /**
         * Client management user.
         */
        @Column(name = "usegescl", length = 7)
        private String usegescl;

        /**
         * Client status code.
         */
        @Column(name = "codetati")
        private Integer codetati;

        /**
         * Country code of residence.
         */
        @Column(name = "codpayrs", length = 2)
        private String codpayrs;

        /**
         * Nationality code.
         */
        @Column(name = "codenate", length = 2)
        private String codenate;

        /**
         * Error data indicator.
         */
        @Column(name = "donneerro", length = 1)
        private String donneerro;

        /**
         * Client reliability indicator.
         */
        @Column(name = "cliefiab", nullable = false)
        private Short cliefiab;

        /**
         * Client prospect indicator.
         */
        @Column(name = "cliepros", nullable = false)
        private Short cliepros;

        /**
         * Father's name.
         */
        @Column(name = "nompere", length = 50)
        private String nompere;

        /**
         * Mother's name.
         */
        @Column(name = "nommere", length = 50)
        private String nommere;

        /**
         * Prefecture code.
         */
        @Column(name = "precture")
        private Integer precture;

        /**
         * Patent number.
         */
        @Column(name = "patente", length = 50)
        private String patente;

        /**
         * Country of birth code.
         */
        @Column(name = "codpaysnaiss", length = 2)
        private String codpaysnaiss;

        /**
         * Passport country code.
         */
        @Column(name = "codpayspass", length = 2)
        private String codpayspass;

        /**
         * New email address.
         * Must be a valid email format.
         */
        @Email(message = "Invalid email format")
        @Column(name = "adressemailnov", length = 120)
        private String adressemailnov;

        /**
         * FDC user code.
         */
        @Column(name = "code_fdc_user")
        private Integer codeFdcUser;

        /**
         * Client information code.
         */
        @Column(name = "code_info_clt")
        private Integer codeInfoClt;

        /**
         * List of accounts associated with this client.
         */
        @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JsonIgnoreProperties("client")
        private List<Compte> comptes;

        /**
         * List of address links associated with this client.
         */
        @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JsonIgnoreProperties("client")
        private List<AdresseLink> adresses;
    }
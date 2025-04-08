package com.albaridbank.BankinApp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Entity class representing a bank account.
 * This class is mapped to the "compte" table in the database.
 * Implements Serializable for object serialization. Database_CCP
 */
@Entity
@Table(name = "compte", indexes = {
        @Index(name = "idx_compte_codcatcp", columnList = "codcatcp"),
        @Index(name = "idx_compte_idenclie", columnList = "idenclie")
})
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(exclude = {"client", "adresses"}) // Prevent circular references
@Builder
public class Compte implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Unique identifier for the Compte.
     */
    @Id
    @Column(name = "idencomp", nullable = false, precision = 12)
    private BigDecimal idencomp;

    /**
     * Code representing the account category.
     * Cannot be null.
     */
    @NotNull(message = "Account category is required")
    @Column(name = "codcatcp", nullable = false, precision = 3)
    private BigDecimal codcatcp;

    /**
     * Code representing the account subcategory.
     * Cannot be null.
     */
    @NotNull(message = "Account subcategory is required")
    @Column(name = "codscatcp", nullable = false, precision = 2)
    private BigDecimal codscatcp;

    /**
     * The client associated with this account.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "idenclie", nullable = false)
    @JsonIgnoreProperties({"comptes", "adresses"})
    private Client client; // Renamed from idenclie for better Java convention

    /**
     * Name of the account.
     * Cannot be empty.
     */
    @NotBlank(message = "Account name is required")
    @Column(name = "inticomp", nullable = false, length = 120)
    private String inticomp;

    /**
     * Date when the account was opened.
     * Cannot be null.
     */
    @NotNull(message = "Opening date is required")
    @Column(name = "dateouve", nullable = false)
    private LocalDate dateouve;

    /**
     * Date when the account was closed.
     */
    @Column(name = "dateclot")
    private LocalDate dateclot;

    /**
     * Date of the last update.
     */
    @Column(name = "datogmaj")
    private LocalDate datogmaj;

    /**
     * Address block indicator.
     */
    @Column(name = "bloqadr", length = 1)
    private Character bloqadr;

    /**
     * Date of the last account transaction.
     */
    @Column(name = "datetacp")
    private LocalDate datetacp;

    /**
     * Signature number.
     */
    @Column(name = "n_sig")
    private Integer NSig;

    /**
     * Institution code.
     */
    @Column(name = "codeinst", precision = 1)
    private BigDecimal codeinst;

    /**
     * Account classification code.
     */
    @Column(name = "codclaure", precision = 2)
    private BigDecimal codclaure;

    /**
     * Status code of the account.
     * Cannot be empty.
     */
    @NotBlank(message = "Account status is required")
    @Column(name = "codetacp", nullable = false, length = 1)
    private String codetacp;

    /**
     * Account key.
     */
    @Column(name = "cle_comp")
    private Short cleComp;

    /**
     * Branch code.
     * Cannot be null.
     */
    @NotNull(message = "Branch code is required")
    @Column(name = "codebpcpt", nullable = false, precision = 5)
    private BigDecimal codebpcpt;

    /**
     * Date of reimbursement.
     */
    @Column(name = "dateremb")
    private LocalDate dateremb;

    /**
     * Mandate account indicator.
     */
    @Column(name = "cmptmand", length = 1)
    private Character cmptmand;

    /**
     * Decoupled account indicator.
     */
    @Column(name = "cptedeco", length = 1)
    private Character cptedeco;

    /**
     * Initial branch code.
     */
    @Column(name = "codbpini", precision = 5)
    private BigDecimal codbpini;

    /**
     * Account manager.
     * Cannot be empty.
     */
    @NotBlank(message = "Account manager is required")
    @Column(name = "usrgestcp", nullable = false, length = 7)
    private String usrgestcp;

    /**
     * Commercial code.
     */
    @Column(name = "comoetco", precision = 3)
    private BigDecimal comoetco;

    /**
     * PIN code.
     */
    @Column(name = "codepinn", precision = 4)
    private BigDecimal codepinn;

    /**
     * Monetary account indicator.
     */
    @Column(name = "comptmon", length = 1)
    private Character comptmon;

    /**
     * Serial number of the book.
     */
    @Column(name = "nserliv", precision = 7)
    private BigDecimal nserliv;

    /**
     * Relationship type code.
     */
    @Column(name = "typerela", length = 2)
    private String typerela;

    /**
     * Relationship period code.
     */
    @Column(name = "perirele", length = 2)
    private String perirele;

    /**
     * Currency code.
     */
    @Column(name = "codedevi", length = 4)
    private String codedevi;

    /**
     * List of address links associated with this account.
     */
    @OneToMany(mappedBy = "compte", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("compte")
    private List<AdresseLink> adresses;
}
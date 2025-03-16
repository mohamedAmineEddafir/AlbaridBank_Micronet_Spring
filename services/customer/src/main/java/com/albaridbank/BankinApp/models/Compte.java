package com.albaridbank.BankinApp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idencomp", nullable = false, precision = 12)
    private BigDecimal idencomp;

    @NotNull(message = "Account category is required")
    @Column(name = "codcatcp", nullable = false, precision = 3)
    private BigDecimal codcatcp;

    @NotNull(message = "Account subcategory is required")
    @Column(name = "codscatcp", nullable = false, precision = 2)
    private BigDecimal codscatcp;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "idenclie", nullable = false)
    @JsonIgnoreProperties({"comptes", "adresses"})
    private Client client; // Renamed from idenclie for better Java convention

    @NotBlank(message = "Account name is required")
    @Column(name = "inticomp", nullable = false, length = 120)
    private String inticomp;

    @NotNull(message = "Opening date is required")
    @Column(name = "dateouve", nullable = false)
    private LocalDate dateouve;

    @Column(name = "dateclot")
    private LocalDate dateclot;

    @Column(name = "datogmaj")
    private LocalDate datogmaj;

    @Column(name = "bloqadr", length = 1)
    private Character bloqadr;

    @Column(name = "datetacp")
    private LocalDate datetacp;

    @Column(name = "n_sig")
    private Integer nSig;

    @Column(name = "codeinst", precision = 1)
    private BigDecimal codeinst;

    @Column(name = "codclaure", precision = 2)
    private BigDecimal codclaure;

    @NotBlank(message = "Account status is required")
    @Column(name = "codetacp", nullable = false, length = 1)
    private String codetacp;

    @Column(name = "cle_comp")
    private Short cleComp;

    @NotNull(message = "Branch code is required")
    @Column(name = "codebpcpt", nullable = false, precision = 5)
    private BigDecimal codebpcpt;

    @Column(name = "dateremb")
    private LocalDate dateremb;

    @Column(name = "cmptmand", length = 1)
    private Character cmptmand;

    @Column(name = "cptedeco", length = 1)
    private Character cptedeco;

    @Column(name = "codbpini", precision = 5)
    private BigDecimal codbpini;

    @NotBlank(message = "Account manager is required")
    @Column(name = "usrgestcp", nullable = false, length = 7)
    private String usrgestcp;

    @Column(name = "comoetco", precision = 3)
    private BigDecimal comoetco;

    @Column(name = "codepinn", precision = 4)
    private BigDecimal codepinn;

    @Column(name = "comptmon", length = 1)
    private Character comptmon;

    @Column(name = "nserliv", precision = 7)
    private BigDecimal nserliv;

    @Column(name = "typerela", length = 2)
    private String typerela;

    @Column(name = "perirele", length = 2)
    private String perirele;

    @Column(name = "codedevi", length = 4)
    private String codedevi;

    @OneToMany(mappedBy = "compte", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("compte")
    private List<AdresseLink> adresses;
}
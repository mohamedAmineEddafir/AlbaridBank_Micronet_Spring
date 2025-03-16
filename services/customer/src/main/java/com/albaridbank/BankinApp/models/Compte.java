package com.albaridbank.BankinApp.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "compte")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class Compte {
    @Id
    @Column(name = "idencomp", nullable = false, precision = 12)
    private BigDecimal idencomp;

    @Column(name = "codcatcp", nullable = false, precision = 3)
    private BigDecimal codcatcp;

    @Column(name = "codscatcp", nullable = false, precision = 2)
    private BigDecimal codscatcp;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "idenclie", nullable = false)
    private Client idenclie;

    @Column(name = "inticomp", nullable = false, length = 120)
    private String inticomp;

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

    @Column(name = "codetacp", nullable = false, length = 1)
    private String codetacp;

    @Column(name = "cle_comp")
    private Short cleComp;

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
}
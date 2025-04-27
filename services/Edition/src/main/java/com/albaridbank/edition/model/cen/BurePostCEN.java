package com.albaridbank.edition.model.cen;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "burePostCEN")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BurePostCEN {

    @Id
    @Column(name = "codburpo")
    private Long codeBureau;

    @Column(name = "cobupodo")
    private Long codeBureauDomicile;

    @Column(name = "cobupora")
    private Long codeBureauParent;

    @Column(name = "coderegi")
    private Integer codeRegion;

    @Column(name = "codeprov")
    private Integer codeProvince;

    @Column(name = "codecomm")
    private Integer codeCommune;

    @Column(name = "desburpo")
    private String designation;

    @Column(name = "adrburpo")
    private String adresse;

    @Column(name = "codepost")
    private String codePostal;

    @Column(name = "numetele")
    private String telephone;

    @Column(name = "nume_fax")
    private String fax;

    @Column(name = "numetelx")
    private String telex;

    @Column(name = "chai_dsn")
    private String chaineDSN;

    @Column(name = "nomliven")
    private Long nombreLivret;

    @Column(name = "nomcomou")
    private Long nombreComptesOuverts;

    @Column(name = "stominli")
    private Integer stockMinimumLivrets;

    @Column(name = "stomaxli")
    private Integer stockMaximumLivrets;

    @Column(name = "attautco")
    private String attributionAutomatiqueCompte;

    @Column(name = "etaburpo")
    private String etatBureau;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cobupora", insertable = false, updatable = false)
    private BurePostCEN bureauParent;
}
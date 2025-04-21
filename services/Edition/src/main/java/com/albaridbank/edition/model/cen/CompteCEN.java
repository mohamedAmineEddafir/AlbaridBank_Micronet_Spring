package com.albaridbank.edition.model.cen;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "\"compteCEN\"")
@IdClass(CompteCENId.class) // Classe ID composée
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompteCEN {

    @Id
    @Column(name = "codeprod")
    private Integer codeProduit;

    @Id
    @Column(name = "idencomp")
    private Long idCompte;

    @Column(name = "codcatco")
    private Integer codeCategorie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idenclie")
    private ClientCEN client;

    @Column(name = "codetaco")
    private Integer codeTypeActivite;

    @Column(name = "codclare")
    private Integer codeClassificationReglementaire;

    @Column(name = "datprere")
    private LocalDate datePremierRetrait;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codburpo")
    private BurePostCEN bureauPoste;

    @Column(name = "codmotet")
    private Integer codeModeEnvoi;

    @Column(name = "inticomp")
    private String intitule;

    @Column(name = "adrecomp")
    private String adresse;

    @Column(name = "codepost")
    private String codePostal;

    @Column(name = "villcomp")
    private String ville;

    @Column(name = "dateouve")
    private LocalDate dateOuverture;

    @Column(name = "soldminu")
    private BigDecimal soldeMinimum;

    @Column(name = "soldcour")
    private BigDecimal soldeCourant;

    @Column(name = "datesold")
    private LocalDate dateSolde;

    @Column(name = "soldoppo")
    private BigDecimal soldeOpposition;

    @Column(name = "soldbloc")
    private BigDecimal soldeBloque;

    @Column(name = "tauintde")
    private BigDecimal tauxInteretDebiteur;

    @Column(name = "tauintcr")
    private BigDecimal tauxInteretCrediteur;

    @Column(name = "taumajin")
    private BigDecimal tauxMajoration;

    @Column(name = "durecomp")
    private Integer dureeCompte;

    @Column(name = "revemens")
    private BigDecimal revenuMensuel;

    @Column(name = "discapin")
    private String distributionCapitalInteret;

    @Column(name = "numcomcc")
    private Long numeroCompteCCP;

    @Column(name = "monpreve")
    private BigDecimal montantPreleve;

    @Column(name = "monverul")
    private BigDecimal montantVersementUlterieur;

    @Column(name = "moncrede")
    private BigDecimal montantCreditDemande;

    @Column(name = "moncreac")
    private BigDecimal montantCreditAccorde;

    @Column(name = "datetaco")
    private LocalDate dateActivite;

    @Column(name = "dateclot")
    private LocalDate dateCloture;

    @Column(name = "inteacqu")
    private BigDecimal interetsAcquis;

    @Column(name = "compliai")
    private Long compteLibelle;

    @Column(name = "livdepce")
    private String livretDepense;

    @Column(name = "datogmaj")
    private LocalDate dateOrigine;

    @Column(name = "bpogemaj")
    private Long bureauOrigine;

    @Column(name = "ordogmaj")
    private Long ordreOrigine;

    @Column(name = "datderog")
    private LocalDate dateDerniereOperation;

    @Column(name = "datderoc")
    private LocalDate dateDerniereOperation2;
}

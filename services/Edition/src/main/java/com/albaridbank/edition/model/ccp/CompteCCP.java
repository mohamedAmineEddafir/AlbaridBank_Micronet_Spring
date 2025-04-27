package com.albaridbank.edition.model.ccp;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "compteCCP")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompteCCP {

    @Id
    @Column(name = "idencomp")
    private Long idCompte;

    @Column(name = "codcatcp")
    private Integer codeCategorie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idenclie")
    private ClientCCP client;

    @Column(name = "compcomp")
    private String compositionCompte;

    @Column(name = "cle_comp")
    private Short cleCompte;

    @Column(name = "inticomp")
    private String intitule;

    @Column(name = "inticond")
    private String intituleCondense;

    @Column(name = "adrecomp")
    private String adresse;

    @Column(name = "codepost")
    private String codePostal;

    @Column(name = "dateouve")
    private LocalDate dateOuverture;

    @Column(name = "perirele")
    private String periodeReleve;

    @Column(name = "numepate")
    private String numeroPatente;

    @Column(name = "numregco")
    private String numeroRegistreCommerce;

    @Column(name = "soldminu")
    private BigDecimal soldeMinimum;

    @Column(name = "soldcour")
    private BigDecimal soldeCourant;

    @Column(name = "datesold")
    private LocalDate dateSolde;

    @Column(name = "solopenc")
    private BigDecimal soldeOperationsEnCours;

    @Column(name = "soldoppo")
    private BigDecimal soldeOpposition;

    @Column(name = "soldtaxe")
    private BigDecimal soldeTaxe;

    @Column(name = "solddebo")
    private BigDecimal soldeDebitOperations;

    @Column(name = "solddeco")
    private BigDecimal soldeCreditOperations;

    @Column(name = "solopede")
    private BigDecimal soldeOperationsPeriode;

    @Column(name = "soldcert")
    private BigDecimal soldeCertifie;

    @Column(name = "totadebe")
    private BigDecimal totalDebit;

    @Column(name = "nochsapr")
    private Short nombreChequesSansPrevision;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codbpcpt")
    private BureauPosteCCP bureauPoste;

    @Column(name = "codetacp")
    private String codeEtatCompte;

    @Column(name = "cmptmand")
    private String compteMandat;

    @Column(name = "commulti")
    private String compteMultiTitulaires;

    @Column(name = "cptedeco")
    private String compteDecouvert;

    @Column(name = "credregu")
    private String creditRegulier;

    @Column(name = "daminscl")
    private LocalDate dateMiseEnCloture;

    @Column(name = "comoclco")
    private Integer codeMotifCloture;

    @Column(name = "dateclot")
    private LocalDate dateCloture;

    @Column(name = "refdeman")
    private String referenceDemande;

    @Column(name = "servdoss")
    private String serviceDossier;

    @Column(name = "numedoss")
    private String numeroDossier;

    @Column(name = "codbpini")
    private Long codeBureauInitial;

    @Column(name = "datogmaj")
    private LocalDate dateOrigine;

    @Column(name = "bpogemaj")
    private Long bureauOrigine;

    @Column(name = "ordogmaj")
    private Long ordreOrigine;

    @Column(name = "exontaxe")
    private String exonerationTaxe;

    @Column(name = "dadeopfi")
    private LocalDate dateDebutOperationFiscale;

    @Column(name = "adresign")
    private Long adresseSignaletique;

    @Column(name = "soltaxte")
    private BigDecimal soldeTaxeTE;

    @Column(name = "codepinn")
    private Integer codePin;

    @Column(name = "codeprod")
    private Integer codeProduit;

    @OneToMany(mappedBy = "compte", cascade = CascadeType.ALL)
    private List<MvtFinancierCCP> mouvements;
}
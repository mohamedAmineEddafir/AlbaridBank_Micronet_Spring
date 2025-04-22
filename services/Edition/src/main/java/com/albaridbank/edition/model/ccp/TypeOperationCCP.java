package com.albaridbank.edition.model.ccp;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "\"typeOperationCCP\"")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeOperationCCP {

    @Id
    @Column(name = "codtypop")
    private Long codeTypeOperation;

    @Column(name = "libtypop")
    private String libelle;

    @Column(name = "natuoper")
    private String natureOperation;

    @Column(name = "joudatva")
    private Short joursDateValeur;

    @Column(name = "codfamop")
    private Integer codeFamilleOperation;

    @Column(name = "codpgmco")
    private String codeProgrammeControle;

    @Column(name = "codecrsa")
    private String codeControle;

    @Column(name = "cotyopte")
    private Integer codeTypeOperationTE;

    @Column(name = "cosoopte")
    private Integer codeSousOperationTE;

    @Column(name = "nbrepoin")
    private BigDecimal nombrePoints;

    @Column(name = "cpteordr")
    private Long compteOrdre;

    @Column(name = "cptetaxe")
    private Long compteTaxe;

    @Column(name = "cptedebo")
    private Long compteDebitOperations;

    @Column(name = "cptedeco")
    private Long compteCreditOperations;

    @Column(name = "debeauto")
    private String debitAutomatique;

    @Column(name = "delrepfo")
    private Integer delaiRepliFonds;

    @Column(name = "grouemis")
    private Integer groupeEmission;

    @Column(name = "groupaie")
    private Integer groupePaiement;

    @Column(name = "priooper")
    private Integer prioriteOperation;

    @Column(name = "cptereje")
    private Long compteRejet;

    @Column(name = "cptataxe")
    private String comptabilisationTaxe;

    @Column(name = "cpttaxte")
    private Long compteTaxeTE;

    @Column(name = "deptaxpa")
    private String depassementTaxePreleve;

    @Column(name = "codearti")
    private Integer codeArticle;
}
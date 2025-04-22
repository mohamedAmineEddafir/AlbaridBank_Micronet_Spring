package com.albaridbank.edition.model.cen;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

/**
 * Entity class representing a type of operation.
 * This class is mapped to the "type_operation" table in the database.
 * It contains information about the operation type such as code and label.
 */
@Entity
@Table(name = "\"typeOperCEN\"")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeOperCEN {

    @Id
    @Column(name = "codtypop")
    private Long codeTypeOperation;

    @Column(name = "libtypop")
    private String libelle;

    @Column(name = "natuoper")
    private String natureOperation;

    @Column(name = "compordr")
    private Long compteurOrdre;

    @Column(name = "famioper")
    private String familleOperation;

    @Column(name = "contauto")
    private String controleAuto;

    @Column(name = "codunisa")
    private String codeUniteSaisie;

    @Column(name = "codunico")
    private String codeUniteControle;

    @Column(name = "regllivr")
    private String regleLivre;

    @Column(name = "libabrop")
    private String libelleAbrege;

    @Column(name = "priooper")
    private Integer priorite;

    @Column(name = "cptetaxe")
    private Long compteTaxe;

    @Column(name = "cptedebo")
    private Long compteDebitOperations;

    @Column(name = "cptedeco")
    private Long compteCreditOperations;

    @Column(name = "cptereje")
    private Long compteRejet;

    @Column(name = "cptataxe")
    private String comptabilisationTaxe;

    @Column(name = "cpttaxte")
    private Long compteTaxeTE;

    @Column(name = "codearti")
    private Long codeArticle;

    @Column(name = "cpteordr")
    private Long compteOrdre;

    @Column(name = "caisagen")
    private String caisse;

    @Column(name = "menimpliv")
    private String mentionImpressionLivret;
}
package com.albaridbank.edition.model.cen;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entity class representing an operation on a CEN account.
 * This class is mapped to the "oper_comp" table in the database.
 * It contains information about the operation such as date, amount, office code, product codes, credit and debit accounts, category code, status code, and type of operation.
 */
@Entity
@Table(name = "operCompCEN")
@IdClass(OperCompCENId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperCompCEN {

    @Id
    @Column(name = "dateoper")
    private LocalDate dateOperation;

    @Id
    @Column(name = "codburpo")
    private Long codeBureau;

    @Id
    @Column(name = "numeordr")
    private Long numeroOrdre;

    @Column(name = "datebord")
    private LocalDate dateBordereau;

    @Column(name = "cobupobo")
    private Long codeBureauBordereau;

    @Column(name = "numebord")
    private Long numeroBordereau;

    @Column(name = "codprode")
    private Integer codeProduitDebit;

    @Column(name = "compdebi")
    private Long compteDebit;

    @Column(name = "codprocr")
    private Integer codeProduitCredit;

    @Column(name = "compcred")
    private Long compteCredit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codtypop")
    private TypeOperCEN typeOperation;

    @Column(name = "comoreop")
    private Integer codeMotifOperation;

    @Column(name = "datevale")
    private LocalDate dateValeur;

    @Column(name = "commenta")
    private String commentaire;

    @Column(name = "reftitju")
    private String referenceJustificative;

    @Column(name = "typepiec")
    private String typePiece;

    @Column(name = "datvalcen")
    private LocalDate dateValeurCEN;

    @Column(name = "numpieid")
    private String numeroPieceIdentite;

    @Column(name = "datpieid")
    private LocalDate datePieceIdentite;

    @Column(name = "nom_tire")
    private String nomTireur;

    @Column(name = "montoper")
    private BigDecimal montantOperation;

    @Column(name = "codutisa")
    private Long codeUtilisateurSaisie;

    @Column(name = "codpgmsa")
    private String codeProgrammeSaisie;

    @Column(name = "datesais")
    private LocalDateTime dateSaisie;

    @Column(name = "codutiva")
    private Long codeUtilisateurValidation;

    @Column(name = "codpgmva")
    private String codeProgrammeValidation;

    @Column(name = "cobupova")
    private Long codeBureauValidation;

    @Column(name = "numeauto")
    private Long numeroAutorisation;

    @Column(name = "datevali")
    private LocalDateTime dateValidation;

    @Column(name = "commreje")
    private String commentaireRejet;

    @Column(name = "menmarde")
    private String memoDebit;

    @Column(name = "menmarcr")
    private String memoCredit;

    @Column(name = "codstaop")
    private String codeStatutOperation;

    @Column(name = "ancstaop")
    private String ancienStatutOperation;

    @Column(name = "datogori")
    private LocalDate dateOrigine;

    @Column(name = "bupoogor")
    private Long bureauOrigine;

    @Column(name = "ordogori")
    private Long ordreOrigine;

    @Column(name = "tracounter")
    private Integer compteurTransaction;

    @Column(name = "modepaie")
    private String modePaiement;

    @Column(name = "refoper")
    private String referenceOperation;

    @Column(name = "numcomccp")
    private Long numeroCompteCCP;

    @Column(name = "datocori")
    private LocalDate dateOperationCorrespondante;

    @Column(name = "bupoocor")
    private Long bureauOperationCorrespondante;

    @Column(name = "ordocori")
    private Long ordreOperationCorrespondante;

    @Column(name = "imprim")
    private Integer imprime;

    @Column(name = "soldepadebi")
    private BigDecimal soldePartielDebit;

    @Column(name = "soldepacred")
    private BigDecimal soldePartielCredit;
}


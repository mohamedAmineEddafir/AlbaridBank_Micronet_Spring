package com.albaridbank.edition.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entity class representing an operation on a CEN account.
 * This class is mapped to the "oper_comp" table in the database.
 * It contains information about the operation such as date, amount, office code, product codes, credit and debit accounts, category code, status code, and type of operation.
 */
@Entity
@Table(name = "oper_comp")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperComp {

    /**
     * The unique identifier for the operation.
     * This value is generated automatically.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_operation")
    private Long idOperation;

    /**
     * The date of the operation.
     */
    @Column(name = "dateoper")
    private LocalDate dateoper;

    /**
     * The amount of the operation.
     * This field is required and has a precision of 15 and a scale of 2.
     */
    @Column(name = "montoper", precision = 15, scale = 2, nullable = false)
    private BigDecimal montoper;

    /**
     * The office code associated with the operation.
     * This field has a maximum length of 6 characters.
     */
    @Column(name = "codburpo", length = 6)
    private String codburpo;

    /**
     * The product code associated with the operation.
     * This field has a precision of 2.
     */
    @Column(name = "codeprod", precision = 2)
    private BigDecimal codeprod;

    /**
     * The credit product code associated with the operation.
     * This field has a precision of 2.
     */
    @Column(name = "codprocr", precision = 2)
    private BigDecimal codprocr;

    /**
     * The debit product code associated with the operation.
     * This field has a precision of 2.
     */
    @Column(name = "codprode", precision = 2)
    private BigDecimal codprode;

    /**
     * The credit account associated with the operation.
     * This field has a precision of 12.
     */
    @Column(name = "compcred", precision = 12)
    private BigDecimal compcred;

    /**
     * The debit account associated with the operation.
     * This field has a precision of 12.
     */
    @Column(name = "compdebi", precision = 12)
    private BigDecimal compdebi;

    /**
     * The category code associated with the operation.
     * This field has a precision of 2.
     */
    @Column(name = "codcatco", precision = 2)
    private BigDecimal codcatco;

    /**
     * The status code associated with the operation.
     * This field has a maximum length of 1 character.
     */
    @Column(name = "codstaop", length = 1)
    private String codstaop;

    /**
     * The type code of the operation.
     * This field has a precision of 5.
     */
    @Column(name = "codtypop", precision = 5)
    private BigDecimal codtypop;

    /**
     * The type of operation associated with the operation.
     * This field is mapped by the "codtypop" field in the TypeOperation entity.
     * The fetch type is set to LAZY.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codtypop", referencedColumnName = "codtypop", insertable = false, updatable = false)
    private TypeOperation typeOperation;

    /**
     * The credit account associated with the operation.
     * This field is mapped by the "compcred" field in the CompteCEN entity.
     * The fetch type is set to LAZY.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compcred", referencedColumnName = "idencomp", insertable = false, updatable = false)
    private CompteCEN compteCredit;

    /**
     * The debit account associated with the operation.
     * This field is mapped by the "compdebi" field in the CompteCEN entity.
     * The fetch type is set to LAZY.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compdebi", referencedColumnName = "idencomp", insertable = false, updatable = false)
    private CompteCEN compteDebit;
}
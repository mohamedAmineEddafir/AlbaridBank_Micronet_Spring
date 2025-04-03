package com.albaridbank.edition.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entity class representing a financial movement.
 * This class is mapped to the "mvt_financier" table in the database.
 * It contains information about the financial movement such as account, amount, direction, date, office code, and operation type.
 */
@Entity
@Table(name = "mvt_financier")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MvtFinancier implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The unique identifier for the financial movement.
     * This value is generated automatically.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mouvement")
    private Long idMouvement;

    /**
     * The account number associated with the financial movement.
     * This field is required and has a precision of 12.
     */
    @Column(name = "cptemouv", precision = 12, nullable = false)
    private BigDecimal cptemouv;

    /**
     * The amount of the financial movement.
     * This field is required and has a precision of 15 and a scale of 2.
     */
    @Column(name = "montmouv", precision = 15, scale = 2, nullable = false)
    private BigDecimal montmouv;

    /**
     * The direction of the financial movement.
     * This field has a maximum length of 1 character.
     */
    @Column(name = "sensmouv", length = 1)
    private String sensmouv;

    /**
     * The date of the financial movement.
     */
    @Column(name = "datemouv")
    private LocalDate datemouv;

    /**
     * The office code associated with the financial movement.
     * This field has a maximum length of 6 characters.
     */
    @Column(name = "codburpo", length = 6)
    private String codburpo;

    /**
     * The type code of the operation associated with the financial movement.
     * This field has a precision of 5.
     */
    @Column(name = "codtypop", precision = 5)
    private BigDecimal codtypop;

    /**
     * The type of operation associated with the financial movement.
     * This field is mapped by the "codtypop" field in the TypeOperation entity.
     * The fetch type is set to LAZY.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codtypop", referencedColumnName = "codtypop", insertable = false, updatable = false)
    private TypeOperation typeOperation;
}
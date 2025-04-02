package com.albaridbank.edition.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Entity class representing a CEN account.
 * This class is mapped to the "compte_cen" table in the database.
 * It contains information about the CEN account such as client ID, account title, address, minimum balance, balance date, account duration, office code, and account type code.
 */
@Entity
@Table(name = "compte_cen")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompteCEN {

    /**
     * The unique identifier for the CEN account.
     * This field is required and has a precision of 12.
     */
    @Id
    @Column(name = "idencomp", precision = 12)
    private BigDecimal idencomp;

    /**
     * The client ID associated with the CEN account.
     * This field is required and has a precision of 10.
     */
    @Column(name = "idenclie", precision = 10, nullable = false)
    private BigDecimal idenclie;

    /**
     * The title of the CEN account.
     * This field is required and has a maximum length of 100 characters.
     */
    @Column(name = "inticomp", length = 100, nullable = false)
    private String inticomp;

    /**
     * The address of the CEN account.
     * This field is optional and has a maximum length of 200 characters.
     */
    @Column(name = "adrecomp", length = 200)
    private String adrecomp;

    /**
     * The minimum balance of the CEN account.
     * This field has a precision of 15 and a scale of 2.
     */
    @Column(name = "soldminu", precision = 15, scale = 2)
    private BigDecimal soldminu;

    /**
     * The date of the balance for the CEN account.
     * This field has a maximum length of 20 characters.
     */
    @Column(name = "datesold", length = 20)
    private String datesold;

    /**
     * The duration of the CEN account.
     * This field has a precision of 10.
     */
    @Column(name = "durecomp", precision = 10)
    private BigDecimal durecomp;

    /**
     * The office code associated with the CEN account.
     * This field is required and has a maximum length of 6 characters.
     */
    @Column(name = "codburpo", length = 6, nullable = false)
    private String codburpo;

    /**
     * The account type code for the CEN account.
     * This field is required and has a precision of 2.
     */
    @Column(name = "codetaco", precision = 2, nullable = false)
    private BigDecimal codetaco;
}
package com.albaridbank.edition.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Entity class representing a CEN client.
 * This class is mapped to the "client_cen" table in the database.
 * It contains information about the CEN client such as client ID, social security code, birth date, ID number, and phone number.
 */
@Entity
@Table(name = "client_cen")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientCEN implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The unique identifier for the CEN client.
     * This field is required and has a precision of 10.
     */
    @Id
    @Column(name = "idenclie", precision = 10)
    private BigDecimal idenclie;

    /**
     * The social security code associated with the CEN client.
     * This field has a precision of 3.
     */
    @Column(name = "cocasopr", precision = 3)
    private BigDecimal cocasopr;

    /**
     * The birth date of the CEN client.
     * This field has a maximum length of 20 characters.
     */
    @Column(name = "datenais", length = 20)
    private String datenais;

    /**
     * The ID number of the CEN client.
     * This field has a maximum length of 30 characters.
     */
    @Column(name = "numpieid", length = 30)
    private String numpieid;

    /**
     * The phone number of the CEN client.
     * This field has a maximum length of 20 characters.
     */
    @Column(name = "numetele", length = 20)
    private String numetele;
}
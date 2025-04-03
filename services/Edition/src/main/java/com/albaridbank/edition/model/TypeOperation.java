package com.albaridbank.edition.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Entity class representing a type of operation.
 * This class is mapped to the "type_operation" table in the database.
 * It contains information about the operation type such as code and label.
 */
@Entity
@Table(name = "type_operation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeOperation implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The unique code for the type of operation.
     * This field is required and has a precision of 5.
     */
    @Id
    @Column(name = "codtypop", precision = 5)
    private BigDecimal codtypop;

    /**
     * The label for the type of operation.
     * This field is required and has a maximum length of 100 characters.
     */
    @Column(name = "libtypop", length = 100, nullable = false)
    private String libtypop;
}
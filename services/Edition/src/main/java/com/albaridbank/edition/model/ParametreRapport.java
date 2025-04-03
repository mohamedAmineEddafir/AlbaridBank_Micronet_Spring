package com.albaridbank.edition.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * Entity class representing a report parameter.
 * This class is mapped to the "parametre_rapport" table in the database.
 * It contains information about the report parameter such as ID, report, name, value, and type.
 */
@Entity
@Table(name = "parametre_rapport")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParametreRapport implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The unique identifier for the report parameter.
     * This value is generated automatically.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The report associated with the parameter.
     * This field is required and is mapped by the "id_rapport" field in the Rapport entity.
     * The fetch type is set to LAZY.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rapport", nullable = false)
    @ToString.Exclude
    private Rapport rapport;

    /**
     * The name of the parameter.
     * This field is required and has a maximum length of 50 characters.
     */
    @Column(name = "nom", length = 50, nullable = false)
    private String nom;

    /**
     * The value of the parameter.
     * This field is optional and has a maximum length of 200 characters.
     */
    @Column(name = "valeur", length = 200)
    private String valeur;

    /**
     * The type of the parameter.
     * This field is optional and has a maximum length of 20 characters.
     */
    @Column(name = "type", length = 20)
    private String type;
}
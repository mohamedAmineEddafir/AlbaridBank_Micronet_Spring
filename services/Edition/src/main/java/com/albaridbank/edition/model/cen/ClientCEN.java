package com.albaridbank.edition.model.cen;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Entity class representing a client in the CEN system.
 * This class is mapped to the "clientCEN" table in the database.
 * It contains information about the client such as ID, category, and personal details.
 */
@Entity
@Table(name = "clientCEN")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientCEN {

    @Id
    @Column(name = "idenclie")
    private Long idClient;

    @Column(name = "codcatcl")
    private Integer codeCategorie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cocasopr")
    private CatSociProfCEN categorieSocioProfessionnelle;

    @Column(name = "desiclie")
    private String designation;

    @Column(name = "adreclie")
    private String adresse;

    @Column(name = "codepost")
    private String codePostal;

    @Column(name = "numetele")
    private String telephone;

    @Column(name = "nume_fax")
    private String fax;

    @Column(name = "numetelx")
    private String telex;

    @Column(name = "datenais")
    private LocalDate dateNaissance;

    @Column(name = "lieunaiss")
    private String lieuNaissance;

    @Column(name = "sexeclie")
    private String sexe;

    @Column(name = "situfami")
    private String situationFamiliale;

    @Column(name = "typepiec")
    private String typePiece;

    @Column(name = "numpieid")
    private String numeroPieceIdentite;

    @Column(name = "datpieid")
    private LocalDate datePieceIdentite;

    @Column(name = "numregco")
    private String numeroRegistreCommerce;

    @Column(name = "numepate")
    private String numeroPatente;

    @Column(name = "datecrea")
    private LocalDate dateCreation;

    @Column(name = "codepays")
    private String codePays;

    @Column(name = "datogmaj")
    private LocalDate dateOrigine;

    @Column(name = "bpogemaj")
    private Long bureauOrigine;

    @Column(name = "ordogmaj")
    private Long ordreOrigine;

    @OneToMany(mappedBy = "client")
    private List<CompteCEN> comptes;
}
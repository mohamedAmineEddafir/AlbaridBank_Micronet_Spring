package com.albaridbank.edition.model.ccp;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "clientCCP")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientCCP {

    @Id
    @Column(name = "idenclie")
    private Long idClient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codsocpr")
    private CatSocioProfCCP categorieSocioProfessionnelle;

    @Column(name = "desiclie")
    private String designation;

    @Column(name = "adreclie")
    private String adresse;

    @Column(name = "codepost")
    private String codePostal;

    @Column(name = "codepays")
    private String codePays;

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

    @Column(name = "numedoti")
    private String numeroDotation;

    @Column(name = "numematr")
    private String numeroMatricule;

    @Column(name = "codetati")
    private Long codeEtablissementTiers;

    @Column(name = "codforju")
    private String codeFormeJuridique;

    @Column(name = "numregco")
    private String numeroRegistreCommerce;

    @Column(name = "numepate")
    private String numeroPatente;

    @Column(name = "numecnss")
    private String numeroCNSS;

    @Column(name = "numercar")
    private String numeroCarte;

    @Column(name = "nume_cmr")
    private String numeroCMR;

    @Column(name = "numecimr")
    private String numeroCIMR;

    @Column(name = "numecfps")
    private String numeroCFPS;

    @Column(name = "numecnop")
    private String numeroCNOP;

    @Column(name = "nume_cen")
    private Long numeroCEN;

    @Column(name = "nume_amo")
    private String numeroAMO;

    @Column(name = "datecrea")
    private LocalDate dateCreation;

    @Column(name = "ordrcpte")
    private Short ordreCompte;

    @Column(name = "datogmaj")
    private LocalDate dateOrigine;

    @Column(name = "bpogemaj")
    private Long bureauOrigine;

    @Column(name = "ordogmaj")
    private Long ordreOrigine;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<CompteCCP> comptes;
}
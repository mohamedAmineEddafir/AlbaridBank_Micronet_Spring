package com.albaridbank.brancheservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchDTO {

    private String codeAgence;            // codburpo
    private String nomAgence;             // libelleBurpo
    private String typeAgence;            // typbur
    private String region;                // libreg - libelleRegion
    private Integer codeRegion;           // codreg
    private String groupe;                // libelleGroupe
    private String localite;              // libelleLocalite
    private String categorie;             // categorie
    private String catAgence;             // catagence

    private String statut;                // statut (O/N)
    private String deploye;               // deploye (O/N)
    private LocalDate dateDeploiement;    // datedeploiement
    private String aDeployer;             // adeployer (O/N)

    private String responsable;           // responsable
    private String ip;                    // ip
    private String typeConnexion;         // typeconnexion

    private String adresse;               // adresse
    private String codePostal;            // codepostale
    private String telephoneFixe;         // telephone_fixe
    private String telephoneMobile;       // telephone_mobile
    private String email;                 // email
}
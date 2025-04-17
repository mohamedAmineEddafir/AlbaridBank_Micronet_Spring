package com.albaridbank.brancheservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Detailed representation of a bank branch (agence)")
public class BranchDTO {

    @Schema(description = "Unique code identifying the branch.", example = "B123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String codeAgence;            // codburpo

    @Schema(description = "Official name of the branch.", example = "Al Barid Bank Agence Centrale")
    private String nomAgence;             // libelleBurpo

    @Schema(description = "Type classification of the branch.", example = "Principale")
    private String typeAgence;            // typbur

    @Schema(description = "Name of the region the branch belongs to.", example = "Rabat-Salé-Kénitra")
    private String region;                // libreg - libelleRegion

    @Schema(description = "Numeric code for the branch's region.", example = "04")
    private Integer codeRegion;           // codreg

    @Schema(description = "Group the branch belongs to.", example = "Groupe A")
    private String groupe;                // libelleGroupe

    @Schema(description = "Locality where the branch is situated.", example = "Rabat Centre")
    private String localite;              // libelleLocalite

    @Schema(description = "Category of the branch.", example = "Urbain")
    private String categorie;             // categorie

    @Schema(description = "Further categorization of the agency.", example = "Type 1")
    private String catAgence;             // catagence

    @Schema(description = "Current operational status of the branch.", example = "O", allowableValues = {"O", "F"})
    private String statut;                // statut (O/F => Ouvert/Ferme ouvert or Opérationnel/Non opérationnel ?) - Clarify meaning

    @Schema(description = "Deployment status.", example = "O", allowableValues = {"O", "N"})
    private String deploye;               // deploye (O/N)

    @Schema(description = "Date when the branch was deployed.", example = "2023-05-15")
    private LocalDate dateDeploiement;    // datedeploiement

    @Schema(description = "Flag indicating if the branch is scheduled for deployment.", example = "N", allowableValues = {"O", "N"})
    private String aDeployer;             // adeployer (O/N)

    @Schema(description = "Name of the person responsible for the branch.", example = "Hamza El Mezouari")
    private String responsable;           // responsable

    @Schema(description = "IP address associated with the branch network.", example = "192.168.1.100")
    private String ip;                    // ip

    @Schema(description = "Type of network connection used by the branch.", example = "Fibre Optique")
    private String typeConnexion;         // typeconnexion

    @Schema(description = "Full street address of the branch.", example = "123 Avenue Mohammed V, Rabat")
    private String adresse;               // adresse

    @Schema(description = "Postal code for the branch's location.", example = "10001")
    private String codePostal;            // codepostale

    @Schema(description = "Landline phone number of the branch.", example = "0537-XX-XX-XX")
    private String telephoneFixe;         // telephone_fixe

    @Schema(description = "Mobile phone number associated with the branch.", example = "0661-XX-XX-XX")
    private String telephoneMobile;       // telephone_mobile

    @Schema(description = "Contact email address for the branch.", example = "agence.centrale@albaridbank.ma")
    private String email;                 // email
}
package com.albaridbank.brancheservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Simplified representation of a bank branch, containing essential identification and contact info.")
public class BranchSimpleDTO {

    @Schema(description = "Unique code identifying the branch.", example = "B123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String codeAgence;    // codburpo

    @Schema(description = "Official name of the branch.", example = "Al Barid Bank Agence Centrale")
    private String nomAgence;     // libelleBurpo

    @Schema(description = "Name of the region the branch belongs to.", example = "Rabat-Salé-Kénitra")
    private String region;        // libreg

    @Schema(description = "Current operational status of the branch.", example = "O", allowableValues = {"O", "F"})
    private String statut;        // statut (O/F) - Clarify meaning

    @Schema(description = "Full street address of the branch.", example = "123 Avenue Mohammed V, Rabat")
    private String adresse;       // adresse

    @Schema(description = "Primary contact phone number (fixed or mobile).", example = "0537-XX-XX-XX")
    private String telephone;     // telephone_fixe ou mobile
}
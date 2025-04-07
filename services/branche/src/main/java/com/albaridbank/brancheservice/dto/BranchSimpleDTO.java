package com.albaridbank.brancheservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchSimpleDTO {

    private String codeAgence;    // codburpo
    private String nomAgence;     // libelleBurpo
    private String region;        // libreg
    private String statut;        // statut (O/N)
    private String adresse;       // adresse
    private String telephone;     // telephone_fixe ou mobile
}
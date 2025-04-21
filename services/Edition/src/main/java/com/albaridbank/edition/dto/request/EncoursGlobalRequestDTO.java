package com.albaridbank.edition.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * DTO de requête pour ETAT_Nbr_total_encours_CEN_G et ETAT_Nbr_total_encours_G
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EncoursGlobalRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long codeBureau;       // Code de l'agence (Parameter1)
    private String typeProduit;    // "CCP" ou "CEN" pour distinguer les deux rapports
}

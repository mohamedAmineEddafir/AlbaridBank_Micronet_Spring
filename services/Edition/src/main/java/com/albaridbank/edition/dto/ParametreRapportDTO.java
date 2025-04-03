package com.albaridbank.edition.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h4> Configurer les paramètres de rapport </h4>
 *
 * @author Mohamed Amine Eddafir
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParametreRapportDTO {
    private Long id;
    private String nom;
    private String valeur;
    private String type;  // STRING, NUMBER, DATE, BOOLEAN, etc.
    private boolean obligatoire;
    private String description;  // Aide contextuelle expliquant le paramètre
}
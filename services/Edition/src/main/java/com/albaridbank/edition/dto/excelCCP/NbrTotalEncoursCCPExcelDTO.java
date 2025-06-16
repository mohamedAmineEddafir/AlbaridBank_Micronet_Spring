package com.albaridbank.edition.dto.excelCCP;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NbrTotalEncoursCCPExcelDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // Informations d'en-tête
    private String titreRapport;
    private LocalDateTime dateEdition;
    private LocalDate journeeDu;

    // Informations de l'agence
    private Long codeBureau;
    private String designationBureau;

    // Données du rapport
    private Long nombreComptes;
    private BigDecimal totalEncours;

    // Métadonnées
    private String utilisateur;
}
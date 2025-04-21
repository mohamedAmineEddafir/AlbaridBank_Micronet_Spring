package com.albaridbank.edition.dto.rapport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class NbrTotalEncoursCENDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // Informations d'en-tête
    private String titreRapport;
    private LocalDate journeeDu;

    // Informations de l'agence
    private Long codburpo;
    private String desburpo;

    // Données du rapport
    private Long nombreComptes;     // ID_count____
    private BigDecimal totalEncours; // ID_sum_
}

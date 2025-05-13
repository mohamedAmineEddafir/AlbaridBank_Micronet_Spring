package com.albaridbank.edition.dto.rapport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class NbrTotalEncoursCCPDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // Informations d'en-tête
    private String titreRapport;
    private Long codeBureau;
    private String designationBureau;
    private LocalDate journeeDu;

    // Données du rapport
    private Long nombreComptes; // ID_count
    private BigDecimal totalEncours; // ID_sum
}
package com.albaridbank.edition.dto.rapport;

import com.albaridbank.edition.dto.base.MouvementFinancierDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@SuperBuilder
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompteMouvementVeilleDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // Informations d'en-tête
    private String titreRapport;
    private LocalDateTime dateEdition;
    private String numeroPage;
    private LocalDate journeeDu;

    // Informations de l'agence
    private Long codeAgence;
    private String nomAgence;

    // Données du rapport
    private List<MouvementFinancierDTO> mouvements;
    private Integer nombreTotalComptes;
    private BigDecimal montantTotal;

    // Paramètres
    private Integer joursAvant; // 1 pour veille, 2 pour avant-veille
    private BigDecimal montantMinimum;
}
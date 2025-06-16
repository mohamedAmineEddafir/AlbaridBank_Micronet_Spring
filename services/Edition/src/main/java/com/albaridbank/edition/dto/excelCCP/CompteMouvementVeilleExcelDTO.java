package com.albaridbank.edition.dto.excelCCP;

import com.albaridbank.edition.dto.base.MouvementFinancierDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompteMouvementVeilleExcelDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // Informations d'en-tête
    private String titreRapport;
    private LocalDateTime dateEdition;
    private LocalDate journeeDu;

    // Informations de l'agence
    private Long codeAgence;
    private String nomAgence;

    // Données du rapport
    private List<MouvementFinancierDTO> mouvements;
    private Integer nombreTotalComptes;
    private BigDecimal montantTotal;

    // Paramètres
    private Integer joursAvant; // 0 pour aujourd'hui, 1 pour veille, 2 pour avant-veille
    private BigDecimal montantMinimum;
}
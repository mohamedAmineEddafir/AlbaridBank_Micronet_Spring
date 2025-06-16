package com.albaridbank.edition.dto.excelCCP;

import com.albaridbank.edition.dto.base.PortefeuilleClientCCPDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PortefeuilleClientCCPMExcelDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // Informations d'en-tête
    private String titreRapport;
    private LocalDateTime dateEdition;
    private String numeroPage;

    // Informations du bureau
    private Long codburpo;
    private String desburpo;

    // Données du rapport
    private List<PortefeuilleClientCCPDetailDTO> comptes;

    // Statistiques
    private Long nombreTotalComptes;
    private BigDecimal encoursTotalComptes;
    private BigDecimal totalSoldeOpposition;
    private BigDecimal totalSoldeTaxe;
    private BigDecimal totalSoldeDebitOperations;
    private BigDecimal totalSoldeCreditOperations;
    private BigDecimal totalSoldeOperationsPeriode;
    private BigDecimal totalSoldeCertifie;

    // Utilisateur
    private String utilisateur;
}
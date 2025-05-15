package com.albaridbank.edition.dto.rapport;

import com.albaridbank.edition.dto.base.PortefeuilleClientCCPDetailDTO;
import lombok.AllArgsConstructor;
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
public class PortefeuilleClientCCPRapportDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    // En-tête du rapport
    private String titreRapport;
    private LocalDateTime dateEdition;
    private String numeroPage;

    // Informations de l'agence
    private Long codburpo;
    private String desburpo;

    // Données du rapport
    private List<PortefeuilleClientCCPDetailDTO> comptes;

    // Totaux
    private Integer nombreTotalComptes;
    private BigDecimal encoursTotalComptes;
    private BigDecimal totalSoldeOpposition;
    private BigDecimal totalSoldeTaxe;
    private BigDecimal totalSoldeDebitOperations;
    private BigDecimal totalSoldeCreditOperations;
    private BigDecimal totalSoldeOperationsPeriode;
    private BigDecimal totalSoldeCertifie;
}
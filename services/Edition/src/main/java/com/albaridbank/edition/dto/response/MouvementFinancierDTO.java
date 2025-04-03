package com.albaridbank.edition.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <h4>Data Transfer Object for financial movements</h4>
 * <ul>
 *     <li>Pour les données de mouvement dans les rapports</li>
 *</ul>
 *
 * @author Mohamed Amine Eddafir
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MouvementFinancierDTO {

    /**
     * The unique identifier for the financial movement.
     */
    private Long idMouvement;

    /**
     * The date and time of the financial movement.
     */
    private LocalDateTime dateMouvement;

    /**
     * The amount of the financial movement.
     */
    private BigDecimal montant;

    /**
     * The direction of the financial movement.
     * "C" for credit, "D" for debit.
     */
    private String sens;  // "C" pour crédit, "D" pour débit

    /**
     * The label of the operation.
     */
    private String libelleOperation;

    /**
     * The code of the operation type.
     */
    private String codeTypeOperation;

    /**
     * The cumulative balance after the operation.
     */
    private BigDecimal soldeCumule;  // Solde après l'opération

    /**
     * The reference of the operation.
     */
    private String referenceOperation;
}
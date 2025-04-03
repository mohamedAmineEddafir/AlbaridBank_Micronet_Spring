package com.albaridbank.edition.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <h4>Data Transfer Object for financial reports.</h4>
 * <ul>
 *     <li>Pour stocker les données d'un rapport financier complexe</li>
 * </ul>
 *
 * @author Mohamed Amine Eddafir
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RapportFinancierDTO {

    // Client information

    /**
     * The unique identifier for the client.
     */
    private Long idClient;

    /**
     * The name of the client.
     */
    private String nomClient;

    /**
     * The address of the client.
     */
    private String adresseClient;

    /**
     * The identification number of the client.
     */
    private String numeroIdentification;

    /**
     * The phone number of the client.
     */
    private String telephone;

    // Account information

    /**
     * The unique identifier for the account.
     */
    private Long idCompte;

    /**
     * The title of the account.
     */
    private String intituleCompte;

    /**
     * The current balance of the account.
     */
    private BigDecimal soldeActuel;

    /**
     * The minimum balance of the account.
     */
    private BigDecimal soldeMinimum;

    // Agency information

    /**
     * The code of the agency.
     */
    private String codeAgence;

    /**
     * The name of the agency.
     */
    private String nomAgence;

    // Report period

    /**
     * The start date of the report period.
     */
    private LocalDateTime dateDebut;

    /**
     * The end date of the report period.
     */
    private LocalDateTime dateFin;

    // Financial movements data

    /**
     * The list of financial movements.
     */
    private List<MouvementFinancierDTO> mouvements;

    // Calculated statistics

    /**
     * The total amount of credits.
     */
    private BigDecimal totalCredits;

    /**
     * The total amount of debits.
     */
    private BigDecimal totalDebits;

    /**
     * The final balance after all operations.
     */
    private BigDecimal soldeFinal;

    /**
     * The number of operations.
     */
    private Integer nombreOperations;
}
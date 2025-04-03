package com.albaridbank.edition.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

import jakarta.validation.constraints.NotBlank;

/**
 * <h4>Data Transfer Object for generating a report request</h4>
 * <ul>
 *     <li> Pour demander la génération d'un rapport</li>
 * </ul>
 *
 * @author Mohamed Amine Eddafir
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenerationRapportRequestDTO {

    /**
     * The format of the report (PDF or EXCEL).
     * This field is required.
     */
    @NotBlank(message = "Le format est obligatoire (PDF ou EXCEL)")
    private String format;

    /**
     * The client ID associated with the report.
     */
    private Long idClient;

    /**
     * The account ID associated with the report.
     */
    private Long idCompte;

    /**
     * The start date for the report.
     */
    private LocalDate dateDebut;

    /**
     * The end date for the report.
     */
    private LocalDate dateFin;

    /**
     * The agency code associated with the report.
     */
    private String codeAgence;

    /**
     * Specific parameters for the report.
     * This field is a map of parameter names to their values.
     */
    private Map<String, Object> parametresSpecifiques;
}
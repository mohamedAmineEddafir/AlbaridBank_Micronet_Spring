package com.albaridbank.edition.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * <h4>Data Transfer Object for creating a report</h4>
 *  <ul>
 *      <li> Créer un nouveau modèle de rapport</li>
 *  </ul>
 *
 * @author Mohamed Amine Eddafir
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RapportCreationDTO {

    /**
     * The title of the report.
     * This field is required and has a maximum length of 100 characters.
     */
    @NotBlank(message = "Le titre est obligatoire")
    @Size(max = 100, message = "Le titre ne peut pas dépasser 100 caractères")
    private String titre;

    /**
     * The description of the report.
     * This field is optional and has a maximum length of 255 characters.
     */
    @Size(max = 255, message = "La description ne peut pas dépasser 255 caractères")
    private String description;

    /**
     * The type of the report.
     * This field is required and has a maximum length of 50 characters.
     */
    @NotBlank(message = "Le type de rapport est obligatoire")
    @Size(max = 50, message = "Le type ne peut pas dépasser 50 caractères")
    private String typeRapport;

    /**
     * The available formats for the report (e.g., "PDF", "EXCEL" or "PDF,EXCEL").
     */
    private String formatDisponible;

    /**
     * The list of parameters for the report.
     */
    private List<ParametreRapportCreationDTO> parametres;

    /**
     * The content of a custom template for the report (optional).
     */
    private byte[] modeleContenu;

    /**
     * The name of the custom template for the report (optional).
     */
    private String modeleNom;
}
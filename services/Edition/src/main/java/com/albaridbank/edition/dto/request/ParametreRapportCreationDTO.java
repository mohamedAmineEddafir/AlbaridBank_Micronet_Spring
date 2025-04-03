package com.albaridbank.edition.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * <h4>Data Transfer Object for creating a report parameter.</h4>
 * <ul>
 *     <li>Définir les paramètres à la création</li>
 *
 * @author Mohamed Amine Eddafir
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParametreRapportCreationDTO {

    /**
     * The name of the parameter.
     * This field is required and has a maximum length of 50 characters.
     */
    @NotBlank(message = "Le nom du paramètre est obligatoire")
    @Size(max = 50, message = "Le nom ne peut pas dépasser 50 caractères")
    private String nom;

    /**
     * The default value of the parameter.
     * This field is optional.
     */
    private String valeurDefaut;

    /**
     * The type of the parameter.
     * This field is required and has a maximum length of 20 characters.
     */
    @NotBlank(message = "Le type du paramètre est obligatoire")
    @Size(max = 20, message = "Le type ne peut pas dépasser 20 caractères")
    private String type;

    /**
     * Indicates whether the parameter is mandatory.
     */
    private boolean obligatoire;

    /**
     * The description of the parameter.
     * This field is optional and has a maximum length of 200 characters.
     */
    @Size(max = 200, message = "La description ne peut pas dépasser 200 caractères")
    private String description;
}
package com.albaridbank.edition.controller;

import com.albaridbank.edition.dto.rapport.CompteMouvementVeilleDTO;
import com.albaridbank.edition.dto.rapport.NbrTotalEncoursCCPDTO;
import com.albaridbank.edition.dto.rapport.PortefeuilleClientCCPDTO;
import com.albaridbank.edition.dto.rapport.PortefeuilleClientCCPRapportDTO;
import com.albaridbank.edition.service.interfaces.RapportCCPService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EntityNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * <p>
 * REST controller for generating CCP reports.
 * Provides endpoints for generating client portfolio, financial movements, and global balance reports.
 * Includes Swagger annotations for API documentation.
 * </p>
 *
 * @author Mohamed Amine Eddafir
 */
@RestController
@RequestMapping("/api/v1/rapportccp")
@Tag(name = "Rapports CCP", description = "Endpoints for generating CCP reports")
@SecurityRequirement(name = "bearer-key")
@RequiredArgsConstructor
@Slf4j
public class RapportClientCCPController {

    private final RapportCCPService rapportCCPService;

    /**
     * Generates a report on the client portfolio status for a specific bureau.
     *
     * @param codeBureau The identifier of the postal bureau.
     * @param page       The page number for pagination (default is 0).
     * @param size       The size of the page for pagination (default is 10).
     * @param sortBy     The field to sort by (default is "codeProduit").
     * @param sortDir    The sort direction (default is "asc").
     * @return A ResponseEntity containing the client portfolio report or an appropriate HTTP status.
     */
    @Operation(
            summary = "Generate client portfolio status report",
            description = "Retrieves the list of active accounts for a specific bureau with their balances"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Report successfully generated"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "404", description = "Bureau not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/agence/{codeBureau}/portefeuille-client")
    public ResponseEntity<PortefeuilleClientCCPDTO> getRapportPortefeuilleClient(
            @PathVariable @Parameter(description = "Postal bureau identifier", required = true) Long codeBureau,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "codeProduit") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        log.info("Generating CCP Client Portfolio report for bureau: {}, page: {}, size: {}, sort: {} {}",
                codeBureau, page, size, sortBy, sortDir);

        try {
            // Vérification de sécurité et audit
            //String username = getCurrentUsername();
            //log.info("Request initiated by user: {}", username);

            // Validation des paramètres
            if (page < 0 || size <= 0) {
                log.warn("Invalid pagination parameters: page={}, size={}", page, size);
                return ResponseEntity.badRequest().build();
            }

            // Création d'un objet de pagination avec tri dynamique
            Sort sort = sortDir.equalsIgnoreCase("desc") ?
                    Sort.by(sortBy).descending() :
                    Sort.by(sortBy).ascending();

            Pageable pageable = PageRequest.of(page, size, sort);

            // Appel au service
            Page<PortefeuilleClientCCPDTO> resultPage = rapportCCPService.genererRapportPortefeuilleClient(pageable, codeBureau);

            // Vérification des résultats
            if (resultPage.isEmpty()) {
                log.warn("No report found for bureau: {}", codeBureau);
                return ResponseEntity.notFound().build();
            }

            // Ajout d'en-têtes supplémentaires pour la pagination
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Total-Count", String.valueOf(resultPage.getTotalElements()));
            headers.add("X-Total-Pages", String.valueOf(resultPage.getTotalPages()));

            PortefeuilleClientCCPDTO rapport = resultPage.getContent().getFirst();

            /*
                // Enrichir le rapport avec des métadonnées d'audit
                rapport.setCreatedBy(username);
                rapport.setCreationDateTime(LocalDateTime.now());
            **/

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(rapport);

        } catch (EntityNotFoundException e) {
            log.error("Bureau not found: {}", codeBureau);
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            log.error("Invalid argument: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Error generating report for bureau: {}", codeBureau, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Generates a financial movements report for a specific bureau.
     *
     * <p>This endpoint retrieves financial movements filtered by date and minimum amount.
     * It supports pagination and sorting by descending amount.
     *
     * @param codeAgence     The identifier of the postal bureau.
     * @param montantMinimum The minimum amount of movements to include (default is 0).
     * @param joursAvant     The number of days before today to filter movements (default is 1).
     * @param page           The page number for pagination (default is 0).
     * @param size           The number of items per page for pagination (default is 20).
     * @return A {@link ResponseEntity} containing the financial movements report.
     */
    @Operation(
            summary = "Générer un rapport de mouvements financiers",
            description = "Récupère les mouvements financiers filtrés par date et montant minimum"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rapport généré avec succès"),
            @ApiResponse(responseCode = "204", description = "Aucun mouvement trouvé"),
            @ApiResponse(responseCode = "400", description = "Paramètres invalides"),
            @ApiResponse(responseCode = "404", description = "Bureau non trouvé"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping("/compte-mouvement-veille")
    public ResponseEntity<CompteMouvementVeilleDTO> genererRapport(
            @RequestParam Long codeAgence,
            @RequestParam(required = false, defaultValue = "0") BigDecimal montantMinimum,
            @RequestParam(required = false, defaultValue = "1") Integer joursAvant,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {

        log.info("Generating financial movements report for bureau: {}", codeAgence);
        log.info("Minimum amount: {}", montantMinimum);
        log.info("Days before: {}", joursAvant);
        log.info("Page: {}, Size: {}", page, size);

        // Create a pagination object with sorting by descending amount
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "montant")
        );

        // Call the service to generate the report
        CompteMouvementVeilleDTO rapport = rapportCCPService.rapportMouvementVeille(
                codeAgence,
                montantMinimum,
                joursAvant,
                pageable
        );

        return ResponseEntity.ok(rapport);
    }

    /**
     * Endpoint to retrieve the total number of accounts and the global outstanding balance for a specific CCP bureau.
     *
     * <p>This method fetches global statistics for CCP accounts associated with a given postal bureau.
     * It returns the total number of accounts and the total outstanding balance in a DTO format.
     *
     * @param codeBureau The identifier of the postal bureau for which the statistics are retrieved.
     * @return A {@link ResponseEntity} containing a {@link NbrTotalEncoursCCPDTO} object with the statistics.
     * If the bureau is not found, a 404 status is returned.
     */
    @Operation(
            summary = "Obtenir le nombre total de comptes et l'encours global CCP",
            description = "Récupère les statistiques globales des comptes CCP pour un bureau de poste spécifique"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Statistiques trouvées",
                    content = @Content(
                            schema = @Schema(implementation = NbrTotalEncoursCCPDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Bureau non trouvé",
                    content = @Content
            )
    })
    @GetMapping("/encours-global/{codeBureau}")
    public ResponseEntity<NbrTotalEncoursCCPDTO> getEncoursGlobal(
            @Parameter(
                    description = "Code du bureau de poste (Agence)",
                    example = "12345",
                    required = true
            )
            @PathVariable Long codeBureau) {
        return ResponseEntity.ok(rapportCCPService.genererRapportEncoursGlobal(codeBureau));
    }


    /**
     * Generates a detailed client portfolio report for a specific bureau.
     *
     * @param codeBureau Bureau postal code
     * @param typeCompte Account type filter
     * @param etatCompte Account state filter
     * @param page       Page number (zero-based)
     * @param size       Number of items per page
     * @return Portfolio report with filtered accounts and statistics
     */
    @Operation(
            summary = "Générer un rapport détaillé du portefeuille client",
            description = """
                    Génère un rapport détaillé du portefeuille client CCP pour un bureau postal spécifique.
                    
                    Fonctionnalités:
                    - Filtrage par type de compte et état
                    - Pagination des résultats
                    - Tri automatique par solde courant décroissant
                    - Calcul des statistiques globales
                    
                    Le rapport inclut:
                    - Informations détaillées du bureau
                    - Liste paginée des comptes filtrés
                    - Statistiques consolidées des soldes
                    - États détaillés des comptes
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Rapport généré avec succès",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PortefeuilleClientCCPRapportDTO.class),
                            examples = @ExampleObject(
                                    name = "Exemple de réponse",
                                    value = """
                                            {
                                              "titreRapport": "ETAT PORTE FEUILLE CLIENT M CCP",
                                              "dateEdition": "15/05/2025 23:44:41",
                                              "codburpo": 1000,
                                              "desburpo": "RABAT RP",
                                              "nombreTotalComptes": 150,
                                              "encoursTotalComptes": "2,470,087,666.88",
                                              "comptes": [
                                                {
                                                  "idencomp": "13004727",
                                                  "inticomp": "Mr EXEMPLE CLIENT",
                                                  "etatCompte": "N",
                                                  "typeCompteLibelle": "Normal",
                                                  "soldcour": 2470087666.88
                                                }
                                              ]
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Paramètres de requête invalides",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "status": 400,
                                              "message": "État de compte invalide: X",
                                              "timestamp": "2025-05-15T23:44:41Z"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Bureau postal non trouvé",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erreur interne du serveur",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @GetMapping(
            value = "/portefeuille-general",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PortefeuilleClientCCPRapportDTO> genererRapport(
            @Parameter(
                    description = "Code du bureau postal",
                    required = true,
                    example = "1000",
                    schema = @Schema(type = "integer", minimum = "1")
            )
            @RequestParam
            @Min(value = 1, message = "Le code bureau doit être supérieur à 0")
            Long codeBureau,

            @Parameter(
                    description = """
                            Type de compte:
                            * `1` - Compte courant postal
                            * `2` - Compte épargne
                            * `3` - Compte professionnel
                            * `4` - Compte spécial
                            * `null` - Tous les types
                            """,
                    schema = @Schema(
                            type = "integer",
                            allowableValues = {"1", "2", "3", "4"},
                            example = "1"
                    )
            )
            @RequestParam(required = false)
            Integer typeCompte,

            @Parameter(
                    description = """
                            État du compte:
                            * `N/NORMAL` - Compte normal
                            * `O/OPPOSE` - Compte opposé
                            * `C/CLOTURE` - Compte clôturé
                            * `B/BLOCAGE` - Compte bloqué
                            * `null` - Tous les états actifs
                            
                            La casse n'est pas prise en compte.
                            """,
                    schema = @Schema(
                            type = "string",
                            allowableValues = {
                                    "N", "O", "C", "B",
                                    "NORMAL", "OPPOSE", "CLOTURE", "BLOCAGE"
                            },
                            example = "NORMAL"
                    )
            )
            @RequestParam(required = false)
            String etatCompte,

            @Parameter(
                    description = "Numéro de page (commence à 0)",
                    schema = @Schema(type = "integer", minimum = "0", defaultValue = "0")
            )
            @RequestParam(defaultValue = "0")
            @Min(value = 0, message = "Le numéro de page doit être supérieur ou égal à 0")
            int page,

            @Parameter(
                    description = "Nombre d'éléments par page",
                    schema = @Schema(type = "integer", minimum = "1", defaultValue = "10")
            )
            @RequestParam(defaultValue = "10")
            @Min(value = 1, message = "La taille de la page doit être supérieure à 0")
            int size,

            @Parameter(
                    description = "Terme de recherche (nom du client, numéro de compte, adresse, etc.)",
                    schema = @Schema(type = "string")
            )
            @RequestParam(required = false)
            String search,

            @Parameter(
                    description = "Indique si la recherche doit être globale (tous les résultats sans pagination)",
                    schema = @Schema(type = "boolean", defaultValue = "false")
            )
            @RequestParam(defaultValue = "false")
            boolean globalSearch
    ) {
        log.debug("Demande de génération de rapport - Bureau: {}, Type: {}, État: {}, Page: {}, Taille: {}, Recherche: {}, Recherche globale: {}",
                codeBureau, typeCompte, etatCompte, page, size, search, globalSearch);

        Pageable pageable = createPageableWithSort(page, size);

        PortefeuilleClientCCPRapportDTO rapport;

        if (StringUtils.hasText(search)) {
            if (globalSearch) {
                rapport = rapportCCPService.genererRapportPortefeuilleClientRechercheGlobale(
                        codeBureau,
                        typeCompte,
                        etatCompte,
                        search
                );
            } else {
                rapport = rapportCCPService.genererRapportPortefeuilleClientRecherche(
                        codeBureau,
                        pageable,
                        typeCompte,
                        etatCompte,
                        search
                );
            }
        } else {
            rapport = rapportCCPService.genererRapportPortefeuilleClientFiltre(
                    codeBureau,
                    pageable,
                    typeCompte,
                    etatCompte
            );
        }

        log.debug("Rapport généré avec succès pour le bureau: {}", codeBureau);

        return ResponseEntity.ok(rapport);
    }

    /**
     * Creates a Pageable object with default sorting by current balance in descending order.
     *
     * @param page Page number (zero-based)
     * @param size Number of items per page
     * @return Configured {@link Pageable} object
     */
    private Pageable createPageableWithSort(int page, int size) {
        return PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "soldeCourant")
        );
    }

}
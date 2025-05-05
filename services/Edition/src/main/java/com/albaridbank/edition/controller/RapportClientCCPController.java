package com.albaridbank.edition.controller;

import com.albaridbank.edition.dto.rapport.CompteMouvementVeilleDTO;
import com.albaridbank.edition.dto.rapport.NbrTotalEncoursCCPDTO;
import com.albaridbank.edition.dto.rapport.PortefeuilleClientCCPDTO;
import com.albaridbank.edition.service.interfaces.RapportCCPService;
import jakarta.persistence.EntityNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * Contrôleur REST pour la génération de rapports CCP.
 * Ce contrôleur expose les endpoints permettant de générer différents types de rapports
 * pour les comptes CCP (Comptes Chèques Postaux).
 *
 * @author Mohamed Amine Eddafir
 */
@RestController
@RequestMapping("/api/rapports-ccp")
@Tag(name = "Rapports CCP", description = "Endpoints pour la génération de rapports CCP")
@SecurityRequirement(name = "bearer-key")
@RequiredArgsConstructor
@Slf4j
public class RapportClientCCPController {

    private final RapportCCPService rapportCCPService;

    /**
     * Génère un rapport sur l'état du portefeuille client pour un bureau spécifique.
     * Ce rapport inclut la liste des comptes actifs du bureau et le solde total.
     *
     * @param codeBureau Identifiant du bureau postal
     * @param page Numéro de la page à récupérer (commence à 0)
     * @param size Nombre d'éléments par page
     * @return Le rapport du portefeuille client avec les détails des comptes
     */
    @Operation(
            summary = "Générer un rapport d'état du portefeuille client",
            description = "Récupère la liste des comptes actifs pour un bureau spécifique avec leurs soldes et le solde total",
            security = @SecurityRequirement(name = "bearer-key")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Rapport généré avec succès",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PortefeuilleClientCCPDTO.class))
            ),
            @ApiResponse(responseCode = "400", description = "Paramètres invalides", content = @Content),
            @ApiResponse(responseCode = "404", description = "Bureau non trouvé", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur", content = @Content)
    })
    @GetMapping("/etat-portefeuille-client/{codeBureau}")
    public ResponseEntity<PortefeuilleClientCCPDTO> getRapportPortefeuilleClient(
            @PathVariable
            @Parameter(description = "Identifiant du bureau postal", required = true, example = "12345")
            Long codeBureau,

            @RequestParam(defaultValue = "0")
            @Parameter(description = "Numéro de page (commence à 0)", example = "0")
            int page,

            @RequestParam(defaultValue = "10")
            @Parameter(description = "Nombre d'éléments par page", example = "10")
            int size
    ) {
        log.info("Génération du rapport Portefeuille Client CCP pour le bureau: {}", codeBureau);

        try {
            if (codeBureau == null) {
                return ResponseEntity.badRequest().build();
            }

            Pageable pageable = PageRequest.of(page, size, Sort.by("codeProduit").ascending());
            Page<PortefeuilleClientCCPDTO> resultPage = rapportCCPService.genererRapportPortefeuilleClient(pageable, codeBureau);

            if (resultPage.isEmpty()) {
                log.warn("Aucun rapport trouvé pour le bureau: {}", codeBureau);
                return ResponseEntity.notFound().build();
            }

            // Extraction du premier élément de la page
            PortefeuilleClientCCPDTO report = resultPage.getContent().getFirst();
            return ResponseEntity.ok(report);
        } catch (EntityNotFoundException e) {
            log.error("Bureau non trouvé: {}", codeBureau, e);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Erreur lors de la génération du rapport pour le bureau: {}", codeBureau, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Génère un rapport des mouvements financiers pour un bureau spécifique.
     * Le rapport inclut les mouvements financiers sur une période définie et avec un montant minimum.
     *
     * @param codeBureau Identifiant du bureau postal
     * @param joursAvant Nombre de jours avant aujourd'hui (1 pour hier, 2 pour avant-hier, etc.)
     * @param montantMinimum Montant minimum des mouvements à inclure
     * @param pageable Informations de pagination
     * @return Un rapport paginé des mouvements financiers
     */
    @Operation(
            summary = "Générer un rapport des mouvements financiers",
            description = "Récupère les mouvements financiers pour un bureau spécifique, filtrés par date et montant minimum",
            security = @SecurityRequirement(name = "bearer-key")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Rapport généré avec succès",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CompteMouvementVeilleDTO.class))
            ),
            @ApiResponse(responseCode = "400", description = "Paramètres de requête invalides", content = @Content),
            @ApiResponse(responseCode = "404", description = "Bureau non trouvé", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur", content = @Content)
    })
    @GetMapping("/mouvements")
    public ResponseEntity<Page<CompteMouvementVeilleDTO>> getRapportMouvements(
            @RequestParam
            @Parameter(description = "Identifiant du bureau postal", required = true, example = "12345")
            Long codeBureau,

            @RequestParam(defaultValue = "1")
            @Parameter(description = "Nombre de jours avant aujourd'hui (1 pour hier, 2 pour avant-hier, etc.)", example = "1")
            Integer joursAvant,

            @RequestParam
            @Parameter(description = "Montant minimum des mouvements à inclure", example = "1000.00")
            BigDecimal montantMinimum,

            @Parameter(hidden = true)
            @PageableDefault(size = 20, sort = "dateMouvement", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        log.info("Génération du rapport de mouvements pour le bureau: {}, jours: {}, montant minimum: {}",
                codeBureau, joursAvant, montantMinimum);

        // Validation des paramètres d'entrée
        if (codeBureau == null || joursAvant == null || joursAvant <= 0 || montantMinimum == null) {
            log.warn("Paramètres invalides: codeBureau={}, joursAvant={}, montantMinimum={}",
                    codeBureau, joursAvant, montantMinimum);
            return ResponseEntity.badRequest().build();
        }

        try {
            Page<CompteMouvementVeilleDTO> rapport = rapportCCPService.genererRapportMouvementVeille(
                    codeBureau, joursAvant, montantMinimum, pageable);

            if (rapport.isEmpty()) {
                log.info("Aucun mouvement trouvé pour les critères spécifiés");
            }

            return ResponseEntity.ok(rapport);
        } catch (EntityNotFoundException e) {
            log.error("Bureau non trouvé: {}", codeBureau, e);
            return ResponseEntity.notFound().build();
        } catch (ServiceException e) {
            log.error("Erreur de service lors de la génération du rapport: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            log.error("Erreur inattendue lors de la génération du rapport de mouvements", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Génère un rapport d'encours global pour un bureau spécifique.
     * Note : Cette méthode est un placeholder car l'implémentation n'est pas encore complète dans le service.
     *
     * @param codeBureau Identifiant du bureau postal
     * @return Le rapport d'encours global ou 501 Not Implemented
     */
    @Operation(
            summary = "Générer un rapport d'encours global",
            description = "Récupère le rapport d'encours global pour un bureau spécifique",
            security = @SecurityRequirement(name = "bearer-key")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Rapport généré avec succès",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = NbrTotalEncoursCCPDTO.class))
            ),
            @ApiResponse(responseCode = "404", description = "Bureau non trouvé", content = @Content),
            @ApiResponse(responseCode = "501", description = "Fonctionnalité non implémentée", content = @Content)
    })
    @GetMapping("/encours-global/{codeBureau}")
    public ResponseEntity<NbrTotalEncoursCCPDTO> getRapportEncoursGlobal(
            @PathVariable
            @Parameter(description = "Identifiant du bureau postal", required = true, example = "12345")
            Long codeBureau
    ) {
        log.info("Demande de rapport d'encours global pour le bureau: {}", codeBureau);

        NbrTotalEncoursCCPDTO rapport = rapportCCPService.genererRapportEncoursGlobal(codeBureau);

        if (rapport == null) {
            log.warn("La fonctionnalité de rapport d'encours global n'est pas encore implémentée");
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
        }

        return ResponseEntity.ok(rapport);
    }
}
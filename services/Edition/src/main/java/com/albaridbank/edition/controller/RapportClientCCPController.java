package com.albaridbank.edition.controller;

import com.albaridbank.edition.dto.rapport.CompteMouvementVeilleDTO;
import com.albaridbank.edition.dto.rapport.NbrTotalEncoursCCPDTO;
import com.albaridbank.edition.dto.rapport.PortefeuilleClientCCPDTO;
import com.albaridbank.edition.service.interfaces.RapportCCPService;
import jakarta.persistence.EntityNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for generating CCP reports.
 * Provides endpoints for generating client portfolio, financial movements, and global balance reports.
 * Includes Swagger annotations for API documentation.
 * <p>
 *
 * @author Mohamed Amine Eddafir
 */
@RestController
@RequestMapping("/api/rapports-ccp")
@Tag(name = "Rapports CCP", description = "Endpoints for generating CCP reports")
@SecurityRequirement(name = "bearer-key")
@RequiredArgsConstructor
@Slf4j
public class RapportClientCCPController {

    private final RapportCCPService rapportCCPService;
    private static final List<String> VALID_SORT_PROPERTIES = List.of("dateCreation", "montant", "dateMouvement");

    /**
     * Generates a report on the client portfolio status for a specific bureau.
     *
     * @param codeBureau The identifier of the postal bureau.
     * @param page       The page number for pagination (default is 0).
     * @param size       The size of the page for pagination (default is 10).
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
            @RequestParam(defaultValue = "10") int size) {

        log.info("Generating CCP Client Portfolio report for bureau: {}", codeBureau);

        try {
            if (codeBureau == null) {
                return ResponseEntity.badRequest().build();
            }

            Pageable pageable = PageRequest.of(page, size, Sort.by("codeProduit").ascending());
            Page<PortefeuilleClientCCPDTO> resultPage = rapportCCPService.genererRapportPortefeuilleClient(pageable, codeBureau);

            if (resultPage.isEmpty()) {
                log.warn("No report found for bureau: {}", codeBureau);
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(resultPage.getContent().getFirst());
        } catch (EntityNotFoundException e) {
            log.error("Bureau not found: {}", codeBureau);
            return ResponseEntity.notFound().build();
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
            @RequestParam(required = false, defaultValue = "20") int size) {

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
     * Generates a global balance report for a specific bureau.
     *
     * @param codeBureau The postal bureau code.
     * @return A ResponseEntity containing the global balance report or an appropriate HTTP status.
     */
    @Operation(
            summary = "Generate global balance report",
            description = "Retrieves the global balance report for a specific bureau"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Report successfully generated"),
            @ApiResponse(responseCode = "404", description = "Bureau not found"),
            @ApiResponse(responseCode = "501", description = "Feature not implemented")
    })
    @GetMapping("/encours-global/{codeBureau}")
    public ResponseEntity<NbrTotalEncoursCCPDTO> getRapportEncoursGlobal(
            @PathVariable Long codeBureau) {

        log.info("Requesting global balance report for bureau: {}", codeBureau);
        NbrTotalEncoursCCPDTO rapport = rapportCCPService.genererRapportEncoursGlobal(codeBureau);

        if (rapport == null) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
        }

        return ResponseEntity.ok(rapport);
    }
}
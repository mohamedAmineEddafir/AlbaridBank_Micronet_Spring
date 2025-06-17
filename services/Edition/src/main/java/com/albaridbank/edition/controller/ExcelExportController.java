package com.albaridbank.edition.controller;

import com.albaridbank.edition.dto.excelCCP.CompteMouvementVeilleExcelDTO;
import com.albaridbank.edition.dto.excelCCP.NbrTotalEncoursCCPExcelDTO;
import com.albaridbank.edition.dto.excelCCP.PortefeuilleClientCCPExcelDTO;
import com.albaridbank.edition.dto.excelCCP.PortefeuilleClientCCPMExcelDTO;
import com.albaridbank.edition.dto.rapport.CompteMouvementVeilleDTO;
import com.albaridbank.edition.mappers.rapport.RapportCCPMapper;
import com.albaridbank.edition.service.excelCCP.ExcelExportService;
import com.albaridbank.edition.service.interfaces.RapportCCPService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/v1/export/excel")
@Tag(name = "Excel Export", description = "API pour l'exportation des rapports au format Excel")
@RequiredArgsConstructor
@Slf4j
public class ExcelExportController {

    private final RapportCCPService rapportCCPService;
    private final ExcelExportService excelExportService;
    private final RapportCCPMapper rapportCCPMapper;

    /**
     * Exporte le rapport "ETAT PORTEFEUILLE CLIENT CCP" au format Excel
     *
     * @param codeBureau Code du bureau de poste
     * @param etatCompte État du compte (optionnel)
     * @return Fichier Excel contenant le rapport
     */
    @Operation(
            summary = "Exporter le rapport portefeuille client CCP au format Excel",
            description = "Génère un fichier Excel contenant l'ensemble des comptes clients CCP pour une agence spécifique"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Fichier Excel généré avec succès"),
            @ApiResponse(responseCode = "400", description = "Paramètres invalides"),
            @ApiResponse(responseCode = "404", description = "Bureau non trouvé"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping("/portefeuille-client-ccp/{codeBureau}")
    public ResponseEntity<byte[]> exportPortefeuilleClientCCPToExcel(
            @PathVariable @Parameter(description = "Code du bureau de poste", required = true) Long codeBureau,
            @RequestParam(required = false) @Parameter(description = "État du compte (A: Actif, I: Inactif, etc.)") String etatCompte,
            @RequestHeader(value = "X-User-Agent", required = false, defaultValue = "system") String username) {

        log.info("Exporting CCP Client Portfolio report to Excel for bureau: {}, etatCompte: {}",
                codeBureau, etatCompte);

        try {
            // Récupérer toutes les données sans pagination
            PortefeuilleClientCCPExcelDTO rapportData = rapportCCPService.genererRapportPortefeuilleClientPourExcel(
                    codeBureau, etatCompte, username);

            // Génération du fichier Excel
            byte[] excelBytes = excelExportService.exportPortefeuilleClientCCPToExcel(rapportData);

            // Construction du nom de fichier
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = String.format("Portefeuille_Client_CCP_Agence_%s_%s.xlsx",
                    codeBureau, timestamp);
            String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8)
                    .replace("+", "%20");

            // Configuration des en-têtes HTTP
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headers.setContentDispositionFormData("attachment", encodedFileName);
            headers.setContentLength(excelBytes.length);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(excelBytes);

        } catch (IllegalArgumentException e) {
            log.error("Invalid parameters for Excel export: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (IOException e) {
            log.error("Error generating Excel file: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        } catch (Exception e) {
            log.error("Unexpected error during Excel export: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Exporte le rapport "ETAT DES COMPTES MOUVEMENTES LA VEILLE" au format Excel
     *
     * @param codeAgence     Code de l'agence
     * @param joursAvant     Nombre de jours avant (0=aujourd'hui, 1=veille, 2=avant-veille)
     * @param montantMinimum Montant minimum des mouvements à considérer
     * @return Fichier Excel contenant le rapport
     */
    @Operation(
            summary = "Exporter le rapport des comptes mouvementés au format Excel",
            description = "Génère un fichier Excel contenant les comptes ayant eu des mouvements à la date spécifiée"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Fichier Excel généré avec succès"),
            @ApiResponse(responseCode = "400", description = "Paramètres invalides"),
            @ApiResponse(responseCode = "404", description = "Agence non trouvée"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping("/compte-mouvement-veille/{codeAgence}")
    public ResponseEntity<byte[]> exportCompteMouvementVeilleToExcel(
            @PathVariable @Parameter(description = "Code de l'agence", required = true) Long codeAgence,
            @RequestParam(required = false, defaultValue = "1") @Parameter(description = "Jours avant (0=aujourd'hui, 1=veille, 2=avant-veille)") Integer joursAvant,
            @RequestParam(required = false, defaultValue = "0") @Parameter(description = "Montant minimum des mouvements") BigDecimal montantMinimum,
            @RequestHeader(value = "eddafir_mohamed_amine", required = false, defaultValue = "system") String username) {

        log.info("Exporting Account Movement report to Excel for agency: {}, joursAvant: {}, montantMinimum: {}",
                codeAgence, joursAvant, montantMinimum);

        try {
            // Récupérer le rapport
            CompteMouvementVeilleDTO rapportData = rapportCCPService.genererRapportMouvementVeillePourExcel(
                    codeAgence, joursAvant, montantMinimum, username);

            // Convertir en DTO pour Excel
            CompteMouvementVeilleExcelDTO excelDTO = rapportCCPMapper.toExcelDTO(rapportData);

            // Génération du fichier Excel
            byte[] excelBytes = excelExportService.exportCompteMouvementVeilleToExcel(excelDTO);

            // Construction du nom de fichier
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = String.format("Comptes_Mouvementes_Agence_%s_%s.xlsx",
                    codeAgence, timestamp);
            String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8)
                    .replace("+", "%20");

            // Configuration des en-têtes HTTP
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headers.setContentDispositionFormData("attachment", encodedFileName);
            headers.setContentLength(excelBytes.length);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(excelBytes);

        } catch (IllegalArgumentException e) {
            log.error("Invalid parameters for Excel export: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (IOException e) {
            log.error("Error generating Excel file: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        } catch (Exception e) {
            log.error("Unexpected error during Excel export: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Exporte le rapport "ETAT NOMBRE TOTAL & ENCOURS GLOBAL CCP" au format Excel
     *
     * @param codeBureau Code du bureau de poste
     * @return Fichier Excel contenant le rapport
     */
    @Operation(
            summary = "Exporter le rapport d'encours global CCP au format Excel",
            description = "Génère un fichier Excel contenant le nombre total de comptes et l'encours global pour une agence spécifique"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Fichier Excel généré avec succès"),
            @ApiResponse(responseCode = "400", description = "Paramètres invalides"),
            @ApiResponse(responseCode = "404", description = "Bureau non trouvé"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping("/encours-global-ccp/{codeBureau}")
    public ResponseEntity<byte[]> exportEncoursGlobalCCPToExcel(
            @PathVariable @Parameter(description = "Code du bureau de poste", required = true) Long codeBureau,
            @RequestHeader(value = "eddafir_mohamed_amine", required = false, defaultValue = "system") String username) {

        log.info("Exporting CCP Global Balance report to Excel for bureau: {}", codeBureau);

        try {
            // Récupérer les données pour l'export
            NbrTotalEncoursCCPExcelDTO rapportData = rapportCCPService.genererRapportEncoursGlobalPourExcel(
                    codeBureau, username);

            // Génération du fichier Excel
            byte[] excelBytes = excelExportService.exportEncoursGlobalCCPToExcel(rapportData);

            // Construction du nom de fichier
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = String.format("Encours_Global_CCP_Bureau_%s_%s.xlsx",
                    codeBureau, timestamp);
            String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8)
                    .replace("+", "%20");

            // Configuration des en-têtes HTTP
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headers.setContentDispositionFormData("attachment", encodedFileName);
            headers.setContentLength(excelBytes.length);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(excelBytes);

        } catch (IllegalArgumentException e) {
            log.error("Invalid parameters for Excel export: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (IOException e) {
            log.error("Error generating Excel file: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        } catch (Exception e) {
            log.error("Unexpected error during Excel export: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Exporte le rapport "ETAT PORTEFEUILLE CLIENT M CCP" au format Excel
     *
     * @param codeBureau Code du bureau de poste
     * @param etatCompte État du compte (optionnel)
     * @return Fichier Excel contenant le rapport
     */
    @Operation(
            summary = "Exporter le rapport portefeuille client M CCP au format Excel",
            description = "Génère un fichier Excel contenant l'ensemble des comptes clients CCP pour une agence spécifique avec détails supplémentaires"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Fichier Excel généré avec succès"),
            @ApiResponse(responseCode = "400", description = "Paramètres invalides"),
            @ApiResponse(responseCode = "404", description = "Bureau non trouvé"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping("/portefeuille-client-m-ccp/{codeBureau}")
    public ResponseEntity<byte[]> exportPortefeuilleClientMCCPToExcel(
            @PathVariable @Parameter(description = "Code du bureau de poste", required = true) Long codeBureau,
            @RequestParam(required = false) @Parameter(description = "État du compte (N: Normal, C: Cloturé, B: Bloqué)") String etatCompte,
            @RequestHeader(value = "X-User-Agent", required = false, defaultValue = "system") String username) {

        log.info("Exporting CCP Client Portfolio M report to Excel for bureau: {}, etatCompte: {}",
                codeBureau, etatCompte);

        try {
            // Récupérer toutes les données sans pagination
            PortefeuilleClientCCPMExcelDTO rapportData = rapportCCPService.genererRapportPortefeuilleClientMPourExcel(
                    codeBureau, etatCompte, username);

            // Génération du fichier Excel
            byte[] excelBytes = excelExportService.exportPortefeuilleClientMCCPToExcel(rapportData);

            // Construction du nom de fichier
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = String.format("Portefeuille_Client_M_CCP_Agence_%s_%s.xlsx",
                    codeBureau, timestamp);
            String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8)
                    .replace("+", "%20");

            // Configuration des en-têtes HTTP
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headers.setContentDispositionFormData("attachment", encodedFileName);
            headers.setContentLength(excelBytes.length);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(excelBytes);

        } catch (IllegalArgumentException e) {
            log.error("Invalid parameters for Excel export: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (IOException e) {
            log.error("Error generating Excel file: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        } catch (Exception e) {
            log.error("Unexpected error during Excel export: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
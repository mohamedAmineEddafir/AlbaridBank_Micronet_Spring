package com.albaridbank.edition.controller;

import com.albaridbank.edition.dto.excelCCP.PortefeuilleClientCCPExcelDTO;
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
            @RequestHeader(value = "eddafir_mohamed_amine", required = false, defaultValue = "system") String username) {

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
}
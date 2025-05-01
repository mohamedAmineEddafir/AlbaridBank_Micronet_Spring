package com.albaridbank.edition.controller;

import com.albaridbank.edition.dto.rapport.PortefeuilleClientCCPDTO;
import com.albaridbank.edition.service.interfaces.RapportCCPService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rapports")
@RequiredArgsConstructor
@Slf4j
public class RapportClientCCPController {

    private final RapportCCPService rapportCCPService;

    /**
     * Endpoint to generate the "Etat_PorteFuilleClient_CCP" report.
     * Example URL: /api/rapports/etat-portefeuille-client/1?page=0&size=10
     *
     * @param codeBureau Bureau code.
     * @param page       Page number to retrieve (starts at 0).
     * @param size       Number of items per page.
     * @return PortefeuilleClientCCPDTO containing the report data.
     */
    @GetMapping("/etat-portefeuille-client/{codeBureau}")
    public ResponseEntity<PortefeuilleClientCCPDTO> getRapportPortefeuilleClient(
            @PathVariable Long codeBureau,
            @Parameter(description = "Page number to retrieve (starts at 0)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page", example = "10")
            @RequestParam(defaultValue = "10") int size
    ) {
        log.info("Generating Portefeuille Client CCP report for bureau: {}", codeBureau);
        Pageable pageable = PageRequest.of(page, size, Sort.by("codeProduit").ascending());
        // The service returns a Page containing a single report, so we extract the first element.
        PortefeuilleClientCCPDTO report = rapportCCPService
                .genererRapportPortefeuilleClient(pageable, codeBureau)
                .getContent()
                .getFirst();
        return ResponseEntity.ok(report);
    }
}
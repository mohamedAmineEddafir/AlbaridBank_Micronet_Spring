package com.albaridbank.BankinApp.controller;

import com.albaridbank.BankinApp.dtos.ClientDTO;
import com.albaridbank.BankinApp.service.interfaces.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/Client")
@RequiredArgsConstructor
@Slf4j
//@Tag(name = "Client", description = "The Client API") // Documentation OpenAPI/Swagger 3 (Springdoc)
public class CustomerController {
    // Dependency injection via constructor (thanks to @RequiredArgsConstructor)
    private final ClientService clientService;

    /**
     * GET /api/v1/clients : Récupère tous les clients avec pagination
     *
     * @param page numéro de la page (commence à 0)
     * @param size nombre d'éléments par page
     * @param sort champ sur lequel effectuer le tri
     * @return une page de clients
     */
    @GetMapping
    // @Operation(summary = "Get all clients with pagination", description = "Retrieves all clients with pagination")
    public ResponseEntity<Page<ClientDTO>> getAllClients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "clientId") String sort) {
        log.info("Getting all clients with pagination: page={}, size={}, sort={}", page, size, sort);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return ResponseEntity.ok(clientService.getAllClients(pageable));
    }

    /**
     * GET /api/v1/clients/{id} : Récupère un client par son ID
     *
     * @param id l'ID du client à récupérer
     * @return le client correspondant à l'ID
     */
    @GetMapping("/{id}")
    /*
     *  @Operation(summary = "Get a client by ID", description = "Retrieves a client by their ID")
     *  @ApiResponses({                       // Documentation des réponses possibles
     *      @ApiResponse(responseCode = "200", description = "Client trouvé"),
     *      @ApiResponse(responseCode = "404", description = "Client non trouvé")
     **/
    public ResponseEntity<ClientDTO> getClientById(
            @PathVariable BigDecimal id) {          //  Récupère la valeur dans le chemin d'URL
        log.info("REST request to get Client with id: {}", id);
        return ResponseEntity.ok(clientService.getClientById(id));
    }

    /**
     * GET /api/v1/clients/status/{status} : Récupère les clients par statut
     *
     * @param status le statut des clients à récupérer
     * @return liste des clients avec le statut spécifié
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ClientDTO>>getClientByStatus(
            @PathVariable Integer status) {
        log.info("REST request to get Clients by status: {}", status);
        return ResponseEntity.ok(clientService.getClientByStatus(status));
    }

}

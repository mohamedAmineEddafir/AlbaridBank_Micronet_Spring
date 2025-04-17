package com.albaridbank.brancheservice.controller;

import com.albaridbank.brancheservice.dto.BranchDTO;
import com.albaridbank.brancheservice.dto.BranchSimpleDTO;
import com.albaridbank.brancheservice.service.interfaces.AgenceService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing branches.
 * Provides endpoints for retrieving branch information with various filters and formats.
 * This controller handles requests related to branches (agences) in the AlbaridBank system.
 *
 * @author Mohamed Amine Eddafir
 */
@RestController
@RequestMapping("/api/v1/branches")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Branches", description = "API for managing bank branches (agences)")
@SecurityRequirement(name = "bearer-key")
public class BrancheController {

    private final AgenceService agenceService;

    /**
     * Retrieves all branches with pagination and sorting.
     *
     * @param page The page number to retrieve (default is 0).
     * @param size The number of items per page (default is 10).
     * @param sort The field to sort by (default is "codburpo").
     * @return A paginated list of branches.
     */
    @Operation(summary = "Retrieve all branches", description = "Retrieves a paginated and sorted list of all branches.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping
    public ResponseEntity<Page<BranchDTO>> getAllBranches(
            @Parameter(description = "Page number to retrieve (starts at 0)", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page", example = "10") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Field to sort by", example = "codburpo") @RequestParam(defaultValue = "codburpo") String sort) {
        log.info("Getting all branches with pagination: page={}, size={}, sort={}", page, size, sort);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return ResponseEntity.ok(agenceService.getAllBranches(pageable));
    }

    /**
     * Retrieves a branch by its code.
     *
     * @param codeAgence The unique code of the branch to retrieve.
     * @return The branch with the specified code.
     */
    @Operation(summary = "Get branch by code", description = "Retrieves the details of a specific branch using its unique code.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Branch found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BranchDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Branch not found with the given code", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/{codeAgence}")
    public ResponseEntity<BranchDTO> getBranchById(
            @Parameter(description = "Unique code of the branch", required = true, example = "B001") @PathVariable String codeAgence) {
        log.info("REST request to get Branch with code: {}", codeAgence);
        return ResponseEntity.ok(agenceService.getBranchById(codeAgence));
    }

    /**
     * Retrieves branches by their status.
     *
     * @param statut The status of the branches to retrieve.
     * @param page   The page number to retrieve (default is 0).
     * @param size   The number of items per page (default is 10).
     * @param sort   The field to sort by (default is "codburpo").
     * @return A paginated list of branches with the specified status.
     */
    @Operation(summary = "Get branches by status", description = "Retrieves a paginated list of branches filtered by their status.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "400", description = "Invalid status value provided", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/statut/{statut}")
    public ResponseEntity<Page<BranchDTO>> getBranchesBystatut(
            @Parameter(description = "Status of the branches to retrieve", required = true, example = "Active") @PathVariable String statut,
            @Parameter(description = "Page number to retrieve (starts at 0)", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page", example = "10") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Field to sort by", example = "codburpo") @RequestParam(defaultValue = "codburpo") String sort) {
        log.info("Getting all branches with statut: page={}, size={}, sort={}", page, size, sort);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return ResponseEntity.ok(agenceService.getBranchesBystatut(pageable, statut));
    }

    /**
     * Searches for branches by name.
     *
     * @param nomAgence The name or partial name of the branches to search for.
     * @return A list of branches matching the specified name.
     */
    @Operation(summary = "Search branches by name", description = "Searches for branches where the name contains the provided query string.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "Missing 'nomAgence' query parameter", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/search")
    public ResponseEntity<List<BranchDTO>> searchBranchesByName(
            @Parameter(description = "Name or partial name to search for", required = true, example = "Central") @RequestParam String nomAgence) {
        log.info("REST request to search Branches by name: {}", nomAgence);
        return ResponseEntity.ok(agenceService.searchBranchesByName(nomAgence));
    }

    /**
     * Retrieves branches by their region.
     *
     * @param region The region of the branches to retrieve.
     * @return A list of branches in the specified region.
     */
    @Operation(summary = "Get branches by region", description = "Retrieves a list of branches located in the specified region.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/region/{region}")
    public ResponseEntity<List<BranchDTO>> getBranchesByRegion(
            @Parameter(description = "Region name", required = true, example = "North") @PathVariable String region) {
        log.info("REST request to get Branches with region: {}", region);
        return ResponseEntity.ok(agenceService.getBranchesByRegion(region));
    }

    /**
     * Retrieves simplified branch information with pagination and sorting.
     *
     * @param page The page number to retrieve (default is 0).
     * @param size The number of items per page (default is 10).
     * @param sort The field to sort by (default is "codburpo").
     * @return A paginated list of simplified branch information.
     */
    @Operation(summary = "Retrieve simplified branch info", description = "Retrieves a paginated list of branches with simplified information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/simpleInfo")
    public ResponseEntity<Page<BranchSimpleDTO>> getSimpleInfoBranches(
            @Parameter(description = "Page number to retrieve (starts at 0)", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page", example = "10") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Field to sort by", example = "codburpo") @RequestParam(defaultValue = "codburpo") String sort) {
        log.info("Getting all branches with simple info: page={}, size={}, sort={}", page, size, sort);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return ResponseEntity.ok(agenceService.getAllBranchesSimple(pageable));
    }
}
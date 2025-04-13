package com.albaridbank.brancheservice.controller;

import com.albaridbank.brancheservice.dto.BranchDTO;
import com.albaridbank.brancheservice.dto.BranchSimpleDTO;
import com.albaridbank.brancheservice.service.interfaces.AgenceService;
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
 * Provides endpoints for retrieving branch information with various filters and pagination.
 *
 * @Author Mohamed Amine Eddafir
 */
@RestController
@RequestMapping("/api/v1/branches")
@RequiredArgsConstructor
@Slf4j
public class BrancheController {

    private final AgenceService agenceService;

    /**
     * GET /api/v1/branches : Retrieves all branches with pagination and sorting.
     *
     * @param page The page number to retrieve (default is 0).
     * @param size The number of items per page (default is 10).
     * @param sort The field to sort by (default is "codburpo").
     * @return A paginated list of branches.
     */
    @GetMapping
    public ResponseEntity<Page<BranchDTO>> getAllBranches(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "codburpo") String sort) {
        log.info("Getting all branches with pagination: page={}, size={}, sort={}", page, size, sort);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return ResponseEntity.ok(agenceService.getAllBranches(pageable));
    }

    /**
     * GET /api/v1/branches/{codeAgence} : Retrieves a branch by its code.
     *
     * @param codeAgence The code of the branch to retrieve.
     * @return The branch with the specified code.
     */
    @GetMapping("/{codeAgence}")
    public ResponseEntity<BranchDTO> getBranchById(
            @PathVariable String codeAgence) {
        log.info("REST request to get Branch with code: {}", codeAgence);
        return ResponseEntity.ok(agenceService.getBranchById(codeAgence));
    }

    /**
     * GET /api/v1/branches/statut/{statut} : Retrieves branches by their status.
     *
     * @param statut The status of the branches to retrieve.
     * @return A list of branches with the specified status.
     */
    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<BranchDTO>> getBranchesBystatut(
            @PathVariable String statut) {
        log.info("REST request to get Branches with statut: {}", statut);
        return ResponseEntity.ok(agenceService.getBranchesBystatut(statut));
    }

    /**
     * GET /api/v1/branches/search : Searches for branches by name.
     *
     * @param nomAgence The name or partial name of the branches to search for.
     * @return A list of branches matching the specified name.
     */
    @GetMapping("/search")
    public ResponseEntity<List<BranchDTO>> searchBranchesByName(
            @RequestParam String nomAgence) {
        log.info("REST request to search Branches by name: {}", nomAgence);
        return ResponseEntity.ok(agenceService.searchBranchesByName(nomAgence));
    }

    /**
     * GET /api/v1/branches/zone/{zone} : Retrieves branches by their zone.
     *
     * @param zone The zone of the branches to retrieve.
     * @return A list of branches in the specified zone.
     */
    @GetMapping("/zone/{zone}")
    public ResponseEntity<List<BranchDTO>> getBranchesByZone(
            @PathVariable String zone) {
        log.info("REST request to get Branches with zone: {}", zone);
        return ResponseEntity.ok(agenceService.getBranchesByZone(zone));
    }

    /**
     * GET /api/v1/branches/groupe/{groupe} : Retrieves branches by their group.
     *
     * @param groupe The group of the branches to retrieve.
     * @return A list of branches in the specified group.
     */
    @GetMapping("/groupe/{groupe}")
    public ResponseEntity<List<BranchDTO>> getBranchesByGroupe(
            @PathVariable String groupe) {
        log.info("REST request to get Branches with groupe: {}", groupe);
        return ResponseEntity.ok(agenceService.getBranchesByGroupe(groupe));
    }

    /**
     * GET /api/v1/branches/localite/{localite} : Retrieves branches by their locality.
     *
     * @param localite The locality of the branches to retrieve.
     * @return A list of branches in the specified locality.
     */
    @GetMapping("/localite/{localite}")
    public ResponseEntity<List<BranchDTO>> getBranchesByLocalite(
            @PathVariable String localite) {
        log.info("REST request to get Branches with localite: {}", localite);
        return ResponseEntity.ok(agenceService.getBranchesByLocalite(localite));
    }

    /**
     * GET /api/v1/branches/region/{region} : Retrieves branches by their region.
     *
     * @param region The region of the branches to retrieve.
     * @return A list of branches in the specified region.
     */
    @GetMapping("/region/{region}")
    public ResponseEntity<List<BranchDTO>> getBranchesByRegion(
            @PathVariable String region) {
        log.info("REST request to get Branches with region: {}", region);
        return ResponseEntity.ok(agenceService.getBranchesByRegion(region));
    }

    /**
     * GET /api/v1/branches/statut/{statut}/region/{region} : Retrieves branches by status and region.
     *
     * @param statut The status of the branches to retrieve.
     * @param region The region of the branches to retrieve.
     * @return A list of branches with the specified status and region.
     */
    @GetMapping("/statut/{statut}/region/{region}")
    public ResponseEntity<List<BranchDTO>> getBranchesBystatutAndRegion(
            @PathVariable String statut,
            @PathVariable String region) {
        log.info("REST request to get Branches with statut: {} and region: {}", statut, region);
        return ResponseEntity.ok(agenceService.getBranchesBystatutAndRegion(statut, region));
    }

    /**
     * GET /api/v1/branches/statut/{statut}/groupe/{groupe} : Retrieves branches by status and group.
     *
     * @param statut The status of the branches to retrieve.
     * @param groupe The group of the branches to retrieve.
     * @return A list of branches with the specified status and group.
     */
    @GetMapping("/statut/{statut}/groupe/{groupe}")
    public ResponseEntity<List<BranchDTO>> getBranchesBystatutAndGroupe(
            @PathVariable String statut,
            @PathVariable String groupe) {
        log.info("REST request to get Branches with statut: {} and groupe: {}", statut, groupe);
        return ResponseEntity.ok(agenceService.getBranchesBystatutAndGroupe(statut, groupe));
    }

    /**
     * GET /api/v1/branches/simpleInfo : Retrieves simplified branch information with pagination and sorting.
     *
     * @param page The page number to retrieve (default is 0).
     * @param size The number of items per page (default is 10).
     * @param sort The field to sort by (default is "codburpo").
     * @return A paginated list of simplified branch information.
     */
    @GetMapping("/simpleInfo")
    public ResponseEntity<Page<BranchSimpleDTO>> getSimpleInfoBranches(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "codburpo") String sort) {
        log.info("Getting all branches with simple info: page={}, size={}, sort={}", page, size, sort);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return ResponseEntity.ok(agenceService.getAllBranchesSimple(pageable));
    }
}
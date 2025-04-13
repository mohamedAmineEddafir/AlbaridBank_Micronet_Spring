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


@RestController
@RequestMapping("/api/v1/branches")
@RequiredArgsConstructor
@Slf4j
public class BrancheController {

    private final AgenceService agenceService;

    @GetMapping
    public ResponseEntity<Page<BranchDTO>> getAllBranches(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "codburpo") String sort) {
        log.info("Getting all branches with pagination: page={}, size={}, sort={}", page, size, sort);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return ResponseEntity.ok(agenceService.getAllBranches(pageable));
    }

    @GetMapping("/{codeAgence}")
    public ResponseEntity<BranchDTO> getBranchById(
            @PathVariable String codeAgence) {
        log.info("REST request to get Branch with code: {}", codeAgence);
        return ResponseEntity.ok(agenceService.getBranchById(codeAgence));
    }

    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<BranchDTO>> getBranchesBystatut(
            @PathVariable String statut) {
        log.info("REST request to get Branches with statut: {}", statut);
        return ResponseEntity.ok(agenceService.getBranchesBystatut(statut));
    }

    @GetMapping("/search")
    public ResponseEntity<List<BranchDTO>> searchBranchesByName(
            @RequestParam String nomAgence) {
        log.info("REST request to search Branches by name: {}", nomAgence);
        return ResponseEntity.ok(agenceService.searchBranchesByName(nomAgence));
    }
    @GetMapping("/zone/{zone}")
    public ResponseEntity<List<BranchDTO>> getBranchesByZone(
            @PathVariable String zone) {
        log.info("REST request to get Branches with zone: {}", zone);
        return ResponseEntity.ok(agenceService.getBranchesByZone(zone));
    }
    @GetMapping("/groupe/{groupe}")
    public ResponseEntity<List<BranchDTO>> getBranchesByGroupe(
            @PathVariable String groupe) {
        log.info("REST request to get Branches with groupe: {}", groupe);
        return ResponseEntity.ok(agenceService.getBranchesByGroupe(groupe));
    }
    @GetMapping("/localite/{localite}")
    public ResponseEntity<List<BranchDTO>> getBranchesByLocalite(
            @PathVariable String localite) {
        log.info("REST request to get Branches with localite: {}", localite);
        return ResponseEntity.ok(agenceService.getBranchesByLocalite(localite));
    }
    @GetMapping("/region/{region}")
    public ResponseEntity<List<BranchDTO>> getBranchesByRegion(
            @PathVariable String region) {
        log.info("REST request to get Branches with region: {}", region);
        return ResponseEntity.ok(agenceService.getBranchesByRegion(region));
    }

    /**
     * GET /api/v1/branches/statut/{statut}/region/{region} : Récupère les agences par statut et région
     *
     * @param statut le statut des agences à récupérer
     * @param region la région des agences à récupérer
     * @return liste des agences avec le statut et la région spécifiés
     */
    @GetMapping("/statut/{statut}/region/{region}")
    public ResponseEntity<List<BranchDTO>> getBranchesBystatutAndRegion(
            @PathVariable String statut,
            @PathVariable String region) {
        log.info("REST request to get Branches with statut: {} and region: {}", statut, region);
        return ResponseEntity.ok(agenceService.getBranchesBystatutAndRegion(statut, region));
    }

    /**
     * GET /api/v1/branches/statut/{statut}/groupe/{groupe} : Récupère les agences par statut et groupe
     *
     * @param statut le statut des agences à récupérer
     * @param groupe le groupe des agences à récupérer
     * @return liste des agences avec le statut et le groupe spécifiés
     */
    @GetMapping("/statut/{statut}/groupe/{groupe}")
    public ResponseEntity<List<BranchDTO>> getBranchesBystatutAndGroupe(
            @PathVariable String statut,
            @PathVariable String groupe) {
        log.info("REST request to get Branches with statut: {} and groupe: {}", statut, groupe);
        return ResponseEntity.ok(agenceService.getBranchesBystatutAndGroupe(statut, groupe));
    }

    @GetMapping("/simpleInfo")
    public ResponseEntity<Page<BranchSimpleDTO>> getSimpleInfoBranches(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "codburpo") String sort) {
        log.info("Getting all branches with simple info: page={}, size={}, sort={}", page, size, sort);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return ResponseEntity.ok(agenceService.getAllBranchesSimple(pageable));
    }

/*    @GetMapping("agencestatistique")
    public ResponseEntity<List<BranchDTO>> getAgenceStatistique(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "codburpo") String sort) {
        log.info("Getting all branches with pagination: page={}, size={}, sort={}", page, size, sort);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return ResponseEntity.ok(agenceService.getAgenceStatistique(pageable));
    }**/
}

package com.albaridbank.brancheservice.service.interfaces;

import com.albaridbank.brancheservice.dto.BranchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;

public interface AgenceService {

    Page<BranchDTO> getAllBranches(Pageable pageable);
    BranchDTO getBranchById(String codeAgence);
    List<BranchDTO> getBranchesByStatus(String status);
    List<BranchDTO> getBranchesByName(String nomAgence);
    List<BranchDTO> getBranchesByRegion(String region);

    /**
     * Recherche avancée d'agences avec pagination.
     *
     * @param searchParams Les paramètres de recherche (statut, zone, type, etc.)
     * @param pageable Paramètres de pagination
     * @return Page des résultats de recherche
     */
    Page<BranchDTO> searchBranches(Map<String, String> searchParams, Pageable pageable);

    /**
     * Récupère les statistiques des agences par région.
     *
     * @return Map contenant les statistiques (région -> nombre d'agences)
     */
    Map<String, Long> getBranchStatsByRegion();
}

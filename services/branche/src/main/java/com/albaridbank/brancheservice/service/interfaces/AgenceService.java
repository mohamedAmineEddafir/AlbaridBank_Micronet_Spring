package com.albaridbank.brancheservice.service.interfaces;

import com.albaridbank.brancheservice.dto.BranchDTO;
import com.albaridbank.brancheservice.dto.BranchSimpleDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service interface for managing branch/agency data.
 * Provides methods for querying and retrieving branch information.
 *
 * @author Mohamed Amine Eddafir
 */
public interface AgenceService {

    /**
     * Récupère toutes les agences avec pagination.
     *
     * @param pageable Paramètres de pagination
     * @return Page de BranchDTO
     */
    Page<BranchDTO> getAllBranches(Pageable pageable);

    /**
     * Récupère une agence par son code.
     *
     * @param codeAgence Le code de l'agence
     * @return L'agence correspondante
     */
    BranchDTO getBranchById(String codeAgence);

    /**
     * Récupère une agence par son code avec gestion Optional.
     *
     * @param codeAgence Le code de l'agence
     * @return Optional contenant l'agence si trouvée
     */
    Optional<BranchDTO> findBranchById(String codeAgence);

    /**
     * Récupère les agences par statut.
     *
     * @param status Le statut à rechercher
     * @return Liste des agences correspondantes
     */
    List<BranchDTO> getBranchesByStatus(String status);

    /**
     * Recherche des agences par nom (recherche partielle).
     *
     * @param nomAgence Le nom à rechercher
     * @return Liste des agences correspondantes
     */
    List<BranchDTO> searchBranchesByName(String nomAgence);

    /**
     * Récupère les agences par zone.
     *
     * @param zone La zone à rechercher
     * @return Liste des agences correspondantes
     */
    List<BranchDTO> getBranchesByZone(String zone);

    /**
     * Récupère les agences par groupe.
     *
     * @param groupe Le groupe à rechercher
     * @return Liste des agences correspondantes
     */
    List<BranchDTO> getBranchesByGroupe(String groupe);

    /**
     * Récupère les agences par localité.
     *
     * @param localite La localité à rechercher
     * @return Liste des agences correspondantes
     */
    List<BranchDTO> getBranchesByLocalite(String localite);

    /**
     * Récupère les agences par région.
     *
     * @param region La région à rechercher
     * @return Liste des agences correspondantes
     */
    List<BranchDTO> getBranchesByRegion(String region);

    /*
     * Recherche avancée d'agences avec pagination et critères multiples.
     * Utilise des specifications pour construire des requêtes dynamiques.
     *
     * @param searchParams Les paramètres de recherche (nom, statut, zone, région, etc.)
     * @param pageable Paramètres de pagination
     * @return Page des résultats de recherche
     * Page<BranchDTO> search _ Branches (Map<String, String> searchParams, Pageable pageable);
     **/

    /**
     * Récupère une version simplifiée des agences pour les interfaces légères.
     * Contient uniquement les informations essentielles (code, nom, région, statut).
     *
     * @return Liste des agences en format simplifié
     */
    List<BranchSimpleDTO> getAllBranchesSimple();

    /**
     * Récupère des statistiques sur les agences.
     *
     * @return Map contenant diverses statistiques
     */
    Map<String, Object> getAgenceStatistics();
}
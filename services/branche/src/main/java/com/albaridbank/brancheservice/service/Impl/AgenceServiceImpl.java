package com.albaridbank.brancheservice.service.Impl;

import com.albaridbank.brancheservice.dto.BranchDTO;
import com.albaridbank.brancheservice.dto.BranchSimpleDTO;
import com.albaridbank.brancheservice.mappers.SRagenceToBranchMapper;
import com.albaridbank.brancheservice.repositorys.AgenceRepository;
import com.albaridbank.brancheservice.service.interfaces.AgenceService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Implémentation du service de gestion des agences bancaires.
 * Ce service fournit des méthodes pour interroger et récupérer des informations sur les agences.
 *
 * @author Mohamed Amine Eddafir
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AgenceServiceImpl implements AgenceService {

    private final AgenceRepository agenceRepository;
    private final SRagenceToBranchMapper branchMapper;
    private final AgenceService agenceService;

    /**
     * Récupère toutes les agences avec pagination.
     *
     * @param pageable les informations de pagination
     * @return une page d'objets BranchDTO
     * @throws RuntimeException si une erreur survient lors de la récupération
     */
    @Override
    public Page<BranchDTO> getAllBranches(Pageable pageable) {
        log.info("Récupération de toutes les agences avec pagination: {}", pageable);

        Objects.requireNonNull(pageable, "Le paramètre Pageable ne peut pas être null");
        try {
            return agenceRepository.findAll(pageable).map(branchMapper::toDto);
        } catch (Exception ex) {
            log.error("Erreur lors de la récupération des agences: {}", ex.getMessage(), ex);
            throw new RuntimeException("Échec de la récupération des agences", ex);
        }
    }

    /**
     * Récupère une agence par son code.
     *
     * @param codeAgence Le code de l'agence
     * @return L'agence correspondante
     * @throws EntityNotFoundException si l'agence n'est pas trouvée
     * @throws RuntimeException        si une autre erreur survient
     */
    @Override
    public BranchDTO getBranchById(String codeAgence) {
        log.info("Récupération de l'agence par code: {}", codeAgence);

        Objects.requireNonNull(codeAgence, "Le code d'agence ne peut pas être null");

        try {
            return agenceRepository.findByCodburpo(codeAgence)
                    .map(branchMapper::toDto)
                    .orElseThrow(() -> new EntityNotFoundException("Agence non trouvée avec le code: " + codeAgence));
        } catch (EntityNotFoundException ex) {
            log.warn("Agence non trouvée avec le code: {}", codeAgence);
            throw ex;
        } catch (Exception ex) {
            log.error("Erreur lors de la récupération de l'agence par code {}: {}", codeAgence, ex.getMessage(), ex);
            throw new RuntimeException("Échec de la récupération de l'agence", ex);
        }
    }

    /**
     * Récupère les agences par statut.
     *
     * @param status Le statut à rechercher
     * @return Liste des agences correspondantes
     */
    @Override
    public List<BranchDTO> getBranchesByStatus(String status) {
        return List.of();
    }

    /**
     * Récupère les agences par statut.
     *
     * @param statut Le statut à rechercher
     * @return Liste des agences correspondantes
     * @throws RuntimeException si une erreur survient
     */
    @Override
    public List<BranchDTO> getBranchesBystatut(String statut) {
        log.info("Récupération des agences par statut: {}", statut);
        Objects.requireNonNull(statut, "Le statut ne peut pas être null");
        try {
            List<BranchDTO> branches = agenceRepository.findByStatut(statut)
                    .stream()
                    .map(branchMapper::toDto)
                    .toList();

            log.info("Nombre d'agences trouvées avec le statut {}: {}", statut, branches.size());
            return branches;
        } catch (Exception ex) {
            log.error("Erreur lors de la récupération des agences par statut {}: {}", statut, ex.getMessage(), ex);
            throw new RuntimeException("Échec de la récupération des agences par statut", ex);
        }
    }

    /**
     * Récupère les agences par statut et région.
     *
     * @param statut Le statut à rechercher
     * @param region La région à rechercher
     * @return Liste des agences correspondantes basée sur le statut et la région
     */
    @Override
    public List<BranchDTO> getBranchesBystatutAndRegion(String statut, String region) {
        return List.of();
    }

    /**
     * Récupère les agences par statut et groupe.
     *
     * @param statut Le statut à rechercher
     * @param groupe Le groupe à rechercher
     * @return Liste des agences correspondantes
     */
    @Override
    public List<BranchDTO> getBranchesBystatutAndGroupe(String statut, String groupe) {
        log.info("Récupération des agences par statut: {} et groupe: {}", statut, groupe);
        Objects.requireNonNull(statut, "Le statut ne peut pas être null");
        Objects.requireNonNull(groupe, "Le groupe ne peut pas être null");
        try {
            List<BranchDTO> branches = agenceRepository.findByStatutAndGroupe(statut, groupe)
                    .stream()
                    .map(branchMapper::toDto)
                    .toList();

            log.info("Nombre d'agences trouvées avec le statut {} et le groupe {}: {}", statut, groupe, branches.size());
            return branches;
        } catch (Exception ex) {
            log.error("Erreur lors de la récupération des agences par statut {} et groupe {}: {}", statut, groupe, ex.getMessage(), ex);
            throw new RuntimeException("Échec de la récupération des agences par statut et groupe", ex);
        }
    }

    /**
     * Recherche des agences par nom (recherche partielle).
     *
     * @param nomAgence Le nom à rechercher
     * @return Liste des agences correspondantes
     * @throws RuntimeException si une erreur survient
     */
    @Override
    public List<BranchDTO> searchBranchesByName(String nomAgence) {
        log.info("Recherche des agences par nom: {}", nomAgence);
        Objects.requireNonNull(nomAgence, "Le nom d'agence ne peut pas être null");
        try {
            // Pour la recherche partielle, vous pourriez avoir besoin d'une méthode dédiée dans le repository
            // Si vous n'avez pas de méthode findByLibelleBurpo Containing, utilisez findByLibelleBurpo.
            List<BranchDTO> branches = agenceRepository.findByLibelleBurpo(nomAgence)
                    .stream()
                    .map(branchMapper::toDto)
                    .filter(branch -> branch.getNomAgence().contains(nomAgence))
                    .toList();

            log.info("Nombre d'agences trouvées avec le nom contenant {}: {}", nomAgence, branches.size());
            return branches;
        } catch (Exception ex) {
            log.error("Erreur lors de la recherche des agences par nom {}: {}", nomAgence, ex.getMessage(), ex);
            throw new RuntimeException("Échec de la recherche des agences par nom", ex);
        }
    }

    /**
     * Récupère les agences par zone.
     *
     * @param zone La zone à rechercher
     * @return Liste des agences correspondantes
     * @throws RuntimeException si une erreur survient
     */
    @Override
    public List<BranchDTO> getBranchesByZone(String zone) {
        log.info("Récupération des agences par zone: {}", zone);
        Objects.requireNonNull(zone, "La zone ne peut pas être null");
        try {
            List<BranchDTO> branches = agenceRepository.findByLibelleZone(zone)
                    .stream()
                    .map(branchMapper::toDto)
                    .toList();

            log.info("Nombre d'agences trouvées dans la zone {}: {}", zone, branches.size());
            return branches;
        } catch (Exception ex) {
            log.error("Erreur lors de la récupération des agences par zone {}: {}", zone, ex.getMessage(), ex);
            throw new RuntimeException("Échec de la récupération des agences par zone", ex);
        }
    }

    /**
     * Récupère les agences par groupe.
     *
     * @param groupe Le groupe à rechercher
     * @return Liste des agences correspondantes
     * @throws RuntimeException si une erreur survient
     */
    @Override
    public List<BranchDTO> getBranchesByGroupe(String groupe) {
        log.info("Récupération des agences par groupe: {}", groupe);
        Objects.requireNonNull(groupe, "Le groupe ne peut pas être null");
        try {
            List<BranchDTO> branches = agenceRepository.findByLibelleGroupe(groupe)
                    .stream()
                    .map(branchMapper::toDto)
                    .toList();

            log.info("Nombre d'agences trouvées dans le groupe {}: {}", groupe, branches.size());
            return branches;
        } catch (Exception ex) {
            log.error("Erreur lors de la récupération des agences par groupe {}: {}", groupe, ex.getMessage(), ex);
            throw new RuntimeException("Échec de la récupération des agences par groupe", ex);
        }
    }

    /**
     * Récupère les agences par localité.
     *
     * @param localite La localité à rechercher
     * @return Liste des agences correspondantes
     * @throws RuntimeException si une erreur survient
     */
    @Override
    public List<BranchDTO> getBranchesByLocalite(String localite) {
        log.info("Récupération des agences par localité: {}", localite);
        Objects.requireNonNull(localite, "La localité ne peut pas être null");
        try {
            List<BranchDTO> branches = agenceRepository.findByLibelleLocalite(localite)
                    .stream()
                    .map(branchMapper::toDto)
                    .toList();

            log.info("Nombre d'agences trouvées dans la localité {}: {}", localite, branches.size());
            return branches;
        } catch (Exception ex) {
            log.error("Erreur lors de la récupération des agences par localité {}: {}", localite, ex.getMessage(), ex);
            throw new RuntimeException("Échec de la récupération des agences par localité", ex);
        }
    }

    /**
     * Récupère les agences par région.
     *
     * @param region La région à rechercher
     * @return Liste des agences correspondantes
     * @throws RuntimeException si une erreur survient
     */
    @Override
    public List<BranchDTO> getBranchesByRegion(String region) {
        log.info("Récupération des agences par région: {}", region);
        Objects.requireNonNull(region, "La région ne peut pas être null");
        try {
            List<BranchDTO> branches = agenceRepository.findByLibelleRegion(region)
                    .stream()
                    .map(branchMapper::toDto)
                    .toList();

            log.info("Nombre d'agences trouvées dans la région {}: {}", region, branches.size());
            return branches;
        } catch (Exception ex) {
            log.error("Erreur lors de la récupération des agences par région {}: {}", region, ex.getMessage(), ex);
            throw new RuntimeException("Échec de la récupération des agences par région", ex);
        }
    }

    /**
     * Récupère une version simplifiée des agences pour les interfaces légères.
     * Contient uniquement les informations essentielles (code, nom, région, statut).
     *
     * @return Liste des agences en format simplifié
     * @throws RuntimeException si une erreur survient
     */
    @Override
    @Cacheable("branchesSimple")
    public Page<BranchSimpleDTO> getAllBranchesSimple(Pageable pageable) {
        log.info("Récupération de toutes les agences en format simplifié");
        Objects.requireNonNull(pageable, "Le paramètre Pageable ne peut pas être null");

        try {
            // Utilisation de la méthode findAll pour récupérer toutes les agences
            return agenceRepository.findAll(pageable).map(branchMapper::toSimpleDto);
        } catch (Exception ex) {
            log.error("Erreur lors de la récupération des agences en format simplifié: {}", ex.getMessage(), ex);
            throw new RuntimeException("Échec de la récupération des agences en format simplifié", ex);
        }
    }

    /**
     * Récupère des statistiques sur les agences.
     *
     * @return Map contenant diverses statistiques
     * @throws RuntimeException si une erreur survient
     */
    @Override
    @Cacheable("agenceStats")
    public Map<String, Object> getAgenceStatistics() {
        log.info("Récupération des statistiques des agences");
        try {
            long totalAgences = agenceRepository.count();
            long activeAgences = agenceRepository.countByStatut("O");
            long inactiveAgences = agenceRepository.countByStatut("F");

            Map<String, Object> stats = Map.of(
                    "totalAgences", totalAgences,
                    "activeAgences", activeAgences,
                    "inactiveAgences", inactiveAgences,
                    "pourcentageActive", totalAgences > 0 ? (activeAgences * 100.0 / totalAgences) : 0
            );

            log.info("Statistiques des agences: {}", stats);
            return stats;
        } catch (Exception ex) {
            log.error("Erreur lors de la récupération des statistiques des agences: {}", ex.getMessage(), ex);
            throw new RuntimeException("Échec de la récupération des statistiques des agences", ex);
        }
    }
}
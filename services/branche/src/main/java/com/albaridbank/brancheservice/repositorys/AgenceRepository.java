package com.albaridbank.brancheservice.repositorys;

import com.albaridbank.brancheservice.model.SgcRefAgence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link SgcRefAgence} entities.
 * Extends {@link JpaRepository} to provide CRUD operations and custom query methods.
 */
public interface AgenceRepository extends JpaRepository<SgcRefAgence, String>, JpaSpecificationExecutor<SgcRefAgence> {

    /**
     * Finds an {@link SgcRefAgence} entity by its codburpo '{@summary Code unique de l'agence}' (agency code).
     *
     * @param codburpo The agency code to search for.
     * @return The matching {@link SgcRefAgence} entity, or null if not found.
     */
    Optional<SgcRefAgence> findByCodburpo(String codburpo);

    /**
     * Finds an {@link SgcRefAgence} entity by its libelleBurpo '{@summary  Nom ou intitule de l'agence}' (agency name).
     *
     * @param libelleBurpo The agency name to search for.
     * @return The matching {@link SgcRefAgence} entity, or null if not found.
     */
    List<SgcRefAgence> findByLibelleBurpo(String libelleBurpo);

    /**
     * Finds an {@link SgcRefAgence} entity by its libelleGroupe '{@summary Groupe auquel elle appartient}' (group name).
     *
     * @param libelleGroupe The group name to search for.
     * @return The matching {@link SgcRefAgence} entity, or null if not found.
     */
    List<SgcRefAgence> findByLibelleGroupe(String libelleGroupe);

    /**
     * Finds an {@link SgcRefAgence} entity by its libelleLocalite '{@summary Ville ou localité}' (locality name).
     *
     * @param libelleLocalite The locality name to search for.
     * @return The matching {@link SgcRefAgence} entity, or null if not found.
     */
    List<SgcRefAgence> findByLibelleLocalite(String libelleLocalite);

    /**
     * Finds an {@link SgcRefAgence} entity by its libelleRegion '{@summary Region}' (region name).
     *
     * @param region The region name to search for.
     * @return The matching {@link SgcRefAgence} entity, or null if not found.
     */
    //List<SgcRefAgence> findByLibelleRegion(String libelleRegion);
    @Query("SELECT b FROM SgcRefAgence b WHERE LOWER(TRIM(b.libelleRegion)) LIKE LOWER(CONCAT('%', TRIM(:region), '%'))")
    List<SgcRefAgence> findByLibelleRegion(@Param("region") String region);

    /**
     * Finds an {@link SgcRefAgence} entity by its libelleBurpo '{@summary  Nom ou intitule de l'agence}' (agency name).
     * This method performs a case-insensitive search.
     *
     * @param nomAgence The agency name to search for.
     * @return A list of matching {@link SgcRefAgence} entities.
     */
    @Query("SELECT b FROM SgcRefAgence b WHERE LOWER(TRIM(b.libelleBurpo)) LIKE LOWER(CONCAT('%', TRIM(:nomAgence), '%'))")
    List<SgcRefAgence> findByNomAgenceContainingIgnoreCase(@Param("nomAgence") String nomAgence);

    /**
     * Finds agencies by their status and region.
     *
     * @param statut The status to search for (e.g., 'O' for Ouvrir)
     * @param libelleRegion The region to search for
     * @return List of matching agencies
     */
    List<SgcRefAgence> findByStatutAndLibelleRegion(String statut, String libelleRegion);

    /**
     * Counts the number of agencies by status.
     *
     * @param statut The status ('O' for Ouvrir , 'F' for Fermer , etc.)
     * @return The count of agencies with the specified status
     */
    long countByStatut(String statut);
}
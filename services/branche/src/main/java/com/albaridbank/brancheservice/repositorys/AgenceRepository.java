package com.albaridbank.brancheservice.repositorys;

import com.albaridbank.brancheservice.model.SgcRefAgence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

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
    SgcRefAgence findByCodburpo(String codburpo);

    /**
     * Finds an {@link SgcRefAgence} entity by its libelleBurpo '{@summary  Nom ou intitule de l'agence}' (agency name).
     *
     * @param libelleBurpo The agency name to search for.
     * @return The matching {@link SgcRefAgence} entity, or null if not found.
     */
    SgcRefAgence findByLibelleBurpo(String libelleBurpo);

    /**
     * Finds an {@link SgcRefAgence} entity by its libelleZone '{@summary Zone géographique}' (zone name).
     *
     * @param libelleZone The zone name to search for.
     * @return The matching {@link SgcRefAgence} entity, or null if not found.
     */
    SgcRefAgence findByLibelleZone(String libelleZone);

    /**
     * Finds an {@link SgcRefAgence} entity by its libelleGroupe '{@summary Groupe auquel elle appartient}' (group name).
     *
     * @param libelleGroupe The group name to search for.
     * @return The matching {@link SgcRefAgence} entity, or null if not found.
     */
    SgcRefAgence findByLibelleGroupe(String libelleGroupe);

    /**
     * Finds an {@link SgcRefAgence} entity by its libelleLocalite '{@summary Ville ou localité}' (locality name).
     *
     * @param libelleLocalite The locality name to search for.
     * @return The matching {@link SgcRefAgence} entity, or null if not found.
     */
    SgcRefAgence findByLibelleLocalite(String libelleLocalite);
}
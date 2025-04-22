package com.albaridbank.edition.repositorys.ccp;

import com.albaridbank.edition.model.ccp.BureauPosteCCP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing {@link BureauPosteCCP} entities.
 * Extends {@link JpaRepository} to provide CRUD operations and custom query methods.
 *
 * @author Mohamed Amine Eddafir
 */
@Repository
public interface BureauPosteCCPRepository extends JpaRepository<BureauPosteCCP, Long> {

    /**
     * Finds a BureauPosteCCP entity by its code.
     *
     * @param codeBureau The code of the bureau to search for.
     * @return An {@link Optional} containing the BureauPosteCCP if found, or empty if not found.
     */
    Optional<BureauPosteCCP> findByCodeBureau(Long codeBureau);

    /**
     * Finds a BureauPosteCCP entity by its designation (name).
     *
     * @param designation The name of the bureau to search for.
     * @return An {@link Optional} containing the BureauPosteCCP if found, or empty if not found.
     */
    Optional<BureauPosteCCP> findByDesignation(String designation);
}
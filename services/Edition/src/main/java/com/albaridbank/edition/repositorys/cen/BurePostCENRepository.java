package com.albaridbank.edition.repositorys.cen;

import com.albaridbank.edition.model.cen.BurePostCEN;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing {@link BurePostCEN} entities.
 * Extends {@link JpaRepository} to provide CRUD operations and custom query methods.
 *
 * @author Mohamed Amine Eddafir
 */
@Repository
public interface BurePostCENRepository extends JpaRepository<BurePostCEN, Long> {

    /**
     * Finds a post office by its code.
     *
     * @param codeBureau The code of the post office to search for.
     * @return An {@link Optional} containing the post office if found, or empty if not found.
     */
    Optional<BurePostCEN> findByCodeBureau(Long codeBureau);

    /**
     * Finds a post office by its designation (name).
     *
     * @param designation The name of the post office to search for.
     * @return An {@link Optional} containing the post office if found, or empty if not found.
     */
    Optional<BurePostCEN> findByDesignation(String designation);

    /**
     * Trouve le nom du bureau à partir de son code
     *
     * @param codburpo Code du bureau
     * @return Nom du bureau
     */
    @Query("SELECT b.designation FROM BurePostCEN b WHERE b.codeBureau = :codburpo")
    String findDesburpoByCodburpo(@Param("codburpo") Long codburpo);


}
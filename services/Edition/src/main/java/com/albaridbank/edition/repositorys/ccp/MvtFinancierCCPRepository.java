package com.albaridbank.edition.repositorys.ccp;

import com.albaridbank.edition.model.ccp.MvtFinancierCCP;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Repository interface for managing {@link MvtFinancierCCP} entities.
 * Provides CRUD operations and custom query methods for financial movements.
 * <p>
 * This interface extends JpaRepository and JpaSpecificationExecutor
 * to provide basic CRUD operations and support for JPA criteria queries.
 * </p>
 *
 * @author Mohamed Amine Eddafir
 */
@Repository
public interface MvtFinancierCCPRepository extends JpaRepository<MvtFinancierCCP, Integer>, JpaSpecificationExecutor<MvtFinancierCCP> {

    /**
     * Finds all financial movements for a specific date and bureau code with a minimum amount.
     *
     * @param dateMouvement  The date of the movements.
     * @param codeBureau     The code of the post office.
     * @param montantMinimum The minimum amount of the movements
     * @param pageable       Pagination information
     * @return A page of financial movements meeting the criteria.
     */
    @EntityGraph(attributePaths = {"bureauPoste", "compte", "typeOperation"})
    @Query("SELECT m FROM MvtFinancierCCP m " +
            "WHERE m.dateMouvement = :dateMouvement AND m.codeBureau = :codeBureau AND ABS(m.montant) >= :montantMinimum " +
            "ORDER BY m.dateCreation DESC, m.montant DESC")
    Page<MvtFinancierCCP> findPageByDateMouvementAndCodeBureauAndMontantMin(
            @Param("dateMouvement") LocalDate dateMouvement,
            @Param("codeBureau") BigDecimal codeBureau,
            @Param("montantMinimum") BigDecimal montantMinimum,
            Pageable pageable);

    /**
     * Interface de projection pour les statistiques des mouvements
     */
    interface MouvementStats {
        Integer getNombreComptes();

        BigDecimal getMontantTotal();
    }

    /**
     * Counts distinct accounts and sums the movement amounts for a given date and bureau.
     *
     * @param date       The date of the movements
     * @param codeBureau The bureau code
     * @return MouvementStats containing count of distinct accounts and sum of movement amounts
     */
    @Query("SELECT COUNT(DISTINCT m.compte.idCompte) as nombreComptes, SUM(m.montant) as montantTotal " +
            "FROM MvtFinancierCCP m " +
            "WHERE m.dateMouvement = :date AND m.codeBureau = :codeBureau AND ABS(m.montant) >= :montantMinimum")
    MouvementStats getStatistiques(
            @Param("date") LocalDate date,
            @Param("codeBureau") BigDecimal codeBureau,
            @Param("montantMinimum") BigDecimal montantMinimum);
}
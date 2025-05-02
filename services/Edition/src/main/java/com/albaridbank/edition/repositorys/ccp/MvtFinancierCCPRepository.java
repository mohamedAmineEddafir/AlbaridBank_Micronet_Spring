package com.albaridbank.edition.repositorys.ccp;

import com.albaridbank.edition.model.ccp.BureauPosteCCP;
import com.albaridbank.edition.model.ccp.MvtFinancierCCP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for managing {@link MvtFinancierCCP} entities.
 * Provides CRUD operations and custom query methods for financial movements.
 */
@Repository
public interface MvtFinancierCCPRepository extends JpaRepository<MvtFinancierCCP, Long> {

    /**
     * Finds financial movements for a specific date and post office.
     *
     * @param dateMouvement   The date of the movements.
     * @param bureauPoste The code of the post office.
     * @return A list of financial movements for the specified date and post office.
     */
    List<MvtFinancierCCP> findByDateMouvementAndBureauPoste(LocalDate dateMouvement, BureauPosteCCP bureauPoste);

    /**
     * Finds financial movements for a specific date and post office with a minimum amount.
     *
     * @param dateMouvement   The date of the movements.
     * @param bureauPoste The code of the post office.
     * @param montantMinimum  The minimum amount of the movements
     * @return A list of financial movements meeting the criteria.
     */
    @Query("SELECT m FROM MvtFinancierCCP m WHERE m.dateMouvement = :dateMouvement AND m.bureauPoste = :codeBureauPoste AND ABS(m.montant) >= :montantMinimum")
    List<MvtFinancierCCP> findByDateMouvementAndCodeBureauPosteAndMontantMin(
            @Param("dateMouvement") LocalDate dateMouvement,
            @Param("bureauPoste") BureauPosteCCP bureauPoste,
            @Param("montantMinimum") BigDecimal montantMinimum);

    /**
     * Calculates the total number of distinct accounts and the total amount for a specific date and post office.
     *
     * @param dateMouvement   The date of the movements.
     * @param bureauPoste The code of the post office.
     * @return An Object array where [0] = number of distinct accounts and [1] = sum of amounts.
     */
    @Query("SELECT COUNT(DISTINCT m.compte.idCompte), SUM(ABS(m.montant)) FROM MvtFinancierCCP m WHERE m.dateMouvement = :dateMouvement AND m.bureauPoste = :bureauPoste")
    Object[] countDistinctAccountsAndSumMontant(@Param("dateMouvement") LocalDate dateMouvement, @Param("bureauPoste") BureauPosteCCP bureauPoste);
}
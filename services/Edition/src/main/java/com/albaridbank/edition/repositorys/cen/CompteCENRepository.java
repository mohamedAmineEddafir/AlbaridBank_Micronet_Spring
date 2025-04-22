package com.albaridbank.edition.repositorys.cen;

import com.albaridbank.edition.model.cen.CompteCEN;
import com.albaridbank.edition.model.cen.CompteCENId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing {@link CompteCEN} entities.
 * Extends {@link JpaRepository} to provide CRUD operations and custom query methods.
 *
 * @author Mohamed Amine Eddafir
 */
@Repository
public interface CompteCENRepository extends JpaRepository<CompteCEN, CompteCENId> {

    /**
     * Finds all active CEN accounts for a specific post office.
     *
     * @param codeBureau       The code of the post office.
     * @param codeTypeActivite The activity type code to exclude (inactive/closed accounts).
     * @return A list of active CEN accounts.
     */
    List<CompteCEN> findByBureauPoste_CodeBureauAndCodeTypeActiviteNot(Long codeBureau, Integer codeTypeActivite);

    /**
     * Finds all active CEN accounts for a specific post office with pagination.
     *
     * @param codeBureau       The code of the post office.
     * @param codeTypeActivite The activity type code to exclude (inactive/closed accounts).
     * @param pageable         The pagination information.
     * @return A paginated list of active CEN accounts.
     */
    Page<CompteCEN> findByBureauPoste_CodeBureauAndCodeTypeActiviteNot(Long codeBureau, Integer codeTypeActivite, Pageable pageable);

    /**
     * Calculates the number of accounts and the sum of balances for a specific post office.
     *
     * @param codeBureau       The code of the post office.
     * @param codeTypeActivite The activity type code to exclude.
     * @return An Object array where [0] = number of accounts and [1] = sum of balances.
     */
    @Query("SELECT COUNT(c), SUM(c.soldeCourant) FROM CompteCEN c WHERE c.bureauPoste.codeBureau = :codeBureau AND c.codeTypeActivite <> :codeTypeActivite")
    Object[] countAndSumSoldeByBureauPosteAndActiveAccounts(@Param("codeBureau") Long codeBureau, @Param("codeTypeActivite") Integer codeTypeActivite);
}
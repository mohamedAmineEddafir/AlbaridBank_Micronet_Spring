package com.albaridbank.edition.repositorys.cen;

import com.albaridbank.edition.model.cen.OperCompCEN;
import com.albaridbank.edition.model.cen.OperCompCENId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for managing {@link OperCompCEN} entities.
 * Extends {@link JpaRepository} to provide CRUD operations and custom query methods.
 */
@Repository
public interface OperCompCENRepository extends JpaRepository<OperCompCEN, OperCompCENId> {

    /**
     * Finds operations for a specific date and post office.
     *
     * @param dateOperation The date of the operations.
     * @param codeBureau    The code of the post office.
     * @return A list of operations for the specified date and post office.
     */
    List<OperCompCEN> findByDateOperationAndCodeBureau(LocalDate dateOperation, Long codeBureau);

    /**
     * Counts the number of distinct accounts with operations and sums the amounts for a specific date and post office.
     *
     * @param dateOperation The date of the operations.
     * @param codeBureau    The code of the post office.
     * @return An Object array where [0] = number of distinct accounts and [1] = sum of operation amounts.
     */
    @Query("SELECT COUNT(DISTINCT CASE WHEN o.compteDebit IS NOT NULL THEN o.compteDebit ELSE o.compteCredit END), SUM(o.montantOperation) " +
            "FROM OperCompCEN o WHERE o.dateOperation = :dateOperation AND o.codeBureau = :codeBureau")
    Object[] countDistinctAccountsAndSumMontant(@Param("dateOperation") LocalDate dateOperation, @Param("codeBureau") Long codeBureau);

    /**
     * Finds operations for a specific date, post office, and list of accounts.
     *
     * @param dateOperation The date of the operations.
     * @param codeBureau    The code of the post office.
     * @param comptes       The list of account numbers.
     * @return A list of operations matching the criteria.
     */
    @Query("SELECT o FROM OperCompCEN o WHERE o.dateOperation = :dateOperation AND o.codeBureau = :codeBureau AND " +
            "(o.compteDebit IN :comptes OR o.compteCredit IN :comptes)")
    List<OperCompCEN> findByDateOperationAndCodeBureauAndCompteIn(
            @Param("dateOperation") LocalDate dateOperation,
            @Param("codeBureau") Long codeBureau,
            @Param("comptes") List<Long> comptes);
}
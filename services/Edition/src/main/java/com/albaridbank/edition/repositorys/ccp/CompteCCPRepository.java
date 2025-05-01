package com.albaridbank.edition.repositorys.ccp;

import com.albaridbank.edition.model.ccp.CompteCCP;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing {@link CompteCCP} entities.
 * Provides methods for querying CCP accounts with various filters and conditions.
 *
 * @author Mohamed Amine Eddafir
 */
@Repository
public interface CompteCCPRepository extends JpaRepository<CompteCCP, Long>, JpaSpecificationExecutor<CompteCCP> {

    /**
     * Finds all CCP accounts associated with a specific bureau code.
     *
     * @param codeBureauPoste The code of the bureau.
     * @return A list of CCP accounts for the specified bureau.
     */
    List<CompteCCP> findByBureauPoste_CodeBureau(Long codeBureauPoste);

    /**
     * Finds all CCP accounts associated with a specific bureau code with pagination.
     *
     * @param codeBureauPoste The code of the bureau.
     * @param pageable The pagination information.
     * @return A page of CCP accounts for the specified bureau.
     */
    Page<CompteCCP> findByBureauPoste_CodeBureau(Long codeBureauPoste, Pageable pageable);

    /**
     * Finds CCP accounts for a specific bureau code, excluding certain statuses and filtering by product codes.
     *
     * @param codeBureauPoste The code of the bureau.
     * @param codeEtatCompte  A list of account statuses to exclude.
     * @param codesProduit    A list of product codes to include.
     * @return A list of CCP accounts matching the criteria.
     */
    List<CompteCCP> findByBureauPoste_CodeBureauAndCodeEtatCompteNotInAndCodeProduitIn(Long codeBureauPoste, List<String> codeEtatCompte, List<Integer> codesProduit);

    /**
     * Counts the number of active CCP accounts and calculates their total balance for a specific bureau.
     *
     * @param codeBureauPoste The code of the bureau.
     * @param codeEtatCompte  A list of account statuses to exclude.
     * @return An array containing the count of accounts and the sum of their balances.
     */
    @Query("SELECT COUNT(c), SUM(c.soldeCourant) FROM CompteCCP c WHERE c.bureauPoste.codeBureau = :codeBureauPoste AND c.codeEtatCompte NOT IN :codeEtatCompte")
    Object[] countAndSumSoldeByBureauPosteAndActiveAccounts(@Param("codeBureauPoste") Long codeBureauPoste, @Param("codeEtatCompte") List<String> codeEtatCompte);

    /**
     * Finds the top CCP accounts by balance for a specific bureau, excluding certain statuses.
     *
     * @param codeBureauPoste The code of the bureau.
     * @param codeEtatCompte  A list of account statuses to exclude.
     * @param pageable        The pagination information to limit the number of results.
     * @return A list of CCP accounts ordered by balance in descending order.
     */
    @Query(value = "SELECT c FROM CompteCCP c WHERE c.bureauPoste.codeBureau = :codeBureauPoste AND c.codeEtatCompte NOT IN :codeEtatCompte ORDER BY c.soldeCourant DESC")
    List<CompteCCP> findTopNAccountsBySolde(@Param("codeBureauPoste") Long codeBureauPoste, @Param("codeEtatCompte") List<String> codeEtatCompte, Pageable pageable);
}
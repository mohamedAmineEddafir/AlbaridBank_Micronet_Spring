package com.albaridbank.edition.repositorys.ccp;

import com.albaridbank.edition.model.ccp.CompteCCP;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing {@link CompteCCP} entities.
 * Provides CRUD operations and custom query methods for CCP accounts.
 * <p>
 * Author: Mohamed Amine Eddafir
 */
@Repository
public interface CompteCCPRepository extends JpaRepository<CompteCCP, Long> {

    /**
     * Finds all accounts belonging to a specific post office.
     *
     * @param codeBureauPoste The code of the post office.
     * @return A list of accounts associated with the specified post office.
     */
    List<CompteCCP> findByBureauPoste_CodeBureau(Long codeBureauPoste);

    /**
     * Finds all active accounts belonging to a specific post office.
     *
     * @param codeBureauPoste The code of the post office.
     * @param codeEtatCompte  The list of account status codes to exclude (inactive accounts).
     * @return A list of active accounts associated with the specified post office.
     */
    List<CompteCCP> findByBureauPoste_CodeBureauAndCodeEtatCompteNotIn(Long codeBureauPoste, List<String> codeEtatCompte);

    /**
     * Finds all active accounts belonging to a specific post office with pagination.
     *
     * @param codeBureauPoste The code of the post office.
     * @param codeEtatCompte  The list of account status codes to exclude (inactive accounts).
     * @param pageable        The pagination information.
     * @return A paginated list of active accounts.
     */
    Page<CompteCCP> findByBureauPoste_CodeBureauAndCodeEtatCompteNotIn(Long codeBureauPoste, List<String> codeEtatCompte, Pageable pageable);

    /**
     * Finds active accounts of a specific product type belonging to a specific post office.
     *
     * @param codeBureauPoste The code of the post office.
     * @param codeEtatCompte  The list of account status codes to exclude.
     * @param codesProduit    The list of product types to include.
     * @return A list of matching accounts.
     */
    List<CompteCCP> findByBureauPoste_CodeBureauAndCodeEtatCompteNotInAndCodeProduitIn(Long codeBureauPoste, List<String> codeEtatCompte, List<Integer> codesProduit);

    /**
     * Finds the number of accounts and the sum of balances for a specific post office.
     *
     * @param codeBureauPoste The code of the post office.
     * @param codeEtatCompte  The list of account status codes to exclude.
     * @return An Object array where [0] = number of accounts and [1] = sum of balances.
     */
    @Query("SELECT COUNT(c), SUM(c.soldeCourant) FROM CompteCCP c WHERE c.bureauPoste.codeBureau = :codeBureauPoste AND c.codeEtatCompte NOT IN :codeEtatCompte")
    Object[] countAndSumSoldeByBureauPosteAndActiveAccounts(@Param("codeBureauPoste") Long codeBureauPoste, @Param("codeEtatCompte") List<String> codeEtatCompte);

    /**
     * Finds the top N accounts by current balance for a specific post office.
     *
     * @param codeBureauPoste The code of the post office.
     * @param codeEtatCompte  The list of account status codes to exclude.
     * @param pageable        The maximum number of accounts to return.
     * @return A list of accounts sorted by descending balance.
     */
    @Query(value = "SELECT c FROM CompteCCP c WHERE c.bureauPoste.codeBureau = :codeBureauPoste AND c.codeEtatCompte NOT IN :codeEtatCompte ORDER BY c.soldeCourant DESC")
    List<CompteCCP> findTopNAccountsBySolde(@Param("codeBureauPoste") Long codeBureauPoste, @Param("codeEtatCompte") List<String> codeEtatCompte, Pageable pageable);
}
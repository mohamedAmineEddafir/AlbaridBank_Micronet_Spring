package com.albaridbank.edition.repositorys.ccp;

import com.albaridbank.edition.model.ccp.CompteCCP;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
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
     * Interface de projection pour les statistiques des comptes
     */
    interface PortefeuilleStats {
        Long getNombreTotalComptes();
        BigDecimal getEncoursTotalComptes();
    }

    /**
     * Calcule les statistiques globales pour les comptes actifs d'un bureau donné
     *
     * @param codeBureauPoste Le code du bureau
     * @param codeEtatCompte Liste des états de compte à exclure (inactifs)
     * @return Les statistiques contenant le nombre total de comptes et la somme des soldes
     */
    @Query("SELECT COUNT(c) as nombreTotalComptes, SUM(c.soldeCourant) as encoursTotalComptes " +
            "FROM CompteCCP c " +
            "WHERE c.bureauPoste.codeBureau = :codeBureauPoste " +
            "AND c.codeEtatCompte NOT IN :codeEtatCompte")
    PortefeuilleStats calculerStatistiquesPortefeuille(
            @Param("codeBureauPoste") Long codeBureauPoste,
            @Param("codeEtatCompte") List<String> codeEtatCompte);
}
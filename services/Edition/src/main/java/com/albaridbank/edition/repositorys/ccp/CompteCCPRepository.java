package com.albaridbank.edition.repositorys.ccp;

import com.albaridbank.edition.model.ccp.CompteCCP;
import com.albaridbank.edition.repositorys.ccp.projectionCCPRepo.CompteStats;
import com.albaridbank.edition.repositorys.ccp.projectionCCPRepo.PortefeuilleStats;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link CompteCCP} entities.
 * Provides methods for querying CCP accounts with various filters and conditions.
 *
 * @author Mohamed Amine Eddafir
 */
@Repository
public interface CompteCCPRepository extends JpaRepository<CompteCCP, Long>, JpaSpecificationExecutor<CompteCCP> {

    /**
     * Calcule les statistiques globales pour les comptes actifs d'un bureau donné
     *
     * @param codeBureauPoste Le code du bureau
     * @param codeEtatCompte  Liste des états de compte à exclure (inactifs)
     * @return Les statistiques contenant le nombre total de comptes et la somme des soldes
     */
    @Query("SELECT COUNT(c) as nombreTotalComptes, SUM(c.soldeCourant) as encoursTotalComptes " +
            "FROM CompteCCP c " +
            "WHERE c.bureauPoste.codeBureau = :codeBureauPoste " +
            "AND c.codeEtatCompte NOT IN :codeEtatCompte")
    PortefeuilleStats calculerStatistiquesPortefeuille(
            @Param("codeBureauPoste") Long codeBureauPoste,
            @Param("codeEtatCompte") List<String> codeEtatCompte);

    /**
     * Calcule les statistiques globales pour les comptes actifs d'un bureau donné
     *
     * @param codeBureauPoste Le code du bureau
     * @param codeEtatCompte  Liste des états de compte à exclure (inactifs)
     * @return Les statistiques des comptes
     */
    @Query("""
             SELECT
                 b.codeBureau as codburpo,
                 b.designation as desburpo,
                 COUNT(c) as nombreTotalComptes,
                 SUM(c.soldeCourant) as totalEncours
             FROM CompteCCP c
             JOIN c.bureauPoste b
             WHERE b.codeBureau = :codeBureauPoste
             AND c.codeEtatCompte NOT IN :codeEtatCompte
             GROUP BY b.codeBureau, b.designation
            """)
    Optional<CompteStats> calculerStatistiquesComptes(
            @Param("codeBureauPoste") Long codeBureauPoste,
            @Param("codeEtatCompte") List<String> codeEtatCompte);

    @Query("""
                SELECT c FROM CompteCCP c
                JOIN FETCH c.client cl
                JOIN FETCH cl.categorieSocioProfessionnelle csp
                JOIN FETCH c.bureauPoste bp
                WHERE c.bureauPoste.codeBureau = :codeBureau
                AND (:etatCompte IS NULL OR c.codeEtatCompte = :etatCompte)
                AND (:typeCompte IS NULL OR c.codeProduit = :typeCompte)
                ORDER BY c.soldeCourant DESC
            """)
    Page<CompteCCP> findPortefeuilleClientsByBureauWithFilters(
            @Param("codeBureau") Long codeBureau,
            @Param("etatCompte") String etatCompte,
            @Param("typeCompte") Integer typeCompte,
            Pageable pageable
    );

    @Query("""
                SELECT
                    COUNT(c) as nombreTotalComptes,
                    SUM(c.soldeCourant) as encoursTotalComptes,
                    SUM(c.soldeOpposition) as totalSoldeOpposition,
                    SUM(c.soldeTaxe) as totalSoldeTaxe,
                    SUM(c.soldeDebitOperations) as totalSoldeDebitOperations,
                    SUM(c.soldeCreditOperations) as totalSoldeCreditOperations,
                    SUM(c.soldeOperationsPeriode) as totalSoldeOperationsPeriode,
                    SUM(c.soldeCertifie) as totalSoldeCertifie
                FROM CompteCCP c
                WHERE c.bureauPoste.codeBureau = :codeBureau
                AND (:etatCompte IS NULL OR c.codeEtatCompte = :etatCompte)
                AND (:typeCompte IS NULL OR c.codeProduit = :typeCompte)
            """)
    PortefeuilleStats calculerStatistiquesPortefeuilleDetail(
            @Param("codeBureau") Long codeBureau,
            @Param("etatCompte") String etatCompte,
            @Param("typeCompte") Integer typeCompte
    );
}
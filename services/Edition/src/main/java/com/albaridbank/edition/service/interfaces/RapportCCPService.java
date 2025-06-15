package com.albaridbank.edition.service.interfaces;

import com.albaridbank.edition.dto.excelCCP.PortefeuilleClientCCPExcelDTO;
import com.albaridbank.edition.dto.rapport.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

/**
 * Interface for generating reports for CCP accounts.
 * This interface defines methods for creating various types of reports
 * related to CCP accounts, such as movement reports, total balances,
 * client portfolios, and top accounts by balance.
 *
 * @author Mohamed Amine Eddafir
 */
public interface RapportCCPService {

    /**
     * Génère un rapport des comptes mouvementés la veille ou l'avant-veille
     *
     * @param codeBureau     Code de l'agence
     * @param montantMinimum Montant minimum des mouvements à prendre en compte
     * @param joursAvant     Nombre de jours avant la date actuelle (1 pour veille, 2 pour avant-veille)
     * @param pageable       Information de pagination
     * @return DTO contenant les informations du rapport
     */
    CompteMouvementVeilleDTO rapportMouvementVeille(Long codeBureau, BigDecimal montantMinimum, Integer joursAvant, Pageable pageable);

    /**
     * Génère un rapport du nombre total et de l'encours des comptes CCP
     *
     * @param codeBureau Code du bureau de poste
     * @return Rapport du nombre total et de l'encours
     */
    NbrTotalEncoursCCPDTO genererRapportEncoursGlobal(Long codeBureau);

    /**
     * Génère un rapport détaillé du portefeuille client CCP
     *
     * @param pageable   Pagination des résultats
     * @param codeBureau Code du bureau de poste
     * @return Rapport du portefeuille client
     */
    Page<PortefeuilleClientCCPDTO> genererRapportPortefeuilleClient(Pageable pageable, Long codeBureau);

    /**
     * Génère le rapport détaillé du portefeuille client CCP pour une agence donnée avec filtres
     *
     * @param codeBureau code de l'agence
     * @param pageable   pagination des résultats
     * @param typeCompte type de compte (1: Normal, 2: Dirham Convertible, null: tous)
     * @param etatCompte état du compte (A : Actif, I : Inactif, etc., null : tous)
     * @return DTO contenant les informations du rapport
     * @throws IllegalArgumentException si le code bureau est null
     */
    PortefeuilleClientCCPRapportDTO genererRapportPortefeuilleClientFiltre(
            Long codeBureau,
            Pageable pageable,
            Integer typeCompte,
            String etatCompte
    );

    /**
     * Génère un rapport des 100 plus grands comptes CCP par solde
     *
     * @param codeBureau Code du bureau de poste
     * @return Rapport des Top 100 comptes
     */
    PortefeuilleClientCCP_Top100_DTO genererRapportTop100(Long codeBureau);

    /**
     * <h3>Export this Rapport as excel 'ETAT PORTEFEUILLE CLIENT CCP'</h3>
     * This method is intended to export the client portfolio report as an Excel file.
     *
     * @param codeBureau Code du bureau de poste
     * @param etatCompte état du compte (A : Actif, I : Inactif, etc., null : tous)
     * @param username   Nom d'utilisateur qui a généré le rapport
     * @return DTO contenant toutes les informations du rapport
     */
    PortefeuilleClientCCPExcelDTO genererRapportPortefeuilleClientPourExcel(
            Long codeBureau,
            String etatCompte,
            String username
    );
}
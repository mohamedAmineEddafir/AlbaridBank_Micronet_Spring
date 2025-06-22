package com.albaridbank.edition.service.interfaces;

import com.albaridbank.edition.dto.excelCCP.NbrTotalEncoursCCPExcelDTO;
import com.albaridbank.edition.dto.excelCCP.PortefeuilleClientCCPExcelDTO;
import com.albaridbank.edition.dto.excelCCP.PortefeuilleClientCCPMExcelDTO;
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
     * Génère un rapport de portefeuille client CCP pour une recherche spécifique
     *
     * @param codeBureau Le code du bureau
     * @param pageable   Information de pagination
     * @param typeCompte Le type de compte (1: Compte courant postal, 2: professional, etc., null: tous)
     * @param etatCompte L'état du compte (N : Normal, O : Oposé, etc., null : tous)
     * @param searchTerm Le terme de recherche pour filtrer les résultats
     * @return Le DTO contenant les données du rapport de portefeuille client CCP
     */
    PortefeuilleClientCCPRapportDTO genererRapportPortefeuilleClientRecherche(Long codeBureau, Pageable pageable, Integer typeCompte, String etatCompte, String searchTerm);

    /**
     * Génère un rapport de portefeuille client CCP avec des filtres spécifiques
     *
     * @param codeBureau Code du bureau de poste
     * @param pageable   Information de pagination
     * @param typeCompte Le type de compte (1: Compte courant postal, 2: professional, etc., null: tous)
     * @param etatCompte L'état du compte (N : Normal, O : Oposé, etc., null : tous)
     * @return Le DTO contenant les données du rapport de portefeuille client CCP
     */
    PortefeuilleClientCCPRapportDTO genererRapportPortefeuilleClientFiltre(Long codeBureau, Pageable pageable, Integer typeCompte, String etatCompte);


    /*
     * Génère un rapport des 100 plus grands comptes CCP par solde
     *
     * @param codeBureau Code du bureau de poste
     * @return Rapport des Top 100 comptes
     **/
    //PortefeuilleClientCCP_Top100_DTO genererRapportTop100(Long codeBureau);

    /**
     * Génère un rapport de portefeuille client CCP pour une recherche globale
     *
     * @param codeBureau Le code du bureau
     * @param typeCompte Le type de compte (1: Compte courant postal, 2: professional, etc., null: tous)
     * @param etatCompte L'état du compte (N : Normal, O : Oposé, etc., null : tous)
     * @param searchTerm Le terme de recherche pour filtrer les résultats
     * @return Le DTO contenant les données du rapport de portefeuille client CCP
     */
    PortefeuilleClientCCPRapportDTO genererRapportPortefeuilleClientRechercheGlobale(Long codeBureau, Integer typeCompte, String etatCompte, String searchTerm);

    /**
     * <h3>Export this Rapport as excel 'ETAT PORTEFEUILLE CLIENT CCP'</h3>
     * This method is intended to export the client portfolio report as an Excel file.
     *
     * @param codeBureau Code du bureau de poste
     * @param etatCompte état du compte (A : Actif, I : Inactif, etc., null : tous)
     * @param username   Nom d'utilisateur qui a généré le rapport
     * @return DTO contenant toutes les informations du rapport
     */
    PortefeuilleClientCCPExcelDTO genererRapportPortefeuilleClientPourExcel(Long codeBureau, String etatCompte, String username);

    /**
     * <h3>Export this Rapport as excel 'ETAT DES COMPTES MOUVEMENTES LA VEILLE'</h3>
     * Génère un rapport des comptes mouvementés pour l'export Excel
     */
    CompteMouvementVeilleDTO genererRapportMouvementVeillePourExcel(Long codeAgence, Integer joursAvant, BigDecimal montantMinimum, String username);

    /**
     * Export this Rapport as Excel <h3>'ETAT NBR TOTAL ENCOURS G CCP'</h3>
     * Génère un rapport du nombre total et de l'encours global pour l'export Excel
     */
    NbrTotalEncoursCCPExcelDTO genererRapportEncoursGlobalPourExcel(Long codeBureau, String username);

    /**
     * Génère un rapport de portefeuille client M CCP pour l'export Excel <h3>ETAT PORTEFEUILLE CLIENT M CCP</h3>
     *
     * @param codeBureau Le code du bureau
     * @param etatCompte L'état du compte (optionnel)
     * @param username   Nom d'utilisateur
     * @return Le DTO contenant les données pour l'export Excel
     */
    PortefeuilleClientCCPMExcelDTO genererRapportPortefeuilleClientMPourExcel(Long codeBureau, String etatCompte, String username);
}
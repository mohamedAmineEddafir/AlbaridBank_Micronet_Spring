package com.albaridbank.edition.service.interfaces;

import com.albaridbank.edition.dto.rapport.CompteMouvementVeilleDTO;
import com.albaridbank.edition.dto.rapport.NbrTotalEncoursCCPDTO;
import com.albaridbank.edition.dto.rapport.PortefeuilleClientCCPDTO;
import com.albaridbank.edition.dto.rapport.PortefeuilleClientCCP_Top100_DTO;
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
     * Génère un rapport des mouvements de veille pour les comptes CCP
     *
     * @param codeBureau     Code du bureau de poste
     * @param joursAvant     Nombre de jours avant la veille
     * @param montantMinimum Montant minimum des mouvements
     * @param pageable       Pagination des résultats
     * @return Rapport des mouvements de veille
     */
    Page<CompteMouvementVeilleDTO> genererRapportMouvementVeille(
            Long codeBureau,
            Integer joursAvant,
            BigDecimal montantMinimum,
            Pageable pageable);

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
     * Génère un rapport des 100 plus grands comptes CCP par solde
     *
     * @param codeBureau Code du bureau de poste
     * @return Rapport des Top 100 comptes
     */
    PortefeuilleClientCCP_Top100_DTO genererRapportTop100(Long codeBureau);
}
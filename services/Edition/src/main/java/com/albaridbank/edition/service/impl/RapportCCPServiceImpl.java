package com.albaridbank.edition.service.impl;

import com.albaridbank.edition.dto.base.CompteCCPDetailDTO;
import com.albaridbank.edition.dto.base.MouvementFinancierDTO;
import com.albaridbank.edition.dto.rapport.CompteMouvementVeilleDTO;
import com.albaridbank.edition.dto.rapport.NbrTotalEncoursCCPDTO;
import com.albaridbank.edition.dto.rapport.PortefeuilleClientCCPDTO;
import com.albaridbank.edition.dto.rapport.PortefeuilleClientCCP_Top100_DTO;
import com.albaridbank.edition.mappers.ccp.MvtFinancierCCPMapper;
import com.albaridbank.edition.model.ccp.BureauPosteCCP;
import com.albaridbank.edition.model.ccp.CompteCCP;
import com.albaridbank.edition.model.ccp.MvtFinancierCCP;
import com.albaridbank.edition.repositorys.ccp.CompteCCPRepository;
import com.albaridbank.edition.repositorys.ccp.BureauPosteCCPRepository;
import com.albaridbank.edition.repositorys.ccp.MvtFinancierCCPRepository;
import com.albaridbank.edition.service.interfaces.RapportCCPService;
import com.albaridbank.edition.mappers.rapport.RapportCCPMapper;
import com.albaridbank.edition.mappers.ccp.CompteCCPMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

/**
 * Service implementation for generating CCP reports.
 * Provides methods to generate various types of reports related to CCP accounts.
 *
 * <p>This service includes functionalities for generating:
 * <ul>
 *   <li>Financial movement reports for a specific past date</li>
 *   <li>Global outstanding balance reports</li>
 *   <li>Client portfolio reports</li>
 *   <li>Top 100 accounts by balance reports</li>
 * </ul>
 *
 * <p>It uses repositories for data access and mappers for converting between entities and DTOs.
 * Pagination is supported for handling large datasets.
 *
 * @author Mohamed Amine Eddafir
 * @see RapportCCPService
 * @see MvtFinancierCCP
 * @see CompteCCP
 * @see BureauPosteCCP
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RapportCCPServiceImpl implements RapportCCPService {

    /**
     * List of inactive account states.
     */
    private static final List<String> INACTIVE_STATES = List.of("C", "I", "B");

    /**
     * Dependencies injected via constructor.
     */
    private final MvtFinancierCCPMapper mvtFinancierMapper;
    private final CompteCCPRepository compteCCPRepository;
    private final RapportCCPMapper rapportCCPMapper;
    private final CompteCCPMapper compteCCPMapper;
    private final BureauPosteCCPRepository bureauPosteRepository;
    private final MvtFinancierCCPRepository mvtFinancierRepository;

    /**
     * Generates a paginated report of financial movements for a specific past date.
     * Uses MapStruct for mapping and integrates pagination.
     *
     * @param codeBureau     The code of the bureau for which the report is generated.
     * @param joursAvant     The number of days before today (0 for to day, 1 for yesterday, 2 for the day before, etc.).
     * @param montantMinimum The minimum amount of movement to consider.
     * @param pageable       The pagination information for the list of movements.
     * @return A {@link CompteMouvementVeilleDTO} object containing the report with paginated financial movements.
     * @throws EntityNotFoundException If the specified bureau does not exist.
     */
    @Override
    public CompteMouvementVeilleDTO rapportMouvementVeille(Long codeBureau, BigDecimal montantMinimum, Integer joursAvant, Pageable pageable) {
        // Validation des paramètres
        if (joursAvant == null || joursAvant < 1 || joursAvant > 2) {
            joursAvant = 0;
        }

        if (montantMinimum == null || montantMinimum.compareTo(BigDecimal.ZERO) < 0) {
            montantMinimum = BigDecimal.ZERO; // Par défaut tous les mouvements
        }

        // Calcul de la date du rapport
        LocalDate dateRapport = LocalDate.now().minusDays(joursAvant);

        // Récupération des informations de l'agence
        Optional<BureauPosteCCP> bureauOpt = bureauPosteRepository.findById(codeBureau);
        if (bureauOpt.isEmpty()) {
            throw new IllegalArgumentException("Bureau de poste non trouvé avec code: " + codeBureau);
        }
        BureauPosteCCP bureau = bureauOpt.get();

        // Conversion du codeBureau en BigDecimal pour la requête
        BigDecimal codeBureauBD = new BigDecimal(codeBureau);

        // Récupération des mouvements paginés
        Page<MvtFinancierCCP> mouvementsPage = mvtFinancierRepository
                .findPageByDateMouvementAndCodeBureauAndMontantMin(
                        dateRapport,
                        codeBureauBD,
                        montantMinimum,
                        pageable
                );

        // Conversion des entités en DTOs
        Page<MouvementFinancierDTO> mouvementDTOs = mouvementsPage.map(mvtFinancierMapper::toMouvementFinancierDTO);

        // Récupération des statistiques
        MvtFinancierCCPRepository.MouvementStats stats = mvtFinancierRepository
                .getStatistiques(dateRapport, codeBureauBD, montantMinimum);

        // Création du rapport
        return rapportCCPMapper.creerRapportMouvementVeille(
                codeBureau,
                bureau.getDesignation(),
                dateRapport,
                mouvementDTOs,
                stats.getNombreComptes(),
                stats.getMontantTotal(),
                joursAvant,
                montantMinimum
        );
    }

    /**
     * Generates a global outstanding balance report for a specific bureau.
     *
     * @param codeBureau The code of the bureau for which the report is generated.
     * @return A {@link NbrTotalEncoursCCPDTO} object containing the outstanding balance report.
     */
    @Override
    @Transactional(readOnly = true)
    public NbrTotalEncoursCCPDTO genererRapportEncoursGlobal(Long codeBureau) {
        return null;
    }

    /**
     * Generates the CCP Client Portfolio report by retrieving all active accounts
     * for the specified bureau and applying the given pagination settings.
     *
     * This method filters accounts based on their active status and the specified bureau,
     * retrieves the accounts for the requested page, calculates global statistics, and maps
     * the results into a DTO for the client portfolio report.
     *
     * @param pageable   The pagination information to control the size and number of results.
     * @param codeBureau The code of the bureau for which the report is generated.
     *                   Must not be null.
     * @return A paginated list containing a single {@link PortefeuilleClientCCPDTO} object
     * with the account details and the total balance.
     * @throws EntityNotFoundException If the specified bureau is not found.
     * @throws RuntimeException        If an unexpected error occurs during report generation.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PortefeuilleClientCCPDTO> genererRapportPortefeuilleClient(Pageable pageable, Long codeBureau) {
        log.info("Generating report for bureau: {}", codeBureau);
        Objects.requireNonNull(codeBureau, "Code bureau cannot be null");
        try {
            // Define a specification to filter active accounts for the given bureau
            Specification<CompteCCP> specification = (root, query, criteriaBuilder) -> {
                assert query != null;
                query.distinct(true);
                return criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("bureauPoste").get("codeBureau"), codeBureau),
                        criteriaBuilder.not(root.get("codeEtatCompte").in(INACTIVE_STATES))
                );
            };

            // Retrieve only the accounts for the requested page using pageable
            Page<CompteCCP> comptesPage = compteCCPRepository.findAll(specification, pageable);
            List<CompteCCP> comptes = comptesPage.getContent();

            // Récupération des statistiques globales (indépendamment de la pagination)
            CompteCCPRepository.PortefeuilleStats stats =
                    compteCCPRepository.calculerStatistiquesPortefeuille(codeBureau, INACTIVE_STATES);

            // Map the accounts to their DTO representations
            List<CompteCCPDetailDTO> comptesDTO = compteCCPMapper.toCompteCCPDetailDTOList(comptes);

            String designation = comptes.stream()
                    .map(CompteCCP::getBureauPoste)
                    .filter(Objects::nonNull)
                    .map(BureauPosteCCP::getDesignation)
                    .findFirst()
                    .orElse("Unknown Bureau");

            // Create the report DTO using the mapper with global totals
            PortefeuilleClientCCPDTO rapport = rapportCCPMapper.creerRapportPortefeuilleClient(
                    codeBureau,
                    designation,
                    comptesDTO,
                    stats.getEncoursTotalComptes(),
                    stats.getNombreTotalComptes()
            );

            // Return the report wrapped in a paginated response using the total count from comptesPage
            return new PageImpl<>(Collections.singletonList(rapport), pageable, comptesPage.getTotalElements());
        } catch (EntityNotFoundException e) {
            log.error("Bureau not found: {}", codeBureau, e);
            throw new EntityNotFoundException("Bureau not found: " + codeBureau);
        } catch (Exception e) {
            log.error("Error generating report for bureau: {}", codeBureau, e);
            throw new RuntimeException("Error generating report", e);
        }
    }

    /**
     * Generates a report of the top 100 CCP accounts by balance for a specific bureau.
     *
     * @param codeBureau The code of the bureau for which the report is generated.
     * @return A {@link PortefeuilleClientCCP_Top100_DTO} object containing the top 100 accounts report.
     */
    @Override
    @Transactional(readOnly = true)
    public PortefeuilleClientCCP_Top100_DTO genererRapportTop100(Long codeBureau) {
        return null;
    }
}
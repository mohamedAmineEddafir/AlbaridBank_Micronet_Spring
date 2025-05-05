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
import org.hibernate.service.spi.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
<<<<<<< Updated upstream
import java.util.List;
=======
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
>>>>>>> Stashed changes

/**
 * Service implementation for generating CCP reports.
 * Provides methods to generate various types of reports related to CCP accounts.
 *
 * @author Mohamed Amine Eddafir
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
     * Repository for accessing CCP account data.
     */
<<<<<<< Updated upstream
    private final MvtFinancierCCPMapper mvtFinancierCCPMapper;
=======
    private final CompteCCPRepository compteCCPRepository;
    private final RapportCCPMapper rapportCCPMapper;
    private final CompteCCPMapper compteCCPMapper;
>>>>>>> Stashed changes
    private final BureauPosteCCPRepository bureauPosteCCPRepository;
    private final MvtFinancierCCPRepository mvtFinancierCCPRepository;

    /**
     * Generates a paginated report of financial movements for a specific past date.
     * Uses MapStruct for mapping and integrates pagination.
     *
     * @param codeBureau     The code of the bureau for which the report is generated.
     * @param joursAvant     The number of days before today (1 for yesterday, 2 for the day before, etc.).
     * @param montantMinimum The minimum amount of movement to consider.
     * @param pageable       The pagination information for the list of movements.
     * @return A Page object containing the report with paginated financial movements.
     * @throws EntityNotFoundException If the specified bureau does not exist.
     */
    @Override
    public Page<CompteMouvementVeilleDTO> genererRapportMouvementVeille(
<<<<<<< Updated upstream
            Long codeBureau,
            Integer joursAvant,
            BigDecimal montantMinimum,
            Pageable pageable) {

        log.info("Génération du rapport de mouvements pour le bureau: {}, jours avant: {}, montant minimum: {}",
                codeBureau, joursAvant, montantMinimum);

        try {
            // Retrieve the bureau and prepare the base data
            LocalDate dateMouvement = LocalDate.now().minusDays(joursAvant);

            BureauPosteCCP bureau = bureauPosteCCPRepository.findByCodeBureau(codeBureau)
                    .orElseThrow(() -> {
                        log.error("Bureau non trouvé avec code: {}", codeBureau);
                        return new EntityNotFoundException("Bureau non trouvé avec code: " + codeBureau);
                    });

            // Calculate statistics (number of accounts and amounts)
            Object[] stats = mvtFinancierCCPRepository.countDistinctAccountsAndSumMontant(dateMouvement, bureau);
            Integer nombreComptes = ((Number) stats[0]).intValue();
            BigDecimal montantTotal = (stats[1] != null) ? (BigDecimal) stats[1] : BigDecimal.ZERO;

            // Retrieve paginated financial movements
            Page<MvtFinancierCCP> mouvementsPage = mvtFinancierCCPRepository
                    .findByDateMouvementAndBureauPosteAndMontantMinPaginated(dateMouvement, bureau, montantMinimum, pageable);

            // Map the movements to DTOs
            List<MouvementFinancierDTO> mouvementDTOs = mvtFinancierCCPMapper
                    .toMouvementFinancierDTOList(mouvementsPage.getContent());

            // Use the MapStruct mapper to create the report
            CompteMouvementVeilleDTO rapport = rapportCCPMapper.creerRapportMouvementVeille(
                    bureau.getCodeBureau(),
                    bureau.getDesignation(),
                    dateMouvement,
=======
            Long codeBureau, Integer joursAvant, BigDecimal montantMinimum, Pageable pageable) {

        try {
            // Rechercher le bureau postal
            BureauPosteCCP bureau = bureauPosteCCPRepository.findById(codeBureau)
                    .orElseThrow(() -> new EntityNotFoundException("Bureau avec code " + codeBureau + " non trouvé"));

            // Calculer la date pour le rapport
            LocalDate dateRapport = LocalDate.now().minusDays(joursAvant);

            // Récupérer les statistiques (nombre comptes + montant total)
            Object[] stats = mvtFinancierCCPRepository.countDistinctAccountsAndSumMontant(dateRapport, bureau);

            // CORRECTION ICI - Extraire correctement les valeurs du tableau
            int nombreComptes = 0;
            BigDecimal montantTotal = BigDecimal.ZERO;

            if (stats != null) {
                // L'index 0 contient le COUNT
                if (stats[0] != null) {
                    nombreComptes = ((Number) stats[0]).intValue(); // Convertir en Integer pour le mapper
                }

                // L'index 1 contient la SUM
                if (stats[1] != null) {
                    montantTotal = new BigDecimal(stats[1].toString());
                }
            }

            // Récupérer les mouvements financiers avec pagination
            Page<MvtFinancierCCP> mouvementsPage = mvtFinancierCCPRepository
                    .findByDateMouvementAndBureauPosteAndMontantMinPaginated(
                            dateRapport, bureau, montantMinimum, pageable);

            // Convertir les mouvements en DTOs
            List<MouvementFinancierDTO> mouvementDTOs = mouvementsPage.getContent().stream()
                    .map(mvt -> {
                        // Créer et mapper votre DTO de mouvement financier
                        // ... (remplir les propriétés du DTO)
                        return new MouvementFinancierDTO();
                    })
                    .collect(Collectors.toList());

            // Créer le rapport principal en utilisant le mapper
            CompteMouvementVeilleDTO rapportDTO = rapportCCPMapper.creerRapportMouvementVeille(
                    codeBureau,
                    bureau.getDesignation(), // ou autre propriété qui contient le nom du bureau
                    dateRapport,
>>>>>>> Stashed changes
                    mouvementDTOs,
                    nombreComptes,
                    montantTotal,
                    joursAvant,
                    montantMinimum
            );

<<<<<<< Updated upstream
            // Return the result with correct pagination information for the UI
            return new PageImpl<>(
                    Collections.singletonList(rapport),    // A single report per page
                    pageable,                              // Original pagination information
                    mouvementsPage.getTotalElements()      // Total number of elements (movements)
            );

        } catch (EntityNotFoundException e) {
            throw e; // Re-throw the already logged exception
        } catch (Exception e) {
            log.error("Erreur lors de la génération du rapport pour le bureau: {}", codeBureau, e);
=======
            // Créer une Page à partir du DTO créé
            return new PageImpl<>(
                    Collections.singletonList(rapportDTO),
                    pageable,
                    1 // un seul résultat
            );
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
>>>>>>> Stashed changes
            throw new ServiceException("Erreur lors de la génération du rapport: " + e.getMessage(), e);
        }
    }

    @Override
    public NbrTotalEncoursCCPDTO genererRapportEncoursGlobal(Long codeBureau) {
        return null;
    }

    /**
     * Generates the CCP Client Portfolio report by retrieving all active accounts
     * for the specified bureau and applying the given pagination settings.
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

            // Map the accounts to their DTO representations
            List<CompteCCPDetailDTO> comptesDTO = compteCCPMapper.toCompteCCPDetailDTOList(comptes);

            String designation = comptes.stream()
                    .map(CompteCCP::getBureauPoste)
                    .filter(Objects::nonNull)
                    .map(BureauPosteCCP::getDesignation)
                    .findFirst()
                    .orElse("Unknown Bureau");

            // Calculate the total balance of the accounts on this page
            BigDecimal sommeSolde = comptes.stream()
                    .map(CompteCCP::getSoldeCourant)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // Create the report DTO using the mapper
            PortefeuilleClientCCPDTO rapport = rapportCCPMapper.creerRapportPortefeuilleClient(
                    codeBureau,
                    designation,
                    comptesDTO,
                    sommeSolde
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
     * Génère un rapport des 100 plus grands comptes CCP par solde
     *
     * @param codeBureau Code du bureau de poste
     * @return Rapport des Top 100 comptes
     */
    @Override
    public PortefeuilleClientCCP_Top100_DTO genererRapportTop100(Long codeBureau) {
        return null;
    }
}
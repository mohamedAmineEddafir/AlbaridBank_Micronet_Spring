package com.albaridbank.edition.service.impl;

import com.albaridbank.edition.dto.base.CompteCCPDetailDTO;
import com.albaridbank.edition.dto.base.MouvementFinancierDTO;
import com.albaridbank.edition.dto.base.PortefeuilleClientCCPDetailDTO;
import com.albaridbank.edition.dto.excelCCP.NbrTotalEncoursCCPExcelDTO;
import com.albaridbank.edition.dto.excelCCP.PortefeuilleClientCCPExcelDTO;
import com.albaridbank.edition.dto.excelCCP.PortefeuilleClientCCPMExcelDTO;
import com.albaridbank.edition.dto.rapport.*;
import com.albaridbank.edition.mappers.ccp.MvtFinancierCCPMapper;
import com.albaridbank.edition.model.ccp.BureauPosteCCP;
import com.albaridbank.edition.model.ccp.CompteCCP;
import com.albaridbank.edition.model.ccp.MvtFinancierCCP;
import com.albaridbank.edition.repositorys.ccp.CompteCCPRepository;
import com.albaridbank.edition.repositorys.ccp.BureauPosteCCPRepository;
import com.albaridbank.edition.repositorys.ccp.MvtFinancierCCPRepository;
import com.albaridbank.edition.repositorys.ccp.projectionCCPRepo.PortefeuilleStats;
import com.albaridbank.edition.service.interfaces.RapportCCPService;
import com.albaridbank.edition.mappers.rapport.RapportCCPMapper;
import com.albaridbank.edition.mappers.ccp.CompteCCPMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
@Transactional(readOnly = true)
public class RapportCCPServiceImpl implements RapportCCPService {

    /**
     * List of inactive account states.
     */
    private static final List<String> INACTIVE_STATES = List.of("C", "B", "O"); // Cloturé , Bloqué , Opposé


    /**
     * List of excluded account states for global statistics.
     */
    private static final List<String> ETATS_COMPTES_EXCLUS = List.of("C", "B", "O"); // Cloturé , Bloqué , Opposé

    /**
     * Map of account states and their corresponding labels.
     */
    private static final Map<String, String> ETATS_MAPPING = Map.of(
            "NORMAL", "N",
            "OPPOSE", "O",
            "CLOTURE", "C",
            "BLOCAGE", "B"
    );

    /**
     * List of product types.
     */
    private static final List<Integer> TYPES_PRODUITS = List.of(1, 2, 3, 4);

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
     * <p>This method calculates the total outstanding balance and the number of accounts
     * for a given bureau, excluding accounts in inactive states. If no data is found for
     * the specified bureau, an empty DTO is returned.</p>
     *
     * @param codeBureau The code of the bureau for which the report is generated.
     *                   Must not be null.
     * @return A {@link NbrTotalEncoursCCPDTO} object containing the outstanding balance report.
     * If no data is found, an empty DTO is returned.
     * @throws NullPointerException If the provided bureau code is null.
     * @throws RuntimeException     If an error occurs during the report generation process.
     */
    @Override
    @Transactional(readOnly = true)
    public NbrTotalEncoursCCPDTO genererRapportEncoursGlobal(Long codeBureau) {
        log.info("Génération du rapport d'encours global pour le bureau: {}", codeBureau);

        Objects.requireNonNull(codeBureau, "Le code du bureau ne peut pas être null");

        try {
            return compteCCPRepository.calculerStatistiquesComptes(codeBureau, ETATS_COMPTES_EXCLUS)
                    .map(rapportCCPMapper::toDto)
                    .orElseGet(() -> {
                        log.warn("Aucune donnée trouvée pour le bureau: {}", codeBureau);
                        return rapportCCPMapper.toEmptyDto(codeBureau);
                    });

        } catch (Exception e) {
            log.error("Erreur lors de la génération du rapport d'encours pour le bureau {}: {}",
                    codeBureau, e.getMessage());
            throw new RuntimeException("Erreur lors de la génération du rapport d'encours", e);
        }
    }

    /**
     * Generates the CCP Client Portfolio report by retrieving all active accounts
     * for the specified bureau and applying the given pagination settings.
     * <p>
     * This method filters accounts based on their active status and the specified bureau,
     * retrieves the accounts for the requested page, calculates global statistics, and maps
     * the results into a DTO for the client portfolio report.
     * </p>
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
            PortefeuilleStats stats =
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

    /*
     * Generates a report of the top 100 CCP accounts by balance for a specific bureau.
     *
     * @param codeBureau The code of the bureau for which the report is generated.
     * @return A {@link PortefeuilleClientCCP_Top100_DTO} object containing the top 100 accounts report.

    @Override
    @Transactional(readOnly = true)
    public PortefeuilleClientCCP_Top100_DTO genererRapportTop100(Long codeBureau) {
        return null;
    }**/

    /**
     * <h1>ETAT PORTEFEUILLE CLIENT CCP M</h1>
     * Generates a detailed client portfolio report for a specific bureau with optional filters.
     *
     * <p>This method processes CCP accounts for a given bureau and applies specified filters
     * for account type and state. The method follows a structured approach:
     * <ol>
     *   <li>Validates input parameters</li>
     *   <li>Retrieves the bureau information</li>
     *   <li>Processes and validates the account state filter</li>
     *   <li>Determines excluded account states</li>
     *   <li>Generates the final report with pagination</li>
     * </ol>
     *
     * @param codeBureau The unique identifier of the postal bureau for which to generate the report.
     *                   Must not be null.
     * @param pageable   The pagination information to control the size and number of results returned.
     * @param typeCompte An optional filter for the type of account (1, 2, 3, or 4).
     *                   If null, accounts of all types are included.
     * @param etatCompte An optional filter for the account state (N, O, C, B or their full names).
     *                   If null, the default excluded states are applied.
     * @return A {@link PortefeuilleClientCCPRapportDTO} containing the detailed client portfolio report.
     * @throws IllegalArgumentException If the bureau code is null.
     * @throws ResponseStatusException  If the bureau is not found, or if invalid account type or state is provided.
     */
    @Override
    public PortefeuilleClientCCPRapportDTO genererRapportPortefeuilleClientFiltre(
            Long codeBureau,
            Pageable pageable,
            Integer typeCompte,
            String etatCompte
    ) {
        validateBureauCode(codeBureau);
        validateTypeCompte(typeCompte);

        log.debug("Génération du rapport portefeuille client - Bureau: {}, Type: {}, État: {}",
                codeBureau, typeCompte, etatCompte);

        BureauPosteCCP bureauPoste = getBureauPoste(codeBureau);
        String etatCompteFiltre = validateEtatCompte(etatCompte);

        return generateReport(bureauPoste, pageable, typeCompte, etatCompteFiltre);
    }

    /**
     * Validates that the bureau code is not null.
     *
     * @param codeBureau The bureau code to validate
     * @throws IllegalArgumentException If the bureau code is null
     */
    private void validateBureauCode(Long codeBureau) {
        if (codeBureau == null) {
            throw new IllegalArgumentException("Le code du bureau ne peut pas être null");
        }
    }

    /**
     * Validates that the account type is valid if specified.
     *
     * @param typeCompte The account type to validate. Can be null.
     * @throws ResponseStatusException If the specified account type is invalid
     */
    private void validateTypeCompte(Integer typeCompte) {
        if (typeCompte != null && !TYPES_PRODUITS.contains(typeCompte)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    String.format("Type de compte invalide: %d. Valeurs acceptées: %s",
                            typeCompte, TYPES_PRODUITS)
            );
        }
    }

    /**
     * Retrieves the bureau information by its code.
     *
     * @param codeBureau The bureau code to look up
     * @return The {@link BureauPosteCCP} entity
     * @throws ResponseStatusException If no bureau is found with the specified code
     */
    private BureauPosteCCP getBureauPoste(Long codeBureau) {
        return bureauPosteRepository.findByCodeBureau(codeBureau)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Bureau non trouvé avec le code: %d", codeBureau)
                ));
    }

    /**
     * Validates and normalizes the account state filter.
     *
     * @param etatCompte The account state to validate and normalize. Can be null.
     * @return The normalized account state code, or null if no state was provided
     * @throws ResponseStatusException If the provided account state is invalid
     */
    private String validateEtatCompte(String etatCompte) {
        if (etatCompte == null) return null;

        String upperEtatCompte = etatCompte.toUpperCase().trim();
        if (ETATS_MAPPING.containsValue(upperEtatCompte)) {
            return upperEtatCompte;
        }

        String mappedCode = ETATS_MAPPING.get(upperEtatCompte);
        if (mappedCode != null) {
            return mappedCode;
        }

        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                String.format("État de compte invalide: %s. Valeurs acceptées: %s ou leurs codes: %s",
                        etatCompte, ETATS_MAPPING.keySet(), ETATS_MAPPING.values())
        );
    }

    /**
     * Generates the detailed client portfolio report.
     *
     * @param bureauPoste      The bureau entity
     * @param pageable         The pagination information
     * @param typeCompte       The account type filter (can be null)
     * @param etatCompteFiltre The validated account state filter (can be null)
     * @return A {@link PortefeuilleClientCCPRapportDTO} containing the portfolio report
     * @throws ResponseStatusException If an error occurs during report generation
     */
    private PortefeuilleClientCCPRapportDTO generateReport(
            BureauPosteCCP bureauPoste,
            Pageable pageable,
            Integer typeCompte,
            String etatCompteFiltre
    ) {
        try {
            Page<CompteCCP> comptesPage = compteCCPRepository
                    .findPortefeuilleClientsByBureauWithFilters(
                            bureauPoste.getCodeBureau(),
                            etatCompteFiltre,
                            typeCompte,
                            pageable
                    );

            PortefeuilleStats stats = compteCCPRepository
                    .calculerStatistiquesPortefeuilleDetail(
                            bureauPoste.getCodeBureau(),
                            etatCompteFiltre,
                            typeCompte
                    );

            List<PortefeuilleClientCCPDetailDTO> comptesDTO = comptesPage.getContent()
                    .stream()
                    .map(rapportCCPMapper::toDetailDTO)
                    .collect(Collectors.toList());

            validateResults(etatCompteFiltre, comptesDTO);

            return rapportCCPMapper.creerRapportPortefeuilleDetaillee(
                    bureauPoste.getCodeBureau(),
                    bureauPoste.getDesignation(),
                    comptesDTO,
                    stats
            );

        } catch (Exception e) {
            log.error("Erreur lors de la génération du rapport pour le bureau {}: {}",
                    bureauPoste.getCodeBureau(), e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erreur lors de la génération du rapport",
                    e
            );
        }
    }

    /**
     * Validates that all returned accounts match the requested state.
     *
     * @param etatCompteFiltre The requested account state
     * @param comptesDTO       The list of account DTOs to validate
     */
    private void validateResults(String etatCompteFiltre, List<PortefeuilleClientCCPDetailDTO> comptesDTO) {
        if (etatCompteFiltre != null && !comptesDTO.isEmpty()) {
            boolean allMatchState = comptesDTO.stream()
                    .allMatch(dto -> etatCompteFiltre.equals(dto.getEtatCompte()));
            if (!allMatchState) {
                log.error("Des comptes avec un état incorrect ont été retournés pour l'état: {}",
                        etatCompteFiltre);
            }
        }
    }

    /**
     * <h1>ETAT PORTEFEUILLE CLIENT CCP M - RECHERCHE</h1>
     * Generates a client portfolio report with search filtering.
     *
     * @param codeBureau Bureau code
     * @param pageable   Pagination information
     * @param typeCompte Optional account type filter
     * @param etatCompte Optional account state filter
     * @param searchTerm Search term to filter accounts
     * @return A {@link PortefeuilleClientCCPRapportDTO} containing the filtered report
     */
    public PortefeuilleClientCCPRapportDTO genererRapportPortefeuilleClientRecherche(
            Long codeBureau,
            Pageable pageable,
            Integer typeCompte,
            String etatCompte,
            String searchTerm
    ) {
        validateBureauCode(codeBureau);
        validateTypeCompte(typeCompte);

        log.debug("Génération du rapport portefeuille client avec recherche - Bureau: {}, Type: {}, État: {}, Recherche: {}",
                codeBureau, typeCompte, etatCompte, searchTerm);

        BureauPosteCCP bureauPoste = getBureauPoste(codeBureau);
        String etatCompteFiltre = validateEtatCompte(etatCompte);

        return generateReportWithSearch(bureauPoste, pageable, typeCompte, etatCompteFiltre, searchTerm);
    }

    /**
     * <h1>ETAT PORTEFEUILLE CLIENT CCP M - RECHERCHE GLOBALE</h1>
     * Generates a client portfolio report with global search (non-paginated).
     *
     * @param codeBureau Bureau code
     * @param typeCompte Optional account type filter
     * @param etatCompte Optional account state filter
     * @param searchTerm Search term to filter accounts
     * @return A {@link PortefeuilleClientCCPRapportDTO} containing all matching accounts
     */
    public PortefeuilleClientCCPRapportDTO genererRapportPortefeuilleClientRechercheGlobale(
            Long codeBureau,
            Integer typeCompte,
            String etatCompte,
            String searchTerm
    ) {
        validateBureauCode(codeBureau);
        validateTypeCompte(typeCompte);

        log.debug("Génération du rapport portefeuille client avec recherche globale - Bureau: {}, Type: {}, État: {}, Recherche: {}",
                codeBureau, typeCompte, etatCompte, searchTerm);

        BureauPosteCCP bureauPoste = getBureauPoste(codeBureau);
        String etatCompteFiltre = validateEtatCompte(etatCompte);

        return generateReportWithGlobalSearch(bureauPoste, typeCompte, etatCompteFiltre, searchTerm);
    }

    /**
     * Generates a report with search filtering and pagination.
     *
     * @param bureauPoste      The bureau entity
     * @param pageable         The pagination information
     * @param typeCompte       The account type filter (can be null)
     * @param etatCompteFiltre The validated account state filter (can be null)
     * @param searchTerm       The search term to filter accounts
     * @return A {@link PortefeuilleClientCCPRapportDTO} containing the filtered portfolio report
     * @throws ResponseStatusException If an error occurs during report generation
     */
    private PortefeuilleClientCCPRapportDTO generateReportWithSearch(
            BureauPosteCCP bureauPoste,
            Pageable pageable,
            Integer typeCompte,
            String etatCompteFiltre,
            String searchTerm
    ) {
        try {
            Page<CompteCCP> comptesPage = compteCCPRepository
                    .findPortefeuilleClientsByBureauWithSearch(
                            bureauPoste.getCodeBureau(),
                            etatCompteFiltre,
                            typeCompte,
                            searchTerm,
                            pageable
                    );

            PortefeuilleStats stats = compteCCPRepository
                    .calculerStatistiquesPortefeuilleDetailAvecRecherche(
                            bureauPoste.getCodeBureau(),
                            etatCompteFiltre,
                            typeCompte,
                            searchTerm
                    );

            List<PortefeuilleClientCCPDetailDTO> comptesDTO = comptesPage.getContent()
                    .stream()
                    .map(rapportCCPMapper::toDetailDTO)
                    .collect(Collectors.toList());

            validateResults(etatCompteFiltre, comptesDTO);

            return rapportCCPMapper.creerRapportPortefeuilleDetaillee(
                    bureauPoste.getCodeBureau(),
                    bureauPoste.getDesignation(),
                    comptesDTO,
                    stats
            );

        } catch (Exception e) {
            log.error("Erreur lors de la génération du rapport avec recherche pour le bureau {}: {}",
                    bureauPoste.getCodeBureau(), e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erreur lors de la génération du rapport avec recherche",
                    e
            );
        }
    }

    /**
     * Generates a report with global search (non-paginated).
     *
     * @param bureauPoste      The bureau entity
     * @param typeCompte       The account type filter (can be null)
     * @param etatCompteFiltre The validated account state filter (can be null)
     * @param searchTerm       The search term to filter accounts
     * @return A {@link PortefeuilleClientCCPRapportDTO} containing all matching accounts
     * @throws ResponseStatusException If an error occurs during report generation
     */
    private PortefeuilleClientCCPRapportDTO generateReportWithGlobalSearch(
            BureauPosteCCP bureauPoste,
            Integer typeCompte,
            String etatCompteFiltre,
            String searchTerm
    ) {
        try {
            // Récupérer tous les comptes correspondant à la recherche (sans pagination)
            List<CompteCCP> comptes = compteCCPRepository
                    .findAllPortefeuilleClientsByBureauWithSearch(
                            bureauPoste.getCodeBureau(),
                            etatCompteFiltre,
                            typeCompte,
                            searchTerm
                    );

            // Calculer les statistiques
            PortefeuilleStats stats = compteCCPRepository
                    .calculerStatistiquesPortefeuilleDetailAvecRecherche(
                            bureauPoste.getCodeBureau(),
                            etatCompteFiltre,
                            typeCompte,
                            searchTerm
                    );

            // Convertir en DTOs
            List<PortefeuilleClientCCPDetailDTO> comptesDTO = comptes.stream()
                    .map(rapportCCPMapper::toDetailDTO)
                    .collect(Collectors.toList());

            validateResults(etatCompteFiltre, comptesDTO);

            // Créer le rapport
            PortefeuilleClientCCPRapportDTO rapport = rapportCCPMapper.creerRapportPortefeuilleDetaillee(
                    bureauPoste.getCodeBureau(),
                    bureauPoste.getDesignation(),
                    comptesDTO,
                    stats
            );

            // Indiquer que c'est une recherche globale dans le titre
            rapport.setTitreRapport(rapport.getTitreRapport() + " - RECHERCHE GLOBALE");

            return rapport;

        } catch (Exception e) {
            log.error("Erreur lors de la génération du rapport avec recherche globale pour le bureau {}: {}",
                    bureauPoste.getCodeBureau(), e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erreur lors de la génération du rapport avec recherche globale",
                    e
            );
        }
    }

    /**
     * Generates an Excel report for CCP client portfolio for a specific bureau.
     *
     * <p>This method retrieves all CCP accounts for the specified bureau, applying an optional
     * filter for account state. The accounts are returned without pagination to ensure the Excel
     * report contains the complete dataset. The method calculates total balances, converts entities
     * to DTOs, and packages the results into a specialized DTO for Excel export.</p>
     *
     * <p>The generated report includes:
     * <ul>
     *   <li>Bureau information (code and name)</li>
     *   <li>Report metadata (title, generation date, user)</li>
     *   <li>Complete list of accounts matching the criteria</li>
     *   <li>Summary statistics (total number of accounts and total balance)</li>
     * </ul>
     *
     * @param codeBureau The unique identifier of the postal bureau for which to generate the report.
     *                   Must not be null.
     * @param etatCompte An optional filter for the account state. Can be null or empty for no filtering.
     *                   When provided, only accounts with the matching state will be included.
     * @param username   The username of the user generating the report, included in the report metadata.
     * @return A {@link PortefeuilleClientCCPExcelDTO} containing the complete dataset and metadata for Excel export.
     * @throws NullPointerException If the provided bureau code is null.
     * @throws RuntimeException     If an error occurs during the report generation process.
     */

    @Override
    @Transactional(readOnly = true)
    public PortefeuilleClientCCPExcelDTO genererRapportPortefeuilleClientPourExcel(
            Long codeBureau,
            String etatCompte,
            String username
    ) {
        log.info("Generating Excel report for bureau: {}, etatCompte: {}",
                codeBureau, etatCompte);
        Objects.requireNonNull(codeBureau, "Code bureau cannot be null");

        try {
            // Construire la spécification en fonction des paramètres de filtrage
            Specification<CompteCCP> specification = (root, query, criteriaBuilder) -> {
                assert query != null;
                query.distinct(true);

                // Filtrer par bureau
                Specification<CompteCCP> spec = Specification.where(
                        (r, q, cb) -> cb.equal(r.get("bureauPoste").get("codeBureau"), codeBureau));

                // Filtrer par état de compte si spécifié
                if (etatCompte != null && !etatCompte.trim().isEmpty()) {
                    spec = spec.and((r, q, cb) -> cb.equal(r.get("codeEtatCompte"), etatCompte));
                }

                return spec.toPredicate(root, query, criteriaBuilder);
            };

            // Récupérer tous les comptes correspondant aux critères sans pagination
            List<CompteCCP> comptes = compteCCPRepository.findAll(specification);

            // Récupérer les informations du bureau
            String designation = comptes.stream()
                    .map(CompteCCP::getBureauPoste)
                    .filter(Objects::nonNull)
                    .map(BureauPosteCCP::getDesignation)
                    .findFirst()
                    .orElse("Bureau inconnu");

            // Calculer les totaux
            BigDecimal encoursTotal = comptes.stream()
                    .map(CompteCCP::getSoldeCourant)  // Utilisez la méthode qui existe réellement dans CompteCCP
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // Convertir les entités en DTOs
            List<CompteCCPDetailDTO> comptesDTO = compteCCPMapper.toCompteCCPDetailDTOList(comptes);

            // Créer le DTO pour l'exportation Excel
            PortefeuilleClientCCPExcelDTO excelDTO = new PortefeuilleClientCCPExcelDTO();
            excelDTO.setTitreRapport("ETAT PORTEFEUILLE CLIENT CCP");
            excelDTO.setDateEdition(LocalDateTime.now());
            excelDTO.setUtilisateur(username);
            excelDTO.setCodburpo(codeBureau);
            excelDTO.setDesburpo(designation);
            excelDTO.setComptes(comptesDTO);
            excelDTO.setNombreTotalComptes(comptes.size());
            excelDTO.setEncoursTotalComptes(encoursTotal);

            return excelDTO;
        } catch (Exception e) {
            log.error("Error generating Excel report for bureau: {}", codeBureau, e);
            throw new RuntimeException("Failed to generate Excel report", e);
        }
    }

    /**
     * Generates an Excel report of financial movements for a specific agency.
     *
     * <p>This method retrieves all financial movements for the specified agency without pagination,
     * ensuring that the Excel report contains the complete dataset. It uses a large page size to
     * fetch all records and sorts them by amount in descending order.</p>
     *
     * @param codeAgence     The unique identifier of the agency for which to generate the report.
     *                       Must not be null.
     * @param joursAvant     The number of days before today to filter movements (e.g., 0 for today,
     *                       1 for yesterday, etc.).
     * @param montantMinimum The minimum amount of movement to include in the report. Can be null.
     * @param username       The username of the user generating the report, included in the report metadata.
     * @return A {@link CompteMouvementVeilleDTO} containing the complete dataset for Excel export.
     */
    @Override
    public CompteMouvementVeilleDTO genererRapportMouvementVeillePourExcel(
            Long codeAgence,
            Integer joursAvant,
            BigDecimal montantMinimum,
            String username) {

        // Récupérer les mouvements sans pagination pour avoir toutes les données
        // Utiliser une taille de page très grande pour récupérer tous les éléments
        Pageable pageable = PageRequest.of(0, 10000, Sort.by(Sort.Direction.DESC, "montant"));

        // Appeler la méthode existante avec la pagination spéciale
        return rapportMouvementVeille(codeAgence, montantMinimum, joursAvant, pageable);
    }

    /**
     * Generates an Excel report for the global balance of CCP accounts for a specific bureau.
     *
     * <p>This method retrieves the global balance report for the specified bureau and maps the
     * data into a DTO suitable for Excel export. The report includes metadata such as the report
     * title, generation date, and user information.</p>
     *
     * @param codeBureau The unique identifier of the bureau for which to generate the report.
     *                   Must not be null.
     * @param username   The username of the user generating the report, included in the report metadata.
     * @return A {@link NbrTotalEncoursCCPExcelDTO} containing the global balance data for Excel export.
     * @throws NullPointerException If the provided bureau code is null.
     * @throws RuntimeException     If an error occurs during the report generation process.
     */
    @Override
    @Transactional(readOnly = true)
    public NbrTotalEncoursCCPExcelDTO genererRapportEncoursGlobalPourExcel(
            Long codeBureau,
            String username) {

        log.info("Generating Excel report for global balance for bureau: {}", codeBureau);
        Objects.requireNonNull(codeBureau, "Code bureau cannot be null");

        try {
            // Récupérer le rapport existant
            NbrTotalEncoursCCPDTO rapportData = genererRapportEncoursGlobal(codeBureau);

            // Créer et remplir le DTO pour l'export Excel
            NbrTotalEncoursCCPExcelDTO excelDTO = new NbrTotalEncoursCCPExcelDTO();
            excelDTO.setTitreRapport(rapportData.getTitreRapport());
            excelDTO.setDateEdition(LocalDateTime.now());
            excelDTO.setJourneeDu(rapportData.getJourneeDu());
            excelDTO.setCodeBureau(rapportData.getCodeBureau());
            excelDTO.setDesignationBureau(rapportData.getDesignationBureau());
            excelDTO.setNombreComptes(rapportData.getNombreComptes());
            excelDTO.setTotalEncours(rapportData.getTotalEncours());
            excelDTO.setUtilisateur(username);

            return excelDTO;
        } catch (Exception e) {
            log.error("Error generating Excel report for global balance for bureau: {}", codeBureau, e);
            throw new RuntimeException("Failed to generate Excel report for global balance", e);
        }
    }

    /**
     * Génère un rapport de portefeuille client M CCP pour l'export Excel
     *
     * <p>Cette méthode récupère les données détaillées des comptes CCP pour un bureau spécifique,
     * avec un filtrage optionnel par état de compte. Les données sont récupérées sans pagination
     * pour assurer que le rapport Excel contient l'ensemble complet des données.</p>
     *
     * @param codeBureau Le code du bureau
     * @param etatCompte L'état du compte (optionnel)
     * @param username   Nom d'utilisateur
     * @return Le DTO contenant les données pour l'export Excel
     */
    @Override
    @Transactional(readOnly = true)
    public PortefeuilleClientCCPMExcelDTO genererRapportPortefeuilleClientMPourExcel(
            Long codeBureau, String etatCompte, String username) {

        log.info("Generating Excel report for Portfolio M CCP for bureau: {}, etatCompte: {}",
                codeBureau, etatCompte);
        Objects.requireNonNull(codeBureau, "Code bureau cannot be null");

        try {
            // Validation de l'état du compte si fourni
            String etatCompteFiltre = null;
            if (etatCompte != null && !etatCompte.trim().isEmpty()) {
                etatCompteFiltre = validateEtatCompte(etatCompte);
            }

            // Récupérer les informations du bureau
            BureauPosteCCP bureauPoste = getBureauPoste(codeBureau);

            // Récupérer toutes les données sans pagination
            List<CompteCCP> comptes = compteCCPRepository.findPortefeuilleClientsByBureauWithFilters(
                            codeBureau, etatCompteFiltre, null, Pageable.unpaged())
                    .getContent();

            // Récupérer les statistiques
            PortefeuilleStats stats = compteCCPRepository.calculerStatistiquesPortefeuilleDetail(
                    codeBureau, etatCompteFiltre, null);

            // Mapper les données
            List<PortefeuilleClientCCPDetailDTO> comptesDTO = comptes.stream()
                    .map(rapportCCPMapper::toDetailDTO)
                    .collect(Collectors.toList());

            // Créer le DTO pour l'export Excel
            PortefeuilleClientCCPMExcelDTO excelDTO = new PortefeuilleClientCCPMExcelDTO();
            excelDTO.setTitreRapport("ETAT PORTE FEUILLE CLIENT M CCP");
            excelDTO.setDateEdition(LocalDateTime.now());
            excelDTO.setNumeroPage("1");
            excelDTO.setCodburpo(codeBureau);
            excelDTO.setDesburpo(bureauPoste.getDesignation());
            excelDTO.setComptes(comptesDTO);
            excelDTO.setNombreTotalComptes(stats.getNombreTotalComptes());
            excelDTO.setEncoursTotalComptes(stats.getEncoursTotalComptes());
            excelDTO.setTotalSoldeOpposition(stats.getTotalSoldeOpposition());
            excelDTO.setTotalSoldeTaxe(stats.getTotalSoldeTaxe());
            excelDTO.setTotalSoldeDebitOperations(stats.getTotalSoldeDebitOperations());
            excelDTO.setTotalSoldeCreditOperations(stats.getTotalSoldeCreditOperations());
            excelDTO.setTotalSoldeOperationsPeriode(stats.getTotalSoldeOperationsPeriode());
            excelDTO.setTotalSoldeCertifie(stats.getTotalSoldeCertifie());
            excelDTO.setUtilisateur(username);

            return excelDTO;

        } catch (Exception e) {
            log.error("Error generating Excel report for Portfolio M CCP for bureau: {}", codeBureau, e);
            throw new RuntimeException("Failed to generate Excel report for Portfolio M CCP", e);
        }
    }
}
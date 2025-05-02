package com.albaridbank.edition.service.impl;

import com.albaridbank.edition.dto.base.CompteCCPDetailDTO;
import com.albaridbank.edition.dto.rapport.CompteMouvementVeilleDTO;
import com.albaridbank.edition.dto.rapport.NbrTotalEncoursCCPDTO;
import com.albaridbank.edition.dto.rapport.PortefeuilleClientCCPDTO;
import com.albaridbank.edition.dto.rapport.PortefeuilleClientCCP_Top100_DTO;
import com.albaridbank.edition.model.ccp.CompteCCP;
import com.albaridbank.edition.repositorys.ccp.CompteCCPRepository;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

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
    private final CompteCCPRepository compteCCPRepository;
    private final RapportCCPMapper rapportCCPMapper;
    private final CompteCCPMapper compteCCPMapper;


    @Override
    public CompteMouvementVeilleDTO genererRapportMouvementVeille(Long codeBureau, Integer joursAvant, BigDecimal montantMinimum) {
        return null;
    }

    @Override
    public NbrTotalEncoursCCPDTO genererRapportEncoursGlobal(Long codeBureau) { return null; }

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

            // Calculate the total balance of the accounts on this page
            BigDecimal sommeSolde = comptes.stream()
                    .map(CompteCCP::getSoldeCourant)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // Create the report DTO using the mapper
            PortefeuilleClientCCPDTO rapport = rapportCCPMapper.creerRapportPortefeuilleClient(
                    codeBureau,
                    "Agence " + codeBureau,
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
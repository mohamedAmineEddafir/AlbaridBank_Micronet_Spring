package com.albaridbank.BankinApp.service.impl;

import com.albaridbank.BankinApp.dtos.CompteDTO;
import com.albaridbank.BankinApp.mappers.CompteMapper;
import com.albaridbank.BankinApp.models.Compte;
import com.albaridbank.BankinApp.repositorys.CompteRepository;
import com.albaridbank.BankinApp.service.interfaces.CompteService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CompteServiceImpl implements CompteService {
    private final CompteRepository compteRepository;
    private final CompteMapper compteMapper;

    /**
     * Retrieves all accounts with pagination
     *
     * @param pageable the pagination information
     * @return a page of account DTOs
     */
    @Override
    public Page<CompteDTO> getAllComptes(Pageable pageable) {
        log.info("Getting all comptes with pagination: {}", pageable);

        // Validate input
        Objects.requireNonNull(pageable, "Pageable cannot be null");

        try {
            return compteRepository.findAll(pageable)
                    .map(compteMapper::toDto);
        } catch (Exception ex) {
            log.error("Error retrieving all comptes: {}", ex.getMessage());
            throw new RuntimeException("Failed to retrieve comptes", ex);
        }
    }

    /**
     * Retrieves an account by its ID
     *
     * @param compteId the account ID
     * @return the account DTO
     * @throws EntityNotFoundException if the account is not found
     */
    @Override
    public CompteDTO getComptesByIdenComp(BigDecimal compteId) {
        log.info("Getting compte by idencomp: {}", compteId);

        Objects.requireNonNull(compteId, "Compte ID cannot be null");

        try {
            return compteRepository.findById(compteId)
                    .map(compteMapper::toDto)
                    .orElseThrow(() -> new EntityNotFoundException("Compte not found with ID: " + compteId));
        } catch (EntityNotFoundException ex) {
            // Rethrow EntityNotFoundException as is
            throw ex;
        } catch (Exception ex) {
            log.error("Error retrieving compte with ID {}: {}", compteId, ex.getMessage());
            throw new RuntimeException("Failed to retrieve compte", ex);
        }
    }

    /**
     * Retrieves accounts by category code
     *
     * @param categoryCode the category code
     * @return a list of account DTOs
     */
    @Override
    public List<CompteDTO> getComptesByCategory(BigDecimal categoryCode) {
        log.info("Getting compte by Category Code: {}", categoryCode);

        Objects.requireNonNull(categoryCode, "Category code cannot be null");

        try {
            List<Compte> comptes = compteRepository.findCompteByCodcatcp(categoryCode);

            if (comptes.isEmpty()) {
                log.info("No comptes found for category code: {}", categoryCode);
                return Collections.emptyList();
            }

            return compteMapper.toDtoList(comptes);
        } catch (Exception ex) {
            log.error("Error retrieving comptes for category {}: {}", categoryCode, ex.getMessage());
            throw new RuntimeException("Failed to retrieve comptes by category", ex);
        }
    }

    /**
     * Retrieves accounts opened after the specified date
     *
     * @param openingDate the opening date
     * @return a list of account DTOs
     */
    @Override
    public List<CompteDTO> getComptesByDateOuverture(LocalDate openingDate) {
        log.info("Getting compte by Date Ouverture: {}", openingDate);

        Objects.requireNonNull(openingDate, "Opening date cannot be null");

        try {
            List<Compte> comptes = compteRepository.findByDateouveGreaterThanEqual(openingDate);

            if (comptes.isEmpty()) {
                log.info("No comptes found with opening date after: {}", openingDate);
                return Collections.emptyList();
            }

            return compteMapper.toDtoList(comptes);
        } catch (Exception ex) {
            log.error("Error retrieving comptes with opening date after {}: {}", openingDate, ex.getMessage());
            throw new RuntimeException("Failed to retrieve comptes by opening date", ex);
        }
    }
}
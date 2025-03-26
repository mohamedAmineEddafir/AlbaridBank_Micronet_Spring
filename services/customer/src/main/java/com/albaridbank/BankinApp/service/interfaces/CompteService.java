package com.albaridbank.BankinApp.service.interfaces;

import com.albaridbank.BankinApp.dtos.CompteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Service interface for managing account-related operations.
 * Provides methods to perform CRUD operations on accounts.
 *
 * @author Mohamed Amine Eddafir
 */
public interface CompteService {

    /**
     * Retrieves all accounts with pagination support.
     *
     * @param pageable the pagination information
     * @return a paginated list of CompteDTO objects representing all accounts
     */
    Page<CompteDTO> getAllComptes(Pageable pageable);

    /**
     * Retrieves an account by its ID.
     *
     * @param compteId the ID of the account to retrieve
     * @return a CompteDTO object representing the account
     */
    CompteDTO getComptesByIdenComp(BigDecimal compteId);

    /**
     * Retrieves accounts by their category code.
     *
     * @param categoryCode the category code of the accounts to retrieve
     * @return a list of CompteDTO objects representing the accounts with the specified category code
     */
    List<CompteDTO> getComptesByCategory(BigDecimal categoryCode);

    /**
     * Retrieves accounts by their opening date.
     *
     * @param openingDate the opening date of the accounts to retrieve
     * @return a list of CompteDTO objects representing the accounts with the specified opening date
     */
    List<CompteDTO> getComptesByDateOuverture(LocalDate openingDate);
}
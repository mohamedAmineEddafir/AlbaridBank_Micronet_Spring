package com.albaridbank.BankinApp.controller;

import com.albaridbank.BankinApp.dtos.CompteDTO;
import com.albaridbank.BankinApp.service.interfaces.CompteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * REST controller for managing accounts.
 * Provides endpoints to perform CRUD operations on accounts.
 * This class is annotated with @RestController to indicate it's a RESTful web service controller.
 * The @RequestMapping annotation maps HTTP requests to handler methods of the controller.
 * The @RequiredArgsConstructor annotation generates a constructor with required arguments.
 * The @Slf4j annotation adds a logger to the class.
 *
 * @author Mohamed Amine Eddafir
 */
@RestController
@RequestMapping("/api/v1/compte")
@RequiredArgsConstructor
@Slf4j
public class CompteController {
    private final CompteService compteService;

    /**
     * Retrieves all accounts with pagination support.
     *
     * @param page the page number to retrieve (default is 0)
     * @param size the number of records per page (default is 10)
     * @param sort the field to sort by (default is "idencomp")
     * @return a ResponseEntity containing a paginated list of CompteDTO objects
     */
    @GetMapping
    public ResponseEntity<Page<CompteDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idencomp") String sort) {
        log.info("Getting all comptes with pagination: page={}, size={}, sort={}", page, size, sort);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return ResponseEntity.ok(compteService.getAllComptes(pageable));
    }

    /**
     * Retrieves an account by its ID.
     *
     * @param compteId the ID of the account to retrieve
     * @return a ResponseEntity containing the CompteDTO object
     */
    @GetMapping("/{compteId}")
    public ResponseEntity<CompteDTO> findCompteByIdencomp(@PathVariable BigDecimal compteId) {
        log.info("Getting compte by idencomp: {}", compteId);
        return ResponseEntity.ok(compteService.getComptesByIdenComp(compteId));
    }

    /**
     * Retrieves accounts by their category code.
     *
     * @param categoryCode the category code of the accounts to retrieve
     * @return a list of CompteDTO objects representing the accounts with the specified category code
     */
    @GetMapping("/category/{categoryCode}")
    public List<CompteDTO> findCompteByCategoryCode(@PathVariable BigDecimal categoryCode) {
        log.info("Getting compte by Category Code : {} ", categoryCode);
        return compteService.getComptesByCategory(categoryCode);
    }

    /**
     * Retrieves accounts by their opening date.
     *
     * @param openingDate the opening date of the accounts to retrieve
     * @return a list of CompteDTO objects representing the accounts with the specified opening date
     */
    @GetMapping("/by-date")
    public List<CompteDTO> findCompteByDateOuverture(
            @RequestParam("opndate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate openingDate) {
        log.info("Getting compte by Opening Date : {} ", openingDate);
        return compteService.getComptesByDateOuverture(openingDate);
    }

}

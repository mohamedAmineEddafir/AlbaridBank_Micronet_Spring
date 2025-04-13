package com.albaridbank.BankinApp.service.impl;

import com.albaridbank.BankinApp.dtos.ClientDTO;
import com.albaridbank.BankinApp.mappers.ClientMapper;
import com.albaridbank.BankinApp.repositorys.ClientRepository;
import com.albaridbank.BankinApp.service.interfaces.ClientService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of the ClientService interface.
 * Provides methods to manage clients.
 * This class is annotated with @Service to indicate it's a service component in Spring.
 * The @Transactional annotation ensures that methods are executed within a transaction context.
 * The @Slf4j annotation adds a logger to the class.
 * The @RequiredArgsConstructor annotation generates a constructor with required arguments.
 *
 * @author Mohamed Amine Eddafir
 */
@Service
@Transactional(readOnly = true) // By default, methods are read-only
@RequiredArgsConstructor         // Creates a constructor with final fields
@Slf4j                          // Adds an SLF4J logger
public class ClientServiceImpl implements ClientService {

    // Dependency injection via constructor (thanks to @RequiredArgsConstructor)
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    /**
     * Retrieves all clients with pagination.
     *
     * @param pageable the pagination information
     * @return a page of ClientDTO objects
     * @throws IllegalArgumentException if pageable is null
     */
    @Override
    public Page<ClientDTO> getAllCli(Pageable pageable) {
        log.info("Getting all clients with pagination: {}", pageable);

        Objects.requireNonNull(pageable, "Pageable cannot be null");

        try {
            return clientRepository.findAll(pageable).map(clientMapper::toDto);
        } catch (Exception ex) {
            log.error("Error retrieving all clients: {}", ex.getMessage());
            throw new RuntimeException("Failed to retrieve clients", ex);
        }
    }

    /**
     * Retrieves a client by their ID.
     *
     * @param clientId the ID of the client to retrieve
     * @return a ClientDTO object representing the client
     * @throws IllegalArgumentException if the clientId is null
     * @throws EntityNotFoundException if no client is found with the given ID
     */
    @Override
    public ClientDTO getClientById(BigDecimal clientId) {
        log.info("Getting client by id: {}", clientId);

        Objects.requireNonNull(clientId, "Client ID cannot be null");

        try {
            return clientMapper.toDto(clientRepository.findById(clientId)
                    .orElseThrow(() -> new EntityNotFoundException("Client not found with ID: " + clientId)));
        } catch (EntityNotFoundException ex) {
            // Rethrow EntityNotFoundException as is
            throw ex;
        } catch (Exception ex) {
            log.error("Error retrieving client with ID {}: {}", clientId, ex.getMessage());
            throw new RuntimeException("Failed to retrieve client", ex);
        }
    }

    /**
     * Retrieves clients by their status.
     *
     * @param status the status of the clients to retrieve
     * @return a list of ClientDTO objects representing the clients with the specified status
     * @throws IllegalArgumentException if status is null
     */
    @Override
    public List<ClientDTO> getClientByStatus(Integer status) {
        log.info("Getting clients by status: {}", status);

        Objects.requireNonNull(status, "Status cannot be null");

        try {
            List<ClientDTO> clients = clientMapper.toDtoList(clientRepository.findByCodetati(status));

            if (clients.isEmpty()) {
                log.warn("No clients found with status: {}", status);
            }

            return clients;
        } catch (Exception ex) {
            log.error("Error retrieving clients with status {}: {}", status, ex.getMessage());
            throw new RuntimeException("Failed to retrieve clients by status", ex);
        }
    }

    /**
     * Retrieves clients by their full name (last name and first name).
     *
     * @param lastName the last name of the clients to retrieve
     * @param firstName the first name of the clients to retrieve
     * @return a list of ClientDTO objects representing the clients with the specified full name
     * @throws IllegalArgumentException if lastName is null or empty
     * @throws EntityNotFoundException if no clients found with the specified name
     */
    @Override
    public List<ClientDTO> getClientsByFullName(String lastName, String firstName) {
        log.info("Getting clients by name: {} {}", lastName, firstName);

        if (lastName == null || lastName.trim().isEmpty()) {
            log.error("Last name cannot be null or empty");
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }

        try {
            List<ClientDTO> clients = clientMapper.toDtoList(clientRepository.findByNomraisAndPrenclie(lastName, firstName));

            if (clients.isEmpty()) {
                log.info("No clients found with name: {} {}", lastName, firstName);
                throw new EntityNotFoundException("No clients found with name: " + lastName + " " + firstName);
            }

            return clients;
        } catch (EntityNotFoundException ex) {
            // Rethrow EntityNotFoundException as is
            throw ex;
        } catch (Exception ex) {
            log.error("Error retrieving clients with name {} {}: {}", lastName, firstName, ex.getMessage());
            throw new RuntimeException("Failed to retrieve clients by name", ex);
        }
    }

    /*
     * Searches for clients by a search term.
     *
     * @param term the search term to use
     * @return a list of ClientDTO objects representing the clients that match the search term
     * @throws IllegalArgumentException if the search term is null or empty

    @Override
    public List<ClientDTO> searchClients(String term) {
        log.info("Searching clients with term: {}", term);

        if (term == null || term.trim().isEmpty()) {
            log.error("Search term cannot be empty");
            throw new IllegalArgumentException("Search term cannot be empty");
        }

        try {
            String searchTerm = "%" + term.trim() + "%";
            List<ClientDTO> clients = clientMapper.toDtoList(
                    clientRepository.findByNomraisContainingOrPrenclieContaining(searchTerm, searchTerm));

            if (clients.isEmpty()) {
                log.info("No clients found matching search term: {}", term);
            }

            return clients;
        } catch (Exception ex) {
            log.error("Error searching clients with term {}: {}", term, ex.getMessage());
            throw new RuntimeException("Failed to search clients", ex);
        }
    }
    **/
}
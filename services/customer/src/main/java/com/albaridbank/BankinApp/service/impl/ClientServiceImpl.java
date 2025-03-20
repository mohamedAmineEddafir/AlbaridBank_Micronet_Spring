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
     * Retrieves all clients.
     *
     * @return a list of ClientDTO objects representing all clients
     */
    @Override
    public Page<ClientDTO> getAllClients(Pageable pageable) {
        log.info("Getting all clients with pagination: {}", pageable);
        return clientRepository.findAll(pageable).map(clientMapper::toDto);
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
        if(clientId == null) {
            throw new IllegalArgumentException("Client ID is required");
        }
        log.info("Getting client by id: {}", clientId);
        return clientMapper.toDto(clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with ID: " + clientId)));
    }

    /**
     * Retrieves clients by their status.
     *
     * @param status the status of the clients to retrieve
     * @return a list of ClientDTO objects representing the clients with the specified status
     */
    @Override
    public List<ClientDTO> getClientByStatus(Integer status) {
        log.info("Getting clients by status: {}", status);
        return clientMapper.toDtoList(clientRepository.getClientsByStatus(status));
    }

    /**
     * Retrieves clients by their full name (last name and first name).
     *
     * @param lastName the last name of the clients to retrieve
     * @param firstName the first name of the clients to retrieve
     * @return a list of ClientDTO objects representing the clients with the specified full name
     */
    @Override
    public List<ClientDTO> getClientsByFullName(String lastName, String firstName) {
        log.info("Getting clients by name: {} {}", lastName, firstName);
        return clientMapper.toDtoList(clientRepository.findByNomraisAndPrenclie(lastName, firstName));
    }

    /*
     * Searches for clients by a search term.
     *
     * @param term the search term to use
     * @return a list of ClientDTO objects representing the clients that match the search term
     * @throws IllegalArgumentException if the search term is null or empty
     * @Override
     * public List<ClientDTO> searchClients(String term) {
     *  if (term == null || term.trim().isEmpty()) {
     *      throw new IllegalArgumentException("Search term cannot be empty");
     *  }
     *  log.info("Searching clients with term: {}", term);
     *  return clientMapper.toDtoList(clientRepository.findByNomraisContainingOrPrenclieContaining(term, term));
     * }
     **/
}
package com.albaridbank.BankinApp.service.interfaces;

import com.albaridbank.BankinApp.dtos.ClientDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service interface for managing client-related operations.
 * Provides methods to perform CRUD operations on clients.
 *
 * @author Mohamed Amine Eddafir
 */
public interface ClientService {

    /**
     * Retrieves all clients.
     *
     * @return a list of ClientDTO objects representing all clients
     */
    Page<ClientDTO> getAllClients(Pageable pageable);

    /**
     * Retrieves a client by their ID.
     *
     * @param clientId the ID of the client to retrieve
     * @return a ClientDTO object representing the client
     */
    ClientDTO getClientById(BigDecimal clientId);

    /**
     * Retrieves clients by their status.
     *
     * @param status the status of the clients to retrieve
     * @return a list of ClientDTO objects representing the clients with the specified status
     */
    List<ClientDTO> getClientByStatus(Integer status);

    /**
     * Retrieves clients by their full name (last name and first name).
     *
     * @param lastName the last name of the clients to retrieve
     * @param firstName the first name of the clients to retrieve
     * @return a list of ClientDTO objects representing the clients with the specified full name
     */
     List<ClientDTO> getClientsByFullName(String lastName, String firstName);


}
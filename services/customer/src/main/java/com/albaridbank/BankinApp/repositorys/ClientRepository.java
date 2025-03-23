package com.albaridbank.BankinApp.repositorys;

import com.albaridbank.BankinApp.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, BigDecimal>, JpaSpecificationExecutor<Client> {

    /**
     * Retrieves a list of clients by their status.
     *
     * @param codetati the status of the clients to retrieve
     * @return a list of clients with the specified status
     */
    List<Client> findByCodetati(Integer codetati);

/**
     * Retrieves clients by their full name (last name and first name).
     *
     * @param nomrais the last name of the clients to retrieve
     * @param prenclie the first name of the clients to retrieve
     * @return a list of clients with the specified full name
     */
    List<Client> findByNomraisAndPrenclie(String nomrais, String prenclie);

    /*
     * Searches for clients by a partial match on their last name or first name.
     *
     * @param nom the partial last name to search for
     * @param prenom the partial first name to search for
     * @return a list of clients that match the partial last name or first name
     * List<Client> findByNomraisContainingOrPrenclieContaining(String nom, String prenom);
     **/
}
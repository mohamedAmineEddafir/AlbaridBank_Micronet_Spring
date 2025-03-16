package com.albaridbank.BankinApp.repositorys;

import com.albaridbank.BankinApp.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, BigDecimal>, JpaSpecificationExecutor<Client> {
    // Find Client by Status
    List<Client> findByStatclie(Integer statclie);

    // Find Client by id
    Optional<Client> findByIdenclie(BigDecimal idenclie);

    // Find Client By Category
    List<Client> findByCateclie(Integer cateclie);


}

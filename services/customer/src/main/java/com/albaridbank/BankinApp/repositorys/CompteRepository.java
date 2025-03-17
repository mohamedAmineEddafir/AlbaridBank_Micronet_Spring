package com.albaridbank.BankinApp.repositorys;

import com.albaridbank.BankinApp.models.Client;
import com.albaridbank.BankinApp.models.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface CompteRepository extends JpaRepository<Compte, BigDecimal>, JpaSpecificationExecutor<Compte> {
    // Find accounts by client
    List<Compte> findByClient(Client client);

    // Find accounts by category and subcategory
    List<Compte> findByCodcatcpAndCodscatcp(BigDecimal codcatcp, BigDecimal codscatcp);

    // Find accounts opened after a specific date
    List<Compte> findByDateouveAfter(LocalDate dateouve);

    // Find accounts by branch code
    List<Compte> findByCodebpcpt(BigDecimal codebpcpt);
}

package com.albaridbank.edition.repositorys.ccp.projectionCCPRepo;

import java.math.BigDecimal;

public interface PortefeuilleStats {
    Long getNombreTotalComptes();
    BigDecimal getEncoursTotalComptes();
    BigDecimal getTotalSoldeOpposition();
    BigDecimal getTotalSoldeTaxe();
    BigDecimal getTotalSoldeDebitOperations();
    BigDecimal getTotalSoldeCreditOperations();
    BigDecimal getTotalSoldeOperationsPeriode();
    BigDecimal getTotalSoldeCertifie();
}
package com.albaridbank.edition.repositorys.ccp.projectionCCPRepo;

import java.math.BigDecimal;

public interface CompteStats {
    Long getCodburpo();            // Correspond à b.codeBureau as codburpo
    String getDesburpo();          // Correspond à b.designation as desburpo
    Long getNombreTotalComptes();  // Correspond à COUNT(c) as nombreTotalComptes
    BigDecimal getTotalEncours();  // Correspond à SUM(c.soldeCourant) as totalEncours
}

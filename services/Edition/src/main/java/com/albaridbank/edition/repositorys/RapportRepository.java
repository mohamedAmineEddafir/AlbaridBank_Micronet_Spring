package com.albaridbank.edition.repositorys;

import com.albaridbank.edition.model.Rapport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RapportRepository extends JpaRepository<Rapport, Long> {
}

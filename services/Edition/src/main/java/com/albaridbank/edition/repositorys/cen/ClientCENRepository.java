package com.albaridbank.edition.repositorys.cen;

import com.albaridbank.edition.model.cen.ClientCEN;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientCENRepository extends JpaRepository<ClientCEN, Long> {
}

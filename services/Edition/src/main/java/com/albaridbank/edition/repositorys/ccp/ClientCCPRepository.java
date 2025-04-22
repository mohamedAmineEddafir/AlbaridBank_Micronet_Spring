package com.albaridbank.edition.repositorys.ccp;

import com.albaridbank.edition.model.ccp.ClientCCP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientCCPRepository extends JpaRepository<ClientCCP, Long> {
}

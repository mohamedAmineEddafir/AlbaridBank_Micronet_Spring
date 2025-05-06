package com.albaridbank.edition.repositorys.ccp;

import com.albaridbank.edition.model.ccp.TypeOperationCCP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeOperationCCPRepository extends JpaRepository<TypeOperationCCP, Long> {
}

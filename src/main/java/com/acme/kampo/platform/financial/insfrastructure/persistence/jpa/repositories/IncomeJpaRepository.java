package com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.repositories;

import com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.entities.IncomePersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository for income persistence entities.
 */
@Repository
public interface IncomeJpaRepository extends JpaRepository<IncomePersistenceEntity, Long> {

    @Query("select i from IncomePersistenceEntity i where i.fundoId = :fundoId")
    List<IncomePersistenceEntity> findByFundoId(@Param("fundoId") Long fundoId);
}

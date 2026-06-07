package com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.repositories;


import com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.entities.ExpensePersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository for expense persistence entities.
 */
@Repository
public interface ExpenseJpaRepository extends JpaRepository<ExpensePersistenceEntity, Long> {

    @Query("select e from ExpensePersistenceEntity e where e.fundoId = :fundoId")
    List<ExpensePersistenceEntity> findByFundoId(@Param("fundoId") Long fundoId);

    @Query("select e from ExpensePersistenceEntity e where e.type = :type")
    List<ExpensePersistenceEntity> findByType(@Param("type") com.acme.kampo.platform.financial.domain.model.enums.ExpenseType type);
}

package com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.repositories;

import com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.entities.ExpenseCategoryPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for expense category persistence entities.
 */
@Repository
public interface ExpenseCategoryJpaRepository extends JpaRepository<ExpenseCategoryPersistenceEntity, Long> {

    @Query("select count(c) from ExpenseCategoryPersistenceEntity c where c.name = :name")
    long countByName(@Param("name") String name);
}

package com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.repositories;


import com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.entities.SalePersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository for sale persistence entities.
 */
@Repository
public interface SaleJpaRepository extends JpaRepository<SalePersistenceEntity, Long> {

    @Query("select s from SalePersistenceEntity s where s.fundoId = :fundoId")
    List<SalePersistenceEntity> findByFundoId(@Param("fundoId") Long fundoId);

    @Query("select s from SalePersistenceEntity s where s.cropName = :cropName")
    List<SalePersistenceEntity> findByCropName(@Param("cropName") String cropName);
}

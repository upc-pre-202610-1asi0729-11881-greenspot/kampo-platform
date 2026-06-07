package com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.repositories;


import com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.entities.ProfitabilityPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data repository for profitability persistence entities.
 */
@Repository
public interface ProfitabilityJpaRepository extends JpaRepository<ProfitabilityPersistenceEntity, Long> {

    @Query("select p from ProfitabilityPersistenceEntity p " +
            "where p.fundoId = :fundoId and p.seasonId = :seasonId " +
            "order by p.calculatedAt desc")
    Optional<ProfitabilityPersistenceEntity> findTopByFundoIdAndSeasonId(
            @Param("fundoId") Long fundoId, @Param("seasonId") Long seasonId);

    @Query("select p from ProfitabilityPersistenceEntity p " +
            "where p.fundoId = :fundoId " +
            "order by p.calculatedAt desc")
    List<ProfitabilityPersistenceEntity> findHistoryByFundoId(@Param("fundoId") Long fundoId);
}

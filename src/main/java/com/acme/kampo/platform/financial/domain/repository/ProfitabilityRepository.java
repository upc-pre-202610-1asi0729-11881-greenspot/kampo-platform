package com.acme.kampo.platform.financial.domain.repository;


import com.acme.kampo.platform.financial.domain.model.aggregates.Profitability;
import com.acme.kampo.platform.financial.domain.model.valueObjects.FundoId;
import com.acme.kampo.platform.financial.domain.model.valueObjects.ProfitabilityId;
import com.acme.kampo.platform.financial.domain.model.valueObjects.SeasonId;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository contract for the {@link Profitability} aggregate.
 *
 * <p>This interface exists because {@link Profitability} is now an aggregate root
 * with its own identity and persistence lifecycle — unlike the original design
 * where it was a transient value object calculated on the fly.</p>
 *
 * <p>Lives in the domain layer — no coupling to JPA, Spring Data, or any
 * infrastructure concern.</p>
 */
public interface ProfitabilityRepository {

    /**
     * Persists a newly calculated Profitability aggregate.
     *
     * @param profitability the aggregate to persist
     * @return the persisted aggregate with its assigned identity
     */
    Profitability save(Profitability profitability);

    /**
     * Finds a Profitability result by its typed identity.
     *
     * @param id the profitability identity
     * @return an {@link Optional} containing the aggregate, or empty if not found
     */
    Optional<Profitability> findById(ProfitabilityId id);

    /**
     * Finds the most recent Profitability result for a given fundo and season.
     * Returns the latest calculation when multiple exist for the same fundo/season pair.
     *
     * @param fundoId  the fundo identity
     * @param seasonId the season identity
     * @return an {@link Optional} containing the profitability, or empty if not calculated yet
     */
    Optional<Profitability> findByFundoIdAndSeasonId(FundoId fundoId, SeasonId seasonId);

    /**
     * Returns the full calculation history for a given fundo, ordered by
     * {@code calculatedAt} descending (most recent first).
     *
     * @param fundoId the fundo identity
     * @return list of profitability results, possibly empty
     */
    List<Profitability> findHistoryByFundoId(FundoId fundoId);
}
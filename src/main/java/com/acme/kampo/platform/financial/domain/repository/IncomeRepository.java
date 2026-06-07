package com.acme.kampo.platform.financial.domain.repository;


import com.acme.kampo.platform.financial.domain.model.aggregates.Income;
import com.acme.kampo.platform.financial.domain.model.valueObjects.FundoId;
import com.acme.kampo.platform.financial.domain.model.valueObjects.IncomeId;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository contract for the {@link Income} aggregate.
 *
 * <p>Lives in the domain layer — no coupling to JPA, Spring Data, or any
 * infrastructure concern.</p>
 */
public interface IncomeRepository {

    /**
     * Persists a new or updated Income aggregate.
     *
     * @param income the aggregate to persist
     * @return the persisted aggregate with its assigned identity
     */
    Income save(Income income);

    /**
     * Finds an Income by its typed identity.
     *
     * @param id the income identity
     * @return an {@link Optional} containing the aggregate, or empty if not found
     */
    Optional<Income> findById(IncomeId id);

    /**
     * Returns all income entries registered for a given fundo.
     *
     * @param fundoId the fundo identity
     * @return list of income entries for the fundo, possibly empty
     */
    List<Income> findByFundoId(FundoId fundoId);

    /**
     * Returns all income entries in the system.
     *
     * @return list of all income entries, possibly empty
     */
    List<Income> findAll();

    /**
     * Deletes an income entry by its typed identity.
     *
     * @param id the identity of the income to delete
     */
    void delete(IncomeId id);

    /**
     * Returns {@code true} if an income with the given identity exists.
     *
     * @param id the income identity to check
     */
    boolean existsById(IncomeId id);
}
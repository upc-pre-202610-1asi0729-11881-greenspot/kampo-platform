package com.acme.kampo.platform.financial.domain.repository;


import com.acme.kampo.platform.financial.domain.model.aggregates.Expense;
import com.acme.kampo.platform.financial.domain.model.enums.ExpenseType;
import com.acme.kampo.platform.financial.domain.model.valueObjects.ExpenseId;
import com.acme.kampo.platform.financial.domain.model.valueObjects.FundoId;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository contract for the {@link Expense} aggregate.
 *
 * <p>Lives in the domain layer — no coupling to JPA, Spring Data, or any
 * infrastructure concern. The infrastructure adapter implements this interface
 * via dependency inversion.</p>
 */
public interface ExpenseRepository {

    /**
     * Persists a new or updated Expense aggregate.
     * After saving, the returned instance has its {@link ExpenseId} populated.
     *
     * @param expense the aggregate to persist
     * @return the persisted aggregate with its assigned identity
     */
    Expense save(Expense expense);

    /**
     * Finds an Expense by its typed identity.
     *
     * @param id the expense identity
     * @return an {@link Optional} containing the aggregate, or empty if not found
     */
    Optional<Expense> findById(ExpenseId id);

    /**
     * Returns all expenses registered for a given fundo.
     *
     * @param fundoId the fundo identity
     * @return list of expenses for the fundo, possibly empty
     */
    List<Expense> findByFundoId(FundoId fundoId);

    /**
     * Returns all expenses of a given type across all fundos.
     *
     * @param type the expense type to filter by
     * @return list of matching expenses, possibly empty
     */
    List<Expense> findByType(ExpenseType type);

    /**
     * Returns all expenses in the system.
     *
     * @return list of all expenses, possibly empty
     */
    List<Expense> findAll();

    /**
     * Deletes an expense by its typed identity.
     *
     * @param id the identity of the expense to delete
     */
    void delete(ExpenseId id);

    /**
     * Returns {@code true} if an expense with the given identity exists.
     *
     * @param id the expense identity to check
     */
    boolean existsById(ExpenseId id);
}

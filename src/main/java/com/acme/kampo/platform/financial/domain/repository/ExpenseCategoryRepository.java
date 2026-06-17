package com.acme.kampo.platform.financial.domain.repository;


import com.acme.kampo.platform.financial.domain.model.entities.ExpenseCategory;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository contract for the {@link ExpenseCategory} entity.
 *
 * <p>{@code ExpenseCategory} is not an aggregate root, but it has an independent
 * persistence lifecycle — it is created, listed, and referenced by ID from
 * {@code Expense}. Hence it needs its own repository contract.</p>
 *
 * <p>Lives in the domain layer — no coupling to JPA, Spring Data, or any
 * infrastructure concern.</p>
 */
public interface ExpenseCategoryRepository {

    /**
     * Persists a new ExpenseCategory entity.
     *
     * @param category the entity to persist
     * @return the persisted entity with its assigned ID
     */
    ExpenseCategory save(ExpenseCategory category);

    /**
     * Finds an ExpenseCategory by its raw Long ID.
     *
     * @param id the category ID
     * @return an {@link Optional} containing the entity, or empty if not found
     */
    Optional<ExpenseCategory> findById(Long id);

    /**
     * Returns all expense categories in the system.
     *
     * @return list of all categories, possibly empty
     */
    List<ExpenseCategory> findAll();

    /**
     * Returns {@code true} if a category with the given name already exists.
     * Used to prevent duplicate category names.
     *
     * @param name the category name to check
     * @return {@code true} if the name is already taken
     */
    boolean existsByName(String name);

    boolean existsById(Long id);
}


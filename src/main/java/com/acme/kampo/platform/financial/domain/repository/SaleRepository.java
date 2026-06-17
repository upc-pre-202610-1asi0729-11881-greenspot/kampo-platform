package com.acme.kampo.platform.financial.domain.repository;


import com.acme.kampo.platform.financial.domain.model.aggregates.Sale;
import com.acme.kampo.platform.financial.domain.model.valueObjects.FundoId;
import com.acme.kampo.platform.financial.domain.model.valueObjects.SaleId;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository contract for the {@link Sale} aggregate.
 *
 * <p>Lives in the domain layer — no coupling to JPA, Spring Data, or any
 * infrastructure concern.</p>
 */
public interface SaleRepository {

    /**
     * Persists a new or updated Sale aggregate.
     *
     * @param sale the aggregate to persist
     * @return the persisted aggregate with its assigned identity
     */
    Sale save(Sale sale);

    /**
     * Finds a Sale by its typed identity.
     *
     * @param id the sale identity
     * @return an {@link Optional} containing the aggregate, or empty if not found
     */
    Optional<Sale> findById(SaleId id);

    /**
     * Returns all sales registered for a given fundo.
     *
     * @param fundoId the fundo identity
     * @return list of sales for the fundo, possibly empty
     */
    List<Sale> findByFundoId(FundoId fundoId);

    /**
     * Returns all sales for a given crop name across all fundos.
     *
     * @param cropName the crop name to filter by
     * @return list of matching sales, possibly empty
     */
    List<Sale> findByCropName(String cropName);

    /**
     * Deletes a sale by its typed identity.
     *
     * @param id the identity of the sale to delete
     */
    void delete(SaleId id);

    /**
     * Returns {@code true} if a sale with the given identity exists.
     *
     * @param id the sale identity to check
     */
    boolean existsById(SaleId id);
}
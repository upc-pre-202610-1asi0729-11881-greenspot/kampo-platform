package com.acme.kampo.platform.inventory.interfaces.events;


import com.acme.kampo.platform.inventory.domain.model.aggregates.Supplier;

/**
 * Integration event published by the {@code inventory} bounded context when a new
 * {@link Supplier} has been successfully created and persisted.
 *
 * <p>This is the <em>published language</em> of the {@code inventory} context.
 * Other bounded contexts must listen to this event rather than to the internal
 * {@link com.acme.kampo.platform.inventory.domain.model.events.SupplierCreatedEvent}.</p>
 *
 * @param supplierId the identity assigned to the newly created supplier
 * @param name       the supplier's name
 * @param email      the supplier's email address
 */
public record SupplierCreatedIntegrationEvent(
        Long supplierId,
        String name,
        String email) {

    /**
     * Convenience factory that extracts all fields from a saved {@link Supplier}.
     *
     * @param supplier the saved supplier (must already carry a non-null id)
     * @return a fully populated {@link SupplierCreatedIntegrationEvent}
     */
    public static SupplierCreatedIntegrationEvent from(Supplier supplier) {
        return new SupplierCreatedIntegrationEvent(
                supplier.getId().getValue(),
                supplier.getName(),
                supplier.getEmail());
    }
}

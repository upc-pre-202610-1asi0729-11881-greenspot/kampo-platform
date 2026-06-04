package com.acme.kampo.platform.inventory.application.commandservices;

import com.acme.kampo.platform.inventory.domain.model.aggregates.Supplier;
import com.acme.kampo.platform.inventory.domain.model.command.AddSupplierCommand;

/**
 * Application service contract for Supplier write operations.
 */
public interface SupplierCommandService {

    /**
     * Handles the registration of a new Supplier.
     *
     * @param command the command carrying the supplier data
     * @return the persisted {@link Supplier} aggregate with its assigned ID
     */
    Supplier handle(AddSupplierCommand command);
}

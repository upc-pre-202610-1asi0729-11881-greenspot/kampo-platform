package com.acme.kampo.platform.inventory.application.commandservices;

import com.acme.kampo.platform.inventory.domain.model.aggregates.Supplier;
import com.acme.kampo.platform.inventory.domain.model.command.AddSupplierCommand;
import com.acme.kampo.platform.shared.application.result.ApplicationError;
import com.acme.kampo.platform.shared.application.result.Result;

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
    Result<Supplier, ApplicationError> handle(AddSupplierCommand command);
}

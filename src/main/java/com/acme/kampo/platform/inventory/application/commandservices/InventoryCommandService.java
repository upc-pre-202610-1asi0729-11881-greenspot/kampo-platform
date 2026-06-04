package com.acme.kampo.platform.inventory.application.commandservices;

import com.acme.kampo.platform.inventory.domain.model.aggregates.Inventory;
import com.acme.kampo.platform.inventory.domain.model.command.CreateInventoryCommand;

/**
 * Application service contract for Inventory write operations.
 *
 * <p>Implementations live in {@code application.internal.commandservices} and are
 * never exposed directly to the interfaces layer — controllers depend on this
 * interface, not the concrete class.
 */
public interface InventoryCommandService {

    /**
     * Handles the creation of a new Inventory item.
     *
     * @param command the command carrying the inventory data
     * @return the persisted {@link Inventory} aggregate with its assigned ID
     */
    Inventory handle(CreateInventoryCommand command);
}

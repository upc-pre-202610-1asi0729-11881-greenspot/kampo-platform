package com.acme.kampo.platform.inventory.application.commandservices;

import com.acme.kampo.platform.inventory.domain.model.aggregates.OrderInput;
import com.acme.kampo.platform.inventory.domain.model.command.OrderInputCommand;
import com.acme.kampo.platform.inventory.domain.model.command.ReceiveInputCommand;

/**
 * Application service contract for OrderInput write operations.
 */
public interface OrderInputCommandService {

    /**
     * Handles placing a new input order.
     *
     * @param command the command with inventory, supplier and quantity data
     * @return the persisted {@link OrderInput} aggregate with its assigned ID
     */
    OrderInput handle(OrderInputCommand command);

    /**
     * Handles receiving a pending order, updating the related inventory stock.
     *
     * @param command the command with the order ID and arrival date-time
     * @return the updated {@link OrderInput} aggregate in RECEIVED status
     */
    OrderInput handle(ReceiveInputCommand command);
}
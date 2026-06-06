package com.acme.kampo.platform.inventory.application.commandservices;

import com.acme.kampo.platform.inventory.domain.model.aggregates.OrderInput;
import com.acme.kampo.platform.inventory.domain.model.command.OrderInputCommand;
import com.acme.kampo.platform.inventory.domain.model.command.ReceiveInputCommand;
import com.acme.kampo.platform.shared.application.result.ApplicationError;
import com.acme.kampo.platform.shared.application.result.Result;

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
    Result<OrderInput, ApplicationError> handle(OrderInputCommand command);

    /**
     * Handles receiving a pending order, updating the related inventory stock.
     *
     * @param command the command with the order ID and arrival date-time
     * @return the updated {@link OrderInput} aggregate in RECEIVED status
     */
    Result<OrderInput, ApplicationError> handle(ReceiveInputCommand command);
}
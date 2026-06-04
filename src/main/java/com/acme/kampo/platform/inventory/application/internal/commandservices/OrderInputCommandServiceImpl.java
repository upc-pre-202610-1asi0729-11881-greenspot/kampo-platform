package com.acme.kampo.platform.inventory.application.internal.commandservices;

import com.acme.kampo.platform.inventory.application.commandservices.OrderInputCommandService;
import com.acme.kampo.platform.inventory.domain.model.aggregates.OrderInput;
import com.acme.kampo.platform.inventory.domain.model.command.OrderInputCommand;
import com.acme.kampo.platform.inventory.domain.model.command.ReceiveInputCommand;
import com.acme.kampo.platform.inventory.domain.model.command.UpdateStockCommand;
import com.acme.kampo.platform.inventory.domain.repositories.InventoryRepository;
import com.acme.kampo.platform.inventory.domain.repositories.OrderInputRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Internal implementation of {@link OrderInputCommandService}.
 *
 * <p>{@code handle(ReceiveInputCommand)} coordinates two aggregates:
 * it marks the order as received AND increases the inventory stock.
 * Both changes happen in a single transaction.
 */
@Service
@Transactional
public class OrderInputCommandServiceImpl implements OrderInputCommandService {

    private final OrderInputRepository orderInputRepository;
    private final InventoryRepository inventoryRepository;

    public OrderInputCommandServiceImpl(
            OrderInputRepository orderInputRepository,
            InventoryRepository inventoryRepository) {
        this.orderInputRepository = orderInputRepository;
        this.inventoryRepository = inventoryRepository;
    }

    /**
     * Places a new input order.
     * Verifies that the referenced inventory item actually exists before creating the order.
     */
    @Override
    public OrderInput handle(OrderInputCommand command) {
        // Guard: inventory item must exist before placing an order for it
        inventoryRepository.findById(command.inventoryId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Inventory item not found with id: " + command.inventoryId()));

        var order = new OrderInput(command);
        return orderInputRepository.save(order);
    }

    /**
     * Receives a pending order:
     * <ol>
     *   <li>Loads and transitions the order to RECEIVED.</li>
     *   <li>Loads the related inventory and adds the ordered quantity to stock.</li>
     *   <li>Persists both aggregates — Spring's @Transactional ensures atomicity.</li>
     * </ol>
     */
    @Override
    public OrderInput handle(ReceiveInputCommand command) {
        var order = orderInputRepository.findById(command.orderId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "OrderInput not found with id: " + command.orderId()));

        // Transition the order aggregate
        order.receive(command);

        // Update the related inventory stock
        var inventory = inventoryRepository.findById(order.getInventoryId().getValue())
                .orElseThrow(() -> new IllegalStateException(
                        "Inventory item not found for order: " + command.orderId()));

        inventory.updateStock(new UpdateStockCommand(
                inventory.getId().getValue(),
                order.getQuantity()));

        inventoryRepository.save(inventory);
        return orderInputRepository.save(order);
    }
}

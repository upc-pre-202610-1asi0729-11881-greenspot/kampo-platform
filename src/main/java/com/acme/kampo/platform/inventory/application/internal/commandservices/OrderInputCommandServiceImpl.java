package com.acme.kampo.platform.inventory.application.internal.commandservices;

import com.acme.kampo.platform.inventory.application.commandservices.OrderInputCommandService;
import com.acme.kampo.platform.inventory.domain.model.aggregates.OrderInput;
import com.acme.kampo.platform.inventory.domain.model.command.OrderInputCommand;
import com.acme.kampo.platform.inventory.domain.model.command.ReceiveInputCommand;
import com.acme.kampo.platform.inventory.domain.model.command.UpdateStockCommand;
import com.acme.kampo.platform.inventory.domain.repositories.InventoryRepository;
import com.acme.kampo.platform.inventory.domain.repositories.OrderInputRepository;
import com.acme.kampo.platform.shared.application.result.ApplicationError;
import com.acme.kampo.platform.shared.application.result.Result;
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
    private final InventoryRepository  inventoryRepository;

    public OrderInputCommandServiceImpl(OrderInputRepository orderInputRepository,
                                        InventoryRepository inventoryRepository) {
        this.orderInputRepository = orderInputRepository;
        this.inventoryRepository  = inventoryRepository;
    }

    @Override
    public Result<OrderInput, ApplicationError> handle(OrderInputCommand command) {
        var inventoryOpt = inventoryRepository.findById(command.inventoryId());
        if (inventoryOpt.isEmpty()) {
            return Result.failure(ApplicationError.notFound(
                    "INVENTORY", String.valueOf(command.inventoryId())));
        }
        try {
            var order = orderInputRepository.save(new OrderInput(command));
            return Result.success(order);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected(
                    "OrderInputCommandService.handle(OrderInputCommand)",
                    e.getMessage()));
        }
    }

    @Override
    public Result<OrderInput, ApplicationError> handle(ReceiveInputCommand command) {
        var orderOpt = orderInputRepository.findById(command.orderId());
        if (orderOpt.isEmpty()) {
            return Result.failure(ApplicationError.notFound(
                    "ORDER_INPUT", String.valueOf(command.orderId())));
        }
        var order = orderOpt.get();
        if (order.getStatus().isTerminal()) {
            return Result.failure(ApplicationError.businessRuleViolation(
                    "ORDER_ALREADY_TERMINAL",
                    "Order %d is already in terminal state: %s"
                            .formatted(command.orderId(), order.getStatus())));
        }
        try {
            order.receive(command);
            var inventoryOpt = inventoryRepository.findById(order.getInventoryId().getValue());
            if (inventoryOpt.isEmpty()) {
                return Result.failure(ApplicationError.notFound(
                        "INVENTORY", String.valueOf(order.getInventoryId().getValue())));
            }
            var inventory = inventoryOpt.get();
            inventory.updateStock(new UpdateStockCommand(
                    inventory.getId().getValue(), order.getQuantity()));
            inventoryRepository.save(inventory);
            var savedOrder = orderInputRepository.save(order);
            return Result.success(savedOrder);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected(
                    "OrderInputCommandService.handle(ReceiveInputCommand)",
                    e.getMessage()));
        }
    }
}

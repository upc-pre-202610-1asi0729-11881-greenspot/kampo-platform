package com.acme.kampo.platform.inventory.application.internal.commandservices;

import com.acme.kampo.platform.inventory.application.commandservices.InventoryCommandService;
import com.acme.kampo.platform.inventory.domain.model.aggregates.Inventory;
import com.acme.kampo.platform.inventory.domain.model.command.CreateInventoryCommand;
import com.acme.kampo.platform.inventory.domain.repositories.InventoryRepository;
import com.acme.kampo.platform.shared.application.result.ApplicationError;
import com.acme.kampo.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Internal implementation of {@link InventoryCommandService}.
 *
 * <p>Orchestrates aggregate creation and persistence.
 * Domain logic stays in the aggregate — this class only coordinates.
 */
@Service
@Transactional
public class InventoryCommandServiceImpl implements InventoryCommandService {

    private final InventoryRepository inventoryRepository;

    public InventoryCommandServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public Result<Inventory, ApplicationError> handle(CreateInventoryCommand command) {
        if (inventoryRepository.existsByName(command.name())) {
            return Result.failure(ApplicationError.conflict(
                    "INVENTORY",
                    "An inventory item with name '%s' already exists".formatted(command.name())));
        }
        try {
            var inventory = inventoryRepository.save(new Inventory(command));
            return Result.success(inventory);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected(
                    "InventoryCommandService.handle",
                    e.getMessage()));
        }
    }
}

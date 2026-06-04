package com.acme.kampo.platform.inventory.application.internal.commandservices;

import com.acme.kampo.platform.inventory.application.commandservices.InventoryCommandService;
import com.acme.kampo.platform.inventory.domain.model.aggregates.Inventory;
import com.acme.kampo.platform.inventory.domain.model.command.CreateInventoryCommand;
import com.acme.kampo.platform.inventory.domain.repositories.InventoryRepository;
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

    /**
     * Creates a new Inventory item:
     * <ol>
     *   <li>Constructs the aggregate from the command (domain event registered inside).</li>
     *   <li>Persists via the domain repository — the adapter handles ID assignment
     *       and calls {@code reconstitute()} before returning.</li>
     * </ol>
     */
    @Override
    public Inventory handle(CreateInventoryCommand command) {
        var inventory = new Inventory(command);
        return inventoryRepository.save(inventory);
    }
}

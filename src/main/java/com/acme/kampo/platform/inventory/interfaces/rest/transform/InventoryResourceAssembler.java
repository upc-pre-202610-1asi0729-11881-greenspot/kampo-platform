package com.acme.kampo.platform.inventory.interfaces.rest.transform;

import com.acme.kampo.platform.inventory.domain.model.aggregates.Inventory;
import com.acme.kampo.platform.inventory.domain.model.command.CreateInventoryCommand;
import com.acme.kampo.platform.inventory.interfaces.rest.resources.CreateInventoryResource;
import com.acme.kampo.platform.inventory.interfaces.rest.resources.InventoryResource;

/**
 * Assembler that converts between the {@link Inventory} aggregate and its REST resources.
 * Static utility class — no state, no Spring bean needed.
 */
public class InventoryResourceAssembler {

    private InventoryResourceAssembler() {}

    /**
     * Converts an inbound {@link CreateInventoryResource} to a domain command.
     */
    public static CreateInventoryCommand toCommand(CreateInventoryResource resource) {
        return new CreateInventoryCommand(
                resource.name(),
                resource.quantity(),
                resource.unit(),
                resource.minStock()
        );
    }

    /**
     * Converts an {@link Inventory} aggregate to an outbound {@link InventoryResource}.
     */
    public static InventoryResource toResource(Inventory inventory) {
        return new InventoryResource(
                inventory.getId().getValue(),
                inventory.getName(),
                inventory.getQuantity(),
                inventory.getUnit(),
                inventory.getMinStock(),
                inventory.getStatus()
        );
    }
}

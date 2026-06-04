package com.acme.kampo.platform.inventory.interfaces.rest.transform;

import com.acme.kampo.platform.inventory.domain.model.aggregates.OrderInput;
import com.acme.kampo.platform.inventory.domain.model.command.OrderInputCommand;
import com.acme.kampo.platform.inventory.interfaces.rest.resources.CreateOrderInputResource;
import com.acme.kampo.platform.inventory.interfaces.rest.resources.OrderInputResource;

/**
 * Assembler that converts between the {@link OrderInput} aggregate and its REST resources.
 */
public class OrderInputResourceAssembler {

    private OrderInputResourceAssembler() {}

    public static OrderInputCommand toCommand(CreateOrderInputResource resource) {
        return new OrderInputCommand(
                resource.inventoryId(),
                resource.supplierId(),
                resource.quantity()
        );
    }

    public static OrderInputResource toResource(OrderInput order) {
        return new OrderInputResource(
                order.getId().getValue(),
                order.getInventoryId().getValue(),
                order.getSupplierId().getValue(),
                order.getQuantity(),
                order.getStatus(),
                order.getOrderedAt(),
                order.getReceivedAt()
        );
    }
}

package com.acme.kampo.platform.inventory.application.acl;

import com.acme.kampo.platform.inventory.application.commandservices.InventoryCommandService;
import com.acme.kampo.platform.inventory.application.commandservices.OrderInputCommandService;
import com.acme.kampo.platform.inventory.application.commandservices.SupplierCommandService;
import com.acme.kampo.platform.inventory.application.queryservices.InventoryQueryService;
import com.acme.kampo.platform.inventory.domain.model.command.AddSupplierCommand;
import com.acme.kampo.platform.inventory.domain.model.command.CreateInventoryCommand;
import com.acme.kampo.platform.inventory.domain.model.command.OrderInputCommand;
import com.acme.kampo.platform.inventory.domain.model.queries.GetAllInventoryQuery;
import com.acme.kampo.platform.inventory.interfaces.acl.InventoryContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of {@link InventoryContextFacade}.
 *
 * <p>Translates primitive inputs from other bounded contexts into domain
 * commands and queries, then returns only primitive results — never
 * domain aggregates or value objects from this context.
 */
@Service
public class InventoriesContextFacadeImpl implements InventoryContextFacade {

    private final InventoryCommandService inventoryCommandService;
    private final InventoryQueryService inventoryQueryService;
    private final SupplierCommandService supplierCommandService;
    private final OrderInputCommandService orderInputCommandService;

    public InventoriesContextFacadeImpl(
            InventoryCommandService inventoryCommandService,
            InventoryQueryService inventoryQueryService,
            SupplierCommandService supplierCommandService,
            OrderInputCommandService orderInputCommandService) {
        this.inventoryCommandService  = inventoryCommandService;
        this.inventoryQueryService    = inventoryQueryService;
        this.supplierCommandService   = supplierCommandService;
        this.orderInputCommandService = orderInputCommandService;
    }

    @Override
    public Long createInventory(String name, int quantity, String unit, int minStock) {
        var command   = new CreateInventoryCommand(name, quantity, unit, minStock);
        var inventory = inventoryCommandService.handle(command);
        return inventory.getId().getValue();
    }

    @Override
    public Long createSupplier(String name, String contact, String email) {
        var command  = new AddSupplierCommand(name, contact, email);
        var supplier = supplierCommandService.handle(command);
        return supplier.getId().getValue();
    }

    @Override
    public Long createOrderInput(Long inventoryId, Long supplierId, int quantity) {
        var command = new OrderInputCommand(inventoryId, supplierId, quantity);
        var order   = orderInputCommandService.handle(command);
        return order.getId().getValue();
    }

    @Override
    public Optional<Long> fetchInventoryIdByName(String name) {
        return inventoryQueryService.handle(new GetAllInventoryQuery())
                .stream()
                .filter(inv -> inv.getName().equalsIgnoreCase(name))
                .findFirst()
                .map(inv -> inv.getId().getValue());
    }
}

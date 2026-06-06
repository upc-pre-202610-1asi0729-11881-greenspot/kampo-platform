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
import com.acme.kampo.platform.shared.application.result.Result;
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

    private final InventoryCommandService  inventoryCommandService;
    private final InventoryQueryService    inventoryQueryService;
    private final SupplierCommandService   supplierCommandService;
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
        var result = inventoryCommandService.handle(
                new CreateInventoryCommand(name, quantity, unit, minStock));
        return switch (result) {
            case Result.Success<?, ?> s ->
                    ((com.acme.kampo.platform.inventory.domain.model.aggregates.Inventory) s.value())
                            .getId().getValue();
            case Result.Failure<?, ?> f ->
                    throw new IllegalStateException("Could not create inventory: " + f.error());
        };
    }

    @Override
    public Long createSupplier(String name, String contact, String email) {
        var result = supplierCommandService.handle(
                new AddSupplierCommand(name, contact, email));
        return switch (result) {
            case Result.Success<?, ?> s ->
                    ((com.acme.kampo.platform.inventory.domain.model.aggregates.Supplier) s.value())
                            .getId().getValue();
            case Result.Failure<?, ?> f ->
                    throw new IllegalStateException("Could not create supplier: " + f.error());
        };
    }

    @Override
    public Long createOrderInput(Long inventoryId, Long supplierId, int quantity) {
        var result = orderInputCommandService.handle(
                new OrderInputCommand(inventoryId, supplierId, quantity));
        return switch (result) {
            case Result.Success<?, ?> s ->
                    ((com.acme.kampo.platform.inventory.domain.model.aggregates.OrderInput) s.value())
                            .getId().getValue();
            case Result.Failure<?, ?> f ->
                    throw new IllegalStateException("Could not create order input: " + f.error());
        };
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

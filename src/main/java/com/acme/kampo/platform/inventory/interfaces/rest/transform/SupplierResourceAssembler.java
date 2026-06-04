package com.acme.kampo.platform.inventory.interfaces.rest.transform;

import com.acme.kampo.platform.inventory.domain.model.aggregates.Supplier;
import com.acme.kampo.platform.inventory.domain.model.command.AddSupplierCommand;
import com.acme.kampo.platform.inventory.interfaces.rest.resources.CreateSupplierResource;
import com.acme.kampo.platform.inventory.interfaces.rest.resources.SupplierResource;

/**
 * Assembler that converts between the {@link Supplier} aggregate and its REST resources.
 */
public class SupplierResourceAssembler {

    private SupplierResourceAssembler() {}

    public static AddSupplierCommand toCommand(CreateSupplierResource resource) {
        return new AddSupplierCommand(
                resource.name(),
                resource.contact(),
                resource.email()
        );
    }

    public static SupplierResource toResource(Supplier supplier) {
        return new SupplierResource(
                supplier.getId().getValue(),
                supplier.getName(),
                supplier.getContact(),
                supplier.getEmail()
        );
    }
}

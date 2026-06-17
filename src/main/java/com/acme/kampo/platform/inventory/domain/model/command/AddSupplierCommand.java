package com.acme.kampo.platform.inventory.domain.model.command;

/**
 * Command to register a new supplier.
 *
 * @param name    supplier's company or person name
 * @param contact phone number or contact reference
 * @param email   supplier's email address
 */
public record AddSupplierCommand(
        String name,
        String contact,
        String email
) {
    public AddSupplierCommand {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Supplier name must not be blank");
        if (contact == null || contact.isBlank())
            throw new IllegalArgumentException("Contact must not be blank");
        if (email == null || !email.contains("@"))
            throw new IllegalArgumentException("Email must be a valid address");
    }
}

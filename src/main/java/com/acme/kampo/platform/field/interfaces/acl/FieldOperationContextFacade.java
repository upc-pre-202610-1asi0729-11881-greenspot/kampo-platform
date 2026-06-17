package com.acme.kampo.platform.field.interfaces.acl;

import java.util.Optional;

/**
 * Anti-Corruption Layer facade exposing the FieldOperation bounded context
 * to other bounded contexts.
 *
 * <p>All methods work with primitive types — never with internal domain
 * aggregates or value objects from this context.</p>
 */
public interface FieldOperationContextFacade {

    /**
     * Finds the most recent field visit ID for a given field.
     *
     * @param fieldId the field identity
     * @return an {@link Optional} with the field visit ID, or empty if no visits exist
     */
    Optional<Long> fetchFieldVisitIdBy(Long fieldId);
}
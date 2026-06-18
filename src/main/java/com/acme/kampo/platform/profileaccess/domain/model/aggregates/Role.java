package com.acme.kampo.platform.profileaccess.domain.model.aggregates;

import com.acme.kampo.platform.profileaccess.domain.model.commands.CreateRoleCommand;
import com.acme.kampo.platform.profileaccess.domain.model.enums.RolePosition;
import com.acme.kampo.platform.profileaccess.domain.model.valueobjects.RoleId;
import com.acme.kampo.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

/**
 * Aggregate root representing a role within the platform.
 *
 * <p>Roles are assigned to users via {@link UserRole} and carry permissions
 * via {@link RolePermission}. Each role has a unique {@link RolePosition}.</p>
 *
 * <p>No domain event is published on creation — role management is a
 * configuration operation, not a business-significant event.</p>
 */
@Getter
public class Role extends AbstractDomainAggregateRoot<Role> {

    private RoleId       id;
    private RolePosition position;
    private String       description;

    /** Required by JPA proxy — do not use directly. */
    protected Role() {}

    /**
     * Reconstitution constructor.
     */
    public Role(Long id, RolePosition position, String description) {
        this.id          = RoleId.of(id);
        this.position    = position;
        this.description = description;
    }

    /**
     * Creation constructor.
     *
     * @param command the role creation command
     */
    public Role(CreateRoleCommand command) {
        this.position    = command.position();
        this.description = command.description();
    }

    public Role reconstitute(Long rawId) {
        this.id = RoleId.of(rawId);
        return this;
    }
}
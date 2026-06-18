package com.acme.kampo.platform.profileaccess.domain.model.aggregates;

import com.acme.kampo.platform.profileaccess.domain.model.commands.CreatePermissionCommand;
import com.acme.kampo.platform.profileaccess.domain.model.enums.PermissionCategory;
import com.acme.kampo.platform.profileaccess.domain.model.valueobjects.PermissionId;
import com.acme.kampo.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

/**
 * Aggregate root representing a permission within the platform.
 *
 * <p>Permissions are categorised by functional area ({@link PermissionCategory})
 * and assigned to roles via {@link RolePermission}.</p>
 */
@Getter
public class Permission extends AbstractDomainAggregateRoot<Permission> {

    private PermissionId       id;
    private PermissionCategory category;
    private String             description;

    /** Required by JPA proxy — do not use directly. */
    protected Permission() {}

    /**
     * Reconstitution constructor.
     */
    public Permission(Long id, PermissionCategory category, String description) {
        this.id          = PermissionId.of(id);
        this.category    = category;
        this.description = description;
    }

    /**
     * Creation constructor.
     *
     * @param command the permission creation command
     */
    public Permission(CreatePermissionCommand command) {
        this.category    = command.category();
        this.description = command.description();
    }

    public Permission reconstitute(Long rawId) {
        this.id = PermissionId.of(rawId);
        return this;
    }
}
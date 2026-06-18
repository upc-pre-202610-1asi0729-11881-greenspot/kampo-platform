package com.acme.kampo.platform.profileaccess.domain.model.aggregates;

import com.acme.kampo.platform.profileaccess.domain.model.commands.AssignPermissionCommand;
import com.acme.kampo.platform.profileaccess.domain.model.valueobjects.PermissionId;
import com.acme.kampo.platform.profileaccess.domain.model.valueobjects.RoleId;
import com.acme.kampo.platform.profileaccess.domain.model.valueobjects.RolePermissionId;
import com.acme.kampo.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

/**
 * Aggregate root representing the assignment of a permission to a role.
 *
 * <p>Acts as an explicit join aggregate between {@link Role} and {@link Permission},
 * referenced by their IDs.</p>
 */
@Getter
public class RolePermission extends AbstractDomainAggregateRoot<RolePermission> {

    private RolePermissionId id;
    private RoleId           roleId;
    private PermissionId     permissionId;

    /** Required by JPA proxy — do not use directly. */
    protected RolePermission() {}

    /**
     * Reconstitution constructor.
     */
    public RolePermission(Long id, Long roleId, Long permissionId) {
        this.id           = RolePermissionId.of(id);
        this.roleId       = RoleId.of(roleId);
        this.permissionId = PermissionId.of(permissionId);
    }

    /**
     * Creation constructor.
     *
     * @param command the permission assignment command
     */
    public RolePermission(AssignPermissionCommand command) {
        this.roleId       = RoleId.of(command.roleId());
        this.permissionId = PermissionId.of(command.permissionId());
    }

    public RolePermission reconstitute(Long rawId) {
        this.id = RolePermissionId.of(rawId);
        return this;
    }
}
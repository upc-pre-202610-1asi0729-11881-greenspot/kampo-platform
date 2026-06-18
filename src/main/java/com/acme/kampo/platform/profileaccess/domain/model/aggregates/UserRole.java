package com.acme.kampo.platform.profileaccess.domain.model.aggregates;

import com.acme.kampo.platform.profileaccess.domain.model.commands.AssignRoleCommand;
import com.acme.kampo.platform.profileaccess.domain.model.valueobjects.RoleId;
import com.acme.kampo.platform.profileaccess.domain.model.valueobjects.UserId;
import com.acme.kampo.platform.profileaccess.domain.model.valueobjects.UserRoleId;
import com.acme.kampo.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

/**
 * Aggregate root representing the assignment of a role to a user.
 *
 * <p>Acts as an explicit join aggregate between {@link User} and {@link Role},
 * referenced by their IDs. Having it as an aggregate (rather than a join table)
 * allows future extensions like assigning roles with an expiry date or a reason.</p>
 */
@Getter
public class UserRole extends AbstractDomainAggregateRoot<UserRole> {

    private UserRoleId id;
    private UserId     userId;
    private RoleId     roleId;

    /** Required by JPA proxy — do not use directly. */
    protected UserRole() {}

    /**
     * Reconstitution constructor.
     */
    public UserRole(Long id, Long userId, Long roleId) {
        this.id     = UserRoleId.of(id);
        this.userId = UserId.of(userId);
        this.roleId = RoleId.of(roleId);
    }

    /**
     * Creation constructor.
     *
     * @param command the role assignment command
     */
    public UserRole(AssignRoleCommand command) {
        this.userId = UserId.of(command.userId());
        this.roleId = RoleId.of(command.roleId());
    }

    public UserRole reconstitute(Long rawId) {
        this.id = UserRoleId.of(rawId);
        return this;
    }
}
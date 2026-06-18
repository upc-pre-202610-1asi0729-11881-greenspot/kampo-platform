package com.acme.kampo.platform.profileaccess.domain.model.enums;

/**
 * Represents the functional category of a permission within the platform.
 *
 * <p>Each category maps to a bounded context or a cross-cutting concern.
 * Permissions are assigned to roles via
 * {@link com.acme.kampo.platform.profileaccess.domain.model.aggregates.RolePermission}.</p>
 */
public enum PermissionCategory {

    /** Permissions related to user and role management. */
    USERS,

    /** Permissions related to financial reports and profitability. */
    REPORTS,

    /** Permissions related to alert rule configuration and alert management. */
    ALERTS,

    /** Permissions related to inventory management (suppliers, orders). */
    INVENTORY,

    /** Permissions related to financial operations (expenses, incomes, sales). */
    FINANCIAL;
}
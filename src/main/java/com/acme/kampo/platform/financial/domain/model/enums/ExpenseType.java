package com.acme.kampo.platform.financial.domain.model.enums;
/**
 * Classifies the nature of an expense within a fundo operation.
 *
 * <p>Used by the {@code Expense} aggregate to categorise costs and by
 * query services to filter or group expenses by type.</p>
 */
public enum ExpenseType {

    /** Wages, salaries, and contractor fees. */
    LABOR,

    /** Seeds, fertilisers, pesticides, and other agricultural inputs. */
    INPUT,

    /** Freight, logistics, and distribution costs. */
    TRANSPORT,

    /** Equipment repair, upkeep, and facility maintenance. */
    MAINTENANCE,

    /** Any expense that does not fit the categories above. */
    OTHER;

    /**
     * Returns {@code true} if this type represents a direct production cost
     * (LABOR or INPUT), which typically carries higher analytical weight.
     */
    public boolean isDirectProductionCost() {
        return this == LABOR || this == INPUT;
    }
}
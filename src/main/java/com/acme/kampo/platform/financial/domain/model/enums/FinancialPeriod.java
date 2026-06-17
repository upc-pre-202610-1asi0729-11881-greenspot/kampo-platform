package com.acme.kampo.platform.financial.domain.model.enums;

/**
 * Represents the granularity of a financial reporting or analysis period.
 *
 * <p>Used by profitability queries and reports to define the time window
 * over which income, expenses, and sales are aggregated.</p>
 */
public enum FinancialPeriod {

    /** Covers a single calendar month. */
    MONTHLY,

    /** Covers three consecutive months. */
    QUARTERLY,

    /** Covers a full agricultural growing season, which may not align with calendar months. */
    SEASONAL,

    /** Covers a full calendar or fiscal year. */
    ANNUAL;

    /**
     * Returns {@code true} if this period spans more than one month —
     * useful to decide whether to show monthly breakdowns in reports.
     */
    public boolean isMultiMonth() {
        return this != MONTHLY;
    }
}

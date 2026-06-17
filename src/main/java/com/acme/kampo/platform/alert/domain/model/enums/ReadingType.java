package com.acme.kampo.platform.alert.domain.model.enums;

/**
 * Represents the type of sensor reading that an {@link com.acme.kampo.platform.alert.domain.model.aggregates.AlertRule}
 * monitors.
 */
public enum ReadingType {

    /** Ambient or soil temperature in degrees Celsius. */
    TEMPERATURE,

    /** Relative humidity percentage. */
    HUMIDITY,

    /** Soil or water pH level. */
    PH;
}

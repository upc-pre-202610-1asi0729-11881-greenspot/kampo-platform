package com.acme.kampo.platform.organization.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

/**
 * Value object representing a geographic coordinate (latitude/longitude pair).
 *
 * <p>Immutable by design. Use {@code GeoLocation.of(...)} to construct instances.</p>
 *
 * <p>Latitude ranges from -90 (South Pole) to +90 (North Pole).
 * Longitude ranges from -180 (West) to +180 (East).</p>
 *
 * @param latitude  geographic latitude in decimal degrees
 * @param longitude geographic longitude in decimal degrees
 */
@Embeddable
public record GeoLocation(Double latitude, Double longitude) {

    /** Earth's mean radius in kilometres, used for the Haversine formula. */
    private static final double EARTH_RADIUS_KM = 6371.0;

    public GeoLocation {
        if (latitude == null)
            throw new IllegalArgumentException("latitude must not be null");
        if (longitude == null)
            throw new IllegalArgumentException("longitude must not be null");
        if (latitude < -90 || latitude > 90)
            throw new IllegalArgumentException(
                    "latitude must be between -90 and 90, got: " + latitude);
        if (longitude < -180 || longitude > 180)
            throw new IllegalArgumentException(
                    "longitude must be between -180 and 180, got: " + longitude);
    }

    /**
     * Primary factory method.
     *
     * @param latitude  decimal degrees, -90 to +90
     * @param longitude decimal degrees, -180 to +180
     * @return a new {@link GeoLocation} instance
     */
    public static GeoLocation of(Double latitude, Double longitude) {
        return new GeoLocation(latitude, longitude);
    }

    /**
     * Calculates the great-circle distance between this location and another
     * using the Haversine formula.
     *
     * @param other the other geographic location
     * @return distance in kilometres, always non-negative
     */
    public double distanceTo(GeoLocation other) {
        double lat1 = Math.toRadians(this.latitude);
        double lat2 = Math.toRadians(other.latitude);
        double dLat = Math.toRadians(other.latitude - this.latitude);
        double dLon = Math.toRadians(other.longitude - this.longitude);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_KM * c;
    }

    /**
     * Returns {@code true} if this location is within the given radius from another.
     *
     * @param other       the reference location
     * @param radiusKm    the maximum distance in kilometres
     * @return {@code true} if within range
     */
    public boolean isWithinRadius(GeoLocation other, double radiusKm) {
        return distanceTo(other) <= radiusKm;
    }

    @Override
    public String toString() {
        return "GeoLocation(lat=%.6f, lon=%.6f)".formatted(latitude, longitude);
    }
}
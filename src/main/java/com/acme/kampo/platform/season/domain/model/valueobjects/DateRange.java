package com.acme.kampo.platform.season.domain.model.valueobjects;

import java.time.LocalDate;

public record DateRange(LocalDate startAt, LocalDate endAt) {

    public DateRange{
        if(startAt == null)
            throw new IllegalArgumentException("Start date must not be null");
        if(endAt != null && endAt.isBefore(startAt))
            throw new IllegalArgumentException("End date must not be before start date");
    }

    public static DateRange of(LocalDate start, LocalDate end) {
        return new DateRange(start, end);
    }

    public LocalDate getStartAt() {
        return startAt;
    }

    public LocalDate getEndAt() {
        return endAt;
    }

    public boolean isValid() {
        return startAt != null;
    }

    public boolean overlapsWith(DateRange other) {
        if (other == null) return false;
        LocalDate thisEnd = this.endAt != null ? this.endAt : LocalDate.MAX;
        LocalDate otherEnd = other.endAt != null ? other.endAt : LocalDate.MAX;
        return !this.startAt.isAfter(otherEnd) && !other.startAt.isAfter(thisEnd);
    }


}

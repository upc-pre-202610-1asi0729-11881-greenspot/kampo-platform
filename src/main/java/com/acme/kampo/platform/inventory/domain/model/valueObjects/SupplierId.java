package com.acme.kampo.platform.inventory.domain.model.valueObjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record SupplierId(Long value) {

    public SupplierId{
        if ( value != null && value <=0){
            throw new IllegalArgumentException("InventoryId value must be positive, got: " + value);
        }
    }

    public static SupplierId of(Long value) {
        return new SupplierId(value);
    }
    public static SupplierId of(long value) {
        return new SupplierId(value);
    }
    public Long getValue() {
        return value;
    }
    @Override
    public String toString() {
        return "Supplier("+value+")";
    }
}

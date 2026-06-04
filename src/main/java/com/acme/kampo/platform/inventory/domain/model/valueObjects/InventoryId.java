package com.acme.kampo.platform.inventory.domain.model.valueObjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record InventoryId(Long value) {
    public InventoryId{
        if( value != null && value <=0){
            throw new IllegalArgumentException("InventoryId value must be positive, got: " + value);
        }
    }

    /**
     * Factory method — preferred way to create an InventoryId.
     *
     * @param value the underlying Long identifier
     * @return a new InventoryId instance
     */
    public static InventoryId of(Long value){
        return new InventoryId(value);
    }
    /**
     * Convenience factory for use in queries and mappers that work with primitives.
     */
    public static InventoryId of(long value){
        return new InventoryId(value);
    }

    public Long getValue(){
        return value;
    }

    @Override
    public String toString(){
        return "InventoryId(" + value + ")";
    }
}

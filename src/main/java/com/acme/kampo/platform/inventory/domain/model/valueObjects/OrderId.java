package com.acme.kampo.platform.inventory.domain.model.valueObjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record OrderId(Long value) {

    public OrderId{
        if( value != null && value <=0){
            throw new IllegalArgumentException("OrderId value must be positive, got: " + value);
        }
    }

    public static OrderId of (Long value){
        return new OrderId(value);
    }
    public static OrderId of (long value){
        return new OrderId(value);
    }
    public Long getValue(){
        return value;
    }
    @Override
    public String toString() {
        return "OrderId(" + value + ")";
    }
}

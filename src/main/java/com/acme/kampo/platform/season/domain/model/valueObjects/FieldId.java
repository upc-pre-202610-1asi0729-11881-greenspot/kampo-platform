package com.acme.kampo.platform.season.domain.model.valueObjects;

public record FieldId(Long value) {

    public FieldId{
        if(value == null || value <= 0)
            throw new IllegalArgumentException("FielId must be a positive number");
    }

    public static FieldId of(Long value){
        return new FieldId(value);
    }

    public Long getValue(){
        return value;
    }
}

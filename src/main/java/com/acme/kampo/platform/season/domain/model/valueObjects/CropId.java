package com.acme.kampo.platform.season.domain.model.valueObjects;

public record CropId(Long value) {

    public CropId{
        if (value == null || value <= 0)
            throw new IllegalArgumentException("CropId must be a positive number");
    }

    public static CropId of(Long value){
        return new CropId(value);
    }

    public Long getValue(){
        return value;
    }
}

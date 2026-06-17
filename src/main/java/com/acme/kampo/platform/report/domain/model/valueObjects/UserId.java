package com.acme.kampo.platform.report.domain.model.valueObjects;

public record UserId(Long value) {

    public UserId{
        if(value == null || value <= 0){
            throw new IllegalArgumentException("UserId must be positive");
        }
    }

    public static UserId of(Long value){
        return new UserId(value);
    }

    public Long getValue(){
        return value;
    }

}

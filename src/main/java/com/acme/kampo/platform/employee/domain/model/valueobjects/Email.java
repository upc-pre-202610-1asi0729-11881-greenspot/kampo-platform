package com.acme.kampo.platform.employee.domain.model.valueobjects;

public record Email(String value) {

    public Email{
        if(value == null || value.isBlank() )
            throw new IllegalArgumentException("Email cannot be blank");
    }

    public static Email of (String value){
        return new Email(value);
    }

    public String getValue(){
        return value;
    }

}

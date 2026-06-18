package com.acme.kampo.platform.employee.domain.model.valueobjects;

public record EmployeeId(Long value) {
    public EmployeeId{
        if(value == null || value <= 0)
            throw new IllegalArgumentException("EmployeeId must be positive");
    }

    public static EmployeeId of (Long value){
        return new EmployeeId(value);
    }

    public Long getValue(){
        return value;
    }

}

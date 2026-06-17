package com.acme.kampo.platform.report.domain.model.valueObjects;

public record ReportId(Long value) {

    public ReportId {
        if(value == null || value <= 0){
            throw new IllegalArgumentException("ReportId must be a positive");
        }
    }

    public static ReportId of(Long value){
        return new ReportId(value);
    }

    public Long getValue(){
        return value;
    }

}

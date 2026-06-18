package com.acme.kampo.platform.report.domain.model.valueobjects;

public record FileUrl(String value) {

    public FileUrl{
        if (value == null || value.isBlank()){
            throw new IllegalArgumentException("FileUrl must not be blank");
        }
    }

    public static FileUrl of (String value){
        return new FileUrl(value);
    }

    public String getValue(){
        return value;
    }
}

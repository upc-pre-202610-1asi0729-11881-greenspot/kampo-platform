package com.acme.kampo.platform.report.domain.model.valueobjects;

public record RecommendationId(Long value) {

    public RecommendationId{
        if(value == null || value <= 0)
            throw new IllegalArgumentException("Recommendation must be a positive");
    }

    public static RecommendationId of(Long value){
        return new RecommendationId(value);
    }

    public Long getValue(){
        return value;
    }

}

package com.acme.kampo.platform.profileaccess.domain.model.queries;
public record GetUserByEmailQuery(String email) {
    public GetUserByEmailQuery { if (email == null || email.isBlank()) throw new IllegalArgumentException("email must not be blank"); }
}
package com.seliverstov.movier.domain;

import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

public enum  Role implements GrantedAuthority {
    USER,ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
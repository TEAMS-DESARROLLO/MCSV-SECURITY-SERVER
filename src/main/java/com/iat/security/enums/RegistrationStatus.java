package com.iat.security.enums;

public enum RegistrationStatus {

    ACTIVE("A"),
    INACTIVE("I");

    private final String value;

    RegistrationStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}

package com.iat.security.enums;

public enum StatusUser {

    ACTIVE(1),
    INACTIVE(0);

    private final Integer value;

    StatusUser(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }


}

package com.ssafy.TripHub.user.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Sex {
    MALE,
    FEMALE;

    @JsonCreator
    public static Sex fromString(String value) {
        return Sex.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}
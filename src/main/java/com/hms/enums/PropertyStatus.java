package com.hms.enums;

public enum PropertyStatus {
    OCCUPIED("Occupied"),
    RESERVED("Reserved"),
    VACANT("Vacant"),
    OUT_OF_SERVICE("Out of Service");

    private final String displayName;

    PropertyStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
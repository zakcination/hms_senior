package com.hms.enums;

public enum PropertyType {
    HV_APARTMENT("Highvill Apartment"),
    CAMPUS_APARTMENT("Campus Apartment"),
    NL_APARTMENT("NL Apartment"),
    TOWNHOUSE("Townhouse"),
    COTTAGE("Cottage"),
    DORMITORY("Dormitory");

    private final String displayName;

    PropertyType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
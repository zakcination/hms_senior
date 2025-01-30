package com.hms.enums;

public enum UtilityType {
    ELECTRICITY("Electricity"),
    WATER("Water"),
    GAS("Gas"),
    HEATING("Heating"),
    INTERNET("Internet"),
    MAINTENANCE("Maintenance"),
    CLEANING("Cleaning");

    private final String displayName;

    UtilityType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
} 
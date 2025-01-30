package com.hms.enums;

public enum MaintenanceRequestType {
    PLUMBING("Plumbing"),
    ELECTRICAL("Electrical"),
    HVAC("HVAC"),
    INTERNET("Internet"),
    GENERAL_REPAIRS("General Repairs"),
    APPLIANCE("Appliance"),
    FURNITURE("Furniture"),
    CLEANING("Cleaning"),
    PEST_CONTROL("Pest Control"),
    SECURITY("Security");

    private final String displayName;

    MaintenanceRequestType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
} 
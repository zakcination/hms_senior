package com.hms.enums;

public enum FeedbackType {
    SERVICE("Service"),
    SYSTEM("System"),
    PROPERTY("Property"),
    MAINTENANCE("Maintenance"),
    STAFF("Staff"),
    GENERAL("General");

    private final String displayName;

    FeedbackType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
} 
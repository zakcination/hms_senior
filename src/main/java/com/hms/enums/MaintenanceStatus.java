package com.hms.enums;

public enum MaintenanceStatus {
    PENDING("Pending"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled"),
    ON_HOLD("On Hold"),
    SCHEDULED("Scheduled");

    private final String displayName;

    MaintenanceStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
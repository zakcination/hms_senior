package com.hms.enums;

public enum NotificationStatus {
    UNREAD("Unread"),
    READ("Read"),
    DISMISSED("Dismissed"),
    ARCHIVED("Archived");

    private final String displayName;

    NotificationStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
} 
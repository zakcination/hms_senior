package com.hms.enums;

public enum NotificationType {
    INFO("Information"),
    WARNING("Warning"),
    CRITICAL("Critical"),
    PAYMENT_DUE("Payment Due"),
    MAINTENANCE("Maintenance"),
    LEASE_EXPIRY("Lease Expiry"),
    POLICY_UPDATE("Policy Update"),
    ANNOUNCEMENT("Announcement");

    private final String displayName;

    NotificationType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
} 
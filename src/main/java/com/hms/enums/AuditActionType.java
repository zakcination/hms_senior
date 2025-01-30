package com.hms.enums;

public enum AuditActionType {
    CREATE("Create"),
    UPDATE("Update"),
    DELETE("Delete"),
    LOGIN("Login"),
    LOGOUT("Logout"),
    ACCESS("Access"),
    APPROVE("Approve"),
    REJECT("Reject"),
    SYSTEM("System");

    private final String displayName;

    AuditActionType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
} 
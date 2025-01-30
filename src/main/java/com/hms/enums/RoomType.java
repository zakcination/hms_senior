package com.hms.enums;

public enum RoomType {
    TWO_BEDDED_ROOM("2-Bedded Room"),
    THREE_BEDDED_ROOM("3-Bedded Room"),
    FOUR_BEDDED_ROOM("4-Bedded Room"),
    STUDIO("Studio"),
    ONE_BEDROOM("1-Bedroom"),
    TWO_BEDROOM("2-Bedroom"),
    THREE_BEDROOM("3-Bedroom"),
    FOUR_BEDROOM("4-Bedroom");

    private final String displayName;

    RoomType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
} 
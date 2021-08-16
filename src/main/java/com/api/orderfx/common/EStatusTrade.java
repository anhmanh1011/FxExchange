package com.api.orderfx.common;

public enum EStatusTrade {
    POSITION_PENDING(1, "POSITION_PENDING"),
    POSITION_SUCCESS(2, "POSITION_SUCCESS"),
    POSITION_REJECTED(3, "POSITION_REJECTED"),
    POSITION_CLOSED(4, "POSITION_CLOSED");

    private final int code;
    private final String description;


    EStatusTrade(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code + ": " + description;
    }
}

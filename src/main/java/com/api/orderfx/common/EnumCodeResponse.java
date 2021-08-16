package com.api.orderfx.common;

public enum EnumCodeResponse {

    SUCCESS(0, "Thành Công"),
    INTERNAL_SERVER(500, "INTERNAL_SERVER"),
    ORDER_REJECTED(2, "ORDER_REJECTED"),
    ORDER_NOT_FOUND(3, "ORDER_NOT_FOUND");

    private final int code;
    private final String description;

    private EnumCodeResponse(int code, String description) {
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

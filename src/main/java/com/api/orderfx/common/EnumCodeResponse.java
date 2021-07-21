package com.api.orderfx.common;

public enum EnumCodeResponse {

    SUCCESS(0, "Thành Công"),
    ORDER_REJECTED(1, "ORDER_REJECTED");

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

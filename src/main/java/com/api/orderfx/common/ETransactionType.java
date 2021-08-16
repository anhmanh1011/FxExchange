package com.api.orderfx.common;


public enum ETransactionType {
    POSITION_BUY(1, "POSITION_BUY"),
    POSITION_SELL(2, "POSITION_SELL"),
    ORDER_BUY(3, "ORDER_BUY"),
    ORDER_SELL(4, "ORDER_SELL");

    private final int code;
    private final String description;

    ETransactionType(int code, String description) {
        this.code = code;
        this.description = description;
    }
    ETransactionType() {
        this.code = 0;
        this.description = "UNKNOW";
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

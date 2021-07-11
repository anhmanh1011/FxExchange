package com.api.orderfx.model.fxcm.response;

import lombok.Data;

@Data
public class OrderFxcmResponse{
    public Integer t;
    public Integer ratePrecision;
    public String orderId;
    public String tradeId;
    public String time;
    public String accountName;
    public String accountId;
    public String timeInForce;
    public String expireDate;
    public String currency;
    public Boolean isBuy;
    public Double buy;
    public Integer sell;
    public String type;
    public Integer status;
    public Integer amountK;
    public Double currencyPoInteger;
    public Integer stopMove;
    public Integer stop;
    public Integer stopRate;
    public Integer limit;
    public Integer limitRate;
    public Boolean isEntryOrder;
    public Integer ocoBulkId;
    public Boolean isNetQuantity;
    public Boolean isLimitOrder;
    public Boolean isStopOrder;
    public Boolean isELSOrder;
    public Integer stopPegBaseType;
    public Integer limitPegBaseType;
    public String child_trailingStop;
    public Integer child_trailing;
    public String trailingStop;
    public Integer trailing;
    public Integer range;
}

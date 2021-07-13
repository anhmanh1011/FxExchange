package com.api.orderfx.model.fxcm.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderFxcmResponse{
    public String t;
    public String ratePrecision;
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
    public String sell;
    public String type;
    public String status;
    public String amountK;
    public Double currencyPoString;
    public Double stopMove;
    public Double stop;
    public Double stopRate;
    public Double limit;
    public Double limitRate;
    public Boolean isEntryOrder;
    public String ocoBulkId;
    public Boolean isNetQuantity;
    public Boolean isLimitOrder;
    public Boolean isStopOrder;
    public Boolean isELSOrder;
    public Double stopPegBaseType;
    public Double limitPegBaseType;
    public String child_trailingStop;
    public String child_trailing;
    public String trailingStop;
    public String trailing;
    public String range;
    public String action;
}

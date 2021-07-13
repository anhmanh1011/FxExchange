package com.api.orderfx.model.fxcm.response;

import lombok.Data;

@Data
public class AccountFxcmResponse {
    public String bus;
    public int usdMr3;
    public String accountName;
    public double usableMargin3;
    public int usableMarginPerc;
    public int usableMargin3Perc;
    public int usdMr;
    public double equity;
    public int ratePrecision;
    public String accountId;
    public String hedging;
    public int t;
    public double balance;
    public String mc;
    public String mcDate;
    public int dayPL;
    public int grossPL;
    public double usableMargin;
    public boolean isTotal;
}

package com.api.orderfx.model.fxcm.request;

import lombok.Data;

@Data
public class CreateEntryOrderRequest {
    public String account_id;
    public String symbol;
    public Boolean is_buy;
    public Double stop;
    public Double limit;
    public Double rate;
    public Integer amount;
    public String order_type = "Entry";
    public String time_in_force = "GTC";
}

package com.api.orderfx.model.fxcm.request;

import lombok.Data;

import javax.persistence.Basic;
import javax.persistence.Column;
import java.util.List;

@Data
public class CreateOrderRequest {

    public Boolean isBuy;

    public List<Double> limit;

    public Double stop;

    public String symbols;

    public Double price;

    public Double amount;

}

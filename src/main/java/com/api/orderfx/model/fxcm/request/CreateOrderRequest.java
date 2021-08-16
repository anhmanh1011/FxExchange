package com.api.orderfx.model.fxcm.request;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest {

    private String broker;

    private Boolean isBuy;

    private List<Double> limit;

    private Double stop;

    private String symbols;

    private Double price;

    private Double amount;

    private Double offset;

    private Long channelId;

}

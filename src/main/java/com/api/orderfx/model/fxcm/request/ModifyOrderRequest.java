package com.api.orderfx.model.fxcm.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifyOrderRequest {
    private Long positionId;
    private Double price;
    private String comment;
    private Double stopLoss;
    private Double takeProfit;
    private Long channelId;
    private String brokerName;
    private String symbols;

}

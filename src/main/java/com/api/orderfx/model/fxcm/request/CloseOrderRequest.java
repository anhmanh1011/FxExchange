package com.api.orderfx.model.fxcm.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CloseOrderRequest {
    private Long positionId;
    private Double price;
    private Long channelId;
}

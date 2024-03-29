package com.api.orderfx.controller;

import com.api.orderfx.Utils.JsonUtils;
import com.api.orderfx.config.SpringContext;
import com.api.orderfx.model.fxcm.request.CloseOrderRequest;
import com.api.orderfx.model.fxcm.request.CreateOrderRequest;
import com.api.orderfx.model.fxcm.request.ModifyOrderRequest;
import com.api.orderfx.service.ITradeApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trade/position")
@Log4j2
public class ApiController {


    @Autowired
    Environment environment;
    String prefixChannel = "tele.channel.";

    @PostMapping(value = "/open")
    public ResponseEntity openPosition(@RequestBody CreateOrderRequest createOrderRequest) throws Exception {
        log.info("Request: " + JsonUtils.ObjectToJson(createOrderRequest));
        String broker = getBrokerByChannelId(createOrderRequest.getChannelId());
        ITradeApi iTradeApi = (ITradeApi) SpringContext.getBean(broker);
        return ResponseEntity.ok(iTradeApi.openPosition(createOrderRequest));
    }

    @PostMapping(value = "/modify")
    public ResponseEntity modifyPosition(@RequestBody ModifyOrderRequest modifyOrderRequest) throws Exception {
        log.info("Modify: " + JsonUtils.ObjectToJson(modifyOrderRequest));

        String broker = getBrokerByChannelId(modifyOrderRequest.getChannelId());
        ITradeApi iTradeApi = (ITradeApi) SpringContext.getBean(broker);
        return ResponseEntity.ok(iTradeApi.modifyPosition(modifyOrderRequest));
    }

    @PostMapping(value = "/closeAll")
    public ResponseEntity close(@RequestBody CloseOrderRequest closeOrderRequest) throws Exception {
        log.info("closeAll: " + JsonUtils.ObjectToJson(closeOrderRequest));
        String broker = getBrokerByChannelId(closeOrderRequest.getChannelId());
        ITradeApi iTradeApi = (ITradeApi) SpringContext.getBean(broker);
        return ResponseEntity.ok(iTradeApi.closePosition(closeOrderRequest));
    }

    String getBrokerByChannelId(Long ChannelId) {
        return environment.getProperty(prefixChannel + ChannelId);
    }


}

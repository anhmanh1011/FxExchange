package com.api.orderfx.controller;

import com.api.orderfx.model.fxcm.request.CreateOrderRequest;
import com.api.orderfx.service.xtb.ITradeApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trade")
public class FxcmController {

    @Autowired
    ITradeApi tradeApi;

//    @GetMapping("/model")
//    public ResponseEntity getModel(@RequestParam(name = "type") String type) throws Exception {
//
//        Object forObject = tradeApi.getModel(type);
//        return ResponseEntity.ok(forObject);
//    }

    @PostMapping(value = "/open")
    public ResponseEntity createOrder(@RequestBody CreateOrderRequest createOrderRequest) throws Exception {
        return ResponseEntity.ok(tradeApi.openTrade(createOrderRequest));
    }


}

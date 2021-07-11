package com.api.orderfx.controller;

import com.api.orderfx.model.fxcm.request.CreateEntryOrderRequest;
import com.api.orderfx.service.fxcm.IFxcmApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fxcm")
public class FxcmController {

    @Autowired
    IFxcmApi iFxcmApi;

    @GetMapping("/model")
    public ResponseEntity getModel(@RequestParam(name = "type") String type) throws Exception {

        Object forObject = iFxcmApi.getModel(type);
        return ResponseEntity.ok(forObject);
    }

    @PostMapping("/order")
    public ResponseEntity getOrder(@RequestBody CreateEntryOrderRequest createEntryOrderRequest) throws Exception {

        Object sendPostRequest = iFxcmApi.createOder(createEntryOrderRequest);
        return ResponseEntity.ok(sendPostRequest);
    }
}

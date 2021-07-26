//package com.api.orderfx.RestClientRequest;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.LinkedHashMap;
//
//@Component
//@Slf4j
//public class FXCMRequestClient {
//
//    @Autowired
//    @Qualifier("RestForFXCM")
//    RestTemplate restTemplate;
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//    public LinkedHashMap sendGetRequest(String path) throws JsonProcessingException {
//        LinkedHashMap linkedHashMap = restTemplate.getForObject(path, LinkedHashMap.class);
//        return linkedHashMap;
//    }
//
//    public LinkedHashMap sendPostRequest(String path, Object body) {
//        return restTemplate.postForObject(path, body, LinkedHashMap.class);
//    }
//
//}

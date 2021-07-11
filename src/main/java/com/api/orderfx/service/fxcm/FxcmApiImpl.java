package com.api.orderfx.service.fxcm;

import com.api.orderfx.RestClientRequest.FXCMRequestClient;
import com.api.orderfx.Utils.JsonUtils;
import com.api.orderfx.Utils.ObjectMapperUtils;
import com.api.orderfx.common.FxcmUtils;
import com.api.orderfx.entity.OrderEntity;
import com.api.orderfx.model.fxcm.request.CreateEntryOrderRequest;
import com.api.orderfx.model.fxcm.response.DataCommonFxcm;
import com.api.orderfx.model.fxcm.response.OrderFxcmResponse;
import com.api.orderfx.model.fxcm.response.ResponseFxcmStatus;
import com.api.orderfx.model.fxcm.response.ResponseRootFxcm;
import com.api.orderfx.repository.OrderRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

@Service
@Slf4j
public class FxcmApiImpl implements IFxcmApi {

    @Autowired
    FXCMRequestClient fxcmRequestClient;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ModelMapper modelMapper;


    @SneakyThrows
    @Override
    public ResponseRootFxcm getModel(String type) {
        try {
            LinkedHashMap linkedHashMap = fxcmRequestClient.sendGetRequest(FxcmUtils.GET_MODEL_BASE + type);
            ResponseRootFxcm rootFxcm = new ResponseRootFxcm();
            ResponseFxcmStatus responseFxcmStatus = ObjectMapperUtils.convert(linkedHashMap.get("response"), ResponseFxcmStatus.class);
            rootFxcm.setResponse(responseFxcmStatus);
            if (type.equals(FxcmUtils.GET_ORDER_PARAM)) {
                List<OrderFxcmResponse> orderFxcmResponses = ObjectMapperUtils.convertList(linkedHashMap.get("orders"), new TypeReference<List<OrderFxcmResponse>>() {
                });
                rootFxcm.setData(orderFxcmResponses);
            }
            return rootFxcm;
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(JsonUtils.ObjectToJson(ex));
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    public ResponseRootFxcm createOder(CreateEntryOrderRequest createEntryOrderRequest) {
        try {
            LinkedHashMap linkedHashMap = fxcmRequestClient.sendPostRequest(FxcmUtils.CREATE_ORDER, createEntryOrderRequest);

            ResponseRootFxcm rootFxcm = new ResponseRootFxcm();
            ResponseFxcmStatus responseFxcmStatus = ObjectMapperUtils.convert(linkedHashMap.get("response"), ResponseFxcmStatus.class);
            rootFxcm.setResponse(responseFxcmStatus);
            DataCommonFxcm data = ObjectMapperUtils.convert(linkedHashMap.get("data"), DataCommonFxcm.class);
            rootFxcm.setData(data);
            if (responseFxcmStatus.getExecuted()) {
                ResponseRootFxcm model = getModel(FxcmUtils.GET_ORDER_PARAM);
                if (model.getResponse().getExecuted()) {
                    List<OrderFxcmResponse> listOrder = (List<OrderFxcmResponse>) model.getData();
                    log.info(JsonUtils.ObjectToJson(listOrder));


                    OrderFxcmResponse order = listOrder.stream().filter(orderFxcmResponse -> orderFxcmResponse.getOrderId().equals(data.getOrderId())).findFirst().orElseThrow();
                    log.info(JsonUtils.ObjectToJson(order));
                    OrderEntity orderEntity = modelMapper.map(order, OrderEntity.class);
                    orderRepository.save(orderEntity);
                }
            }
            return rootFxcm;


        } catch (Exception exception) {
            exception.printStackTrace();
            log.error(JsonUtils.ObjectToJson(exception));
            throw exception;
        }
    }
}

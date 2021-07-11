package com.api.orderfx.Utils;

import com.api.orderfx.config.SpringContext;
import com.api.orderfx.model.fxcm.response.OrderFxcmResponse;
import com.api.orderfx.model.fxcm.response.ResponseFxcmStatus;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@UtilityClass
public class ObjectMapperUtils {

    ObjectMapper objectMapper = SpringContext.getBean(ObjectMapper.class);

    public <T> T convert(Object fromValue, Class<T> toValueType){
        return ObjectMapperUtils.objectMapper.convertValue(fromValue, toValueType);
    }
    public <T> T convertList(Object fromValue, TypeReference<T> toValueType){
       return objectMapper.convertValue(fromValue, toValueType);
    }

}

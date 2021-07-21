package com.api.orderfx.controller;

import com.api.orderfx.Utils.JsonUtils;
import com.api.orderfx.common.BaseException;
import com.api.orderfx.model.common.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> customHandleException(
            Exception ex, WebRequest request) {
        log.error("ex: " + JsonUtils.ObjectToJson(ex));
        return new ResponseEntity<>(new BaseResponse(HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity customHandleBaseException(
            BaseException ex, WebRequest request) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(ex.getCode());
        baseResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);

    }


}

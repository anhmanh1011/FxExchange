package com.api.orderfx.common;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

@Slf4j
public class MyRestErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
        HttpStatus status = clientHttpResponse.getStatusCode();
        return status.is4xxClientError() || status.is5xxServerError();
    }

    @SneakyThrows
    @Override
    public void handleError(ClientHttpResponse clientHttpResponse){
        String responseAsString = IOUtils.toString(clientHttpResponse.getBody(), StandardCharsets.UTF_8);
        log.error("ResponseBody: {}", responseAsString);
        throw new BaseException(clientHttpResponse.getStatusCode().value(),responseAsString);
    }


    @SneakyThrows
    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
        String responseAsString = IOUtils.toString(response.getBody(), StandardCharsets.UTF_8);
        log.error("URL: {}, HttpMethod: {}, ResponseBody: {}", url, method, responseAsString);
        throw new BaseException(response.getStatusCode().value(),responseAsString);
    }
}

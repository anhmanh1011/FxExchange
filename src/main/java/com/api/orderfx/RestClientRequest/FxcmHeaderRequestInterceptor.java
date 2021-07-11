package com.api.orderfx.RestClientRequest;

import com.api.orderfx.ApplicationListener.SocketConnectFXCM;
import com.api.orderfx.config.SpringContext;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;


public class FxcmHeaderRequestInterceptor implements ClientHttpRequestInterceptor {


    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        Environment properties = SpringContext.getBean(Environment.class);
        request.getHeaders().add("Authorization", SocketConnectFXCM.bearer_access_token);
        request.getHeaders().add("Accept", "application/json");
        request.getHeaders().add("port", properties.getProperty("fxcm.api.port"));
        request.getHeaders().add("host", properties.getProperty("fxcm.api.host"));
        request.getHeaders().add("path", request.getURI().getPath());
        request.getHeaders().add("User-Agent", "request");

        return execution.execute(request, body);
    }
}

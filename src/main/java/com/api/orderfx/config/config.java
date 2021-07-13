package com.api.orderfx.config;

import com.api.orderfx.RestClientRequest.FxcmHeaderRequestInterceptor;
import com.api.orderfx.common.MyRestErrorHandler;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.Collections;
import java.util.Objects;

@Configuration
public class config implements WebMvcConfigurer {


    @Bean
    public BeanUtilsBean beanUtilsBean(){
        return new NullAwareBeanUtilsBean();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper;
    }

    @Autowired
    private Environment env;

    @Bean("RestForFXCM")
    public RestTemplate restForFXCM() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(new FxcmHeaderRequestInterceptor()));
        String uriConnection = env.getProperty("fxcm.uri.connection");
        DefaultUriBuilderFactory defaultUriBuilderFactory = new DefaultUriBuilderFactory(Objects.requireNonNull(uriConnection));
        restTemplate.setUriTemplateHandler(defaultUriBuilderFactory);
        restTemplate.setErrorHandler(new MyRestErrorHandler());
        return restTemplate;
    }


    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
        loggingFilter.setIncludeClientInfo(true);
        loggingFilter.setIncludeQueryString(true);
        loggingFilter.setIncludePayload(true);
        loggingFilter.setMaxPayloadLength(64000);
        return loggingFilter;
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new FxCustomInterceptor());
//    }


}

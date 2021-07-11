//package com.api.orderfx.common;
//
//import com.api.orderfx.Utils.JsonUtils;
//import com.api.orderfx.model.common.BaseResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@Slf4j
//public class FxCustomInterceptor implements HandlerInterceptor {
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        log.info("request path " + request.getPathInfo());
//        log.info("method " + request.getMethod());
//        log.info("ip " + request.getRemoteAddr());
//        return true;
//    }
//
//
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        log.info("request " + JsonUtils.ObjectToJson(request));
//        log.info("response " + JsonUtils.ObjectToJson(response));
//        log.info("handler " + JsonUtils.ObjectToJson(handler));
//        log.info("Exception " + JsonUtils.ObjectToJson(ex));
//
//        if (handler instanceof ResponseEntity){
//            ResponseEntity responseEntity = (ResponseEntity) handler;
//            BaseResponse body = (BaseResponse) responseEntity.getBody();
//            body.setPath(request.getPathInfo());
//        }
//
//    }
//}

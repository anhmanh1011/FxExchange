package com.api.orderfx.common;

import com.api.orderfx.Utils.JsonUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.UUID;

@Log4j2
public class ImplInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestId = UUID.randomUUID().toString();
        request.setAttribute(REQUEST_ID, requestId);

//        ThreadContext.push(REQUEST_ID, UUID.randomUUID().toString());
        ThreadContext.put(REQUEST_ID, requestId);
        ThreadContext.put("ipAddress", request.getRemoteAddr());
        ThreadContext.put("hostName", request.getServerName());

        logRequest(request, requestId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("[postHandle][" + JsonUtils.ObjectToJson(request) + "]");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (ex != null) {
            ex.printStackTrace();
        }
        ThreadContext.clearMap();
    }

    private static final String REQUEST_ID = "request_id";


    private void logRequest(HttpServletRequest request, String requestId) {
        if (request != null) {
            StringBuilder data = new StringBuilder();
            data.append("\nLOGGING REQUEST-----------------------------------\n")
                    .append("[REQUEST-ID]: ").append(requestId).append("\n")
                    .append("[PATH]: ").append(request.getRequestURI()).append("\n")
                    .append("[QUERIES]: ").append(request.getQueryString()).append("\n")
                    .append("[HEADERS]: ").append("\n");

            Enumeration headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String key = (String) headerNames.nextElement();
                String value = request.getHeader(key);
                data.append("---").append(key).append(" : ").append(value).append("\n");
            }
            data.append("LOGGING REQUEST-----------------------------------\n");

            log.info(data.toString());
        }
    }
}

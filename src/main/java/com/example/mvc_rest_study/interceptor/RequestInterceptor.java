package com.example.mvc_rest_study.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Component
@Slf4j
public class RequestInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestId = request.getHeader("X-Request-ID");
        if (requestId == null) {
            requestId = UUID.randomUUID().toString();
        }
        request.setAttribute("X-Request-ID", requestId);

        if (handler instanceof HandlerMethod handlerMethod) {
            String controller = handlerMethod.getBeanType().getSimpleName();
            String method = handlerMethod.getMethod().getName();
            log.info("X-Request-ID : {} | Handler : {}.{}", requestId, controller,method);

        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String requestId = (String) request.getAttribute("X-Request-ID");
        response.setHeader("x-Request_ID",requestId);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (ex != null) {
            String requestId = (String) request.getAttribute("X-Request-ID");
            log.error("X-Request-ID : {} | Exception : {}",requestId,ex.getMessage());
        }

    }
}

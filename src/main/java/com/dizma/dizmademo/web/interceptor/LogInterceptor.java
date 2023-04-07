package com.dizma.dizmademo.web.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Configuration("login")
public class LogInterceptor implements HandlerInterceptor {
   private final Logger LOGGER = LoggerFactory.getLogger(LogInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        UUID uuid = UUID.randomUUID();
        request.setAttribute("startAction", System.currentTimeMillis());
        request.setAttribute("request-id",uuid);
        LOGGER.info(String.format("%s - calling %s", uuid, request.getRequestURI()));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        LOGGER.info(String.format("%s - response in %s ms",request.getAttribute("request-id"),
                System.currentTimeMillis() - (long)request.getAttribute("startAction")));
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LOGGER.info(String.format("%s completed in %s ms", request.getAttribute("request-id"),
                System.currentTimeMillis() - (long) request.getAttribute("startAction")));
    }
}

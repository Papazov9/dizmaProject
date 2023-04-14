package com.dizma.dizmademo.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalTime;

@Configuration("maintenance")
public class MaintenanceInterceptorConf implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        if (!requestURI.equals("/maintenance")) {
            LocalTime now = LocalTime.now();
            if (now.getHour() == 23) {
                response.sendRedirect("/maintenance");
                return false;
            }
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

}

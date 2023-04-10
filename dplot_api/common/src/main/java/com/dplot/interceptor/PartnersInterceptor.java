package com.dplot.interceptor;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PartnersInterceptor extends WebContentInterceptor {

	@Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws ServletException  {
		logger.debug("PartnersInterceptor preHandle Start");
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request,
                            HttpServletResponse response,
                            Object handler,
                            ModelAndView mv) throws Exception {
        logger.debug("PartnersInterceptor postHandle Start");
        
    }


    @Override
    public void afterCompletion(HttpServletRequest req,
                                HttpServletResponse res,
                                Object handler,
                                Exception ex) throws Exception {
        logger.debug("AdminInterceptor afterCompletion Start");
    }
}

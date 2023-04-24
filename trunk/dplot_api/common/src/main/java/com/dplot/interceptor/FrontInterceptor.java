package com.dplot.interceptor;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

import com.dplot.common.Response;

public class FrontInterceptor  extends WebContentInterceptor {
	
	@Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws ServletException  {
		logger.debug("FrontInterceptor preHandle Start");
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request,
                            HttpServletResponse response,
                            Object handler,
                            ModelAndView mv) throws Exception {
        logger.debug("FrontInterceptor postHandle Start");
        
        logger.debug(handler);
        logger.debug(mv);
//        
//        Map<String, Object> modelMap = mv.getModel();
//        for(String key : modelMap.keySet()){
//        	logger.debug(key + " : " + modelMap.get(key));
//        }
        
    }


    @Override
    public void afterCompletion(HttpServletRequest req,
                                HttpServletResponse res,
                                Object handler,
                                Exception ex) throws Exception {
        logger.debug("FrontInterceptor afterCompletion Start");
        logger.debug(handler);
        //Response response = (Response)handler;
        //response.getData().put("testtest", "testtesttest");
        
    }
}

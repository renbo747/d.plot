package com.dplot.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dplot.util.Util;

@Component("corsFilter")
public class CORSFilter implements Filter {
	private static final Logger logger = LoggerFactory.getLogger(CORSFilter.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		if(!Util.isEmpty(request.getHeader("content-type"))){
			logger.debug("=================================");
			Enumeration<String> keys = request.getHeaderNames();
			while(keys.hasMoreElements()) {
				String key = keys.nextElement();
				logger.debug(key + " : " + request.getHeader(key));
			}
			logger.debug("=================================");
		}
		
		HttpServletResponse response = (HttpServletResponse) res;
		
		//String host = String.format("%s://%s", request.getScheme(), request.getServerName());
		String host = request.getHeader("origin");
		
		logger.debug("Request Host : " + host);
	    response.setHeader("Access-Control-Allow-Origin", host);
	    response.setHeader("Access-Control-Allow-Credentials", "true");
	    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
	    response.setHeader("Access-Control-Max-Age", "3600");
	    response.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Content-Type, Accept, X-Requested-With, remember-me, platform");
		response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
	    
	    chain.doFilter(req, res);
	}

}
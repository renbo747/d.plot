package com.dplot.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class XSSFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (excludeUrl((HttpServletRequest) request)) {
			chain.doFilter(request, response); 
		} else {
			chain.doFilter(new XSSRequestWrapper((HttpServletRequest) request), response);
		}
	}

	
	private boolean excludeUrl(HttpServletRequest request) {
		  String uri = request.getRequestURI().toString().trim();
		  if (uri.startsWith("/pay/payco/payco_without_bankbook.do")) {
			  return true;
		  } else if (uri.startsWith("/mw/pay/payco/payco_without_bankbook.do")) {
			  return true;
		  }
		  
		  return false;
	}

}
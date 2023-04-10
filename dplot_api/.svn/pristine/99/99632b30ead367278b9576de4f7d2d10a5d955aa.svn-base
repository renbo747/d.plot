package com.dplot.security.filter;

import java.io.BufferedReader;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.dplot.security.model.CustomWebAuthenticationDetailsSource;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private String jsonUsername;
    private String jsonPassword;
    
    @Autowired
    CustomWebAuthenticationDetailsSource customWebAuthenticationDetailsSource;
    
	@Override
    protected String obtainPassword(HttpServletRequest request) {
        String password = null; 

        if ("application/json".equalsIgnoreCase(request.getHeader("Content-Type"))) {
            password = this.jsonPassword;
        }else{
            password = super.obtainPassword(request);
        }

        return password;
    }

    @Override
    protected String obtainUsername(HttpServletRequest request){
        String username = null;

        if ("application/json".equalsIgnoreCase(request.getHeader("Content-Type"))) {
            username = this.jsonUsername;
        }else{
            username = super.obtainUsername(request);
        }

        return username;
    }

    @SuppressWarnings("unchecked")
	@Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)  throws AuthenticationException {
    	if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
    		return null;
    	}
    	
    	if (!request.getMethod().equalsIgnoreCase("POST")) {
			throw new AuthenticationServiceException(
					"Authentication method not supported: " + request.getMethod());
		}
    	
        if ("application/json".equalsIgnoreCase(request.getHeader("Content-Type"))) {
            try {
                StringBuffer sb = new StringBuffer();
                String line = null;

                BufferedReader reader = request.getReader();
                while ((line = reader.readLine()) != null){
                    sb.append(line);
                }

                //json transformation
                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> paramMap = mapper.readValue(sb.toString(), Map.class);

                this.jsonUsername = (String)paramMap.get("id");
                this.jsonPassword = (String)paramMap.get("pw");
                request.setAttribute("paramMap", paramMap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        String username = obtainUsername(request);
		String password = obtainPassword(request);

		if (username == null) {
			username = "";
		}

		if (password == null) {
			password = "";
		}

		username = username.trim();

		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				username, password);

		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);

		return this.getAuthenticationManager().authenticate(authRequest);
    }
	
	@Override
	protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
		authRequest.setDetails(customWebAuthenticationDetailsSource.buildDetails(request));
	}

}

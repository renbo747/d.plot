package com.dplot.security.service;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import com.dplot.common.service.MallConfigService;

public class RememberMeService extends TokenBasedRememberMeServices {
	
	private static final Logger logger = LoggerFactory.getLogger(RememberMeService.class);
	
	@Autowired
    protected MallConfigService cs;
	
	public RememberMeService(String key, UserDetailsService userDetailsService) {
		super(key, userDetailsService);
	}

	@Override
	protected UserDetails processAutoLoginCookie(String[] cookieTokens,
			HttpServletRequest request, HttpServletResponse response) {

		if (cookieTokens.length != 3) {
			throw new InvalidCookieException("Cookie token did not contain 3"
					+ " tokens, but contained '" + Arrays.asList(cookieTokens) + "'");
		}

		long tokenExpiryTime;

		try {
			tokenExpiryTime = new Long(cookieTokens[1]).longValue();
		}
		catch (NumberFormatException nfe) {
			throw new InvalidCookieException(
					"Cookie token[1] did not contain a valid number (contained '"
							+ cookieTokens[1] + "')");
		}

		if (isTokenExpired(tokenExpiryTime)) {
			throw new InvalidCookieException("Cookie token[1] has expired (expired on '"
					+ new Date(tokenExpiryTime) + "'; current time is '" + new Date()
					+ "')");
		}

		try {
			cs.init(request.getServerName());
		} catch (Exception e) {
			throw new InvalidCookieException("MallConfigService has error to init");
		}
		// Check the user exists.
		// Defer lookup until after expiry time checked, to possibly avoid expensive
		// database call.

		UserDetails userDetails = getUserDetailsService().loadUserByUsername(
				cookieTokens[0]);

		// Check signature of token matches remaining details.
		// Must do this after user lookup, as we need the DAO-derived password.
		// If efficiency was a major issue, just add in a UserCache implementation,
		// but recall that this method is usually only called once per HttpSession - if
		// the token is valid,
		// it will cause SecurityContextHolder population, whilst if invalid, will cause
		// the cookie to be cancelled.
		String expectedTokenSignature = makeTokenSignature(tokenExpiryTime,
				userDetails.getUsername(), userDetails.getPassword());

		if (!equals(expectedTokenSignature, cookieTokens[2])) {
			throw new InvalidCookieException("Cookie token[2] contained signature '"
					+ cookieTokens[2] + "' but expected '" + expectedTokenSignature + "'");
		}

		return userDetails;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected boolean rememberMeRequested(HttpServletRequest request, String parameter){
		String paramValue = null;
			
		if ("application/json".equalsIgnoreCase(request.getHeader("Content-Type"))) {
			Map<String, Object> data = (Map<String, Object>)request.getAttribute("paramMap");
			paramValue = String.valueOf(data.get(parameter));
		} else {
			paramValue = request.getParameter(parameter);
		}
		
		if (paramValue != null) {
			if (paramValue.equalsIgnoreCase("true") || paramValue.equalsIgnoreCase("on")
					|| paramValue.equalsIgnoreCase("yes") || paramValue.equals("1")
					|| paramValue.equalsIgnoreCase("T")) {
				return true;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug(
					String.format("Did not send remember-me cookie (principal did not set parameter '%s')", parameter));
		} 

		return false;
	}
	
	/**
	 * Constant time comparison to prevent against timing attacks.
	 */
	private static boolean equals(String expected, String actual) {
		byte[] expectedBytes = bytesUtf8(expected);
		byte[] actualBytes = bytesUtf8(actual);
		if (expectedBytes.length != actualBytes.length) {
			return false;
		}

		int result = 0;
		for (int i = 0; i < expectedBytes.length; i++) {
			result |= expectedBytes[i] ^ actualBytes[i];
		}
		return result == 0;
	}

	private static byte[] bytesUtf8(String s) {
		if (s == null) {
			return null;
		}
		return Utf8.encode(s);
	}
}

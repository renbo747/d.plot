package com.dplot.security.repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SaveContextOnUpdateOrErrorResponseWrapper;
import org.springframework.security.web.context.SecurityContextRepository;

import com.dplot.common.service.MallConfigService;
import com.dplot.security.domain.Role;
import com.dplot.security.domain.RoleName;
import com.dplot.security.model.Dealer;
import com.dplot.util.Util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class DealerSecurityContextRepository implements SecurityContextRepository {
	private static final Logger log = LoggerFactory.getLogger(DealerSecurityContextRepository.class);

	private final String tokenName ;
	private final String secret ;
	private final MallConfigService cs;
	private final Integer minute;

	DealerSecurityContextRepository(
			String tokenName,
			String secret,
			MallConfigService cs,
			Integer minute) {
		this.tokenName = tokenName;
		this.secret = secret;
		this.cs = cs;
		this.minute = minute;
	}

	@Override
	public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
		HttpServletRequest request = requestResponseHolder.getRequest();
		HttpServletResponse response = requestResponseHolder.getResponse();
		requestResponseHolder.setResponse(new SaveToCookieResponseWrapper(request, response, tokenName, secret, cs, minute));
		SecurityContext context = readSecurityContextFromCookie(request);
		if (context == null) {
			return SecurityContextHolder.createEmptyContext();
		}
		return context;
	}
	
	private SecurityContext readSecurityContextFromCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		} else {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(tokenName)) {
					try {
						Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(cookie.getValue()).getBody();
						
						SecurityContext context = SecurityContextHolder.createEmptyContext();
						
						Dealer dealer = new Dealer();
						
						dealer.setUserid((String)claims.get("userId"));
						dealer.setUserno((Integer)claims.get("userNo"));
						dealer.setDealertype((Integer)claims.get("dealerType"));
						dealer.setName((String)claims.get("name"));
						
						Role role = new Role();
						role.setName(RoleName.ROLE_DEALER.getName());
						
						List<Role> roles = new ArrayList<Role>();
						roles.add(role);
						dealer.setAuthorities(roles);
						
						dealer.setSecurecode((String)claims.get("secureCode"));
						
						context.setAuthentication(new UsernamePasswordAuthenticationToken(dealer, null, dealer.getAuthorities()));
					
						return context;
					} catch (ExpiredJwtException ex) {
						log.debug("authentication cookie is expired");
					} catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
						log.warn("tampered jwt authentication cookie detected");
						return null;
					}

				}
			}
		}
		log.debug("no [{}] found in request.", tokenName);
		return null;
	}

	@Override
	public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
		SaveToCookieResponseWrapper responseWrapper = (SaveToCookieResponseWrapper) response;
		if (!responseWrapper.isContextSaved()) {
			responseWrapper.saveContext(context);
		}

	}

	@Override
	public boolean containsContext(HttpServletRequest request) {
		return readSecurityContextFromCookie(request) != null;
	}


	private static class SaveToCookieResponseWrapper extends SaveContextOnUpdateOrErrorResponseWrapper {

		private AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();

		private final HttpServletRequest request;
		private final String token;
		private final String secret;
		private final MallConfigService cs;
		private final Integer minute;
		private final String domain;
		
		SaveToCookieResponseWrapper(HttpServletRequest request,
				HttpServletResponse response, String token, String secret, MallConfigService cs,
				Integer minute) {
			super(response, true);
			this.request = request;
			this.token = token;
			this.secret = secret;
			this.cs = cs;
			this.minute = minute;
			this.domain = Util.getMainDomain(request.getHeader("origin"));
		}
		
		@Override
		protected void saveContext(SecurityContext securityContext) {
			HttpServletResponse response = (HttpServletResponse) getResponse();
			Authentication authentication = securityContext.getAuthentication();
			if (authentication == null || trustResolver.isAnonymous(authentication)) {
				response.addCookie(createExpireAuthenticationCookie(request));
				return;
			}
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.MINUTE, minute);
			
			Object principal = authentication.getPrincipal();
			
			Dealer dealer = (Dealer) principal;
			
			Map<String, Object> map = new HashMap<>();
			map.put("userId", dealer.getUserid());
			map.put("userNo", dealer.getUserno());
			map.put("name", dealer.getName());
			map.put("dealerType", dealer.getDealertype());
			map.put("secureCode", dealer.getSecurecode());

			JwtBuilder jwtBuilder = Jwts.builder()
					.signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, secret)
					.setClaims(map);
			if (minute > 0) {
				jwtBuilder.setExpiration(calendar.getTime());
			}
			String jwt = jwtBuilder.compact();
			response.addCookie(createAuthenticationCookie(jwt));
			
		}

		private Cookie createAuthenticationCookie(String cookieValue) {
			log.debug("create dealer authentication cookie {} {}", token,cookieValue);
			Cookie authenticationCookie = new Cookie(token, cookieValue);
			authenticationCookie.setPath("/");
			if(this.domain != null) {
				authenticationCookie.setDomain(this.domain);
			}
			authenticationCookie.setHttpOnly(true);
			return authenticationCookie;
		}

		private Cookie createExpireAuthenticationCookie(HttpServletRequest request) {
			log.debug("remove dealer authentication cookie {}", token);
			Cookie removeSessionCookie = new Cookie(token, "");
			removeSessionCookie.setPath("/");
			if(this.domain != null) {
				removeSessionCookie.setDomain(this.domain);
			}
			removeSessionCookie.setMaxAge(0);
			removeSessionCookie.setHttpOnly(true);
			return removeSessionCookie;
		}

	}

}
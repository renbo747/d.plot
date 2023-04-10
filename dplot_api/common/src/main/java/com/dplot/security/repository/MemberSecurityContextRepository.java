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
import com.dplot.security.model.Member;
import com.dplot.security.model.NonMember;
import com.dplot.util.Util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class MemberSecurityContextRepository implements SecurityContextRepository {
	private static final Logger log = LoggerFactory.getLogger(MemberSecurityContextRepository.class);

	private final String tokenName ;
	private final String secret ;
	private final MallConfigService cs;
	private final Integer memberMinute;
	private final Integer nonMemberMinute;

	MemberSecurityContextRepository(
			String tokenName,
			String secret,
			MallConfigService cs,
			Integer memberMinute,
			Integer nonMemberMinute) {
		this.tokenName = tokenName;
		this.secret = secret;
		this.cs = cs;
		this.memberMinute = memberMinute;
		this.nonMemberMinute = nonMemberMinute;
	}

	@Override
	public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
		HttpServletRequest request = requestResponseHolder.getRequest();
		HttpServletResponse response = requestResponseHolder.getResponse();
		requestResponseHolder.setResponse(new SaveToCookieResponseWrapper(request, response, tokenName, secret, cs, memberMinute,nonMemberMinute));
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
						
						String type = (String)claims.get("authType");
						
						if (Util.equal(type, "nonMember")) {
							Integer orderIdx = (Integer)claims.get("orderIdx");
							NonMember nonMember = new NonMember();
							nonMember.setOrderIdx(orderIdx);
							nonMember.setUserno(-1);
							nonMember.setName((String)claims.get("name"));
							nonMember.setOrdno((String)claims.get("ordno"));
							Role role = new Role();
							role.setName(RoleName.ROLE_NON_MEMBER.getName());
							
							List<Role> roles = new ArrayList<Role>();
							roles.add(role);
							nonMember.setAuthorities(roles);
							
							context.setAuthentication(new UsernamePasswordAuthenticationToken(nonMember, null, nonMember.getAuthorities()));
							
						} else {
							Member member = new Member();
							
							member.setUserid((String)claims.get("userId"));
							member.setUserno((Integer)claims.get("userNo"));
							member.setName((String)claims.get("name"));
							member.setMembertype((String)claims.get("memberType"));
							member.setJoinchtype((String)claims.get("joinchtype"));
							member.setMemlvtype((String)claims.get("memlvtype"));
							member.setMemlvtypenm((String)claims.get("memlvtypenm"));
							member.setSecurecode((String)claims.get("secureCode"));
							member.setAutoLogin((Boolean)claims.get("isAutoLogin"));
							
							Role role = new Role();
							role.setName(RoleName.ROLE_MEMBER.getName());
							
							List<Role> roles = new ArrayList<Role>();
							roles.add(role);
							member.setAuthorities(roles);
							
							context.setAuthentication(new UsernamePasswordAuthenticationToken(member, null, member.getAuthorities()));
						}
						
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
		private final Integer memberMinute;
		private final Integer nonMemberMinute;

		SaveToCookieResponseWrapper(HttpServletRequest request,
				HttpServletResponse response, String token, String secret, MallConfigService cs,
				Integer memberMinute,
				Integer nonMemberMinute) {
			super(response, true);
			this.request = request;
			this.token = token;
			this.secret = secret;
			this.cs = cs;
			this.memberMinute =memberMinute;
			this.nonMemberMinute =nonMemberMinute;
		}
		
		@Override
		protected void saveContext(SecurityContext securityContext) {
			HttpServletResponse response = (HttpServletResponse) getResponse();
			Authentication authentication = securityContext.getAuthentication();
			if (authentication == null || trustResolver.isAnonymous(authentication)) {
				response.addCookie(createExpireAuthenticationCookie(request));
				return;
			}
			
			Object principal = authentication.getPrincipal();
			
			if (principal instanceof NonMember) { //비회원일경우
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				calendar.add(Calendar.MINUTE, nonMemberMinute);
				NonMember nonMember = (NonMember) principal;
				Map<String, Object> map = new HashMap<>();
				map.put("orderIdx", nonMember.getOrderIdx());
				map.put("name", nonMember.getName());
				map.put("ordno", nonMember.getOrdno());
				map.put("authType", "nonMember");
				JwtBuilder jwtBuilder = Jwts.builder()
						.signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, secret)
						.setClaims(map);
				if (nonMemberMinute > 0) {
					jwtBuilder.setExpiration(calendar.getTime());
				}
				String jwt = jwtBuilder.compact();
				response.addCookie(createAuthenticationCookie(jwt));
			} else { // 나머지 회원,관리자,SCM 관리자
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				calendar.add(Calendar.MINUTE, memberMinute);
				Member member = (Member) principal;
				Map<String, Object> map = new HashMap<>();
				map.put("authType", "member");
				map.put("userId", member.getUserid());
				map.put("userNo", member.getUserno());
				map.put("memberType", member.getMembertype());
				map.put("name", member.getName());
				map.put("memlvtype", member.getMemlvtype());
				map.put("joinchtype", member.getJoinchtype());
				map.put("memlvtypenm", member.getMemlvtypenm());
				map.put("secureCode", member.getSecurecode());
				map.put("isAutoLogin", member.isAutoLogin());
				JwtBuilder jwtBuilder = Jwts.builder()
						.signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, secret)
						.setClaims(map);
				if (memberMinute > 0) {
					jwtBuilder.setExpiration(calendar.getTime());
				}
				String jwt = jwtBuilder.compact();
				response.addCookie(createAuthenticationCookie(jwt));
			}
			
		}

		private Cookie createAuthenticationCookie(String cookieValue) {
			log.debug("create member authentication cookie {} {}", token,cookieValue);
			Cookie authenticationCookie = new Cookie(token, cookieValue);
			authenticationCookie.setPath("/");
			authenticationCookie.setHttpOnly(true);
			return authenticationCookie;
		}

		private Cookie createExpireAuthenticationCookie(HttpServletRequest request) {
			log.debug("remove member authentication cookie {}", token);
			Cookie removeSessionCookie = new Cookie(token, "");
			removeSessionCookie.setPath("/");
			removeSessionCookie.setMaxAge(0);
			removeSessionCookie.setHttpOnly(true);
			return removeSessionCookie;
		}

	}

}
package com.dplot.security.provider;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.dplot.common.service.util.MallBaseService;
import com.dplot.security.domain.Message;
import com.dplot.security.domain.RoleName;
import com.dplot.security.service.DealerAuthManagerService;
import com.dplot.util.CryptHash;

@Component
public class DealerAuthenticationProvider extends MallBaseService implements AuthenticationProvider {
	
	private static final Logger logger = LoggerFactory.getLogger(DealerAuthenticationProvider.class);
	
	@Autowired
	private DealerAuthManagerService dealerAuthManagerService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName(); 
		String password = (String) authentication.getCredentials();

		UserDetails user; 
		Collection<? extends GrantedAuthority> authorities; 
		try { 
			String logPrefix = cs.makeLogPrefix("authenticate");
			user = dealerAuthManagerService.loadUserByUsername(username); 
			String hashedPassword = CryptHash.hash(password); 
			
			if (!hashedPassword.equals(user.getPassword())) {
				logger.info(String.format("%s 패스워드 불일치 아이디 [%s]", logPrefix, username));
				throw new BadCredentialsException(Message.USER_OR_PWD_NOT_MATCH.getMsg());
			}
			authorities = user.getAuthorities(); 
			
			logger.info(String.format("%s 판매자 로그인 성공 아이디 : [%s], 권한 : [%s]", logPrefix, username,RoleName.ROLE_DEALER.getName()));
			
		} catch(UsernameNotFoundException e) { 
			logger.info(e.toString()); 
			throw new UsernameNotFoundException(e.getMessage()); 
		} catch(BadCredentialsException e) { 
			logger.info(e.toString()); 
			throw new BadCredentialsException(e.getMessage()); 
		} catch(Exception e) { 
			logger.info(e.toString()); 
			throw new RuntimeException("로그인 처리중 에러가 발생하였습니다.");
		} 
		return new UsernamePasswordAuthenticationToken(user, password, authorities);

	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}

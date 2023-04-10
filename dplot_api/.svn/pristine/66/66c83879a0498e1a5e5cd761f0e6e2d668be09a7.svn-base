package com.dplot.security.service;

import java.util.ArrayList;
import java.util.List;

import com.dplot.util.ServletRequestInfoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dplot.common.SOMap;
import com.dplot.common.service.MallConfigService;
import com.dplot.mapper.UserMapper;
import com.dplot.security.domain.Message;
import com.dplot.security.domain.Role;
import com.dplot.security.domain.RoleName;
import com.dplot.security.model.User;
import com.dplot.util.BeanUtil;
import com.dplot.util.Util;

import javax.servlet.http.HttpServletRequest;

@Service
public class AdminAuthManagerService implements UserDetailsService{
	
	private static final Logger logger = LoggerFactory.getLogger(AdminAuthManagerService.class);

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private MallConfigService cs;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		try {
			String logPrefix = cs.makeLogPrefix("loadUserByUsername");
			
			SOMap dbparams = new SOMap();
			HttpServletRequest request = ServletRequestInfoUtil.getRequest();

			cs.init(request.getServerName());
			dbparams.put("siteid", cs.getStr("siteid"));
			dbparams.put("userid", username);
			SOMap userMap = userMapper.selectUser(dbparams);
			
			User userInfo = BeanUtil.convertMapToBean(userMap, User.class);
	
			if (userInfo == null) {
				logger.info(String.format("%s 존재하지 않는 아이디 [%s]", logPrefix, username));
				throw new UsernameNotFoundException(Message.USER_OR_PWD_NOT_MATCH.getMsg());
			}
	
			Role role = new Role();
			role.setName(RoleName.ROLE_ADMIN.getName());
			
			List<Role> roles = new ArrayList<Role>();
			roles.add(role);
			userInfo.setAuthorities(roles);
			
			//중복 로그인 체크용
			userInfo.setSecurecode(Util.getGUID());
	
			return userInfo;
		} catch(UsernameNotFoundException ue) {
			logger.error("", ue);
			throw new UsernameNotFoundException(ue.getMessage());
		} catch (Exception se) {
			logger.error("", se);
			throw new UsernameNotFoundException("로그인 처리중 에러가 발생하였습니다.");
		}
	}

}
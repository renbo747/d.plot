package com.dplot.security.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dplot.common.CMConst;
import com.dplot.common.SOMap;
import com.dplot.common.service.MallConfigService;
import com.dplot.mapper.MemberMapper;
import com.dplot.mapper.MemberSleepMapper;
import com.dplot.mapper.UserMapper;
import com.dplot.security.domain.Message;
import com.dplot.security.domain.Role;
import com.dplot.security.domain.RoleName;
import com.dplot.security.model.Member;
import com.dplot.util.Util;

@Service
public class MemberAuthManagerService implements UserDetailsService{
	
	private static final Logger logger = LoggerFactory.getLogger(MemberAuthManagerService.class);

	@Autowired
	UserMapper userMapper;
	
	@Autowired
	MemberMapper memberMapper;
	
	@Autowired
	MemberSleepMapper memberSleepMapper;
	
	@Autowired
	private MallConfigService cs;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		try {
			String logPrefix = cs.makeLogPrefix("loadUserByUsername");
			
			SOMap dbparams = new SOMap();
			dbparams.put("siteid", cs.getStr("siteid"));
			dbparams.put("userid", username);
			SOMap userMap = userMapper.selectUserJoinUserTotal(dbparams);
			
			//회원여부확인
			if(Util.isEmpty(userMap) || !CMConst.USER_TYPE_MEMBER.equals(userMap.getDbStr("usertype"))) {
			//if(Util.isEmpty(userMap)) {
				logger.info(String.format("%s 존재하지 않거나 아이디가 회원 종류가 아닌 아이디 [%s]", logPrefix, username));
				throw new UsernameNotFoundException(Message.USER_OR_PWD_NOT_MATCH.getMsg());
			}
			
			int userNo = userMap.getDbInt("no");
			String userId = userMap.getDbStr("userid");
			String userPw = userMap.getDbStr("userpw");
			boolean isSleep = false;
			
			//회원정보조회
			dbparams.clear();
			dbparams.put("userno", userNo);
			SOMap memberMap = memberMapper.selectMember(dbparams);
			
			//회원탈퇴 상태체크
			if(CMConst.CM_STATE_WITHDRAW.equals(memberMap.getDbStr("state"))){
				logger.info(String.format("%s 탈퇴한 회원 아이디 [%s]", logPrefix, username));
				throw new UsernameNotFoundException(Message.USER_OR_PWD_NOT_MATCH.getMsg());
			}
			
			//휴면계정일 경우 휴면회원정보조회
			if(CMConst.CM_STATE_SLEEP.equals(memberMap.getDbStr("state"))){
				memberMap = memberSleepMapper.selectMemberSleep(dbparams);
				isSleep = true;
			}
			
			Member userInfo = new Member();
			userInfo.setUserid(userId);
			userInfo.setUserno(userNo);
			userInfo.setUserpw(userPw);
			userInfo.setName(memberMap.getDbStr("name"));
			userInfo.setMembertype(memberMap.getDbStr("dadamembertype"));
			userInfo.setJoinchtype(memberMap.getDbStr("joinchtype"));
			userInfo.setMemlvtype(memberMap.getDbStr("memlvtype"));
			userInfo.setMemlvtypenm(memberMap.getDbStr("memlvtypenm"));
			userInfo.setPwmoddate(memberMap.getDateStr("pwmoddate"));
			userInfo.setSleep(isSleep);
			userInfo.setPush(Util.flag2Bool(memberMap.getDateStr("isadpush")));
			userInfo.setBio(Util.flag2Bool(memberMap.getDateStr("isbio")));
			userInfo.setSms(Util.flag2Bool(memberMap.getDateStr("isSms")));
			userInfo.setMail(Util.flag2Bool(memberMap.getDateStr("isMail")));
			
			Role role = new Role();
			role.setName(RoleName.ROLE_MEMBER.getName());
			
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
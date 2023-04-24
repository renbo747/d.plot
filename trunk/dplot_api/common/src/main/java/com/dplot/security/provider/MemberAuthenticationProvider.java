package com.dplot.security.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import com.dplot.common.SOMap;
import com.dplot.common.service.ERPService;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.mapper.ComOrderMapper;
import com.dplot.mapper.MemberHistoryMapper;
import com.dplot.mapper.MemberMapper;
import com.dplot.mapper.UserMapper;
import com.dplot.security.domain.Message;
import com.dplot.security.domain.Role;
import com.dplot.security.domain.RoleName;
import com.dplot.security.model.CustomWebAuthenticationDetails;
import com.dplot.security.model.NonMember;
import com.dplot.security.model.Member;
import com.dplot.security.service.MemberAuthManagerService;
import com.dplot.util.CryptHash;
import com.dplot.util.ServletRequestInfoUtil;
import com.dplot.util.Util;

@Component
public class MemberAuthenticationProvider extends MallBaseService implements AuthenticationProvider {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberAuthenticationProvider.class);
	
	@Autowired
	private MemberAuthManagerService memberAuthManagerService;
	
	@Autowired
	private ComOrderMapper comOrderMapper;
	
	@Autowired
	private MemberMapper memberMapper;
	
	@Autowired
	private MemberHistoryMapper memberHistoryMapper;
	
	@Autowired
	private ERPService eRPService;
	
	@Autowired
	private UserMapper userMapper;

	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName(); 
		String password = (String) authentication.getCredentials();
		
		UserDetails user = null; 
		Collection<? extends GrantedAuthority> authorities = null;
		try {
			String logPrefix = cs.makeLogPrefix("authenticate");
			
			CustomWebAuthenticationDetails detail = (CustomWebAuthenticationDetails)authentication.getDetails();
			
			HttpServletRequest request = ServletRequestInfoUtil.getRequest();
			cs.init(request.getServerName());
			
			String mode = detail.getMode();
			
			//비회원
			if (Util.equal(mode, "guest")) {
				SOMap dbparams = new SOMap();

				dbparams.put("siteid", cs.getStr("siteid"));
				dbparams.put("ordno", detail.getOrdno());
				dbparams.put("ordname", detail.getOrdname());

				SOMap orderMap = comOrderMapper.selectComOrderNoneMember(dbparams);
				
				if (orderMap == null) {
					logger.info(String.format("%s 비회원 주문번호 불일치 아이디 [%s]", logPrefix, username));
					throw new BadCredentialsException(Message.ORDER_NOT_FOUND.getMsg()); 
				}

				NonMember nonMember = new NonMember();
				nonMember.setOrderIdx(orderMap.getDbInt("orderidx"));
				nonMember.setUserno(-1);
				nonMember.setName(orderMap.getDbStr("ordname"));
				nonMember.setOrdno(orderMap.getDbStr("ordno"));
				
				Role role = new Role();
				role.setName(RoleName.ROLE_NON_MEMBER.getName());
				
				List<Role> roles = new ArrayList<Role>();
				roles.add(role);
				nonMember.setAuthorities(roles);
				
				user = nonMember;
				authorities = user.getAuthorities();
				logger.info(String.format("%s 비회원 로그인 성공 아이디 : [%s], 권한 : [%s]", logPrefix, username,RoleName.ROLE_NON_MEMBER.toString()));
			} else {
				user = memberAuthManagerService.loadUserByUsername(username);
				
				//SNS Login이 아닐경우 비밀번호 체크
				if(Util.equal(mode, "sns") || Util.equal(mode, "bio")) {
					SOMap param = new SOMap();
					param.put("userid", user.getUsername());
					param.put("mode", detail.getMode());
					param.put("otherid", detail.getOtherid());
					
					int cnt = userMapper.selectCheckSnsBioInfo(param);
					if(cnt == 0) {
						if(Util.equal(mode, "sns")) {
							throw new BadCredentialsException(Message.USER_SNS_NOT_MATCH.getMsg());
						} else {
							throw new BadCredentialsException(Message.USER_BIO_NOT_MATCH.getMsg());
						}
					}else {
						if (Util.equal(mode, "sns")) {
							SOMap dbParam = new SOMap();
							dbParam.put("userid", param.get("userid"));
							SOMap memberInfo = memberMapper.selectMemberAdInfo(dbParam);
							if (!Util.isEmpty(detail.getIsadmailing()) || !Util.isEmpty(detail.getIsadsms())) {
								boolean isAd = false;
								// 기존 sms 와 email 광고성 수신여부 비교
								if (!memberInfo.getStr("isadsms").equals(detail.getIsadsms())) {
									dbParam.put("isadsms", detail.getIsadsms());
									isAd = true;
								}
								if (!memberInfo.getStr("isadmailing").equals(detail.getIsadmailing())) {
									dbParam.put("isadmailing", detail.getIsadmailing());
									isAd = true;
								}
								if (isAd) {
									//광고성이메일 & 광고성SMS 변경
									dbParam.put("userno", memberInfo.get("no"));
									memberMapper.updateMember(dbParam);
									SOMap historyInfo = new SOMap();
									//광고성이메일 변경 회원 변경이력
									if ((!Util.isEmpty(detail.getIsadmailing()))
											&& !memberInfo.getStr("isadmailing").equals(detail.getIsadmailing())) {
										historyInfo.put("userno", memberInfo.get("no"));
										historyInfo.put("mhistype", "MHT003");
										historyInfo.put("dadamembertype", memberInfo.get("dadamembertype"));
										historyInfo.put("memlvtype", memberInfo.get("memlvtype"));
										historyInfo.put("preval", memberInfo.get("isadmailing"));
										historyInfo.put("aftval", detail.getIsadmailing());
										historyInfo.put("reguserid", memberInfo.get("userid"));
										memberHistoryMapper.insertMemberHistory(historyInfo);
									}
									//광고성SMS 변경 회원 변경이력
									if ((!Util.isEmpty(param.getStr("isadsms"))) && !memberInfo.getStr("isadsms").equals(param.getStr("isadsms"))) {
										historyInfo.put("userno", memberInfo.get("no"));
										historyInfo.put("mhistype", "MHT004");
										historyInfo.put("dadamembertype", memberInfo.get("dadamembertype"));
										historyInfo.put("memlvtype", memberInfo.get("memlvtype"));
										historyInfo.put("preval", memberInfo.get("isadsms"));
										historyInfo.put("aftval", detail.getIsadmailing());
										historyInfo.put("reguserid", memberInfo.get("userid"));
										memberHistoryMapper.insertMemberHistory(historyInfo);
									}
									/**********************************
									 * ERP 회원정보 수정
									 **********************************/
									SOMap erpData = new SOMap();
									erpData.put("siteid", cs.getStr("siteid"));
									erpData.put("userno", memberInfo.get("no"));
									erpData.put("aud", "U");
									eRPService.insertMemberERPData(erpData);
								}	
							}
						}
					}
				} else {
					String hashedPassword = CryptHash.hash(password); 
					
					if (!hashedPassword.equals(user.getPassword())) {
						
						logger.info(String.format("%s 패스워드 불일치 아이디 [%s]", logPrefix, username));
						
						SOMap param = new SOMap();
						param.put("userid", user.getUsername());
						userMapper.increasePwFailCnt(param);
						int cnt = userMapper.selectPwFailCnt(param);
						if (cnt >= 5) {
							throw new BadCredentialsException(Message.USER_OR_PWD_NOT_MATCH_OVERTIME.getMsg());
						} else {
							throw new BadCredentialsException(Message.USER_OR_PWD_NOT_MATCH.getMsg());
						}
					} else {
						if(((Member)user).isSleep() == true) {
							throw new BadCredentialsException(Message.USER_STATUS_SLEEP.getMsg());
						}
					}
				}
				
				authorities = user.getAuthorities();
				
				logger.info(String.format("%s 회원 로그인 성공 아이디 : [%s], 권한 : [%s]", logPrefix, username,RoleName.ROLE_MEMBER.toString()));
			}
			
		} catch(UsernameNotFoundException e) { 
			logger.info(e.toString()); 
			throw e; 
		} catch(BadCredentialsException e) { 
			logger.info(e.toString()); 
			throw e; 
		} catch(Exception e) { 
			logger.info(e.toString());
			throw new RuntimeException("로그인 처리중 에러가 발생하였습니다.");
		} 
		return new UsernamePasswordAuthenticationToken(user, password, authorities);

	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

}

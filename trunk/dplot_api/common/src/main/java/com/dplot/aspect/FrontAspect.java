package com.dplot.aspect;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.dplot.annontation.FrontInfo;
import com.dplot.common.CMConst;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.mapper.CouponMapper;
import com.dplot.mapper.MemberMapper;
import com.dplot.security.model.Member;
import com.dplot.security.model.NonMember;

@EnableAspectJAutoProxy
@Component
@Aspect
public class FrontAspect extends MallBaseService{
	private static final Logger logger = LoggerFactory.getLogger(FrontAspect.class);
	
	//@Pointcut("execution(* com.dplot.front.controller.*.*(..))")
	//private void frontControllerMethodPointcut() {}
	
	//@Pointcut("within(com.dplot.common.controller.*)")
	//private void commonControllerMethodPointcut() {}
	@Autowired
	private MemberMapper memberMapper;
	
	@Autowired
	private CouponMapper couponMapper;
	
	@AfterReturning(value="execution(* com.dplot.front.controller.*.*(..))", returning="returnValue")
	public void afterReturning(JoinPoint joinPoint, Object returnValue) throws Exception {
		AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();
		
		Response res = (Response) returnValue;
		if(res == null) return;
		
		Authentication authentication = SecurityContextHolder. getContext(). getAuthentication();
		logger.debug(authentication + "");

		Map<String, Object> resultData = res.getData();
		if(resultData == null) resultData = new HashMap<String, Object>();
		
		if(authentication == null || trustResolver.isAnonymous(authentication)) {
			SOMap map = new SOMap();
			map.put("authType", "nonMember");
			map.put("userid", CMConst.GUEST_ID);
			map.put("userNo", 0);
			map.put("memberType", "");
			map.put("name", "비회원");
			
			resultData.put("memberinfo", map);
		} else {
			Object principal = authentication.getPrincipal();
			
			if(principal instanceof Member) {
				Member member = (Member) principal;
				SOMap map = new SOMap();
				map.put("authType", "member");
				map.put("userId", cs.getStr("authmemberid"));
				map.put("userNo", cs.getInt("authmemberno"));
				map.put("memberType", cs.getStr("authmembertype"));
				map.put("name", cs.getStr("authmembername"));
				map.put("memlvtype", cs.getStr("authmemlvtype"));
				map.put("joinchtype", member.getJoinchtype());
				map.put("memlvtypenm", cs.getStr("authmemlvtypenm"));
				
				MethodSignature signature = (MethodSignature) joinPoint.getSignature();
			    Method method = signature.getMethod();

			    FrontInfo frontInfo = method.getAnnotation(FrontInfo.class);
			    
				if(frontInfo != null) {
					SOMap param = new SOMap();
					param.put("userno", member.getUserno());
					param.put("siteid", cs.getStr("siteid"));
					SOMap memberEtcInfo = memberMapper.selectMemberEtcInfo(param);
					map.putAll(memberEtcInfo);
				}
				
				resultData.put("memberinfo", map);
			} else if(principal instanceof NonMember) {
				NonMember nonMember = (NonMember) principal;
				SOMap map = new SOMap();
				map.put("authType", "nonMember");
				map.put("userid", CMConst.GUEST_ID);
				map.put("userNo", -1);
				map.put("memberType", "");
				map.put("name", nonMember.getName());
				map.put("orderIdx", nonMember.getOrderIdx());
				
				resultData.put("memberinfo", map);
			}
		}
		
		res.setData(resultData);
		
		logger.debug("frontInfo : " + returnValue);
	}
}

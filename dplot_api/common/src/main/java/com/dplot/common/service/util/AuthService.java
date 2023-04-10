package com.dplot.common.service.util;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.util.CookieGenerator;

import com.dplot.common.CMConst;
import com.dplot.common.SOMap;
import com.dplot.mapper.MemberMapper;
import com.dplot.mapper.UserTotalMapper;
import com.dplot.security.domain.RoleName;
import com.dplot.security.model.Dealer;
import com.dplot.security.model.Member;
import com.dplot.security.model.NonMember;
import com.dplot.security.model.User;
import com.dplot.util.DateTimeUtil;
import com.dplot.util.ServletRequestInfoUtil;
import com.dplot.util.Util;

/**
 * 중요 Session
 *  cs.getStr("AUTH_KEY") :
 *  	중복 로그인을 위하여, v2에서 변형된 값을 가진다.
 *  	SOMap형태로 userno를 키로 가지며 값은 SOMap (회원 타입에 따라 id, kind, type이 있을 수 있다.)
 *  	guest_id 가 키 일경우는 Guest유저로 취급됨. 값은 SOMap (order, name)
 *  	Guest 유저는 다른 세션이 있을경우 생성될 수 없다.
 *  	Guest 유저는 다른 세션이 로그인 하는 순간 사라진다.
 * 
 *  cs.getStr("AUTH_KEY_ADMIN") : SOMap 형태로 name을 가짐
 *  cs.getStr("AUTH_KEY_PARTNER") : SOMap 형태로 name을 가짐
 *  cs.getStr("AUTH_KEY_DEALER") : SOMap 형태로 name, type을 가짐
 *  cs.getStr("AUTH_KEY_MEMBER") : SOMap 형태로 name, type을 가짐
 * 
 * 중요 쿠키
 *  cs.getStr("AUTH_KEY")
 *  	cs.getStr("AUTH_KEY_CODE")=cookieCode&cs.getStr("AUTH_KEY_TIME")=현재날짜
 *  cookieCode 는 라인피드를 구분자로 하여 userno, 구분키 를 중복로그린을 위하여 복수개 가지고 있을 수 있다.
 */
@Service
public class AuthService extends MallBaseService {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

	@Autowired
	@Qualifier("adminSecurityContextRepository")
	private SecurityContextRepository adminSecurityContextRepository;
	
	@Autowired
	@Qualifier("dealerSecurityContextRepository")
	private SecurityContextRepository dealerSecurityContextRepository;
	
	@Autowired
	@Qualifier("memberSecurityContextRepository")
	private SecurityContextRepository memberSecurityContextRepository;
	
	@Autowired
	@Qualifier("memberAuthManagerService")
	private UserDetailsService memberAuthManagerService;
	
	@Autowired
	private MemberMapper memberMapper;
	
	@Autowired
	@Qualifier("memberLogoutSuccessHandler")
	private LogoutSuccessHandler logoutSuccessHandler;
	
	/**
	 * 관리자 접근 아이피 체크
	 *
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean adminLoginIPCheck() throws Exception {
		String ipcheck = cs.getStr("cfgisadminaccessip");
		if (logger.isDebugEnabled()) {
			logger.debug("admin ip check flag : " + ipcheck);
		}
		
		if (! Util.flag2Bool(ipcheck)) return true;
		
		//return adminBackofficeService.checkAdminAccessIp();
		return true;
	}

	/**
	 * 한글명 조사 설정
	 *
	 * @throws Exception the exception
	 */
	public void setSiteHantail() throws Exception {
		String cfgPostWord = cs.getStr("cfgpostword");
		String CM_POSTWORD_WA = cs.getStr("CM_POSTWORD_WA");

		if (Util.equal(cfgPostWord, CM_POSTWORD_WA)) {
			cs.setString("cfgpostwordun", "는");
			cs.setString("cfgpostwordig", "가");
			cs.setString("cfgpostwordur", "를");
			cs.setString("cfgpostwordwg", "와");
		} else {
			cs.setString("cfgpostwordun", "은");
			cs.setString("cfgpostwordig", "이");
			cs.setString("cfgpostwordur", "을");
			cs.setString("cfgpostwordwg", "과");
		}
	}

	/**
	 * 세션 아이디 세팅
	 *
	 * @param request the request
	 * @param response the response
	 * @param session the session
	 * @throws Exception the exception
	 */
	public void setSessID(HttpServletRequest request, HttpServletResponse response, HttpSession	session) throws Exception {

		String COOKIE_SESS = CMConst.COOKIE_SESS;
		String cookiedomain = cs.getStr("cookiedomain");
		
		// SimpleCookie에서 쿠키를 꺼낼때 null 만 나와서, SESSID가 계속 초기화되는 문제있음.
		// 솔루션이랑 차이를 못찾아서 임시방편으로 주석처리하고 request에서 직접 꺼냄
		// SimpleCookie cookie = new SimpleCookie(request);
		String cSessId = "";
		if (request.getCookies() != null && request.getCookies().length > 0) {
			for (int i = 0; i < request.getCookies().length; ++i) {
				if (COOKIE_SESS.equals(request.getCookies()[i].getName())) {
					cSessId = request.getCookies()[i].getValue();
				}
			}
		}
		// String cSessId = cookie.getValue(COOKIE_SESS).trim();
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("COOKIE_SESS[%s] cookiedomain[%s] cSessId[%s]"
				, COOKIE_SESS, cookiedomain, cSessId));
		}
		if (Util.isEmpty(cSessId)) {
			cSessId = String.valueOf(DateTimeUtil.getNowUnixTimeStamp()) + session.getId();
			
			CookieGenerator cg = new CookieGenerator();
			cg.setCookieDomain(cookiedomain);
			cg.setCookiePath("/");
			cg.setCookieName(COOKIE_SESS);
			cg.addCookie(response, URLEncoder.encode(cSessId, "UTF-8"));
			cg.setCookieHttpOnly(true);
			
			if (logger.isDebugEnabled()) {
				logger.debug(String.format("COOKIE SET cSessId[%s]", cSessId));
			}
		}
		cs.setString("csessid", cSessId);
	}


	/**
	 * 로그인 시큐어코드와 쿠키의 시큐어코드가 같은지 대조한다.
	 * t_user_total 테이블 삭제로 실제 동작하지 않는 코드 (2022-04-01)
	 * @param userno
	 * @param secureCode
	 * @return
	 * @throws Exception
	 */
	private boolean isSameSecureCode(int userno, String secureCode) throws Exception {
		SOMap dbparams = new SOMap();
		dbparams.put("userno", userno);
		dbparams.put("secureCode", secureCode);
		int cnt = 0;
		// cnt = userTotalMapper.selectUserCountByUserNoandSecureCode(dbparams);
		if (logger.isDebugEnabled()) {
			logger.debug("selectUserCountByUserNoandVisitIp : " + cnt);
		}
		return cnt > 0;
	}

	
	//시큐리티 강제 로그아웃
	public void logoutSecurity(RoleName role, boolean isIncludeAutoLogin) throws Exception {
		
		HttpServletRequest request = ServletRequestInfoUtil.getRequest();
		
		if (role == RoleName.ROLE_ADMIN) {
			request.logout();
			cs.setInt("authuserno", 0);
			cs.setString("authuserid", "");
			cs.setInt("authadminno", 0);
			cs.setString("authadminid", "");
			cs.setString("authadminname", "");
			cs.setString("authadminmanager", "");
		} else if (role == RoleName.ROLE_DEALER) {
			request.logout();
			cs.setInt("authuserno", 0);
			cs.setString("authuserid", "");
			cs.setInt("authdealerno", 0);
			cs.setString("authdealerid", "");
			cs.setString("authdealername", "");
			cs.setInt("authdealertype", 0);
		} else if (role == RoleName.ROLE_MEMBER) {
			cs.setInt("authmemberno", 0);
			cs.setString("authmemberid", "");
			cs.setString("authmembername", "");
			cs.setInt("authmembertype", 0);
			cs.setString("authmemberlevelname", "");
			cs.setInt("authguestorder", 0);
			cs.setStr("authguestname", "");
			
			//다필요없고 자동로그인은 중복체크 하지 않는다.
			request.logout();
			
		} else if (role == RoleName.ROLE_NON_MEMBER) {
			request.logout();
			cs.setInt("authuserno", 0);
			cs.setStr("authuserid", "");
			cs.setInt("authguestorder", 0);
			cs.setStr("authguestname", "");
		}
		
	}
	
	//시큐리티용 새로 추가한것
	public void defineSecurityAuthentication(HttpServletRequest request,
											 HttpServletResponse response, HttpSession session) throws Exception {
		
		HttpRequestResponseHolder holder = new HttpRequestResponseHolder(request,response);
		Authentication adminAuthentication = adminSecurityContextRepository.loadContext(holder).getAuthentication();
		Authentication dealerAuthentication = dealerSecurityContextRepository.loadContext(holder).getAuthentication();
		Authentication memberAuthentication = memberSecurityContextRepository.loadContext(holder).getAuthentication();
		
		List<Object> principalList = new ArrayList<>();
		if (adminAuthentication != null) {
			Object adminUser = adminAuthentication.getPrincipal();
			principalList.add(adminUser);
		}
		if (dealerAuthentication != null) {
			Object dealerUser = dealerAuthentication.getPrincipal();
			principalList.add(dealerUser);
		}
		if (memberAuthentication != null) {
			Object memberUser = memberAuthentication.getPrincipal();
			principalList.add(memberUser);
		}
		
		if (principalList.size() == 0) {
			//return Const.D_RETURN_AUTH_FAIL;
		}
		
		for (Object user : principalList) {
			int userNo = 0;
			RoleName role = null;
			String secureCode = "";
			boolean allowDupLogin = true;
			boolean isAutoLogin = false;
			if (user instanceof User) {//관리자
				User userInfo = (User)user;
				cs.setInt("authuserno", userInfo.getNo());
				cs.setString("authuserid", userInfo.getUserid());
				cs.setInt("authadminno", userInfo.getNo());
				cs.setString("authadminid", userInfo.getUserid());
				cs.setString("authadminname", userInfo.getName());
				cs.setString("authadminmanager", Util.bool2Flag(true));
				cs.setString("usertype", userInfo.getUsertype());
//				if (userInfo.getUsertype().equals(CMConst.USER_TYPE_MANAGER)) {
//					cs.setString("authadminmanager", Util.bool2Flag(true));
//				} else {
//					cs.setString("authadminmanager", Util.bool2Flag(false));
//				}
				userNo = userInfo.getNo();
				role = RoleName.ROLE_ADMIN;
				secureCode = userInfo.getSecurecode();
			} else if (user instanceof Dealer) {//판매자
				Dealer userInfo = (Dealer)user;
				cs.setInt("authuserno", userInfo.getUserno());
				cs.setString("authdealerid", userInfo.getUserid());
				cs.setInt("authdealerno", userInfo.getUserno());
				cs.setString("authdealername", userInfo.getName());
				cs.setString("usertype", CMConst.USER_TYPE_PARTNER);
				userNo = userInfo.getUserno();
				role = RoleName.ROLE_DEALER;
				secureCode = userInfo.getSecurecode();
			} else if (user instanceof Member) {//사용자
				Member memberInfo = (Member)user;
				cs.setInt("authmemberno", memberInfo.getUserno());
				SOMap param = new SOMap();
				param.put("userno", memberInfo.getUserno());
				SOMap member = memberMapper.selectMember(param);
				cs.setString("authmemberid", memberInfo.getUserid());
				cs.setString("authmembername", member.getStr("name"));
				cs.setString("authmembertype", member.getStr("dadamembertype"));
				cs.setString("authmemlvtype", member.getStr("memlvtype"));
				cs.setString("authmemlvtypenm", member.getStr("memlvtypenm"));
				// guest 가 세팅되어 있다면 없애준다.
				cs.setInt("authguestorder", 0);
				cs.setStr("authguestname", "");
				cs.setString("usertype", CMConst.USER_TYPE_MEMBER);
				userNo = memberInfo.getUserno();
				role = RoleName.ROLE_MEMBER;
				secureCode = memberInfo.getSecurecode();
				isAutoLogin = memberInfo.isAutoLogin();
				cs.setString("authmemberrole", role.getName());
			} else if (user instanceof NonMember) {
				NonMember nonMemberInfo = (NonMember) user;
				cs.setInt("authuserno", -1);
				cs.setStr("authuserid", CMConst.GUEST_ID);
				cs.setInt("authguestorderidx", nonMemberInfo.getOrderIdx());
				cs.setStr("authguestordno", nonMemberInfo.getOrdno());
				cs.setStr("authguestname", nonMemberInfo.getName());
				
				role = RoleName.ROLE_NON_MEMBER;
				cs.setString("authmemberrole", role.getName());
			}
			
			if (!allowDupLogin && userNo > 0 && ! isAutoLogin) {
				boolean isSameSecureCode = this.isSameSecureCode(userNo, secureCode);
				if (! isSameSecureCode) {
					if (logger.isDebugEnabled()) {
						logger.debug(String.format("RETURN SECURECODE MISMATCH[%s] no[%d]",secureCode, userNo));
					}
					logoutSecurity(role,false);
				}
			}
		}

	}
	
}
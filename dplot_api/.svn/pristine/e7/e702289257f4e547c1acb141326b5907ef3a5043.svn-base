package com.dplot.interceptor;

import javax.inject.Provider;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

import com.dplot.common.CMConst;
import com.dplot.common.service.CommonTrackerService;
import com.dplot.common.service.MallConfigService;
import com.dplot.common.service.util.AuthService;
import com.dplot.util.Util;

public class IndexInterceptor  extends WebContentInterceptor {
	
	@Autowired
    protected MallConfigService cs;
	
	@Autowired
    private AuthService authService;
	
	@Autowired
    private Provider<CommonTrackerService> trackerProvider;
	
	
	@Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws ServletException  {
        try {
            CommonTrackerService tracker = trackerProvider.get();

            HttpSession session = request.getSession(true);
            String requestURI = request.getRequestURI();

            tracker.setIp(Util.getClientIp(request));

            logger.debug(String.format("========== IndexInterceptor preHandle Start req[%s] ==========", requestURI));

//            Enumeration<String> headerNames = request.getHeaderNames();
//            while(headerNames.hasMoreElements()){
//                String name = (String)headerNames.nextElement();
//                String value = request.getHeader(name);
//                if (logger.isDebugEnabled()) logger.debug(name + " : " + value);
//            }
            
            boolean _init = cs.init(request.getServerName());
            // boolean _init = cs.initdb(request.getServerName());
            if (! _init) {
                throw new Exception("등록되지 않은 사이트 계정 입니다.");
            }
            tracker.add("initdb", request.getServerName());
            
            cs.setStr("platform", request.getHeader("platform"));
            cs.setStr("ismobile", CMConst.CM_PLATFORM_PC.equals(request.getHeader("platform")) ? "F" : "T");
            
            logger.debug("platform : " + cs.getStr("platform"));
            logger.debug("ismobile : " + cs.getStr("ismobile"));
            
            // 접근 도메인 확인 : 기본 도메인으로 REDIRECT (SSL 사용 및 기타 외부 연동시 필요)
            String sitehost = cs.getStr("sitehost");
            String domain = cs.getStr("cfgdomain");
            String url = requestURI;
            String query = request.getQueryString();


            logger.debug(String.format("sitehost[%s] domain[%s] url[%s] query[%s]", sitehost, domain, url, query));
            

            authService.setSessID(request, response, session);

            // Call defineAuth
            authService.defineSecurityAuthentication(request, response, session);
            
            tracker.setAdminNo(cs.getInt("authadminno"));
            tracker.setMemberNo(cs.getInt("authmemberno"));
            tracker.setSess(cs.getSessPrefix());
            tracker.setFlatform(cs.getStr("cfgflatform"));

        } catch (Exception e) {
            logger.error("", e);
        }

        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request,
                            HttpServletResponse response,
                            Object handler,
                            ModelAndView mv) throws Exception {
        logger.debug("MallInterceptor postHandle Start");
        
    }


    @Override
    public void afterCompletion(HttpServletRequest req,
                                HttpServletResponse res,
                                Object handler,
                                Exception ex) throws Exception {
        logger.debug("MallInterceptor afterCompletion Start");

        CommonTrackerService tracker = trackerProvider.get();
        long diff = System.currentTimeMillis() - tracker.getCTime();
        String log = String.format("%d %s", diff, tracker.toStr());

        if (logger.isInfoEnabled()) logger.info(log);
        
        if (ex != null) {
            String requestURI = req.getRequestURI();
            logger.error(requestURI, ex);
        }

    }
}

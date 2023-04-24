package com.dplot.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 스태틱 서블릿 접근 클래스.
 */
public class ServletRequestInfoUtil {

	/**
	 * Gets the request.
	 *
	 * @return the request
	 */
	public static HttpServletRequest getRequest() {
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return req;
	}
	
	/**
	 * Gets the request.
	 *
	 * @return the request
	 */
	public static HttpServletResponse getResponse() {
		HttpServletResponse res = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
		return res;
	}

	/**
	 * Gets the session.
	 *
	 * @param create the create
	 * @return the session
	 */
	public static HttpSession getSession(boolean create) {
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return req.getSession(create);
	}
	public static HttpSession getSession() {
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return req.getSession();
	}

	/**
	 * Gets the request domain.
	 *
	 * @return the request domain
	 */
	public static String getRequestDomain() {
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return Util.hasText(req.getServerName()) ? req.getServerName() : "";
	}

	/**
	 * 접속 아이피 조회
	 *
	 * @return the request ip
	 */
	public static String getRequestIp() {
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return Util.getClientIp(req);
		//return Util.hasText(req.getRemoteAddr()) ? req.getRemoteAddr() : "";
	}

	/**
	 * 접속 포트 조회
	 *
	 * @return the request port
	 */
	public static int getRequestPort() {
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return req.getServerPort() ;
	}

	/**
	 * Gets the request ssl.
	 *
	 * @return the request ssl
	 */
	public static String getRequestSSL() {
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return String.valueOf(req.isSecure()) ;
	}

	/**
	 * 접속 쿼리스트링 조회
	 *
	 * @return the query string
	 */
	public static String getQueryString() {
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return String.valueOf(req.getQueryString()) ;
	}

	/**
	 * 접속 경로 조회
	 *
	 * @return the servlet path
	 */
	public static String getServletPath() {
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String  path = req.getServletPath();
		return path;
	}

	public static String getRequestURL() {
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String  path = req.getRequestURL().toString();
		return path;
	}

	/**
	 * 접속 이전 경로 조회
	 *
	 * @return the referer
	 */
	public static String getReferer() {
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String  path = req.getHeader("referer");
		return path;
	}
	
	
	public static String getLocalServerIp() {
		try {
		    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
		        NetworkInterface intf = en.nextElement();
		        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
		            InetAddress inetAddress = enumIpAddr.nextElement();
		            if (! inetAddress.isLoopbackAddress() && 
		            	! inetAddress.isLinkLocalAddress() && 
		            	inetAddress.isSiteLocalAddress()) {
		            	
		            	return inetAddress.getHostAddress().toString();
		            }
		        }
		    }
		} catch (SocketException ex) {}
		
		return null;
	}
	
	public static boolean isMobileFlatform(){
		String userAgent = getUserAgent();
		
		if(userAgent.indexOf("mobile") > - 1  ){
			return true;
		}
		return false;
	}
	
	public static String getUserAgent(){
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		if(req.getHeader("User-Agent") != null){
			return req.getHeader("User-Agent").toLowerCase();
		}else{
			return "";
		}
	}

}

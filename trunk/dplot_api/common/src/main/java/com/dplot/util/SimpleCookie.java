/**
 *
 */
package com.dplot.util;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dplot.common.SOMap;

/**
 * 쿠키 관리용 클래스
 */
public class SimpleCookie {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(SimpleCookie.class);

	/** The cookies. */
	private Cookie[] cookies = null;

	/** The cookie map. */
	private SOMap cookieMap = new SOMap();

	/**
	 * 생성자
	 *
	 * @param request the request
	 */
	public SimpleCookie(HttpServletRequest request) {
		this.cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : this.cookies) {
				String value = null;
				String key = cookie.getName();

				try {
					value = URLDecoder.decode(cookie.getValue(), "UTF-8");
				} catch (Exception e) {
					value = cookie.getValue();
				}
				this.cookieMap.put(key, value);
				try {
					if (value.indexOf("&") > -1) {
						parseData(key, value);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 쿠키 데이터 파싱
	 *
	 * @param key the key
	 * @param value the value
	 */
	private void parseData(String key, String value) {
		String[] datas = value.split("&");
		for (String data : datas) {
			int idx = data.indexOf("=");
			if (idx > 1) {
				String tmpkey = key + "." + data.substring(0, idx);
				String tmpval = data.substring(idx+1);
				this.cookieMap.put(tmpkey, tmpval);
			}
		}
	}

	/**
	 * 쿠키 생성
	 *
	 * @param name the name
	 * @param value the value
	 * @return the cookie
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Cookie createCookie(String name, String value) throws IOException {
		return new Cookie(name, URLEncoder.encode(value, "UTF-8"));
	}

	/**
	 * 쿠키 생성
	 *
	 * @param name the name
	 * @param value the value
	 * @param path the path
	 * @param maxAge the max age
	 * @return the cookie
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Cookie createCookie(String name, String value, String path, int maxAge) throws IOException {
		Cookie cookie = new Cookie(name, URLEncoder.encode(value, "UTF-8"));
		cookie.setPath(path);
		cookie.setMaxAge(maxAge);
		return cookie;
	}

	/**
	 * 쿠키 생성
	 *
	 * @param name the name
	 * @param value the value
	 * @param domain the domain
	 * @param path the path
	 * @param maxAge the max age
	 * @return the cookie
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Cookie createCookie(String name, String value,  String domain, String path, int maxAge) throws IOException {
		Cookie cookie = new Cookie(name, URLEncoder.encode(value, "UTF-8"));
		cookie.setDomain(domain);
		cookie.setPath(path);
		cookie.setMaxAge(maxAge);
		return cookie;
	}

	/**
	 * 쿠키값 획득
	 *
	 * @param name the name
	 * @return the value
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public String getValue(String name) throws IOException {
		if (name != null) {
			Iterator<String> keys = this.cookieMap.keySet().iterator();
			while(keys.hasNext()) {
				String key = keys.next();
				// 2021.12.13 
				// SOMap이 put할때 대소문자를 소문자로 바꿔서 대문자 쿠키(ex:GDLOG)를 못찾음 => 임시로toLowerCase처리함!
				// 솔루션 SOMap에는 put이 없어서 문제없음!
				if (key.toLowerCase().equals(name.toLowerCase())) return this.cookieMap.getStr(name.toLowerCase());
			}
		}
		return "";
	}

	/**
	 * 쿠키값 존재 여부 확인
	 *
	 * @param name the name
	 * @return true, if successful
	 */
	public boolean exists(String name) {
		return cookieMap.get(name) != null;
	}

}
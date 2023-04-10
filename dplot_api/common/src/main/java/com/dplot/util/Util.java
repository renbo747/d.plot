package com.dplot.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.dplot.common.SOMap;

import net.sf.json.JSONObject;

/**
 * 공통함수를 위한  클래스.
 *
 * @version 1.0
 */
public class Util {
		
	private static final Logger logger = LoggerFactory.getLogger(Util.class);

	private static final int NOPOS = -1;

	/**
	 * from 을 카피한 새로운 맵을 만든다.
	 *
	 * @param map
	 * @return 새로운 HashMap<String, Object>
	 */
	public static HashMap<String, Object> copyMap(Map<String, Object> from) {
		HashMap<String, Object> to = new HashMap<String, Object>();
		Set<String> set = from.keySet();
		for (String s:set) {
			to.put(s, from.get(s));
		}

		return to;
	}

	/**
	 * 기존맵에 맵을 추가한다.
	 *
	 * @param org 기존맵
	 * @param append 추가 할 맵
	 * @param mode lo,hi,none  맵의 키를 대소문자로 지정가능함.
	 * @return append가 추가된 기존 맵
	 */
	public static void mergeMap(Map<String, Object> org, Map<String, Object> append, String mode) {
		Set<String> set = append.keySet();
		for (String s:set) {
			String newkey = s;

			if ("lo".equals(mode)) {
				newkey = s.toLowerCase();
			}
			else if ("hi".equals(mode)) {
				newkey = s.toUpperCase();
			}

			org.put(newkey, append.get(s));
		}
	}

	/**
	 * T 면 참을 리턴한다.
	 *
	 * @param val the val
	 * @return true, 만약 파라미터가 T라면
	 */
	public static boolean flag2Bool(String val) {
		return "T".equals(val);
	}

	/**
	 * 불리언값을 T 또느 F 로 반환
	 *
	 * @param boolean b
	 * @return T 또느 F
	 */
	public static String bool2Flag(boolean b) {
		return b ? "T" : "F";
	}

	/**
	 * 불리언값을 T 또느 F 로 반환
	 *
	 * @param boolean b
	 * @return T 또느 F
	 */
	public static String bool2Flag(String b) {
		return b.equals("true") ? "T" : "F";
	}
	
	/**
	 * 플래그는 T와 F 값 둘중의 하나를 가지며
	 * T 가 아닌경우에는 무조건 F 로 반환한다.
	 *
	 * @param value the value
	 * @return the string
	 */
	public static String toFlag(String value) {
		return ("T".equals(value)) ? "T" : "F";
	}

	/**
	 * Dec request.
	 *
	 * @param value the value
	 * @return the string
	 */
	public static String decRequest(String value) {
		String ret = "";
		ret = Converter.toStr(value);
		ret = ret.replace("&#39;", "'");
		ret = ret.replace("&quot;", "\"");
		ret = ret.replace("&gt;", ">");
		ret = ret.replace("&lt;", "<");
		ret = ret.replace("&amp;", "&");

		return ret;
	}

	/**
	 * 몰스토어에서는 일반 페이지외에 특수한 페이지타입을 url 에서 추출가능하다.
	 * XXX_act.do, XXX_ifrm.do  등
	 * act, ajax, pop, ifrm
	 *
	 * @param uri the uri
	 * @return String act, ajax, pop, ifrm
	 */
	public static String getPageType(String uri) {
		String page = StringUtils.getFilename(uri);

		String name = StringUtils.stripFilenameExtension(page);
		String[] name_arr = name.split("_");
		String type = (name_arr.length > 1) ? name_arr[name_arr.length-1] : name;

		if (logger.isDebugEnabled()) {
			logger.debug(String.format("uri[%s] page[%s] name[%s] type[%s]", uri, page, name, type));
		}

		return type;
	}

	/**
	 * 프론트 페이지의 형태 분석
	 *
	 * @param uri the uri
	 * @return the front page info
	 */
	public static String[] getFrontPageInfo(String uri) {
		String[] strs = null;

		if (hasText(uri) && ! "/".equals(uri)) {
			String[] arr = uri.split("/");
			String lastdir = arr[arr.length - 2];
			String page = arr[arr.length - 1];

			String name = StringUtils.stripFilenameExtension(page);
			String[] name_arr = name.split("_");

			strs = new String[name_arr.length + 1];
			strs[0] = lastdir;

			for (int i=0; i<name_arr.length; i++) {
				strs[i+1] = name_arr[i];
			}
		}

		return strs;
	}

	/**
	 * 싱글쿼테이션 더블쿼테이션 치환
	 *
	 * @param value the value
	 * @return String
	 */
	public static String encQuote(String value) {
		return value == null ? "" : value.replaceAll("'", "&#39;").replaceAll("\"","&quot;");
	}

	/**
	 * encQuote의 역변환
	 *
	 * @param value the value
	 * @return the string
	 */
	public static String decQuote(String value) {
		return value == null ? "" : value.replaceAll("&#39;", "'").replaceAll("&quot;","\"");
	}


	/**
	 * URL 인코딩된 특수문자 변환
	 *
	 * @param value the value
	 * @return String
	 */
	public static String decUrlCharMap(String value) {
		String return_str = null;
		if (value != null) {
			return_str = value;
			return_str = return_str.replaceAll("%2C", ",");
		}
		return return_str;
	}

	/**
	 * 유니크한 값 추출
	 *
	 * @return String
	 * @Method Name  : getGUID
	 * @Method 유니크한 값 추출
	 */
	public static String getGUID() {
		String result;
		result = UUID.randomUUID().toString();
		result = result.replaceAll("-", "");

		return result;
	}

	/**
	 * 비트연산 설정
	 *
	 * @param arrBit the arr bit
	 * @return the int
	 */
	public static int calcBitwise(String[] arrBit) {
		int[] intarr = new int[arrBit.length];
		int idx = 0;
		for (String s : arrBit) {
			intarr[idx++] = Converter.toInt(s);
		}

		return calcBitwise(intarr);
	}

	/**
	 * 비트연산 설정
	 *
	 * @param arrBit the arr bit
	 * @return the int
	 */
	public static int calcBitwise(int[] arrBit) {
		int result = 0;

		for (int f=0; f < arrBit.length; f++) {
			if (arrBit[f] > 0) result = (result | arrBit[f]);
		}

		return result;
	}

	/**
	 * 비트연산 제거
	 *
	 * @param haystack the haystack
	 * @param removal the removal
	 * @return the int
	 */
	public static int remBitwise(int haystack, int removal) {
		if ((haystack&removal) > 0) haystack = haystack^removal;

		return haystack;
	}

	/**
	 * 비트연산 확인
	 *
	 * @param haystack Or 연산 비트값
	 * @param needle 비교대상 비트값
	 * @return true, if successful
	 */
	public static boolean checkBitwise(int haystack, int needle) {
		return (haystack & needle) > 0 ? true : false;
	}

	/**
	 * 숫자조사
	 *
	 * @param str the str
	 * @return true, if is number
	 */
	public static boolean isNumber(String str) {
		if (str == null) return false;

		Pattern p = Pattern.compile("([\\p{Digit}]+)(([.]?)([\\p{Digit}]+))?");
		Matcher m = p.matcher(str);

		return m.matches();
	}

	/**
	 * 문자열 정규식 조사
	 *
	 * @param value 검사 문자열
	 * @param pattern 정규식
	 * @return true, if successful
	 */
	public static boolean eRegiTest(String value, String pattern) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(value);
		if (m.find()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 문자열 정규식 치환
	 *
	 * @param value the value
	 * @param pattern the pattern
	 * @param replacement the replacement
	 * @return the string
	 */
	public static String eRegiReplace(String value,String pattern,String replacement) {
		String resultStr = "";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(value);
		if (m.matches()) {
			resultStr = m.replaceAll(replacement);
		} else {
			resultStr = value;
		}

		return resultStr;
	}

	/**
	 * 문자열에서 Tag 제거
	 *
	 * @param value the value
	 * @return the string
	 */
	public static String stripTags (String value) {
		String pattern = "<[/]*(" +
				"!|a|abbr|acronym|address|applet|area|b|base|basefont|bdo|big|blockquote|body|br|button|caption|center|cite|code|col|colgroup|" +
				"dd|del|dfn|dir|div|dl|dt|em|fieldset|font|form|frame|frameset|h1|h2|h3|h4|h5|h6|head|hr|html|i|iframe|img|input|ins|isindex|kbd|" +
				"label|legend|li|link|map|menu|meta|noframes|noscript|object|ol|option|p|param|pre|q|" +
				"s|samp|script|select|small|span|strike|strong|style|sub|sup|table|tbody|td|textarea|tfoot|th|thead|title|tr|tt|u|ul|var|\\?xml|o:" +
			")[^>]*>";
		return eRegiReplace(value, pattern, "");
	}

	/**
	 * 문자열에서 Tag 제거
	 *
	 * @param value the value
	 * @return the string
	 */
	public static String stripTagsToReplace(String value, String replaceValue) {
		if (replaceValue == null) {
			replaceValue = "";
		}

		return value.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>",  replaceValue);
	}

	/**
	 * 값 비교후 readonly 처리
	 *
	 * @param arg1 비교값 1
	 * @param arg2 비교값 2
	 * @return the string
	 */
	public static String setReadOnly(String arg1, String arg2) {
		return (arg1.equals(arg2)) ? " readonly" : "";
	}

	/**
	 * 값 비교후 readonly 처리
	 *
	 * @param arg1 the arg1
	 * @param arg2 the arg2
	 * @return the string
	 */
	public static String setReadOnly(boolean arg1, boolean arg2) {
		return (arg1 == arg2) ? " readonly" : "";
	}

	/**
	 * 값 비교후 checked 처리
	 *
	 * @param arg1 비교값 1
	 * @param arg2 비교값 2
	 * @return the string
	 */
	public static String setChecked(String arg1, String arg2) {
		return (arg1.equals(arg2)) ? " checked" : "";
	}

	/**
	 * 값 비교후 checked 처리
	 *
	 * @param arg1 the arg1
	 * @param arg2 the arg2
	 * @return the string
	 */
	public static String setChecked(boolean arg1,boolean arg2) {
		return (arg1 == arg2) ? " checked" : "";
	}

	/**
	 * 값 비교후 checked 처리
	 *
	 * @param arg1 the arg1
	 * @param arg2 the arg2
	 * @return the string
	 */
	public static String setChecked(int arg1,int arg2) {
		return (arg1 == arg2) ? " checked" : "";
	}

	/**
	 * 값 비교후 selected 처리
	 *
	 * @param arg1 비교값 1
	 * @param arg2 비교값 2
	 * @return the string
	 */
	public static String setSelected(String arg1, String arg2) {
	  return (arg1.equals(arg2)) ? " selected" : "";
	}

	/**
	 * 값 비교후 disabled 처리
	 *
	 * @param arg1 비교값 1
	 * @param arg2 비교값 2
	 * @return the string
	 */
	public static String setDisabled(String arg1, String arg2) {
		  return (arg1.equals(arg2)) ? " disabled" : "";
	}

	/**
	 * 값 비교후 disabled 처리
	 *
	 * @param arg1 the arg1
	 * @param arg2 the arg2
	 * @return the string
	 */
	public static String setDisabled(boolean arg1, boolean arg2) {
		  return (arg1 == arg2) ? " disabled" : "";
	}

	/**
	 * 숫자 0 이 들어왔을때 빈값으로 치환
	 *
	 * @param value the value
	 * @return the string
	 */
	public static String zero2Blank(int value) {
		return value == 0 ? "" : String.format("%d", value);
	}

	/**
	 * 숫자 0 이 들어왔을때 빈값으로 치환
	 *
	 * @param value the value
	 * @return the string
	 */
	public static String zero2Blank(String value) {
		return "0".equals(value) ? "" : value;
	}

	/**
	 * 파라미터 인코딩
	 *
	 * RFC 3986 허용 문자열 적용
	 * ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-._~:/?#[]@!$&'()*+,;=`.
	 *
	 * @param value the value
	 * @return the string
	 */
	public static String encParams(String value) {
//		return value == null ? "" : value.replaceAll("&amp;", "&").replaceAll("&", "^");
		
		String enc = "";
		if (value != null) {
			enc = value.replaceAll("&amp;", "&").replaceAll("&", "^");
			return CryptAES.toHex(enc);
		} else {
			return "";
		}
		
		//return value == null ? "" : value.replaceAll("&amp;", "&").replaceAll("&", "@@");
	}

	/**
	 * 파라미터 디코딩
	 *
	 * @param value the value
	 * @return the string
	 */
	public static String decParams(String value) {
		value = CryptAES.fromHex(value);
		
		String[] arrParams = null;
		String newParams = "";

		// 한글 파라미터 인코딩 추가
		if (Util.hasText(value)) {
			int k = 0;
			arrParams = value.split("\\^");
//			arrParams = value.split("@@");
			for (String s : arrParams) {
				if (s.indexOf("sword=") > -1) {
					String[] arrParams1 = s.split("\\=");
					if (arrParams1.length == 2) {
						String s1 = arrParams1[0];
						String s2 = arrParams1[1];
						// 한글 포함 여부 확인
						if (s2.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {
							s2 = URLEncoder.encode(s2);
						}
						s = s1 + "=" + s2;
					}
				}

				if (k == 0) newParams = s;
				else newParams = newParams + "^" + s;
//				else newParams = newParams + "@@" + s;
				k++;
			}

		}

		return newParams.replaceAll("\\^", "&");
//		return newParams.replaceAll("@@", "&");

//		return value == null ? "" : value.replaceAll("\\^", "&");
	}

	/**
	 * 배열안에 값이 포함되어 있는지 확인
	 *
	 * @param haystack the haystack
	 * @param needle the needle
	 * @return true, if successful
	 */
	public static boolean in_array(String[] haystack, String needle) {
		for (String s : haystack) {
			if (s.equals(needle)) return true;
		}

		return false;
	}
	
	public static <T> T[] addArr(T[] arr, T element) {
	    final int N = arr.length;
	    arr = Arrays.copyOf(arr, N + 1);
	    arr[N] = element;
	    return arr;
	}
	
	/**
	 * 숫자 천단위 쉼표 찍어줌
	 *
	 * @param vlaue the vlaue
	 * @return the string
	 */
	public static String num2Cur(String value) {
		DecimalFormat df = new DecimalFormat("###,###.#####");

		String result="";
		if (hasText(value))
			result = df.format(Double.parseDouble(value));

		return result;
	}

	public static String float2Cur(String value){
		Pattern pattern = Pattern.compile("([0-9]*)([.])?([0]*)?([1-9]*)?.*?"); // 대소문자 구분 안함
		Matcher matcher = pattern.matcher(value);

		String return_Str = "";
		matcher.find();

		//소수점 뒷자리 체크
		//. 또는 .0~
		if(null == matcher.group(2) ||
		   "".equals(matcher.group(2)) ||
		   null == matcher.group(4) ||
		   "".equals(matcher.group(4)))
		{
			return_Str += num2Cur(matcher.group(1));
		}
		else{
			for(int i= 1; i<= matcher.groupCount() ; i++) {
				if(i==1){
					return_Str += num2Cur(matcher.group(i));
				}else{
					return_Str += matcher.group(i);
				}
			}
		}

		return return_Str;
	}
	
	public static String removeNum2Cur(String value){
		return value.replaceAll(",", "");
	}

	/**
	 * 숫자 천단위 쉼표 찍어줌
	 *
	 * @param vlaue the vlaue
	 * @return the string
	 */
	public static String num2Cur(int value) {
		return num2Cur(Converter.toStr(value));
	}

	/**
	 * 숫자 천단위 쉼표 찍어줌
	 *
	 * @param vlaue the vlaue
	 * @return the string
	 */
	public static String num2Cur(double value) {
		return num2Cur(Converter.toStr(value));
	}

	/**
	 * 값 비교
	 *
	 * @param arg1 the arg1
	 * @param arg2 the arg2
	 * @return the string
	 */
	public static String setCheckedArea(String arg1, String arg2) {
		arg1 = "," + arg1 + ",";
		arg2 = "," + arg2 + ",";

		return arg1.indexOf(arg2) > NOPOS ? " checked" : "";
	}

	/**
	 * Math 의 pow 함수 래퍼
	 *
	 * @param arg1 the arg1
	 * @param arg2 the arg2
	 * @return the double
	 */
	public static double pow(int arg1, int arg2) {
		return Math.pow(arg1, arg2);
	}

	/**
	 * Math 의 pow 함수 래퍼
	 *
	 * @param arg1 the arg1
	 * @param arg2 the arg2
	 * @return the double
	 */
	public static int intPow(int arg1, int arg2) {
		return (int) Math.pow(arg1, arg2);
	}

	/**
	 * Xss 방지
	 *
	 * @param value the value
	 * @return the string
	 */
	public static String rejectXss(String value) {
		if (value != null && Util.hasText(value)) {
			value = value.replaceAll("&#39;", "'");
			value = value.replaceAll("&quot;", "\"");
			value = value.replaceAll("&gt;", ">");
			value = value.replaceAll("&lt;", "<");
			//value = value.replaceAll("&amp;", "&");
			value = value.replaceAll("<script[^>]*>.*?<\\/script[^>]*>", "");
			value = value.replaceAll("<style[^>]*>.*?<\\/style[^>]*>", "");
			value = value.replaceAll("(\\<a [^>]+)onclick=[^ ]+([^>]*>)", "$1$2");
			value = value.replaceAll("(\\<a [^>]+)href=.*javascript:[^ ]+([^>]*>)", "$1$2");
			value = value.replaceAll("(\\<a [^>]+)href=.*vbscript:[^ ]+([^>]*>)", "$1$2");
			value = value.replaceAll("javascript\\s*:", "x-javascript:");
			value = value.replaceAll("vbscript\\s*:", "x-vbscript:");
			value = value.replaceAll("onload=[^>]+", "");
			value = value.replaceAll("onclick=[^>]+", "");
			value = value.replaceAll("onmouseover=[^>]+", "");
			value = value.replaceAll("onmouseout=[^>]+", "");
			value = value.replaceAll("onmousedown=[^>]+", "");

			value = value.replaceAll("document", "x-document");
			value = value.replaceAll("applet", "x-applet");
			value = value.replaceAll("object", "x-object");
			value = value.replaceAll("frame", "x-frame");
			value = value.replaceAll("frameset", "x-frameset");
			value = value.replaceAll("layer", "x-layer");
			value = value.replaceAll("bgsoundv", "x-bgsound");
			value = value.replaceAll("alert", "x-alert");
			value = value.replaceAll("onblur", "x-onblur");
			value = value.replaceAll("onchange", "x-onchange");
			value = value.replaceAll("ondblclick", "x-ondblclick");
			value = value.replaceAll("enerror", "x-enerror");
			value = value.replaceAll("onfocus", "x-onfocus");
			value = value.replaceAll("onmouse", "x-onmouse");
			value = value.replaceAll("onscroll", "x-onscroll");
			value = value.replaceAll("onsubmit", "x-onsubmit");
			value = value.replaceAll("onunload", "x-onunload");
			//value = value.replaceAll("&", "&amp;");
			value = value.replaceAll("<", "&lt;");
			value = value.replaceAll(">", "&gt;");
			value = value.replaceAll("\"", "&quot;");
			value = value.replaceAll("'", "&#39;");
		}
		return value;
	}

	/**
	 * Style Sheet 등록 방지
	 *
	 * @param value the value
	 * @return the string
	 */
	public static String rejectStyle(String value) {
		value = value.replaceAll("<style[^>]*>.*?<\\/style[^>]*>", "");
		return value;
	}

	public static String calcMath(String strMode, double nCalcVal, int nDigit) {
		if ("ROUND".equals(strMode)) {		//반올림
			if (nDigit < 0) {
				nDigit = -(nDigit);
				nCalcVal = Math.round(nCalcVal / Math.pow(10, nDigit)) * Math.pow(10, nDigit);
			} else {
				nCalcVal = Math.round(nCalcVal * Math.pow(10, nDigit)) / Math.pow(10, nDigit);
			}
		}
		//절상
		else if ("CEIL".equals(strMode)) {
			if (nDigit < 0) {
				nDigit = -(nDigit);
				nCalcVal = Math.ceil(nCalcVal / Math.pow(10, nDigit)) * Math.pow(10, nDigit);
			} else {
				nCalcVal = Math.ceil(nCalcVal * Math.pow(10, nDigit)) / Math.pow(10, nDigit);
			}
		}
		//절하
		else if ("FLOOR".equals(strMode)) {
			if (nDigit < 0) {
				nDigit = -(nDigit);
				nCalcVal = Math.floor(nCalcVal / Math.pow(10, nDigit)) * Math.pow(10, nDigit);
			} else {
				nCalcVal = Math.floor(nCalcVal * Math.pow(10, nDigit)) / Math.pow(10, nDigit);
			}
		}
		//그대로(무조건 소수점 첫째 자리에서 반올림)
		else {
			nCalcVal = Math.round(nCalcVal);
		}

		return String.valueOf(nCalcVal);
	}

	/**
	 * 반올림
	 *
	 * @param value the value
	 * @param pos the pos
	 * @return the int
	 */
	public static int setRound(int value, int pos) {
		int ret = 0;
		if (pos < 0) {
			pos = -(pos);
			ret = (int) (Math.round(value / Math.pow(10, pos)) * Math.pow(10, pos));
		}
		else {
			ret = (int) (Math.round(value * Math.pow(10, pos)) / Math.pow(10, pos));
		}
		return ret;
	}

	/**
	 * 반올림
	 *
	 * @param value the value
	 * @param pos the pos
	 * @return the int
	 */
	public static int setRound(double value, int pos) {
		int ret = 0;
		if (pos < 0) {
			pos = -(pos);
			ret = (int) (Math.round(value / Math.pow(10, pos)) * Math.pow(10, pos));
		} else {
			ret = (int) (Math.round(value * Math.pow(10, pos)) / Math.pow(10, pos));
		}
		return ret;
	}

	/**
	 * 올림
	 *
	 * @param value the value
	 * @param pos the pos
	 * @return the int
	 */
	public static int setCeil(double value, int pos) {
		double ret = 0.0;
		ret = Math.ceil(value);
		return (int)ret;
	}

	/**
	 * 올림
	 *
	 * @param value the value
	 * @param pos the pos
	 * @return the double
	 */
	public static double setCeilDouble(double value, int pos) {
		BigDecimal bd = new BigDecimal(value);
		BigDecimal ret = null;

		ret =  bd.setScale(pos, BigDecimal.ROUND_UP);	//올림

		return ret.doubleValue();
	}

	/**
	 * Sets the checked step.
	 *
	 * @param arg1 the arg1
	 * @param arg2 the arg2
	 * @return the string
	 */
	public static String setCheckedStep(String arg1, String arg2) {
		arg1 = "," + arg1 + ",";
		arg2 = "," + arg2 + ",";
		if (arg1.indexOf(arg2) > -1) {
			return " checked";
		}

		return "";
	}

	/**
	 * 현재 URL
	 *
	 * @param isQuery the is query
	 * @return the self url
	 */
	public static String getSelfURL(boolean isQuery) {
		return isQuery ?
				String.format("%s?%s", ServletRequestInfoUtil.getServletPath(), ServletRequestInfoUtil.getQueryString())
				: ServletRequestInfoUtil.getServletPath();
	}
	public static String getRequestURL(boolean isQuery) {
		return ServletRequestInfoUtil.getRequestURL();
	}


	/**
	 * 할인 및 배송비 출력
	 *
	 * @param title the title
	 * @param summary the summary
	 * @param strAmount the str amount
	 * @param arrGoods the arr goods
	 * @param rowSpan the row span
	 * @return the string
	 */
	public static String printAddtionItem(String title, String summary, String strAmount, String[] arrGoods, int rowSpan) {

		String strGoods = Converter.arrayParamJoin(arrGoods, "", "<br />");

		title = Converter.nvl(title);
		summary = Converter.nvl(summary);

		StringBuffer sb = new StringBuffer();

		sb.append("<tr>");
		if (rowSpan > -1) {
			if (rowSpan == 0)
			{
				sb.append(String.format("<td class=\"black\" >%s</td>",  title));
				sb.append(String.format("<td class=\"text_left\" >%s</td>", summary));
			}
			else
			{
				sb.append(String.format("<td class=\"black\" rowSpan=\"%d\">%s</td>", rowSpan, title));
				sb.append(String.format("<td class=\"text_left\" rowSpan=\"%d\">%s</td>", rowSpan, summary));
			}
		}
		sb.append(String.format("<td><strong>%s</strong></td>", strAmount));
		sb.append(String.format("<td>%s</td>", strGoods));
		sb.append("</tr>");

		return sb.toString();
	}

	/**
	 * 절대값
	 *
	 * @param val the val
	 * @return the string
	 */
	public static String Abs(int val) {
		return Converter.toStr(Math.abs(val));
	}

	/**
	 * 문자열 공백제거
	 *
	 * @param value the value
	 * @return the string
	 */
	public static String null2Blank(String value) {
		return value == null ? "" : value.trim();
	}

	/**
	 * Tag를 문자열로 변환
	 *
	 * @param value the value
	 * @return the string
	 */
	public static String tag2Text(String value) {
		value = value.replaceAll("&", "&amp;");
		value = value.replaceAll("<", "&lt;");
		value = value.replaceAll(">", "&gt;");
		return value;
	}

	/**
	 * 문자열을 Tag로 변환
	 *
	 * @param value the value
	 * @return the string
	 */
	public static String text2Tag(String value) {
		value = value.replaceAll("&gt;", ">");
		value = value.replaceAll("&lt;", "<");
		value = value.replaceAll("&amp;", "&");
		value = value.replaceAll("&quot;", "\"");
		return value;
	}

	/**
	 * DB엔터값을 Tag로 변환
	 *
	 * @param value the value
	 * @return the string
	 */
	public static String text2Enter(String value) {
		value = value.replaceAll("\r\n", "<br/>");
		return value;
	}

	 /**
	 * 내림, 반올림, 올림 함수.
	 *
	 * @param decimal	부동소수
	 * @param loc 		자릿수 제한 위치. 2자리까지 보이면 2 , 3자리까지면 3 이런식으로 지정
	 * @param mode		1 내림 , 2 반올림 , 3 올림
	 * @return the double
	 */
	public static double decimalScale(double decimal , int loc , int mode) {
		BigDecimal bd = new BigDecimal(decimal);
		BigDecimal result = null;

		//내림
		if (mode == 1) {
			result = bd.setScale(loc, BigDecimal.ROUND_DOWN);
		}
		//반올림
		else if (mode == 2) {
			result = bd.setScale(loc, BigDecimal.ROUND_HALF_UP);
		}
		//올림
		else if (mode == 3) {
			result = bd.setScale(loc, BigDecimal.ROUND_UP);
		}

		return result.doubleValue();
	}

	/**
	 * Prints the zip result.
	 *
	 * @param result the result
	 * @param message the message
	 * @return the string
	 */
	public static String printZipResult(int result, String message) {
		JSONObject json = new JSONObject();

		json.put("result", result);
		json.put("message", Util.encQuote(message));

		return json.toString();
	}

	/**
	 * Replace.
	 *
	 * @param value the value
	 * @param regex the regex
	 * @param replacement the replacement
	 * @return the string
	 */
	public static String Replace(String value, String regex, String replacement) {
		value = value.replaceAll(regex, replacement);
		return value;
	}

	/**
	 * 문자열 자르기
	 *
	 * @param str the str
	 * @param limit the limit
	 * @return the string
	 */
	public static String cutString(String str, int limit) {
		if (str == null) {
			return str;
		}

		int len = str.length();
		int cnt = 0, index = 0;

		while (index < len && cnt < limit) {
			// 1바이트 문자라면...
			if (str.charAt(index++) < 256) {
				cnt++; // 길이 1 증가
			}
			// 2바이트 문자라면...
			else {
				cnt += 2; // 길이 2 증가
			}
		}

		if (index < len) {
			str = str.substring(0, index) + "..";
		}

		return str;
	}

	/**
	 * 이전페이지 체크
	 *
	 * @param pageUrl the page url
	 * @return true, if successful
	 */
	public static boolean checkReferer(String pageUrl) {
		String referer = ServletRequestInfoUtil.getReferer();
		boolean result = false;

		if (logger.isDebugEnabled()) {
			logger.debug(String.format("checkReferer pageUrl[%s] referer[%s]", pageUrl, referer));
		}

		pageUrl = pageUrl.replace("http://", "").replace("https://", "");
		if (Util.hasText(referer)) {
			referer = referer.replace("http://", "").replace("https://", "");
			String[] arrReferer = referer.split("\\?");

			if (arrReferer.length > 0) {
				if (arrReferer[0].trim().equals(pageUrl)) {
					result = true;
				}
			}
		}

		return result;
	}

	/**
	 * 임시비밀번호 생성
	 *
	 * @return the string
	 */
	public static String createTemporaryPw() {
		String keys = "ab0cde1fg2hi3jk4lm5no6pq7rs8tu9vwx0yz";
		String pw="";
		for (int i=0 ;i<13;i++) {
			int random = (int) (Math.random()*37);
			pw += keys.substring(random, random+1);
		}

		return pw;
	}

	/**
	 * 파라미터를 모델로 변환
	 *
	 * @param params the params
	 * @param obj the obj
	 * @return the object
	 */
	public static Object setParamToModel(HashMap<String,Object> params, Object obj) {
		try {
			for (Map.Entry<String, Object> mapEntry : params.entrySet()) {
				if (mapEntry != null) {
					if (mapEntry.getValue() != null) {
						//logger.error(mapEntry.getKey().toString() + " : " + mapEntry.getValue().toString()+"   "+mapEntry.getValue().getClass().getSimpleName());
						if (mapEntry.getValue().getClass().getSimpleName().equals("String") || mapEntry.getValue().getClass().getSimpleName().equals("Integer")) {
							setModel(obj, mapEntry);
						} else {
							//objectValue(mapEntry.getValue());
						}
					} else {
						//logger.error(mapEntry.getKey().toString().replace('\r', '_').replace('\n', '_') + " : null");
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

		return obj;
	}

	/**
	 * Sets the model.
	 *
	 * @param obj the obj
	 * @param mapEntry the map entry
	 * @throws Exception the exception
	 */
	private static void setModel(Object obj, Map.Entry<String, Object> mapEntry) throws Exception {
		for (Method optMethod : obj.getClass().getMethods()) {
			if (optMethod != null) {
				String methodName =optMethod.getName();
				String mapKey=mapEntry.getKey().toString();
				String mapValue=mapEntry.getValue().toString();
				//logger.error("isVarArgs:"+optMethod.isVarArgs()+" : "+methodName+" getValue:"+mapValue);
				if (! optMethod.isVarArgs() && methodName.indexOf("set") == 0 && methodName.replace("set", "").toLowerCase().equals(mapKey.toLowerCase())) {
					Field field= obj.getClass().getDeclaredField(mapKey.toLowerCase());
					String objSimpleType=field.getType().getSimpleName();
					String objType=field.getType().getName();

					logger.error(methodName + " : " + mapValue+"  objSimpleType:"+objSimpleType+" objType:"+objType);
					if ("Integer".equals(objSimpleType)) {
						optMethod.invoke(obj, Integer.parseInt(mapValue));
					}
					else if ("Double".equals(objSimpleType)) {
						optMethod.invoke(obj, Double.parseDouble(mapValue));
					}
					else {
						optMethod.invoke(obj, mapValue);
					}
				}
			}
		}
	}

	/**
	 * Sets the param to param detail.
	 *
	 * @param params the params
	 * @return the string
	 */
	public static String setParamToParamDetail(HashMap<String,Object> params) {
		String params_detail="";
		String key ="";
		String value ="";
		String classSimpleName="";
		try{
			for (Map.Entry<String, Object> mapEntry : params.entrySet()) {
				if (mapEntry!=null) {
					if (mapEntry.getValue()!=null) {
						//logger.error(mapEntry.getKey().toString() + " : " + mapEntry.getValue().toString()+"   "+mapEntry.getValue().getClass().getSimpleName());
						classSimpleName=mapEntry.getValue().getClass().getSimpleName();
						if (classSimpleName.equals("String")||classSimpleName.equals("Integer")) {
							key = mapEntry.getKey().toString();
							value = mapEntry.getValue().toString();
							if (key.indexOf("price")!= -1 || key.indexOf("money")!= -1 ||classSimpleName.equals("Integer")) {
								params_detail+=String.format(key+"=%d&",Converter.toInt(value));
							}
							else {
								params_detail+=String.format(key+"=%s&",Converter.toStr(value));
							}
						}
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

		return hasText(params_detail) ?
				String.format("%s",params_detail.substring(0,params_detail.length()-1))
				: "" ;
	}

	/**
	 * Prints the error.
	 *
	 * @param no the no
	 * @param message the message
	 * @return the string
	 */
	public static String printError(int no, String message) {
		return String.format("error:%d:%s", no, message);
	}
	
	/**
	 * Object type 변수가 비어있는지 체크
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object obj) {
		if (obj instanceof String) return obj == null || "".equals(obj.toString().trim());
		else if (obj instanceof List) return obj == null || ((List) obj).isEmpty();
		else if (obj instanceof Map) return obj == null || ((Map) obj).isEmpty();
		else if (obj instanceof Object[]) return obj == null || Array.getLength(obj) == 0;
		else return obj == null;
	}
	
	/**
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNotEmpty(Object obj) {
		return ! isEmpty(obj);
	}



	/**
	 * Checks if is not empty.
	 *
	 * @param o the o
	 * @return true, if is not empty
	 */
	public static boolean isNotEmpty(List o) {
		return ! isEmpty(o);
	}
	
	/**
	 * Checks if is not empty.
	 *
	 * @param o the o
	 * @return true, if is not empty
	 */
	public static boolean isNotEmpty(String [] data) {
		if(data == null || data.length == 0){
			return false;
		}else{
			return true;
		}
	}
	
	public static boolean isEmpty(String [] data) {
		if (data == null || data.length == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks if is empty. null safe.
	 *
	 * @param o the o
	 * @return true, if is empty
	 */
	public static boolean isEmpty(List o) {
		if (o == null) return true;
		return o.isEmpty();
	}

	public static boolean isEmptyMap(Map map) {
		if (map == null) return true;
		return map.isEmpty();
	}
	
	public static boolean isNotEmpty(Map map) {
		if (map == null) return false;
		return ! map.isEmpty();
	} 
	
	/**
	 * Checks if is array.
	 *
	 * @param list the list
	 * @return true, if successful
	 */
	@Deprecated
	public static boolean IsArray(List list) {
		if (list == null) return false;
		return ! list.isEmpty();
	}

	/**
	 * Gets the field required.
	 *
	 * @param imgurl the imgurl
	 * @param pathsite the pathsite
	 * @param code the code
	 * @param required the required
	 * @return the field required
	 */
	public static String getFieldRequired(String imgurl, String pathsite, String code, int required) {
		return required > 0 ?
				//String.format("<img src=\"%s/imgs/icon/ico_essential.gif\" class=\"essential field_required_%s\" alt=\"필수입력사항\">", imgurl, code)
				String.format("<img src=\"/imgs/icon/ico_essential.gif\" class=\"essential field_required_%s\" alt=\"필수입력사항\">", code)
				: "";
	}

	public static String getMwFieldRequired(String code, int required) {
		return required > 0 ?
				String.format("<span class=\"x_highlight\">*</span> ", code)
				: "";
	}

	/**
	 * Sets the nice xss.
	 *
	 * @param value the value
	 * @return the string
	 */
	public static String setNiceXss(String value) {
		String ret = "";
		ret = Converter.toStr(value);
		ret = ret.replace("<", "&lt;");
		ret = ret.replace(">", "&gt;");
		ret = ret.replace("\"", "");
		ret = ret.replace("'", "");
		ret = ret.replace("(", "");
		ret = ret.replace(")", "");
		ret = ret.replace("#", "");
		ret = ret.replace("%", "");
		ret = ret.replace(";", "");
		ret = ret.replace(":", "");
		ret = ret.replace("-", "");
		ret = ret.replace("`", "");
		ret = ret.replace("--", "");
		ret = ret.replace("\\", "");
		return ret;
	}

	/**
	 * Sets the positive img src.
	 *
	 * @param value the value
	 * @return the string
	 */
	public static String setPositiveImgSrc(String value) {
		return value;
	}

	/**
	 * 파일용량 문자열 생성
	 *
	 * @param size = 파일용량(byte)
	 * @return String
	 */
	public static String makeCapacity (int size) {
		String result = "";

		if (size < 1024) {
			result = size + " Byte";
		}
		else if (size >= 1024 && size < (1024 * 1024)) {
			size = size / 1024;
			result =size + " KB";
		}
		else {
			size = size / (1024 * 1024);
			result = size + " MB";
		}

		return result;
	}

	/**
	 * escape함수
	 *
	 * @param src the src
	 * @return the string
	 */
	public static String escape(String src) {
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);
		for (i = 0; i < src.length(); i++) {
			j = src.charAt(i);
			if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j))
				tmp.append(j);
			else if (j < 256) {
				tmp.append("%");
			if (j < 16)
				tmp.append("0");
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}

		return tmp.toString();
	}

	/**
	 * unescape함수
	 *
	 * @param src the src
	 * @return the string
	 */
	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}

		return tmp.toString();
	}

	/**
	 * 첨부파일확장자 가져오기
	 *
	 * @param imgURL the img url
	 * @param pathSite the path site
	 * @param filename the filename
	 * @return the file extension icon
	 */
	public static String getFileExtensionIcon(String imgURL, String pathSite, String filename) {
		String ext = StringUtils.getFilenameExtension(filename);
		String result = "etc";

		if (in_array(new String[]{"avi", "bmp", "doc", "exe", "gif", "hwp", "mp3", "ppt", "rar", "swf", "txt", "wav", "xls", "zip"}, ext)) {
			result = ext;
		}
		else if ("xlsx".equals(ext)) {
			result = "xls";
		}
		else if (in_array(new String[]{"ra", "ram", "rm"}, ext)) {
			result = "ra";
		}
		else if (in_array(new String[]{"htm", "html"}, ext)) {
			result = "htm";
		}
		else if (in_array(new String[]{"jpg", "jpeg"}, ext)) {
			result = "jpg";
		}
		else if (in_array(new String[]{"mpg", "mpeg"}, ext)) {
			result = "mpg";
		}
		else if (in_array(new String[]{"asf", "asx"}, ext)) {
			result = "asf";
		}

		return String.format("%s/imgs/icon/ico_extension_%s.gif", imgURL, result);
	}

	/**
	 * 자바스크립트 문자열
	 *
	 * @param msg the msg
	 * @return the string
	 */
	public static String jsmsg(String msg) {
		return jsmsg(msg, "");
	}

	/**
	 * 코드가 있는 자바스크립트 문자열
	 *
	 * @param msg the msg
	 * @param code the code
	 * @return the string
	 */
	public static String jsmsg(String msg, String code) {
		return String.format("__jsmsg('%s', '%s');", msg, code);
	}
	
	public static String jsmsg(String msg, String code, String loading) {
		return String.format("__jsmsg('%s', '%s', '%s');", msg, code, loading);
	}

	/**
	 * 자바스크립트 리로드
	 *
	 * @param msg the msg
	 * @param code the code
	 * @return the string
	 */
	public static String jsmsgReload(String msg, String code) {
		return String.format("__jsmsgReload('%s', '%s');", msg, code);
	}

	/**
	 * 자바스크립트 close
	 *
	 * @param msg the msg
	 * @return the string
	 */
	public static String jsmsgClose(String msg) {
		return String.format("__jsmsgClose('%s');", msg);
	}

	/**
	 * 자바스크립트 url 이동
	 *
	 * @param msg the msg
	 * @return the string
	 */
	public static String gotoURL(String msg) {
		return gotoURL(msg, "");
	}

	/**
	 * 코드가 있는 자바스크립트 url 이동
	 *
	 * @param msg the msg
	 * @param code the code
	 * @return the string
	 */
	public static String gotoURL(String msg, String code) {
		return String.format("__gotoURL('%s', '%s');", msg, code);
	}

	/**
	 * 자바스크립트 링크 메시지
	 *
	 * @param msg the msg
	 * @param url the url
	 * @param mode the mode
	 * @return the string
	 */
	public static String jsmsgLink(String msg, String url, String mode) {
		return String.format("__jsmsgLink('%s', '%s', '%s');", msg, url, mode);
	}

	/**
	 * 자바스크립트 페이지 리로드
	 *
	 * @param mode the mode
	 * @return the string
	 */
	public static String pageReload(String mode) {
		return String.format("__pageReload('%s');", mode);
	}

	/**
	 * Long double2 string.
	 *
	 * @param value the value
	 * @param size the size
	 * @return the string
	 */
	public static String longDouble2String(double value, int size) {
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(size);
		nf.setGroupingUsed(false);

		return nf.format(value);
	}

	/**
	 * string 이 값을 가지고 있는지 확인
	 * 현재는 StringUtils의 hasText 랩퍼
	 *
	 * @param str the str
	 * @return true, if successful
	 */
	public static boolean hasText(String str) {
		return StringUtils.hasText(str);
	}


	public static boolean isEmpty(String str) {
		return StringUtils.isEmpty(str);
	}

	/**
	 * string 들이 모두 값을 가지고 있는지 확인
	 *
	 * @param strs str 들
	 * @return true, if successful
	 */
	public static boolean hasAllText(String ...strings) {
		for (String s : strings) {
			if (! hasText(s)) return false;
		}
		return true;
	}

	/**
	 * Gets the filename url encode.
	 *
	 * @param fname the fname
	 * @return the filename url encode
	 */
	public static String getFilenameUrlEncode(String fname) {
		String f_head = StringUtils.stripFilenameExtension(fname);
		String f_ext = StringUtils.getFilenameExtension(fname);

		try {
			f_head = URLEncoder.encode(f_head, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return String.format("%s.%s", f_head, f_ext);
	}

	/**
	 * 스트링이 서로 같은지 비교
	 * 둘다 null 이 아니면  String 의 equals 랩퍼
	 *
	 * @param a the a
	 * @param b the b
	 * @return true, if successful
	 */
	public static boolean equal(String a, String b) {
		if (a == null || b == null) return false;

		return a.equals(b);
	}

	/**
	 *
	* @Method Name : convGray
	* @Method 설명 : 0 인 데이터 폰트색상 변경
	*
	* @param value
	* @return
	 */
	public static String convGray (String value) {
		return "0".equals(value) ?
				String.format("<font color='#C0C0C0'>%s</font>", value)
				: Converter.nvl(value);
	}

	/**
	 *
	* @Method Name : isDisplayOn
	* @Method 설명 :
	*
	* @param val1
	* @param val2
	* @return
	 */
	public static String isDisplayOn (String val1, String val2) {
		return in_array(val1.split(","), val2) ? "" : "none";
	}

	/**
	 *
	* @Method Name : getMyIpList
	* @Method 설명 : 시스템 인터페이스 아이피
	*
	* @return
	 */
	public static List<String> getMyIpList() {
		List<String> list = new ArrayList<String>();
		NetworkInterface iface = null;
		try {
			for (Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements();) {
				iface = ifaces.nextElement();
				InetAddress ia = null;

				for (Enumeration<InetAddress> ips = iface.getInetAddresses(); ips.hasMoreElements();) {
					ia = ips.nextElement();
					String address = ia.getHostAddress();
					//if ("127.0.0.1".compareTo(address) != 0) {
						list.add(address);
					//}
				}
			}
		}
		catch (SocketException e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 *
	* @Method Name : Left
	* @Method 설명 : 문자열 앞에서부터 짤라내기
	*
	* @param val
	* @param limit
	* @return
	 */
	public static String Left (String val, int limit) {
		if (val == null && "".equals(val)) {
			return "";
		}
		if (val.length() > limit) {
			return val.substring(0, val.length());
		}

		return val;
	}

	/**
	 * 위도/경도로 두 지점간 거리 구하기
	 */
	public static double getDistance(String startPointLon, String startPointLat, String endPointLon, String endPointLat) throws Exception {
		double d2r = Math.PI / 180;
		double dStartPointLon = Double.parseDouble(startPointLon);
		double dStartPointLat = Double.parseDouble(startPointLat);
		double dEndPointLon = Double.parseDouble(endPointLon);
		double dEndPointLat = Double.parseDouble(endPointLat);

		double dLon = (dEndPointLon - dStartPointLon) * d2r;
		double dLat = (dEndPointLat - dStartPointLat) * d2r;

		double a = Math.pow(Math.sin(dLat / 2.0), 2)
					+ Math.cos(dStartPointLat * d2r)
					* Math.cos(dEndPointLat * d2r)
					* Math.pow(Math.sin(dLon / 2.0), 2);

		double c = Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)) * 2;

		double distance = c * 6378;

		return distance;
	}

	/**
	*
	* @Method Name : combineWithComma
	* @Method 설명 : a 가 있을경우 콤마 문자열로 묶는다.
	*
	* @param a
	* @param b
	* @return
	*/
	public static String combineWithComma(String a, String b) {
		return String.format("%s%s", hasText(a) ? a + ", " : "", b);
	}

	/**
	 *
	* @Method Name : split
	* @Method 설명 : 문자열 자르기 jsp 에서 사용함.
	*
	* @param str
	* @param delim
	* @return
	 */
	public static String[] split(String str, String delim) {
		return str.split(delim);
	}
	
	public static String[] split2(String str, String delim){
		
		StringTokenizer stz = new StringTokenizer(str, delim);
		
		String[] result = new String[stz.countTokens()];
		int count = 0;
		while (stz.hasMoreElements()) {
			String value = (String) stz.nextElement();
			result[count++] = value;
		}
		return result;
	}
	

	/**
	 *
	* @Method Name : isMobile
	* @Method 설명 : 모바일 에이전트 판별
	*
	* @param request
	* @return
	 */
	public static boolean isMobile (HttpServletRequest request) {
		String user_agent = request.getHeader("User-Agent").toLowerCase();
		if ("lgtelecom".indexOf(user_agent) > -1 || 
			"android".indexOf(user_agent) > -1 || 
			"blackberry".indexOf(user_agent) > -1 || 
			"iphone".indexOf(user_agent) > -1 || 
			"ipad".indexOf(user_agent) > -1 || 
			"samsung".indexOf(user_agent) > -1 || 
			"symbian".indexOf(user_agent) > -1 || 
			"sony".indexOf(user_agent) > -1 || 
			"SCH-".indexOf(user_agent) > -1 || 
			"SPH-".indexOf(user_agent) > -1) {
			
			return true;
		}

		return false;
	}

	/**
	 *
	* @Method Name : removeHtmlTag
	* @Method 설명 : html 태그 삭제
	*
	* @param val
	* @return
	 */
	public static String removeHtmlTag(String val) {
		String returnStr = text2Tag(val);
		returnStr = returnStr.replaceAll("\\<.*?\\>", "");
		returnStr = returnStr.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");

		return returnStr;
	}

	public static int getOriginalPrice(int price , double margin){
		return (int) (price - Math.floor(price * (margin / 100) ));
	}

	/**
	* @Method Name : makeOrdNo
	* @Method 설명 : 주문 번호 생성
	*
	* @param head : 주문 번호 헤더 정보
	* @return String
	 */
	public static String makeOrdNo(String head) {
		
		/*
		Calendar cal = Calendar.getInstance();
		
        Random rnd = new Random();
	
		String randomStr = "" + rnd.nextInt(999999);
		randomStr = LPAD(randomStr, 5, "0");
			
		return String.format("%s%04d%02d%02d%02d%02d%02d%s"
            	, head
            	, cal.get(Calendar.YEAR)
            	, cal.get(Calendar.MONTH) + 1
            	, cal.get(Calendar.DAY_OF_MONTH)
            	, cal.get(Calendar.HOUR_OF_DAY)
            	, cal.get(Calendar.MINUTE)
            	, cal.get(Calendar.SECOND)
            	, randomStr);
		 */
		
		// 기존 채번 방식
		Date d = new Date();
		Calendar today = Calendar.getInstance();

		String curYear = String.valueOf( today.get(Calendar.YEAR) );
		String curJulianDay = String.valueOf( today.get(Calendar.DAY_OF_YEAR) );
		String curTime = String.valueOf( d.getTime() );
		String curMilliSecond = String.valueOf( today.get(Calendar.MILLISECOND) );

		curYear = curYear.substring(2, 4);
		curTime = curTime.substring( curTime.length() - 5 , curTime.length() );

		if( curJulianDay.length() == 1 ){
			curJulianDay = "00" + curJulianDay;
		}
		else if( curJulianDay.length() == 2 ){
			curJulianDay = "0" + curJulianDay;
		}

		if( curMilliSecond.length() == 1 ){
			curMilliSecond = "00" + curMilliSecond;
		}
		else if( curMilliSecond.length() == 2 ){
			curMilliSecond = "0" + curMilliSecond;
		}

		return head + curYear + curJulianDay + curTime +  curMilliSecond;
	}

	/**
	* @Method Name : LPAD
	* @Method 설명 : 좌측에 원하는 문자열 채우기
	*
	* @param str : 문자열
	* @param len : 원하는 문자열 길이
	* @param append : 채울 문자
	* @return String
	 */
	public static String LPAD(String str, int len, String append) {
		String rtn = str;
		if (str.length() < len) {
			for (int i = (len - str.length()); i > 0; i--) {
				rtn = append + rtn;
			}
		} else {
			rtn = str.substring(0, len);
		}
		return rtn;
	}
	
	/**
	* @Method Name : RPAD
	* @Method 설명 : 우측에 원하는 문자열 채우기
	*
	* @param str : 문자열
	* @param len : 원하는 문자열 길이
	* @param append : 채울 문자
	* @return String
	 */
	public static String RPAD(String str, int len, String append) {
		String rtn = str;
		if (str.length() < len) {
			for (int i = (len - str.length()); i > 0; i--) {
				rtn = rtn + append;
			}
		} else {
			rtn = str.substring(0, len);
		}
		return rtn;
	}
	
	/**
	 * Integer null 확인
	 * @param key : Integer
	 * @param def : key null일 시 반환 할 default 값
	 * @return
	 */
	public static int getInt(Integer key, int def) {
		if (key == null){
			return def;
		}else{
			return key;
		}
	}

	public static String replaceLast(String string, String toReplace, String replacement) {

		int pos = string.lastIndexOf(toReplace);

		if (pos > -1) {
			return string.substring(0, pos) + replacement + string.substring(pos + toReplace.length(), string.length());
		} else {
			return string;
		}
	}

	public static int calcBitwise(int haystack, int addition) {
		return haystack - (haystack & addition) + addition;
	}

	/**
	 * ID의 뒤부분을 * 처리 함
	 *
	 * @param value the value
	 * @return String
	 */
	public static String maskId(String value) {
		String return_str = "";
		String domain = "";

		if (value != null) {
			return_str = value;

//			if (value.indexOf("@") > -1) {
//				domain = "@" + value.split("@")[1];
//				return_str = value.split("@")[0];
//			}

			int length = return_str.length();

			if (length > 3) {
				return_str = return_str.substring(0, length-3) + "***";
			}
//			else if (length > 2) {
//				return_str = return_str.substring(0, length-2) + "**";
//			}
			else if (length > 1) {
				return_str = return_str.substring(0, length-1) + "*";
			}

		}
		return return_str + domain;
	}

	/**
	 * 전화번호 자르기
	 * 10자리 11자리 다 잘됨
	 * @param noStr
	 * @return
	 * @throws Exception
	 */
	public static String[] getTelPhoneSpliter(String noStr) throws Exception {
		Pattern tellPattern = Pattern.compile("^(01\\d{1}|02|0505|0502|0506|0\\d{1,2})-?(\\d{3,4})-?(\\d{4})");

		if (noStr == null || "".equals(noStr)) return new String[]{ "", "", ""};

		Matcher matcher = tellPattern.matcher(noStr);
		if (matcher.matches()) {
			return new String[]{ matcher.group(1), matcher.group(2), matcher.group(3)};
		} else {
			String str1 = noStr.substring(0, 3);
			String str2 = noStr.substring(3, 7);
			String str3 = noStr.substring(7, 11);
			return new String[]{ str1, str2, str3};
		}
	}

	public static String getClientIp(HttpServletRequest req) {
		String ip = req.getHeader("X-Forwarded-For");
		if (ip == null) ip = req.getRemoteAddr();
		else{
			ip = ip.split(",")[0].trim();
		}
		return ip;
	}

	public static String urlUtf8Decode(String str) throws UnsupportedEncodingException {
		return URLDecoder.decode(str, "UTF-8");
	}
	
	public static String[] getStringArray(HttpServletRequest req, String name) {
		
		if (req.getParameterValues(name) != null) {
			return req.getParameterValues(name);
		} else {
			return new String[] {};
		}
		
	}
	
	/**
	 * 스트링값 획득
	 *
	 * @param str the str
	 * @return the str
	 */
	public static String getStr(String str) {
		String s = "";
		if (str == null) return s;
		return str;
	}
	 
	/**
	 * 스트링값 획득
	 *
	 * @param str the str
	 * @param def the def
	 * @return the str
	 */
	public static String getStr(String str, String def) {
		String val = getStr(str);
		return val.equals("") ? def : val;
	}
	
	/**
	 * 문자열 뒤집기
	 *
	 * @param str the str
	 * @param def the def
	 * @return the str
	 */
	public static String reverseStr(String str) {
	    return ( new StringBuffer(str) ).reverse().toString();
	}
	
	/**
	 * '*' 처리
	 * 
	 * @param valueObj 변환 대상
	 * @param beginIndex 시작 index, 시작 index보다 사이즈가 작으면 그냥 반환
	 * @param asteriskCount 처리할 갯수(0)이면 모두 처리
	 * @return
	 */
	public static String asterisk(Object valueObj, int beginIndex, int asteriskCount) {
		if (valueObj == null || org.apache.commons.lang3.StringUtils.isBlank(valueObj.toString())) {
			return "";
		}
		String str = valueObj.toString();
		if (str.length() < beginIndex) {
			return str;
		}
		// 1. 앞에 문자 추출
		String preStr = str.substring(0, beginIndex);
		// 2. 뒤에 문자 추출
		String postStr = asteriskCount > 0 ? org.apache.commons.lang3.StringUtils.substring(str, beginIndex + asteriskCount) : "";
		// 3. 별표 변환 대상 문자 추출
		String targetStr = "".equals(preStr) ? str : org.apache.commons.lang3.StringUtils.removeStart(str, preStr);
		targetStr = "".equals(postStr) ? targetStr : org.apache.commons.lang3.StringUtils.removeEnd(targetStr, postStr);
		String startStr = org.apache.commons.lang3.StringUtils.repeat("*", targetStr.length());
		return String.format("%s%s%s", preStr, startStr, postStr);
	}
	/**
	 * 01011113333 -> 010-1111-3333
	 * 010-1111-3333 -> 010-1111-3333
	 * 나머지 유효하지 않으면 "" 리턴
	 * @param number
	 * @return
	 */
	public static String phoneFormat(String number) {
		String temp = getStr(number).trim();
		temp = temp.replaceAll("\\D", "");
		String regEx = "(\\d{3})(\\d{3,4})(\\d{4})"; 
		return temp.replaceAll(regEx, "$1-$2-$3"); 
	}

	/**
	 * 핸드폰번호 하이픈 넣어서 맞는지 체크
	 * @param number
	 * @return
	 */
	public static boolean validationPhoneWithHypen(String number) {
		Pattern pattern = Pattern.compile("\\d{3}-\\d{3,4}-\\d{4}");
		Matcher matcher = pattern.matcher(number);
		return matcher.matches();
	}
	
	/**
	 * 핸드폰번호 하이픈없이 맞는지 체크
	 * @param number
	 * @return
	 */
	public static boolean validationPhoneWithoutHypen(String number) {
		Pattern pattern = Pattern.compile("(\\d{3})(\\d{3,4})(\\d{4})");
		Matcher matcher = pattern.matcher(number);
		return matcher.matches();
	}
	
	/**
	 * 입력한 값이 패스워드 형태인지 체크한다.
	 * 프론트에서 체크하는 것과 같다.
	 * https://regex101.com/ 에서 자바스크립트 정규식을 자바 정규식으로 Code Generator로 바꿀수있음
	 * @param value 최소 8 자, 하나 이상의 문자, 하나의 숫자 및 하나의 특수 문자 또는 최소 10 자, 영문/숫자/특수문자 2가지 이상 조합
	 * @return true, false
	 */
	public static boolean isPassword(String value) {
        if (isEmpty(value)) {
            return false;
        }
        
        // 최소 8 자, 하나 이상의 문자, 하나의 숫자, 하나의 특수 문자 조합
        // /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/
        Pattern p = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");//. represents single character  
        boolean reg1Valid = p.matcher(value).matches();  

        // 최소 10 자, 영문/숫자/특수문자 2가지 이상 조합
        boolean reg2Valid = true;
        boolean num = Pattern.compile("[0-9]").matcher(value).find(); // 숫자체크, 없으면 0 리턴
        boolean eng = Pattern.compile("[A-Za-z]").matcher(value).find(); // 영문체크, 없으면 0 리턴
        boolean spe = Pattern.compile("[~!@#$%^&*()_+|<>?:\\{\\}]").matcher(value).find(); // 특수문자체크, 없으면 0 리턴
        int len = value.length(); // 최소 길이 체크
        if (len < 10 || (num == false && eng == false) || (eng == false && spe == false) || (spe == false && num == false) ) {
            reg2Valid = false;
        }
        return reg1Valid || reg2Valid;
	}
	
	/**
	 * 아이디 유효성 검사
	 *
	 * @param id the id
	 * @param min the min
	 * @param max the max
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public static boolean checkInvalidId(String id,String min,String max) throws Exception {
		String filterStr = "^[a-zA-Z0-9_]{" + min + "," + max + "}$";
		
		if (Util.eRegiTest(id, filterStr)) {
			return true;
		} else {
			return false;
		}
	}
	
	/*************************************
	 * KMC 본인인증 파라미터 유효성 검사
	 * @param patn
	 * @param param
	 * @return
	 */
	public static Boolean paramChk(String patn, String param){
		Pattern pattern = Pattern.compile(patn);
		Matcher matcher = pattern.matcher(param);
		boolean b = matcher.matches();
		return b;
	}
	
	/**금칙어 처리
	 * @param badWordList
	 * @param sText
	 * @return 사용된 금칙어 반환
	 */
	public static String filterText(String badWordList, String sText) {
		if(badWordList == null || badWordList.length() <= 0) {
			return "";
		}
	    Pattern p = Pattern.compile(badWordList, Pattern.CASE_INSENSITIVE);
	    Matcher m = p.matcher(sText);

	    StringBuffer sb = new StringBuffer();
	    while (m.find()) {
	    	sb.append(m.group() + " ");
	    }

	    return sb.toString();
	}
	
	/**
	 * List<SOMap>으로 변환 
	 * @param list
	 * @return
	 */
	public static List<SOMap> convertToListSOMap(List<Map<String, Object>> list) {
		List<SOMap> result = new ArrayList<SOMap>();
		for (Map<String, Object> map : list) {
			SOMap somap = new SOMap();
			somap.putAll(map);
			result.add(somap);
		}
		
		return result;
	}
	
	public static String getMainDomain(String domain) {
		String result = null;
		if(domain == null) {
			return result;
		}
		String[] split = domain.split("\\.");
		
		if(split.length == 4) {
			result = split[1] + "." + split[2] + "." + split[3];
		} else {
			result = domain.replaceAll(".*\\.(?=.*\\.)", "");
		}
		
		result = result.split("\\:")[0];
		
		logger.debug("getMainDomain : " + domain + "->" + result);
		return result;
	}
}
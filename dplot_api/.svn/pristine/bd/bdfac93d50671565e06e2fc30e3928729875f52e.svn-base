package com.dplot.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

/**
 * 변환관련 클래스
 */
public class Converter {

	// server validation
	/**
	 * html코드에 주석이 있는지 여부 판단
	 *
	 * @param params the params
	 * @param flag the flag
	 * @return true, if successful
	 */
	public static boolean validationChk(Map<String,Object> params, String flag) {
		boolean result = true;

		if (flag.indexOf("notsp")  > -1) {
			Iterator<String> it = params.keySet().iterator();

			while (it.hasNext()) {
				String key = (it.next());
				String value = (String)(params.get(key));
				if (value.contains("<") || value.contains(">") || value.contains("--") || value.contains("'")) {
					result = false;
					break;
				}
			}
		}

		return result;
	}

	/**
	 * null 이면 디폴트 문자열 리턴
	 *
	 * @param val the val
	 * @return the string
	 */
	public static String nvl(String val) {
		return nvl(val, "");
	}

	/**
	 * null 이면 디폴트 문자열 리턴
	 *
	 * @param val the val
	 * @param def the def
	 * @return the string
	 */
	public static String nvl(String val, String def) {
		if (val == null || val.equals("")) return def;
		return val;
	}

	/**
	 * 스트링으로 변환
	 *
	 * @param val the val
	 * @return the string
	 */
	public static String toStr(Object val) {
		String result = "";
		if (val != null) result = val.toString();
		return result;
	}

	/**
	 * 스트링으로 변환
	 *
	 * @param val the val
	 * @return the string
	 */
	public static String toStr(Object val, String def) {
		String result = "";

		if (val == null || "".equals(val)) {
			return def;
		}

		if (val != null) result = val.toString();
		return result;
	}

	/**
	 * 기본 값이 적용된 Integer로 변환.
	 * @param val
	 * @param defaultValue
	 * @return
	 */
	public static Integer toIntWithDefaultValue(Object val, int defaultValue) {
		int result = defaultValue;
		
		if (val == null) {
			return defaultValue;
		}
		
		// 실수 표기 처리를 위한 Formatter.
		NumberFormat numberFormatter = DecimalFormat.getInstance();
		numberFormatter.setMaximumFractionDigits(1);
		
		String sVal = null;
		if (val instanceof Float) {
			sVal = numberFormatter.format((float) val);
		} else if ( val instanceof Double) {
			sVal = numberFormatter.format((double) val);
		} else {
			sVal = val.toString().trim();
			
			// 12.345E6 과 같이 지수+가수 형태의 문자열이 들어올 때 Double로 처리.
			int dotPosition = sVal.indexOf(".");
			int ePosition = sVal.indexOf("E");
			if (dotPosition > 0 && ePosition > 0
					&& dotPosition < ePosition) {
				try {
					Double dVal = numberFormatter.parse(sVal).doubleValue();
					sVal = toIntWithDefaultValue(dVal, defaultValue).toString();
				} catch (ParseException pe) {
					// sVal == 0 이므로 아무 것도 하지 않음.
				}
			}
		}
		
		sVal = sVal.replaceAll(",", "");
		
		// 소수점 이하 버림.
		int pos = sVal.indexOf(".");
		if (pos > 0) {
			sVal = sVal.substring(0, pos);
		}
		
		try {
			result = Integer.parseInt(sVal);
		} catch (NumberFormatException nfe) {
			// result == 0 이므로 아무 것도 하지 않음.
		}
		
		return result;
	}
	
	/**
	 * 인티져로 변환
	 *
	 * @param val the val
	 * @return the integer
	 */
	public static Integer toInt(Object val) {
		return toIntWithDefaultValue(val, 0);
	}
	
	/**
	 * Long형으로 변환
	 *
	 * @param val the val
	 * @return the long
	 */
	public static Long toLong(Object val) {
		String v1 = (val==null) ? "0" : val.toString().trim();
		v1 = (v1.equals("")) ? "0" : v1;
		v1 = v1.replaceAll(",", "");
		long i = 0;
		try {
			i = Long.parseLong(v1);
		} catch (NumberFormatException e) {
			i = 0;
		}

		return i;
	}

	/**
	 * Double형으로 변환
	 *
	 * @param val the val
	 * @return the double
	 */
	public static double toDouble(Object val) {
		String v1 = (val==null) ? "0" : val.toString().trim();
		v1 = (v1.equals("")) ? "0" : v1;
		v1 = v1.replaceAll(",", "");
		double i = 0;
		try {
			i = Double.valueOf(v1).doubleValue();
		} catch (NumberFormatException e) {
			i = 0;
		}

		return i;
	}

	/**
	 * 맵을 어레이로 변환
	 *
	 * @param map the map
	 * @return the object[]
	 */
	public static Object[] toArr(Map<String,Object> map) {
		if (map.isEmpty()) return null;

		int s = map.size();

		Object[] args = new Object[s];

		for (Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
			String key = it.next();
			Object val = map.get(key);

			args[Integer.parseInt(key)]=val;
		}

		return args;
	}

	/**
	 * 금액으로 변환
	 *
	 * @param money the money
	 * @return the string
	 */
	public static String toWon(Object money) {
			String str;
			String rstr="",dstr="";
			int ind=0,l=0,m=0,n=0,s;

			str=String.valueOf(money);
			l = str.length();
			ind = str.indexOf(".");

			if (ind > -1) {
				dstr = str.substring(ind,l);
				str = str.substring(0,ind);
				l = str.length();
			}
			m = l/3;
			n = l%3;

			if (n > 0) rstr = str.substring(0,n);

			for (int i=0;i<m;i++) {
				s = i*3 + n;
				if (n == 0 && i == 0) rstr = str.substring(s,s+3);
				else rstr += "," + str.substring(s,s+3);
			}

			return rstr + dstr;
	}

	/**
	 * 앞자리 len만큼 0를 채운다
	 *
	 * @param val the val
	 * @param len the len
	 * @return the string
	 */
	public static String setZeroFill(String val, int len) {
		StringBuffer sb = new StringBuffer();
		for (int i=0; i<len; i++) {
			sb.append("0");
		}
		sb.append(val);

		return RightString(sb.toString(),len);
	}

	/**
	 * 앞자리 len만큼 0를 채운다
	 *
	 * @param val the val
	 * @param len the len
	 * @return the string
	 */
	public static String setZeroFill(int val, int len) {
		String value = String.format("%d", val);
		return setZeroFill(value, len);
	}

	/**
	 * 길이n 에 맞도록 0을 채운다
	 *
	 * @param val the val
	 * @param n the n
	 * @return the string
	 */
	public static String addZero(Object val, int n) {
		String result = (val == null) ? "":val.toString().trim();

		StringBuffer sb = new StringBuffer();

		int l = result.length();

		for (int i=l;i<n;i++) {
			sb.append("0");
		}

		return sb.append(result).toString();

	}

	/**
	 * 길이n 에 맞도록 0을 채운다
	 *
	 * @param val the val
	 * @param n the n
	 * @return the string
	 */
	public static String addRightZero(Object val, int n) {
		String result = (val == null) ? "":val.toString().trim();

		int l = result.length();

		StringBuffer sb = new StringBuffer();
		sb.append(result);

		for (int i=l;i<n;i++) {
			sb.append("0");
		}

		return sb.toString();

	}

	/**
	 * 길이n 에 맞도록 공백을 채운다
	 *
	 * @param val the val
	 * @param n the n
	 * @return the string
	 */
	public static String addBlank(Object val, int n) {
		StringBuffer sb = new StringBuffer();
		String result = (val == null) ? "":val.toString().trim();

		int l = result.length();
		for (int i=l;i<n;i++) {
			sb.append(" ");
		}

		return sb.toString();
	}

	/**
	 * camel case로 변환
	 *
	 * @param str the str
	 * @return the string
	 */
	public static String toCamelCase(String str) {
		// 2014.03.11 수정
		String a = str.toLowerCase();
		//String b = "";
		StringBuffer bBuf = new StringBuffer();
		int s=0;

		int k=a.indexOf(("_"),s);

		while(k>-1) {
			// b+=a.substring(s, k);
			bBuf.append(a.substring(s, k));
			// b+=a.substring(k+1, k+2).toUpperCase();
			bBuf.append(a.substring(k+1, k+2).toUpperCase());
			s=k+2;
			k=a.indexOf(("_"),s);
		}

		// b+=a.substring(s);
		bBuf.append(a.substring(s));

		return bBuf.toString();
	}

	/**
	 * pascal case로 변환
	 *
	 * @param str the str
	 * @return the string
	 */
	public static String toPascalCase(String str) {
		String b = "";

		if (! "".equals(str))
			b=str.substring(0,1).toUpperCase()+str.substring(1);

		return b;
	}

	/**
	 * Html special chars 변환.
	 *
	 * @param str the str
	 * @return the string
	 */
	public static String htmlSpecialChars(String str) {
		if (str == null) {
			return "";
		}

		str = str.replaceAll("\"", "&quot;");
		str = str.replaceAll("\\)", "&#41;");
		str = str.replaceAll("\\(", "&#40;");
		str = str.replaceAll("'", "&#39;");
		str = str.replaceAll(" ", "&nbsp;");
		str = str.replaceAll(">", "&gt;");
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll("&", "&amp;");

		return str;
	}

	/**
	 * 특수문자를 html로 변환
	 *
	 * @param 특수문자 포함한 스트링
	 * @return 변환된 html
	 */
	public static String getTtextToTag(String str) {
		if (str == null) {
			return "";
		}

		str = str.replaceAll("&amp;", "&");
		str = str.replaceAll("&lt;", "<");
		str = str.replaceAll("&gt;", ">");
		str = str.replaceAll("&nbsp;", " ");
		str = str.replaceAll("&#39;", "'");
		str = str.replaceAll("&#40;", "\\(");
		str = str.replaceAll("&#41;", "\\)");
		str = str.replaceAll("&quot;", "\"");

		return str;
	}

	
	/**
	 * Db의 in조건 형태로 변환
	 *
	 * @param str the str
	 * @return the string
	 */
	public static List<Integer> toIntArrayByComma(String str) {
		
		List<Integer> dataList = new ArrayList<Integer>();
		
		if (Util.hasText(str)) {
			for (String s : str.split(",")) {
				if (Util.isNumber(s)) {
					dataList.add(Integer.parseInt(s));
				}
			}

		}
		
		return dataList;
	}
	
	/**
	 * Db의 in조건 형태로 변환
	 *
	 * @param str the str
	 * @return the string
	 */
	public static List<String> toStringArray(String ...str ) {
		
		List<String> dataList = new ArrayList<String>();
		
		if (str != null && str.length > 0) {
			for (String s : str) {
				dataList.add(s);
			}
		}
		
		return dataList;
	}
	
	
	public static List<Integer> toIntArray(String arr[]){

		List<Integer> data = new ArrayList<Integer>();

		for (String string : arr) {
			if (Util.hasText(string)){
				try {
					data.add(Integer.parseInt(string));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return data;
	}

	/**
	 * Db의 in조건 형태로 변환
	 *
	 * @param str the str
	 * @return the string
	 */
	public static String toInCodeByComma(String str) {
		return toInIdx(str.split(","));
	}

	/**
	 * Db의 in조건 형태로 변환
	 *
	 * @param arr the arr
	 * @return the string
	 */
	public static String toInIdx(java.lang.Object[] arr) {
		String strIdxs = StringUtils.arrayToCommaDelimitedString(arr);
		if (Util.hasText(strIdxs)) return strIdxs;

		return "0";
	}

	/**
	 * 스트링과 어레이를 묶어서 변환
	 *
	 * @param str the str
	 * @param arr the arr
	 * @return the string
	 */
	public static String strGlueArray(String str, java.lang.Object[] arr) {
		String strIdxs = StringUtils.arrayToCommaDelimitedString(arr);
		if (Util.hasText(strIdxs)) {
			str = str + ", " + strIdxs;
		}

		return str;
	}

	/**
	 * 스트링과 어레이를 묶어서 변환
	 *
	 * @param str the str
	 * @param delim the delim
	 * @param arr the arr
	 * @return the string
	 */
	public static String strGlueArray(String str, String delim, java.lang.Object[] arr) {
		String strIdxs = StringUtils.arrayToDelimitedString(arr, delim);
		if (Util.hasText(strIdxs)) {
			str = str + delim + " " + strIdxs;
		}

		return str;
	}

	/**
	 * 스트링의 오른쪽 len을 반환
	 *
	 * @param sText the s text
	 * @param iTextLenth the i text lenth
	 * @return the string
	 */
	public static String RightString(String sText, int iTextLenth) {
		String sConvertText;

		if (sText.length() < iTextLenth) {
			iTextLenth = sText.length();
		}

		sConvertText= sText.substring(sText.length() - iTextLenth, sText.length());

		return sConvertText;
	}

	/**
	 * 전화번호 형태로 변환
	 *
	 * @param a the a
	 * @param b the b
	 * @param c the c
	 * @return the string
	 */
	public static String makeTel(String a, String b, String c) {
		return Util.hasAllText(a, b, c)  ? a + "-" + b + "-" + c : "";
	}

	/**
	 * 이메일 형태로 변환
	 *
	 * @param a the a
	 * @param b the b
	 * @return the string
	 */
	public static String makeEmail(String a, String b) {
		return Util.hasAllText(a, b)  ? a + "@" + b : "";
	}

	/**
	 * 우편번호 형태로 변환
	 *
	 * @param a the a
	 * @param b the b
	 * @return the string
	 */
	public static String makePost(String a, String b) {
		return Util.hasAllText(a, b)  ? a + "-" + b : "";
	}

	/**
	 *
	* @Method Name : utf8ToEuckr
	* @Method 설명 : utf8 문자열을 euc-kr 로 변환한다.
	*
	* @param utf8
	* @return
	* @throws IOException
	 */
	public static String utf8ToEuckr(String utf8) throws IOException {
		return enc(utf8, "EUC-KR");
	}

	/**
	 *
	* @Method Name : euckrToUtf8
	* @Method 설명 : euc-kr 문자열을 utf8 로 변환한다.
	*
	* @param euckr
	* @return
	* @throws IOException
	 */
	public static String euckrToUtf8(String euckr) throws IOException {
		return enc(euckr, "UTF-8");
	}

	/**
	 *
	* @Method Name : boolToYN
	* @Method 설명 : boolean 을 Y 또는 N 으로 변환한다.
	*
	* @param yn
	* @return
	 */
	public static String boolToYN(boolean yn) {
		return yn ? "Y" : "N";
	}


	/**
	 *
	* @Method Name : arrayParamJoin
	* @Method 설명 : 어레이를 "," 문자열로 조합한다.
	*
	* @param arr
	* @return
	 */
	public static String arrayParamJoin(Object[] arr) {
		return arrayParamJoin(arr, "", ",");
	}

	/**
	 *
	* @Method Name : arrayParamJoin
	* @Method 설명 : 어레이를 "," 문자열로 조합한다.
	*
	* @param arr
	* @param def
	* @return
	 */
	public static String arrayParamJoin(Object[] arr, String def) {
		return arrayParamJoin(arr, def, ",");
	}

	/**
	 *
	* @Method Name : arrayParamJoin
	* @Method 설명 : 어레이를 "," 문자열로 조합한다.
	*
	* @param arr
	* @param def
	* @param join
	* @return
	 */
	public static String arrayParamJoin(Object[] arr, String def, String join) {
		if (arr == null || arr.length == 0) return def;

		return StringUtils.arrayToDelimitedString(arr, join);
	}

	/**
	 *
	* @Method Name : enc
	* @Method 설명 : 해당 인코딩으로 변환한다.
	*
	* @param str
	* @param encoding
	* @return
	* @throws IOException
	 */
	public static String enc(String str, String encoding) throws IOException {
		ByteArrayOutputStream requestOutputStream = new ByteArrayOutputStream();
		requestOutputStream.write(str.getBytes(encoding));
		return requestOutputStream.toString(encoding);
	}
}
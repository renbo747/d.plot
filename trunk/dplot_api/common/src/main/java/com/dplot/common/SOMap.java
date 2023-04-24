package com.dplot.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import com.dplot.util.Util;

/**
 * 몰스토어에서 사용하는 기본 해쉬맵
 */
public class SOMap extends HashMap<String, Object> implements Serializable {

	private static final long serialVersionUID = -386055286582898088L;

	/**
	 * 생성자
	 */
	public SOMap() {
	}
	
	/**
	 * 생성자
	 */
	public SOMap(SOMap map) {
		super(map);
	}

//	@Override
//	public Object get(Object key) {
//		return super.get(key.toString().toLowerCase());
//	}
//
//	@Override
//	public boolean containsKey(Object key) {
//		return super.containsKey(key.toString().toLowerCase());
//	}

	@Override
	public Object put(String key, Object value) {
		return super.put(key.toLowerCase(), value);
	}

	/**
	 * DB의 int 값 획득
	 *
	 * @param key the key
	 * @return the db int
	 */
	public int getDbInt(String key) {
		return getInt(key.toLowerCase());
	}

	/**
	 * int값 획득
	 *
	 * @param key the key
	 * @return the int
	 */
	public int getInt(String key) {
		int i = 0;
		Object o = get(key);
		if (o == null) return i;

		String s = o.toString().trim().replace(",", "");
		try {
			i = Integer.parseInt(s);
		} catch (NumberFormatException e) {
		}

		return i;
	}

	/**
	 * 최소값이 적용된 int값 획득
	 *
	 * @param key the key
	 * @param min the min
	 * @return the int
	 */
	public int getInt(String key, int min) {
		int i = getInt(key);
		return (i < min) ? min : i;
	}


	public long getDbLong(String key) {
		return getLong(key.toLowerCase());
	}

	public long getLong(String key) {
		long i = 0;
		Object o = get(key);
		if (o == null) return i;

		String s = o.toString().trim().replace(",", "");
		try {
			i = Long.parseLong(s);
		} catch (NumberFormatException e) {
		}

		return i;
	}

	/**
	 * float값 획득
	 *
	 * @param key the key
	 * @return the int
	 */
	public float getFloat(String key) {
		float i = 0.0f;
		Object o = get(key);
		if (o == null) return i;

		String s = o.toString().trim().replace(",", "");
		try {
			i = Float.parseFloat(s);
		} catch (NumberFormatException e) {
		}

		return i;
	}

	public float getDbFloat(String key) {
		return getFloat(key.toLowerCase());
	}

	/**
	 * 스트링값 획득
	 *
	 * @param key the key
	 * @param def the def
	 * @return the str
	 */
	public String getStr(String key, String def) {
		String val = getStr(key);
		return val.equals("") ? def : val;
	}

	public void putDb(String key, Object value) {
		put(key.toLowerCase(), value == null ? "" : value);
	}

	/**
	 * 디비의 스트링값 획득
	 *
	 * @param key the key
	 * @return the db str
	 */
	public String getDbStr(String key) {
		return getStr(key.toLowerCase());
	}
	public String getDbStr(String key, String _default) {
		String str = getDbStr(key);
		return Util.hasText(str) ? str : _default;
	}

	/**
	 * 스트링값 획득
	 *
	 * @param key the key
	 * @return the str
	 */
	public String getStr(String key) {
		String s = "";
		Object o = get(key);
		if (o == null) return s;

		return o.toString();
	}

	/**
	 * Trim된 스트링 획득
	 *
	 * @param key the key
	 * @return the str trim
	 */
	public String getStrTrim(String key) {
		return getStr(key).trim();
	}

	public String getStrNoSpaceTrim(String key) {
		return getStr(key).replaceAll(" ", "").trim();
	}

	/**
	 * 디비의 더블형 획득
	 *
	 * @param key the key
	 * @return the db double
	 */
	public double getDbDouble(String key) {
		return getDouble(key.toLowerCase());
	}

	/**
	 * 더블형 획득
	 *
	 * @param key the key
	 * @return the double
	 */
	public double getDouble(String key) {
		double d = 0;
		Object o = get(key);
		if (o == null) return d;

		String s = o.toString().trim().replace(",", "");
		try {
			d = Double.valueOf(s).doubleValue();
		} catch (NumberFormatException e) {
		}

		return d;
	}

	/**
	 * 디비의 날짜형 스트링 획득
	 *
	 * @param key the key
	 * @return the db date str
	 */
	public String getDbDateStr(String key) {
		return getDateStr(key.toLowerCase());
	}

	/**
	 * 날짜형 스트링 획득
	 *
	 * @param key the key
	 * @return the date str
	 */
	public String getDateStr(String key) {
		String s = "";
		Object o = get(key);
		if (o == null) return s;

		s = o.toString();
		if (s.length() > 19) s = s.substring(0, 19);
		return s;
	}

	/**
	 * 날짜형 스트링 획득
	 *
	 * @param key the key
	 * @return the date str
	 */
	public String getDateStrShort(String key) {
		String s = "";
		Object o = get(key);
		if (o == null) return s;

		s = o.toString();
		if (s.length() > 19) s = s.substring(0, 10);
		return s;
	}

	/**
	 * SOMAP 획득
	 *
	 * @param key the key
	 * @return the str
	 */
	@SuppressWarnings("unchecked")
	public SOMap getSOMap(String key) {
		HashMap<String, Object> o = (HashMap<String, Object>) get(key);
		if (o == null) return null;
		SOMap result = new SOMap();
		result.putAll(o); 
		return result;
	}

	/**
	 * Arraylist<String> 획득
	 *
	 * @param key the key
	 * @return the str
	 */
	@SuppressWarnings("unchecked")
	public <T> ArrayList<T> getArrayList(String key) {
		Object o = get(key);
		ArrayList<T> list = new ArrayList<T>();
		if (o == null) return list;

		if (o instanceof ArrayList) {
			list = (ArrayList<T>)o;
			return list;
		} else {
			return list;
		}
	}

	public static class ascCompare implements Comparator<SOMap> {
		/**
		 * 오름차순(ASC)
		 */
		@Override
		public int compare(SOMap arg0, SOMap arg1) {
			// TODO Auto-generated method stub
			return arg0.getStr("SORTKEY").compareTo(arg1.getStr("SORTKEY"));
		}
	}

	public static class descCompare implements Comparator<SOMap> {
		/**
		 * 오름차순(DESC)
		 */
		@Override
		public int compare(SOMap arg0, SOMap arg1) {
			// TODO Auto-generated method stub
			return arg1.getStr("SORTKEY").compareTo(arg0.getStr("SORTKEY"));
		}
	}

}
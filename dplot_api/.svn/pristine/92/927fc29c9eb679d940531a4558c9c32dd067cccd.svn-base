package com.dplot.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * DateTime 클래스.
 * 거의 모든 함수가 MallDateTime 에 포팅되었으며,
 * DateTime 클래스는 가급적 사용하지 않도록 합니다.
 */
public final class DateTime {

	/**
	 * Don't let anyone instantiate this class.
	 */
	private DateTime() {}

	/**
	 * check date string validation with the default format "yyyyMMdd".
	 *
	 * @param s date string you want to check with default format "yyyyMMdd".
	 * @return date java.util.Date
	 * @throws ParseException the parse exception
	 */
	public static java.util.Date check(String s) throws java.text.
		ParseException {
		return check(s, "yyyyMMdd");
	}

	/**
	 * Removes the non number.
	 *
	 * @param org the org
	 * @return the string
	 */
	public static String _removeNonNumber(String org) {
		return org.replace("-", "").replace(" ", "").replace(":", "");
	}

	/**
	 * check date string validation with an user defined format.
	 *
	 * @param s date string you want to check.
	 * @param format string representation of the date format. For example, "yyyy-MM-dd".
	 * @return date java.util.Date
	 * @throws ParseException the parse exception
	 */
	public static java.util.Date  check(String s, String format) throws java.text.ParseException {
		if (s == null) {
			throw new java.text.ParseException("date string to check is null", 0);
		}

		if (s.equals("")) {
			throw new java.text.ParseException("date string to check is null", 0);
		}

		if (format == null) {
			throw new java.text.ParseException("format string to check date is null", 0);
		}

		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format, java.util.Locale.KOREA);
		java.util.Date date = null;

		try {
			date = formatter.parse(s);
		}
		catch (java.text.ParseException e) {
			throw new java.text.ParseException(" wrong date:\"" + s + "\" with format \"" + format + "\"", 0);
		} // End of try ~ catch

		if (! formatter.format(date).equals(s)) {
			throw new java.text.ParseException("Out of bound date:\"" + s + "\" with format \"" + format + "\"", 0);
		}

		return date;
	} // End of check()

	/**
	 * check date string validation with the default format "yyyyMMdd".
	 *
	 * @param s date string you want to check with default format "yyyyMMdd"
	 * @return boolean true 날짜 형식이 맞고, 존재하는 날짜일 때
	 *				 false 날짜 형식이 맞지 않거나, 존재하지 않는 날짜일 때
	 * @throws Exception the exception
	 */
	public static boolean _isValid(String s) throws Exception {
		return DateTime._isValid(s, "yyyyMMdd");
	} // End of isValid()

	/**
	 * check date string validation with an user defined format.
	 * @param s date string you want to check.
	 * @param format string representation of the date format. For example, "yyyy-MM-dd".
	 * @return boolean true 날짜 형식이 맞고, 존재하는 날짜일 때
	 *				 false 날짜 형식이 맞지 않거나, 존재하지 않는 날짜일 때
	 */
	public static boolean _isValid(String s, String format) {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format, java.util.Locale.KOREA);
		java.util.Date date = null;

		try {
			date = formatter.parse(s);
		}
		catch (java.text.ParseException e) {
			return false;
		} // End of try ~ catch

		if (! formatter.format(date).equals(s)) {
			return false;
		}

		return true;
	} // End of isValid()

	/**
	 * Gets the date string.
	 *
	 * @return formatted string representation of current day with  "yyyy-MM-dd".
	 */

	// getDateString to move MallDateTime.getNowDatePartStr
	public static String _getDateString() {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.KOREA);
		return formatter.format(new java.util.Date());
	} // End of getDateString()

	/**
	 * Gets the date string.
	 *
	 * @return formatted string representation of current day with  "yyyy-MM-dd".
	 */

	// getDateString to move MallDateTime.getNowDatePartStr
	public static String _getDateString(Date date) {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.KOREA);
		return formatter.format(date);
	} // End of getDateString()

	/**
	 * Gets the date string.
	 *
	 * @param flag the flag
	 * @return the date string
	 */
	public static String _getDateString(boolean flag) {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd", java.util.Locale.KOREA);
		return formatter.format(new java.util.Date());
	} // End of getDateString()

	/**
	 * Gets the date string for han.
	 *
	 * @return the date string for han
	 */
	public static String _getDateStringForHan() {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmss", java.util.Locale.KOREA);

		String date = formatter.format(new java.util.Date());

		return date.substring(0, 4) + "년 " + date.substring(4, 6) + "월 " +
			date.substring(6, 8) + "일 " + date.substring(8,10) + "시 " + date.substring(10, 12) + "분 " + date.substring(12, 14) + "초";
	} // End of getDateString()

	/**
	 * Gets the date string for web.
	 *
	 * @return formatted string representation of current day with  "yyyy-MM-dd".
	 */
	public static String _getDateStringForWeb() {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd", java.util.Locale.KOREA);

		String date = formatter.format(new java.util.Date());

		return date.substring(0, 4) + "년 " + date.substring(4, 6) + "월 " + date.substring(6, 8) + "일";
	} // End of getDateString()

	/**
	 * 입력받은 yyyymmdd형식의 값을 yyyy년 mm월 dd일 형식으로 리턴.
	 *
	 * @param date String
	 * @return String
	 */
	public static String _getDateString(String date) {
		return date.substring(0, 4) + "년 " + date.substring(4, 6) + "월 " + date.substring(6, 8) + "일";
	} // End of getDateString()

	/**
	 * For example, String time = DateTime.getFormatString("yyyy-MM-dd HH:mm:ss");
	 *
	 * @return formatted string representation of current day and time with  your pattern.
	 */
	public static int _getDay() {
		return _getNumberByPattern("dd");
	} // End of getDay()

	/**
	 * For example, String time = DateTime.getFormatString("yyyy-MM-dd HH:mm:ss");
	 *
	 * @return formatted string representation of current day and time with  your pattern.
	 */
	public static int _getYear() {
		return _getNumberByPattern("yyyy");
	} // End of getYear()

	/**
	 * For example, String time = DateTime.getFormatString("yyyy-MM-dd HH:mm:ss");
	 *
	 * @return formatted string representation of current day and time with  your pattern.
	 */
	public static int _getHour() {
		return _getNumberByPattern("HH");
	}

	/**
	 * For example, String time = DateTime.getFormatString("yyyy-MM-dd HH:mm:ss");
	 *
	 * @return formatted string representation of current day and time with  your pattern.
	 */
	public static int _getMinute() {
		return _getNumberByPattern("mm");
	}

	/**
	 * For example, String time = DateTime.getFormatString("yyyy-MM-dd HH:mm:ss");
	 *
	 * @return formatted string representation of current day and time with  your pattern.
	 */
	public static int _getSecond() {
		return _getNumberByPattern("ss");
	}

	/**
	 * For example, String time = DateTime.getFormatString("yyyy-MM-dd HH:mm:ss");
	 *
	 * @return formatted string representation of current day and time with  your pattern.
	 */
	public static int _getMonth() {
		return _getNumberByPattern("MM");
	}

	/**
	 * For example, String time = DateTime.getFormatString("yyyy-MM-dd HH:mm:ss");
	 *
	 * @param pattern the pattern
	 * @return formatted string representation of current day and time with  your pattern.
	 */
	public static int _getNumberByPattern(String pattern) {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(pattern, java.util.Locale.KOREA);

		String dateString = formatter.format(new java.util.Date());

		return Integer.parseInt(dateString);
	}

	/**
	 * Locale이 한국 시간으로 됨...
	 * @param pattern String
	 * @return String
	 */
	public static String _getFormatString(String pattern) {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(pattern, java.util.Locale.KOREA);
		String dateString = formatter.format(new java.util.Date());
		return dateString;
	}

	/**
	 * Gets the short date string.
	 *
	 * @return the short date string
	 */
	public static String _getShortDateString() {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd", java.util.Locale.KOREA);
		return formatter.format(new java.util.Date());
	}

	/**
	 * Gets the short date string.
	 *
	 * @param format the format
	 * @return the short date string
	 */
	public static String _getShortDateString(String format) {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format, java.util.Locale.KOREA);
		return formatter.format(new java.util.Date());
	}

	/**
	 * Gets the now time string.
	 *
	 * @param format the format
	 * @return the now time string
	 */
	public static String _getNowTimeString(String format) {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format, java.util.Locale.KOREA);
		return formatter.format(new java.util.Date());
	}

	/**
	 * Gets the short time string.
	 *
	 * @return formatted string representation of current time with  "HHmmss".
	 */
	public static String _getShortTimeString() {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("HHmmss", java.util.Locale.KOREA);
		return formatter.format(new java.util.Date());
	}

	/**
	 * Gets the time stamp string.
	 *
	 * @return formatted string representation of current time with  "yyyy-MM-dd-HH:mm:ss".
	 */
	public static String _getTimeStampString() {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd-HH:mm:ss:SSS", java.util.Locale.KOREA);
		return formatter.format(new java.util.Date());
	}

	/**
	 * Gets the time stamp string.
	 *
	 * @return formatted string representation of current time with  "yyyy-MM-dd HH:mm:ss".
	 */
	public static String _getTimeStampString3() {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.KOREA);
		return formatter.format(new java.util.Date());
	}

	/**
	 * Gets the time stamp string.
	 *
	 * @return formatted string representation of current time with  "yyyy-MM-dd HH:mm:ss".
	 */
	public static String _getTimeStampString3(Date date) {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.KOREA);
		return formatter.format(date);
	}

	/**
	 * Gets the time stamp strings.
	 *
	 * @return formatted string representation of current time with  "yyyy-MM-dd-HH:mm:ss".
	 */
	public static String _getTimeStampStrings() {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmssSSS", java.util.Locale.KOREA);
		return formatter.format(new java.util.Date());
	}

	/**
	 * Gets the time stamp string2.
	 *
	 * @return formatted string representation of current time with  "yyyy-MM-dd-HH:mm:ss".
	 */
	public static String _getTimeStampString2() {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss", java.util.Locale.KOREA);
		return formatter.format(new java.util.Date());
	}

	/**
	 * Gets the time stamp string2.
	 *
	 * @return formatted string representation of current time with  "yyyy-MM-dd-HH:mm:ss".
	 */
	public static String _getTimeStampString2(Date date) {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss", java.util.Locale.KOREA);
		return formatter.format(date);
	}

	/**
	 * Gets the date time string.
	 *
	 * @return formatted string representation of current time with  "yyyy-MM-dd-HH:mm:ss".
	 */
	public static String _getDateTimeString() {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmm", java.util.Locale.KOREA);
		return formatter.format(new java.util.Date());
	}

	/**
	 * Gets the date time full string.
	 *
	 * @return formatted string representation of current time with  "yyyy-MM-dd-HH:mm:ss".
	 */
	public static String _getDateTimeFullString() {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmss", java.util.Locale.KOREA);
		return formatter.format(new java.util.Date());
	}

	/**
	 * Gets the date time string.
	 *
	 * @param hour the hour
	 * @return formatted string representation of current time with  "yyyy-MM-dd-HH:mm:ss".
	 */
	public static String _getDateTimeString(int hour) {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmm", java.util.Locale.KOREA);
		long addDate = 1000*60*60*hour;
		return formatter.format(new Date(System.currentTimeMillis()+addDate));
	}

	/**
	 * Gets the time string.
	 *
	 * @return formatted string representation of current time with  "HH:mm:ss".
	 */
	public static String _getTimeString() {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.KOREA);
		return formatter.format(new java.util.Date());
	}

	/**
	 * return days between two date strings with default defined format.(yyyyMMdd)
	 *
	 * @param s date string you want to check.
	 * @return int 날짜 형식이 맞고, 존재하는 날짜일 때 요일을 리턴
	 *		형식이 잘못 되었거나 존재하지 않는 날짜: java.text.ParseException 발생
	 *		0: 일요일 (java.util.Calendar.SUNDAY 와 비교)
	 *		1: 월요일 (java.util.Calendar.MONDAY 와 비교)
	 *		2: 화요일 (java.util.Calendar.TUESDAY 와 비교)
	 *		3: 수요일 (java.util.Calendar.WENDESDAY 와 비교)
	 *		4: 목요일 (java.util.Calendar.THURSDAY 와 비교)
	 *		5: 금요일 (java.util.Calendar.FRIDAY 와 비교)
	 *		6: 토요일 (java.util.Calendar.SATURDAY 와 비교)
	 * 예) String s = "20000529";
	 *  int dayOfWeek = whichDay(s, format);
	 *  if (dayOfWeek == java.util.Calendar.MONDAY)
	 *		System.out.println(" 월요일: " + dayOfWeek);
	 *  if (dayOfWeek == java.util.Calendar.TUESDAY)
	 *		System.out.println(" 화요일: " + dayOfWeek);
	 * @throws ParseException the parse exception
	 */
	public static int _whichDay(String s) throws java.text.ParseException {
		return _whichDay(s, "yyyyMMdd");
	}

	/**
	 * return days between two date strings with user defined format.
	 *
	 * @param s date string you want to check.
	 * @param format string representation of the date format. For example, "yyyy-MM-dd".
	 * @return int 날짜 형식이 맞고, 존재하는 날짜일 때 요일을 리턴
	 *		형식이 잘못 되었거나 존재하지 않는 날짜: java.text.ParseException 발생
	 *		0: 일요일 (java.util.Calendar.SUNDAY 와 비교)
	 *		1: 월요일 (java.util.Calendar.MONDAY 와 비교)
	 *		2: 화요일 (java.util.Calendar.TUESDAY 와 비교)
	 *		3: 수요일 (java.util.Calendar.WENDESDAY 와 비교)
	 *		4: 목요일 (java.util.Calendar.THURSDAY 와 비교)
	 *		5: 금요일 (java.util.Calendar.FRIDAY 와 비교)
	 *		6: 토요일 (java.util.Calendar.SATURDAY 와 비교)
	 * 예) String s = "2000-05-29";
	 *  int dayOfWeek = whichDay(s, "yyyy-MM-dd");
	 *  if (dayOfWeek == java.util.Calendar.MONDAY)
	 *	  System.out.println(" 월요일: " + dayOfWeek);
	 *  if (dayOfWeek == java.util.Calendar.TUESDAY)
	 *	  System.out.println(" 화요일: " + dayOfWeek);
	 * @throws ParseException the parse exception
	 */
	public static int _whichDay(String s, String format) throws java.text.
		ParseException {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format, java.util.Locale.KOREA);

		java.util.Date date = check(s, format);
		java.util.Calendar calendar = formatter.getCalendar();
		calendar.setTime(date);

		return calendar.get(java.util.Calendar.DAY_OF_WEEK);
	}

	/**
	 * return days between two date strings with default defined format.("yyyyMMdd")
	 *
	 * @param from the from
	 * @param to the to
	 * @return int 날짜 형식이 맞고, 존재하는 날짜일 때 2개 일자 사이의 나이 리턴
	 *		   형식이 잘못 되었거나 존재하지 않는 날짜: java.text.ParseException 발생
	 * @throws ParseException the parse exception
	 */
	public static int _daysBetween(String from, String to) throws java.text.ParseException {
		return _daysBetween(from, to, "yyyyMMdd");
	}

	/**
	 * return days between two date strings with user defined format.
	 *
	 * @param from the from
	 * @param to the to
	 * @param format the format
	 * @return int 날짜 형식이 맞고, 존재하는 날짜일 때 2개 일자 사이의 일자 리턴
	 *		   형식이 잘못 되었거나 존재하지 않는 날짜: java.text.ParseException 발생
	 * @throws ParseException the parse exception
	 */
	public static int _daysBetween(String from, String to, String format) throws java.text.ParseException {
		if (from == null || from.equals("")) return 0;
		if (to == null || to.equals("")) return 0;

		java.util.Date d1 = check(from, format);
		java.util.Date d2 = check(to, format);

		long duration = d2.getTime() - d1.getTime();

		return (int) (duration / (1000 * 60 * 60 * 24));
	}

	/**
	 * Hour between.
	 *
	 * @param from the from
	 * @return the int
	 * @throws ParseException the parse exception
	 */
	public static int _hourBetween(String from) throws java.text. ParseException {
		return _hourBetween(from, "yyyyMMddHHmm");
	}

	/**
	 * Hour between.
	 *
	 * @param from the from
	 * @param format the format
	 * @return the int
	 * @throws ParseException the parse exception
	 */
	public static int _hourBetween(String from, String format) throws java.text.ParseException {
		java.util.Date d1 = check(from, format);
		java.util.Date d2 = check(DateTimeUtil.getNowFullStr(), DateTimeUtil.MALL_DATE_FORMAT_FULL);

		long duration = d2.getTime() - d1.getTime();

		return (int) (duration / (1000 * 60 * 60));
	}

	public static int _hourBetween(String from, String format, String to, String format2) throws java.text.ParseException {
		java.util.Date d1 = check(from, format);
		java.util.Date d2 = check(to, format2);

		long duration = d2.getTime() - d1.getTime();

		return (int) (duration / (1000 * 60 * 60));
	}

	/**
	 * return age between two date strings with default defined format.("yyyyMMdd")
	 *
	 * @param from the from
	 * @param to the to
	 * @return int 날짜 형식이 맞고, 존재하는 날짜일 때 2개 일자 사이의 나이 리턴
	 *		   형식이 잘못 되었거나 존재하지 않는 날짜: java.text.ParseException 발생
	 * @throws ParseException the parse exception
	 */
	public static int _ageBetween(String from, String to) throws java.text.ParseException {
		return _ageBetween(from, to, "yyyyMMdd");
	}

	/**
	 * return age between two date strings with user defined format.
	 *
	 * @param from the from
	 * @param to the to
	 * @param format string representation of the date format. For example, "yyyy-MM-dd".
	 * @return int 날짜 형식이 맞고, 존재하는 날짜일 때 2개 일자 사이의 나이 리턴
	 *		   형식이 잘못 되었거나 존재하지 않는 날짜: java.text.ParseException 발생
	 * @throws ParseException the parse exception
	 */
	public static int _ageBetween(String from, String to, String format) throws java.text.ParseException {
		return _daysBetween(from, to, format) / 365;
	}

	/**
	 * return add day to date strings.
	 *
	 * @param s the s
	 * @param day the day
	 * @return int 날짜 형식이 맞고, 존재하는 날짜일 때 일수 더하기
	 *		   형식이 잘못 되었거나 존재하지 않는 날짜: java.text.ParseException 발생
	 * @throws ParseException the parse exception
	 */
	public static String _addDays(String s, int day) throws java.text.ParseException {
		return _addDays(s, day, "yyyyMMdd");
	}

	/**
	 * return add day to date strings with user defined format.
	 *
	 * @param s the s
	 * @param day the day
	 * @param format string representation of the date format. For example, "yyyy-MM-dd".
	 * @return int 날짜 형식이 맞고, 존재하는 날짜일 때 일수 더하기
	 *		   형식이 잘못 되었거나 존재하지 않는 날짜: java.text.ParseException 발생
	 * @throws ParseException the parse exception
	 */
	public static String _addDays(String s, int day, String format) throws java.text.ParseException {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format, java.util.Locale.KOREA);

		java.util.Date date = check(s, format);
		date.setTime(date.getTime() + ((long) day * 1000 * 60 * 60 * 24));

		return formatter.format(date);
	}

	/**
	 * return add month to date strings.
	 *
	 * @param s the s
	 * @param month the month
	 * @return int 날짜 형식이 맞고, 존재하는 날짜일 때 월수 더하기
	 *		   형식이 잘못 되었거나 존재하지 않는 날짜: java.text.ParseException 발생
	 * @throws Exception the exception
	 */
	public static String _addMonths(String s, int month) throws Exception {
		return _addMonths(s, month, "yyyyMMdd");
	}

	/**
	 * return add month to date strings with user defined format.
	 *
	 * @param s the s
	 * @param addMonth the add month
	 * @param format string representation of the date format. For example, "yyyy-MM-dd".
	 * @return int 날짜 형식이 맞고, 존재하는 날짜일 때 월수 더하기
	 *		   형식이 잘못 되었거나 존재하지 않는 날짜: java.text.ParseException 발생
	 * @throws Exception the exception
	 */
	public static String _addMonths(String s, int addMonth, String format) throws Exception {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format, java.util.Locale.KOREA);

		java.util.Date date = check(s, format);
		java.text.SimpleDateFormat yearFormat = new java.text.SimpleDateFormat("yyyy", java.util.Locale.KOREA);
		java.text.SimpleDateFormat monthFormat = new java.text.SimpleDateFormat("MM", java.util.Locale.KOREA);
		java.text.SimpleDateFormat dayFormat = new java.text.SimpleDateFormat("dd", java.util.Locale.KOREA);

		int year = Integer.parseInt(yearFormat.format(date));
		int month = Integer.parseInt(monthFormat.format(date));
		int day = Integer.parseInt(dayFormat.format(date));

		month += addMonth;

		if (addMonth > 0) {
			while (month > 12) {
				month -= 12;
				year += 1;
			}
		}
		else {
			while (month <= 0) {
				month += 12;
				year -= 1;
			}
		}

		java.text.DecimalFormat fourDf = new java.text.DecimalFormat("0000");
		java.text.DecimalFormat twoDf = new java.text.DecimalFormat("00");

		String tempDate = String.valueOf(fourDf.format(year)) + String.valueOf(twoDf.format(month)) + String.valueOf(twoDf.format(day));

		java.util.Date targetDate = null;

		try {
			targetDate = check(tempDate, "yyyyMMdd");
		}
		catch (java.text.ParseException pe) {
			day = _lastDay(year, month);
			tempDate = String.valueOf(fourDf.format(year)) +
				String.valueOf(twoDf.format(month)) +
				String.valueOf(twoDf.format(day));

			targetDate = check(tempDate, "yyyyMMdd");
		}

		return formatter.format(targetDate);
	}

	public static final String LONG_DATE_STR = "yyyy-MM-dd HH:mm:ss";
	public static final String SHORT_DATE_STR = "yyyy-MM-dd";
	public static final SimpleDateFormat DEFAULT_DATETIME_FORMAT = new SimpleDateFormat(LONG_DATE_STR);

	public static final SimpleDateFormat MIN_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
	public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat(SHORT_DATE_STR);

	public static final int MILLI_SEC_A_DAY = 1000 * 60 * 60 * 24;

	/**
	 * 날짜 String을 받아서 i 값에서 지정한 날짜만큼 더한 날짜 String을 반환하는 메소드.
	 *
	 * @param dateStr
	 * @param i
	 * @return
	 */
	public static String _addDay(String dateStr, int i) {
		String retStr = dateStr;
		if (dateStr != null && dateStr.length() >= SHORT_DATE_STR.length()) {
			dateStr = dateStr.trim();
			Calendar calendar = _toCalendar(dateStr);
			calendar.add(Calendar.DATE, i);
			retStr = getProperFormat(dateStr).format(calendar.getTime());
		}

		return retStr;
	}

	/**
	 * 월을 연산한다.
	 * @param dateStr yyyy.MM or yyyy.MM.dd
	 * @param i
	 * @return dateStr 길이에 따라 같은 형식 반환
	 */
	public static String _addMonth(String dateStr, int i) {
		String retStr = dateStr;
		String deli = SHORT_DATE_STR.substring(4, 5);

		if (dateStr != null && retStr.length() > 6) {
			retStr = retStr.trim();
			if (retStr.length() == 7) {
				retStr += (deli + "01");
			}
			Calendar calendar = _toCalendar(retStr);
			calendar.add(Calendar.MONTH, i);
			retStr = getProperFormat(dateStr).format(calendar.getTime());

			retStr = dateStr.length() == 7 ? retStr.substring(0, 7) : retStr;
		}
		return retStr;
	}

	/**
	 * 날짜 String을 Calendar 객체로 변환해주는 메소드.
	 *
	 * @param dateStr
	 * @return
	 */
	public static Calendar _toCalendar(String dateStr) {
		DateFormat format = getProperFormat(dateStr);

		Date convDate = null;
		try {
			convDate = format.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(convDate);

		return calendar;
	}

	/**
	 * 날짜 String의 길이에 따라 적절한 DateFormat을 반환하는 메소드.
	 *
	 * @param dateStr
	 * @return
	 */
	private static DateFormat getProperFormat(String dateStr) {
		DateFormat format;
		if (dateStr.length() == SHORT_DATE_STR.length()) {
			format = DEFAULT_DATE_FORMAT;
		} else {
			format = DEFAULT_DATETIME_FORMAT;
		}
		return format;
	}

	/**
	 * Adds the years.
	 *
	 * @param s the s
	 * @param year the year
	 * @return the string
	 * @throws ParseException the parse exception
	 */
	public static String _addYears(String s, int year) throws java.text.ParseException {
		return _addYears(s, year, "yyyyMMdd");
	}

	/**
	 * Adds the years.
	 *
	 * @param s the s
	 * @param year the year
	 * @param format the format
	 * @return the string
	 * @throws ParseException the parse exception
	 */
	public static String _addYears(String s, int year, String format) throws java.text.ParseException {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format, java.util.Locale.KOREA);

		java.util.Date date = check(s, format);
		date.setTime(date.getTime() + ((long) year * 1000 * 60 * 60 * 24 * (365 + 1)));

		return formatter.format(date);
	}

	/**
	 * Months between.
	 *
	 * @param from the from
	 * @param to the to
	 * @return the int
	 * @throws ParseException the parse exception
	 */
	public static int _monthsBetween(String from, String to) throws java.text.ParseException {
		return _monthsBetween(from, to, "yyyyMMdd");
	}

	/**
	 * Months between.
	 *
	 * @param from the from
	 * @param to the to
	 * @param format the format
	 * @return the int
	 * @throws ParseException the parse exception
	 */
	public static int _monthsBetween(String from, String to, String format) throws java.text.ParseException {
		java.util.Date fromDate = check(from, format);
		java.util.Date toDate = check(to, format);

		if (fromDate.compareTo(toDate) == 0) {
			return 0;
		}

		java.text.SimpleDateFormat yearFormat = new java.text.SimpleDateFormat("yyyy", java.util.Locale.KOREA);
		java.text.SimpleDateFormat monthFormat = new java.text.SimpleDateFormat("MM", java.util.Locale.KOREA);
		java.text.SimpleDateFormat dayFormat = new java.text.SimpleDateFormat("dd", java.util.Locale.KOREA);

		int fromYear = Integer.parseInt(yearFormat.format(fromDate));
		int toYear = Integer.parseInt(yearFormat.format(toDate));
		int fromMonth = Integer.parseInt(monthFormat.format(fromDate));
		int toMonth = Integer.parseInt(monthFormat.format(toDate));
		int fromDay = Integer.parseInt(dayFormat.format(fromDate));
		int toDay = Integer.parseInt(dayFormat.format(toDate));
		int result = 0;

		result += ((toYear - fromYear) * 12);
		result += (toMonth - fromMonth);

		if (((toDay - fromDay) > 0)) {
			result += toDate.compareTo(fromDate);

		}

		return result;
	}

	/**
	 * Last day of month.
	 *
	 * @param src the src
	 * @return the string
	 * @throws ParseException the parse exception
	 */
	public static String _lastDayOfMonth(String src) throws java.text.ParseException {
		return _lastDayOfMonth(src, "yyyyMMdd");
	}

	/**
	 * first day of month.
	 *
	 * @param src the src
	 * @param format the format
	 * @return the string
	 * @throws ParseException the parse exception
	 */
	public static String _firstDayOfMonth(String src, String format) throws java.text.ParseException {
		String[] dateArray = new String[3];
		if (src.indexOf("-") > -1) {
			dateArray = src.split("-");
		} else {
			dateArray[0] = src.substring(0, 4);
			dateArray[1] = src.substring(4, 6);
		}

		if (format.indexOf("-") > -1) {
			return String.format("%s-%s-01", dateArray[0], dateArray[1]);
		}

		return String.format("%s%s01", dateArray[0], dateArray[1]);
	}

	/**
	 * Last day of month.
	 *
	 * @param src the src
	 * @param format the format
	 * @return the string
	 * @throws ParseException the parse exception
	 */
	public static String _lastDayOfMonth(String src, String format) throws java.text.ParseException {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format, java.util.Locale.KOREA);

		java.util.Date date = check(src, format);
		java.text.SimpleDateFormat yearFormat = new java.text.SimpleDateFormat("yyyy", java.util.Locale.KOREA);
		java.text.SimpleDateFormat monthFormat = new java.text.SimpleDateFormat("MM", java.util.Locale.KOREA);

		int year = Integer.parseInt(yearFormat.format(date));
		int month = Integer.parseInt(monthFormat.format(date));
		int day = _lastDay(year, month);

		java.text.DecimalFormat fourDf = new java.text.DecimalFormat("0000");
		java.text.DecimalFormat twoDf = new java.text.DecimalFormat("00");

		String tempDate = String.valueOf(fourDf.format(year)) +
			String.valueOf(twoDf.format(month)) +
			String.valueOf(twoDf.format(day));

		date = check(tempDate, format);

		return formatter.format(date);
	}

	/**
	 * Last day.
	 *
	 * @param year the year
	 * @param month the month
	 * @return the int
	 * @throws ParseException the parse exception
	 */
	private static int _lastDay(int year, int month) throws java.text.
		ParseException {
		int day = 0;

		switch (month) {
			case 1:

			case 3:

			case 5:

			case 7:

			case 8:

			case 10:

			case 12:
				day = 31;
				break;

			case 2:
				if ((year % 4) == 0) {
					if ((year % 100) == 0 && (year % 400) != 0) {
						day = 28;
					}
					else {
						day = 29;
					}
				}
				else {
					day = 28;
				}
				break;

			default:
				day = 30;
		}

		return day;
	}

	/**
	 * yyyymmdd 형식의 날짜를 yyyy/mm/dd 로 변환한다.
	 * @param dateString yyyymmdd형식 날짜 스트링
	 * @param gubun	  구분스트링 ex) /
	 * @return yyyy/dd/mm 형식의 문자 스트링
	 */
	public static String _changeDateString(String dateString, String gubun) {
		StringBuffer dateSB = new StringBuffer(20);
		if (dateString.length() == 8) {
			dateSB.append(dateString.substring(0, 4));
			dateSB.append(gubun);

			dateSB.append(dateString.substring(4, 6));
			dateSB.append(gubun);

			dateSB.append(dateString.substring(6, 8));
			return dateSB.toString();
		}

		return dateString;
	}

	/**
	 * yyyymmdd의 형식의 스트링을 [dd MMM yyyy] 형식의 문자로 치환한다.
	 * ex) DateTime.getDate2US("20040112")  => 12 Jan 2004
	 *	 DateTime.getDate2US("20031213")  => 13 Dec 2003
	 * @param date yyyymmdd형식의 날짜문자 String
	 * @return  변경된 문자스트링.
	 */
	public static String _getDate2US(String date) {
		int yyyy = Integer.parseInt(date.substring(0, 4));
		int mm = Integer.parseInt(date.substring(4, 6)) - 1;
		int dd = Integer.parseInt(date.substring(6, 8));

		//타임존을 생성한다.
		////String[] ids = TimeZone.getAvailableIDs(+9 * 60 * 60 * 1000);
		////SimpleTimeZone pdt = new SimpleTimeZone(+9 * 60 * 60 * 1000, ids[0]);
		////Calendar calendar = new GregorianCalendar(pdt);
		//
		//Constructs a default GregorianCalendar using the current time in the default time zone with the default locale
		//

		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(yyyy, mm, dd);

		//
		//Constructs a SimpleDateFormat using the given pattern and the default date format symbols for the given locale.
		//
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("dd MMM yyyy", java.util.Locale.US);
		//	System.out.println(formatter.format(calendar.getTime()));

		return formatter.format(calendar.getTime());
	}

	/**
	 * yyyy/mm/dd의 형식의 스트링을 [dd MMM yyyy] 형식의 문자로 치환한다.
	 * yyyy-mm-dd
	 * ex) DateTime.getDate2US("20040112")  => 12 Jan 2004
	 *	 DateTime.getDate2US("20031213")  => 13 Dec 2003
	 * @param date yyyymmdd형식의 날짜문자 String
	 * @return  변경된 문자스트링.
	 */
	public static String _getDate2US2(String date) {
		int yyyy = Integer.parseInt(date.substring(0, 4));
		int mm = Integer.parseInt(date.substring(5, 7)) - 1;
		int dd = Integer.parseInt(date.substring(8, 10));

		//타임존을 생성한다.
		////String[] ids = TimeZone.getAvailableIDs(+9 * 60 * 60 * 1000);
		////SimpleTimeZone pdt = new SimpleTimeZone(+9 * 60 * 60 * 1000, ids[0]);
		////Calendar calendar = new GregorianCalendar(pdt);

		//
		//Constructs a default GregorianCalendar using the current time in the default time zone with the default locale
		//
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(yyyy, mm, dd);
		//
		//Constructs a SimpleDateFormat using the given pattern and the default date format symbols for the given locale.
		//
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("dd MMM yyyy", java.util.Locale.US);
		//	System.out.println(formatter.format(calendar.getTime()));
		return formatter.format(calendar.getTime());
	}

	/**
	 * yyyy/mm/dd의 형식의 스트링을 [dd MMM yyyy] 형식의 문자로 치환한다.
	 * yyyy-mm-dd
	 * ex) DateTime.getDate2US("20040112")  => 12 Jan 2004
	 *	 DateTime.getDate2US("20031213")  => 13 Dec 2003
	 * @param date yyyymmdd형식의 날짜문자 String
	 * @return  변경된 문자스트링.
	 */
	public static String _getDate2USWeek(String date) {
		int yyyy = Integer.parseInt(date.substring(0, 4));
		int mm = Integer.parseInt(date.substring(4, 6)) - 1;
		int dd = Integer.parseInt(date.substring(6, 8));

		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(yyyy, mm, dd);

		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("MMMM", java.util.Locale.US);
		return formatter.format(calendar.getTime());
	}

	/**
	 * 일/월/년 시:분:초 형식의 date string으로 반환한다.
	 *
	 * @author 성일경 (추가)
	 * @return formatted string representation of current time with  "MM/dd/yyyy HH:mm:ss".
	 * @since 2004/02/16
	 */
	public static String _getRevDateTimeString() {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss", java.util.Locale.KOREA);
		return formatter.format(new java.util.Date());
	}

	/**
	 *  yyyyMMddHHmmss형식의 날짜를 초 (timestamp)로 변경한다.
	 *
	 * @author 김기현
	 * @param date the date
	 * @return 초 time.
	 * @since 2004/10/18
	 */
	public static long _getDateToSecond(String date) {
		int yyyy = Integer.parseInt(date.substring(0, 4));
		int mm = Integer.parseInt(date.substring(4, 6)) - 1;
		int dd = Integer.parseInt(date.substring(6, 8));
		int hh = Integer.parseInt(date.substring(8, 10));
		int mi = Integer.parseInt(date.substring(10, 12));
		int ss = Integer.parseInt(date.substring(12, 14));

		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(yyyy, mm, dd, hh, mi, ss);

		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss ", java.util.Locale.US);
		System.out.println((calendar.getTimeInMillis() / 1000));
		System.out.println(formatter.format(calendar.getTime()));

		return calendar.getTime().getTime() / 1000;
	}

	/**
	 * 지정한 일자로부터 현재까지의 UNIX timeStamp 반환.
	 *
	 * @return 				일자정보
	 */
	public static int _getTimeStamp() {
		Locale lc = new Locale("Locale.KOREAN", "Locale.KOREA");
		TimeZone mySTZ = TimeZone.getTimeZone("JST");
		Calendar calendar = Calendar.getInstance(mySTZ, lc);
		calendar.set(Integer.parseInt(new Integer(_getYear()).toString()),
					 Integer.parseInt(new Integer(_getMonth()).toString()) - 1,
					 Integer.parseInt(new Integer(_getDay()).toString()),
					 Integer.parseInt(new Integer(_getHour()).toString()),
					 Integer.parseInt(new Integer(_getMinute()).toString()));
		java.util.Date currDate = calendar.getTime();

		return (int) Math.round(((double) currDate.getTime() / 1000));
	}

	/**
	 * select box의 년도 구하기.
	 *
	 * @param nCnt the n cnt
	 * @param Year the year
	 * @return String
	 * ex) <select name="Year">
	 *	   <option value="%" <% if (Year.equals("")) out.print("selected"); %> >----</option>
	 *	   <%= OmDateTime.SelectYear(3, Year)%>
	 *	 </select>
	 */
	public static String _SelectYear(int nCnt, String Year) {
		String SelectYear = new String();
		String YYYY = new String();

		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy", java.util.Locale.KOREA);
		java.util.Date currentTime = new java.util.Date();
		YYYY = formatter.format(currentTime);

		int iYY = Integer.parseInt(YYYY);
		for (int i = (iYY + 1); i >= (iYY - nCnt); i--) {
			if (iYY == i && Year.equals("")) {
				SelectYear = SelectYear + "<option value=" + i +
					" selected>&nbsp;" + i + "</option>";
			}
			else if (Year.equals(String.valueOf(i))) {
				SelectYear = SelectYear + "<option value=" + i +
					" selected>&nbsp;" + i + "</option>";
			}
			else {
				SelectYear = SelectYear + "<option value=" + i + ">&nbsp;" + i +
					"</option>";
			}
		}

		return SelectYear;
	}

	 // 년-월-일의 값을 default로 보여줄때, 현재일을 기준으로 특정일 전/후의 날짜를 가져올때 지정한다.
	 // 예) 2004년6월11일 기준으로   getDate(-20)은  2004-05-22 값을 돌려준다.
	 /**
	 * Gets the date.
	 *
	 * @param days the days
	 * @return the date
	 */
	public static String _getDate(int days) {
		if (days == 0) {
			return "";
		}

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		sdf = new SimpleDateFormat("yyyyMMdd");
		cal.add(Calendar.DATE, days);

		String date = sdf.format(cal.getTime());

		return date;
	}

	/**
	 * Gets the date.
	 *
	 * @param year the year
	 * @param month the month
	 * @param day the day
	 * @param days the days
	 * @return the date
	 */
	public static String _getDate(int year, int month, int day, int days) {
		if (days == 0) {
			return "";
		}

		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		sdf = new SimpleDateFormat("yyyyMMdd");
		cal.add(Calendar.DATE, days);

		String date = sdf.format(cal.getTime());

		return date;
	}


	/**
	 * 현재일 기준 x개월 이전/이후의 일자 (yyyymmdd) 구하기
	 * ex) 현재일 : 20040502 경우  ==>   getYmd(-1) -> 20040403
	 * ex) 현재일 : 20040502 경우  ==>   getYmd(0)  -> 20040502
	 * ex) 현재일 : 20040502 경우  ==>   getYmd(1)  -> 20040601.
	 *
	 * @param month the month
	 * @return String (yyyymmdd)
	 */
	public static String _getYmd(int month) {
		int intDate = 0;

		if (month > 0) {
			intDate = -1;
		}
		else if (month < 0) {
			intDate = 1;

		}
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		cal.add(Calendar.MONTH, month);
		cal.add(Calendar.DATE, intDate);

		String yearmonth = sdf.format(cal.getTime());
		return yearmonth;
	}

	/**
	 * 어제일 기준 x개월 이전/이후의 일자 (yyyymmdd) 구하기
	 * ex) 현재일 : 20040502 경우  ==>   getYmd(-1) -> 20040403
	 * ex) 현재일 : 20040502 경우  ==>   getYmd(0)  -> 20040502
	 * ex) 현재일 : 20040502 경우  ==>   getYmd(1)  -> 20040601.
	 *
	 * @param month the month
	 * @return String (yyyymmdd)
	 */
	public static String _getYmd2(int month) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		cal.add(Calendar.MONTH, month);

		String yearmonth = sdf.format(cal.getTime());
		return yearmonth;
	}

	/**
	 * 입력된 년월일에서 입려된 월만큼 계산.
	 *
	 * @param year the year
	 * @param month the month
	 * @param day the day
	 * @param mon the mon
	 * @return the month
	 */
	public static String _getMonth(int year, int month, int day, int mon) {
		if (mon == 0) {
			return "";
		}

		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		//월 계산
		cal.add(Calendar.MONTH, mon);
		String date = sdf.format(cal.getTime());

		return date;
	}

	/**
	 * 어제일 기준 X일전 이전/이후의 일자 (yyyymmdd) 구하기.
	 *
	 * @param day the day
	 * @return String (yyyymmdd)
	 */
	public static String _getYmd3(int day) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		cal.add(Calendar.DATE, day);

		String yearmonth = sdf.format(cal.getTime());
		return yearmonth;
	}

	/**
	 * select box의 년도 구하기 Asc.
	 *
	 * @param nCnt the n cnt
	 * @param Year the year
	 * @return String
	 * ex) <select name="Year">
	 *	   <option value="%" <% if (Year.equals("")) out.print("selected"); %> >----</option>
	 *	   <%= OmDateTime.SelectYear(3, Year)%>
	 *	 </select>
	 */
	public static String _SelectYearAdd(int nCnt, String Year) {
		String SelectYear = new String();
		String YYYY = new String();

		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy", java.util.Locale.KOREA);
		java.util.Date currentTime = new java.util.Date();
		YYYY = formatter.format(currentTime);

		int iYY = Integer.parseInt(YYYY);
		for (int i = (iYY - 1); i >= (iYY + nCnt); i++) {
			if (iYY == i && Year.equals("")) {
				SelectYear = SelectYear + "<option value=" + i +
					" selected>&nbsp;" + i + "</option>";
			}
			else if (Year.equals(String.valueOf(i))) {
				SelectYear = SelectYear + "<option value=" + i +
					" selected>&nbsp;" + i + "</option>";
			}
			else {
				SelectYear = SelectYear + "<option value=" + i + ">&nbsp;" + i +
					"</option>";
			}
		}

		return SelectYear;
	}

	/**
	 * double를 date형태로.
	 *
	 * @param d double
	 * @return Date
	 */
	public static Date _doubleToDate(double d) {
		if (d <= -657434D || d >= 2958466D) {
			throw new RuntimeException("InvalidBoundArgument");
		}
		long l = (long) (d * 8640000D + 0.5D);
		long l1 = l * 10L;
		if (l1 < 0L) {
			l1 -= (l1 % 0x5265c00L) * 2L;
		}

		return new Date((0x33a950c55800L + l1) - 0x35abad103400L);
	}

	/**
	 * date를 double로.
	 *
	 * @param date Date
	 * @return double
	 */
	public static double _dateToDouble(Date date) {
		System.out.println("datetime===>"+date);
		long l = (date.getTime() - 0x33a950c55800L) + 0x35abad103400L;
		if (l < 0L) {
			long l1 = l % 0x5265c00L;
			if (l1 != 0L) {
				l -= (0x5265c00L + l1) * 2L;
			}
		}

		return l / 86400000D;
	}

	/**
	 * Date format.
	 *
	 * @param sdate the sdate
	 * @param format the format
	 * @return the string
	 */
	public static String _dateFormat(Date sdate, String format) {
		String datestring = "";
		if (sdate!=null) {
			try {
				Date date = sdate;
				DateFormat dateFormat2 = new SimpleDateFormat(format);
				datestring = dateFormat2.format(date);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

		return datestring;
	}

	/**
	 * Date format.
	 *
	 * @param sdate :
	 * @param format the format
	 * @return the string
	 * @Method Name : dateFormat
	 * @Method 설명 :
	 */
	public static String _dateFormat(String sdate, String format) {
		if (sdate == null || "".equals(sdate) || sdate.indexOf("-") == 0) {
			return "";
		}

		String arrSdate[] = null;
		if (sdate.indexOf("-") > -1) {
			arrSdate = sdate.split("-");
		}
		else if (sdate.indexOf(".") > -1) {
			arrSdate = sdate.split(".");
		}
		else if (sdate.indexOf("/") > -1) {
			arrSdate = sdate.split("/");
		}
		else {
			arrSdate = new String[3];
			arrSdate[0] = sdate.substring(0, 4);
			arrSdate[1] = sdate.substring(4, 6);
			arrSdate[2] = sdate.substring(6, 8);
		}

		if (arrSdate.length == 1) {
			format = format.replace("yyyy", arrSdate[0]);
		}
		else if (arrSdate.length == 2) {
			format = format.replace("yyyy", arrSdate[0]);
			format = format.replace("mm", arrSdate[1]);
		}
		else {
			format = format.replace("yyyy", arrSdate[0]);
			format = format.replace("mm", arrSdate[1]);
			format = format.replace("dd", arrSdate[2]);
		}

		return format;
	}

	/**
	 * Date formatt.
	 *
	 * @param sdate the sdate
	 * @return the string
	 */
	public static String _dateFormatt(Date sdate) {
		String datestring = "";

		Date date = sdate;
		DateFormat dateFormat2 = new SimpleDateFormat("yyyyMMdd HH:mm");
		try {
			datestring = dateFormat2.format(date);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return datestring;
	}

		/*
	public static String setApidate(String date) {
		String sdate = "";
		Date d = new Date(date);


		return sdate;
	}
		*/

	/**
	 * Gets the this year diff second.
	 *
	 * @return the this year diff second
	 */
	public static long _getThisYearDiffSecond() {
		GregorianCalendar cal = new GregorianCalendar();

		//start date
		cal.set(_getYear(), 0, 1, 0, 0, 0);
		long startDate = cal.getTimeInMillis();

		cal.set(_getYear(), _getMonth()-1, _getDay(), _getHour(), _getMinute(), _getSecond());
		long endDate = cal.getTimeInMillis();

		long diffMillis = endDate-startDate;

		//차이 날짜
		long diff = diffMillis / (1000);

		return diff;
	}

	/**
	 * from, to 날짜사이의 차이를 구함.
	 *
	 * @param sdate the sdate
	 * @param edate the edate
	 * @return long
	 */
	public static long _betweenDate(String sdate, String edate) {
		sdate = sdate.replaceAll("-", "");
		sdate = sdate.replaceAll("/", "");
		edate = edate.replaceAll("-", "");
		edate = edate.replaceAll("/", "");

		GregorianCalendar cal = new GregorianCalendar();
		long diff = 0l;
		long diffMillis = 0l;
		int syear=0, smonth=0, sday=0;
		int eyear=0, emonth=0, eday=0;

		//날짜 자리수 체크
		if (sdate == null || sdate.length() < 5) {
			return 0;
		}
		if (edate == null || edate.length() < 5) {
			return 0;
		}

		if (sdate.length()>5) {
			syear = Integer.parseInt(sdate.substring(0, 4));
			smonth = Integer.parseInt(sdate.substring(4, 6));
			sday = Integer.parseInt(sdate.substring(6, 8));
		}

		if (edate.length()>5) {
			eyear = Integer.parseInt(edate.substring(0, 4));
			emonth = Integer.parseInt(edate.substring(4, 6));
			eday = Integer.parseInt(edate.substring(6, 8));
		}

		//start date
		cal.set(syear, smonth-1, sday, 0, 0);
		long startDate = cal.getTimeInMillis();

		//end date
		cal.set(eyear, emonth-1, eday, 0, 0);
		long endDate = cal.getTimeInMillis();

		diffMillis = endDate-startDate;

		//차이 날짜
		diff = diffMillis / (24*60*60*1000);
		return diff;
	}

	/**
	 * from, to 날짜사이의 차이를 구함.
	 *
	 * @param sdate the sdate
	 * @param edate the edate
	 * @param conf the conf
	 * @return long
	 */
	public static boolean _betweenDate(String sdate, String edate, int conf) {
		GregorianCalendar cal = new GregorianCalendar();
		long diff = 0l;
		long diffMillis = 0l;
		int syear=0, smonth=0, sday=0;
		int eyear=0, emonth=0, eday=0;
		boolean blnData = true;

		//날짜 자리수 체크
		if (sdate == null || sdate.length() < 5) {
			return false;
		}
		if (edate == null || edate.length() < 5) {
			return false;
		}

		if (sdate.length()>5) {
			syear = Integer.parseInt(sdate.substring(0, 4));
			smonth = Integer.parseInt(sdate.substring(4, 6));
			sday = Integer.parseInt(sdate.substring(6, 8));
		}

		if (edate.length()>5) {
			eyear = Integer.parseInt(edate.substring(0, 4));
			emonth = Integer.parseInt(edate.substring(4, 6));
			eday = Integer.parseInt(edate.substring(6, 8));
		}

		//start date
		cal.set(syear, smonth-1, sday, 0, 0);
		long startDate = cal.getTimeInMillis();

		//end date
		cal.set(eyear, emonth-1, eday, 0, 0);
		long endDate = cal.getTimeInMillis();

		diffMillis = endDate-startDate;

		//날짜 차이
		diff = diffMillis / (24*60*60*1000);

		if (diff <= conf) {
			blnData = true;
		} else {
			blnData = false;
		}

		return blnData;
	}

	/**
	 * Convert java date to sql date.
	 *
	 * @param date the date
	 * @return the java.sql. date
	 */
	public static java.sql.Date _convertJavaDateToSqlDate(java.util.Date date) {
		return new java.sql.Date(date.getTime());
	}

	/**
	 * String to date.
	 *
	 * @param strDate the str date
	 * @return the date
	 */
	public static Date _stringToDate(String strDate) {
		DateFormat formatter=null;
		Date date =null;
		String fromat="yyyyMMddhhmmss";
		try {
			if (strDate!=null && !"".equals(strDate)) {
				strDate=strDate.replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "");
				formatter = new SimpleDateFormat(fromat.substring(0, strDate.length()));
				date = formatter.parse(strDate);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}

	/**
	* @Method Name : WeekDayName
	* @Method 설명 : 지정된 요일을 나타내는 문자열을 반환합니다.
	*
	* @param weekKey
	* @return
	*/
	public static String _WeekDayName (int weekKey) {
		String weekStr = "";

		switch (weekKey) {
		case 1 :
			weekStr = "일";
			break;

		case 2 :
			weekStr = "월";
			break;

		case 3 :
			weekStr = "화";
			break;

		case 4 :
			weekStr = "수";
			break;

		case 5 :
			weekStr = "목";
			break;

		case 6 :
			weekStr = "금";
			break;

		case 7 :
			weekStr = "토";
			break;

		default :
			weekStr = "요일정보가 틀립니다.";
			break;
		};

		return weekStr;
	}

	/**
	 * Date 객체에서 시간 가져오기
	 *
	 * @param date
	 * @return 2021-10-25 (년-월-일)
	 */
	public static String _getDateYearToString(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		String year = Integer.toString(calendar.get(Calendar.YEAR));
		String month = Integer.toString(calendar.get(Calendar.MONTH) + 1);
		String day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
		return year + "-" + month + "-" + day;
	}

	/**
	 * Date 객체에서 시간 가져오기
	 *
	 * @param date
	 * @return 23 (24시간)
	 */
	public static String _getDateHourToString(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return Integer.toString(calendar.get(Calendar.HOUR_OF_DAY));
	}

	/**
	 * Date 객체에서 시간 가져오기
	 *
	 * @param date
	 * @return 10
	 */
	public static String _getDateMinuteToString(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return Integer.toString(calendar.get(Calendar.MINUTE));
	}
}
package com.dplot.filter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.dplot.util.Util;

public class XSSRequestWrapper extends HttpServletRequestWrapper {

	private static Pattern[] patterns = new Pattern[] {
			Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE),
			Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
							| Pattern.DOTALL),
			Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
							| Pattern.DOTALL),
			Pattern.compile("</script>", Pattern.CASE_INSENSITIVE),
			Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE
					| Pattern.MULTILINE | Pattern.DOTALL),
			Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE
					| Pattern.MULTILINE | Pattern.DOTALL),
			Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE
					| Pattern.MULTILINE | Pattern.DOTALL),
			Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),
			Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),
			Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE
					| Pattern.MULTILINE | Pattern.DOTALL),
			Pattern.compile("onclick(.*?)=", Pattern.CASE_INSENSITIVE
					| Pattern.MULTILINE | Pattern.DOTALL),
			Pattern.compile("onmouseover(.*?)=", Pattern.CASE_INSENSITIVE
					| Pattern.MULTILINE | Pattern.DOTALL),
			Pattern.compile("onmouseout(.*?)=", Pattern.CASE_INSENSITIVE
					| Pattern.MULTILINE | Pattern.DOTALL),
			Pattern.compile("onmousedown(.*?)=", Pattern.CASE_INSENSITIVE
					| Pattern.MULTILINE | Pattern.DOTALL)

	};

	public XSSRequestWrapper(HttpServletRequest servletRequest) {

		super(servletRequest);

	}

	@Override
	public String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues(parameter);

		if (values == null) {

			return null;

		}

		int count = values.length;

		String[] encodedValues = new String[count];

		for (int i = 0; i < count; i++) {

			encodedValues[i] = Util.rejectXss(values[i]);

		}

		return encodedValues;

	}

	@Override
	public String getParameter(String parameter) {
		String value = super.getParameter(parameter);

		return Util.rejectXss(value);

	}

	@Override
	public String getHeader(String name) {
		String value = super.getHeader(name);

		return Util.rejectXss(value);

	}

	private String stripXSS(String value) {
		if (value != null) {

			// NOTE: It's highly recommended to use the ESAPI library and
			// uncomment the following line to
			// avoid encoded attacks.
			// value = ESAPI.encoder().canonicalize(value);
			// Avoid null characters

			value = value.replaceAll("\0", "");

			// Remove all sections that match a pattern
			for (Pattern scriptPattern : patterns) {
				if (scriptPattern.matcher(value).matches()) {
					value=value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
				}
			}
		}
		return value;

	}

	@Override
	public Map<String,String[]> getParameterMap() {

		@SuppressWarnings("unchecked")
		Map<String,String[]> parameterMap = super.getParameterMap();
		Set<String> keySet = parameterMap.keySet();
		Iterator<String>  itrator = keySet.iterator();


		Map<String,String[]> cleanMap = new HashMap<String, String[]>();

		while(itrator.hasNext()) {
			String key = itrator.next();
			String[] paramValues = parameterMap.get(key);

			if (paramValues == null) {
				cleanMap.put(key, paramValues);
			} else {
				int count = paramValues.length;
				String[] encodedValues = new String[count];

				for (int i = 0; i < count; i++) {
					encodedValues[i] = Util.rejectXss(paramValues[i]);
				}
				cleanMap.put(key, encodedValues);
			}
		}

		return cleanMap;
	}

}
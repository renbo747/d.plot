package com.dplot.util;

import java.lang.reflect.Field;
import java.util.Map;

public class BeanUtil {
	
	/**
	 * Map -> Bean 전환
	 * @param beanPropMap
	 * @param type
	 * @return
	 */
	public static <T> T convertMapToBean(Map<String, Object> beanPropMap, Class<T> type) {
		try {
			T beanInstance = type.getConstructor().newInstance();
			for (Object k : beanPropMap.keySet()) {
				String key = (String) k;
				Object value = beanPropMap.get(k);
				if (value != null) {
					try {
						Field field = type.getDeclaredField(key);
						field.setAccessible(true);
						field.set(beanInstance, value);
						field.setAccessible(false);
					} catch (Exception e) {
						e.printStackTrace();

					}
				}
			}
			return beanInstance;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

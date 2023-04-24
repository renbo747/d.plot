/**
 * 
 */
package com.dplot.common.service;

import java.util.Map;
/**
 * The Interface CommonDbConfigService.
 */
public interface MallDbConfigService {
	
	/**
	 * Checks if is inited.
	 *
	 * @return true, if is inited
	 * @throws Exception the exception
	 */
	boolean isInited() throws Exception;
	
	/**
	 * Sets the site.
	 *
	 * @param site the site
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	boolean init(String site) throws Exception;
	
	/**
	 * Gets the config.
	 *
	 * @return the config
	 * @throws Exception the exception
	 */
	Map<String, Object> getMap() throws Exception;
	
	/**
	 * Gets the string.
	 *
	 * @param key the key
	 * @return the string
	 * @throws Exception the exception
	 */
	String getStr(String key) throws Exception;
	
	/**
	 * Sets the string.
	 *
	 * @param key the key
	 * @param value the value
	 * @throws Exception the exception
	 */
	void setValue(String key, Object value) throws Exception;

	Object getObject(String key) throws Exception;
}


/**
 * 
 */
package com.dplot.common.service;

import java.util.Map;

/**
 * The Interface MallConfigService.
 *
 */
public interface MallConfigService {
	void setValue(String key, Object value) throws Exception;
	void setStr(String key, String value) throws Exception;
	void setString(String key, String value) throws Exception;
	void setInt(String key, int value) throws Exception;

	int getInt(String key) throws Exception;
	String getStr(String key) throws Exception;
	String getString(String key) throws Exception;
	
	boolean init(String domain) throws Exception;
	Map<String, Object> getMap() throws Exception;
	
	void setObject(String key, Object value) throws Exception;
	Object getObject(String key) throws Exception;
	
	public String makeLogPrefix() throws Exception;
	
	public String makeLogPrefix(String prefix) throws Exception;
	
	public String getSessPrefix() throws Exception;
}

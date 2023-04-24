package com.dplot.common.service;

import com.dplot.common.SOMap;

public interface ConfigService {
	
	public SOMap getConfig(String site);
	
	public int updateConfig(SOMap config);

}

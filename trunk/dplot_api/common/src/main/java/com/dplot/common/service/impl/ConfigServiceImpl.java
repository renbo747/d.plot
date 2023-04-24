package com.dplot.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dplot.common.SOMap;
import com.dplot.common.service.ConfigService;
import com.dplot.mapper.ConfigMapper;


@Service
public class ConfigServiceImpl implements ConfigService{
	@Autowired
	private ConfigMapper configMapper;
	
	@Override
	public SOMap getConfig(String site) {
		return configMapper.selectConfig(site);
	}

	@Override
	public int updateConfig(SOMap config) {
		return this.configMapper.updateConfig(config);
	}
	
}

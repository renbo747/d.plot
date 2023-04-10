package com.dplot.common.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.dplot.common.ApplicationConfig;
import com.dplot.common.service.MallDbConfigService;
import com.dplot.util.ServletRequestInfoUtil;

@Service
@Scope("request")
public class MallDbConfigServiceImpl implements MallDbConfigService {

	private static final Logger logger = LoggerFactory.getLogger(MallDbConfigServiceImpl.class);

	//@Autowired
	//private ConfigService configService;
	
	@Autowired
	private ApplicationConfig applicationConfig;
	
	private Map<String, Object> config = null;
	
	private boolean inited = false;
	
	@Override
	public boolean isInited() throws Exception {
		return inited;
	}

	/**
	 * Sets the inited.
	 *
	 * @param inited the new inited
	 */
	public void setInited(boolean inited) {
		this.inited = inited;
	}

	/**
	 * Instantiates a new common db config service impl.
	 */
	public MallDbConfigServiceImpl() {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("MallDbConfigServiceImpl[%s]", this));
		}
	}

	public MallDbConfigServiceImpl(String domain) {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("MallDbConfigServiceImpl domain[%s] [%s]", domain, this));
		}
	}
	/**
	 * Inits the.
	 *
	 * @return true, if successful
	 */
	@Override
	public boolean init(String site) throws Exception {
		
		//this.config = this.configService.getConfig(site);
		this.config = applicationConfig.getConfig(site);

		if (logger.isDebugEnabled()) {
			logger.debug(String.format("init called [%s] config[%s]", site, this.config));
		}
		if (this.config == null) return false;

		_makeConfig();
		return true;
	}

	@Override
	public Map<String, Object> getMap() throws Exception {
		return this.config;
	}

	@Override
	public void setValue(String key, Object value) throws Exception {
		config.put(key, value);
	}

	@Override
	public String getStr(String key) throws Exception {
		return (config.get(key) != null) ? config.get(key).toString() : "";
	}
	
	@Override
	public Object getObject(String key) throws Exception {
		return config.get(key);
	}

	private void _makeConfig() {
		// 로그인 후 아이디 저장 쿠키 세팅이 안되서 추가함 2021.11.15 
		//기존에는 SOMAP에 세팅했지만, config에다가 세팅함
		String sitehost = ServletRequestInfoUtil.getRequestDomain();
		String sitedomain = sitehost.replaceAll("www", "");
		config.put("cookiedomain", sitedomain);
	}
}
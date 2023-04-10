package com.dplot.common.service.impl;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.dplot.common.service.MallConfigService;
import com.dplot.common.service.MallDbConfigService;
import com.dplot.util.Converter;
import com.dplot.util.Util;

@Configuration
public class MallConfigServiceImpl implements MallConfigService {

	private static final Logger logger = LoggerFactory.getLogger(MallConfigServiceImpl.class);

	@Autowired
	Provider<MallDbConfigService> dbProvider;


	public MallConfigServiceImpl() throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("MallConfigServiceImpl[%s]", this));
		}
	}

	@PostConstruct
	void _init() {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("init postConstruct[%s]", this));
		}
	}

	@Override
	public boolean init(String domain) throws Exception {
		MallDbConfigService db = dbProvider.get();
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("init db[%s]", db));
		}
		return db.init(domain);
	}
	
	@Override
	public Object getObject(String key) throws Exception {
		Object obj = null;
		
		try {
			MallDbConfigService db = dbProvider.get();
			obj = db.getObject(key);
		} catch (Exception e) {

		}

		return obj;
	}

	@Override
	public int getInt(String key) throws Exception {
		int value = 0;
		try {
			MallDbConfigService db = dbProvider.get();
			value = Converter.toInt(db.getStr(key));
		} catch (Exception e) {

		}

		return value;
	}
	
	@Override
	public String getStr(String key) throws Exception {
		String value = "";
		try {
			MallDbConfigService db = dbProvider.get();
			value = db.getStr(key);
		} catch (Exception e) {

		}
		return value == null ? "" : value;
	}
	
	@Override
	public String getString(String key) throws Exception {
		return getStr(key);
	}
	
	@Override
	public void setValue(String key, Object value) throws Exception {
		MallDbConfigService db = dbProvider.get();
		db.setValue(key, value);
	}

	@Override
	public void setStr(String key, String value) throws Exception {
		setValue(key, value);
	}
	@Override
	public void setObject(String key, Object value) throws Exception {
		setValue(key, value);
	}
	@Override
	public void setString(String key, String value) throws Exception {
		setValue(key, value);
	}
	@Override
	public void setInt(String key, int value) throws Exception {
		setValue(key, value);
	}

	@Override
	public Map<String, Object> getMap() throws Exception {

		MallDbConfigService db = dbProvider.get();

		return  db.getMap();
	}
	
	public String makeLogPrefix() throws Exception {
		return makeLogPrefix(null);
	}

	public String makeLogPrefix(String prefix) throws Exception {
		if (Util.isEmpty(prefix)) prefix = "";

		return String.format("(%s%s%s_m%d_a%d)", prefix, (Util.hasText(prefix) ? "_" : ""), getSessPrefix(), this.getInt("authmemberno"), this.getInt("authadminno"));
	}
	
	public String getSessPrefix() throws Exception {
		String sess = this.getStr("csessId");

		if (sess.length() > 4) {
			return sess.substring(sess.length() - 4);
		}


		return sess;
	}
}
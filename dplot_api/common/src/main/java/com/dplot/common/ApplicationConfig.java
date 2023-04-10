package com.dplot.common;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.dplot.mapper.ConfigMapper;

@Component
public class ApplicationConfig  implements ApplicationListener<ContextRefreshedEvent> {
	private static final Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);
 
    //@Autowired
    //private Environment environment;
 
    @Autowired
	private ConfigMapper configMapper;
    
    /*
     * T_CONFIG 설정으로 T_CONFIG_DOMAIN의 DOMAIN별로 설정저장
     */
    private Map<String, SOMap> config;
    
    @Override 
    public void onApplicationEvent(ContextRefreshedEvent event) {
    	logger.debug("Domain별로 Config저장처리");
    	this.config = new LinkedHashMap<String, SOMap>();
    	List<SOMap> result = configMapper.selectConfig();
    	for (SOMap config : result) {
			this.config.put(config.getDbStr("cfgdomain"), config);
		}
    }
    
    /**
     * 도메인으로 설정가져오기
     * @param domain
     * @return
     */
    public SOMap getConfig(String domain) {
    	SOMap result = null;
    	for (Map.Entry<String, SOMap> config : this.config.entrySet()) {
			if(domain.contains(config.getKey())){
				result = new SOMap(config.getValue());
			}
		}
    	return result;
    }
    
    /**
     * 도메인으로 설정저장
     * @param domain
     * @param config
     */
    public void setConfig(String domain, SOMap config) {
    	this.config.put(domain, config);
    }
    
    /**
     * SiteID로 설정 저장
     * @param siteId
     * @param config
     */
    public void setConfigBySiteId(String siteId, SOMap config) {
    	for (String key : this.config.keySet()) {
    		if(this.config.get(key).equals(siteId)) {
    			this.config.put(key, config);
    		}
		}
    }
}
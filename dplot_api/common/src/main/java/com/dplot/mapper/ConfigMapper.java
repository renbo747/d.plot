package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * The Interface ConfigMapper.
 */
@MapperInterface
public interface ConfigMapper {

	/**
	 * Select config List
	 * @return
	 */
	List<SOMap> selectConfig();
	
	/**
	 * Select config by domain
	 *
	 * @param params the params
	 * @return the config
	 */
	SOMap selectConfig(String params);
	
	/**
	 * Select config by siteId
	 * @param params
	 * @return
	 */
	SOMap selectConfigSiteId(String params);
	/**
	 * Select config.
	 *
	 * @param params the params
	 * @return the hash map
	 */
	SOMap selectConfigForDecisionPeriod(String params);
	
	/**
	 * Select config.
	 *
	 * @param params the params
	 * @return the hash map
	 */
	SOMap selectConfigForDeliveryPeriod(String params);

	/**
	 * Update config.
	 *
	 * @param config the config
	 * @return the int
	 */
	int updateConfig(SOMap config);

	/**
	 * Select config join config company.
	 *
	 * @param params the params
	 * @return the hash map
	 */
	SOMap selectConfigJoinConfigCompany(SOMap params);
	
	/**
	 * 
	 * @param p
	 * @return
	 */
	SOMap selectConfigCateDepth(SOMap p);
	
	/**
	 * Select config for reserve config
	 *
	 * @param params the params
	 * @return the hash map
	 */
	SOMap selectConfigForReserveConfig(SOMap params);

	/**
	 * Select config.
	 * @param params
	 * @return
	 */
	SOMap selectConfigCardBenefit(SOMap params);

	/**
	 * Select config.
	 * @param params
	 * @return
	 */
	SOMap selectConfigForSearchLink(SOMap params);

	/**
	 * Select config
	 * @param param
	 * @return
	 */
	SOMap selectConfigForInsta(SOMap param);

	/**
	 * 앱버전 조회
	 * @param param
	 * @return
	 */
	SOMap selectConfigAppVersion(SOMap param);
}

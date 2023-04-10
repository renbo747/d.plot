package com.dplot.mapper;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * The Interface ConfigUserMapper.
 */
@MapperInterface
public interface ConfigUserMapper {

	/**
	 * Select config user block id.
	 *
	 * @param params the params
	 * @return the config user
	 */
	SOMap selectConfigUserBlockId(SOMap params);
	
	/**
	 * Update config user.
	 *
	 * @param configUser the config user
	 * @return the int
	 */
	int updateConfigUser(SOMap configUser);
	
}

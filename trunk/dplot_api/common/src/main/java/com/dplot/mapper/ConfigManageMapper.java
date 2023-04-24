package com.dplot.mapper;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * The Interface ConfigMangeMapper.
 */
@MapperInterface
public interface ConfigManageMapper {

	/**
	 * Select config manage.
	 *
	 * @param params the params
	 * @return the config
	 * ASIS는 category.jsp에서 사용!
	 */
	SOMap selectConfigManage(SOMap params);

}

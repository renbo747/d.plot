package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

@MapperInterface
public interface GoodsHistoryMapper {

	/**
	 * select Goods History
	 *
	 * @param SOMap params
	 * @return the List<SOMap>
	 */
	List<SOMap> selectGoodsHistory(SOMap param);
		
	/**
	 * insert Goods History.
	 *
	 * @param SOMap params
	 * @return the int
	 */
	int insertGoodsHistory(SOMap param);
}

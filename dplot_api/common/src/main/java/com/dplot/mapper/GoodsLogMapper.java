package com.dplot.mapper;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @discription	: 상품 Mapper (T_GOODS_LOG)
 * @fileName	: GoodsLogMapper.java
 * @author		: KTH
 * @date		: 2021.11.23
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.11.23	KTH			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface GoodsLogMapper {

	/**
	 * select Goods Log count.
	 *
	 * @param SOMap params
	 * @return the int
	 */
	int selectGoodsLogCount(SOMap param);
		
	/**
	 * Update Goods Log.
	 *
	 * @param SOMap params
	 * @return the int
	 */
	int updateGoodsLog(SOMap param);
	
	/**
	 * insert Goods Log.
	 *
	 * @param SOMap params
	 * @return the int
	 */
	int insertGoodsLog(SOMap param);
}

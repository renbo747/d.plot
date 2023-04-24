package com.dplot.mapper;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @author : KTH
 * @discription : 상품_Total Mapper (t_goods_total)
 * @fileName : GoodsTotalMapper.java
 * @date : 2021-11-23
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021-11-16	KTH		최초생성
 * -----
 */
@MapperInterface
public interface GoodsTotalMapper {
		
	/**
	 * Update goods total hits.
	 *
	 * @param params the params
	 * @return the int
	 */
	int updateGoodsTotalHits(SOMap goodsTotal);
}
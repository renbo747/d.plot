package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

@MapperInterface
public interface BestGoodsMapper {

	/**
	 * 베스트 상품 목록 조회
	 * @param param
	 * @return
	 */
	List<SOMap> selectBestGoodsList(SOMap param);
}

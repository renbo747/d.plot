package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @discription	: 상품정보고시 Mapper (T_GOODS_NOTIFY)
 * @fileName	: GoodsNotifyMapper.java
 * @author		: JSK
 * @date		: 2021.11.26
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.11.26	JSK			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface GoodsNotifyMapper {
	
	/**
	 * 상품별 상품정보고시 목록 조회
	 * @param params
	 * @return List<SOMap>
	 */
	List<SOMap> selectGoodsNotifyList(SOMap params);
	
	/**
	 * 상품별 상품정보고시 추가
	 * @param params
	 * @return int
	 */
	int insertGoodsNotify(SOMap params);
	
	/**
	 * 상품별 상품정보고시 삭제
	 * @param params
	 * @return int
	 */
	int deleteGoodsNotify(SOMap params);
}

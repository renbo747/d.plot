package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * 
 * @FileName : ShopEx45GoodsMapper.java
 * @Project : datapick_api
 * @Date : 2022. 04. 06. 
 * @Author : LKW
 * ============================================================
 *  DATE					AUTHOR			NOTE
 * ============================================================
 *  2022. 04. 06.			LKW			        최초작성
 * ------------------------------------------------------------
 *
 */
@MapperInterface
public interface ShopEx45GoodsMapper {
	
	/**
	 * 전시영역45상품 저장
	 * @param params
	 * @return
	 */
	int insertShopEx45Goods(SOMap params);

	/**
	 * 전시영역45상품 조회
	 * @param params
	 * @return
	 */
	List<SOMap> selectShopEx45GoodsList(SOMap params);

	/**
	 * 전시영역45상품 삭제
	 * @param params
	 * @return
	 */
	int deleteShopEx45Goods(SOMap params);

	/**
	 * FRONT 전시45 상품 목록 조회
	 * @param param
	 * @return
	 */
	List<SOMap> selectFrontShopEx45GoodsList(SOMap param);
	
}

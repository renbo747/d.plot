package com.dplot.mapper;

import java.util.List;
import java.util.Map;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @discription	: 상품_카테고리 Mapper (T_GOODS_CATEGORY)
 * @fileName	: GoodsCategoryMapper.java
 * @author		: JSK
 * @date		: 2021.11.15
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.11.15	JSK			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface GoodsCategoryMapper {

	/**
	 * 상품별카테고리 목록 조회
	 * @param params
	 * 
	 * @return List<SOMap>
	 */
	List<SOMap> selectGoodsCategoryList(SOMap params);

	/**
	 * 상품별카테고리 추가
	 * @param params
	 * @return int
	 */
	int insertGoodsCategory(SOMap params);

	/**
	 * 상품별카테고리 삭제
	 * @param params
	 * @return int
	 */
	int deleteGoodsCategory(SOMap params);
	
	/**
	 * 상품 카테고리 개수 조회
	 * @param params
	 * @return int
	 */
	int selectGoodsCategoryCnt(SOMap params);

	/**
	 * ERP 전송 상품 카테고리 리스트 조회
	 */
	List<SOMap> selectGoodsCategoryListERP(SOMap params);
}

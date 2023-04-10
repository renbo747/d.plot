package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @discription	: 프로모션 상품 Mapper (T_PROMO_GOODS)
 * @fileName	: PromoGoodsMapper.java
 * @author		: JSK
 * @date		: 2021.12.28
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.12.28	JSK			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface PromoGoodsMapper {
	
	/**
	 * 프로모션 상품 목록 조회
     * @param param
     * @return List<SOMap>
     * @throws Exception
	 */
	List<SOMap> selectPromoGoodsList(SOMap params);
	
	/**
	 * 프로모션 상품 저장
     * @param param
     * @return int
     * @throws Exception
	 */
	int insertPromoGoods(SOMap params);
	
	/**
	 * 프로모션 상품 삭제
     * @param param
     * @return int
     * @throws Exception
	 */
	int deletePromoGoods(SOMap params);

	/**
	 * ERP 전송용
	 */
	List<SOMap> selectPromoGoodsERPData(SOMap param);
}

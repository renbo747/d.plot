package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @discription	: 공통쿠폰상품 Mapper (T_COMCOUPON_GOODS)
 * @fileName	: CouponGoodsMapper.java
 * @author		: JSK
 * @date		: 2022.01.19
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.01.19	JSK			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface CouponGoodsMapper {
	
	/**
	 * 쿠폰 상품 목록조회
     * @param params
     * @return List<SOMap>
	 */
	List<SOMap> selectCouponGoodsList(SOMap params);
	
	/**
	 * 쿠폰 상품 저장
     * @param params
     * @return int
	 */
	int insertCouponGoods(SOMap params);
	
	/**
	 * 쿠폰 상품 삭제
     * @param params
     * @return int
	 */
	int deleteCouponGoods(SOMap params);

	/**
	 * ERP 전송용
	 */
	List<SOMap> selectCouponGoodsERPData(SOMap params);
}

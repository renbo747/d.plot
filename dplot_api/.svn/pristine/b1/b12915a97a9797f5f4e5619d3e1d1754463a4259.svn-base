package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @discription	: 공통쿠폰구매확정상품 Mapper (T_COMCOUPON_ORD_GOODS)
 * @fileName	: CouponOrdGoodsMapper.java
 * @author		: JSK
 * @date		: 2022.01.19
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.01.19	JSK			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface CouponOrdGoodsMapper {
	
	/**
	 * 쿠폰 구매확정 상품 목록조회
     * @param params
     * @return List<SOMap>
	 */
	List<SOMap> selectCouponOrdGoodsList(SOMap params);
	
	/**
	 * 쿠폰 구매확정 상품 저장
     * @param params
     * @return int
	 */
	int insertCouponOrdGoods(SOMap params);
	
	/**
	 * 쿠폰 구매확정 상품 삭제
     * @param params
     * @return int
	 */
	int deleteCouponOrdGoods(SOMap params);

	/**
	 * ERP 전송용
	 */
	List<SOMap> selectCouponOrdGoodERPData(SOMap param);
}

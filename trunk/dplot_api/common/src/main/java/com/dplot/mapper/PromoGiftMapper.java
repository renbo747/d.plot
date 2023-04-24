package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @discription	: 프로모션 사은품 Mapper (T_PROMO_GIFT)
 * @fileName	: PromoGiftMapper.java
 * @author		: JSK
 * @date		: 2021.12.31
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.12.31	JSK			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface PromoGiftMapper {
	
	/**
	 * 프로모션 사은품 목록 조회
     * @param param
     * @return List<SOMap>
     * @throws Exception
	 */
	List<SOMap> selectPromoGiftList(SOMap params);
	
	/**
	 * 프로모션 사은품 저장
     * @param param
     * @return int
     * @throws Exception
	 */
	int insertPromoGift(SOMap params);
	
	/**
	 * 프로모션 사은품 삭제
     * @param param
     * @return int
     * @throws Exception
	 */
	int deletePromoGift(SOMap params);

	/**
	 * ERP 전송용
	 */
	List<SOMap> selectPromoGiftERPData(SOMap param);
}

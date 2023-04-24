package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * 
 * @FileName : ComOrderGoodsGiftMapper.java
 * @Project : datapick_api
 * @Date : 2022. 1. 26. 
 * @Author : YIY
 * ============================================================
 *  DATE					AUTHOR			NOTE
 * ============================================================
 *  2022. 1. 26.			YIY			        최초작성
 * ------------------------------------------------------------
 *
 */
@MapperInterface
public interface ComOrderGoodsGiftMapper {

	/**
	 * 주문 사은품 내역
	 * @param param
	 * @return
	 */
	List<SOMap> selectComOrderGoodsGiftList(SOMap param);
	
	/**
	 * 주문 사은품 저장
	 * @param dbParam
	 * @return
	 */
	int insertComOrderGoodsGift(SOMap dbParam);

	/**
	 * 주문/클레임 사은품 목록 조회
	 * @param param
	 * @return
	 */
	List<SOMap> selectOrderClaimGoodsGiftList(SOMap param);

	/**
	 *	ERP 전송용 사은품 조회
	 */
	List<SOMap> selectOrderGiftERPData(SOMap param);
}

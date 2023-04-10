package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @discription	: 클레임사은품 Mapper (T_CLAIM_GOODS_GIFT)
 * @fileName	: ClaimGoodsGiftMapper.java
 * @author		: JSK
 * @date		: 2022.03.02
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.03.02	JSK			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface ClaimGoodsGiftMapper {
	
	/**
	 * 클레임 사은품 저장
	 * @param param
	 * @return
	 */
	int insertClaimGoodsGift(SOMap param);
	
	/**
	 * 클레임사은품 조회
	 * @param param
	 * @return
	 */
	List<SOMap> selectClaimGoodsGift(SOMap param);
	
	/**
	 * 주문사은품목록 클레임제외
	 * @param param
	 * @return
	 */
	List<SOMap> selectClaimGoodsGiftWithoutClaim(SOMap param);
	
	/**
	 * 클레임 사은품여부 조회
	 * @param param
	 * @return
	 */
	int selectClaimGoodsGiftCheck(SOMap param);
	
	/**
	 * 클레임 사은품여부 수정
	 * @param param
	 * @return
	 */
	int updateClaimGoodsGiftTrash(SOMap param);

	/**
	 *
	 */
	List<SOMap> selectClaimGoodsGiftERPData(SOMap param);
}

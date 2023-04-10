package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * 
 * @FileName : GiftErpMapper.java
 * @Project : datapick_api
 * @Date : 2022. 05. 24. 
 * @Author : LKW
 * ============================================================
 *  DATE					AUTHOR			NOTE
 * ============================================================
 *  2022. 05. 24.			LKW			        최초작성
 * ------------------------------------------------------------
 *
 */
@MapperInterface
public interface GiftErpMapper {
	
	/**
	 * 사은픔ERP 저장
	 * @param params
	 * @return
	 */
	int insertGiftERP(SOMap params);

	/**
	 * 사은품ERP 조회
	 * @param params
	 * @return
	 */
	List<SOMap> selectGiftERP(SOMap params);

	/**
	 * 사은품ERP 삭제
	 * @param params
	 * @return
	 */
	int deleteGiftERP(SOMap params);

	/**
	 * 사은품 ERP 조회 ERP 전송용
	 */
	List<SOMap> selectGiftERPSendData(SOMap params);
}

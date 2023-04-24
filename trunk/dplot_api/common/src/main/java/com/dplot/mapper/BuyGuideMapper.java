package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * 
 * @FileName : BuyGuideMapper.java
 * @Project : datapick_api
 * @Date : 2021. 12. 2. 
 * @Author : YIY
 * ============================================================
 *  DATE					AUTHOR			NOTE
 * ============================================================
 *  2021. 12. 2.			YIY			        최초작성
 * ------------------------------------------------------------
 *
 */
@MapperInterface
public interface BuyGuideMapper {
	
	/**
	 * Front 구매 가이드 목록 노출
	 * @param params
	 * @return
	 */
	List<SOMap> selectFrontBuyGuidelist(SOMap params);
}

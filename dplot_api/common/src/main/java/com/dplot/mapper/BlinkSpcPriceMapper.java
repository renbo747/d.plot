/**
 * 
 */
package com.dplot.mapper;

import java.util.List;
import java.util.Map;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @FileName : BlinkSpcPriceMapper.java
 * @Project : datapick_api
 * @Date : 2021. 12. 1. 
 * @Author : KTH
 * ============================================================
 *  DATE               AUTHOR         NOTE
 * ============================================================
 *  2021. 12. 10.         LCK                 최초작성
 * ------------------------------------------------------------
 **/
@MapperInterface
public interface BlinkSpcPriceMapper {
	/**
	 * 반짝특가 목록조회
	 *
	 * @param params
	 * @return 
	 */
	List<SOMap> selectBlinkSpcPriceList(SOMap params);

	/**
	 * 반짝특가 사용여부별 수량 조회
	 * @param params
	 * @return
	 */
	SOMap selectBlinkSpcPriceUseCnt(SOMap params);
	
	
}

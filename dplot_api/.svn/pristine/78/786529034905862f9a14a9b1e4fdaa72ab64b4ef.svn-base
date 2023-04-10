/**
 * 
 */
package com.dplot.mapper;

import java.util.List;
import java.util.Map;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @FileName : TimeSpcPriceMapper.java
 * @Project : datapick_api
 * @Date : 2021. 12. 1. 
 * @Author : KTH
 * ============================================================
 *  DATE               AUTHOR         NOTE
 * ============================================================
 *  2021. 12. 1.         KTH                 최초작성
 * ------------------------------------------------------------
 **/
@MapperInterface
public interface TimeSpcPriceMapper {
	/**
	 * 타임특가 조회
	 *
	 * @param params the params
	 * @return the params
	 */
	SOMap selectTimeSpcPrice(SOMap params);
	
	
	/**
	 * Front 타임특가 조회(메인 전시)
	 * 작성자:yiy
	 * @param params
	 * @return
	 */
	List<SOMap> selectFrontTimePriceList(SOMap params);

	/**
	 * 타임특가 전체 조회
	 * @param params
	 * @return
	 */
	List<SOMap> selectTimeSpcPriceList(SOMap params);


	/**
	 * 타임특가 사용여부별 수량 조회
	 * @param params
	 * @return
	 */
	SOMap selectTimeSpcPriceUseCnt(SOMap params);


	/**
	 * 타임특가 추가
	 * @param params
	 * @return
	 */
	int insertTimeSpcPrice(SOMap params);


	/**
	 * 타임특가 사용여부 수정
	 * @param params
	 * @return
	 */
	int updateTimeSpcPriceUse(SOMap params);


	/**
	 * 타임특가 엑셀 목록 조회
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> selectTimeSpcPriceExcelList(SOMap params);

	/**
	 * 타임특가 상세 조회
	 * @param params
	 * @return
	 */
	SOMap selectTimeSpcPriceDetail(SOMap params);


	/**
	 * 타임특가 수정
	 * @param params
	 * @return
	 */
	int updateTimeSpcPrice(SOMap params);
}

package com.dplot.mapper;

import java.util.List;
import java.util.Map;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * 
 * @FileName : RecomRewardLogMapper.java
 * @Project : datapick_api
 * @Date : 2021. 12. 23. 
 * @Author : LCK
 * ============================================================
 *  DATE					AUTHOR			NOTE
 * ============================================================
 *  2021. 12. 23.			LCK			        최초작성
 * ------------------------------------------------------------
 *
 */
@MapperInterface
public interface RecomRewardLogMapper {

	/**
	 * 추천리워드로그 엑셀 조회
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> selectRecomRewardLogExcel(SOMap params);

	/**
	 * 추천리워드로그 추가
	 * @param params
	 * @return
	 */
	int insertRecomRewardLog(SOMap params);

	/**
	 * 추천리워드로그 목록 조회
	 * @param params
	 * @return
	 */
	List<SOMap> selectRecomRewardLogList(SOMap params);

	/**
	 * 추천리워드 현재 지급 정보 조회
	 * @param param
	 * @return
	 */
	SOMap selectSignupRewardLog(SOMap param);

	/**
	 * 추천리워드 목록 수량조회
	 * @param params
	 * @return
	 */
	int selectRecomRewardLogCnt(SOMap params);
}

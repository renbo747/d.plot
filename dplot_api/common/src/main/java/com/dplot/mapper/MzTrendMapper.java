package com.dplot.mapper;

import java.util.List;
import java.util.Map;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * 
 * @FileName : MzexhibitMapper.java
 * @Project : datapick_api
 * @Date : 2022. 03. 30. 
 * @Author : LKW
 * ============================================================
 *  DATE					AUTHOR			NOTE
 * ============================================================
 *  2022. 03. 30.			LKW			        최초작성
 * ------------------------------------------------------------
 *
 */
@MapperInterface
public interface MzTrendMapper {

	List<SOMap> selectTrendList(SOMap params);

	SOMap selectTrendShowCnt(SOMap params);

	int updateTrendSortNum(SOMap params);

	int updateTrend(SOMap params);

    int updateAllSortNum(SOMap params);

	SOMap checkCondition(SOMap params);

	List<Map<String, Object>> selectTrendExcelList(SOMap params);

	int insertTrend(SOMap params);

	SOMap selectTrendDetail(SOMap params);

	int selectDispShopCnt(SOMap params);

	int selectDispShopCntList(SOMap params);

	/**
	 * Front메거진 트랜드 목록 조회
	 * @param param
	 * @return
	 */
	List<SOMap> selectFrontMzTrendList(SOMap param);

	SOMap selectFrontTrendDetail(SOMap param);

}

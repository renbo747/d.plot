package com.dplot.mapper;

import java.util.List;
import java.util.Map;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * 
 * @FileName : MzexhibitwordMapper.java
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
public interface MzexhibitwordMapper {
	
	/**
	 * 전시관리키워드 저장
	 * @param params
	 * @return
	 */
	int insertMzexhibitword(SOMap params);

	/**
	 * 전시관리키워드 조회
	 * @param params
	 * @return
	 */
	List<SOMap> selectMzexhibitwordList(SOMap params);

	/**
	 * 전시관리키워드 삭제
	 * @param params
	 * @return
	 */
	int deleteMzexhibitword(SOMap params);
	
}

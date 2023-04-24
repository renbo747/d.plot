package com.dplot.mapper;

import java.util.List;
import java.util.Map;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * 
 * @FileName : MzKeywordListMapper.java
 * @Project : datapick_api
 * @Date : 2022. 03. 31. 
 * @Author : LKW
 * ============================================================
 *  DATE					AUTHOR			NOTE
 * ============================================================
 *  2022. 03. 31.			LKW			        최초작성
 * ------------------------------------------------------------
 *
 */
@MapperInterface
public interface MzKeywordListMapper {
	
	/**
	 * 매거진키워드 저장
	 * @param params
	 * @return
	 */
	int insertMzKeyword(SOMap params);

	/**
	 * 매거진키워드 조회
	 * @param params
	 * @return
	 */
	List<SOMap> selectMzKeywordList(SOMap params);

	/**
	 * 매거진키워드 삭제
	 * @param params
	 * @return
	 */
	int deleteMzKeyword(SOMap params);
	
}

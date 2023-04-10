package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * 
 * @FileName : BannedMapper.java
 * @Project : datapick_api
 * @Date : 2022. 04. 12. 
 * @Author : LKW
 * ============================================================
 *  DATE					AUTHOR			NOTE
 * ============================================================
 *  2022. 04. 12.			LKW			        최초작성
 * ------------------------------------------------------------
 *
 */
@MapperInterface
public interface BannedMapper {

	/**
	 * 금칙어 목록 조회
	 * @param params
	 * @return
	 */
	List<SOMap> selectBannedList(SOMap params);

	/**
	 * 금칙어 추가
	 * @param params
	 * @return
	 */
	int insertBanned(SOMap params);

	/**
	 * 금칙어 삭제
	 * @param params
	 * @return
	 */
	int removeBanned(SOMap params);
	
	/**금칙어 단어
	 * @param
	 * @return 금칙어 목록
	 */
	String badWordList();
	
}

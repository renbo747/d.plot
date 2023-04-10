package com.dplot.mapper;

import java.util.List;
import java.util.Map;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @discription	: 카테고리템플릿 Mapper (T_CATEGORY)
 * @fileName	: CateTemplateMapper.java
 * @author		: JSK
 * @date		: 2021.11.15
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.11.15	JSK			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface CateTemplateMapper {
	
	/**
	 * 카테고리 템플릿 목록 조회
	 * @param params
	 * @return List<SOMap>
	 */
	List<SOMap> selectCateTemplateList(SOMap params);

	/**
	 * 카테고리 템플릿 중복건수 조회
	 * @param params
	 * @return int
	 */
	int selectCateTamplateDupCnt(SOMap params);
	
	/**
	 * 카테고리 템플릿 추가
	 * @param params
	 * @return int
	 */
	int insertCateTemplate(SOMap params);

	/**
	 * 카테고리 템플릿 삭제
	 * @param params
	 * @return int
	 */
	int deleteCateTemplate(SOMap params);
}

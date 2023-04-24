package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * 
 * @FileName : BrandContenstMapper.java
 * @Project : datapick_api
 * @Date : 2022. 05. 12. 
 * @Author : LKW
 * ============================================================
 *  DATE					AUTHOR			NOTE
 * ============================================================
 *  2022. 05. 12.			LKW			        최초작성
 * ------------------------------------------------------------
 *
 */
@MapperInterface
public interface BrandContentsMapper {
	
	/**
	 * 브랜드컨텐츠 저장
	 * @param params
	 * @return
	 */
	int insertBrandContents(SOMap params);

	/**
	 * 브랜드컨텐츠 조회
	 * @param params
	 * @return
	 */
	List<SOMap> selectBrandContentsList(SOMap params);

	/**
	 * 브랜드컨텐츠 삭제
	 * @param params
	 * @return
	 */
	int deleteBrandContents(SOMap params);
	
}

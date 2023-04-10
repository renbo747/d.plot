package com.dplot.mapper;

import java.util.List;
import java.util.Map;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * 
 * @FileName : CateSubsetMapper.java
 * @Project : datapick_api
 * @Date : 2022. 04. 04. 
 * @Author : LKW
 * ============================================================
 *  DATE					AUTHOR			NOTE
 * ============================================================
 *  2022. 04. 04.			LKW			        최초작성
 * ------------------------------------------------------------
 *
 */
@MapperInterface
public interface CateSubsetMapper {

	List<SOMap> selectSubsetList(SOMap params);

	SOMap selectSubsetShowCnt(SOMap params);

	int updateSubset(SOMap params);

	int selectDisplayCheckCnt(SOMap params);

	List<Map<String, Object>> selectSubsetExcelList(SOMap params);

	int insertSubset(SOMap params);

	SOMap selectSubsetDetail(SOMap params);

	List<SOMap> selectFrontSubsetList(SOMap param);
	

}

/**
 * 
 */
package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @FileName : ReqTemplateMapper.java
 * @Project : datapick_api
 * @Date : 2022. 03. 15. 
 * @Author : LKW
 * ============================================================
 *  DATE               AUTHOR         NOTE
 * ============================================================
 *  2022. 03. 15.         LKW                 최초작성
 * ------------------------------------------------------------
 **/
@MapperInterface
public interface ReqTemplateMapper {
	
	/**
	 * 사용중인 문구 템플릿 목록 조회
	 * @param params
	 * @return List<SOMap>
	 */
	List<SOMap> selectAllUseTemplateList(SOMap params);
	
	/**
	 * 답변템플릿 목록 조회
	 * @param params
	 * @return List<SOMap>
	 */
	List<SOMap> selectRepTemplateList(SOMap params);
	
	/**
	 * 답변템플릿 건수 조회
	 * @param params
	 * @return SOMap
	 */
	SOMap selectRepTemplateListCount(SOMap params);
	
	/**
	 * 답변템플릿 상세 조회
	 * @param params
	 * @return SOMap
	 */
	SOMap selectRepTemplateDetail(SOMap params);
	
	/**
	 * 답변템플릿 저장
	 * @param params
	 * @return int
	 */
	int mergeRepTemplate(SOMap params);
	
	/**
	 * 답변템플릿 삭제
	 * @param params
	 * @return int
	 */
	int updateRepTemplateUse(SOMap params);
	
	/**
	 * 답변템플릿 노출순서 변경
	 * @param params
	 * @return int
	 */
	int updateRepTemplateSort(SOMap params);
}

/**
 *
 */
package com.dplot.mapper;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

import java.util.List;
import java.util.Map;

/**
 * @FileName : EPointMapper.java
 * @Project : datapick_api
 * @Date : 2021. 12. 27.
 * @Author : LKW
 * ============================================================
 *  DATE               AUTHOR         NOTE
 * ============================================================
 *  2021. 12. 27.         LKW                 최초작성
 * ------------------------------------------------------------
 **/
@MapperInterface
public interface EPointMapper {

	/**
	 * e포인트 저장
	 *
	 * @param params
	 */
	void insertEpoint(SOMap params);

	/**
	 * e포인트 수정
	 * 
	 * @param params
	 */
	void updateEpoint(SOMap params);

	/**
	 * e포인트 리스트 조회
	 *
	 * @param params
	 * @return
	 */
	List<SOMap> selectAdminEpointList(SOMap params);

	/**
	 * e포인트 상세 조회
	 *
	 * @param params
	 * @return
	 */
	SOMap selectAdminEpointDetail(SOMap params);

	/**
	 * e포인트 리스트 상태값 (전체,)
	 *
	 * @param params
	 * @return
	 */
	Map<String, Object> selectAdminEpointStateList(Map<String, Object> params);

	/**
	 * e포인트 저장 여부 판별
	 *
	 * @param params
	 * @return
	 */
	SOMap isAdminCanUpdateCheck(SOMap params);

	/**
	 * e포인트 중복기간 체크
	 * @param params
	 * @return
	 */
	SOMap checkDuplEpoint(SOMap params);
}

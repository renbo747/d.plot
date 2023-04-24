package com.dplot.mapper;

import java.util.List;
import java.util.Map;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @discription	: 관리자  Mapper (T_ADMIN)
 * @fileName	: AdminMapper.java
 * @author		: LKW
 * @date		: 2021.12.30
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.12.30	LKW			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface AdminMapper {

	/**
	 * 운영자 목록 조회
	 * @param params
	 * @return
	 */
	List<SOMap> selectOperatorList(SOMap params);

	/**
	 * 운영자 사용여부별 수량 조회
	 * @param params
	 * @return
	 */
	SOMap selectOperatorState(SOMap params);

	/**
	 * 운영자 사용여부 수정
	 * @param params
	 * @return
	 */
	int updateOperatorUse(SOMap params);

	/**
	 * 운영자 엑셀 목록 조회
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> selectOperatorExcelList(SOMap params);

	/**
	 * 운영자 추가
	 * @param params
	 * @return
	 */
	int insertAdmin(SOMap params);

	/**
	 * 운영자 상세 조회
	 * @param params
	 * @return
	 */
	SOMap selectOperator(SOMap params);

	/**
	 * 운영자 수정
	 * @param params
	 * @return
	 */
	int updateAdmin(SOMap params);
	
}

package com.dplot.mapper;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The Interface CodeMapper.
 */
@MapperInterface
public interface CodeMapper {
	/**
	* @Method Name : selectCodeMstList
	* @Method 설명 : 관리자 환경설정 -> 전체운영설정 -> 코드관리 대코드 목록
	* 
	* @param params
	* @return
	*/
	ArrayList<HashMap<String, Object>> selectCodeMstList(SOMap params);
	
	/**
	* @Method Name : selectCodeDtlList
	* @Method 설명 : 관리자 환경설정 -> 전체운영설정 -> 코드관리 소코드 상세목록
	* 
	* @param params
	* @return
	*/
	ArrayList<SOMap> selectCodeDtlList(SOMap params);

	/**
	 *
	 * @param params
	 * @return
	 */
	List<SOMap> selectCodeDtlMapList(SOMap params);

	/**
	 * @Method Name : selectCodeDtlTotalList
	 * @Method 설명 : 관리자 환경설정 -> 전체운영설정 -> 코드관리 소코드 상세목록 ( 전체 temp 데이터 추가 )
	 *
	 * @param params
	 * @return
	 */
	ArrayList<SOMap> selectCodeDtlAddDummyTotalList(SOMap params);
}

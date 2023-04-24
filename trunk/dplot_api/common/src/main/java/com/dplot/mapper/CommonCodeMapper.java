package com.dplot.mapper;

import java.util.List;
import java.util.Map;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @discription	: 공통코드  Mapper (T_COMMON_CODE)
 * @fileName	: CommonCodeMapper.java
 * @author		: LKW
 * @date		: 2022.01.10
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.01.10	LKW			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface CommonCodeMapper {

	/**
	 * 공통코드 목록 조회
	 * @param params
	 * @return
	 */
	List<SOMap> selectCodeList(SOMap params);

	/**
	 * 공통코드 사용여부 변경
	 * @param params
	 * @return
	 */
	int updateCodeUse(SOMap params);

	/**
	 * 공통코드 엑셀 목록 조회
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> selectCodeExcelList(SOMap params);

	/**
	 * 공통코드 저장
	 * @param params
	 * @return
	 */
	int saveCode(SOMap params);

	/**
	 * 공통코드 상세조회
	 * @param params
	 * @return
	 */
	SOMap selectCodeDetail(SOMap params);

	/**
	 * 공통코드 수정
	 * @param params
	 * @return
	 */
	int modifyCode(SOMap params);

}

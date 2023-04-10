package com.dplot.mapper;

import java.util.List;
import java.util.Map;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @discription	: 공통코드분류  Mapper (T_COMMON_CLASS)
 * @fileName	: CommonClassMapper.java
 * @author		: LKW
 * @date		: 2022.01.10
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.01.10	LKW			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface CommonClassMapper {
	
	/**
	 * 공통코드그룹 목록 조회
	 * @param params
	 * @return
	 */
	List<SOMap> selectCodeClassList(SOMap params);

	/**
	 * 공통코드그룹 사용여부 변경
	 * @param params
	 * @return
	 */
	int updateCodeClassUse(SOMap params);

	/**
	 * 공통코드그룹 엑셀 목록 조회
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> selectCodeClassExcelList(SOMap params);

	/**
	 * 공통코드그룹 저장
	 * @param params
	 * @return
	 */
	int saveCodeClass(SOMap params);

	/**
	 * 공통코드그룹 상세조회
	 * @param params
	 * @return
	 */
	SOMap selectCodeClassDetail(SOMap params);

	/**
	 * 공통코드그룹 수정
	 * @param params
	 * @return
	 */
	int modifyCodeClass(SOMap params);

	/**
	 * 공통코드그룹 수량 조회
	 * @param params
	 * @return
	 */
	int selectCodeClassCnt(SOMap params);

}

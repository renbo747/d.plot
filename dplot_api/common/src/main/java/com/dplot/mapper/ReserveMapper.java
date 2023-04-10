package com.dplot.mapper;

import java.util.List;
import java.util.Map;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;


/**
 * @discription	: 적립금 Mapper (T_RESERVE)
 * @fileName	: ReserveMapper.java
 * @author		: JSK
 * @date		: 2021.12.20
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.12.20	JSK			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface ReserveMapper {
	
	/**
	 * 적립금 목록 조회
     * @param param
     * @return List<SOMap>
     * @throws Exception
	 */
	List<SOMap> selectReserveList(SOMap params);
	
	/**
	 * 적립금 목록 전체건수 조회
     * @param param
     * @return SOMap
     * @throws Exception
	 */
	SOMap selectReserveListCount(SOMap params);
	
	/**
	 * 적립금 목록 조회(엑셀용)
     * @param param
     * @return List<SOMap>
     * @throws Exception
	 */
	List<Map<String, Object>> selectReserveListForExcel(SOMap params);
	
	/**
	 * 적립금 저장
     * @param param
     * @return int
     * @throws Exception
	 */
	int insertReserve(SOMap params);

	/**
	 * FRONT 적립금 조회
	 * @param dbParam
	 * @return
	 */
	List<SOMap> selectFrontReserveList(SOMap dbParam);

	/**
	 * 적립금 소멸 예정 대상 리스트 조회 (카카오톡 발송용)
	 */
	List<SOMap> selectReserveExpireList(SOMap param);

	/**
	 * 사용자 소멸금액 조회
	 * @param param
	 * @return
	 */
	SOMap selectExtinctReserveInfo(SOMap param);
	
	/**
	 * 적립금차감 저장
     * @param param
     * @throws Exception
	 */
	void spInsertManualReserveuse(SOMap params);
}

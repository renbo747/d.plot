package com.dplot.mapper;

import java.util.List;
import java.util.Map;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @discription	: 도서산간배송비 Mapper (T_ISOLATION_FARE)
 * @fileName	: IsolationFareMapper.java
 * @author		: JSK
 * @date		: 2021.11.22
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.11.22	JSK			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface IsolationFareMapper {

	/**
	 * 제주/도서산간지역 목록 조회
	 * @param params
	 * @return List<SOMap>
	 */
	List<SOMap> selectIsolationList(SOMap params);

	/**
	 * 제주/도서산간지역 전체건수 조회
	 * @param params
	 * @return int
	 */
	int selectIsolationListCount(SOMap params);
	
	/**
	 * 제주/도서산간여부 조회
	 * @param param
	 * @return
	 */
	String selectIsolationType(SOMap param);
}

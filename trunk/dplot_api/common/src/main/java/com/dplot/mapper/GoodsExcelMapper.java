package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;


/**
 * @discription	: 상품엑셀업로드 Mapper
 * @fileName	: GoodsExcelMapper.java
 * @author		: JSK
 * @date		: 2022.07.10
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.07.10	JSK			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface GoodsExcelMapper {

	/**
	 * 상품엑셀업로드 목록 조회
	 * @param params
	 * @return SOMap
	 */
	List<SOMap> selectGoodsExcelList(SOMap params);

	/**
	 * 상품엑셀업로드 목록 건수 조회
	 * @param params
	 * @return int
	 */
	int selectGoodsExcelListCount(SOMap params);
	
	/**
	 * 상품엑셀업로드 저장
	 * @param params
	 * @return int
	 */
	int insertGoodsExcel(SOMap params);
	
	/**
	 * 상품엑셀업로드 갱신
	 * @param params
	 * @return int
	 */
	int updateGoodsExcel(SOMap params);
}

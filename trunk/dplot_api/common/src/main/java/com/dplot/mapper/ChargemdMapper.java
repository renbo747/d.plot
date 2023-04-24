package com.dplot.mapper;

import java.util.List;
import java.util.Map;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @discription	: 담당MD Mapper (T_CHARGE_MD)
 * @fileName	: ChargemdMapper.java
 * @author		: JSK
 * @date		: 2021.11.10
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.11.10	JSK			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface ChargemdMapper {

	/**
	 * 담당MD 목록 조회
	 * @param params
	 * @return List<SOMap>
	 */
	List<SOMap> selectChargemdList(SOMap params);

	/**
	 * 상품 일괄업로드 담당MD 목록 조회
	 * @param params
	 * @return List<Map<String, Object>>
	 */
	List<Map<String, Object>> selectChargemdListForExcel(SOMap params);


	/**
	 * MD 존재여부 체크
	 */
	int selectMdCount(SOMap param);

	/**
	 * ERP로 부터 전달받은 MD 데이터 INSERT
	 */
	int insertMdInfo(SOMap param);

	/**
	 * ERP로 부터 전달받은 MD 데이터 UPDATE
	 */
	int updateMdInfo(SOMap param);
}

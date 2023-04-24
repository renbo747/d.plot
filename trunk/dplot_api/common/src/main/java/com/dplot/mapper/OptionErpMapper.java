package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @discription	: 상품옵션 ERP MAP (T_OPTION_ERP)
 * @fileName	: OptionErpMapper.java
 * @author		: JSK
 * @date		: 2021.12.03
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.12.03	JSK			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface OptionErpMapper {
    
    /**
     * 연결상품 목록 조회
     * @param param
     * @return List<SOMap>
     * @throws Exception
     */
    List<SOMap> selectOptionErpList(SOMap param) throws Exception;
	
	/**
	 * 연결상품 추가
	 * @param params
	 * @return int
	 */
	int insertOptionErp(SOMap params);
	
	/**
	 * 연결상품 삭제
	 * @param params
	 * @return int
	 */
	int deleteOptionErp(SOMap params);

	/**
	 * ERP 전송 상품 ERP MAPPING 정보 조회
	 */
	List<SOMap> selectOptionErpListERP(SOMap param) throws Exception;
}

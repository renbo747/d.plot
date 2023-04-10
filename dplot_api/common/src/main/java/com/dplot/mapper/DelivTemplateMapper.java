package com.dplot.mapper;

import java.util.List;
import java.util.Map;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @discription	: 배송템플릿 Mapper (T_DELIV_TEMPLATE)
 * @fileName	: DelivTemplateMapper.java
 * @author		: JSK
 * @date		: 2021.11.19
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.11.19	JSK			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface DelivTemplateMapper {
	
	/**
	 * 배송 템플릿 목록 조회
	 * @param params
	 * @return List<SOMap>
	 */
	List<SOMap> selectDelivTemplateList(SOMap params);
	
	/**
	 * 배송 템플릿 상세 조회
	 * @param params
	 * @return SOMap
	 */
	SOMap selectDelivTemplateDetail(SOMap params);
	
	/**
	 * 배송 템플릿 추가/수정
	 * @param params
	 * @return int
	 */
	int mergeDelivTemplate(SOMap params);

	/**
	 * ERP 전송 파트너사 배송 템플릿 조회
	 */
	List<SOMap> selectDelivTemplateListERP(SOMap param);
	
	/**
	 * 상품 일괄업로드 배송 템플릿 조회
	 * @param params
	 * @return List<Map<String, Object>>
	 */
	List<Map<String, Object>> selectDelivTemplateListForExcel(SOMap params);
}

package com.dplot.admin.service.operation;

import java.util.List;
import java.util.Map;

import com.dplot.common.SOMap;
import com.dplot.common.Status;

/**
 * @discription	: 반짝특가 Service
 * @fileName	: Service.java
 * @author		: LKW
 * @date		: 2021.12.10
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.12.10	LKW			최초생성
 * -----------------------------------------------------------------
 */
public interface DisplayBlinkService {

	/**
	 * 기본정보 - 타임특가 전체 조회
	 * @return SOMap
	 * @throws Exception
	 */
	SOMap selectBlinkSpcPriceList(SOMap params) throws Exception;
}

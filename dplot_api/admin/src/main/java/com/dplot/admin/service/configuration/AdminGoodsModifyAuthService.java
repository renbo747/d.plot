package com.dplot.admin.service.configuration;

import com.dplot.common.SOMap;
import com.dplot.common.Status;

public interface AdminGoodsModifyAuthService {

	/**
	 * 상품수정권한 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectAuthType(SOMap params) throws Exception;

	/**
	 * 상푸수정권한 수정
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap updateAuthType(SOMap params) throws Exception; 
	
}

package com.dplot.admin.service.configuration;

import com.dplot.common.SOMap;
import com.dplot.common.Status;

public interface AdminTermsManageService {

	/**
	 * 이용약관 목록 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectTermsList(SOMap params) throws Exception;

	/**
	 * 이용약관 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectTerms(SOMap params) throws Exception;

	/**
	 * 이용약관 수정
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap updateTerms(SOMap params) throws Exception;
}

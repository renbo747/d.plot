package com.dplot.admin.service.configuration;

import java.util.List;
import java.util.Map;

import com.dplot.common.SOMap;
import com.dplot.common.Status;

public interface AdminNotifyManageService {

	/**
	 * 상품정보고시관리 목록 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectNotifyList(SOMap params) throws Exception;

	/**
	 * 상품정보고시 수정
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	SOMap updateNotify(SOMap params) throws Exception;

	/**
	 * 상품정보고시관리 엑셀 목록 조회
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	List<Map<String, Object>> selectNotifyExcelList(SOMap params) throws Exception;

	/**
	 * 상품정보고시 상세 조회
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	SOMap selectNotifyTpl(SOMap params) throws Exception;

	/**
	 * 상품정보고시 저장
	 * @param params
	 * @return
	 */
	SOMap saveNotifyTpl(SOMap params) throws Exception;

	/**
	 * 상품정보고시 수정
	 * @param params
	 * @return
	 */
	SOMap  modifyNotifyTpl(SOMap params) throws Exception;
	
}

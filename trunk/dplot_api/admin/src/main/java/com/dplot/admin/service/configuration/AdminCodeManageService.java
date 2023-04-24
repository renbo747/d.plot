package com.dplot.admin.service.configuration;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.dplot.common.SOMap;
import com.dplot.common.Status;

public interface AdminCodeManageService {
	/**
	 * 공통코드그룹 목록 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectCodeClassList(SOMap params) throws Exception;

	/**
	 * 공통코드 목록 조회
	 * @param params
	 * @return
	 */
	SOMap selectCodeList(SOMap params) throws Exception;

	/**
	 * 공통코드그룹 or 공통코드 사용여부 변경
	 * @param params
	 * @return
	 */
	SOMap updateCodeUse(SOMap params) throws Exception;

	/**
	 * 공통코드그룹 엑셀 목록 조회
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> selectCodeClassExcelList(SOMap params) throws Exception;

	/**
	 * 공통코드 엑셀 목록 조회
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> selectCodeExcelList(SOMap params) throws Exception;

	/**
	 * 공통코드그룹 or 공통코드 저장
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap saveCode(SOMap params) throws Exception;

	/**
	 * 공통코드그룹 or 공통코드 상세조회
	 * @param params
	 * @return
	 */
	SOMap selectCodeDetail(SOMap params) throws Exception;

	/**
	 * 공통코드그룹 or 공통코드 수정
	 * @param params
	 * @return
	 */
	SOMap modifyCode(SOMap params) throws Exception;
}

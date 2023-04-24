package com.dplot.admin.service.operation;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.dplot.common.SOMap;

/**
 * @discription	: 팝업 Service
 * @fileName	: OperationMainPopupService.java
 * @author		: LKW
 * @date		: 2022.04.11
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.04.11	LKW			최초생성
 * -----------------------------------------------------------------
 */
public interface OperationMainPopupService {

	/**
	 * 메인팝업 목록 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectMainPopupList(SOMap params) throws Exception;

	/**
	 * 메인팝업 수정
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap updateMainPopup(SOMap params) throws Exception;

	/**
	 * 메인팝업 저장
	 * @param params
	 * @param uploadFile 
	 * @return
	 * @throws Exception
	 */
	SOMap saveMainPopup(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception;

	/**
	 * 메인팝업 수정
	 * @param params
	 * @param uploadFile
	 * @return
	 * @throws Exception
	 */
	SOMap modifyMainPopup(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception;

	/**
	 * 메인팝업 상세 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectMainPopupDetail(SOMap params) throws Exception;

	/**
	 * 메인팝업 변경 체크
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap checkMainPopup(SOMap params) throws Exception;

	/**
	 * 메인팝업 엑셀목록 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> selectMainPopupExcelList(SOMap params) throws Exception;
}

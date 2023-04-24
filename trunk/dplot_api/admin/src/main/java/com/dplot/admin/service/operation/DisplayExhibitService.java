package com.dplot.admin.service.operation;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.dplot.common.SOMap;
import com.dplot.common.Status;

/**
 * @discription	: 기획전 Service
 * @fileName	: Service.java
 * @author		: LKW
 * @date		: 2021.11.26
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.11.26	LKW			최초생성
 * -----------------------------------------------------------------
 */
public interface DisplayExhibitService {

	/**
	 * 기본정보 - 기획전 전체 조회
	 * @return SOMap
	 * @throws Exception
	 */
	SOMap selectExhibitList(SOMap params) throws Exception;

	/**
	 * 기획전 수정
	 * @return SOMap
	 * @throws Exception
	 */
	int updateExhibit(SOMap params) throws Exception;
	
	/**
	 * 기획전 메인노출가능 체크
	 * @return SOMap
	 * @throws Exception
	 */
	SOMap checkExhibitMain(SOMap params) throws Exception;

	/**
	 * 기획전 엑셀 다운로드
	 * @return SOMap
	 * @throws Exception
	 */
	List<Map<String, Object>> selectExhibitExcelList(SOMap params) throws Exception;

	/**
	 * 기획전 저장
	 * @param params
	 * @param uploadFile
	 * @return
	 */
	SOMap saveExhibit(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception;

	/**
	 * 기획전 상세 조회
	 * @param params
	 * @return
	 */
	SOMap selectExhibitDetail(SOMap params) throws Exception;
	
	/**
	 * 기획전 수정
	 * @param params
	 * @param uploadFile
	 * @return
	 * @throws Exception 
	 */
	SOMap updateExhibit(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception;
}

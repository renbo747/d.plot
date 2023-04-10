package com.dplot.admin.service.operation;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.dplot.common.SOMap;
import com.dplot.common.Status;

/**
 * @discription	: 매거진키워드 Service
 * @fileName	: Service.java
 * @author		: LKW
 * @date		: 2022.03.31
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.03.31	LKW			최초생성
 * -----------------------------------------------------------------
 */
public interface MagazineKeywordService {

	/**
	 * 매거진키워드 목록 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectMzKeywordList(SOMap params) throws Exception;

	/**
	 * 매거진키워드 엑셀 목록 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> selectMzKeywordExcelList(SOMap params) throws Exception;

	/**
	 * 매거진키워드 사용여부, 전시여부 수정
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap updateMzKeyword(SOMap params) throws Exception;

	/**
	 * 매거진키워드 수정
	 * @param params
	 * @param uploadFile
	 * @return
	 * @throws Exception
	 */
	SOMap modifyMzKeyword(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception;

	/**
	 * 매거진키워드 상세조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectMzKeywordDetail(SOMap params) throws Exception;

	/**
	 * 매거진키워드 전시조건 체크
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectMzKeywordCheck(SOMap params) throws Exception;

	/**
	 * 매거진키워드 저장
	 * @param params
	 * @param uploadFile
	 * @return
	 * @throws Exception
	 */
	SOMap saveMzKeyword(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception;


}

package com.dplot.admin.service.operation;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.dplot.common.SOMap;
import com.dplot.common.Status;

/**
 * @discription	: 전시영역 Service
 * @fileName	: MagazineDisplayService.java
 * @author		: LKW
 * @date		: 2022.03.30
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.03.30	LKW			최초생성
 * -----------------------------------------------------------------
 */
public interface MagazineDisplayService {

	/**
	 * 매거진 전시영역관리 목록 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectDisplayList(SOMap params) throws Exception;

	/**
	 * 운영관리 공지사항 사용여부 수정
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap updateDisplay(SOMap params) throws Exception;

	/**
	 * 운영관리 공지사항 엑셀 목록 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> selectDisplayExcelList(SOMap params) throws Exception;

	/**
	 * 운영관리 공지사항 등록
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap saveDisplay(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception;

	/**
	 * 운영관리 공지사항 상세조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectDisplayDetail(SOMap params) throws Exception;

	/**
	 * 운영관리 공지사항 수정
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap modifyDisplay(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception;
	
	/**
	 * 운영관리 공지사항 변경시 알람용 체크
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap checkDisplay(SOMap params) throws Exception;

}

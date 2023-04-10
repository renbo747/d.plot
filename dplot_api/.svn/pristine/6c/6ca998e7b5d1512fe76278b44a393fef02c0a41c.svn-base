package com.dplot.admin.service.operation;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.dplot.common.SOMap;
import com.dplot.common.Status;

/**
 * @discription	: 매거진트렌트 Service
 * @fileName	: MagazineTrendService.java
 * @author		: LKW
 * @date		: 2022.04.01
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.04.01	LKW			최초생성
 * -----------------------------------------------------------------
 */
public interface MagazineTrendService {

	/**
	 * 매거진트렌트 목록 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectTrendList(SOMap params) throws Exception;

	/**
	 * 매거진트렌트 사용여부, 전시여부 수정
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap updateTrend(SOMap params) throws Exception;

	/**
	 * 매거진트렌트 엑셀 목록 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> selectTrendExcelList(SOMap params) throws Exception;

	/**
	 * 매거진트렌트 등록
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap saveTrend(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception;

	/**
	 * 매거진트렌트 상세조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectTrendDetail(SOMap params) throws Exception;

	/**
	 * 매거진트렌트 수정
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap modifyTrend(SOMap params, Map<String, MultipartFile> uploadFile) throws Exception;
	
	/**
	 * 매거진트렌트 변경시 알람용 체크
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap checkTrend(SOMap params) throws Exception;

	/**
	 * 매거진트렌드용 매거진카테고리 목록 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectAllMzCategoryList(SOMap params) throws Exception;

}

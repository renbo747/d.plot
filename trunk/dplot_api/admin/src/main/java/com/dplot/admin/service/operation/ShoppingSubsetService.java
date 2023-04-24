package com.dplot.admin.service.operation;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.dplot.common.SOMap;
import com.dplot.common.Status;

/**
 * @discription	: 배너대용문구관리 Service
 * @fileName	: ShoppingSubsetService.java
 * @author		: LKW
 * @date		: 2022.04.04
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.04.04	LKW			최초생성
 * -----------------------------------------------------------------
 */
public interface ShoppingSubsetService {

	/**
	 * 매거진트렌트 목록 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectSubsetList(SOMap params) throws Exception;

	/**
	 * 매거진트렌트 사용여부, 전시여부 수정
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap updateSubset(SOMap params) throws Exception;

	/**
	 * 매거진트렌트 엑셀 목록 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> selectSubsetExcelList(SOMap params) throws Exception;

	/**
	 * 매거진트렌트 등록
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap saveSubset(SOMap params) throws Exception;

	/**
	 * 매거진트렌트 상세조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectSubsetDetail(SOMap params) throws Exception;

	/**
	 * 매거진트렌트 수정
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap modifySubset(SOMap params) throws Exception;
	
	/**
	 * 매거진트렌트 변경시 알람용 체크
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap checkSubset(SOMap params) throws Exception;

}

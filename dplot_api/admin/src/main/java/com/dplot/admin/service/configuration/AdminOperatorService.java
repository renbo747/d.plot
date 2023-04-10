package com.dplot.admin.service.configuration;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.dplot.common.SOMap;
import com.dplot.common.Status;

public interface AdminOperatorService {
    /**
     * 운영자 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    SOMap selectOperatorList(SOMap params) throws Exception;

    /**
     * 운영자 사용여부 수정
     * @param params
     * @return
     */
	SOMap updateOperatorUse(SOMap params) throws Exception;

	/**
	 * 운영자 엑셀 목록 조회
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> selectOperatorExcelList(SOMap params) throws Exception;

	/**
	 * 아이디 중복체크
	 * @param params
	 * @return
	 */
	SOMap checkIdDupl(SOMap params) throws Exception;

	/**
	 * 운영자 추가
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	SOMap insertOperator(SOMap params) throws Exception;

	/**
	 * 운영자 상세 조회
	 * @param params
	 * @return
	 */
	SOMap selectOperator(SOMap params) throws Exception;

	/**
	 * 운영자 수정
	 * @param params
	 * @return
	 */
	SOMap modifyOperator(SOMap params) throws Exception;
}

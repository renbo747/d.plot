package com.dplot.admin.service.promotion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.dplot.common.SOMap;
import com.dplot.common.Status;

public interface AdminRecomRewardService {
    /**
     * 추천리워드 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    SOMap selectRecomReward(SOMap params) throws Exception;
	/**
	 * 추천리워드 엑셀 목록 조회
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> selectRecomRewardExcelList(SOMap params) throws Exception;

	/**
	 * 추천리워드 저장
	 * @param params
	 * @return
	 */
	SOMap saveRecomReward(SOMap params) throws Exception;
	
	/**
	 * 추천리워드로그 목록 조회
	 * @param params
	 * @return
	 */
	SOMap selectRecomRewardLogList(SOMap params) throws Exception;
}

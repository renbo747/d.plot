package com.dplot.admin.service.goods;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.dplot.common.SOMap;

/**
 * @discription	: 상품관리 Service
 * @fileName	: GoodsManageService.java
 * @author		: JSK
 * @date		: 2021.12.08
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.12.08	JSK			최초생성
 * -----------------------------------------------------------------
 */
public interface GoodsManageService {

	/**
	 * 조회조건 - 파트너사 목록 조회
	 * @return List<SOMap>
	 * @return SOMap
	 * @throws Exception
	 */
	List<SOMap> selectPartnerList(SOMap params) throws Exception;

	/**
	 * 전체상품목록 조회
	 * @param params
	 * @return SOMap
	 * @throws Exception
	 */
	SOMap selectAllGoodsList(SOMap params) throws Exception;

	/**
	 * 전체상품목록(엑셀다운로드용) 조회
	 * @param params
	 * @return List<Map<String, Object>>
	 * @throws Exception
	 */
	List<Map<String, Object>> selectAllGoodsListForExcel(SOMap params) throws Exception;

    /**
     * 상품 전시상태 변경
     * @param params
     * @return int
     * @throws Exception
     */
	int updateGoodsDisplay(SOMap params) throws Exception;

    /**
     * 상품 판매상태 변경
     * @param params
     * @return int
     * @throws Exception
     */
	int updateGoodsSellType(SOMap params) throws Exception;

    /**
     * 상품삭제
     * - 판매상태 영구종료 변경
     * - 상품 미전시로 변경
     * @param params
     * @return int
     * @throws Exception
     */
	int updateGoodsTerminate(SOMap params) throws Exception;

    /**
     * 상품 승인상태 변경
     * @param params
     * @return int
     * @throws Exception
     */
	int updateGoodsApprType(SOMap params) throws Exception;

	/**
	 * 상품 처리(승인)이력 조회
	 * @param params
	 * @return SOMap
	 * @throws Exception
	 */
	SOMap selectGoodsApprHistList(SOMap params) throws Exception;

	/**
	 * 상품이력 조회
	 * @param params
	 * @return SOMap
	 * @throws Exception
	 */
	SOMap selectGoodsHistList(SOMap params) throws Exception;

    /**
     * 상품정보 일괄수정
     * @param params
     * @param uploadFiles
     * @return int
     * @throws Exception
     */
	int updateGoodsAll(SOMap params, Map<String, MultipartFile> uploadFiles) throws Exception;
}

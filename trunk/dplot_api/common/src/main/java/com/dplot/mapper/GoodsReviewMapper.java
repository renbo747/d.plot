package com.dplot.mapper;

import java.util.List;
import java.util.Map;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * 
 * @FileName : GoodsReviewMapper.java
 * @Project : datapick_api
 * @Date : 2021. 12. 1. 
 * @Author : YIY
 * ============================================================
 *  DATE					AUTHOR			NOTE
 * ============================================================
 *  2021. 12. 1.			YIY			        최초작성
 * ------------------------------------------------------------
 *
 */
@MapperInterface
public interface GoodsReviewMapper {
	
	/**
	 * Front 베스트 리뷰 목록 조회
	 * @param params
	 * @return
	 */
	List<SOMap> selectFrontBestReviewList(SOMap params);

	/**
	 * 리뷰 목록 조회
	 * @param params
	 * @return
	 */
	List<SOMap> selectOperationReviewList(SOMap params);

	/**
	 * 리뷰 수량 조회
	 * @param params
	 * @return
	 */
	SOMap selectOperationReviewCnt(SOMap params);

	/**
	 * 리뷰 베스트여부 수정
	 * @param params
	 * @return
	 */
	int updateReview(SOMap params);

	/**
	 * 리뷰 엑셀 목록 조회
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> selectOperationReviewExcelList(SOMap params);

	/**
	 * 리뷰 상세 조회
	 * @param params
	 * @return
	 */
	SOMap selectReviewDetail(SOMap params);

	/**
	 * 리뷰 수정
	 * @param params
	 * @return
	 */
	int modifyReview(SOMap params);

	/**
	 * 베스트 리뷰 수량 조회
	 * @param params
	 * @return
	 */
	int selectBestCnt(SOMap params);

	/**
	 * 리뷰 저장처리
	 * @param param
	 * @return
	 */
	int insertGoodsReview(SOMap param);

	/**
	 * 나의 리뷰 목록 조회
	 * @param param
	 * @return
	 */
	List<SOMap> selectMyReviewList(SOMap param);

	/**
	 * 나의 리뷰 상세 조회
	 * @param param
	 * @return
	 */
	SOMap selectFrontReviewDetail(SOMap param);

	/**
	 * 리뷰 수정 처리
	 * @param param
	 * @return
	 */
	int updateFrontGoodsReview(SOMap param);

	/**
	 * 리뷰 삭제 처리
	 * @param param
	 * @return
	 */
	int updateReviewIstrash(SOMap param);

	/**
	 * 전체 목록수
	 * @param param
	 * @return
	 */
	int selectMyReviewCnt(SOMap param);

	/**
	 * 리뷰 목록 조회
	 * @param param
	 * @return
	 */
	List<SOMap> selectReviewListbyGoodsno(SOMap param);

	/**
	 * 리뷰 목록수 조회(페이징)
	 * @param param
	 * @return
	 */
	int selectReviewListbyGoodsnoCnt(SOMap param);

	/**
	 * 회원 리뷰 작성 수 조회
	 * @param reserveParam
	 * @return
	 */
	int selectReviewCnt(SOMap reserveParam);

	/**
	 * 신고여부확인
	 * @param param
	 * @return
	 */
	SOMap selectReviewIsNoti(SOMap param);
}

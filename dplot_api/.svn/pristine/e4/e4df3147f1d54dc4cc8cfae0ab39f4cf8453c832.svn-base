package com.dplot.front.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.dplot.common.SOMap;
import com.dplot.common.Status;

public interface ReviewService {

	/**
	 * 리뷰보상 정보 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public SOMap selectReviewReward(SOMap param) throws Exception;

	/**
	 * 나의 리뷰 목록 조회(작성가능, 작성완료)
	 * @param param
	 * @return
	 */
	public SOMap selectMyReviewList(SOMap param) throws Exception;
	
	/**
	 * 리뷰 저장 처리
	 * @param param
	 * @param uploadFile 
	 * @return
	 */
	public SOMap saveReview(SOMap param, Map<String, MultipartFile> uploadFile) throws Exception;

	/**
	 * 리뷰 상세 조회
	 * @param param
	 * @return
	 */
	public SOMap selectReviewDetail(SOMap param) throws Exception;

	/**
	 * 나의 리뷰 삭제 처리
	 * @param param
	 * @return
	 */
	public SOMap deleteReview(SOMap param) throws Exception;

	/**
	 * 상품상세 리뷰 목록 조회
	 * @param param
	 * @return
	 */
	public SOMap selectReviewList(SOMap param) throws Exception;

	/**
	 * 리뷰 신고하기
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public SOMap saveReviewNoti(SOMap param) throws Exception;

	/**
	 * 리뷰 좋아요 처리
	 * @param param
	 * @return
	 */
	public SOMap saveReviewHelp(SOMap param) throws Exception;

	/**
	 * 메거진_리뷰 목록 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public SOMap selectMzReview(SOMap param) throws Exception;

	/**
	 * 메거진_라이브 리뷰 목록 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public SOMap selectMzLiveReview(SOMap param) throws Exception;
}

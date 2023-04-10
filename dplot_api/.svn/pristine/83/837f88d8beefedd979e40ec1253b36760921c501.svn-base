package com.dplot.admin.service.operation;

import java.util.List;
import java.util.Map;

import com.dplot.common.SOMap;
import com.dplot.common.Status;

/**
 * @discription	: 리뷰 Service
 * @fileName	: Service.java
 * @author		: LKW
 * @date		: 2022.03.03
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.03.03	LKW			최초생성
 * -----------------------------------------------------------------
 */
public interface OperationReviewService {

	/**
	 * 운영관리 리뷰 목록 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectReviewList(SOMap params) throws Exception;

	/**
	 * 운영관리 리뷰 엑셀 목록 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> selectReviewExcelList(SOMap params) throws Exception;

	/**
	 * 운영관리 리뷰 상세조회
	 * @param params
	 * @return
	 */
	SOMap selectReviewDetail(SOMap params) throws Exception;

	/**
	 * 리뷰신고 목록 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectReviewNotiList(SOMap params) throws Exception;

	/**
	 * 리뷰 수정
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap modifyReview(SOMap params) throws Exception;

	/**
	 * 베스트 리뷰 수량체크
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectReviewCheck(SOMap params) throws Exception;

//	/**
//	 * 운영관리 공지사항 등록
//	 * @param params
//	 * @return
//	 * @throws Exception
//	 */
//	SOMap saveNotice(SOMap params) throws Exception;
//
//	/**
//	 * 운영관리 공지사항 상세조회
//	 * @param params
//	 * @return
//	 * @throws Exception
//	 */
//	SOMap selectNoticeDetail(SOMap params) throws Exception;
//
//	/**
//	 * 운영관리 공지사항 수정
//	 * @param params
//	 * @return
//	 * @throws Exception
//	 */
//	SOMap modifyNotice(SOMap params) throws Exception;

}

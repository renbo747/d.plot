package com.dplot.front.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.dplot.common.SOMap;

/**
 * 
 * @FileName : FrontReviewService.java
 * @Project : datapick_api
 * @Date : 2021. 11. 29. 
 * @Author : YIY
 * ============================================================
 *  DATE					AUTHOR			NOTE
 * ============================================================
 *  2021. 11. 29.			YIY			        최초작성
 * ------------------------------------------------------------
 */
public interface FrontCsService {
	
	/**자주하는질문 메인 노출
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<SOMap> selectMainFaq(SOMap params) throws Exception;
	
	/**고객센터 FAQ 검색
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<SOMap> selectFaq(SOMap params) throws Exception;
	
	/**
	 * 고객센터 FAQ 총 갯수
	 * @param params
	 * @return
	 * @throws Exception
	 */
	int selectFaqCount(SOMap params) throws Exception;
		
	/**
	 * 고객센터 공지사항 노출 (상단 고정 최신글 + 최신글)
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<SOMap> selectMainNotice(SOMap params) throws Exception;
	
	
	/**
	 * 고객센터 공지사항 총 갯수
	 * @param params
	 * @return
	 * @throws Exception
	 */
	int selectMainNoticeCount(SOMap params) throws Exception;

	/**
	 * 고객센터 공지사항 상세
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectNoticeDetail(SOMap params) throws Exception;
	
	
	/**
	 * 1:1 문의 내역 검색
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	List<SOMap> selectInquiry(SOMap params) throws Exception;

	/**
	 * 1:1 문의 내역 검색 카운트
	 * @param params
	 * @return
	 */
	int selectInquiryCount(SOMap params) throws Exception;
	
	/**
	 * 1:1 문의 내역 상세
	 * @param params
	 * @return
	 */
	SOMap selectInquiryDetail(SOMap params) throws Exception;
	
	/** 1:1 문의 내역 삭제
	 * @param params
	 * @return
	 */
	int deleteInquiryIdx(SOMap params) throws Exception;
	
	/**
	 * 상품 Q&A
	 * @param params
	 * @return
	 */
	List<SOMap> selectInquiryQna(SOMap params) throws Exception;
	
	/**
	 * 상품 Q&A 카운트
	 * @param params
	 * @return
	 */
	int selectInquiryQnaCount(SOMap params) throws Exception;
	
	/**
	 * 상품 Q&A 상세
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectInquiryQnaDetail(SOMap params) throws Exception;
	
	/**
	 * 상품별 문의 전체
	 * @param parmas
	 * @return
	 * @throws Exception
	 */
	List<SOMap> selectQnaGoods(SOMap params) throws Exception;
	
	/**
	 * 상품별 문의 전체 카운트
	 * @param params
	 * @return
	 */
	int selectQnaGoodsCount(SOMap params) throws Exception;

	/**
	 * 상품별 내 문의 삭제
	 * @param params
	 * @return
	 */
	int deleteMyQna(SOMap params) throws Exception;	
	
	/**
	 * 주문번호 검색
	 * @param params
	 * @return
	 */
	List<SOMap> selectComorder(SOMap params) throws Exception;
	
	/** 주문번호 검색 총 갯수
	 * @param params
	 * @return
	 * @throws Exception
	 */
	int selectComorderCount(SOMap params) throws Exception;

	
	/**
	 * 주문번호로 상품 검색
	 * @param params
	 * @return
	 */
	List<SOMap> selectComorderGoods(SOMap params) throws Exception;
	
	/**
	 * 1:1 문의 유형 등록
	 * @param params
	 * @return idx
	 */
	SOMap saveInquiry(SOMap params, Map<String, MultipartFile> uploadFiles) throws Exception;	

	/**
	 * 1:1 문의 유형 수정
	 * @param params
	 * @return idx
	 */
	void reviseInquiry(SOMap params, Map<String, MultipartFile> uploadFiles) throws Exception;	
	
	
	/**
	 * 상품 옵션 검색
	 * @param params
	 * @return
	 */
	List<SOMap> selectGoodsOption(SOMap params) throws Exception;
	
	/**
	 * 상품 옵션 상세 검색
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<SOMap> selectGoodsOptionDetail(SOMap params) throws Exception;
	
	/**
	 * 상품별 문의 등록
	 * @param params
	 * @return
	 */
	int insertGoodsQna(SOMap params) throws Exception;
	
	/**
	 * 상품 Q&A 수정
	 * @param params
	 * @return
	 * @throws Exception
	 */
	int updateMyQna(SOMap params) throws Exception;


	/**입점문의
	 * @param params
	 * @throws Exception
	 */
	SOMap sendPartnersApply(SOMap params) throws Exception;
	
	/**상품별 옵션 상세 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<SOMap> selectOptionDetail(SOMap params) throws Exception;
	
	/**옵션 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap searchOption(SOMap params) throws Exception;

	/**
	 * 상품 문의 수정
	 * @param params
	 * @return
	 * @throws Exception
	 */
	int updateQna(SOMap params) throws Exception;
	
	/** 조회수 증가
	 * @param params
	 * @return
	 * @throws Exception
	 */
	void addHit(SOMap params) throws Exception;

}

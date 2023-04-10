package com.dplot.front.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.Status;

/**
 * 사용자 상품 service
 */
public interface FrontGoodsService {
	
	/**
	 * 상품 정보 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap goodsDetail(SOMap params) throws Exception;
	
	/**
	 * 상품 로그 등록
	 * SP name : SP_GOODS_LOG
	 * @param p
	 * @throws Exception
	 */
	void spGoodsLog(SOMap p) throws Exception;
	
	/**
	 * 상품 쿠폰 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	Response getCoupon(SOMap params) throws Exception;
	
	/**
	 * 상품 하트 처리
	 * @param params
	 * @return
	 * @throws Exception
	 */
	Response wish(SOMap params) throws Exception;

	/**
	 * 상품 옵션 목록
	 * @param params
	 * @return
	 */
	SOMap selectOptionList(SOMap params) throws Exception;
	
	/**
	 * 추가 상품 목록
	 * @param params
	 * @return
	 */
	SOMap selectAddGoodsList(SOMap params) throws Exception;
	
	/**
	 * 상품 옵션 상세목록
	 * @param params
	 * @return
	 */
	SOMap selectOptionDetailList(SOMap params) throws Exception;

	/**
	 * 쇼핑 상품목록조회(카테고리idx로 조회)
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectGoodsListByCateIdx(SOMap params) throws Exception;

	/**
	 * 상품위시리스트 목록 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	SOMap selectWishList(SOMap params) throws Exception;

	/**
	 * 상품위시리스트 목록 삭제
	 * @param param
	 * @return
	 * @throws Exception
	 */
	SOMap deleteWishLike(SOMap param) throws Exception;

	/**
	 * 최근본 상품 목록 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	SOMap selectRecentList(SOMap param) throws Exception;
	
	/**
	 * 베스트 상품 목록 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	SOMap selectBestList(SOMap param) throws Exception;

	/**
	 * 재입고알림 신청 목록 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	SOMap selectRestockList(SOMap param) throws Exception;

	/**
	 * 재입고알림 목록 삭제
	 * @param param
	 * @return
	 * @throws Exception
	 */
	SOMap deletelRestock(SOMap param) throws Exception;

	/**
	 * 필터 상품목록 조회 목록의 좋아요 여부 가져오기
	 * @param param
	 * @return
	 * @throws Exception
	 */
	SOMap selectIswished(SOMap param) throws Exception;

	/**
	 *  재입고알림 신청
	 * @param param
	 * @return
	 */
	SOMap insertReware(SOMap param) throws Exception;
}

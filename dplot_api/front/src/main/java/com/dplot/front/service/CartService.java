/**
 * 
 */
package com.dplot.front.service;

import com.dplot.common.SOMap;

/**
 * @FileName : CartService.java
 * @Project : datapick_api
 * @Date : 2021. 12. 8. 
 * @Author : KTH
 * ============================================================
 *  DATE               AUTHOR         NOTE
 * ============================================================
 *  2021. 12. 8.         KTH                 최초작성
 * ------------------------------------------------------------
 **/
public interface CartService {
	
	/**
	 * 카트목록 조회
	 * @param param
	 * @return
	 */
	public SOMap selectCartList(SOMap param) throws Exception;
	
	/**
	 * 카트등록
	 * @param param, request, response
	 * @return
	 */
	public SOMap saveCart(SOMap param) throws Exception;
	
	
	/**
	 * 카트 개수 조회
	 * @param param
	 * @return
	 */
	public SOMap selectCartCnt(SOMap param) throws Exception;
	
	/**
	 * 카드전체삭제
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public SOMap deleteCart(SOMap param) throws Exception;
}

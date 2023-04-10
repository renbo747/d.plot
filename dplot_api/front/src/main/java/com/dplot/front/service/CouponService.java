/**
 * 
 */
package com.dplot.front.service;

import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.Status;

/**
 * @FileName : CouponService.java
 * @Project : datapick_api
 * @Date : 2021. 12. 8. 
 * @Author : KTH
 * ============================================================
 *  DATE               AUTHOR         NOTE
 * ============================================================
 *  2021. 12. 8.         KTH                 최초작성
 * ------------------------------------------------------------
 **/
public interface CouponService {
	
	/**
	 * 주문결제 다운로드 쿠폰 조회
	 * @param param
	 * @return
	 */
	public SOMap selectOrderCouponList(SOMap param) throws Exception;
	
	/**
	 * 마이페이지 쿠폰목록 조회
	 * @param param
	 * @return
	 */
	public SOMap selectMypageCouponList(SOMap param) throws Exception;
	
	/**
	 * 쿠폰다운로드
	 * @param param, request, response
	 * @return
	 */
	public Response downloadCoupon(SOMap param) throws Exception;
	
	/**
	 * 쿠폰전체다운로드
	 * @param param, request, response
	 * @return
	 */
	public Response downloadAllCoupon(SOMap param) throws Exception;

	/**
	 * 쿠폰전체다운로드(페이징으로인하여 전체 목록 조회후  다운로드처리)
	 * @param params
	 * @return
	 */
	public SOMap pageDownloadAllCoupon(SOMap params) throws Exception;

	/**
	 * 평생회원 쿠폰 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public SOMap lifetimeCoupon(SOMap params) throws Exception;
	
	
	/**
	 * 이벤트 쿠폰 다운로드
	 * @param param, request, response
	 * @return
	 */
	public Response downCoupon(SOMap param) throws Exception;

}

package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * 
 * @FileName : ComOrderGoodsDelivMapper.java
 * @Project : datapick_api
 * @Date : 2022. 1. 26. 
 * @Author : YIY
 * ============================================================
 *  DATE					AUTHOR			NOTE
 * ============================================================
 *  2022. 1. 26.			YIY			        최초작성
 * ------------------------------------------------------------
 *
 */
@MapperInterface
public interface ComOrderGoodsDelivMapper {
	/**
	 * 주문배송정보 저장
	 * @param dbParam
	 * @return
	 */
	int insertComOrderGoodsDeliv(SOMap dbParam);

	/**
	 * 주문 배송정보 저장 (ERP 직매입)
	 * @param dbParam
	 * @return
	 */
	int insertComOrderGoodsDelivByERP(SOMap dbParam);
	
	/**
	 * 관리자 주문배송정보 수정
	 * @param dbParam
	 * @return
	 */
	int updateAdminOrderGoodsDeliv(SOMap dbParam);
	
	/**
	 * 송장정보 목록조회
	 * @param dbParam
	 * @return List<SOMap>
	 */
	List<SOMap> selectOrderInvoiceList(SOMap dbParam);
	
	/**
	 * 송장정보 목록조회
	 * @param dbParam
	 * @return
	 */
	List<SOMap> selectOrderInvoiceListByOrderidx(SOMap dbParam);
	
	
	/**
	 * 송장건수 조회
	 * @param dbParam
	 * @return int
	 */
	int selectOrderInvoiceCount(SOMap dbParam);
	
	/**
	 * 주문상품배송정보 사용여부 갱신
	 * @param dbParam
	 * @return int
	 */
	int updateComOrderGoodsDelivUse(SOMap dbParam);

	/**
	 * 송장번호 ARRAY 를 통한 주문상품배송리스트 조회
	 * @param dbParam
	 * @return
	 */
	List<SOMap> selectOrderInvoiceListByInvoiceArr(SOMap dbParam);

	/**
	 * 주문상품배송정보 수정
	 * @param dbParam
	 * @return
	 */
	int updateOrderGoodsDeliv(SOMap dbParam);

	/**
	 * 배송추적에러메세지 업데이트
	 * @param param
	 * @return
	 */
	int updateDeliveryTrackingErrorMessage(SOMap param);
	
	/**
	 * 주문IDX로 배송상태 수정
	 * @param param
	 * @return
	 */
	int updateDeliveryStatusByOrderIdx(SOMap param);
	
	/**
	 * 주문상품IDX로 배송상태 수정
	 * @param param
	 * @return
	 */
	int updateDeliveryStatusByOrdgdIdx(SOMap param);

	/**
	 * 굿스플로 데이터기반 IDX 조회
	 */
	List<SOMap> selectDeliveringList(SOMap param);

	/**
	 * 주문 배송 리스트 조회 ERP 처리용
	 */
	List<SOMap> selectOrderDelivERPData(SOMap param);

	/**
	 * 구매확정 리스트 조회 ERP 전송용
	 */
	List<SOMap> selectOrderConfirmERPData(SOMap param);
	
	/**
	 * 주문상품IDX로 배송중 주문상품배송 건수 조회
	 * @param param
	 * @return int
	 */
	int selectInDelivCntByOrdgdidx(SOMap param);
	
	/**
	 * 배송수량 갱신
	 * @param param
	 * @return int
	 */
	int updateDelivCnt(SOMap param);
	
	/**
	 * 직배송 안내문구 저장
	 * @param param
	 * @return int
	 */
	int updateDirDelivMsg(SOMap param);
	
	/**
	 * 배송중인 같은 운송장 배송정보 조회
	 * @param param
	 * @return List<SOMap>
	 */
	List<SOMap> selectSameInvoiceInfo(SOMap param);

	/**
	 * 데이터 처리
	 */
	SOMap selectOrderDeliveryMatchedERPData(SOMap param);
}

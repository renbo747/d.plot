package com.dplot.common.service;

import com.dplot.common.SOMap;

import java.util.List;
import java.util.Map;

public interface DeliveryTrackingService {
    /**
     * 배송/반품/교환 추적요청
     */
    SOMap sendTraceRequest(SOMap param, List<SOMap> orderList) throws Exception;

    /**
     * 배송추적응답
     */
    SOMap receiveTraceResult(SOMap param) throws Exception;

    /**
     * 배송최종상태확인
     */
    SOMap traceLastDlvState(List<Map<String, Object>> list) throws Exception;

    /**
     * 반품요청(반품추적요청)
     */
    SOMap requestReturn(SOMap param, List<SOMap> orderList) throws Exception;

    /**
     * 반품추척응답
     */
    SOMap receiveTransResult(SOMap param) throws Exception;

	/**
	 * 배송추적 URL 가져오기
	 */
	SOMap getDelivTrackingUrl(SOMap params) throws Exception;

	/**
	 * 굿스플로에서 사용하는 택배사 코드 조회
	 */
	String getGoodsFlowLogisCode(String params) throws Exception;

    /**
     * 서비스 이용 신청을 위한 OTP 코드 발급 요청
     */
	SOMap getPartnersOTPCode(SOMap param) throws Exception;

    /**
     * 서비스 이용 신청에 대한 결과 (일 단위)
     */
	SOMap requestApplyServiceUsageResult(SOMap param) throws Exception;

    /**
     * 배송 추적 상세 내용 리턴
     */
	SOMap getDeliveryTrackingData(SOMap param);
}

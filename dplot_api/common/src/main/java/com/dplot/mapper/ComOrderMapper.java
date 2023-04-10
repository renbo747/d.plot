package com.dplot.mapper;

import java.util.List;
import java.util.Map;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * 
 * @FileName : ComOrderMapper.java
 * @Project : datapick_api
 * @Date : 2022. 1. 20. 
 * @Author : YIY
 * ============================================================
 *  DATE					AUTHOR			NOTE
 * ============================================================
 *  2022. 1. 20.			YIY			        최초작성
 * ------------------------------------------------------------
 *
 */
@MapperInterface
public interface ComOrderMapper {
	
	
	/**
	 * 비회원주문 정보 조회
	 * @param dbParam
	 * @return
	 */
	SOMap selectComOrderNoneMember(SOMap dbParam);
	
	/**
	 * 공통주문 정보 조회
	 * @param dbParam
	 * @return
	 */
	SOMap selectComOrderInfo(SOMap dbParam);
	
	/**
	 * 공통주문 목록 조회
	 * @param params
	 * @return
	 */
	List<SOMap> selectComOrderList(SOMap params);

	/**
	 * 주문결재일 수정
	 * @param dbParam
	 * @return int
	 */
	int updateOrderPayment(SOMap dbParam);
	
	/**
	 * 현금영수증 발급
	 * @param dbParam
	 * @return
	 */
	int updateCashReceipts(SOMap dbParam);
	
	/**
	 * 주문삭제여부 수정
	 * @param dbParam
	 * @return int
	 */
	int updateOrderIsTrash(SOMap dbParam);
	
	/**
	 * 주문/배송목록 조회
	 * @param dbParam
	 * @return
	 */
	List<SOMap> selectMyOrderList(SOMap dbParam);

	/**
	 * 주문내역 저장
	 * @param dbParam
	 * @return
	 */
	int insertComOrder(SOMap dbParam);

	/**
	 * 사용자 6개월간 구매 금액 및 건수 조회
	 * @param dbParam
	 * @return
	 */
	SOMap selectUserOrderInfo(SOMap dbParam);
	
	/**
	 * 구매확정건수조회
	 * @param dbParam
	 * @return
	 */
	int selectConfirmOrderCnt(SOMap dbParam);
	
	/**
	 * 관리자용 전체 주문/클레임 목록 조회
	 * @param params
	 * @return List<SOMap>
	 */
	List<SOMap> selectAdminOrderClaimList(SOMap params);
	
	/**
	 * 관리자용 전체 주문/클레임 건수 조회
	 */
	SOMap selectAdminOrderClaimListCount(SOMap params);
	
	/**
	 * 관리자용 전체 주문/클레임 조회(엑셀용)
	 */
	List<Map<String, Object>> selectAdminOrderClaimListForExcel(SOMap params);
	
	/**
	 * 관리자용 전체 주문/클레임 조회(엑셀용) NEW
	 */
	List<Map<String, Object>> selectAdminOrderClaimListForExcelNew(SOMap params);
	
	/**
	 * 관리자용 상태별 주문목록 조회
	 * @param params
	 * @return List<SOMap>
	 */
	List<SOMap> selectAdminOrderList(SOMap params);
	
	/**
	 * 관리자용 상태별 주문목록 건수 조회
	 */
	SOMap selectAdminOrderListCount(SOMap params);
	
	/**
	 * 관리자용 상태별 주문목록 조회(엑셀용)
	 */
	List<Map<String, Object>> selectAdminOrderListForExcel(SOMap params);

	/**
	 * 관리자-주문기본정보 조회
	 * @param dbParam
	 * @return SOMap
	 */
	SOMap selectAdminOrderInfo(SOMap dbParam);

	/**
	 * 관리자-주문상세정보 조회
	 * @param dbParam
	 * @return SOMap
	 */
	SOMap selectAdminOrderDetail(SOMap dbParam);

	/**
	 * 관리자-주문배송지정보 수정
	 * @param dbParam
	 * @return int
	 */
	int updateOrderRcvInfo(SOMap dbParam);
	
	/**
	 * 미입금 취소처리 대상 조회 (카카오 미입금 취소 발송 대상)
	 */
	List<SOMap> selectOutstandingTargetList(SOMap param);	
	
	/**
	 * 프론트 주문목록
	 * @param dbParam
	 * @return
	 */
	List<SOMap> selectFrontComOrder(SOMap dbParam);
	
	/**프론트 주문목록 총 갯수
	 * @param dbParam
	 * @return
	 */
	int selectFrontComOrderCount(SOMap dbParam);

	/**
	 *	카카오 메세지 발송을 위한 조회 기준은 송장번호 (배송중/배송완료)
	 */
	List<SOMap> selectDeliverySendTargetByInvoice(SOMap param);

	/**
	 *	카카오 메세지 발송대상 조회(관리자 강제 배송완료시)
	 */
	List<SOMap> selectCompDelivMsgTargetByInvoice(SOMap param);

	/**
	 * 관리자 사용자 디테일 주문 리스트 3개
	 */
	List<SOMap> selectAdminMemberOrderList(SOMap param);

	/**
	 * ERP 전송용 주문 생성 데이터 조회
	 */
	SOMap selectOrderERPData(SOMap params);

	/**
	 * ERP 전송용 주문 배송 정보 데이터 조회
	 */
	List<SOMap> selectOrderDeliveryInfoERPData(SOMap param);

	/**
	 * PG 대사 결과비교용 데이터 조회
	 */
	List<SOMap> selectOrderPaymentList(SOMap param);

	/**
	 * 회원 진행중인 주문건 조회
	 * @param param
	 * @return
	 */
	int selectUserOrderIngCnt(SOMap param);

	/**
	 *	대시보드 판매 현황 1개월 조회
	 */
	SOMap selectOrderDashBoard(SOMap param);

	/**
	 *	대시보드 지연 현황 조회
	 */
	SOMap selectOrderClaimDelayDashBoard(SOMap param);

	/**
	 * 대시보드 매출 현황
	 */
	SOMap selectOrderClaimAccountDashBoard(SOMap param);

	/**
	 * 대시보드 매출 현황 결제/구매확정/취소,반품,교환 카운트
	 */
	SOMap selectDashBoardOrderClaimCount(SOMap param);

	/**
	 * 대시보드 4주 7일 통계 현황값
	 */
	SOMap selectOrderClaimAccountCalcDashBoard(SOMap param);

	/**
	 * 년간 대시보드 통계 현황값
	 */
	List<SOMap> selectYearCalcListDashBoard(SOMap param);

	/**
	 * 파트너사 2주간 통계 대시보드 현황값
	 */
	List<SOMap> selectTwoWeekPartnersDashBoard(SOMap param);

	/**
	 * 통계 상품 판매 순위
	 */
	List<SOMap> selectStatsProductRankByList(SOMap param);

	/**
	 * 통계 상품 판매 순위 정보
	 */
	SOMap selectStatsProductRankByListCountInfo(SOMap param);


	/**
	 * 통계 상품 분류 순위
	 */
	List<SOMap> selectStatsCategoryRankByList(SOMap param);

	/**
	 * 통계 상품 분류 순위 정보
	 */
	SOMap selectStatsCategoryRankByListCountInfo(SOMap param);

	/**
	 * 통계 일별/주별/월별 매출
	 */
	List<SOMap> selectStatsSaleDateList(SOMap param);

	/**
	 * 통계 일별/주별/월별 매출 정보
	 */
	SOMap selectStatsSaleDateListCountInfo(SOMap param);

	/**
	 * 통계 연령별/셩별 분석
	 */
	List<SOMap> selectAgeStatList(SOMap param);

	/**
	 * 통계 연령별/셩별 분석 정보
	 */
	SOMap selectAgeStatListCountInfo(SOMap param);

	/**
	 * 통계 일별/주별/월별 분석
	 */
	List<SOMap> selectDateStatList(SOMap param);

	/**
	 * 통계 일별/주별/월별 분석 정보
	 */
	SOMap selectDateStatListCountInfo(SOMap param);


	/**
	 * 파트너사 통계 매출현황
	 */
	List<SOMap> selectPartnersSaleList(SOMap param);

	/**
	 * 파트너사 통계 매출현황
	 */
	SOMap selectPartnersSaleListCountInfo(SOMap param);

}

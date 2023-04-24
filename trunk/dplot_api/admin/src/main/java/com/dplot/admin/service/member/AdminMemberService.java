package com.dplot.admin.service.member;

import com.dplot.common.SOMap;

import java.util.List;
import java.util.Map;

public interface AdminMemberService {
    /**
     * 관리자 - 정상회원 리스트
     * @param params
     * @return
     * @throws Exception
     */
    SOMap selectMemberList(SOMap params) throws Exception;

    /**
     * 관리자 - 정상회원 엑셀 리스트
     * @param params
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> selectMemberExcelList(SOMap params) throws Exception;

    /**
     * 관리자 - 휴면회원 리스트
     * @param params
     * @return
     * @throws Exception
     */
    SOMap selectDormancyMemberList(SOMap params) throws Exception;

    /**
     * 관리자 - 탈퇴회원 복구처리
     */
    SOMap memberResignRestore(SOMap params) throws Exception;

    /**
     * 휴면회원 탈퇴처리
     * @param params
     * @return
     * @throws Exception
     */
    SOMap memberSleepResign(SOMap params) throws Exception;

    /**
     * 휴면회원 리스트 조회
     * @param params
     * @return
     * @throws Exception
     */
    SOMap selectResignMemberList(SOMap params) throws Exception;

    /**
     * 관리자 - 전체회원 - 블랙회원 업데이트 처리 (회원 메모 INSERT)
     * @param params
     * @return
     * @throws Exception
     */
    SOMap updateBlackAndMemo(SOMap params) throws Exception;

    /**
     * 관리자 - 전체회원 - 탈퇴회원 처리 (회원 메모 INSERT)
     * @param params
     * @return
     * @throws Exception
     */
    SOMap memberResign(SOMap params) throws Exception;

    /**
     * 관리자 - 전체회원 - 회원 디테일 조회
     * @param params
     * @return
     * @throws Exception
     */
    SOMap selectMemberDetail(SOMap params) throws Exception;

    /**
     * 관리자 - 회원정보 상세에서 보여질 1:1 문의 리스트 (3개)
     * @param params
     * @return
     * @throws Exception
     */
    List<SOMap> selectMyInquiryList(SOMap params) throws Exception;

    /**
     * 관리자 - 회원정보 상세에서 패스워드 초기화
     * @param params
     * @return
     * @throws Exception
     */
    SOMap updateMemberPasswordTemp(SOMap params) throws Exception;

    /**
     * 관리자 - 전체회원 - 회원상세 - 회원수정
     * @param params
     * @return
     * @throws Exception
     */
    SOMap updateMemberInfo(SOMap params) throws Exception;

    /**
     * 관리자 - 전체회원 - 회원상세 - 메모 조회
     * @param params
     * @return
     * @throws Exception
     */
    SOMap selectMemberMemoList(SOMap params) throws Exception;

    /**
     * 관리자 - 전체회원 - 회원상세 - 메모 등록
     * @param params
     * @return
     * @throws Exception
     */
    SOMap insertMemberMemo(SOMap params) throws Exception;

    /**
     * 관리자 - 전체회원 - 회원상세 - 메모 삭제
     * @param params
     * @return
     * @throws Exception
     */
    SOMap deleteMemberMemo(SOMap params) throws Exception;

    /**
     * 회원 상세 적립금 리스트
     */
    SOMap selectMemberReserveInfo(SOMap params) throws Exception;

    /**
     * 회원 상세 적립금 리스트 엑셀 리스트
     */
    List<Map<String, Object>> selectMemberReserveListForExcel(SOMap params) throws Exception;

    /**
     * 적립금 조회(디테일)
     * @param params
     * @return
     * @throws Exception
     */
    SOMap getMemberCMoneyInfo(SOMap params) throws Exception;

    /**
     * 적립금 및 포인트 보유(사용가능) 금액만 조회(적립금/D포인트/임직원적립금)
     * @param params
     * @return
     * @throws Exception
     */
    SOMap selectMemberCmoney(SOMap params) throws Exception;

    /**
     * 블랙 회원 처리 업데이트
     * @param params
     * @return
     * @throws Exception
     */
    SOMap updateBlack(SOMap params) throws Exception;

    /**
     * 회원 D포인트 정보 조회
     * @param params
     * @return
     * @throws Exception
     */
    SOMap selectMemberEPointInfo(SOMap params) throws Exception;

    /**
     * 회원 D포인트 리스트 조회 엑셀
     * @param params
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> selectMemberEPointListForExcel(SOMap params) throws Exception;

    /**
     * 회원 찜한상품 리스트 조회
     * @param params
     * @return
     * @throws Exception
     */
    SOMap selectMemberWishList(SOMap params) throws Exception;

    /**
     * 회원 장바구니 리스트 조회
     * @param params
     * @return
     * @throws Exception
     */
    SOMap selectMemberCartList(SOMap params) throws Exception;

    /**
     * 회원 쿠폰 리스트 조회
     * @param params
     * @return
     * @throws Exception
     */
    SOMap selectMemberCouponList(SOMap params) throws Exception;

    /**
     * 회원정보 상세의 쿠폰정보
     */
    SOMap selectMyCouponInfo(SOMap params) throws Exception;

    /**
     * 회원 작성 리뷰 목록
     */
    SOMap selectMemberReviewList(SOMap params) throws Exception;

    /**
     * 최근 주문 리스트
     */
    List<SOMap> selectMemberOrder(SOMap params) throws Exception;
}

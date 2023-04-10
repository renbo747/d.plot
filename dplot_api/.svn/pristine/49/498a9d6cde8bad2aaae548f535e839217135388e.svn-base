package com.dplot.mapper;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

import java.util.List;

@MapperInterface
public interface BatchMapper {
    /**
     * 사이트 설정 정보 조회 (SITE ID)
     */
    SOMap selectConfigBySiteId(String siteId);

    /**
     * 파트너사 3개월 미접속 휴점 처리 프로시저
     */
    void spUpdatePartnersStateByLastlogin(SOMap param);

    /**
     * 회원 1년 미접속 휴점 처리 프로시저
     */
    void spUpdateMemberStateByLastLogin(SOMap param);

    /**
     * 전시기간에 따른 상품 판매 상태 업데이트 처리
     */
    void spUpdateGoodsStateByDisplay(SOMap param);

    /**
     * 재고수량에 따른 상품 자동품절 업데이트 처리
     */
    void spUpdateGoodsStateByStock(SOMap param);

    /**
     * 회원 휴면 1년 지난 계정 탈퇴 처리 프로시저
     */
    void spDeleteMemberThatDormancy(SOMap param);

    /**
     * 1분 마다 회원 적립금/e포인트 소멸처리
     */
    void spMemberPointExtinct(SOMap param);

    /**
     * 매일 회원 생일 확인 후 적립금 지급
     */
    void spMemberBirthdayPayPoint(SOMap param);

    /**
     * 적립금 예약 지급
     */
    void spInsertReservePayPoint(SOMap param);

    /**
     * e포인트 예약 지급
     */
    void spInsertEPointPayPoint(SOMap param);

    /**
     * 쿠폰 상태 업데이트
     */
    void spCouponStateUpdate(SOMap param);

    /**
     * 주문 상태 업데이트 처리
     */
    void spOrderStatusUpdate(SOMap param);

    /**
     * 파트너사 동의현황 게시상태 업데이트
     */
    void spUpdatePartnersAgreeState(SOMap param);

    /**
     * 베스트 상품 등록
     */
    void spBestGoodsInsert(SOMap param);
}

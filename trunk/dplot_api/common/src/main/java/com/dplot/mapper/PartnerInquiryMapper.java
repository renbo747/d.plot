package com.dplot.mapper;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

import java.util.List;

/**
 * @author : ywm2004
 * @discription : 파트너사 문의 테이블 Mapper (t_partner_inquiry)
 * @fileName : PartnerInquiryMapper.java
 * @date : 2021-11-22
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021-11-22	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface PartnerInquiryMapper {

    /**
     * 파트너사 문의 조회
     *
     * @param params
     * @return
     */
    List<SOMap> selectPartnerInquiryList(SOMap params);

    /**
     * 파트너사 문의 상세 조회
     *
     * @param params
     * @return
     */
    SOMap selectPartnerInquiryDtl(SOMap params);

    /**
     * 파트너사 문의 상태 값(대기, 완료)
     *
     * @param params
     * @return
     */
    SOMap selectPartnerInquiryListState(SOMap params);

    /**
     * 파트너사 문의 저장
     *
     * @param params
     */
    void insertPartnerInquiry(SOMap params);

    /**
     * 파트너사 문의 수정
     *
     * @param params
     */
    void updatePartnerInquiry(SOMap params);

    /**
     * 관리자 문의 대시보드
     */
    SOMap selectPartnerInquiryDashBoard(SOMap params);

}

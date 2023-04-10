package com.dplot.mapper;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

import java.util.List;

/**
 * @author : ywm2004
 * @discription : 1:1 문의 Mapper (T_Inquiry)
 * @fileName : InquiryMapper.java
 * @date : 2021-11-19
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021-11-19	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface InquiryMapper {

    /**
     * 1:1 문의 조회
     *
     * @param params
     * @return
     */
    List<SOMap> selectInquiryList(SOMap params);
    
	/**
	 * 1:1 문의 내역 검색
	 * @param params
	 * @return
	 */
	List<SOMap> selectInquiry(SOMap params);
	
	/**
	 * 1:1 문의 내역 검색 카운트
	 * @param params
	 * @return
	 */
	int selectInquiryCount(SOMap params);
    
    
	/**
	 * 1:1 문의 내역 상세
	 * @param params
	 * @return
	 */
	SOMap selectInquiryDetail(SOMap params);

	/** 1:1 문의 내역 상세 주문선택목록 맵핑
	 * @param params
	 * @return
	 */
	List<SOMap> selectInquiryOrderDetail(SOMap params);

	/** 1:1 문의 내역 삭제
	 * @param params
	 * @return
	 */
	int deleteInquiryIdx(SOMap params);
    

    /**
     * 1:1 문의 상태 갯수 (대기, 완료)
     *
     * @param params
     * @return
     */
    SOMap selectInquiryState(SOMap params);

    /**
     * 1:1 문의 수정
     *
     * @param params
     */
    int updateInquiry(SOMap params);

    /**
     * 1:1 문의 작성
     *
     * @param params
     */
    int insertInquiry(SOMap params);
    
    /** 1:1 문의 주문상품 등록
     * @param params
     * @return
     */
    int insertInquiryOrder(SOMap params);
    
    /**1:1 문의 - 주문상품맵핑 삭제
     * @param params
     * @return
     */
    int deleteInquiryMapping(SOMap params);
    
	/**
	 * 1:1문의 유형 수정
	 * @param params
	 */
	int updateInquiryIdx(SOMap params);    
    

    List<SOMap> selectInquiryListForMemberInfo(SOMap params);

	int sendPartnerInquiry(SOMap params);

    /**
     * 주문상세 CS처리목록 조회
     *
     * @param params
     */
	List<SOMap> selecrOrderInquiryList(SOMap params);
	
	
    SOMap selectInquiryDtl(SOMap params);

	/**
	 * 관리자 대시보드용 문의 데이터
	 */
	SOMap selectInquiryDashBoard(SOMap param);
	
}

package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @discription	: AS문의 Mapper (T_ASINQUIRY)
 * @fileName	: AsInquiryMapper.java
 * @author		: JSK
 * @date		: 2022.03.25
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.03.25	JSK			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface AsInquiryMapper {
	
    /**
     * A/S문의 목록조회
     * @param params
     * @return List<SOMap>
     */
    List<SOMap> selectAsInquiryList(SOMap params);
    
    /**
     * 비회원 A/S문의 목록조회
     * @param params
     * @return List<SOMap>
     */
    List<SOMap> selectNoneMemberAsApplyList(SOMap params);
	
    /**
     * A/S문의 상태별 건수 조회
     * @param params
     * @return SOMap
     */
    SOMap selectAsInquiryListCount(SOMap params);
	
    /**
     * A/S문의 접수완료가 아닌 건수 조회
     * @param params
     * @return int
     */
    int selectNonReceiptAsCount(SOMap params);
	
    /**
     * A/S상태 갱신
     * @param params
     * @return int
     */
    int updateAsStatus(SOMap params);
	
    /**
     * A/S문의내용 수정
     * @param params
     * @return int
     */
    int updateAsInquiry(SOMap params);
	
    /**
     * A/S문의 상세조회
     * @param params
     * @return SOMap
     */
    SOMap selectAsInquiryDetail(SOMap params);
    
    /**AS신청 완료
     * @param params
     * @return
     */
    int insertAsApplySave(SOMap params);
    
    /**AS신청 목록 리스트
     * @param params
     * @return
     */
    List<SOMap> selectAsApplyList(SOMap params);
    
    /**AS신청 목록 리스트 총 갯수
     * @param params
     * @return
     */
    int selectAsApplyListCount(SOMap params);
    
    /**AS 신청 목록 상세
     * @param params
     * @return
     */
    SOMap selectAsApplyDetail(SOMap params);
    
    /**AS 신청 삭제
     * @param params
     * @return
     */
    int deleteAsApply(SOMap params);
    
    /**AS 신청 수정
     * @param params
     * @return
     */
    int updateAsApply(SOMap params);
}

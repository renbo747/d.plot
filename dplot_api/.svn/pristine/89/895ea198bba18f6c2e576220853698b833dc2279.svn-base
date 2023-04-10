package com.dplot.mapper;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

import java.util.List;

/**
 * @discription	: 클레임쿠폰 Mapper (T_CLAIM_COUPON
 * @fileName	: ClaimCouponMapper.java
 * @author		: JSK
 * @date		: 2022.03.02
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.03.02	JSK			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface ClaimCouponMapper {
    /**
     * 관리자-클레임상품 할인내역 조회
     * @param param
     * @return List<SOMap>
     */
    List<SOMap> selectAdminClaimDiscountList(SOMap param);
    
    /**
     * 주문쿠폰목록 클레임제외
     * @param param
     * @return
     */
    List<SOMap> selectOrderCouponListWithoutClaim(SOMap param);
    
    /**
     * 클레임쿠폰목록
     * @param param
     * @return
     */
    List<SOMap> selectOrderCouponList(SOMap param);
    
    /**
     * 클레임 쿠폰저장
     * @param param
     * @return
     */
    int insertClaimCoupon(SOMap param);
    
    /**
     * 클레임 쿠폰여부 조회
     * @param param
     * @return
     */
    int selectClaimCouponCheck(SOMap param);
    
    /**
     * 클레임 삭제여부 수정
     * @param param
     * @return
     */
    int updateClaimCouponTrash(SOMap param);

    /**
     * 클레임 쿠폰 리스트 ERP 전송용 (취소만)
     */
    List<SOMap> selectClaimCouponERPData(SOMap param);

    /**
     * 클레임 쿠폰 리스트 ERP 전송용 (반품용)
     */
    List<SOMap> selectReturnCouponERPData(SOMap param);
}

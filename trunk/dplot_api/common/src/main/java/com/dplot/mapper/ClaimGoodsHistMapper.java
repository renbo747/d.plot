package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

@MapperInterface
public interface ClaimGoodsHistMapper {

    /**
     * 클레임상태이력 등록처리 (굿스플로 배송추적처리 배치에서 사용)
     */
    int insertClaimGoodsHist(SOMap param);
	
    /**
     * 클레임신청 상태이력 저장 
     * @param param
     * @return
     */
    int insertClaimGoodsHistForApply(SOMap param);
    
	/**
	 * 클레임상태이력 목록조회
	 */
	List<SOMap> selectClaimStatusHistList(SOMap params);

	/**
	 * 굿스플로 배송상태 변경에 따른 히스토리 등록
	 */
	int insertClaimGoodsHisForDelivery(SOMap param);
	
    /**
     * 클레임 상태이력 저장 (클레임신청 이후)
     */
    int insertClaimGoodsHistAll(SOMap param);
}

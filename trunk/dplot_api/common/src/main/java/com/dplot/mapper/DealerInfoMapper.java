package com.dplot.mapper;

import java.util.List;
import java.util.Map;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

@MapperInterface
public interface DealerInfoMapper {

    /**
     *파트너사 정보 등록
     */
    void insertPartnershipInfo(Map<String, Object> param);

    /**
     * 파트너사 정보 수정
     */
    void updatePartnershipInfo(SOMap param);

    /**
     * 파트너사주소 조회
     */
    SOMap selectPartnerAddr(SOMap param) throws Exception;
    
    
    /**
     * 상품상세 위탁사 AS정보 조회
     * @param param
     * @return
     * @throws Exception
     */
    SOMap selectDealerAsInfo(SOMap param) throws Exception;

    /**
     *
     */
    SOMap selectDealerInfoByCI(SOMap param) throws Exception;

    /**
     *
     */
    List<SOMap> selectDealerInfoByCIList(SOMap param) throws Exception;

    /**
     * 파트너사 추가수정권한 여부 조회
     * @param param
     * @return int
     * @throws Exception
     */
    int selectDealerAddAuth(SOMap param) throws Exception;
}

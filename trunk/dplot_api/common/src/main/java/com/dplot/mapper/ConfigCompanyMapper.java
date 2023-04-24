package com.dplot.mapper;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * @discription	: 환경설정_상점정보 Mapper (T_CONFIG_COMPANY)
 * @fileName	: ConfigCompanyMapper.java
 * @author		: JSK
 * @date		: 2021.11.23
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021.11.23	JSK			최초생성
 * -----------------------------------------------------------------
 */
@MapperInterface
public interface ConfigCompanyMapper {
    
    /**
     * 사업자주소 조회 
     * @param param
     * @return SOMap
     * @throws Exception
     */
    SOMap selectCompanyAddr(SOMap param) throws Exception;
    
    /**
     * 상품상세 자사 AS정보 조회
     * @param param
     * @return SOMap
     * @throws Exception
     */
    SOMap selectCompanyAsInfo(SOMap param) throws Exception;
}

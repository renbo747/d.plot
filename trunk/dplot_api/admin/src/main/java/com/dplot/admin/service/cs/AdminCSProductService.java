package com.dplot.admin.service.cs;

import com.dplot.common.SOMap;

/**
 * @author : ywm2004
 * @discription : CS관리 상품 문의 Service
 * @fileName : AdminCSProductService.java
 * @date : 2021-11-16
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021-11-16	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
public interface AdminCSProductService {

    /**
     * 상품 문의 조회
     *
     * @param params
     * @return
     */
    SOMap selectProductList(SOMap params) throws Exception;

    /**
     * 상품 상세 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    SOMap selectProductDtl(SOMap params) throws Exception;

    /**
     * 상품 문의 수정
     *
     * @param params
     * @throws Exception
     */
    void updateProduct(SOMap params) throws Exception;

}

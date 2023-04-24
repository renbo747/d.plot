package com.dplot.admin.controller.cs;

import com.dplot.admin.service.cs.AdminCSProductService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.Status;
import com.dplot.mapper.CodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : ywm2004
 * @discription : CS관리 상품 문의 Controller
 * @fileName : ProductController.java
 * @date : 2021-11-16
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021-11-16	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
@RestController
@RequestMapping(value={"/admin/cs/product", "/partners/cs/product"})
public class ProductController {

    @Autowired
    AdminCSProductService adminCSProductService;

    /**
     * 상품 문의 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Response page(@RequestBody SOMap params) throws Exception {
        return new Response(adminCSProductService.selectProductList(params));
    }

    /**
     * 상품 문의 상세
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Response detail(@RequestBody SOMap params) throws Exception {
        return new Response(adminCSProductService.selectProductDtl(params));
    }

    /**
     * 상품 문의 수정
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Response update(@RequestBody SOMap params) throws Exception {
        Response result = null;

        try {
            adminCSProductService.updateProduct(params);
            result = new Response(Status.OK);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Response(Status.FAIL);
        }

        return result;
    }
}

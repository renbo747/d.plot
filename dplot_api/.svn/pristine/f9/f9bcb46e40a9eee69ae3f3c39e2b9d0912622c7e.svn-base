package com.dplot.admin.controller.cs;

import com.dplot.admin.service.cs.AdminCSPartnersService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : ywm2004
 * @discription : CS관리 파트너사 문의 Controller
 * @fileName : PartnersController.java
 * @date : 2021-11-22
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021-11-22	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
@RestController
@RequestMapping(value={"/admin/cs/partners", "/partners/cs/partners"})
public class PartnersController {

    @Autowired
    AdminCSPartnersService adminCSPartnersService;

    /**
     * 파트너사 문의 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Response page(@RequestBody SOMap params) throws Exception {
        return new Response(adminCSPartnersService.selectPartnersList(params));
    }

    /**
     * 파트너사 문의 상세 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Response detail(@RequestBody SOMap params) throws Exception {
        return new Response(adminCSPartnersService.selectPartnersDtl(params));
    }

    /**
     * 파트너사 문의 저장
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Response save(@RequestBody SOMap params) throws Exception {
        Response result = null;

        try {
            adminCSPartnersService.insertPartners(params);
            result = new Response(Status.OK);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Response(Status.FAIL);
        }

        return result;
    }

    /**
     * 파트너사 문의 수정
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Response update(@RequestBody SOMap params) throws Exception {
        Response result = null;

        try {
            adminCSPartnersService.updatePartners(params);
            result = new Response(Status.OK);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Response(Status.FAIL);
        }

        return result;
    }

    /**
     * 파트너사 목록 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/box/dealer", method = RequestMethod.POST)
    public Response searchDealer(@RequestBody SOMap params) throws Exception {
        return new Response(adminCSPartnersService.selectBoxPartnersList(params));
    }

    /**
     * 파트너사 담당자 조회
     *
     * @param params
     * @throws Exception
     */
    @RequestMapping(value = "/box/charge", method = RequestMethod.POST)
    public Response searchCharge(@RequestBody SOMap params) throws Exception {
        return new Response(adminCSPartnersService.selectBoxChargeList(params));
    }
}

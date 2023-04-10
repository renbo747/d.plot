package com.dplot.admin.controller.partners;

import com.dplot.admin.service.partners.AdminPartnersConsentService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * @author : ywm2004
 * @discription : 파트너사 동의현황 게시판 Controller
 * @fileName : PartnersConsentController.java
 * @date : 2021-11-04
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021-11-04	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
@RestController
@RequestMapping(value = {"/admin/partners/consent", "/partners/partners/consent"})
public class PartnersConsentController {

    @Autowired
    private AdminPartnersConsentService adminPartnersConsentService;

    /**
     * 파트너사 동의현황 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Response page(@RequestBody SOMap params) throws Exception {
        return new Response(adminPartnersConsentService.selectConsentList(params));
    }

    /**
     * 파트너사 동의현황 게시
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Response save(MultipartHttpServletRequest files, @RequestPart("params") SOMap params) throws Exception {
        Response response;
        try {
            params.put("uploadfile", files);
            adminPartnersConsentService.insertConsent(params);
            response = new Response(Status.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response(Status.FAIL);
        }

        return response;
    }

    /**
     * 파트너사 동의현황 상세 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Response detail(@RequestBody SOMap params) throws Exception {
        return new Response(adminPartnersConsentService.selectConsentDetail(params));
    }

    /**
     * 파트너사 동의현황 게시 종류 변경
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Response update(MultipartHttpServletRequest files, @RequestPart("params") SOMap params) throws Exception {
        Response response;
        try {
            params.put("uploadfile", files);
            adminPartnersConsentService.updateConsent(params);
            response = new Response(Status.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response(Status.FAIL);
        }
        return response;
    }

    @RequestMapping(value = "/update/trash", method = RequestMethod.POST)
    public Response updateTrash(@RequestBody SOMap params) throws Exception {
        Response result = null;

        try {
            adminPartnersConsentService.updateTrash(params);
            result = new Response(Status.OK);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Response(Status.FAIL);
        }

        return result;
    }
    
    @RequestMapping(value = "/agree", method = RequestMethod.POST)
    public Response agree(@RequestBody SOMap params) throws Exception {
        return new Response(adminPartnersConsentService.updateAgree(params));
    }

}

package com.dplot.admin.controller.cs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dplot.admin.service.cs.AdminCSAsService;
import com.dplot.common.Response;
import com.dplot.common.SOMap;

/**
 * @discription	: A/S문의 Controller
 * @fileName	: AsInquiryController.java
 * @author		: JSK
 * @date		: 2022.03.25
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022.03.25	JSK			최초생성
 * -----------------------------------------------------------------
 */
@RestController
@RequestMapping(value={"/admin/cs/as", "/partners/cs/as"})
public class AsInquiryController {

    @Autowired
    private AdminCSAsService adminCSAsService;
    
    /**
     * A/S문의 목록조회
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Response asList(@RequestBody SOMap params) throws Exception {
        return new Response(adminCSAsService.selectAsInquiryList(params));
    }
    
    /**
     * A/S문의 신청취소
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public Response cancelAs(@RequestBody SOMap params) throws Exception {
        SOMap result = new SOMap();
    	result.put("resultCnt", adminCSAsService.updateAsStatusCancel(params));
        return new Response(result);
    }
    
    /**
     * A/S문의 파트너사 전송
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public Response sendAs(@RequestBody SOMap params) throws Exception {
        SOMap result = new SOMap();
    	result.put("resultCnt", adminCSAsService.sendPartnerAsInquiry(params));
        return new Response(result);
    }
    
    /**
     * A/S문의 상세조회
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Response asDetail(@RequestBody SOMap params) throws Exception {
        return new Response(adminCSAsService.selectAsInquiryDetail(params));
    }
    
    /**
     * A/S문의 답변
     * @param params
     * @return Response
     * @throws Exception
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Response saveAsInquiry(@RequestBody SOMap params) throws Exception {
        SOMap result = new SOMap();
    	result.put("resultCnt", adminCSAsService.saveAsInquiry(params));
        return new Response(result);
    }
}

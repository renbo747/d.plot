package com.dplot.admin.controller.cs;

import com.dplot.admin.service.cs.AdminCSFaqService;
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
 * @discription : CS관리 자주 묻는 질문 Controller
 * @fileName : FaqController.java
 * @date : 2021-11-15
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2021-11-15	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
@RestController
@RequestMapping("/admin/cs/faq")
public class FaqController {

    @Autowired
    private AdminCSFaqService adminCSFaqService;

    /**
     * FAQ 리스트 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Response page(@RequestBody SOMap params) throws Exception {
        return new Response(adminCSFaqService.selectFaqList(params));
    }

    /**
     * FAQ 상세 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Response detail(@RequestBody SOMap params) throws Exception {
        return new Response(adminCSFaqService.selectFaqDetail(params));
    }

    /**
     * FAQ 저장
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Response save(@RequestBody SOMap params) throws Exception {
        Response result = null;

        try {
            adminCSFaqService.insertFaq(params);
            result = new Response(Status.OK);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Response(Status.FAIL);
        }

        return result;
    }

    /**
     * FAQ 수정
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Response update(@RequestBody SOMap params) throws Exception {
        Response result = null;

        try {
            adminCSFaqService.updateFaq(params);
            result = new Response(Status.OK);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Response(Status.FAIL);
        }

        return result;
    }
    
    /**
     * FAQ 메인노출여부 체크
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public Response check(@RequestBody SOMap params) throws Exception {
        return new Response(adminCSFaqService.checkFaqMain(params));
    }
}

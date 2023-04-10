package com.dplot.admin.controller.common;

import com.dplot.admin.service.common.AdminCommonMailService;
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
 * @discription : 메일 보내기 공통 팝업 Controller
 * @fileName : AdminCommonMailController.java
 * @date : 2022-02-09
 * =================================================================
 * DATE			AUTHOR		NOTE
 * -----------------------------------------------------------------
 * 2022-02-09	ywm2004		최초생성
 * -----------------------------------------------------------------
 */
@RequestMapping("/admin/mail")
@RestController
public class AdminCommonMailController {

    @Autowired
    AdminCommonMailService adminCommonMailService;

    /**
     * 유저 정보 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/init", method = RequestMethod.POST)
    public Response init(@RequestBody SOMap params) throws Exception {
        return new Response(adminCommonMailService.selectUserInfo(params));
    }

    /**
     * 메일 발송
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public Response send(@RequestBody SOMap params) throws Exception {
        return new Response(adminCommonMailService.sendMail(params));
    }
}
